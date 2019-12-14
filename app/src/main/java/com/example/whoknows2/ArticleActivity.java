package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whoknows2.models.Article;
import com.example.whoknows2.models.FluxArticles;

public class ArticleActivity extends AppCompatActivity {

    private Article mArticle;
    private String tag = "TAG PERSO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        mArticle = new Article();
        Intent intent = getIntent();

        if (intent != null){
            if (intent.hasExtra("id_article")){
                int position = intent.getIntExtra("id_article", 0);
                mArticle = FluxArticles.getArticleById(position);

                display(mArticle);
            }
            else {
                Log.d("tag", "mauvais nom d'extra");
            }
        }
        else {
            Log.d("tag", "pas d'intent");
        }


    }

    private void display (final Article article){
        TextView mTitre_txt = (TextView) findViewById(R.id.activity_article_titre_txt);
        mTitre_txt.setText(article.getTitle());

        TextView mSource_txt = (TextView) findViewById(R.id.activity_article_source_txt);
        mSource_txt.setText(article.getSource().getName());

        TextView mDescription_txt = (TextView) findViewById(R.id.activity_article_description_txt);
        mDescription_txt.setText(article.getDescription());


        Button mPrecedent_btn = (Button) findViewById(R.id.activity_article_precedent_btn);
        mPrecedent_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (article.getId()!=0){
                    Intent toArticle = new Intent(getApplicationContext(), ArticleActivity.class);
                    toArticle.putExtra("id_article", article.getId()-1);
                    startActivity(toArticle);
                }
                else{
                    Toast mToast = Toast.makeText(getApplicationContext(), "pas d'articles supplémentaires", Toast.LENGTH_SHORT);

                }
            }
        });

        Button mSuivant_btn = (Button) findViewById(R.id.activity_article_suivant_btn);
        mSuivant_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (article.getId()!=FluxArticles.get_totalResult()){
                    Intent toArticle = new Intent(getApplicationContext(), ArticleActivity.class);
                    toArticle.putExtra("id_article", article.getId()+1);
                    startActivity(toArticle);
                }
                else{
                    Toast mToast = Toast.makeText(getApplicationContext(), "pas d'articles supplémentaires", Toast.LENGTH_SHORT);

                }
            }
        });

        Button mRetour_btn = (Button) findViewById(R.id.activity_article_retour_btn);
        mRetour_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toFlux = new Intent(getApplicationContext(), FluxActivity.class);
                toFlux.putExtra("source", (Parcelable) article.getSource());
                startActivity(toFlux);
            }
        });

        Button mWeb_btn = (Button) findViewById(R.id.activity_article_web_btn);
        mWeb_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toWeb = new Intent(getApplicationContext(), WebActivity.class);
                toWeb.putExtra("url", article.getUrl());
                startActivity(toWeb);
            }
        });


    }
}
