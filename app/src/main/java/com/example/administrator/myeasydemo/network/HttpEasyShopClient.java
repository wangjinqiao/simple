package com.example.administrator.myeasydemo.network;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 网络客户端
 * Created by Administrator on 2017/2/9 0009.
 */

public class HttpEasyShopClient {

    private OkHttpClient okHttpClient;
    private static HttpEasyShopClient easyShopClient;

    private HttpEasyShopClient() {
        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设置级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        this.okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    /**
     * 登录，注册请求
     * post
     */
    public static synchronized HttpEasyShopClient getInstance() {
        if (easyShopClient == null) {
            easyShopClient = new HttpEasyShopClient();
        }
        return easyShopClient;
    }

    /**
     * 注册
     * post
     *
     * @param username 用户名
     * @param password 密码
     */
    public Call visitHttp(String username, String password, String path) {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + path)
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request);
    }

}
