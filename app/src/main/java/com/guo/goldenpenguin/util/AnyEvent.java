package com.guo.goldenpenguin.util;

/**
 * EventBus 传递类
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/7 22 38
 */
public class AnyEvent {

    //消息来源 类型一 为发布消息的控件ID 类型二 如果发布消息并非控件点击，那么采用全局变量作为发布消息ID
    private int fromID;
    //消息体
    private Object discribe;


    //构造函数
    public AnyEvent(int fromId,Object discribe) {
        this.discribe = discribe;
        this.fromID=fromId;
    }

    public int getFromID() {
        return fromID;
    }

    public void setFromID(int fromID) {
        this.fromID = fromID;
    }

    public Object getDiscribe() {
        return discribe;
    }

    public void setDiscribe(Object discribe) {
        this.discribe = discribe;
    }
}
