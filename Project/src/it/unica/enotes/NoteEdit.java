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

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
<<<<<<< HEAD:Project/src/it/unica/enotes/NoteEdit.java
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
=======
>>>>>>> 2d88e27ed7c7c8d8a69e4a34fe22cdb37ea1217d:Project/src/it/unica/enotes/NoteEdit.java
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
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
   /** Menu IDs */
   private static final int kMenuItemUrl = 102;
   private static final int kMenuItemAttach = 103;
   private static final int kSubmenuPictures = 104;
   private static final int kSubmenuCapturePicture = 105;
   private static final int kSubmenuVideos = 106;
   private static final int kSubmenuCaptureVideo = 107;  
   private static final int kSubmenuAudio = 108;
   private static final int kSubmenuRecordAudio = 109;
   private static final int TAKE_PICTURE_WITH_GALLERY = 110;
   private static final int TAKE_PICTURE_WITH_CAMERA = 111;
   private static final int TAKE_VIDEO_WITH_GALLERY = 112;
   private static final int TAKE_VIDEO_WITH_CAMERA = 113;
   private static final int TAKE_SOUND_WITH_AUDIO = 114;
   private static final int TAKE_SOUND_WITH_MIC = 115;

   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Log.v(kTag, "created activity");
      setContentView(R.layout.edit);
      
      EditText addUrl = (EditText) findViewById(R.id.EditUrlText);     
      Button cancelUrl = (Button) findViewById(R.id.EditUrlButton);     
      addUrl.setVisibility(View.GONE);
      cancelUrl.setVisibility(View.GONE);
