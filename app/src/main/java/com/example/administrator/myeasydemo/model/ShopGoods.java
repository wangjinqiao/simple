package com.example.administrator.myeasydemo.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class ShopGoods {

    /**
     * code : 1
     * msg :  success
     * datas : [{"price":"66","name":"单车","description":"......","page":"/images/D3228118230A43C0B77/5606FF8A48F1FC4907D/F99E38F09A.JPEG","type":"other","uuid":"5606FF8EF60146A48F1FCDC34144907D","master":"android"}]
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<GoodsInfo> getDatas() {
        return datas;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDatas(List<GoodsInfo> datas) {
        this.datas = datas;
    }

    private List<GoodsInfo> datas;

    public static class GoodsInfo {
        /**
         * price : 66
         * name : 单车
         * description : ......
         * page : /images/D3228118230A43C0B77/5606FF8A48F1FC4907D/F99E38F09A.JPEG
         * type : other
         * uuid : 5606FF8EF60146A48F1FCDC34144907D
         * master : android
         */

        private String price;
        private String name;
        private String description;
        private String page;
        private String type;
        private String uuid;
        private String master;

        public String getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getPage() {
            return page;
        }

        public String getType() {
            return type;
        }

        public String getUuid() {
            return uuid;
        }

        public String getMaster() {
            return master;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public void setMaster(String master) {
            this.master = master;
        }
    }
}
