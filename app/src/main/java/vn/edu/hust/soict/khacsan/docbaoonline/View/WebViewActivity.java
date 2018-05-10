package vn.edu.hust.soict.khacsan.docbaoonline.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import vn.edu.hust.soict.khacsan.docbaoonline.R;


public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private String URL;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);

        refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setRefreshing(true);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.web_view);
        Intent intent = getIntent();
        if(intent !=null){
            URL = intent.getStringExtra("URL");
            webView.setWebViewClient(webViewClient);
            webView.loadUrl(URL);
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    webView.reload();
                }
            });
        }
    }

    public WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            toolbar.setTitle("Loading...");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            toolbar.setTitle(view.getTitle());
            refreshLayout.setRefreshing(false);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuwebview,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(webView.canGoBack()){
                    webView.goBack();
                }else {
                    finish();
                }
                return true;
            case R.id.action_reload:
                webView.reload();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
