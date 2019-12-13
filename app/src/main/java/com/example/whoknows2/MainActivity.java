package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.whoknows2.controllers.LecteurFluxAsync;

public class MainActivity extends AppCompatActivity {

    /* variables */
    private String mUrl_str = "http://newsapi.org/v2/";
    private String mSource = "google-news-fr";
    private LecteurFluxAsync lecteurFluxAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("source")){
                mSource = intent.getStringExtra("source");
            }
        }
        lecteurFluxAsync = new LecteurFluxAsync(MainActivity.this, mUrl_str, mSource);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.source_menu, menu);
        return true;
    }

}
