package com.example.whoknows2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.example.whoknows2.controllers.MyWebViewClient;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();

        if (intent != null){
            if (intent.hasExtra("url")){
                String mUrlArticle = intent.getStringExtra("url");

                WebView webView =(WebView)findViewById(R.id.activity_web_webview);

                webView.setWebViewClient(new MyWebViewClient(this));

                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadUrl(mUrlArticle);
            }
        }
    }
}
