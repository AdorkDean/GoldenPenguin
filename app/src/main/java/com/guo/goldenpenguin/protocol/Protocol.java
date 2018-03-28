package com.guo.goldenpenguin.protocol;



import com.alibaba.fastjson.JSON;
import com.guo.goldenpenguin.App;
import com.guo.goldenpenguin.beans.BaseJsonBeans;
import com.guo.goldenpenguin.util.Singlton;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 除网络图片外，所有的网络数据均来自此页面
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/5 23:52
 */
public class Protocol {

    //请求参数 String
    protected Map<String, Object> mMapParams = new HashMap<String, Object>();
    //上传文件
    protected Map<String, File> mFileMapParams = new HashMap<String, File>();

    /**
     * 网络请求 类 单例模式
     *
     * @return
     */
    public static Protocol getInstance() {
        return Singlton.getInstance(Protocol.class);
    }


    /**
     * 统一处理解析异常信息
     *
     * @param text
     * @param clazz
     * @param response
     * @param isCallGetData true 为返回 getData  false为返回本身
     * @param <T>
     * @return
     */
    <T> T parseObject(String text, Class<T> clazz, Request.OnHttpParseResponse response, Boolean isCallGetData) {

        T jsonBeans = null;
        try {
            jsonBeans= JSON.parseObject(text,clazz);
            //进行错误及正确的信息返回
            if (jsonBeans instanceof BaseJsonBeans) {
                if (response != null) {
                    int resultCode=((BaseJsonBeans) jsonBeans).getResult();
                    //获取数据成功
                    if (resultCode==0) {
                        if (isCallGetData) {
                            Class aClass = jsonBeans.getClass();
                            Method m1 = clazz.getDeclaredMethod("getData");
                            response.onSuccessResponse(m1.invoke(jsonBeans));
                        } else {
                            response.onSuccessResponse(jsonBeans);
                        }
                        //获取数据失败
                    }else if (resultCode==1){
                        if (response != null) {
                            response.onErrorResponse(resultCode, ((BaseJsonBeans) jsonBeans).getMessage());
                        }
                        //登录过期
                    }else if (resultCode==2){
//                        LoginActivity.forwardLoginActivity(App.getInstance());
                        CException cException = new CException(CException.LOGIN_OUT_OF_DATE);
                        response.onErrorResponse(cException.getCode(), cException.getMessage());
                    }
                }
            } else {
                //总的有返回w(ﾟДﾟ)w
                CException cException = new CException(CException.JSONFORWARTERROR);
                //错误信息回调
                if (response != null) {
                    response.onErrorResponse(cException.getCode(), cException.getMessage());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //为json格式错误
            CException cException = new CException(CException.JSONFORWARTERROR);
            //错误信息回调
            if (response != null) {
                response.onErrorResponse(cException.getCode(), cException.getMessage());
            }
        }
        return jsonBeans;
    }


}


