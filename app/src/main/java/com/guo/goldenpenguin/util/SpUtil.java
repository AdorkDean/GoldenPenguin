package com.guo.goldenpenguin.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.guo.goldenpenguin.App;


/**
 * Created by zhonggaoyong on 2017/3/24.
 */

public class SpUtil {
    private static SharedPreferences hmSpref;
    private static SharedPreferences.Editor editor;
    private static SpUtil spUtil;
    private final String FLAG = "flag";

    private SpUtil() {
        hmSpref = App.getInstance().getSharedPreferences("hmSpref", Context.MODE_PRIVATE);
        editor = hmSpref.edit();
    }

    public static SpUtil getInstance() {
        if (spUtil == null) {
            synchronized (SpUtil.class) {
                if (spUtil == null) {
                    spUtil = new SpUtil();
                }
            }
        }
        return spUtil;
    }

    public void putFlag(boolean flag) {
        editor.putBoolean(FLAG, flag);
        editor.commit();
    }

    //第一次请求权限的时候，返回的flag是false
    public boolean getFlag() {
        return hmSpref.getBoolean(FLAG, false);
    }
}
