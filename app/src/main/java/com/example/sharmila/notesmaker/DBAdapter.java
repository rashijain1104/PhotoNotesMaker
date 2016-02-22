package com.example.sharmila.notesmaker;
/**
 * Created by Rashi Jain on 2/13/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rashi Jain on 2/15/2016.
 */
public class DBAdapter extends ArrayAdapter<Photo> {




        private final List<Photo> Photos;

        public DBAdapter(Context context, int resource, List<Photo> Photos) {
            super(context, resource, Photos);
            this.Photos = Photos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getPhotoView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getPhotoView(position, convertView, parent);
        }

        public View getPhotoView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.activeity_detail, null);

            TextView textView = (TextView) row.findViewById(R.id.rowText);
            textView.setText(Photos.get(position).getName());

            return row;    }
    }
