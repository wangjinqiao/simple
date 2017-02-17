package com.example.administrator.myeasydemo.main.shop;

import com.example.administrator.myeasydemo.model.ShopGoods;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public interface ShopView extends MvpView {
    //刷新  处理中
    void showRefresh();

    //刷新  失败
    void showRefreshError(String msg);

    //数据刷新 --刷新结束
    void showRefreshEnd();

    //刷新  成功
    void showRefreshSuccess();

    //隐藏刷新视图
    void hideRefresh();

    //加载  处理中
    void showLoadMoreLoading();

    //加载  失败
    void showLoadMoreError(String msg);

    //加载更多 -- 没有更多数据
    void showLoadMoreEnd();

    //隐藏加载更多的视图
    void hideLoadMore();

    //添加更多数据
    void addMoreData(List<ShopGoods.GoodsInfo> data);

    //添加刷新更多的数据
    void addRefreshData(List<ShopGoods.GoodsInfo> data);

    // 消息提示
    void showMessage(String msg);
}
