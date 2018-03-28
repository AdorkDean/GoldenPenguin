package com.guo.goldenpenguin.protocol;



import com.guo.goldenpenguin.App;
import com.guo.goldenpenguin.beans.UserInfoBean;
import com.guo.goldenpenguin.logic.UserLogic;
import com.guo.goldenpenguin.switches.Switch;
import com.guo.goldenpenguin.util.Singlton;
import com.guo.goldenpenguin.util.StringUtil;
import com.guo.goldenpenguin.util.UATick;
import com.guo.goldenpenguin.util.XLog;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


/**
 * @Description: 网络请求封装类
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 13 54
 */

public class Request {
    public static final String headerKey ="header";
    public static final String LOG_PREFIX = "Net#";
    // 请求数据LOG标示
    public static final String LOG_REQUEST_PREFIX = LOG_PREFIX + "REQUEST=";
    // 返回数据LOG标示
    public static final String LOG_RESPONSE_PREFIX = LOG_PREFIX + "RESPONSE=";
    // 请求所使用的时间
    public static final String LOG_RESPONSE_TIME = LOG_PREFIX + "TIME";

    /**
     * 网络请求 类 单例模式
     *
     * @return
     */
    public static Request getInstance() {
        return Singlton.getInstance(Request.class);
    }


    /**
     * get方式请求、不需要缓存
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param response 网络请求回调
     */
    public void get(String url, Map<String, Object> params, OnHttpParseResponse response) {
        getRequest(false, HttpMethod.GET, url, params, null, response, false,false);
    }

    /**
     * get方式请求、不需要缓存
     *
     * @param url       请求地址
     * @param params    请求参数
     * @param fileParam 文件请求参数
     * @param response  网络请求回调
     */
    public void get(String url, Map<String, Object> params, Map<String, File> fileParam, OnHttpParseResponse response) {
        getRequest(false,HttpMethod.GET, url, params, fileParam, response, false,false);
    }

    /**
     * get方式请求、可选择是否需要缓存
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param response 网络请求回调
     */
    public void get(String url, Map<String, Object> params, OnHttpParseResponse response, Boolean isCache) {
        getRequest(false,HttpMethod.GET, url, params, null, response, isCache,false);
    }

    /**
     * post方式请求、可选择是否需要缓存
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param response 网络请求回调
     */
    public void post(String url, Map<String, Object> params, OnHttpParseResponse response) {
        getRequest(false,HttpMethod.POST, url, params, null, response, false,false);

    }

    /**
     * post方式请求、可选择是否需要缓存
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param isCache  是否需要缓存
     * @param response 网络请求回调
     */
    public void post(String url, Map<String, Object> params, OnHttpParseResponse response, Boolean isCache) {
        getRequest(false,HttpMethod.POST, url, params, null, response, isCache,false);
    }

