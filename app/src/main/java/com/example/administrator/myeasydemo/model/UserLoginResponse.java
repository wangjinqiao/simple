package com.example.administrator.myeasydemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户登录响应
 */

public class UserLoginResponse extends UserRegisterResponse{


    /**
     * other : /images/35C69D35E4164D19B4278DC45FDCAF45/2D505F81BB.jpg
     * nickname : 666
     */

    @SerializedName("other")
    protected String iconUrl;
    protected String nickname;

    public String getNickname() {
        return nickname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "iconUrl='" + iconUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
