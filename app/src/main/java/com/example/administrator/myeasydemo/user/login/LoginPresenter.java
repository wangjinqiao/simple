package com.example.administrator.myeasydemo.user.login;

import com.example.administrator.myeasydemo.model.CachePreferences;
import com.example.administrator.myeasydemo.model.User;
import com.example.administrator.myeasydemo.network.CallBackUI;
import com.example.administrator.myeasydemo.network.HttpEasyShopClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    private Call call;

    public void login(String username, String password, String path) {

        getView().showpgb();
        call = HttpEasyShopClient.getInstance().
                visitHttp(username, password, path);
        call.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidepgb();
                getView().showToast(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String s) {

                getView().hidepgb();
                User response = new Gson().fromJson(s, User.class);
            if (response.getCode()==1){
                getView().showToast("登录成功");
                //保存到本地
                User.UserInfo user = response.getData();
                CachePreferences.setUser(user);

                getView().loginSuccess();
            }else if(response.getCode()==2){
                getView().showToast(response.getMsg());
                getView().loginFail();
            }else {
                getView().showToast("未知错误！");
            }


            }
        });
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        call.cancel();
    }
}
