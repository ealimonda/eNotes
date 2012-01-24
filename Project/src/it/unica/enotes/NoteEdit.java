/* *************************************************************************************************
 *                                         eNotes                                                  *
 * *************************************************************************************************
 * File:        NoteEdit.java                                                                      *
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
import android.widget.EditText;

/**
 * Activity to edit an existing note or compose a new one
 */
public class NoteEdit extends Activity {
	   /** Database helper / content provider */
	   private NoteDB database;
	   /** Logging tag */
	   private static final String kTag = "NoteEdit";

	   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.edit);

      Bundle extras = getIntent().getExtras();
      if (extras == null) {
         return;
      }

      database = new NoteDB();

      // shows selected note's details
      Note note = database.getNoteById(this, extras.getLong(Note.kID));
      EditText titleField = (EditText)findViewById(R.id.EditTitle);
      titleField.setText(note.getTitle());
      EditText contentField = (EditText)findViewById(R.id.EditContent);
      contentField.setText(note.getText());
}

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      menu.add(0, 0, 1, R.string.addAttachment).setIcon(getResources().getDrawable(R.drawable.ic_menu_attachment));
      menu.add(0, 0, 2, R.string.addUrl).setIcon(getResources().getDrawable(R.drawable.ic_input_get));
      return true;
   }
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
