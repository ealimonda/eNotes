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

import java.util.ArrayList;

/**
 * Represents each note entry and offers conversion methods from and to the JSON format.
 * @author Emanuele Alimonda
 * @author Giovanni Serra
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
   private String _URL;
   /** Note tags */
   private ArrayList<String> _tags;

   /** Default constructor */
   public Note() {
      this._title = new String();
      this._text = new String();
//      this._attachment = null;
      this._URL = null;
      this._tags = new ArrayList<String>(0);
   }
   /**
    * Constructor
    * @param title   The title of the note
    * @param text    Content (text) of the note
    * @param URL     Attached URL to the note
    * @param tags    ArrayList of tags of the note
    */
   public Note(String title, String text, String URL, ArrayList<String> tags) {
      // FIXME: Missing attachment
      this._title = title;
      this._text = text;
      this._URL = URL;
      this._tags = tags;
   }

   /**
    * Import note from JSON data
    * @param json    A json object representing the note
    */
   public void NoteFromJSON(String json) {
      // TODO
      // possibly FIXME:  Use a JSONObject (org.json.JSONObject)?
   }

   /**
    * Export note to JSON data
    * @return  A JSON object representing the note
    * */
   public String getJSON() {
      // TODO
      // possibly FIXME:  Use a JSONObject (org.json.JSONObject)?
      return "";
   }

   // Accessors
   /**
    * Get the note's title
    * @return  The note's title
    */
   public String getTitle() {
      return this._title;
   }
   /**
    * Set the note's title
    * @param title   A new title to set
    */
   public void setTitle(String newTitle) {
      this._title = newTitle;
   }

   /**
    * Get the note's contents
    * @return  The note's contents (text)
    */
   public String getText() {
      return this._text;
   }
   /**
    * Set the note's contents
    * @param text    New text contents for the note
    */
   public void setText(String text) {
      this._text = text;
   }

   /**
    * Get the note's attached URL
    * @return  The note's attached URL
    */
   public String getURL() {
      return this._URL;
   }
   /**
    * Set the note's attached URL
    * @param URL     A new URL to attach (replacing the previous one)
    */
   public void setURL(String URL) {
      this._URL = URL;
   }

   // TODO: Handle attachments

   /**
    * Get the note's tags
    * @return  The note's tags as an ArrayList.  In no tags are set, return an empty array.
    */
   public ArrayList<String> getTags() {
      return this._tags;
   }
   /**
    * Set the note's tags
    * @param tags    An ArrayList containing the tags to set
    */
   public void setTags(ArrayList<String> tags) {
      this._tags = tags;
   }

   // TODO: Add or remove one tag at a time?
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
