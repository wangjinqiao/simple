package com.example.administrator.myeasydemo.main.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.commons.ActivityUtils;
import com.example.administrator.myeasydemo.main.me.personInfo.PersonActivity;
import com.example.administrator.myeasydemo.model.CachePreferences;
import com.example.administrator.myeasydemo.user.login.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的页面
 */

public class MeFragment extends Fragment {

    private View view;
    private ActivityUtils activityUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, container, false);
            ButterKnife.bind(this, view);
            activityUtils = new ActivityUtils(this);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // TODO: 2017/2/14 0014 判断是否登录显示图像和用户名
    }

    @OnClick({R.id.iv_user_head, R.id.tv_person_info, R.id.tv_login, R.id.tv_person_goods, R.id.tv_goods_upload})
    public void onClick(View view) {
        //未登录时跳转到登录界面
        if (CachePreferences.getUser().getUsername() == null) {
            activityUtils.startActivity(LoginActivity.class);
            return;
        }
        switch (view.getId()){
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
