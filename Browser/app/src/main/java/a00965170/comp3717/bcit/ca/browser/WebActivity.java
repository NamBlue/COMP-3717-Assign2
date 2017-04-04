package a00965170.comp3717.bcit.ca.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

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
        webview.loadUrl(url);
        setContentView(webview);
    }
}
