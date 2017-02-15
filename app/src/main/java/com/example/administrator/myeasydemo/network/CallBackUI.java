package com.example.administrator.myeasydemo.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public abstract class CallBackUI implements Callback {
    Handler handler = new Handler(Looper.getMainLooper());

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
        final String s = response.body().string();


        handler.post(new Runnable() {
            @Override
            public void run() {
                onResponseUI(call, s);
            }
        });
    }

    public abstract void onFailureUI(Call call, IOException e);

    public abstract void onResponseUI(Call call, String s);
}
