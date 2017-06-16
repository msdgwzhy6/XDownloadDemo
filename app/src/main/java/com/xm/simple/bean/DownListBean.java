package com.xm.simple.bean;

import java.util.List;

/**
 * @author: 小民
 * @date: 2017-06-05
 * @time: 16:26
 * @开源地址: https://github.com/2745329043/XDownloadDemo
 * @说明:
 */
public class DownListBean {
    private List<BannerBean> banner;
    private List<ListBean> list;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class BannerBean {
        /**
         * id : 00
         * large_logo_category_url : http://p0.qhimg.com/t01a0c12c7f6a6d46cd.jpg
         * url : http://openbox.mobilem.360.cn/html/2017/events/0602/yichewang.html?webpg=yiche0602
         * isactivity : 1
         * soft_order : 1
         * is_big_banner : 1
         */

        private String id;
        private String large_logo_category_url;
        private String url;
        private String isactivity;
        private String soft_order;
        private String is_big_banner;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLarge_logo_category_url() {
            return large_logo_category_url;
        }

        public void setLarge_logo_category_url(String large_logo_category_url) {
            this.large_logo_category_url = large_logo_category_url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIsactivity() {
            return isactivity;
        }

        public void setIsactivity(String isactivity) {
            this.isactivity = isactivity;
        }

        public String getSoft_order() {
            return soft_order;
        }

        public void setSoft_order(String soft_order) {
            this.soft_order = soft_order;
        }

        public String getIs_big_banner() {
            return is_big_banner;
        }

        public void setIs_big_banner(String is_big_banner) {
            this.is_big_banner = is_big_banner;
        }
    }

    public static class ListBean {
        /**
         * market_id : 360market
         * market_name : 360手机助手
         * apk_md5 : d16afc04d5890c69c7df3c4bbc719bad
         * type : soft
         * needapkdata : 0
         * apkid : com.qihoo360.mobilesafe
         * down_url : http://hot.m.shouji.360tpcdn.com/170602/d16afc04d5890c69c7df3c4bbc719bad/com.qihoo360.mobilesafe_252.apk
         * download_times : 1731342572
         * id : 77208
         * logo_url : http://p0.qhimg.com/t0168f384a0b6a971c2.png
         * name : 360手机卫士-一键连免费wifi
         * rating : 8.8
         * size : 16398367
         * version_code : 252
         * version_name : 7.7.1
         * baike_name : 360手机卫士
         * box_label : 3
         * os_version : 15
         * signature_md5 : 9fae2c537de3bc929109e320f80f10fd
         * is_ad : 1
         * is_push_ad : 0
         * is_inscreen_ad : 0
         * is_offerwall : 0
         * category_level1_ids : 1,3,5,10,101984
         * category_level2_ids : 11,108,101591,101606,101608,101619,101642,101648,101653,101712,101714,101716,101758,101760,101796,101986,102209
         * soft_order :
         * soft_large_logo_url :
         * cid2 : 11
         * cid : 1
         * tag : 系统安全
         * tag2 : 系统
         * logo_url_160 : http://p0.qhimg.com/dr/160_160_/t010a9112f18d6947ed.png
         * single_word : 微信红包，快人一秒
         * short_word : 用户量庞大的安全软件
         * brief : 用户量庞大的安全软件
         * size_fixed : 15.64
         * download_times_fixed : 173134万次
         * rating_fixed : 88
         * micro_type : 1
         * micro_url : http://m.iqiyi.com/?showTitleBar=0µwebview=1&pname=com.qiyi.video
         * is_gift : 1
         * gift_description : 看头条，领200元现金红包
         * gift_useage : 1-200元现金红包，随时随地可从微信钱包提前，100%中奖。
         * single_img : http://p1.qhimg.com/t01430e63bf11a54b60.png
         * contentInfo : {"content_type":0,"image_url":"http://p0.qhimg.com/dc/300_187_100_0_0/t01ff5c88f537279381.jpg","title":"复旦老教授工资条惊呆众人","jump_url":"","des":"为何工资不低到手却很少"}
         */

        private String market_id;
        private String market_name;
        private String apk_md5;
        private String type;
        private String needapkdata;
        private String apkid;
        private String down_url;
        private String download_times;
        private String id;
        private String logo_url;
        private String name;
        private String rating;
        private String size;
        private String version_code;
        private String version_name;
        private String baike_name;
        private String box_label;
        private String os_version;
        private String signature_md5;
        private String is_ad;
        private String is_push_ad;
        private String is_inscreen_ad;
        private String is_offerwall;
        private String category_level1_ids;
        private String category_level2_ids;
        private String soft_order;
        private String soft_large_logo_url;
        private String cid2;
        private int cid;
        private String tag;
        private String tag2;
        private String logo_url_160;
        private String single_word;
        private String short_word;
        private String brief;
        private double size_fixed;
        private String download_times_fixed;
        private int rating_fixed;
        private String micro_type;
        private String micro_url;
        private int is_gift;
        private String gift_description;
        private String gift_useage;
        private String single_img;
        private ContentInfoBean contentInfo;

        public String getMarket_id() {
            return market_id;
        }

        public void setMarket_id(String market_id) {
            this.market_id = market_id;
        }

        public String getMarket_name() {
            return market_name;
        }

        public void setMarket_name(String market_name) {
            this.market_name = market_name;
        }

        public String getApk_md5() {
            return apk_md5;
        }

        public void setApk_md5(String apk_md5) {
            this.apk_md5 = apk_md5;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNeedapkdata() {
            return needapkdata;
        }

        public void setNeedapkdata(String needapkdata) {
            this.needapkdata = needapkdata;
        }

        public String getApkid() {
            return apkid;
        }

        public void setApkid(String apkid) {
            this.apkid = apkid;
        }

        public String getDown_url() {
            return down_url;
        }

        public void setDown_url(String down_url) {
            this.down_url = down_url;
        }

        public String getDownload_times() {
            return download_times;
        }

        public void setDownload_times(String download_times) {
            this.download_times = download_times;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getBaike_name() {
            return baike_name;
        }

        public void setBaike_name(String baike_name) {
            this.baike_name = baike_name;
        }

        public String getBox_label() {
            return box_label;
        }

        public void setBox_label(String box_label) {
            this.box_label = box_label;
        }

        public String getOs_version() {
            return os_version;
        }

        public void setOs_version(String os_version) {
            this.os_version = os_version;
        }

        public String getSignature_md5() {
            return signature_md5;
        }

        public void setSignature_md5(String signature_md5) {
            this.signature_md5 = signature_md5;
        }

        public String getIs_ad() {
            return is_ad;
        }

        public void setIs_ad(String is_ad) {
            this.is_ad = is_ad;
        }

        public String getIs_push_ad() {
            return is_push_ad;
        }

        public void setIs_push_ad(String is_push_ad) {
            this.is_push_ad = is_push_ad;
        }

        public String getIs_inscreen_ad() {
            return is_inscreen_ad;
        }

        public void setIs_inscreen_ad(String is_inscreen_ad) {
            this.is_inscreen_ad = is_inscreen_ad;
        }

        public String getIs_offerwall() {
            return is_offerwall;
        }

        public void setIs_offerwall(String is_offerwall) {
            this.is_offerwall = is_offerwall;
        }

        public String getCategory_level1_ids() {
            return category_level1_ids;
        }

        public void setCategory_level1_ids(String category_level1_ids) {
            this.category_level1_ids = category_level1_ids;
        }

        public String getCategory_level2_ids() {
            return category_level2_ids;
        }

        public void setCategory_level2_ids(String category_level2_ids) {
            this.category_level2_ids = category_level2_ids;
        }

        public String getSoft_order() {
            return soft_order;
        }

        public void setSoft_order(String soft_order) {
            this.soft_order = soft_order;
        }

        public String getSoft_large_logo_url() {
            return soft_large_logo_url;
        }

        public void setSoft_large_logo_url(String soft_large_logo_url) {
            this.soft_large_logo_url = soft_large_logo_url;
        }

        public String getCid2() {
            return cid2;
        }

        public void setCid2(String cid2) {
            this.cid2 = cid2;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTag2() {
            return tag2;
        }

        public void setTag2(String tag2) {
            this.tag2 = tag2;
        }

        public String getLogo_url_160() {
            return logo_url_160;
        }

        public void setLogo_url_160(String logo_url_160) {
            this.logo_url_160 = logo_url_160;
        }

        public String getSingle_word() {
            return single_word;
        }

        public void setSingle_word(String single_word) {
            this.single_word = single_word;
        }

        public String getShort_word() {
            return short_word;
        }

        public void setShort_word(String short_word) {
            this.short_word = short_word;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public double getSize_fixed() {
            return size_fixed;
        }

        public void setSize_fixed(double size_fixed) {
            this.size_fixed = size_fixed;
        }

        public String getDownload_times_fixed() {
            return download_times_fixed;
        }

        public void setDownload_times_fixed(String download_times_fixed) {
            this.download_times_fixed = download_times_fixed;
        }

        public int getRating_fixed() {
            return rating_fixed;
        }

        public void setRating_fixed(int rating_fixed) {
            this.rating_fixed = rating_fixed;
        }

        public String getMicro_type() {
            return micro_type;
        }

        public void setMicro_type(String micro_type) {
            this.micro_type = micro_type;
        }

        public String getMicro_url() {
            return micro_url;
        }

        public void setMicro_url(String micro_url) {
            this.micro_url = micro_url;
        }

        public int getIs_gift() {
            return is_gift;
        }

        public void setIs_gift(int is_gift) {
            this.is_gift = is_gift;
        }

        public String getGift_description() {
            return gift_description;
        }

        public void setGift_description(String gift_description) {
            this.gift_description = gift_description;
        }

        public String getGift_useage() {
            return gift_useage;
        }

        public void setGift_useage(String gift_useage) {
            this.gift_useage = gift_useage;
        }

        public String getSingle_img() {
            return single_img;
        }

        public void setSingle_img(String single_img) {
            this.single_img = single_img;
        }

        public ContentInfoBean getContentInfo() {
            return contentInfo;
        }

        public void setContentInfo(ContentInfoBean contentInfo) {
            this.contentInfo = contentInfo;
        }

        public static class ContentInfoBean {
            /**
             * content_type : 0
             * image_url : http://p0.qhimg.com/dc/300_187_100_0_0/t01ff5c88f537279381.jpg
             * title : 复旦老教授工资条惊呆众人
             * jump_url :
             * des : 为何工资不低到手却很少
             */

            private int content_type;
            private String image_url;
            private String title;
            private String jump_url;
            private String des;

            public int getContent_type() {
                return content_type;
            }

            public void setContent_type(int content_type) {
                this.content_type = content_type;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getJump_url() {
                return jump_url;
            }

            public void setJump_url(String jump_url) {
                this.jump_url = jump_url;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }
        }
    }
}
