package com.example.administrator.myeasydemo.main.shop;

import com.example.administrator.myeasydemo.model.ShopGoods;
import com.example.administrator.myeasydemo.network.CallBackUI;
import com.example.administrator.myeasydemo.network.HttpEasyShopClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class ShopPresenter extends MvpNullObjectBasePresenter<ShopView> {
    private Call call;
    private int pageNum = 2;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call == null) call.cancel();
    }

    //刷新
    public void refreshPage(String type) {
        getView().showRefresh();
        call = HttpEasyShopClient.getInstance().getGoods(1, type);
        call.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().showRefreshError("error"+e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String s) {
                //解析
                ShopGoods shopGoods = new Gson().fromJson(s, ShopGoods.class);
                switch (shopGoods.getCode()) {
                    case 1:
                        if (shopGoods.getDatas().size() == 0) {
                            getView().showRefreshEnd();
                        } else {
                            getView().addRefreshData(shopGoods.getDatas());
                            getView().showRefreshSuccess();
                        }
                        pageNum = 2;
                        break;
                    default:
                        getView().showRefreshError(shopGoods.getMsg());
                        break;
                }

            }
        });

    }

    //加载
    public void loadMore(String type) {
        getView().showLoadMoreLoading();
        call = HttpEasyShopClient.getInstance().getGoods(pageNum, type);
        call.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().showLoadMoreError(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String s) {
                ShopGoods shopGoods = new Gson().fromJson(s, ShopGoods.class);
                switch (shopGoods.getCode()) {
                    case 1:
                        if (shopGoods.getDatas().size() != 0) {
                            getView().addMoreData(shopGoods.getDatas());
                            getView().showRefreshSuccess();
                            pageNum++;
                        }else{
                        getView().showLoadMoreEnd();
                        }
                        break;
                    default:
                        getView().showLoadMoreError(shopGoods.getMsg());
                        break;
                }
            }
        });
    }
}
