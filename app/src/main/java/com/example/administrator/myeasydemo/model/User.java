package com.example.administrator.myeasydemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户 响应
 */
public class User {
    private int code;
    private String msg;
    private UserInfo data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public UserInfo getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public static class UserInfo {


        protected String username;
        @SerializedName("name")
        protected String hx_id;
        @SerializedName("uuid")
        protected String table_key;
        protected String password;
        @SerializedName("other")
        protected String iconUrl;
        protected String nickname;

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

        public String getIconUrl() {
            return iconUrl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public void setTable_key(String table_key) {
            this.table_key = table_key;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}