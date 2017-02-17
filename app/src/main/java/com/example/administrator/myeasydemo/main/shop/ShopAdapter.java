package com.example.administrator.myeasydemo.main.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myeasydemo.R;
import com.example.administrator.myeasydemo.components.AvatarLoadOptions;
import com.example.administrator.myeasydemo.model.ShopGoods;
import com.example.administrator.myeasydemo.network.EasyShopApi;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHoulder> {

    //数据
    private List<ShopGoods.GoodsInfo> mDatas = new ArrayList<>();
    private Context context;

    public ShopAdapter() {
    }

    public ShopAdapter(List<ShopGoods.GoodsInfo> mDatas) {
        this.mDatas = mDatas;
    }


    //添加数据
    public void addData(List<ShopGoods.GoodsInfo> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    //清空数据
    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public ShopViewHoulder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false);
        ShopViewHoulder houlder = new ShopViewHoulder(view);
        return houlder;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public void onBindViewHolder(ShopViewHoulder holder, final int position) {
        holder.tvItemName.setText(mDatas.get(position).getName());
        String price = context.getString(R.string.goods_money, mDatas.get(position).getPrice());
        holder.tvItemPrice.setText(price);
        ImageLoader.getInstance()
                .displayImage(EasyShopApi.IMAGE_URL + mDatas.get(position).getPage(),
                        holder.ivItemRecycler, AvatarLoadOptions.build_item());

        holder.ivItemRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mDatas.get(position));
                }
            }
        });
    }


    class ShopViewHoulder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_recycler)
        ImageView ivItemRecycler;
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_item_price)
        TextView tvItemPrice;

        public ShopViewHoulder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ShopGoods.GoodsInfo goodsInfo);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
