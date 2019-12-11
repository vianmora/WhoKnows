package com.example.whoknows2.models;

import com.example.whoknows2.adapters.ArticleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CatalogueSources {

    private String mStatus;
    private ArrayList<Source> mCatalogue;

    public CatalogueSources() {
        mStatus = null;
        mCatalogue = new ArrayList<>();
    }

    public void fillArticlesArrayWhithJSONArray(JSONArray jsonArray) throws JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = new JSONObject(jsonArray.getString(i));

            Source source = new Source();

            source.setId(obj.getString("id"));
            source.setName(obj.getString("name"));
            source.setDescription(obj.getString("description"));
            source.setUrl(obj.getString("url"));
            source.setCategory(obj.getString("category"));
            source.setLanguage(obj.getString("language"));
            source.setCountry(obj.getString("country"));

            // On ajoute l'article au flux
            mCatalogue.add(source);

        }
    }

    public void fillWithJSONObject(JSONObject object) throws JSONException {
        setStatus(object.getString("status"));

        fillArticlesArrayWhithJSONArray(object.getJSONArray("articles"));

    }

    /* setters & getters*/

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public ArrayList<Source> getCatalogue() {
        return mCatalogue;
    }
}
