/* *************************************************************************************************
 *                                         eNotes                                                  *
 * *************************************************************************************************
 * File:        Note.java                                                                          *
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

/**
 * Represents each note entry and offers conversion methods from and to the JSON format.
 */
public class Note {

   // Members
   /** Note title */
   private String _title;
   /** Note contents (plain text) */
   private String _text;
   /** Note attachment */
   // TODO
   /** Note URL */
   private string _URL;
   /** Note tags */
   private Array _tags;

   /** Constructor */
   public Note() {
      this._title = new String();
      this._text = new String();
//      this._attachment = null;
      this._URL = null;
      this._tags = new Array();
   }
   public Note(String title, String text, String URL, Array tags) {
   }

   public NoteFromJSON(String json) {
   }

   public String getJSON() {
   }

   // Accessors
   public String getTitle() {
      // TODO
   }
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
