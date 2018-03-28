package com.guo.goldenpenguin.logic;


import android.os.Handler;

import com.guo.goldenpenguin.protocol.Protocol;
import com.guo.goldenpenguin.util.Singlton;


/**
 * 核心业务类
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 16 49
 */
public class CoreController {


    /**
     * 核心业务类
     * 单例模式
     *
     * @return
     */
    public static CoreController getInstance() {
        return Singlton.getInstance(CoreController.class);
    }


}