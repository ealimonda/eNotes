/* *************************************************************************************************
 *                                         eNotes                                                  *
 * *************************************************************************************************
 * File:        NoteDB.java                                                                        *
 * Copyright:   (c) 2011-2012 Emanuele Alimonda, Giovanni Serra                                    *
 *              eNotes is free software: you can redistribute it and/or modify it under the terms  *
 *              of the GNU General Public License as published by the Free Software Foundation,    *
 *              either version 3 of the License, or (at your option) any later version.  eNotes is *
 *              distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without  *
 *              even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  *
 *              See the GNU General Public License for more details.  You should have received a   *
 *              copy of the GNU General Public License along with eNotes.                          *
 *              If not, see <http://www.gnu.org/licenses/>                                         *
 * *************************************************************************************************/

package it.unica.enotes;

import java.util.HashMap;
import java.util.UUID;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;

/**
 * Loads and stores notes to the database and performs searches
 */
public class NoteDB extends ContentProvider {

   // ContentProvider constants
   private static final String kDatabaseName = "eNotes.db";
   private static final String kDatabaseTableNotes = "notes";
   private static final int kDatabaseVersion = 1;

   private static final String kDefaultSortOrder = Note.kTimestamp + " DESC";
   
   private static final int kUriNotes = 1;
   private static final int kUriNoteByID = 2;
   private static final int kUriNoteByGUID = 3;
   private static final int kUriNotesByTag = 4;

   public static final String[] kNotesFullProjection = {
      Note.kGUID,
      Note.kTitle,
      Note.kTimestamp,
      Note.kContent
   };
   public static final String[] kNotesHeadersProjection = {
	   Note.kID,
      Note.kGUID,
      Note.kTitle,
      Note.kTimestamp
   };
   
   private static final UriMatcher uriMatcher;

   private static HashMap<String, String> notesProjectionMap;
      
//      public NoteDB(Context context) {
//         super(context, "eNotes", null, 1);
//      }
   
   private static final String TAG = "INFO";
   public static final int  OPEN_READWRITE = 1;
   //private CursorAdapter dataSource;

   public static class NoteDBHelper extends SQLiteOpenHelper {

      NoteDBHelper(Context context) {
         super(context, kDatabaseName, null, kDatabaseVersion);
      }

      // create tables in the database
      @Override
      public void onCreate(SQLiteDatabase db) {
    	  Log.v("notedb", "NoteDB Helper create");
         // FIXME: TEMP Testing stuff
         db.execSQL("DROP TABLE IF EXISTS "+ kDatabaseTableNotes +";");
         db.execSQL("CREATE TABLE IF NOT EXISTS " + kDatabaseTableNotes + " ("
               + Note.kID +         " INTEGER PRIMARY KEY AUTOINCREMENT,"
               + Note.kGUID +       " STRING UNIQUE,"
               + Note.kTitle +      " STRING,"
               + Note.kTimestamp +  " INTEGER,"
               + Note.kContent +    " TEXT"
            // TODO: Tags
               + ");");
         // FIXME: TEMP testing stuff
         Time testTime = new Time();
         testTime.set(0, 10, 11, 5, 1, 2012);
         Note[] testNotes = {
            new Note(null, "First test note", null, "Text of the note\n\nBla bla", null, null),
            new Note(null, "Second test note", null, "Bla bla", null, null),
            new Note(null, "Another test note", testTime, "Text of the note\n\nBla bla", null, null)
         };

         for (int i = 0; i < testNotes.length; ++i) {
            ContentValues values = new ContentValues();
            values.put(Note.kGUID, testNotes[i].getGUID());
            values.put(Note.kTitle, testNotes[i].getTitle());
            values.put(Note.kTimestamp, testNotes[i].getTimestamp().toMillis(false));
            values.put(Note.kContent, testNotes[i].getJSON());
            db.insert(kDatabaseTableNotes, null, values);
         }
         //db.close();
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         // TODO Auto-generated method stub
      }
   }

   private NoteDBHelper dbHelper;

   @Override
   public boolean onCreate() {
      dbHelper = new NoteDBHelper(getContext());
	  Log.v("notedb", "NoteDB create");
      return true;
   }

