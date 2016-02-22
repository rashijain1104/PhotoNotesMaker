package com.example.sharmila.notesmaker;
/**
 * Created by Rashi Jain on 2/13/2016.
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;


public class ViewPhotoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activeity_detail);

        Photo photo = getIntent().getExtras().getParcelable("myPhoto");
        String name = photo.getName();
        String path = photo.getFileName();
        Log.d("PhotoNotes", String.format("%s, %s", name, path));
        TextView textView = (TextView) findViewById(R.id.rowText);
        textView.setText(name);

        ImageView imageView = (ImageView) findViewById(R.id.rowImage);

        File imgFile = new File(path);
        if(imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.uninstall) {
        	Uri packageURI = Uri.parse("package:com.example.sharmila.notesmaker");
        	Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        	startActivity(uninstallIntent);
        }
        else if(id == R.id.addphoto){
        	final Intent intent = new Intent(ViewPhotoActivity.this, AddPhotoActivity.class);
        	startActivity(intent);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
}