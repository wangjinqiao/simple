package com.example.administrator.myeasydemo.network;

import com.example.administrator.myeasydemo.main.me.uploadGoods.MyGoodsToUpload;
import com.example.administrator.myeasydemo.model.CachePreferences;
import com.example.administrator.myeasydemo.model.User;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private Gson gson;

    private HttpEasyShopClient() {
        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设置级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        this.okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        gson = new Gson();
    }

    /**
     * 客户端单例模式
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

    //修改昵称
    public Call uploadUser(User.UserInfo user) {
        //构架请求体（多部分形式）
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //传一个用户实体类，转换为json字符串（Gson）
                .addFormDataPart("user", gson.toJson(user))
                .build();

        //构建请求
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.UPDATA)
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request);
    }


    //上传图像
    public Call uploadHeadIcon(File file) {
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user", gson.toJson(CachePreferences.getUser()))
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.UPDATA)
                .post(body)
                .build();
        return okHttpClient.newCall(request);
    }

    //获取商品列表
    public Call getGoods(int pageNum, String type) {
        FormBody body = new FormBody.Builder()
                .add("pageNo", pageNum + "")
                .add("type", type)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.GETGOODS)
                .post(body)
                .build();
        return okHttpClient.newCall(request);
    }

    //获取商品详情
    public Call getGoodsData(String goodsUuid) {
        RequestBody requestBody = new FormBody.Builder()
                .add("uuid", goodsUuid)
                .build();

        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.DETAIL)
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request);
    }

    //获取我的商品
    public Call getMyGoods(String pageNo, String master, String type) {
        RequestBody requestBody = new FormBody.Builder()
                .add("pageNo", pageNo)
                .add("master", master)
                .add("type", type)
                .build();

        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.GETGOODS)
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request);
    }

    //删除商品
    public Call deleteGoods(String uuid) {
        RequestBody requestBody = new FormBody.Builder()
                .add("uuid", uuid)
                .build();

        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.DELETE)
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request);
    }

    //    商品上传
    public Call uploadGoods(MyGoodsToUpload myGoodsToUpload, ArrayList<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("good", gson.toJson(myGoodsToUpload));

        for (File file : files) {
            RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("image", file.getName(), body);
        }

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.UPLOADGOODS)
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request);
    }
}
