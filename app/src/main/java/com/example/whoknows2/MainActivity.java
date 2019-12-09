package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;
import java.util.List;

import com.example.whoknows2.model.Article;
import com.example.whoknows2.model.FluxArticle;
import com.example.whoknows2.model.LecteurFluxAsync;
import com.example.whoknows2.model.Singleton;

import static com.example.whoknows2.model.Singleton.getInstance;

public class MainActivity extends AppCompatActivity {

    /* Views */

    private TextView JSONString;
    private ListView mFluxListView;


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
                        mFluxListView = (ListView) findViewById(R.id.main_articles_listview);

                        try {
                            mCurrentFlux.set_status(response.getString("status"));
                            mCurrentFlux.set_totalResult(response.getInt("totalResults"));

                            Log.d("MON TAG", "avant list");
                            mCurrentFlux.fillArticlesArrayWhithJSONArray(response.getJSONArray("articles"));
                            Log.d("MON TAG", "après list");

                            ListAdapter adapter = new SimpleAdapter(
                                    getApplicationContext(),
                                    mCurrentFlux.get_FluxListMap(),
                                    R.layout.activity_flux,
                                    new String[] { "title", "description" },
                                    new int[] { R.id.activity_flux_title_txt, R.id.activity_flux_descriptionItem_txt });

                            mFluxListView.setAdapter(adapter);


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
