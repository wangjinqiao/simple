package com.example.administrator.myeasydemo.main.me.uploadGoods;

import com.example.administrator.myeasydemo.commons.MyFileUtils;
import com.example.administrator.myeasydemo.model.ImageItem;
import com.example.administrator.myeasydemo.model.User;
import com.example.administrator.myeasydemo.network.CallBackUI;
import com.example.administrator.myeasydemo.network.HttpEasyShopClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/17 0017.
 */

public class UploadGoodsPresenter extends MvpNullObjectBasePresenter<UploadGoodsView> {
    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null) call.cancel();
    }

    //上传商品
    public void uploadMyGoods(MyGoodsToUpload myGoodsToUpload, ArrayList<ImageItem> imgs) {
        getView().showPgb();
        call = HttpEasyShopClient.getInstance().uploadGoods(myGoodsToUpload, getFile(imgs));
        call.enqueue(new CallBackUI() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePgb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String s) {
                getView().hidePgb();
                User user = new Gson().fromJson(s, User.class);
                getView().showMsg(user.getMsg());
                if (user.getCode() == 1) {
                    getView().uploadSuccess();
                }
            }
        });
    }

    //将ImageItem转化file文件
    private ArrayList<File> getFile(ArrayList<ImageItem> imgs) {
        ArrayList<File> files = new ArrayList<>();
        for (ImageItem img : imgs) {
            File file = new File(MyFileUtils.SD_PATH + img.getImagePath());
            files.add(file);
        }
        return files;
    }

}
