package com.example.whoknows2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.whoknows2.R;
import com.example.whoknows2.models.Article;
import com.squareup.picasso.Picasso;

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
        mTitle_tv.setText(article.getTitle());


        TextView mDescription_tv = (TextView) convertView.findViewById(R.id.item_flux_description);
        mDescription_tv.setText(article.getDescription());

        TextView mDate_tv = (TextView) convertView.findViewById(R.id.item_flux_date);
        mDate_tv.setText(article.getPublishedAt());

        ImageView mIcone_left = (ImageView) convertView.findViewById(R.id.item_flux_icone_left_img);
        ImageView mIcone_right = (ImageView) convertView.findViewById(R.id.item_flux_icone_right_img);


        Picasso.with(getContext()).setLoggingEnabled(true);


        if (!article.getUrlToImage().equals("null") && !article.getSource().getId().equals("le-monde")){
            if(position%2==1){
                mIcone_right.setVisibility(View.GONE);
                mIcone_left.setVisibility(View.VISIBLE);

                Picasso.with(getContext()).load(article.getUrlToImage()).fit().centerInside().into(mIcone_left);
            }
            else{
                mIcone_right.setVisibility(View.VISIBLE);
                mIcone_left.setVisibility(View.GONE);

                Picasso.with(getContext()).load(article.getUrlToImage()).fit().centerInside().into(mIcone_right);
            }

        }

        return convertView;

    }

}
