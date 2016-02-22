package com.example.sharmila.notesmaker;
/**
 * Created by Rashi Jain on 2/13/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;


public class AddPhotoActivity extends ActionBarActivity {
    private static final int REQUEST_CODE = 0;
	EditText editText;
	ImageView imageView;
	Button photo;
	Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activeity_addphoto);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.caption);
        photo = (Button) findViewById(R.id.photobutton);
        save = (Button) findViewById(R.id.savebutton);
        save.setVisibility(Button.INVISIBLE);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	            finish();
			}
		});
    }

    private void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            save.setVisibility(Button.VISIBLE);

            Uri tempUri = getImageUri(getApplicationContext(), photo);

            Photo Photo = new Photo(editText.getText().toString(), getRealPathFromURI(tempUri));

            insert(Photo);
        }  
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(CompressFormat.JPEG, 100, bytes);
        String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null); 
        cursor.moveToFirst(); 
        int idx = cursor.getColumnIndex(Images.ImageColumns.DATA);
        return cursor.getString(idx); 
    }    
    


    private void insert(Photo Photo) {
        SQLiteDatabase db = new DBHelper(this).getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(DBHelper.NAME_COLUMN, Photo.getName());
        newValues.put(DBHelper.FILE_PATH_COLUMN, Photo.getFileName());
        db.insert(DBHelper.DATABASE_TABLE, null, newValues);
    }    
}
