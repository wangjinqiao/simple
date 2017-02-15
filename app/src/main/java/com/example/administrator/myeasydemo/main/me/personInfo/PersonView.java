package com.example.administrator.myeasydemo.main.me.personInfo;


import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public interface PersonView extends MvpView {

    void showPrb();

    void hidePrb();

    void showMsg(String msg);
    //用来更新头像
    void updataAvatar(String url);
}
