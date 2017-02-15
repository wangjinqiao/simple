package com.example.administrator.myeasydemo.user.register;

import android.support.annotation.NonNull;

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

public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {
    private Call call;

    @Override
    public void attachView(@NonNull RegisterView view) {
        super.attachView(view);
        //初始化相关代码
    }

    public void regiser(String username, String password, String path) {
        getView().showpgb();
        call = HttpEasyShopClient.getInstance().visitHttp(username, password, path);
        call.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                //隐藏加载动画
                getView().hidepgb();
                //显示异常信息
                getView().showToast(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String s) {
                //隐藏进度条
                getView().hidepgb();
                //解析
                User user = new Gson().fromJson(s, User.class);
                //根据不同的结果码处理
                if (user.getCode() == 1) {
                    //成功提示
                    getView().showToast("注册成功");

                    //拿到用户的实体类
                    User.UserInfo data = user.getData();
                    //  将用户的信息保存到本地配置中
                    CachePreferences.setUser(data);
                    //执行注册成功的方法
                    getView().registerSuccess();
                } else if (user.getCode() == 2) {
                    //提示失败信息
                    getView().showToast(user.getMsg());
                    //执行注册失败的方法
                    getView().registerFail();
                } else {
                    getView().showToast("未知错误！");
                }
            }
        });
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        //释放资源
        //视图销毁，取消网络请求
        if (call != null) {
            call.cancel();
        }
    }
}
