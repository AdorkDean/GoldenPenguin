package com.guo.goldenpenguin.protocol.Xutils;



import com.guo.goldenpenguin.protocol.HttpsSocketFactory;
import com.guo.goldenpenguin.protocol.Request;
import com.guo.goldenpenguin.util.XLog;

import org.xutils.http.RequestParams;

import java.io.File;
import java.util.Map;


/**
 * 自己封装网络请求的参数类、包括封装SSL keys等通用数据
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 16 28
 */
public class HttpsRequestParams extends RequestParams {

    public static final String headerKey ="header";

    public HttpsRequestParams(String url) {

        super(url);
        //https
        this.setSslSocketFactory(HttpsSocketFactory.getSSLSocketFactory());
    }

    public HttpsRequestParams(String url, Map<String, String> params, Map<String, File> fileParams) {

        super(url);
        //如果登录了，那么所有的参数加上用户表示  就这样吧，虽然有点坑
//        UserInfoBean.UserInfo userInfo = UserLogic.getDefaultUserInfo();
//        if (userInfo != null && userInfo.getInfo() != null) {
//            //用户ID
//            this.addQueryStringParameter("customer_id", userInfo.getInfo().getCustomer_id());
//            this.addQueryStringParameter("mobile_token", userInfo.getInfo().getMobile_token());
//
//        }

        //拼装参数
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {

                String key=entry.getKey();
                String value=entry.getValue();
                //判断key是否是header
                if(key.contains(headerKey)){
                    this.addHeader(key.substring(headerKey.length()),value);
                }else {
                    this.addBodyParameter(entry.getKey(), entry.getValue());
                }
            }
        }

        //拼接文件参数
        if (fileParams != null && !fileParams.isEmpty()) {
            for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                String key = entry.getKey();
                File value = entry.getValue();
                this.addBodyParameter("up_image", value, "image/jpg");
                XLog.d(Request.LOG_REQUEST_PREFIX, key + "=" + value.getAbsolutePath() +" isExists="+value.exists());


            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
