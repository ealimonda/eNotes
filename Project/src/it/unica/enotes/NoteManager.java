/* *************************************************************************************************
 *                                         eNotes                                                  *
 * *************************************************************************************************
 * File:        NoteManager.java                                                                   *
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
import it.unica.enotes.Note;
import it.unica.enotes.NoteDB;

/**
 * Manages a collection of notes, calling the appropriate classes and methods to load, save, search
 * them.
 * @author Emanuele Alimonda
 */
public class NoteManager {
   // Members
   /** Notes */
   private ArrayList<Note> _notes;
   /** Database backend */
   private NoteDB _dbBackend;

   /** Default constructor */
   public NoteManager() {
      this._notes = new ArrayList<Note>();
      //this._dbBackend = new NoteDB(); // FIXME: it needs a Context argument, where do I get it?
   }

   /**
    * Load notes titles and GUIDs
    * @return  true in case of success, false otherwise
    */
   public boolean loadNotes() {
      // TODO
      this._notes.add(new Note(null, "Test note"));
      return true;
   }

   /**
    * Load a note's details
    * @param id   GUID of the note
    * @return     true in case of success, false otherwise
    */
   public boolean loadNoteDetails(String id) {
      // TODO: Search the note by ID.  We'll need some HashList or something
      //       then call the appropriate NoteDB function and finally setLoaded(true)
      return true;
   }

   /**
    * Save all unsaved notes
    * @return  true in case of success, false otherwise
    */
   public boolean saveNotes() {
      // TODO: Iterate over all notes and save the ones where isDirty()==true
      //       then setDirty(false)
      return true;
   }

   // TODO: Method to edit a note

   // Accessors
   /**
    * Get the notes list
    * @return  The notes list
    */
   public ArrayList<Note> getNotes() {
      return this._notes;
   }
   /**
    * Get the notes count
    * @return  The amount of existing notes
    */
   public int getNotesCount() {
      return this._notes.size();
   }
   /**
    * Get a specific note by index
    * @param index   The index of the searched note
    * @return        The requested note
    */
   public Note getNoteAtIndex(int index) {
      if (index >= this._notes.size() || index < 0)
         return null;
      return this._notes.get(index);
   }
   /**
    * Append a note to the list
    * @param note    The note to append
    */
   public void appendNote(Note note) {
      if (note != null)
         this._notes.add(note);
   }
   // TODO: add a method to remove a note
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
