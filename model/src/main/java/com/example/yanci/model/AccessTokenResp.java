package com.example.yanci.model;

/**
 * Created by yanci on 16/3/2.
 */
public class AccessTokenResp {
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    private String access_token;
    private String scope;
    private String token_type;
}
