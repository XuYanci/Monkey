package com.example.yanci.monkey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.yanci.monkey.R;

public class LoginActivity extends KBaseActivity {

    private static String TAG = "TAG_LOGINACTIVITY";
    private static String clientId = "1b1f2fb299778668210e";
    private static String authLogin = "https://github.com/login/oauth/authorize";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: 清理缓存

        // 加载GitHub界面
        webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl(authLogin + "?client_id=" + clientId);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Here put your code
                Log.d(TAG, url);
                if (url.contains("xuyanci.github.io")) {
                    // 解析URL中的code,并返回到上一个界面
                    String code = url.substring(url.indexOf("code=") + 5);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.putExtra("code",code);
                    setResult(1,intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }



}
