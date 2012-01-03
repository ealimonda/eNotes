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

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Loads and stores notes to the database and performs searches
 */
public class NoteDB extends SQLiteOpenHelper {

	public NoteDB(Context context) {
		super(context, "eNotes", null, 1);
	}

	// create tables in the database
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS notes ("
				+ BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, content VARCHAR)");
		db.execSQL("INSERT INTO notes (title, content) VALUES ('Nota 1', 'Testo contenuto nella nota')");
		db.execSQL("INSERT INTO notes (title, content) VALUES ('Nota 2', 'Testo contenuto nella nota')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

   /**
    * Get all notes'IDs from the database
    * @return  an HashMap containing all the notes' IDs and Titles
    */
   public HashMap<String, String> getNotes() {
      // TODO: Query db and return 
      return new HashMap<String, String>();
   }

   /**
    * Get the count of notes in the database
    * @return  Amount of notes found
    */
   public int getNotesCount() {
      // TODO
      return 0;
   }

   /**
    * Get the details for the note with the given ID
    * @param id   ID to search for
    * @return     Raw JSON data for the searched note
    */
   public String getNoteDetails(String id) {
      // TODO
      return "";
   }

   // TODO: Add a method to only get the title for a note with the given ID? Do we need it?

   /**
    * Add the given note to the database
    * @param id      The ID of the note to store
    * @param title   Title for the note to store
    * @param note    Raw JSON data for the note to store
    * @return        Success status
    */
   public boolean addNote(String id, String title, String note) {
      // TODO: INSERT or REPLACE and return success status
      return true;
   }

   // TODO: Add a method to check if an ID already exists ?

   /**
    * Delete the given note from the database
    * @param id   ID of the note to delete
    * @return     Success status
    */
   public boolean deleteNote(String id) {
      // TODO
      return true;
   }

   /**
    * Get all notes with the given tag
    * @param tag  Tag to search for
    * @return     IDs of the wanted notes, as an ArrayList
    */
   public ArrayList<String> searchNotesByTag(String tag) {
      // TODO
      return new ArrayList<String>();
   }
}

/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
