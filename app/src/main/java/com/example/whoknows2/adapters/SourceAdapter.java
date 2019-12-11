package com.example.whoknows2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.whoknows2.R;
import com.example.whoknows2.models.Article;
import com.example.whoknows2.models.Source;

import java.util.ArrayList;

public class SourceAdapter extends ArrayAdapter<Source> {


    public SourceAdapter(@NonNull Context context, ArrayList<Source> sources) {
        super(context, 0, sources);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Source source = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, parent, false);
        }

        TextView mTitle_tv = (TextView) convertView.findViewById(R.id.item_spinner);

        mTitle_tv.setText(source.getName());

        return convertView;

    }
}
