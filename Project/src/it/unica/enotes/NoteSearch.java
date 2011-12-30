/* *************************************************************************************************
 *                                         eNotes                                                  *
 * *************************************************************************************************
 * File:        NoteSearch.java                                                                    *
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

/**
 * Activity to search a note by tag
 */
public class NoteSearch extends Activity {
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
   }
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
