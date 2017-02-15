package com.blm.comparepoint.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;

import com.blm.comparepoint.BaseActivity;
import com.blm.comparepoint.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebView extends BaseActivity {

    @BindView(R.id.webview)
    android.webkit.WebView webview;

    private String url,title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        url=getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        webview.loadUrl(url);
    }

    private void initView() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(false);
        webview.setBackgroundColor(0);
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(android.webkit.WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
