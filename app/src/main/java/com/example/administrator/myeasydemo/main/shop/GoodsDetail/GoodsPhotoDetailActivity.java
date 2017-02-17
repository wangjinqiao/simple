package com.example.administrator.myeasydemo.main.shop.GoodsDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.components.AvatarLoadOptions;
import com.example.administrator.myeasydemo.network.EasyShopApi;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class GoodsPhotoDetailActivity extends AppCompatActivity {
    //用来拿图片地址的key
    private static final String IMAGES = "images";

    ViewPager viewpager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;


    public static Intent getStartIntent(Context context,
                                        ArrayList<String> imageUris) {
        Intent intent = new Intent(context, GoodsPhotoDetailActivity.class);
        intent.putExtra(IMAGES, imageUris);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_info);
        ButterKnife.bind(this);
        viewpager = (ViewPager) this.findViewById(R.id.viewpager_goods_photo);
        ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(IMAGES);
        GoogsDetailAdapter adapter = new GoogsDetailAdapter(getImage(imgUrls));

        adapter.setListener(new GoogsDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                finish();
            }
        });
        viewpager.setAdapter(adapter);
        indicator.setViewPager(viewpager);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    private ArrayList<ImageView> getImage(ArrayList<String> list) {
        ArrayList<ImageView> list_image = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            ImageLoader.getInstance()
                    .displayImage(EasyShopApi.IMAGE_URL + list.get(i),
                            imageView, AvatarLoadOptions.build_item());
            list_image.add(imageView);
        }
        return list_image;
    }
}
