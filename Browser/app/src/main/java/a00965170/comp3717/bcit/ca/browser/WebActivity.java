package a00965170.comp3717.bcit.ca.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by namblue on 4/3/2017.
 */

public class WebActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final Intent intent;
        final String url;
        intent = getIntent();
        url = intent.getStringExtra("url");
        WebView webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Intent intent = new Intent();
                intent.putExtra("error", "Code: 404 - Server not found." + " Url: " + failingUrl);
                setResult(1, intent);
                finish();
            }
        });
        webview.loadUrl(url);
        setContentView(webview);
    }
}
