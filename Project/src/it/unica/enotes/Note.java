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
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents each note entry and offers conversion methods from and to the JSON format.
 * @author Emanuele Alimonda
 * @author Giovanni Serra
 */
public class Note {

   // Members
   /** Have the note details been loaded? */
   private boolean _loaded;
   /** Have the note details been changed since the last save? */
   private boolean _dirty;
   /** Global Unique IDentifier of the note */
   private String _GUID;
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
      this(null, null, null, null, null);
   }
   /**
    * Constructor
    * @param GUID    The GUID of the note
    * @param title   The title of the note
    */
   public Note(String GUID, String title) {
      this(GUID, title, null, null, null);
   }
   /**
    * Constructor
    * @param GUID    The GUID of the note
    * @param title   The title of the note
    * @param text    Content (text) of the note
    * @param URL     Attached URL to the note
    * @param tags    ArrayList of tags of the note
    */
   public Note(String GUID, String title, String text, String URL, ArrayList<String> tags) {
      if (GUID == null) {
         this._GUID = UUID.randomUUID().toString();
      } else {
         this._GUID = GUID;
      }
      if (title != null) {
         this._title = title;
      } else {
         this._title = "";
      }
      if (text != null) {
         this._text = text;
         this._URL = URL;
         if (this._tags != null) {
            this._tags = tags;
         } else {
            this._tags = new ArrayList<String>(0);
         }
         this._loaded = true;
      } else {
         this._text = null;
         this._URL = null;
         this._tags = null;
         this._loaded = false;
      }
      this._dirty = false;
   }

   /**
    * Import note from JSON data
    * @param json    A json object representing the note
    */
   public void NoteFromJSON(String json) {
      JSONObject jsObject;
      try {
         jsObject = new JSONObject(json);
         if (jsObject.has("text")) {
            this._text = jsObject.getString("text");
         }
         if (jsObject.has("url")) {
            this._URL = jsObject.getString("url");
         }
         if (jsObject.has("tags")) {
            JSONArray tagsArray = jsObject.getJSONArray("tags");
            this._tags.clear();
            for (int i = 0; i < tagsArray.length(); i++) {
                this._tags.add(tagsArray.getString(i));
            }
         }
      } catch (JSONException e) {
         return;
      }
      return;
   }

   /**
    * Export note to JSON data
    * @return  A JSON object representing the note
    * */
   public String getJSON() {
      JSONObject jsObject;
      try {
         jsObject = new JSONObject();
         jsObject.put("text", this._text);
         jsObject.put("url", this._URL);
         jsObject.put("tags", new JSONArray(this._tags));
      } catch (JSONException e) {
         return "";
      }
      return jsObject.toString();
   }

   // Accessors
   /**
    * Get the note's GUID
    * @return  The note's GUID
    */
   public String getGUID() {
      return this._GUID;
   }
   /**
    * Set the note's GUID
    * @param GUID    A new GUID to set
    */
   public void setGUID(String GUID) {
      this._GUID = GUID;
      this.setDirty(true);
   }

   /**
    * Return the note details loaded state
    * @return  true if details have been loaded, false otherwise
    */
   public boolean isLoaded() {
      return this._loaded;
   }
   /**
    * Set the note details' loaded state
    * @param state   State to set
    */
   public void setLoaded(boolean state) {
      this._loaded = state;
      this.setDirty(false);
   }

   /**
    * Return the note's dirty state
    * @return  true is the note was edited after last save, false otherwise
    */
   public boolean isDirty() {
      return this._dirty;
   }

   /**
    * Set the dirty state for a note
    * @param state   Whether the note was edited after last save
    */
   public void setDirty(boolean state) {
      this._dirty = state;
   }

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
      this.setDirty(true);
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
      this.setDirty(true);
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
      this.setDirty(true);
   }

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
      this.setDirty(true);
   }

   // TODO: Add or remove one tag at a time?
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
