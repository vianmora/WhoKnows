package com.example.whoknows2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import com.example.whoknows2.adapters.ArticleAdapter;
import com.example.whoknows2.models.FluxArticle;
import com.example.whoknows2.models.LecteurFluxAsync;
import com.example.whoknows2.utils.Singleton;

public class MainActivity extends AppCompatActivity {

    /* Views */
    private ListView mFluxListView;
    private TextView mSourceTextView;


    /* variables */
    private RequestQueue mRequestQueue;

    private FluxArticle mCurrentFlux;
    private String mUrlSources_str = "http://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
    private String mUrlFlux_str = "http://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
    private String mSource = "google-news-fr";
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar();

        mCurrentFlux = new FluxArticle();



        mRequestQueue = Singleton.getInstance(this.getApplicationContext()).getRequestQueue();

        JsonObjectRequest jr = new JsonObjectRequest(
                Request.Method.GET,
                mUrlFlux_str+"&sources="+mSource,
                null,
                new Response.Listener<JSONObject>(){

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onResponse(JSONObject response) {

                        setContentView(R.layout.activity_flux);

                        mFluxListView = (ListView) findViewById(R.id.activity_flux_listview);
                        mSourceTextView = (TextView) findViewById(R.id.activity_flux_source);

                        try {
                            mCurrentFlux.fillWithJSONObject(response, null);
                            Log.d("TAG", "premiere requete");

                            final ArticleAdapter mArticleAdapter = new ArticleAdapter(getApplicationContext(), mCurrentFlux.get_articlesArray(mPage-1));

                            mFluxListView.setAdapter(mArticleAdapter);

                            mArticleAdapter.notifyDataSetChanged();


                            mFluxListView.setOnScrollListener(new AbsListView.OnScrollListener() {

                                public void onScrollStateChanged(AbsListView view, int scrollState) {

                                }

                                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                    if (firstVisibleItem + visibleItemCount > totalItemCount-1 && totalItemCount == mPage*20 && totalItemCount<100){

                                        Log.d("TAG", firstVisibleItem+"&"+visibleItemCount+"&"+totalItemCount);
                                        mPage++;

                                        JsonObjectRequest new_jr = new JsonObjectRequest(
                                                Request.Method.GET,
                                                mUrlFlux_str+"&sources="+mSource+"&page="+(mPage),
                                                null,
                                                new Response.Listener<JSONObject>() {

                                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                                    public void onResponse(JSONObject response) {

                                                        try {
                                                            mCurrentFlux.fillWithJSONObject(response, null);
                                                            mArticleAdapter.addAll(mCurrentFlux.get_articlesArray(mPage-1));


                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    public void onErrorResponse(VolleyError error) {

                                                        new AlertDialog.Builder(MainActivity.this)
                                                                .setTitle("Connection lente")
                                                                .setMessage("Nous sommes désolé :( nous avons du mal à nous connecter au réseau...")

                                                                .setPositiveButton("réessayer ?", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        Toast mToast = Toast.makeText(getApplicationContext(), "problème de connection...", Toast.LENGTH_LONG);

                                                                        recreate();
                                                                    }
                                                                })
                                                                .show();
                                                        error.getStackTrace();
                                                    }
                                                });

                                        Singleton.getInstance(getApplicationContext()).addToRequestQueue(new_jr);
                                    }
                                }
                            });

                            mSourceTextView.setText(mCurrentFlux.get_source().getName());

                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }


                        Log.d("MON TAG", "bien recu");

                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Connection lente")
                                .setMessage("Nous sommes désolé, nous avons du mal à nous connecter au réseau...")

                                .setPositiveButton("réessayer ?", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast mToast = Toast.makeText(getApplicationContext(), "problème de connection...", Toast.LENGTH_LONG);

                                        //mToast.show();
                                        recreate();
                                    }
                                })
                                .show();
                    }
                });

        Singleton.getInstance(this).addToRequestQueue(jr);
    }

}
