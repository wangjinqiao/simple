package com.example.administrator.myeasydemo.main.me.personInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.commons.ActivityUtils;
import com.example.administrator.myeasydemo.components.AvatarLoadOptions;
import com.example.administrator.myeasydemo.components.PicWindow;
import com.example.administrator.myeasydemo.components.ProgressDialogFragment;
import com.example.administrator.myeasydemo.main.MainActivity;
import com.example.administrator.myeasydemo.main.NickNameActivity;
import com.example.administrator.myeasydemo.model.CachePreferences;
import com.example.administrator.myeasydemo.model.ItemShow;
import com.example.administrator.myeasydemo.model.User;
import com.example.administrator.myeasydemo.network.EasyShopApi;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonActivity extends MvpActivity<PersonView, PersonPersenter> implements PersonView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user_head)
    CircularImageView ivUserHead;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.btn_login_out)
    Button btnLoginOut;

    protected PicWindow picWindow;
    private ActivityUtils activityUtils;
    private ProgressDialogFragment progressDialogFragment;
    private List<ItemShow> list = new ArrayList<>();
    private PersonAdapter adapter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        unbinder = ButterKnife.bind(this);

        activityUtils = new ActivityUtils(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new PersonAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        //获取用户头像，显示图像
        updataAvatar(CachePreferences.getUser().getIconUrl());
    }

    @NonNull
    @Override
    public PersonPersenter createPresenter() {
        return new PersonPersenter();
    }

    //方便修改完昵称，回来更改数据
    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        init();//数据初始化
        adapter.notifyDataSetChanged();
    }

    //数据初始化
    private void init() {
        User.UserInfo user = CachePreferences.getUser();
        list.add(new ItemShow(getResources().getString(R.string.username), user.getUsername()));
        list.add(new ItemShow(getResources().getString(R.string.nickname), user.getNickname()));
        list.add(new ItemShow(getResources().getString(R.string.hx_id), user.getHx_id()));
    }

    //返回键设置
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //子条目点击事件
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                //用户名
                case 0:
                    activityUtils.showToast(getResources().getString(R.string.username_update));
                    break;
                //昵称
                case 1:
                    activityUtils.startActivity(NickNameActivity.class);
                    break;
                //环信ID
                case 2:
                    activityUtils.showToast(getResources().getString(R.string.id_update));
                    break;
            }
        }
    };


    @Override
    public void showPrb() {
        if (progressDialogFragment == null) progressDialogFragment = new ProgressDialogFragment();
        if (progressDialogFragment.isVisible()) return;
        progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog_fragment");
    }

    @Override
    public void hidePrb() {
        progressDialogFragment.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }

    //上传图像后加载图像
    @Override
    public void updataAvatar(String url) {
        //  头像加载操作
        ImageLoader.getInstance()
                .displayImage(EasyShopApi.IMAGE_URL + url, ivUserHead,
                        AvatarLoadOptions.build());
    }

    //裁剪图像的监听
    private PicWindow.Listener listener = new PicWindow.Listener() {
        @Override
        public void toGallery() {
            //清空缓存文件
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            //跳转
            Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent, CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        }
    };

    CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            //拿到图片文件
            File file = new File(uri.getPath());
            //上传图片文件通过业务类
            getPresenter().updataAvatar(file);
        }

        @Override
        public void onCropCancel() {

        }

        @Override
        public void onCropFailed(String message) {

        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 400;
            cropParams.aspectY = 400;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return PersonActivity.this;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
    }

    @OnClick({R.id.iv_user_head, R.id.btn_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            //点击头像
            case R.id.iv_user_head:
                // 头像来源选择（相册，拍照）
                if (picWindow == null) {
                    picWindow = new PicWindow(this, listener);
                }
                if (picWindow.isShowing()) {
                    picWindow.dismiss();
                    return;
                }
                picWindow.show();
                break;
            //点击退出登录
            case R.id.btn_login_out:
                //清空本地配置
                CachePreferences.clearAllData();
                //清除所有旧的Activity
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                // TODO: 2017/2/14 0014 退出环信相关
                break;

        }
    }

    @Override
    protected void onDestroy() {
        if (cropHandler.getCropParams() != null)
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);

        unbinder.unbind();
        super.onDestroy();
    }
}
