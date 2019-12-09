package com.example.whoknows2.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FluxArticle {

    private String _status;
    private int _totalResult;
    private ArrayList<Article> _articlesArray;

    public FluxArticle() {
        this._status = null;
        this._totalResult = 0;
        this._articlesArray = new ArrayList<>();
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public int get_totalResult() {
        return _totalResult;
    }

    public void set_totalResult(int _totalResult) {
        this._totalResult = _totalResult;
    }

    public ArrayList<Article> get_articlesArray() {
        return _articlesArray;
    }

    public void fillArticlesArrayWhithJSONArray(JSONArray jsonArray) throws JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = new JSONObject(jsonArray.getString(i));

            Article article = new Article();

            article.setSource(new Source(obj.getJSONObject("source").getString("id"), obj.getJSONObject("source").getString("name")));
            article.setAuthor(obj.getString("author"));
            article.setTitle(obj.getString("title"));
            article.setDescription(obj.getString("description"));
            article.setUrl(obj.getString("url"));
            article.setUrlToImage(obj.getString("urlToImage"));
            article.setPublishedAt(obj.getString("publishedAt"));
            article.setContent(obj.getString("content"));

            // On ajoute l'article au flux
            _articlesArray.add(article);

        }

    }

    public ArrayList<HashMap<String, String>> get_FluxListMap () {

        ArrayList<HashMap<String, String>> fluxListMap = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < _totalResult; i++) {


            HashMap<String, String> map = new HashMap<>();

            map.put("id", String.valueOf(i));
            map.put("title", _articlesArray.get(i).getTitle());
            map.put("description", _articlesArray.get(i).getDescription());
            fluxListMap.add(map);
        }

        return fluxListMap;
    }
}
