package com.example.administrator.myeasydemo.main.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.commons.ActivityUtils;
import com.example.administrator.myeasydemo.components.AvatarLoadOptions;
import com.example.administrator.myeasydemo.main.me.personInfo.PersonActivity;
import com.example.administrator.myeasydemo.model.CachePreferences;
import com.example.administrator.myeasydemo.network.EasyShopApi;
import com.example.administrator.myeasydemo.user.login.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的页面
 */

public class MeFragment extends Fragment {

    @BindView(R.id.iv_user_head)
    CircularImageView ivUserHead;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private View view;
    private ActivityUtils activityUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, container, false);
        }
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //  判断是否登录显示图像和用户名
        if (CachePreferences.getUser().getUsername() == null) return;
        if (CachePreferences.getUser().getNickname() == null) {
            tvLogin.setText("请输入昵称");
        } else {
            tvLogin.setText(CachePreferences.getUser().getNickname());
        }
        ImageLoader.getInstance()
                .displayImage(EasyShopApi.IMAGE_URL + CachePreferences.getUser().getIconUrl(), ivUserHead, AvatarLoadOptions.build());
    }

    @OnClick({R.id.iv_user_head, R.id.tv_person_info, R.id.tv_login, R.id.tv_person_goods, R.id.tv_goods_upload})
    public void onClick(View view) {
        //未登录时跳转到登录界面
        if (CachePreferences.getUser().getUsername() == null) {
            activityUtils.startActivity(LoginActivity.class);
            return;
        }
        switch (view.getId()) {
            case R.id.iv_user_head:
            case R.id.tv_person_info:
            case R.id.tv_login:
                activityUtils.startActivity(PersonActivity.class);
                break;
            case R.id.tv_person_goods:
                // TODO: 2017/2/14 0014 跳转到我的商品页面
                activityUtils.showToast("我的商品页面，待实现");
                break;
            case R.id.tv_goods_upload:
                // TODO: 2017/2/14 0014 跳转到商品上传页面
                activityUtils.showToast("商品上传页面，待实现");
                break;
        }
    }
}
