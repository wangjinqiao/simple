package com.example.administrator.myeasydemo.main.me.MyGoods;

import com.example.administrator.myeasydemo.main.shop.ShopView;
import com.example.administrator.myeasydemo.model.CachePreferences;
import com.example.administrator.myeasydemo.model.ShopGoods;
import com.example.administrator.myeasydemo.network.CallBackUI;
import com.example.administrator.myeasydemo.network.HttpEasyShopClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/17 0017.
 */

public class MyGoodsPresenter extends MvpNullObjectBasePresenter<ShopView> {
    private Call call;
    private int pageNum = 2;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }

    //刷新
    public void refresh(String type) {
        getView().showRefresh();
        call = HttpEasyShopClient.getInstance().getMyGoods(1 + "", CachePreferences.getUser().getUsername(), type);
        call.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().showRefreshError(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String s) {
                ShopGoods shopGoods = new Gson().fromJson(s, ShopGoods.class);
                getView().hideRefresh();
                switch (shopGoods.getCode()) {
                    case 1:
                        if (shopGoods.getDatas().size() == 0) {

                            getView().showRefreshEnd();
                        } else {
                            getView().addRefreshData(shopGoods.getDatas());
                            getView().showMessage("刷新成功");
                        }
                        pageNum=2;
                        break;
                    default:
                        getView().showRefreshError(shopGoods.getMsg());

                }
            }
        });

    }

    //加载更多
    public void loadMore(String type) {
        getView().showLoadMoreLoading();
        call = HttpEasyShopClient.getInstance().getMyGoods(pageNum + "", CachePreferences.getUser().getUsername(), type);
        call.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().showLoadMoreError(e.getMessage());
                getView().hideLoadMore();
            }

            @Override
            public void onResponseUI(Call call, String s) {
                ShopGoods shopGoods = new Gson().fromJson(s, ShopGoods.class);
                getView().hideLoadMore();
                switch (shopGoods.getCode()) {
                    case 1:
                        if (shopGoods.getDatas().size() == 0) {
                            getView().showLoadMoreEnd();
                        } else {
                            getView().addMoreData(shopGoods.getDatas());
                            getView().showMessage("加载成功");
                        }
                        pageNum++;
                        break;
                    default:
                        getView().showLoadMoreError(shopGoods.getMsg());

                }
            }
        });

    }

}
