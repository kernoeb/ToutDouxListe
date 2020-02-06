package com.dutinfo.boisnard.tp12;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

/**
 * WebViewClient
 */
public class MyWebViewClient extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        // Back arrow in top bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        WebSettings params = webView.getSettings();
        params.setJavaScriptEnabled(true);
        params.setBuiltInZoomControls(true);

        // Get the url
        Bundle bundle = getIntent().getExtras();
        String url = Objects.requireNonNull(bundle).getString("url");
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
