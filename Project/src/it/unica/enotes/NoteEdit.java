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
	   private NoteDB _database;
	   /** ID of the current note */
	   private long _noteID;
	   /** Current note */
	   private Note _note;
	   /** Logging tag */
	   private static final String kTag = "NoteEdit";

	   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.edit);

      Bundle extras = getIntent().getExtras();
      if (extras == null) {
    	  this._noteID = -1;
         return;
      }
      this._noteID = extras.getLong(Note.kID);
      
      this._note = null;

      this._database = new NoteDB();
   }
   
   @Override
   public void onResume() {
      super.onResume();
      // shows selected note's details
      this._note = this._database.getNoteById(this, this._noteID);
      EditText titleField = (EditText)findViewById(R.id.EditTitle);
      titleField.setText(this._note.getTitle());
      EditText contentField = (EditText)findViewById(R.id.EditContent);
      contentField.setText(this._note.getText());
   }
   
   @Override
   public void onPause() {
      super.onPause();
      EditText titleField = (EditText)findViewById(R.id.EditTitle);
      EditText contentField = (EditText)findViewById(R.id.EditContent);
      this._note.setTitle(titleField.getText().toString());
      this._note.setText(contentField.getText().toString());
      this._note.setTimestamp(null);
      this._database.saveNote(this, this._noteID, this._note);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      menu.add(0, 0, 1, R.string.addAttachment).setIcon(getResources().getDrawable(R.drawable.ic_menu_attachment));
      menu.add(0, 0, 2, R.string.addUrl).setIcon(getResources().getDrawable(R.drawable.ic_input_get));
      return true;
   }
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
