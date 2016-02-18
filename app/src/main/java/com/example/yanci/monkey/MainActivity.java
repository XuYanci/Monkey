package com.example.yanci.monkey;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        radioGroup = (RadioGroup)findViewById(R.id.tab_menu);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rbUsers:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragmentUsers).commit();
                        break;

                    case R.id.rbRepo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragmentRepo).commit();
                        break;

                    case R.id.rbDisc:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragmentDisc).commit();
                        break;

                    case R.id.rbMore:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragmentMore).commit();
                        break;
                    default:break;
                }
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
