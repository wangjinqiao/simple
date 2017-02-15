package com.example.administrator.myeasydemo.user.login;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public interface LoginView extends MvpView {
    void showpgb();

    void hidepgb();

    void showToast(String msg);

    //登录 成功
    void loginSuccess();

    //登录 失败
    void loginFail();

    //用户名 密码不对时提示用户
    void showUserPasswordError(String msg);
}
