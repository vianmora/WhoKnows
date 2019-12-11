package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import com.example.whoknows2.models.FluxArticle;
import com.example.whoknows2.controllers.LecteurFluxAsync;

public class MainActivity extends AppCompatActivity {

    /* variables */
    private String mUrlSources_str = "http://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
    private String mUrlFlux_str = "http://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
    private String mSource = "google-news-fr";
    private LecteurFluxAsync lecteurFluxAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lecteurFluxAsync = new LecteurFluxAsync(MainActivity.this, mUrlFlux_str, mSource);

    }

    protected void onStart() {

        super.onStart();

        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("source")){
                mSource = intent.getStringExtra("source");
            }
        }

        lecteurFluxAsync = new LecteurFluxAsync(MainActivity.this, mUrlFlux_str, mSource);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.source_menu, menu);
        return true;
    }

}
