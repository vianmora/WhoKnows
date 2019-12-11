package com.example.whoknows2.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.whoknows2.MainActivity;
import com.example.whoknows2.R;
import com.example.whoknows2.adapters.ArticleAdapter;
import com.example.whoknows2.models.FluxArticle;
import com.example.whoknows2.utils.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.os.FileUtils.copy;

public class LecteurFluxAsync{

    /* Views */
    private ListView mFluxListView;
    private TextView mSourceTextView;
    private ImageView mLogoImageView;

    /* variable */

    private AppCompatActivity mActivity;
    private RequestQueue mRequestQueue;
    private String mUrlFlux_str;
    private FluxArticle mCurrentFlux;
    private int mPage = 1;


    /* constructeur */

    public LecteurFluxAsync(AppCompatActivity activity, String urlFlux_str, String source_str){
        mActivity = activity;
        mUrlFlux_str = urlFlux_str;
        mCurrentFlux = new FluxArticle();
        mRequestQueue = Singleton.getInstance(mActivity.getApplicationContext()).getRequestQueue();
        mActivity.setContentView(R.layout.activity_main);

        lireFlux(source_str);
    }

    private void lireFlux(final String source_str){
        JsonObjectRequest jr = new JsonObjectRequest(
                Request.Method.GET,
                mUrlFlux_str+"&sources="+source_str,
                null,
                new Response.Listener<JSONObject>(){

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onResponse(JSONObject response) {

                        mActivity.setContentView(R.layout.activity_flux);

                        mFluxListView = (ListView) mActivity.findViewById(R.id.activity_flux_listview);
                        mSourceTextView = (TextView) mActivity.findViewById(R.id.activity_flux_source);
                        mLogoImageView = (ImageView) mActivity.findViewById(R.id.activity_flux_logo);

                        try {
                            mCurrentFlux.fillWithJSONObject(response, null);
                            Log.d("TAG", "premiere requete");

                            final ArticleAdapter mArticleAdapter = new ArticleAdapter(mActivity.getApplicationContext(), mCurrentFlux.get_articlesArray(mPage-1));

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
                                                mUrlFlux_str+"&sources="+source_str+"&page="+(mPage),
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

                                                        new AlertDialog.Builder(mActivity)
                                                                .setTitle("Connection lente")
                                                                .setMessage("Nous sommes désolé :( nous avons du mal à nous connecter au réseau...")

                                                                .setPositiveButton("réessayer ?", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        Toast mToast = Toast.makeText(mActivity.getApplicationContext(), "problème de connection...", Toast.LENGTH_LONG);

                                                                        mActivity.recreate();
                                                                    }
                                                                })
                                                                .show();
                                                        error.getStackTrace();
                                                    }
                                                });

                                        Singleton.getInstance(mActivity.getApplicationContext()).addToRequestQueue(new_jr);
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

                        new AlertDialog.Builder(mActivity)
                                .setTitle("Connection lente")
                                .setMessage("Nous sommes désolé, nous avons du mal à nous connecter au réseau...")

                                .setPositiveButton("réessayer ?", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast mToast = Toast.makeText(mActivity.getApplicationContext(), "problème de connection...", Toast.LENGTH_LONG);

                                        //mToast.show();
                                        mActivity.recreate();
                                    }
                                })
                                .show();
                        Toast mToast = Toast.makeText(mActivity.getApplicationContext(), "problème de connection...", Toast.LENGTH_LONG);

                        mToast.show();
                    }
                });

        Singleton.getInstance(mActivity.getApplicationContext()).addToRequestQueue(jr);
    }

    /* getters & setters */

    private AppCompatActivity getActivity() {
        return mActivity;
    }
}
