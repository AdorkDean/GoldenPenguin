package com.guo.goldenpenguin.protocol;


import com.guo.goldenpenguin.App;
import com.guo.goldenpenguin.R;
import com.guo.goldenpenguin.util.StringUtil;

/**
 * 异常捕获
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/5 23:53
 */

public class CException extends Exception {
    /**
     * 登录过期
     */
    public static final int LOGIN_OUT_OF_DATE=10000006;
    /**
     * 获取数据错误
     */
    public static final int GET_DATA_ERROR=1000005;

    /**
     * 无网络
     **/
    public static final int NET_ERROR = 10000;
    /**
     * IO异常
     **/
    public static final int IOERROR = 408;
    /**
     * JSON格式有误
     **/
    public static final int JSONFORWARTERROR = 10002;
    /**
     * JSON获取后为空
     **/
    public static final int JSONISNULLERROR = 10003;
    /**
     * JSON解析失败
     **/
    public static final int JSONPARSEERROR = 10004;

    public static final int JSONOBTAINSUCCESS = 10013;// JSON获取成功

    private static final long serialVersionUID = 1L;

    public static final int UNSPECIFIED_EXCEPTION = 0;

    public static final int NETWORK_REQUEST_FAILURE = -1;
    public static final int NETWORK_UNAVAILABLE = -2;
    public static final int NETWORK_FAILURE = -3;
    public static final int UBKNOWN_HOST = 998;
    public static final int TIME_OUT = 999;
    public static final int TEMPORARILY_MOVED = 302;
    public static final int ACCESS_DENIED = 403;
    public static final int SERVER_ERROR = 500;
    protected int exceptionType;

    public CException() {
    }

    public CException(int type) {
        exceptionType = type;
    }

    public CException(int type, String message) {
        super(message);
        exceptionType = type;
    }

    public CException(String message) {
        super(message);
    }

    public int getCode() {
        return exceptionType;
    }

    public String getError() {
        return "";
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        String messageError = super.getMessage();
        if (StringUtil.isEmpty(messageError)) {
            switch (exceptionType) {
                // 无网络
                case NET_ERROR:
                    messageError = App.getInstance().getString(R.string.exception_net_error);
                    break;
                // IO异常(包括超时等)
                case IOERROR:
                    messageError = App.getInstance().getString(R.string.exception_io_error);
                    break;
                // JSON格式有误
                case JSONFORWARTERROR:
                    // JSON为空
                case JSONISNULLERROR:
                    // JSON解析失败
                case JSONPARSEERROR:
                    messageError = App.getInstance().getString(R.string.exception_json_error);
                    break;
                case GET_DATA_ERROR:
                    messageError = App.getInstance().getString(R.string.exception_data_error);
                    break;
                case LOGIN_OUT_OF_DATE:
                    messageError = App.getInstance().getString(R.string.exception_login_outdata);
                    break;
                default:
                    messageError = App.getInstance().getString(R.string.exception_json_error);
                    break;
            }
        }
        return messageError;
    }

}
