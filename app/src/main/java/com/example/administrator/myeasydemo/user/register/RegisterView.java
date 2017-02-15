package com.example.administrator.myeasydemo.user.register;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public interface RegisterView extends MvpView {
    void showpgb();

    void hidepgb();

    void showToast(String msg);

    //注册 成功
    void registerSuccess();

    //注册 失败
    void registerFail();

    //用户名 密码不对时提示用户
    void showUserPasswordError(String msg);
}
