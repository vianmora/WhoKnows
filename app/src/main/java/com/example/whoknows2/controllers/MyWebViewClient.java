package com.example.whoknows2.controllers;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whoknows2.R;


public class MyWebViewClient extends WebViewClient {

    private AppCompatActivity mActivity;
    private ProgressBar mProgressBar;

    public MyWebViewClient( AppCompatActivity activity) {
        mActivity = activity;
        mProgressBar = (ProgressBar) mActivity.findViewById(R.id.loading_progressBar);
    }

    // When you click on any interlink on webview.
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("MyLog","Click on any interlink on webview that time you got url :-" + url);
        return super.shouldOverrideUrlLoading(view, url);
    }

    // When page loading
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mProgressBar.setVisibility(View.VISIBLE);
        super.onPageStarted(view, url, favicon);
        Log.i("MyLog", "Your current url when webpage loading.." + url);
    }

    // When page load finish.
    @Override
    public void onPageFinished(WebView view, String url) {
        mProgressBar.setVisibility(View.INVISIBLE);
        Log.i("MyLog", "Your current url when webpage loading.. finish" + url);
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

}
