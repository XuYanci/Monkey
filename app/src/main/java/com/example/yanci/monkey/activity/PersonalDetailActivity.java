package com.example.yanci.monkey.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.yanci.core.AppActionCallBackListener;
import com.example.yanci.model.PersonalDetailResp;
import com.example.yanci.monkey.R;

public class PersonalDetailActivity extends KBaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);

        webView = (WebView)findViewById(R.id.webView2);

        SharedPreferences preference_accesstoken = this.getSharedPreferences("user_accesstoken", 0);
        String accessToken = preference_accesstoken.getString("ACCESSTOKEN", "0");
        this.appAction.getPersonalDetailByAccessToken(accessToken, new AppActionCallBackListener<PersonalDetailResp>() {
            @Override
            public void onSuccess(final PersonalDetailResp data) {
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(data.getHtml_url());
                    }
                });
            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });
    }
}
