package com.example.administrator.myeasydemo.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 对用户信息本地保存
 */
public class CachePreferences {

    private static final String NAME = CachePreferences.class.getSimpleName();
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PWD = "userPwd";
    private static final String KEY_USER_HX_ID = "userHxID";
    private static final String KEY_USER_TABLE_ID = "userUuid";
    private static final String KEY_USER_HEAD_IMAGE = "userHeadImage";
    private static final String KEY_USER_NICKNAME = "userNickName";

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private CachePreferences() {
    }

    @SuppressLint("CommitPrefEdits")
    public static void init(Context context) {
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static void clearAllData() {
        editor.clear();
        editor.apply();
    }

    public static void setUser(User.UserInfo user) {
        editor.putString(KEY_USER_NAME, user.getUsername());
        editor.putString(KEY_USER_PWD, user.getPassword());
        editor.putString(KEY_USER_HX_ID, user.getHx_id());
        editor.putString(KEY_USER_TABLE_ID, user.getTable_key());
        editor.putString(KEY_USER_HEAD_IMAGE, user.getIconUrl());
        editor.putString(KEY_USER_NICKNAME, user.getNickname());

        editor.apply();
    }



    public static User.UserInfo getUser() {
        User.UserInfo user = new User.UserInfo();
        user.setUsername(preferences.getString(KEY_USER_NAME, null));
        user.setPassword(preferences.getString(KEY_USER_PWD, null));
        user.setHx_id(preferences.getString(KEY_USER_HX_ID, null));
        user.setTable_key(preferences.getString(KEY_USER_TABLE_ID, null));
        user.setIconUrl(preferences.getString(KEY_USER_HEAD_IMAGE, null));
        user.setNickname(preferences.getString(KEY_USER_NICKNAME, null));
        return user;
    }

}
