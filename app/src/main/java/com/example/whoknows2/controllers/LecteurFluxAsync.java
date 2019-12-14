package com.example.whoknows2.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.whoknows2.ArticleActivity;
import com.example.whoknows2.FluxActivity;
import com.example.whoknows2.R;
import com.example.whoknows2.adapters.ArticleAdapter;
import com.example.whoknows2.models.CatalogueSources;
import com.example.whoknows2.models.FluxArticles;
import com.example.whoknows2.models.Source;
import com.example.whoknows2.utils.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.os.FileUtils.copy;

public class LecteurFluxAsync{



    /* Views */
    private ListView mFluxListView;
    private TextView mSourceTextView;
    private ImageView mLogoImageView;
    private Spinner spinner;

    /* variable */

    private AppCompatActivity mActivity;
    private RequestQueue mRequestQueue;
    private FluxArticles mCurrentFlux;
    private int mPage = 1;
    private CatalogueSources mCatalogue;

    private Toast toast;
    private boolean load_bool;
    private String tag = "TAG PERSO";
    private String mUrlFlux_str;
    private String mUrlSource_str;
    private Source mCurrentSource;


    /* constructeur */

    public LecteurFluxAsync(AppCompatActivity activity, String url, Source current_source){
        mActivity = activity;
        mCurrentFlux = new FluxArticles();

        mUrlFlux_str = url+"everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
        mUrlSource_str = url + "sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";

        mCurrentSource = current_source;

        mRequestQueue = Singleton.getInstance(mActivity.getApplicationContext()).getRequestQueue();
        mActivity.setContentView(R.layout.activity_main);

        Log.d(tag, "1_ source : "+current_source.getName());
        lireFlux();
    }


    public void lireFlux(){
        JsonObjectRequest jr = new JsonObjectRequest(
                Request.Method.GET,
                mUrlFlux_str+"&sources="+mCurrentSource.getId(),
                null,
                new Response.Listener<JSONObject>(){

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onResponse(JSONObject response) {

                        Log.d(tag, "2_ JSON chargé");

                        set_flux(response);

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

    private void set_flux(JSONObject response){

        mActivity.setContentView(R.layout.activity_flux);
        load_menu();

        mFluxListView = (ListView) mActivity.findViewById(R.id.activity_flux_listview);
        mSourceTextView = (TextView) mActivity.findViewById(R.id.activity_flux_source);
        mLogoImageView = (ImageView) mActivity.findViewById(R.id.activity_flux_logo);

        try {
            mCurrentFlux.fillWithJSONObject(response, null);
            Log.d("TAG", "premiere requete");
            load_bool = false;

            final ArticleAdapter mArticleAdapter = new ArticleAdapter(mActivity.getApplicationContext(), mCurrentFlux.get_articlesArray(mPage-1));

            mFluxListView.setAdapter(mArticleAdapter);

            mArticleAdapter.notifyDataSetChanged();

            mFluxListView.setOnScrollListener(new AbsListView.OnScrollListener() {

                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem + visibleItemCount > totalItemCount-1 && !load_bool){

                        if (totalItemCount >= 100){
                            toast = Toast.makeText(mActivity.getApplicationContext(), "vous avez atteint la limite des 100 articles disponibles", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else if (totalItemCount == mCurrentFlux.get_totalResult()){
                            toast = Toast.makeText(mActivity.getApplicationContext(), "vous avez atteint la fin du fil", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            Log.d("TAG", firstVisibleItem+"&"+visibleItemCount+"&"+totalItemCount+"&"+mCurrentFlux.get_totalResult());
                            load_bool = true;
                            mPage++;

                            JsonObjectRequest new_jr = new JsonObjectRequest(
                                    Request.Method.GET,
                                    mUrlFlux_str+"&sources="+mCurrentSource.getId()+"&page="+(mPage),
                                    null,
                                    new Response.Listener<JSONObject>() {

                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        public void onResponse(JSONObject response) {

                                            load_bool = false;

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
                }
            });

            mSourceTextView.setText(mCurrentFlux.get_source().getName());

            mFluxListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent toArticle = new Intent(mActivity.getApplicationContext(), ArticleActivity.class);
                    toArticle.putExtra("id_article", position);
                    Log.d(tag,"title : " + FluxArticles.getArticleById(position).getTitle() + "   description : " + FluxArticles.getArticleById(position).getDescription());
                    mActivity.startActivity(toArticle);
                }
            });

        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    private void load_menu(){

        if (CatalogueSources.getCatalogue_str()==null){

            mCatalogue = new CatalogueSources();

            JsonObjectRequest jr = new JsonObjectRequest(
                    Request.Method.GET,
                    mUrlSource_str,
                    null,
                    new Response.Listener<JSONObject>() {

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        public void onResponse(JSONObject response) {
                            try {
                                mCatalogue.fillWithJSONObject(response);
                                set_menu();
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            Singleton.getInstance(mActivity.getApplicationContext()).addToRequestQueue(jr);
        }
        else{
            set_menu();
        }
    }

    private void set_menu(){

        spinner = (Spinner) mActivity.findViewById(R.id.activity_flux_spinner);

        ArrayList<String> sources = CatalogueSources.getCatalogue_str();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.item_spinner, sources);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("sources")){
                    Intent change_source = new Intent(mActivity.getApplicationContext(), FluxActivity.class);
                    change_source.putExtra("source", (Parcelable) CatalogueSources.getCatalogue().get(position-1));
                    mActivity.startActivity(change_source);
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /* getters & setters */

    private AppCompatActivity getActivity() {
        return mActivity;
    }
}
