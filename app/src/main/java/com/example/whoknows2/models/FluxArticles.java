package com.example.whoknows2.models;

import android.util.Log;

import com.example.whoknows2.adapters.ArticleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class FluxArticles {

    private String _status;
    private static int _totalResult;
    private Source _source;
    private static ArrayList<ArrayList<Article>> _articlesArray;

    public FluxArticles() {
        this._status = null;
        _totalResult = 0;
        _articlesArray = new ArrayList<>();
        this._source = null;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public static int get_totalResult() {
        return _totalResult;
    }

    public void set_totalResult(int _totalResult) {
        this._totalResult = _totalResult;
    }

    public static ArrayList<Article> get_articlesArray(int page) {
        return _articlesArray.get(page);
    }

    public static Article getArticleById(int id){
        int page = (int) id/20;
        int sousId = id - 20*page;
        return _articlesArray.get(page).get(sousId);
    }

    public Source get_source() {
        return _source;
    }

    public void set_source(Source _source) {
        this._source = _source;
    }

    public void fillArticlesArrayWithJSONArray(JSONArray jsonArray) throws JSONException {

        int _idArticle = _articlesArray.size()*20;

        ArrayList<Article> articleArrayList = new ArrayList<>();

        JSONObject obj = new JSONObject(jsonArray.getString(0));

        if (get_source()==null){
            this.set_source(new Source(obj.getJSONObject("source").getString("id"), obj.getJSONObject("source").getString("name")));
        }

        for (int i = 0; i < jsonArray.length(); i++) {

            obj = new JSONObject(jsonArray.getString(i));

            String date_str = null;
            try {
                SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.FRANCE);
                Date date = oldFormat.parse(obj.getString("publishedAt"));

                SimpleDateFormat newFormat = new SimpleDateFormat("EEE dd MMMM",Locale.FRANCE);
                date_str = newFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Article article = new Article();

            article.setSource(new Source(obj.getJSONObject("source").getString("id"), obj.getJSONObject("source").getString("name")));
            article.setAuthor(obj.getString("author"));
            article.setTitle(obj.getString("title"));
            article.setDescription(obj.getString("description"));
            article.setUrl(obj.getString("url"));
            article.setUrlToImage(obj.getString("urlToImage"));
            article.setPublishedAt(date_str);
            article.setContent(obj.getString("content"));
            article.setId(_idArticle);

            // On ajoute l'article au flux
            articleArrayList.add(article);
            _idArticle++;

        }
        _articlesArray.add(articleArrayList);
    }

    public void fillWithJSONObject(JSONObject object, ArticleAdapter adapter) throws JSONException {
        set_status(object.getString("status"));
        set_totalResult(object.getInt("totalResults"));

        fillArticlesArrayWithJSONArray(object.getJSONArray("articles"));

    }
}
