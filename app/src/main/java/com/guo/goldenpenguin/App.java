package com.guo.goldenpenguin;

import android.support.multidex.MultiDexApplication;



/**
 * @Description:
 * @see:
 * @since:
 * @author:GuoBaoJun
 * @copyright www.elleshop.
 * @Date:2016/11/5 23:57
 */

public class App extends MultiDexApplication{

    /** application 全局实例 **/
    private static App mApp;

    public static App getInstance() {
        return mApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
    }
}



