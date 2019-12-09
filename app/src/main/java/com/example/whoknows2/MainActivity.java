package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.whoknows2.model.Article;
import com.example.whoknows2.model.FluxArticle;
import com.example.whoknows2.model.LecteurFluxAsync;
import com.example.whoknows2.model.Singleton;

import static com.example.whoknows2.model.Singleton.getInstance;

public class MainActivity extends AppCompatActivity {

    /* Views */

    private TextView JSONString;
    private ProgressBar mProgressBar;

    /* variables */
    private LecteurFluxAsync mLecteurFlux;

    private RequestQueue mRequestQueue;

    private String mArticles = "Articles.JSON";
    private File mArticlesFile;
    private FluxArticle mCurrentFlux;
    private String mUrlSources_str = "http://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
    private String mUrlFlux_str = "http://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr&sources=google-news-fr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentFlux = new FluxArticle();

        mRequestQueue = Singleton.getInstance(this.getApplicationContext()).getRequestQueue();

        JsonObjectRequest jr = new JsonObjectRequest(
                Request.Method.GET,
                mUrlFlux_str,
                null,
                new Response.Listener<JSONObject>(){

                    public void onResponse(JSONObject response) {

                        setContentView(R.layout.activity_flux);
                        JSONString = (TextView) findViewById(R.id.main_JSONtxt);

                        try {
                            mCurrentFlux.set_status(response.getString("status"));
                            mCurrentFlux.set_totalResult(response.getInt("totalResults"));

                            mCurrentFlux.fillArticlesArrayWhithJSONArray(response.getJSONArray("articles"));



                            JSONString.setText("reponse :" + mCurrentFlux.get_articlesArray().get(0).getTitle());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            JSONString.setText("reponse :" );
                        }


                        Log.d("MON TAG", "bien recu");
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d("probleme de réponse", error.getMessage());
                        error.getStackTrace();

                    }
                });

        Singleton.getInstance(this).addToRequestQueue(jr);
        Log.d("MonTag", "Tag2");

    }

}