//      addUrl.setKeyListener(DialerKeyListener.getInstance());

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
      
      this._note = this._database.getNoteById(this, this._noteID);

      EditText titleField = (EditText)findViewById(R.id.EditTitle);
      EditText contentField = (EditText)findViewById(R.id.EditContent);
      EditText tagsField = (EditText)findViewById(R.id.EditTags);
      EditText urlField = (EditText)findViewById(R.id.EditUrlText);
      Button cancelUrl = (Button) findViewById(R.id.EditUrlButton);     

      titleField.setText(this._note.getTitle());
      contentField.setText(this._note.getText());
      tagsField.setText(this._note.getTagsAsString());
      String url = this._note.getURL();
      if (url.length() > 0) {
    	  urlField.setText(this._note.getURL());
    	  urlField.setVisibility(View.VISIBLE);
    	  cancelUrl.setVisibility(View.VISIBLE);
      } else {
    	  urlField.setVisibility(View.GONE);
    	  cancelUrl.setVisibility(View.GONE);
      }
   }
   
   @Override
   public void onPause() {
      super.onPause();

      EditText titleField = (EditText)findViewById(R.id.EditTitle);
      EditText contentField = (EditText)findViewById(R.id.EditContent);
      EditText tagsField = (EditText)findViewById(R.id.EditTags);
      EditText urlField = (EditText)findViewById(R.id.EditUrlText);

      this._note.setTitle(titleField.getText().toString());
      this._note.setText(contentField.getText().toString());
      this._note.setTagsFromString(tagsField.getText().toString());
      this._note.setTimestamp(null);
      this._note.setURL(urlField.getText().toString());

      this._database.saveNote(this, this._noteID, this._note);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      //menu.add(0, 0, 1, R.string.addAttachment).setIcon(getResources().getDrawable(R.drawable.ic_menu_attachment));
	  //menu.add(0, kMenuItemAttach, 1, R.string.addAttachment).setIcon(getResources().getDrawable(R.drawable.ic_menu_attachment));
	  SubMenu attachListMenu = menu.addSubMenu(0, kMenuItemAttach, 1, R.string.addAttachment).setIcon(getResources().getDrawable(R.drawable.ic_menu_attachment));
      menu.add(0, kMenuItemUrl, 2, R.string.addUrl).setIcon(getResources().getDrawable(R.drawable.ic_input_get));
      attachListMenu.add(0, kSubmenuPictures, 3, R.string.addPictures);
      attachListMenu.add(0, kSubmenuCapturePicture, 4, R.string.addCapturePicture);
      attachListMenu.add(0, kSubmenuVideos, 5, R.string.addVideos);
      attachListMenu.add(0, kSubmenuCaptureVideo, 6, R.string.addCaptureVideo);
      attachListMenu.add(0, kSubmenuAudio, 7, R.string.addAudio);
      attachListMenu.add(0, kSubmenuRecordAudio, 8, R.string.addRecordAudio);      
      return true;
   }
   
   @Override
   public boolean onMenuItemSelected(int featureId, MenuItem item) {
      if (this._note == null) {
              Log.v(kTag, "ERROR: note is NULL!!!");
              return false;
      }
      if (item.getItemId() == kMenuItemUrl) {
          EditText addUrl = (EditText) findViewById(R.id.EditUrlText);     
          Button cancelUrl = (Button) findViewById(R.id.EditUrlButton);     
    	  addUrl.setVisibility(View.VISIBLE);
    	  cancelUrl.setVisibility(View.VISIBLE);
   		}
      if (item.getItemId() == kSubmenuPictures) {    	 
    	  Intent takePictureFromGalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    	  startActivityForResult(takePictureFromGalleryIntent, TAKE_PICTURE_WITH_GALLERY);
      	}
      if (item.getItemId() == kSubmenuCapturePicture) {
    	  ContentValues values = new ContentValues();
    	  values.put(MediaStore.Images.Media.TITLE, "eNotesTmpPhoto.jpg");
    	  Uri capturedImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    	  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	  intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
    	  startActivityForResult(intent, TAKE_PICTURE_WITH_CAMERA);    	  
      	} 
      if (item.getItemId() == kSubmenuVideos) {    	  
      }
      if (item.getItemId() == kSubmenuCaptureVideo) {    	  
      }
      if (item.getItemId() == kSubmenuAudio) { 
    	  Intent takeSoundFromAudio = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    	  startActivityForResult(takeSoundFromAudio, TAKE_SOUND_WITH_AUDIO);
      }
      if (item.getItemId() == kSubmenuRecordAudio) {    	  
      }
      return true;
      }
   
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // TODO Auto-generated method stub
    super.onActivityResult(requestCode, resultCode, data);
    switch(requestCode) {
    // Picture taken from gallery
<<<<<<< HEAD:Project/src/it/unica/enotes/NoteEdit.java
    case TAKE_PICTURE_WITH_GALLERY:
        if(resultCode == RESULT_OK){  
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            // the selected image
            Bitmap picture = BitmapFactory.decodeFile(filePath);            
        }
        break;        
=======
    if (requestCode == TAKE_PICTURE_WITH_GALLERY) {
    	if (resultCode == RESULT_OK){
    		// on activity return
    		Uri targetUri = data.getData();
	     //textTargetUri.setText(targetUri.toString());
    		}
    	}
>>>>>>> 2d88e27ed7c7c8d8a69e4a34fe22cdb37ea1217d:Project/src/it/unica/enotes/NoteEdit.java
    // Picture taken from camera
    case TAKE_PICTURE_WITH_CAMERA:    
    	if (resultCode == Activity.RESULT_OK) {
            // on activity return
    		String[] projection = { MediaStore.Images.Media.DATA };
      	  ContentValues values = new ContentValues();
      	  values.put(MediaStore.Images.Media.TITLE, "tmpPhoto.jpg");
      	  Uri capturedImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    		Cursor cursor = managedQuery(capturedImageUri, projection, null, null, null);
    		int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    		cursor.moveToFirst();
    		String  capturedImageFilePath = cursor.getString(column_index_data);
//            String SD_CARD_TEMP_DIR = Environment.getExternalStorageDirectory() + File.separator + "tmpPhoto.jpg";
            File f = new File(capturedImageFilePath);
            // FIXME: WIP, I'll fix this up later
//            try {
//            	Uri capturedImage =  Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(), f.getAbsolutePath(), null, null));
//            	Log.i("camera", "Selected image: " + capturedImage.toString());
            	Log.v("camera", "Selected image: " + capturedImageFilePath + " ("+ f.getAbsolutePath() +")");
            	f.delete();
  //          	} catch (FileNotFoundException e) {
            		// TODO Auto-generated catch block
    //        		e.printStackTrace();
      //      		}
            }
    	else {
    		Log.i("Camera", "Result code was " + resultCode);
    		}
    	break;
	// Audio taken from audio
    case TAKE_SOUND_WITH_AUDIO:
    	if (resultCode == Activity.RESULT_OK) {
            // on activity return
    		}
    	break;
    default:
  	  //return false;
    	}
    }
}
/* vim: set ts=3 sw=3 smarttab expandtab cc=101 : */
