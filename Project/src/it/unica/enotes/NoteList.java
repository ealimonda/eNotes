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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Activity to list all existing notes
 */
public class NoteList extends ListActivity {
   private static final int DIALOG_ID = 100;
   private SQLiteDatabase database;
   private CursorAdapter dataSource;
   private View entryView;
   private EditText titleEditor;
   private EditText contentEditor;
   private static final String fields[] = { "title", "content", BaseColumns._ID };
   
   /** Called when the activity is first created. */
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      NoteDB helper = new NoteDB(this);
	   database = helper.getWritableDatabase();
      Cursor data = database.query("notes", fields, null, null, null, null, null);

      dataSource = new SimpleCursorAdapter(this, R.layout.row, data, fields,
            new int[] { R.id.title, R.id.content });

      ListView view = getListView();
      view.setHeaderDividersEnabled(true);
      view.addHeaderView(getLayoutInflater().inflate(R.layout.row, null));

      setListAdapter(dataSource);
      //setContentView(R.layout.main);
   }
      
   /*@Override
   public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.main_menu, menu);
       return true;
   }*/
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      menu.add(0, DIALOG_ID, 1, R.string.addItem).setIcon(getResources().getDrawable(R.drawable.ic_new_note));
      menu.add(0, 0, 2, R.string.searchItem).setIcon(getResources().getDrawable(R.drawable.ic_search));
      return true;
   }
   
   @Override
   public boolean onMenuItemSelected(int featureId, MenuItem item) {
      if (item.getItemId() == DIALOG_ID) {
         showDialog(DIALOG_ID);
      }
      return true;
   }

   @Override
   protected Dialog onCreateDialog(int id) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      entryView = getLayoutInflater().inflate(R.layout.entry, null);
      builder.setView(entryView);
      titleEditor = (EditText) entryView.findViewById(R.id.title);
      contentEditor = (EditText) entryView.findViewById(R.id.content);
      builder.setTitle(R.string.addDialogTitle);
      builder.setPositiveButton(R.string.addItem, new DialogInterface.OnClickListener() {

         //@Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            ContentValues values = new ContentValues();
            values.put("title", titleEditor.getText().toString());
            values.put("content", contentEditor.getText().toString());
            database.insert("notes", null, values);
            dataSource.getCursor().requery();
         }
      });
      
      builder.setNegativeButton(R.string.cancelItem, new DialogInterface.OnClickListener() {
         
         //@Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
         }
      });
      return builder.create();
   }
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
