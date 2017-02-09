package com.example.administrator.myeasydemo.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.commons.ActivityUtils;
import com.example.administrator.myeasydemo.components.ProgressDialogFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText et_userName;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;
    private String username;
    private String password;

    //解除绑定
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         unbinder = ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);

        init();
    }

    private void init() {
        //给左上角加一个返回图标，需要重写菜单点击事件，否则点击无效
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //给EditText添加监听
        et_userName.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);
    }

    //给左上角加一个返回图标，需要重写菜单点击事件，否则点击无效
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //如果点击的是返回键，则finish
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //EditText监听
    private TextWatcher textWatcher = new TextWatcher() {
        //这里的s表示改变之前的内容，通常start和count组合，可以在s中读取本次改变字段中被改变的内容。
        //而after表示改变后新的内容的数量
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        //这里的s表示改变之后的内容，通常start和count组合，可以在s中读取本次改变字段中新的内容。
        //而before表示被改变的内容的数量。
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        //表示最终内容
        @Override
        public void afterTextChanged(Editable s) {
            username = et_userName.getText().toString();
            password = et_pwd.getText().toString();
            //判断用户名和密码是否为空
            boolean canLogin = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password));
            btn_login.setEnabled(canLogin);
        }
    };

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                activityUtils.showToast("执行登陆的网络请求");
                visitHttp();
                break;
            case R.id.tv_register:
                activityUtils.startActivity(RegisterActivity.class);
                break;
            default:
                break;
        }
    }
   // 执行登陆的网络请求
    private void visitHttp() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=login")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
