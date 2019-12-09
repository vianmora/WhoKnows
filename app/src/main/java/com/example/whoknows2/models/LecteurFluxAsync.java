package com.example.whoknows2.models;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.example.whoknows2.R;
import com.example.whoknows2.utils.Singleton;

import org.json.JSONArray;

import static android.os.FileUtils.copy;

public class LecteurFluxAsync extends AsyncTask<String, Void, JSONArray> {

    /* variable */

    private AppCompatActivity mActivity;
    private RequestQueue mRequestQueue;
    JSONArray mJSONArray;

    /* constructeur */

    public LecteurFluxAsync(AppCompatActivity activity){
        mActivity = activity;
        mRequestQueue = Singleton.getInstance(mActivity.getApplicationContext()).getRequestQueue();
    }

    protected JSONArray doInBackground(final String... params) {

       mJSONArray = null;
       return mJSONArray; // returns the result
    }

    protected void onPreExecute() {
        super.onPreExecute();
        mActivity.setContentView(R.layout.activity_main);
    }

    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(JSONArray articlesJSON) {
        mActivity.setContentView(R.layout.activity_flux);
    }
}