   @Override
   public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
         String sortOrder) {
      SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
      
      switch (uriMatcher.match(uri)) {
         case kUriNotes:
            qb.setTables(kDatabaseTableNotes);
            qb.setProjectionMap(notesProjectionMap);
            break;
            
         case kUriNoteByID:
             qb.setTables(kDatabaseTableNotes);
             qb.setProjectionMap(notesProjectionMap);
             qb.appendWhere(Note.kID + "=" + uri.getPathSegments().get(2));
             break;
  
         case kUriNoteByGUID:
            qb.setTables(kDatabaseTableNotes);
            qb.setProjectionMap(notesProjectionMap);
            qb.appendWhere(Note.kGUID + "=" + uri.getPathSegments().get(2));
            break;
 
         case kUriNotesByTag:
            // TODO
//            qb.setTables(kDatabaseTableNotes);
//            qb.setProjectionMap(notesProjectionMap);
//            qb.appendWhere(Note.kTitle + " LIKE '" + uri.getLastPathSegment()+"'");
//            break;
  
         default:
            throw new IllegalArgumentException("Unknown URI " + uri);
      }

      // If no sort order is specified use the default
      String orderBy;
      if (TextUtils.isEmpty(sortOrder)) {
         orderBy = kDefaultSortOrder;
      } else {
         orderBy = sortOrder;
      }

      // Get the database and run the query
      SQLiteDatabase db = dbHelper.getReadableDatabase();
      Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
      
      // Tell the cursor what uri to watch, so it knows when its source data changes
      c.setNotificationUri(getContext().getContentResolver(), uri);
      return c;
   }

   @Override
   public String getType(Uri uri) {
      switch (uriMatcher.match(uri)) {
         case kUriNotes:
            return Note.kContentType;
 
         case kUriNoteByID:
         case kUriNoteByGUID:
            return Note.kContentItemType;
     
         case kUriNotesByTag:
            // TODO
//            return Note.kContentType;

         default:
            throw new IllegalArgumentException("Unknown URI " + uri);
      }
   }

   // TODO the following method is probably never called and probably wouldn't work
   @Override
   public Uri insert(Uri uri, ContentValues initialValues) {
	   Log.v(TAG, "insert() was called");
      // Validate the requested uri
      if (uriMatcher.match(uri) != kUriNotes) {
         throw new IllegalArgumentException("Unknown URI " + uri);
      }
      
      ContentValues values;
      if (initialValues != null) {
         values = new ContentValues(initialValues);
      } else {
         values = new ContentValues();
      }

      Time now = new Time();
      now.setToNow();

      // Make sure that the fields are all set
      if (values.containsKey(Note.kTimestamp) == false) {
         values.put(Note.kTimestamp, now.toMillis(false));
      }

      // The guid is the unique identifier for a note so it has to be set.
      if (values.containsKey(Note.kGUID) == false) {
         values.put(Note.kGUID, UUID.randomUUID().toString());
      }

      // TODO does this make sense?
      if (values.containsKey(Note.kTitle) == false) {
         Resources r = Resources.getSystem();
         values.put(Note.kTitle, r.getString(android.R.string.untitled));
      }

      if (values.containsKey(Note.kContent) == false) {
         values.put(Note.kContent, "");
      }

      SQLiteDatabase db = dbHelper.getWritableDatabase();
      long rowId = db.insert(kDatabaseTableNotes, null, values);
      if (rowId > 0) {
    	  Uri noteUri = Uri.withAppendedPath(Note.kContentURI, "id/"+rowId);

         getContext().getContentResolver().notifyChange(noteUri, null);
         dbHelper.close();
         return noteUri;
      }

      dbHelper.close();
      throw new SQLException("Failed to insert row into " + uri);
    }

   @Override
   public int delete(Uri uri, String where, String[] whereArgs) {
      // TODO
	   SQLiteDatabase db = dbHelper.getWritableDatabase();
	   int count;
	   switch (uriMatcher.match(uri)) {
	   	case kUriNotes:
	   		count = db.delete(kDatabaseTableNotes, where, whereArgs);
            break;
            
         case kUriNoteByID:
            String noteId = uri.getPathSegments().get(2);
            count = db.delete(kDatabaseTableNotes, Note.kID + "=" + noteId
                  + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
         case kUriNoteByGUID:
             String noteGuid = uri.getPathSegments().get(2);
             count = db.delete(kDatabaseTableNotes, Note.kGUID + "=" + noteGuid
                   + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
             break;
             
         default:
            throw new IllegalArgumentException("Unknown URI " + uri);
      }
      
      getContext().getContentResolver().notifyChange(uri, null);
      return count;
   }

   @Override
   public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
      SQLiteDatabase db = dbHelper.getWritableDatabase();
      int count;
      switch (uriMatcher.match(uri)) {
         case kUriNotes:
            count = db.update(kDatabaseTableNotes, values, where, whereArgs);
            break;
            
         case kUriNoteByID:
            String noteId = uri.getPathSegments().get(2);
            count = db.update(kDatabaseTableNotes, values, Note.kID + "=" + noteId
                  + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
            
         case kUriNoteByGUID:
             String noteGuid = uri.getPathSegments().get(2);
             count = db.update(kDatabaseTableNotes, values, Note.kGUID + "=" + noteGuid
                   + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
             break;
             
         default:
            throw new IllegalArgumentException("Unknown URI " + uri);
      }
      
      getContext().getContentResolver().notifyChange(uri, null);
      return count;
   }

   static {
      uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
      uriMatcher.addURI(Note.kAuthority, "notes", kUriNotes);
      uriMatcher.addURI(Note.kAuthority, "notes/id/#", kUriNoteByID);
      uriMatcher.addURI(Note.kAuthority, "notes/guid/*", kUriNoteByGUID);
      uriMatcher.addURI(Note.kAuthority, "notes/tag/*", kUriNotesByTag);

      notesProjectionMap = new HashMap<String, String>();
      notesProjectionMap.put(Note.kID, Note.kID);
      notesProjectionMap.put(Note.kGUID, Note.kGUID);
      notesProjectionMap.put(Note.kTitle, Note.kTitle);
      notesProjectionMap.put(Note.kTimestamp, Note.kTimestamp);
      notesProjectionMap.put(Note.kContent, Note.kContent);
   }

   /**
    * Add the given note to the database
    * @param activity	The activity this is called from
    * @param id      	The ID of the note to store
    * @param title   	Title for the note to store
    * @param note    	Raw JSON data for the note to store
    * @return        	Success status
    */
   public boolean addNote(Activity activity, String id, String title, String note) {
     ContentResolver cr = activity.getContentResolver();
     ContentValues values = new ContentValues();
     values.put(Note.kGUID, id);
     values.put(Note.kTitle, title);
     values.put(Note.kContent, note);           
     Uri uri = cr.insert(Note.kContentURI, values);
     Log.v(TAG, "Inserted note: "+ uri.toString());
     return true;
   }

   // TODO: Add a method to check if an ID already exists ?

   /**
    * Delete the given note from the database
    * @param activity	The activity this is called from
    * @param id   		ID of the note to delete
    * @return     		Success status
    */
   public boolean deleteNote(Activity activity, long id) {
	     ContentResolver cr = activity.getContentResolver();
	     int quantity = cr.delete(Uri.withAppendedPath(Note.kContentURI, "id/"+id), null, null);
	     Log.v(TAG, "Deleted "+ quantity +" note(s)");
      return true;
   }

   /**
    * Get all notes with the given tag
    * @param tag  Tag to search for
    * @return     IDs of the wanted notes, as an ArrayList
    */
/*   public ArrayList<String> searchNotesByTag(String tag) {
      // TODO
      return new ArrayList<String>();
   }
*/

   /**
    * Get all notes' headers from the database
    * @return  a cursor pointing to all the notes' GUIDs, titles and timestamps
    */
   public Cursor getAllNotesHeaders(Activity activity) {
      // get a cursor representing all notes
      Uri notes = Note.kContentURI;
      String where = null;
      String orderBy;
      orderBy = Note.kTimestamp + " DESC";
      return activity.managedQuery(notes, kNotesHeadersProjection, where, null, orderBy);		
	}
   
   // gets a note from the content provider
   public Note getNote(Activity activity, Uri uri) {
      Note note = null;

      // can we find a matching note?
      Cursor cursor = activity.managedQuery(uri, kNotesFullProjection, null, null, null);
      // cursor must not be null and must return more than 0 entry
      if (!(cursor == null || cursor.getCount() == 0)) {
         // create the note from the cursor
         cursor.moveToFirst();
         String noteContent = cursor.getString(cursor.getColumnIndexOrThrow(Note.kContent));
         String noteTitle = cursor.getString(cursor.getColumnIndexOrThrow(Note.kTitle));
         String noteGUID = cursor.getString(cursor.getColumnIndexOrThrow(Note.kGUID));
         Time noteTimestamp = new Time();
         noteTimestamp.set(cursor.getLong(cursor.getColumnIndexOrThrow(Note.kTimestamp)));
			
			note = new Note(noteGUID, noteTitle);
         note.setTimestamp(noteTimestamp);
			note.NoteFromJSON(noteContent);
		}

		return note;
	}
   
   // Gets note by ID
   public Note getNoteById(Activity activity, long id) {
	   return getNote(activity, Uri.withAppendedPath(Note.kContentURI, "id/"+id));
   }
   
}

/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
