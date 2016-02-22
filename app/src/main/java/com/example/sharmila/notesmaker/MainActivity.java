package com.example.sharmila.notesmaker;
/**
 * Created by Rashi Jain on 2/13/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
	final Context context = this;
	final List<Photo> Photos = new ArrayList<Photo>();
	private ListView listView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
         deleteAll();

        query();

        listView.setAdapter(new DBAdapter(this, R.layout.activeity_detail, Photos));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                	Photo Photo = Photos.get(position);
                	final Intent intent = new Intent(MainActivity.this, ViewPhotoActivity.class).putExtra("myPhoto", Photo);
                	startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Photos.clear();
        query();

        listView.setAdapter(new DBAdapter(this, R.layout.activeity_detail, Photos));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Photo Photo = Photos.get(position);
                final Intent intent = new Intent(MainActivity.this, ViewPhotoActivity.class).putExtra("myPhoto", Photo);
                startActivity(intent);
            }
        });
    }


    private void deleteAll() {
        SQLiteDatabase db = new DBHelper(this).getWritableDatabase();
        String whereClause = DBHelper.NAME_COLUMN + "=?";
        String[] whereArgs = {"2"};
        db.delete(DBHelper.DATABASE_TABLE, null, null);
    }

    private void query() {
        SQLiteDatabase db = new DBHelper(this).getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DBHelper.ID_COLUMN, DBHelper.NAME_COLUMN, DBHelper.DESCRIPTION_COLUMN, DBHelper.FILE_PATH_COLUMN};
        Cursor cursor = db.query(DBHelper.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String filepath = cursor.getString(3);
            Photos.add(new Photo(name, filepath));
            Log.d("PhotoNotes", String.format("%s, %s",  name,  filepath));
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
        	final Intent intent = new Intent(MainActivity.this, AddPhotoActivity.class);
        	startActivity(intent);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
