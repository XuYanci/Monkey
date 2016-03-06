package com.example.yanci.monkey.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yanci.core.AppActionCallBackListener;
import com.example.yanci.core.AppConfig;
import com.example.yanci.core.AsynImageLoader;
import com.example.yanci.model.AccessTokenResp;
import com.example.yanci.model.PersonalDetailResp;
import com.example.yanci.monkey.R;
import com.example.yanci.monkey.activity.KBaseActivity;
import com.example.yanci.monkey.activity.LoginActivity;
import com.example.yanci.monkey.activity.PersonalDetailActivity;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMore.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMore extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "TAG_FRAGMENTMORE";
    private static final int REQUEST_CODE = 1;

    private LinearLayout ll_login,ll_about,ll_feedback,ll_logout;
    private ImageView userImageView;
    private TextView userTextView;

    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentMore() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMore.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMore newInstance(String param1, String param2) {
        FragmentMore fragment = new FragmentMore();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
            UpdateUIWhenCreateView();
        }
        else {
            view = inflater.inflate(R.layout.fragment_fragment_more, container, false);
            InitUI();
            AddUIListener();
            UpdateUIWhenCreateView();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 获取用户登录返回的code
        String code = data.getStringExtra("code");
        // 获取用户AccessToken
        String client_id = AppConfig.client_id;
        String client_secret = AppConfig.client_secret;
                ((KBaseActivity) getActivity()).appAction.getAccessToken(client_id, client_secret, code, new AppActionCallBackListener<AccessTokenResp>() {
            @Override
            public void onSuccess(AccessTokenResp data) {
                // 保存用户AccessToken
                SharedPreferences preference_accesstoken = getActivity().getSharedPreferences("user_accesstoken",0);
                SharedPreferences.Editor editor = preference_accesstoken.edit();
                editor.putString("ACCESSTOKEN",data.getAccess_token());
                editor.putString("SCOPE",data.getScope());
                editor.putString("TOKENTYPE",data.getToken_type());
                editor.commit();
                UpdateUIWhenCreateView();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Log.i(TAG, "onFailure: " + errorEvent + message);
            }
        });
    }

    private void InitUI() {
        ll_login = (LinearLayout) view.findViewById(R.id.ll_login);
        ll_about = (LinearLayout) view.findViewById(R.id.ll_about);
        ll_feedback = (LinearLayout) view.findViewById(R.id.ll_feedback);
        ll_logout = (LinearLayout) view.findViewById(R.id.ll_logout);
        userImageView = (ImageView) view.findViewById(R.id.userImageView);
        userTextView = (TextView) view.findViewById(R.id.userTextView);
    }

    private void AddUIListener() {
        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: LOGIN");
                // 判断本地是否保存AccessToken,有则进入个人资料界面,否则则进入登陆界面
                SharedPreferences preference_accesstoken = getActivity().getSharedPreferences("user_accesstoken", 0);
                String accessToken = preference_accesstoken.getString("ACCESSTOKEN", "0");
                if (accessToken.equals("0")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    // 进入个人资料界面
                    Intent intent = new Intent(getActivity(), PersonalDetailActivity.class);
                    intent.putExtra("accesstoken",accessToken);
                    startActivity(intent);
                }
            }
        });

        ll_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ABOUT");
            }
        });

        ll_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: FEEDBACK");
            }
        });

        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preference = getActivity().getSharedPreferences("user_accesstoken",0);
                SharedPreferences.Editor editor = preference.edit();
                editor.clear();
                editor.commit();
                ll_logout.setVisibility(View.GONE);
                UpdateUIWhenCreateView();
            }
        });

    }

    private void UpdateUIWhenCreateView() {
        // 更新用户头像和用户名
        SharedPreferences preference_accesstoken = getActivity().getSharedPreferences("user_accesstoken", 0);
        String accessToken = preference_accesstoken.getString("ACCESSTOKEN", "0");

        if (accessToken.equals("0")) {
            ll_logout.setVisibility(View.GONE);
            userTextView.setText("登录");
            userImageView.setImageDrawable(null);
        }

        if (!accessToken.equals("0")) {
            ll_logout.setVisibility(View.VISIBLE);
            ((KBaseActivity) getActivity()).appAction.getPersonalDetailByAccessToken(accessToken, new AppActionCallBackListener<PersonalDetailResp>() {
                @Override
                public void onSuccess(PersonalDetailResp data) {
                    userTextView.setText(data.getName());
                    new AsynImageLoader().loadImage(data.getAvatar_url(), new AsynImageLoader.ImageLoadInterface() {
                        @Override
                        public void loadImageCallback(final Drawable drawable) {
                            userImageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    userImageView.setImageDrawable(drawable);
                                }
                            });
                        }
                    });
                }

                @Override
                public void onFailure(String errorEvent, String message) {

                }
            });
        }
    }

}
