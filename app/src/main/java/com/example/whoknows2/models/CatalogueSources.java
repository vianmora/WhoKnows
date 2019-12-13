package com.example.whoknows2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Signature;
import java.util.ArrayList;

public class CatalogueSources {

    private String mStatus;
    private ArrayList<Source> mCatalogue;
    private ArrayList<String> mCatalogue_str;

    public CatalogueSources() {
        mStatus = null;
        mCatalogue = new ArrayList<>();
        mCatalogue_str = new ArrayList<>();
        mCatalogue_str.add("sources");
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

            mCatalogue_str.add(obj.getString("name"));
        }
    }

    public void fillWithJSONObject(JSONObject object) throws JSONException {

        setStatus(object.getString("status"));
        fillArticlesArrayWhithJSONArray(object.getJSONArray("sources"));

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

    public ArrayList<String> getCatalogue_str(){
        return mCatalogue_str;
    }

}
