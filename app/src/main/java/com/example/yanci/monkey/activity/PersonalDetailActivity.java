package com.example.yanci.monkey.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yanci.core.AppActionCallBackListener;
import com.example.yanci.core.AsynImageLoader;
import com.example.yanci.model.PersonalDetailResp;
import com.example.yanci.monkey.R;

import org.w3c.dom.Text;

public class PersonalDetailActivity extends KBaseActivity {

    private final String TAG = "TAG_PERSONALDETAIL";

    private ImageView iv_headicon;
    private TextView tv_username;
    private TextView tv_nickname;
    private TextView tv_createtime;
    private TextView tv_company;
    private TextView tv_email;
    private TextView tv_blog;
    private TextView tv_address;

    private LinearLayout ll_repo;
    private LinearLayout ll_follow;
    private LinearLayout ll_follower;

    private TextView tv_repocount;
    private TextView tv_repostr;

    private TextView tv_followcount;
    private TextView tv_followstr;

    private TextView tv_followercount;
    private TextView tv_followerstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);

        // Init UI
        iv_headicon = (ImageView)findViewById(R.id.iv_headicon);
        tv_username = (TextView)findViewById(R.id.tv_username);
        tv_nickname = (TextView)findViewById(R.id.tv_nickname);
        tv_createtime = (TextView)findViewById(R.id.tv_createtime);
        tv_company = (TextView)findViewById(R.id.tv_company);
        tv_email = (TextView)findViewById(R.id.tv_email);
        tv_blog = (TextView)findViewById(R.id.tv_blog);
        tv_address = (TextView)findViewById(R.id.tv_address);

        tv_repostr = (TextView)findViewById(R.id.tv_repostr);
        tv_repocount = (TextView)findViewById(R.id.tv_repocount);

        tv_followcount = (TextView)findViewById(R.id.tv_followcount);
        tv_followstr = (TextView)findViewById(R.id.tv_followstr);

        tv_followercount = (TextView)findViewById(R.id.tv_followercount);
        tv_followerstr = (TextView)findViewById(R.id.tv_followerstr);

        ll_repo = (LinearLayout)findViewById(R.id.ll_repo);
        ll_follow = (LinearLayout)findViewById(R.id.ll_follow);
        ll_follower = (LinearLayout)findViewById(R.id.ll_follower);



        // Add UI Event
        ll_repo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        String accesstoken = this.getIntent().getStringExtra("accesstoken");
        String username = this.getIntent().getStringExtra("username");

        if (!accesstoken.isEmpty()) {
            this.appAction.getPersonalDetailByAccessToken(accesstoken,new PersonalDetailFetchListener());
        }
        else if(!username.isEmpty()) {
            this.appAction.getPersonalDetailByUserName(username,new PersonalDetailFetchListener());
        }
    }

    class PersonalDetailFetchListener implements AppActionCallBackListener<PersonalDetailResp> {
        @Override
        public void onSuccess(PersonalDetailResp data) {
            Log.i(TAG, "onSuccess: " + data);
            // TODO: 更新UI
            new AsynImageLoader().loadImage(data.getAvatar_url(), new AsynImageLoader.ImageLoadInterface() {
                @Override
                public void loadImageCallback(final Drawable drawable) {
                    iv_headicon.post(new Runnable() {
                        @Override
                        public void run() {
                            iv_headicon.setImageDrawable(drawable);
                        }
                    });
                }
            });

            tv_username.setText(data.getLogin());
            tv_nickname.setText(data.getName());
            tv_createtime.setText(data.getCreated_at());
            tv_company.setText(data.getCompany());
            tv_email.setText(data.getEmail());
            tv_blog.setText(data.getBlog());
            tv_address.setText(data.getLocation());

            tv_repocount.setText(data.getPublic_repos());
            tv_repostr.setText("Repositories");

            tv_followcount.setText(data.getFollowing());
            tv_followstr.setText("Following");

            tv_followercount.setText(data.getFollowers());
            tv_followerstr.setText("Follower");
        }

        @Override
        public void onFailure(String errorEvent, String message) {

        }
    }
}

