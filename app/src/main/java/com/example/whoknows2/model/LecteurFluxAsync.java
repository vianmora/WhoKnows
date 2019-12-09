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
    private String mArticles = "Articles.JSON";
    private JSONArray mJSONArray;
    private File mArticlesFile;

    private RequestQueue mRequestQueue;

    /* constructeur */

    public LecteurFluxAsync(AppCompatActivity activity){
        mActivity = activity;
        mJSONArray = null;
        mRequestQueue = getInstance(mActivity.getApplicationContext()).getRequestQueue();
    }

    protected JSONArray doInBackground(final String... params) {

        String mUrlFlux_str = params[0];

        JsonObjectRequest jr = new JsonObjectRequest(
                Request.Method.GET,
                mUrlFlux_str,
                null,
                new Response.Listener<JSONObject>(){

                    public void onResponse(JSONObject response) {


                        TextView JSONString = (TextView) mActivity.findViewById(R.id.main_JSONtxt);

                        JSONString.setText("Response: " + response.toString());


                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d("probleme de réponse", error.getMessage());
                        error.getStackTrace();
                    }
                });

        getInstance(mActivity.getApplicationContext()).addToRequestQueue(jr);

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
        /*mActivity.setContentView(R.layout.activity_main);
        TextView mTextinit = (TextView) mActivity.findViewById(R.id.main_JSONtxt);
        String str = "hey";
        try{
            str = articlesJSON.toString();
        }catch (Exception e){
            e.getStackTrace();
        }
        mTextinit.setText(str);*/
    }

    /*private String readStream(InputStream is) throws IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        int i = 0;
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
            Log.i("ça avance", "ligne n°"+i);
        }
        is.close();

        //Log.i("CIO", jsonextracted);
        Log.d("readstream", "heyeheyeheyeheyeheyeh");
        return sb.toString();

    }*/

}
