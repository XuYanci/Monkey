package com.example.yanci.monkey;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.example.yanci.monkey.Fragment.FragmentDisc;
import com.example.yanci.monkey.Fragment.FragmentMore;
import com.example.yanci.monkey.Fragment.FragmentRepo;
import com.example.yanci.monkey.Fragment.FragmentUsers;
import com.example.yanci.monkey.activity.KBaseActivity;

public class MainActivity extends KBaseActivity implements FragmentDisc.OnFragmentInteractionListener,
                                            FragmentMore.OnFragmentInteractionListener,
                                            FragmentRepo.OnFragmentInteractionListener,
                                            FragmentUsers.OnFragmentInteractionListener {



    private RadioGroup radioGroup;
    private FragmentDisc fragmentDisc;      /* Discover 模块 */
    private FragmentRepo fragmentRepo;      /* Repository 模块 */
    private FragmentMore fragmentMore;      /* More 模块 */
    private FragmentUsers fragmentUsers;    /* Users 模块 */

    private static String TAG_FRAGDISC = "0";
    private static String TAG_FRAGREPO = "1";
    private static String TAG_FRAGMORE = "2";
    private static String TAG_FRAGUSERS = "3";
    private String curFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Implement TabBar
        fragmentDisc = new FragmentDisc();
        fragmentRepo = new FragmentRepo();
        fragmentMore = new FragmentMore();
        fragmentUsers = new FragmentUsers();


        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragmentUsers).commit();

        curFragmentTag = TAG_FRAGUSERS;

        radioGroup = (RadioGroup)findViewById(R.id.tab_menu);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rbUsers:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragmentUsers,"0").commit();
                        curFragmentTag = TAG_FRAGUSERS;
                        break;

                    case R.id.rbRepo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragmentRepo,"1").commit();
                        curFragmentTag = TAG_FRAGREPO;
                        break;

                    case R.id.rbDisc:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragmentDisc,"2").commit();
                        curFragmentTag = TAG_FRAGDISC;
                        break;

                    case R.id.rbMore:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragmentMore,"3").commit();
                        curFragmentTag = TAG_FRAGMORE;
                        break;
                    default:break;
                }
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
