package com.guo.goldenpenguin.switches;

import android.os.Environment;


import com.guo.goldenpenguin.App;

import java.io.File;

/**
 * 常量解析TODO 正式版、测试版
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 0:08
 */
public abstract class ConstantParse {

    //缓存根目录
    public final static File cacheRootDirectory = new File(getSDPath());

    //图片缓存目录
    public final static File imgCacheDirectory = new File(cacheRootDirectory, "image");

    //数据库缓存目录
    public final static File dbCacheDirectory = new File(cacheRootDirectory, "DB");


    //数据库版本号
    public final static int DB_VERSION = 1;


    /// 微博
    public static final String WeiboAppKey = "783799007";
    //微博回调页
    public static final String REDIRECT_URL = "http://shouji.baidu.com/software/22091896.html";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    public static final String WeiboAppSecret = "10ef2853dc807764e337edd41d2851a1";

    /// 微信
    public static final String WXAppKey = "wxbfc6f4833a1dfbff";
    public static final String WXSecret = "63997c81d874aa0c400df2f8ebc6f9a2";
    //支付密码：已设置
    public static final String WXPaySecret = "TCUgkySfdiB09yV6QSKFyWPZBVa0GtBY";

    public static final String WXPartnerId = "1359376802";
    public static final String WXPrepayId = "wx201607151953156c4ed2b3d50850088476";
    public static final String WXNoncestr = "c213253a6ebcbdcb2585588f1dd2e1c7";
    public static final String WXStamp = "1468589966";
    public static final String WXPrepayIdURL = "https://api.mch.icon_weixin_login.icon_qq_login.com/pay/unifiedorder";
    public static final String WXCallBackURL = "/catalog/controller/payment/weipay_callback.php";
    //支付宝
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String ALIPAYAPPID = "2016062801561922";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String ALIPAYPID = "2088421346283269";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String ALIPAYTARGET_ID = "elleshop@elleshop.com.cn";
    /**
     * 商户私钥，pkcs8格式
     */
    public static final String ALIPAYRSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK/1zhZeLLIPxX2el+wq/d5H/PQV15eIeelyPUTxkk/DDEknPkKzEWFE1eamuQ8woXMPjIzYlvEFGT8jFoyXSlfjBr3BA6ZXtRUmbErWNaTx9E3y0mQLrN6s6fJ9NvE1roIjJpvjUYv/QyRAnIKX7cIzIze7X4wFEmb5CQ3BG3gJAgMBAAECgYBrzoUizTZDLkiIq5dXGgwKmT6Q6aT6caKH9fc1+2/yMIWheEq8IOxAFSWcuH9foCL61Qb+8GCicMZEGaJiJvtjk2FG2yB+ezpu+ebxnPvdltnC2O4M8/wsOOXrwf+vSJfuNfX44KNPD/zP7ZiPiouD5er0r1+CjKnQQDA09k5sgQJBANRd74NKRzGLlGZUdnkOAJ3JQJQkuYjprcuELsGTIFZBzVeoGYNR+okAqpyx5tKcniwdv15PgeOdSxl34zQ1xDECQQDUHPLcLMxb1SO3HEA9Xo7LNHvSq6o0XFnZhdEl1Ez3RkGTVIomO+vy+u9YIf4YRQsaNoBMj7Lvy6VHz9GC/LNZAkBwwArJDuerk2rPxao87UVAgLhLrw/edAq00dItLhruEPOfxXz+Lskwce0jUoS1bze/Pm/694WDvmA//lnfv2gRAkB/8mZcgXlT+6FrltL5McyYrrPyRDKLGxnnqFycoQ88KVjs8hXTMloFW3B8jSSkpyEnUrDZfeTF2OV+B9GtXZkhAkAm/XHnhbv7MydkE/GsEn5AXo8lLrLQ9+tjOkhp2o6ejdlFsoiGBjILj4ce72f7C/eg2BlPiCcuZuUTrJwSC/67";


    //  腾讯
    public static final String TencentID = "1106280363";
    public static final String TencentKey = "LxmkDDMUwEvYfUgU";
    //public static final String QQCallBackURL="/catalog/controller/payment/weipay_callback.php"

    //  百度统计
    public static final String BaiduTongji = "71c0178152";

    //  Ucloud
    public static final String UcloudPrivateKey = "7d21df0d05a032e2f692eb885ebe6ce9b0bcd231";
    public static final String UcloudPublicKey = "IBzltC4eJn4nb/0VeJYFO3s1PP/gn/OuPItMUJ3XXnaQJxfMa6ctSw==";
    /**
     * bucket : bucket name
     * proxySuffix : 域名后缀
     */
    public static final String bucket = "elleshop";
    public static final String proxySuffix = ".ufilesec.ucloud.cn";


    //英文标题
    public static final String NEO_ENG_TITLE = "fonts/NeoSansStd-Bold.otf";
    //英文正文
    public static final String NEO_ENG_CONTNT = "fonts/NeoSansStd-Medium.otf";
    //中文标题
    public static final String NEO_CHINESE_TITLE = "fonts/PingFangBold.ttf";
    //中文正文
    public static final String NEO_CHINESE_CONTNT = "fonts/PingFangMedium.ttf";


    /**
     * 获取缓存根目录
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        //存在SD卡则缓存到SD卡否则缓存到工程目录
        if (sdCardExist) {
            sdDir = new File(Environment.getExternalStorageDirectory(), "Android/" + App.getInstance().getPackageName() + "Cache");//获取跟目录
        } else {

            sdDir = new File(App.getInstance().getCacheDir(), "Cache");
        }
        return sdDir.toString();
    }
}
