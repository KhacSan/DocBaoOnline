package vn.edu.hust.soict.khacsan.docbaoonline.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import vn.edu.hust.soict.khacsan.docbaoonline.R;


public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    String URL = "https://vnexpress.net/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progressbar);

        Intent intent = getIntent();
        if(intent !=null){
            URL = intent.getStringExtra("URL");
        }
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(URL);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
    }

    public WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            setTitle("Loading...");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            setTitle(view.getTitle());
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
