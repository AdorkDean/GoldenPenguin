package com.guo.goldenpenguin.beans;

import java.io.Serializable;

/**
 * 实体类父类，主要是为了提取json返回数据公共部分
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/8 20 22
 */
public class BaseJsonBeans implements Serializable {


    /**
     * Result : true
     * Message : 获取成功!
     */

    private int Result;
    private String Message;

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
