package com.example.administrator.myeasydemo.main.shop.GoodsDetail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.commons.ActivityUtils;
import com.example.administrator.myeasydemo.commons.LogUtils;
import com.example.administrator.myeasydemo.components.AvatarLoadOptions;
import com.example.administrator.myeasydemo.components.ProgressDialogFragment;
import com.example.administrator.myeasydemo.model.CachePreferences;
import com.example.administrator.myeasydemo.model.GoodsDetailResult;
import com.example.administrator.myeasydemo.model.User;
import com.example.administrator.myeasydemo.network.EasyShopApi;
import com.example.administrator.myeasydemo.user.login.LoginActivity;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

public class GoodsDetailActivity extends MvpActivity<GoodsDetailView, GoodsDetailPresenter> implements GoodsDetailView {

    @BindView(R.id.tv_goods_delete)
    TextView tvGoodsDelete;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.tv_detail_name)
    TextView tvDetailName;
    @BindView(R.id.tv_detail_price)
    TextView tvDetailPrice;
    @BindView(R.id.tv_detail_master)
    TextView tvDetailMaster;
    @BindView(R.id.tv_detail_describe)
    TextView tvDetailDescribe;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.btn_detail_message)
    Button btnDetailMessage;
    @BindView(R.id.tv_goods_error)
    TextView tvGoodsError;

    public static final int FROM_SHOP = 1;
    public static final int FROM_MY = 2;
    public static final String FROM = "from";
    public static final String UUID = "uuid";//商品主键


    public static Intent getStartIntent(Context context, String uuid, int from) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(UUID, uuid);
        intent.putExtra(FROM, from);
        return intent;
    }


    private Unbinder unbinder;
    //存放图片路径的集合
    private ArrayList<String> imgUrls = new ArrayList<>();
    private ArrayList<ImageView> imgsData = new ArrayList<>();
    private String str_uuid;
    //viewPager的适配器
    private GoogsDetailAdapter adapter;
    private ActivityUtils activityUtils;
    private User.UserInfo goods_user;
    private ProgressDialogFragment progressDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        unbinder = ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);
        init();
        adapter = new GoogsDetailAdapter(imgsData);
        adapter.setListener(new GoogsDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                //点击图片，跳转到图片详情页
                LogUtils.e(imgUrls.size() + "点击图片，跳转到图片详情页");
                Intent startIntent = GoodsPhotoDetailActivity.getStartIntent(GoodsDetailActivity.this, imgUrls);
                startActivity(startIntent);
            }
        });
        viewpager.setAdapter(adapter);

        //返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void init() {
        //拿到uuid（商品主键）
        str_uuid = getIntent().getStringExtra(UUID);
        //来自哪个页面，1=从市场页面，2=从我的商品页面
        int btn_show = getIntent().getIntExtra(FROM, 1);
        //如果=2，来自我的页面
        if (btn_show == 2) {
            tvGoodsDelete.setVisibility(View.VISIBLE);//显示“删除”
            btnDetailMessage.setVisibility(View.GONE);//隐藏“发消息“
        }else{
            tvGoodsDelete.setVisibility(View.GONE);//显示“发消息”
            btnDetailMessage.setVisibility(View.VISIBLE);//隐藏“删除“
        }
        //获取商品详情，业务
        presenter.getData(str_uuid);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter();
    }

    //**************************************    视图接口相关方法    *****************************************************
    @Override
    public void showProgress() {
        if (progressDialogFragment == null) progressDialogFragment = new ProgressDialogFragment();
        if (progressDialogFragment.isVisible()) return;
        progressDialogFragment.show(getSupportFragmentManager(), "fragment_progress_dialog");
    }

    @Override
    public void hideProgress() {
        progressDialogFragment.dismiss();
    }

    @Override
    public void setImageData(ArrayList<String> arrayList) {
        imgUrls = arrayList;
        //加载图片
        for (int i = 0; i < imgUrls.size(); i++) {
            ImageView imageView = new ImageView(this);
            ImageLoader.getInstance()
                    .displayImage(EasyShopApi.IMAGE_URL + arrayList.get(i),
                            imageView, AvatarLoadOptions.build_item());
            imgsData.add(imageView);
        }
        //刷新适配器
        adapter.notifyDataSetChanged();
        //确认图片数量，创建圆点指示器
        indicator.setViewPager(viewpager);

    }

    @Override
    public void setData(GoodsDetailResult.GoodsDetail data, User.UserInfo goods_user) {
        //数据显示
        this.goods_user = goods_user;
        tvDetailName.setText(data.getName());
        tvDetailPrice.setText(getString(R.string.goods_money, data.getPrice()));
        tvDetailMaster.setText(getString(R.string.goods_detail_master, goods_user.getNickname()));
        tvDetailDescribe.setText(data.getDescription());
    }

    @Override
    public void showError() {
        tvGoodsError.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.goods_overdue);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteEnd() {
        finish();
    }

    //点击事件
    @OnClick({R.id.tv_goods_delete, R.id.btn_detail_message})
    public void onClick(View view) {
        //判断是否登录
        if (CachePreferences.getUser().getUsername() == null) {
            activityUtils.startActivity(LoginActivity.class);
            return;
        }
        switch (view.getId()) {
            case R.id.tv_goods_delete:
                //警告删除
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.goods_title_delete);
                builder.setMessage(R.string.goods_info_delete);
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除
                        presenter.deleteGoods(str_uuid);

                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();

                break;
            case R.id.btn_detail_message:
                // TODO: 2017/2/16 0016  环信发消息页面
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
