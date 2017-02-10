package com.example.administrator.myeasydemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户注册响应
 */

public class UserRegisterResponse {

    /**
     * username : xc62
     * name : yt59856b15cf394e7b84a7d48447d16098
     * uuid : 0F8EC12223174657B2E842076D54C361
     * password : 123456
     */

    protected String username;
    @SerializedName("name")
    protected String hx_id;
    @SerializedName("uuid")
    protected String table_key;
    protected String password;

    public String getUsername() {
        return username;
    }

    public String getHx_id() {
        return hx_id;
    }

    public String getTable_key() {
        return table_key;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserRegisterResponse{" +
                "username='" + username + '\'' +
                ", hx_id='" + hx_id + '\'' +
                ", table_key='" + table_key + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}



