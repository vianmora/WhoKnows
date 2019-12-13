package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.whoknows2.controllers.LecteurFluxAsync;
import com.example.whoknows2.models.Source;

public class MainActivity extends AppCompatActivity {

    /* variables */
    private String mUrl_str = "http://newsapi.org/v2/";
    private Source mSource;
    private LecteurFluxAsync lecteurFluxAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        mSource = new Source("google-news-fr", "Google News (France)");

        if (intent != null){
            if (intent.hasExtra("source")){
                mSource = intent.getParcelableExtra("source");
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
