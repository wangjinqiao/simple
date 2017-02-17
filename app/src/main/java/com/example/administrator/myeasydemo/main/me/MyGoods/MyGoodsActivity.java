package com.example.administrator.myeasydemo.main.me.MyGoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.main.shop.GoodsDetail.GoodsDetailActivity;
import com.example.administrator.myeasydemo.main.shop.GoodsDetail.GoodsPhotoDetailActivity;
import com.example.administrator.myeasydemo.main.shop.ShopAdapter;
import com.example.administrator.myeasydemo.main.shop.ShopView;
import com.example.administrator.myeasydemo.model.ShopGoods;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MyGoodsActivity extends MvpActivity<ShopView, MyGoodsPresenter> implements ShopView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @BindView(R.id.tv_load_error)
    TextView tvLoadError;
    @BindView(R.id.tv_load_empty)
    TextView tvLoadEmpty;
    @BindView(R.id.activity_person_goods)
    RelativeLayout activityPersonGoods;
    private String pageType = "";
    ShopAdapter shopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_goods);
        ButterKnife.bind(this);
        setToolbar();
        initView();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置toolbar的菜单监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_household:
                        pageType = "household";
                        presenter.refresh("household");
                        break;
                    case R.id.menu_electron:
                        pageType = "electron";
                        presenter.refresh("electron");
                        break;
                    case R.id.menu_dress:
                        pageType = "dress";
                        presenter.refresh("dress");
                        break;
                    case R.id.menu_book:
                        pageType = "book";
                        presenter.refresh("book");
                        break;
                    case R.id.menu_toy:
                        pageType = "toy";
                        presenter.refresh("toy");
                        break;
                    case R.id.menu_gift:
                        pageType = "gift";
                        presenter.refresh("gift");
                        break;
                    case R.id.menu_other:
                        pageType = "other";
                        presenter.refresh("other");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goods_type, menu);
        return true;
    }

    private void initView() {
        //初始化recyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        shopAdapter = new ShopAdapter();
        shopAdapter.setmOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ShopGoods.GoodsInfo goodsInfo) {
                // 点击跳转到图片详情界面
                Intent startIntent = GoodsDetailActivity.getStartIntent(MyGoodsActivity.this, goodsInfo.getUuid(), GoodsDetailActivity.FROM_MY);
                startActivity(startIntent);


            }
        });
        recyclerView.setAdapter(shopAdapter);
        //刷新，加载
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setBackgroundResource(R.color.text_hint);
        refreshLayout.setDurationToCloseHeader(1500);
        //设置回调
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                presenter.loadMore(pageType);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refresh(pageType);
            }
        });
    }

    //进入页面，自动刷新
    @Override
    protected void onStart() {
        super.onStart();
        pageType = "";
        //自动刷新
        refreshLayout.autoRefresh();

    }

    @NonNull
    @Override
    public MyGoodsPresenter createPresenter() {
        return new MyGoodsPresenter();
    }

    //*****************************************  视图接口 相关  ***********************************************
    @Override
    public void showRefresh() {
        tvLoadEmpty.setVisibility(View.GONE);
        tvLoadError.setVisibility(View.GONE);
        if (shopAdapter.getItemCount() == 0) {
            pageType = "";
        }
    }

    @Override
    public void showRefreshError(String msg) {
        //停止刷新
        refreshLayout.refreshComplete();
        //判断是否有数据
        if (shopAdapter.getItemCount() == 0) {
            tvLoadError.setVisibility(View.VISIBLE);
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefreshEnd() {
        refreshLayout.refreshComplete();
        tvLoadEmpty.setVisibility(View.VISIBLE);
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
        refreshLayout.autoLoadMore();
        tvLoadEmpty.setVisibility(View.GONE);
        tvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoreError(String msg) {
        //停止刷新
        refreshLayout.refreshComplete();
        //判断是否有数据
        if (shopAdapter.getItemCount() == 0) {
            tvLoadError.setVisibility(View.VISIBLE);
            tvLoadEmpty.setVisibility(View.VISIBLE);
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadMoreEnd() {
        refreshLayout.refreshComplete();
        Toast.makeText(this, R.string.load_more_end, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //*******************************点击错误刷新*************************************************
    @OnClick(R.id.tv_load_error)
    public void onClick() {
        refreshLayout.autoRefresh();
    }
}
