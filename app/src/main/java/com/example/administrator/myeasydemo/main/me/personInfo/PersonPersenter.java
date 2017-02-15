package com.example.administrator.myeasydemo.main.me.personInfo;


import com.example.administrator.myeasydemo.model.CachePreferences;
import com.example.administrator.myeasydemo.model.User;
import com.example.administrator.myeasydemo.network.CallBackUI;
import com.example.administrator.myeasydemo.network.HttpEasyShopClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class PersonPersenter extends MvpNullObjectBasePresenter<PersonView> {

    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }

    //上传头像
    public void updataAvatar(File file) {
        getView().showPrb();
        // 上传头像，网络请求
        call = HttpEasyShopClient.getInstance().uploadHeadIcon(file);
        call.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String s) {
                getView().hidePrb();
                //解析
                User user = new Gson().fromJson(s, User.class);
                if (user == null) {
                    getView().showMsg("出现未知错误");
                    return;
                } else if (user.getCode() != 1) {
                    getView().showMsg(user.getMsg());
                    return;
                }
                //上传成功
                getView().showMsg("上传成功");

                //保存本地
                CachePreferences.setUser(user.getData());
                //更新图像
                getView().updataAvatar(user.getData().getIconUrl());
                // TODO: 2017/2/15 0015 更新环信图像 
            }
        });
    }
}
