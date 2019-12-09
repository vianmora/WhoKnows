package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.example.whoknows2.model.LecteurFluxAsync;
import com.example.whoknows2.model.Singleton;

import static com.example.whoknows2.model.Singleton.getInstance;

public class MainActivity extends AppCompatActivity {

    /* Views */

    private TextView JSONString;

    /* variables */
    private LecteurFluxAsync mLecteurFlux;

    private RequestQueue mRequestQueue;

    private String mArticles = "Articles.JSON";
    private File mArticlesFile;
    private String affiche_text = "hey";
    private String mUrlSources_str = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
    private String mUrlFlux_str = "http://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr&sources=google-news-fr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*LecteurFluxAsync mLecteurFluxAsync = new LecteurFluxAsync(this);
        mLecteurFluxAsync.execute(mUrlFlux_str, "articles");
*/
        mRequestQueue = getInstance(this.getApplicationContext()).getRequestQueue();

        JsonObjectRequest jr = new JsonObjectRequest(
                Request.Method.GET,
                mUrlFlux_str,
                null,
                new Response.Listener<JSONObject>(){

                    public void onResponse(JSONObject response) {

                        ArrayList<Article> flux = new ArrayList<>();
                        JSONArray articlesJSONArray = null;


                        try {
                            articlesJSONArray = new JSONArray(response.getString("articles"));

                            for (int i = 0; i < articlesJSONArray.length(); i++) {

                                JSONObject obj = new JSONObject(articlesJSONArray.getString(i));
                                // On fait le lien Article - Objet JSON
                                Article article = new Article();

                                article.setId(obj.getString("id"));
                                article.setName(obj.getString("name"));
                                //article.setAuthor(obj.getString("author"));
                                article.setTitle(obj.getString("title"));
                                article.setDescription(obj.getString("descrition"));
                                article.setUrl(obj.getString("url"));
                                //article.setUrlToImage(obj.getString("urlToImage"));
                                article.setPublishedAt(obj.getString("publishedAt"));
                                //article.setContent(obj.getString("content"));

                                // On ajoute l'article au flux
                                flux.add(article);
                            }

                            affiche_text = "Response: " + flux.get(0).getAuthor();



                        } catch (JSONException e) {
                            e.printStackTrace();

                            affiche_text = e.getMessage();
                        }




                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d("probleme de réponse", error.getMessage());
                        error.getStackTrace();

                        affiche_text = "Response: " + error.getMessage();
                    }
                });

        getInstance(this).addToRequestQueue(jr);

        setContentView(R.layout.activity_flux);
        JSONString = (TextView) findViewById(R.id.main_JSONtxt);
        JSONString.setText(affiche_text);

    }

}
