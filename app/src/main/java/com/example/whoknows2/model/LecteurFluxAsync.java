package com.example.whoknows2.model;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.whoknows2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.os.FileUtils.copy;
import static com.example.whoknows2.model.Singleton.getInstance;

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
