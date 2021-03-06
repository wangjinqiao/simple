package com.example.administrator.myeasydemo.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.commons.ActivityUtils;
import com.example.administrator.myeasydemo.main.me.MeFragment;
import com.example.administrator.myeasydemo.main.shop.ShopFragment;
import com.example.administrator.myeasydemo.main.shop.UnLoginFragment;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainActivity extends AppCompatActivity {

    @BindViews({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    TextView[] textViews;

    @BindView(R.id.main_toobar)
    Toolbar toolbar;
    @BindView(R.id.main_title)
    TextView tv_title;
    @BindView(R.id.viewpager)
    ViewPager viewPager;


    private ActivityUtils activityUtils;
    //解除绑定
    Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        //初始化视图
        init();
    }

    //初始化视图
    private void init() {
//        未登录时
        viewPager.setAdapter(unLoginAdapter);

        //刚进来默认选择市场
        textViews[0].setSelected(true);
        //viewPager添加滑动监听，用于控制TextView的展示
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //textView全部设为为选中
                for (TextView textView : textViews) {
                    textView.setSelected(false);
                }
                //更改title，设置选择效果
                tv_title.setText(textViews[position].getText());
                textViews[position].setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //未登录时的ViewPager适配器
    private FragmentStatePagerAdapter unLoginAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    //进入市场fragment
                    return new ShopFragment();
                case 1:
                    //进入未登录fragment
                    return new UnLoginFragment();
                case 2:
                    //进入未登录fragment
                    return new UnLoginFragment();
                case 3:
                    //进入我的fragment
                    return new MeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    //textview点击事件
    @OnClick({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    public void onClick(TextView view) {
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setSelected(false);
            textViews[i].setTag(i);
        }
        //设置选择效果
        view.setSelected(true);
        //参数false代表瞬间切换，而不是平滑过渡
        viewPager.setCurrentItem((Integer) view.getTag(), false);
        //设置一下toolbar的title 标题
        tv_title.setText(textViews[(Integer) view.getTag()].getText());
    }

    //点击2次返回，退出程序
    private boolean isExit = false;

    //点击两次返回退出程序
    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            activityUtils.showToast("再按一次退出程序");
            //两秒内再次点击返回则退出
            //如果两秒内，用户没有再次点击，则把isExit设置为false
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
