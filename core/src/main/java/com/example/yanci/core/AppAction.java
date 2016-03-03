package com.example.yanci.core;

import com.example.yanci.model.AccessTokenResp;
import com.example.yanci.model.PersonalDetailResp;

/**
 * Created by yanci on 16/2/17.
 */
public interface AppAction {

    /***
     * 获取用户令牌
     * @param client_id
     * @param client_secret
     * @param listener
     */
    public void getAccessToken(String client_id,String client_secret,String code,AppActionCallBackListener<AccessTokenResp>listener);

    /***
     * 获取个人信息通过Accesstoken
     * @param access_token
     * @param listener
     */
    public void getPersonalDetailByAccessToken(String access_token,AppActionCallBackListener<PersonalDetailResp>listener);
}