    /**
     * postjson方式请求
     * @param url
     * @param params
     * @param response
     */
    public void postJson(boolean isNeedUserID, String url, Map<String, Object> params, OnHttpParseResponse response){
        getRequest(isNeedUserID,HttpMethod.POST, url, params, null, response, false,true);
    }
    /**
     * postjson方式请求、可选择是否需要缓存
     * @param url
     * @param params
     * @param response
     */
    public void postJson(String url, Map<String, Object> params, OnHttpParseResponse response, Boolean isCache){
        getRequest(false,HttpMethod.POST, url, params, null, response, isCache,true);
    }
    /**
     * 网络请求封装类
     *
     * @param method    请求方式
     * @param url       请求地址
     * @param params    参数
     * @param response1 回调
     * @param isCache   是否需要缓存
     * @param  isJsonRequest 是否为json格式请求
     * @throws NoSuchAlgorithmException
     */
    public void getRequest(boolean isNeedUserId, final HttpMethod method, final String url, final Map<String, Object> params, Map<String, File> fileParam,
                           final OnHttpParseResponse response1, Boolean isCache, boolean isJsonRequest) {
        UserInfoBean.DataBean userInfo= UserLogic.getDefaultUserInfo();
        //如果需要用户id
        if (isNeedUserId){
            if (userInfo==null){
//                LoginActivity.forwardLoginActivity(App.getInstance());
                response1.onErrorResponse(CException.LOGIN_OUT_OF_DATE, new CException(
                        CException.NET_ERROR).getMessage());
                return ;
            }else{
                int MemberId=userInfo.getMemberID();
                params.put("MemberID",MemberId+"");
            }

        }
            if (userInfo!=null){
                if (!method.equals(HttpMethod.GET)){
                    String Session=userInfo.getSessionKey();
                    if (!StringUtil.isEmpty(Session)) {
                        params.put("SessionKey", Session);
                    }
                }

            }

        RequestParams netParams = new RequestParams(url);
        // 获取网络请求所需要的时间
        final UATick tick = new UATick(true);
        // 获取网络请求所需要的时间
        // 打印请求log
            netParams.setAsJsonContent(isJsonRequest);
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    //判断key是否是header
                    if (key.contains(headerKey)) {
                        netParams.addHeader(key.substring(headerKey.length()), (String)value);
                    } else {
                        Object obj=entry.getValue();
                        if (obj instanceof String){
                            netParams.addBodyParameter(entry.getKey(),(String) obj);
                        }else if (obj instanceof File){
                            netParams.addBodyParameter(entry.getKey(),(File) obj,null);
                        }

                    }
                }
            }
            if (Switch.LOG_DEBUG) {
                StringBuilder netParamsLog = new StringBuilder();
                for (KeyValue keyValue : netParams.getStringParams()) {
                    netParamsLog.append("&");
                    netParamsLog.append(keyValue.key);
                    netParamsLog.append("=");
                    netParamsLog.append(keyValue.value);
                }
                XLog.d(LOG_REQUEST_PREFIX, url + netParamsLog.toString());
            }
//        else{
//            netParams.setAsJsonContent(true);
//            String jsonData= hashMapToJson(params);
//            if (Switch.LOG_DEBUG) {
//                XLog.d(LOG_REQUEST_PREFIX, url + jsonData);
//            }
//            netParams.setBodyContent(jsonData);
//        }
        x.http().request(method, netParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                XLog.d(LOG_RESPONSE_PREFIX, result.toString());
                tick.end(LOG_RESPONSE_TIME);
                response1.onSuccessResponse(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //自定义异常
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    tick.end(LOG_RESPONSE_TIME);
                    XLog.d(LOG_RESPONSE_PREFIX, "code=" + responseCode + " msg=" + ex.toString());
                    response1.onErrorResponse(responseCode, new CException(
                            responseCode).getMessage());
                }else if (ex instanceof ConnectException) {
                    //无网络
                    response1.onErrorResponse(CException.NET_ERROR, new CException(
                            CException.NET_ERROR).getMessage());
                }else if(ex instanceof UnknownHostException){
                    //无网络
                    response1.onErrorResponse(CException.NET_ERROR, new CException(
                            CException.NET_ERROR).getMessage());
                }else {
                    response1.onErrorResponse(CException.JSONISNULLERROR, new CException(
                            CException.JSONISNULLERROR).getMessage());
                }

            }

            @Override
            public void onCancelled(CancelledException cex) {
                tick.end(LOG_RESPONSE_TIME);
            }

            @Override
            public void onFinished() {
                tick.end(LOG_RESPONSE_TIME);
            }
        });

    }
    /**
     * 网络请求回调
     *
     * @Description:
     * @see:
     * @since:
     * @author:ZhongGaoYong
     * @copyright www.elleshop.com
     * @Date:2016/11/6 下午2:08
     */
    public interface OnHttpParseResponse<T extends Object> {

        public  void  onSuccessResponse(T response);

        public void onErrorResponse(int code, String error);
    }
}
