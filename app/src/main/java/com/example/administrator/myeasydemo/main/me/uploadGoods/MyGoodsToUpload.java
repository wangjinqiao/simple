package com.example.administrator.myeasydemo.main.me.uploadGoods;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/2/17 0017.
 * 商品上传实体类
 */

public class MyGoodsToUpload {

    /**
     * description : 诚信商家，非诚勿扰
     * master : android
     * name : 礼物，鱼丸，鱼翅，火箭，飞机
     * price : 88
     * type : gift
     */

    private String description;
    private String master;
    @SerializedName("name")
    private String goodsName;
    private String price;
    private String type;

    public String getDescription() {
        return description;
    }

    public String getMaster() {
        return master;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }
}
