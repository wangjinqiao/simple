package com.example.administrator.myeasydemo.main.me.uploadGoods;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Administrator on 2017/2/17 0017.
 */

public interface UploadGoodsView extends MvpView {
    void showPgb();

    void hidePgb();

    void uploadSuccess();

    void showMsg(String msg);
}
