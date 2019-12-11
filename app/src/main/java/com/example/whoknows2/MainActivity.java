package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LecteurFluxAsync lecteurFluxAsync = new LecteurFluxAsync(MainActivity.this, mUrlFlux_str, mSource);

    }

}
