package com.example.administrator.myeasydemo.main.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.main.shop.GoodsDetail.GoodsDetailActivity;
import com.example.administrator.myeasydemo.model.ShopGoods;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * 市场页面
 */

public class ShopFragment extends MvpFragment<ShopView, ShopPresenter> implements ShopView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @BindView(R.id.tv_load_error)
    TextView tvLoadError;
    private Unbinder unbinder;
    private ShopAdapter shopAdapter;
    private String pageType = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopAdapter = new ShopAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public ShopPresenter createPresenter() {
        return new ShopPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        //如果当前页面没有数据，自动刷新
        if (shopAdapter.getItemCount() == 0) {
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }


    //初始化视图
    private void init() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        shopAdapter.setmOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ShopGoods.GoodsInfo goodsInfo) {
                //点击图片，跳转到商品详情页
                Intent startIntent = GoodsDetailActivity.getStartIntent(getContext(), goodsInfo.getUuid(), GoodsDetailActivity.FROM_SHOP);
                startActivity(startIntent);
            }
        });
        recyclerView.setAdapter(shopAdapter);

        //初始化刷新布局
        //使用本对象作为key，用来记录上一次刷新的时间，如果刷新时间间隔过短，则不会触发刷新事件
        refreshLayout.setLastUpdateTimeRelateObject(this);
        //设置刷新时背景颜色
        refreshLayout.setBackgroundResource(R.color.text_hint);
        //关闭刷新头使用时间
        refreshLayout.setDurationToCloseHeader(1500);
        //实现刷新，加载回调
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                presenter.loadMore(pageType);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refreshPage(pageType);
            }
        });
    }

    //


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
//##################################   #########################################

    @Override
    public void showRefresh() {
        refreshLayout.autoRefresh();
        tvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshError(String msg) {
        //停止刷新
        refreshLayout.refreshComplete();
        //判断是否有数据
        if (shopAdapter.getItemCount() == 0) {
            tvLoadError.setVisibility(View.VISIBLE);
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefreshEnd() {
        refreshLayout.refreshComplete();
        Toast.makeText(getContext(), R.string.refresh_more_end, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefreshSuccess() {
        refreshLayout.refreshComplete();
    }

    @Override
    public void hideRefresh() {
        refreshLayout.refreshComplete();
    }

    @Override
    public void showLoadMoreLoading() {
        tvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoreError(String msg) {
        //停止刷新
        refreshLayout.refreshComplete();
        //判断是否有数据
        if (shopAdapter.getItemCount() == 0) {
            tvLoadError.setVisibility(View.VISIBLE);
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadMoreEnd() {
        refreshLayout.refreshComplete();
        Toast.makeText(getContext(), R.string.load_more_end, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoadMore() {
        refreshLayout.refreshComplete();
    }

    @Override
    public void addMoreData(List<ShopGoods.GoodsInfo> data) {
        if (data != null) {
            shopAdapter.addData(data);
        }
    }

    @Override
    public void addRefreshData(List<ShopGoods.GoodsInfo> data) {
        if (data != null) {
            shopAdapter.clearData();
            shopAdapter.addData(data);
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //*******************************点击错误刷新*************************************************
    @OnClick(R.id.tv_load_error)
    public void onClick() {
        refreshLayout.autoRefresh();
    }
}
