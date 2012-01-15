/* *************************************************************************************************
 *                                         eNotes                                                  *
 * *************************************************************************************************
 * File:        NoteList.java                                                                      *
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

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Activity to list all existing notes
 * @author Emanuele Alimonda
 * @author Giovanni Serra
 */
public class NoteList extends ListActivity {
   /** Menu IDs */
   private static final int kMenuItemAdd = 100;
   private static final int kMenuItemSearch = 101;
   /** Database helper / content provider */
   private NoteDB database;
   /** Fields to query */
   private static final String fields[] = { Note.kTitle, Note.kTimestamp, Note.kID };
   /** Logging tag */
   private static final String kTag = "NoteList";
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      database = new NoteDB();

      ListView view = getListView();
      view.setHeaderDividersEnabled(true);
      view.addHeaderView(getLayoutInflater().inflate(R.layout.row, null));

      refreshList();
      //setContentView(R.layout.main);
   }
      
   /**
    * Refresh the list, re-querying the database as needed
    */
   protected void refreshList() {
      Cursor data = database.getAllNotesHeaders(this);

      SimpleCursorAdapter dataSource = new SimpleCursorAdapter(this, R.layout.row, data, fields,
            new int[] { R.id.title, R.id.timestamp, -1 });

      dataSource.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

         @Override
         public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {
            if (aView.getId() == R.id.timestamp) {
               TextView textView = (TextView) aView;
               Time timestamp = new Time();
               timestamp.set(aCursor.getLong(aColumnIndex));
               textView.setText(timestamp.format("%c"));
               return true;
            }

            return false;
         }
      });

      setListAdapter(dataSource);
   }

   /*@Override
   public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.main_menu, menu);
       return true;
   }*/
   
   @Override
   protected void onListItemClick(ListView l, View v, int position, long id) {
      //String item = (String) getListAdapter().getItem(position);

      Intent i = new Intent(this, NoteView.class);
      i.putExtra(Note.kID, id);
      // Set the request code to any code you like, you can identify the callback via this code
      startActivityForResult(i, 0);
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      menu.add(0, kMenuItemAdd, 1, R.string.addItem).setIcon(getResources().getDrawable(R.drawable.ic_new_note));
      menu.add(0, kMenuItemSearch, 2, R.string.searchItem).setIcon(getResources().getDrawable(R.drawable.ic_search));
      return true;
   }
   
   @Override
   public boolean onMenuItemSelected(int featureId, MenuItem item) {
      if (item.getItemId() == kMenuItemAdd) {
         database.addNote(this, null, null, null);
//          Uri uri = cr.insert(Note.kContentURI, values);
         refreshList();
      }
      return true;
   }

   @Override
   protected Dialog onCreateDialog(int id) {
      return null;
   }
   
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
