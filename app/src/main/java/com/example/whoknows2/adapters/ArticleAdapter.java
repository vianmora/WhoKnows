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

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {


    public ArticleAdapter(@NonNull Context context, ArrayList<Article> articles_Array) {
        super(context, 0, articles_Array);

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_flux, parent, false);
        }

        TextView mTitle_tv = (TextView) convertView.findViewById(R.id.item_flux_title);

        TextView mDescription_tv = (TextView) convertView.findViewById(R.id.item_flux_description);

        mTitle_tv.setText(article.getTitle());

        mDescription_tv.setText(article.getDescription());

        return convertView;

    }

}
