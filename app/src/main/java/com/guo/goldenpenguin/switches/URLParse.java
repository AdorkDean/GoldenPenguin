package com.guo.goldenpenguin.switches;

/**
 * HTTP URL
 *
 * @Description:
 * @author:ZhongGaoYong
 * @see:
 * @since:
 * @copyright www.wozhongla.com
 * @Date:2015-7-1
 */
public abstract class URLParse {


    public static String getHost() {
        //测试服务器
        if (Switch.ISTEST) {
            return "http://59.110.48.164:9011";
        }//正式服务器
        else {
            return "http://59.110.48.164:9001";
        }

    }

    /**
     * 请求地址IP
     *
     * @return
     */
    public static String getUrlPath() {
        return getHost() ;
    }



}
