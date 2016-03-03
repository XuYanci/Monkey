package com.example.yanci.api;

import com.example.yanci.model.AccessTokenResp;
import com.example.yanci.model.PersonalDetailResp;

/**
 * Created by yanci on 16/2/14.
 */
public interface Api {

    public final static String MORE_LOGIN = "github.more.login";
    public final static String MORE_LOGOUT = "github.more.logout";
    public final static String USERS_COUNTRYS = "github.users.countrys";
    public final static String USERS_CITYS = "github.users.citys";
    public final static String USERS_LANGUAGE = "github.users.languages";
    public final static String REPOSITORIES_LANGUAGE = "github.repositories.languages";
    public final static String DISCOVERY_TRENDING = "github.discovery.trending";
    public final static String DISCOVERY_SHOWCASE = "github.discovery.showcase";
    public final static String DISCOVERY_DYNAMIC = "github.discovery.dynamic";
    public final static String DISCOVERY_SEARCH = "github.discovery.search";
    public final static String DISCOVERY_GITHUBRANKING = "github.discovery.githubranking";
    public final static String DISCOVERY_GITHUBAWARDS = "github.discovery.githubawards";

    // 服务器Root URL
    public final static String SERVER_API_URL = "https://api.github.com";
    public final static String SERVER_URL = "https://github.com";

    // url suffix
    public final static String SERVER_ACCESSTOKEN_SUFFIX = "/login/oauth/access_token";
    public final static String SERVER_USERS_SUFFIX = "/users";
    public final static String SERVER_USER_SUFFIX = "/user";

    // TODO: Github Api List

    /**********  MORE 模 块 **********/
    // 获取用户AccessToken
    public AccessTokenResp getAccessToken(String client_id,String client_secret,String code);

    // 通过AccessToken获取用户个人信息
    public PersonalDetailResp getPersonalDetailByAccessToken(String access_token);

    // 获取用户个人信息
    public PersonalDetailResp getPersonalDetail(String username);
    /********** USERS 模块 **********/

    // 获取国家列表

    // 获取城市列表
    // Search based on the location where a user resides

    // 获取语言列表
    // Search based on the languages of a user's repositories

    /********** REPOSITORIES 模块 **********/

    // 获取语言列表

    /********** DISCOVERY 模块 ***********/


    // Trending

    // Language & month & day & week

    // Showcases


    // 动态

    // 搜索
    // Search Users & Repositories

    // githubranking
    // Repositories ranking & users ranking & organizations ranking

    // github - awards
    // Users Ranking world & Users ranking country & User Ranking City

}
