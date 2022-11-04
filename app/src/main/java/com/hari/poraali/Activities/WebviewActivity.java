package com.hari.poraali.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hari.poraali.R;

public class WebviewActivity extends AppCompatActivity {

    WebView web;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String url = getIntent ().getStringExtra ("blogURL");
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_webview);
//        getSupportActionBar ().hide ();

        web = findViewById (R.id.webview);
        progressBar = findViewById (R.id.progress_id);


        web.loadUrl (url);


        WebSettings webSettings = web.getSettings ();
        webSettings.setJavaScriptEnabled (true);
        WebViewClient webViewClient = new WebViewClient (){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility (View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility (View.GONE);
            }
        };

        web.setWebViewClient (webViewClient);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)&& web.canGoBack ()){
            web.goBack ();
            return true;
        }
        return super.onKeyDown (keyCode,event);
    }


}