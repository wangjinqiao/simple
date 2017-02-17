package com.example.administrator.myeasydemo.main.shop.GoodsDetail;

import com.example.administrator.myeasydemo.model.GoodsDetailResult;
import com.example.administrator.myeasydemo.model.User;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public interface GoodsDetailView extends MvpView {

    void showProgress();

    void hideProgress();

    //设置图片路径
    void setImageData(ArrayList<String> arrayList);

    //设置商品信息
    void setData(GoodsDetailResult.GoodsDetail data, User.UserInfo goods_user);

    //*商品不存在了*/
    void showError();

    //提示信息
    void showMessage(String msg);

    //*删除商品*/
    void deleteEnd();
}
