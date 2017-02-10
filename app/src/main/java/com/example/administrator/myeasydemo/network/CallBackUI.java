package com.example.administrator.myeasydemo.network;

import android.os.Handler;
import android.os.Looper;

import com.example.administrator.myeasydemo.commons.LogUtils;
import com.example.administrator.myeasydemo.model.HttpResponse;
import com.example.administrator.myeasydemo.model.UserLoginResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public abstract class CallBackUI<T> implements Callback {
    Handler handler = new Handler(Looper.getMainLooper());


    HttpResponse<T> httpResponse;

    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailureUI(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {

        //拿到响应
        if (!response.isSuccessful()) {
            throw new IOException("error code:" + response.code());
        }
        //拿到响应体
        String s = response.body().string();
        //解析
        httpResponse = new Gson().fromJson(s, HttpResponse.class);

        handler.post(new Runnable() {
            @Override
            public void run() {
                onResponseUI(call, httpResponse);
            }
        });
    }

    public abstract void onFailureUI(Call call, IOException e);

    public abstract void onResponseUI(Call call,  HttpResponse<T> httpResponse);
}
