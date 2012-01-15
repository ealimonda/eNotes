/* *************************************************************************************************
 *                                         eNotes                                                  *
 * *************************************************************************************************
 * File:        NoteView.java                                                                      *
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

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

/**
 * Activity to display the contents of a single note
 * @author Emanuele Alimonda
 * @author Giovanni Serra
 */
public class NoteView extends Activity {
   private static final String kTag = "NoteView";
   private NoteDB database;

   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.view);
      //Bundle extras = getIntentactivity().getExtras();
      Bundle extras = getIntent().getExtras();
      if (extras == null) {
         return;
      }

      // shows selected note's details
      database = new NoteDB();
      Note note = database.getNoteById(this, extras.getLong(Note.kID));
      TextView text1 = (TextView) findViewById(R.id.title);
      text1.setText(note.getTitle());
      TextView text2 = (TextView) findViewById(R.id.contents);
      text2.setText(note.getText());
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      menu.add(0, 0, 1, R.string.editItem).setIcon(getResources().getDrawable(R.drawable.ic_menu_edit));
      menu.add(0, 0, 2, R.string.deleteItem).setIcon(getResources().getDrawable(R.drawable.ic_menu_delete));
      menu.add(0, 0, 3, R.string.sendItem).setIcon(getResources().getDrawable(R.drawable.ic_menu_send));
      return true;
   }
}

/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
