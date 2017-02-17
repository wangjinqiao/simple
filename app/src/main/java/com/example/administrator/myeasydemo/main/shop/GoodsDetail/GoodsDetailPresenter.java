package com.example.administrator.myeasydemo.main.shop.GoodsDetail;


import com.example.administrator.myeasydemo.model.GoodsDetailResult;
import com.example.administrator.myeasydemo.model.User;
import com.example.administrator.myeasydemo.network.CallBackUI;
import com.example.administrator.myeasydemo.network.HttpEasyShopClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class GoodsDetailPresenter extends MvpNullObjectBasePresenter<GoodsDetailView> {

    //  删除商品相关
    public void deleteGoods(String uuid) {
        getView().showProgress();
        deleteCall = HttpEasyShopClient.getInstance().deleteGoods(uuid);
        deleteCall.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hideProgress();
                getView().showMessage(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String s) {
                getView().hideProgress();
                User user = new Gson().fromJson(s, User.class);
                if (user.getCode() == 1) {
                    getView().deleteEnd();
                    getView().showMessage("删除成功");
                } else {
                    getView().showMessage("删除失败");

                }
            }
        });
    }

    //获取详情的Call
    private Call getDetailCall;
    private Call deleteCall;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (getDetailCall != null) getDetailCall.cancel();
        if (deleteCall != null) deleteCall.cancel();
    }

    //获取商品的详细数据
    public void getData(String uuid) {
        getView().showProgress();
        getDetailCall = HttpEasyShopClient.getInstance().getGoodsData(uuid);
        getDetailCall.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hideProgress();

                getView().showError();
                getView().showMessage("error" + e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                getView().hideProgress();
                GoodsDetailResult goodsDetailResult = new Gson().fromJson(body, GoodsDetailResult.class);
                if (goodsDetailResult.getCode() == 1) {
                    //商品详情
                    GoodsDetailResult.GoodsDetail goodsDetail = goodsDetailResult.getDatas();
                    //用来存放图片路径集合
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < goodsDetail.getPages().size(); i++) {
                        String page = goodsDetail.getPages().get(i).getUri();
                        list.add(page);
                    }
                    getView().setImageData(list);
                    getView().setData(goodsDetail, goodsDetailResult.getUser());
                } else {
                    getView().showError();
                }
            }
        });
    }
}
