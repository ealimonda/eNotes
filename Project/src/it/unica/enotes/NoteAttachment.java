/* *************************************************************************************************
 *                                         eNotes                                                  *
 * *************************************************************************************************
 * File:        NoteAttachment.java                                                                *
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

/**
 * Represents an attachment to a note
 * @author Emanuele Alimonda
 * @author Giovanni Serra
 */
public class NoteAttachment {
   public static final int kFileTypeInvalid = 0;
   public static final int kFileTypePicture = 1;
   public static final int kFileTypeAudio = 2;
   public static final int kFileTypeVideo = 3;
   public static final int kFileTypeMax = 4;

   public static final String kAttachmentFileName = "name";
   public static final String kAttachmentFileType = "type";
   public static final String kAttachmentFileData = "data";
   
   public static final long kMaxAttachmentSize = 1000000; // 1MB

   private String _filename;
   private ByteBuffer _filedata;
   private int _filetype;

   public NoteAttachment() {
      this.init();
   }

   public NoteAttachment(int filetype, File file) {
      this();
      this.init(filetype, file);
   }

   public NoteAttachment(int filetype, String filename, InputStream stream) {
      this();
      this.init(filetype, filename, stream);
   }

   public NoteAttachment(JSONObject contents) {
      this();
      this.init(contents);
   }

   public NoteAttachment(int filetype, String filename, String filedata) {
      this();
      this.init(filetype, filename, filedata);
   }

   public NoteAttachment(int filetype, String filename, ByteBuffer filedata) {
      this();
      this.init(filetype, filename, filedata);
   }
   
   private void init() {
      this._filename = "";
      this._filedata = ByteBuffer.allocate(0);
      this._filetype = kFileTypeInvalid;
   }
	   
   private void init(int filetype, File file) {
      if (!file.isFile()) {
         return;
      }
      try {
         FileInputStream importStream = new FileInputStream(file);
         this.init(filetype, file.getName(), importStream);
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
   }

   private void init(int filetype, String filename, InputStream stream) {
      if (filetype <= kFileTypeInvalid || filetype >= kFileTypeMax) {
         return;
      }
      this._filename = filename;
      this._filetype = filetype;
      
      try {
         int bufferSize = 0x20000; // ~130k
         byte[] buffer = new byte[bufferSize];
         ByteArrayOutputStream importBufferStream = new ByteArrayOutputStream(bufferSize);
         int read;
         while (true) {
            read = stream.read(buffer);
            if (read == -1) {
               break;
            }
            importBufferStream.write(buffer, 0, read);
            if (importBufferStream.size() > kMaxAttachmentSize) {
               this.init();
               return;
            }
         }
         this._filedata = ByteBuffer.wrap(importBufferStream.toByteArray());
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void init(JSONObject contents) {
      if (contents == null) {
         return;
      }
      try {
         if (contents.has(kAttachmentFileName)
               && contents.has(kAttachmentFileType)
               && contents.has(kAttachmentFileData)) {
            this.setFilename(contents.getString(kAttachmentFileName));
            this.setFiletype(contents.getInt(kAttachmentFileType));
            this.setEncodedData(contents.getString(kAttachmentFileData));
               }
      } catch (JSONException e) {
         this.setFilename(null);
         this.setFiletype(kFileTypeInvalid);
         this.setRawData(null);
      }
   }

   private void init(int filetype, String filename, String filedata) {
      this.setFiletype(filetype);
      this.setFilename(filename);
      this.setEncodedData(filedata);
   }
   private void init(int filetype, String filename, ByteBuffer filedata) {
      this.setFiletype(filetype);
      this.setFilename(filename);
      this.setRawData(filedata);
   }

   public String getFilename() {
      return this._filename;
   }
   public void setFilename(String filename) {
      if (filename == null) {
         this._filename = "";
      } else {
         this._filename = filename;
      }
   }

   public ByteBuffer getRawData() {
      return this._filedata;
   }
   public void setRawData(ByteBuffer rawdata) {
      if (rawdata == null) {
         this._filedata = ByteBuffer.allocate(0);
      } else {
         this._filedata = rawdata;
      }
   }
   public String getEncodedData() {
      return Base64.encodeToString(this._filedata.array(), Base64.DEFAULT);
   }
   public void setEncodedData(String encodeddata) {
      if (encodeddata == null) {
         this._filedata = ByteBuffer.allocate(0);
      } else {
         byte[] buffer = Base64.decode(encodeddata, Base64.DEFAULT);
         this._filedata = ByteBuffer.allocate(buffer.length);
         this._filedata.put(buffer);
      }
   }

   public int getFiletype() {
      return this._filetype;
   }
   public void setFiletype(int filetype) {
      if (filetype >= kFileTypeMax || filetype <= kFileTypeInvalid) {
         this._filetype = kFileTypeInvalid;
         return;
      }
      this._filetype = filetype;
   }

   public JSONObject getJson() {
      if (this._filetype <= kFileTypeInvalid || this._filetype >= kFileTypeMax) {
         return null;
      }
      JSONObject jsObject;
      try {
         jsObject = new JSONObject();
         jsObject.put(kAttachmentFileName, this.getFilename());
         jsObject.put(kAttachmentFileType, this.getFiletype());
         jsObject.put(kAttachmentFileData, this.getEncodedData());
      } catch (JSONException e) {
         return null;
      }
      return jsObject;
   }
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
