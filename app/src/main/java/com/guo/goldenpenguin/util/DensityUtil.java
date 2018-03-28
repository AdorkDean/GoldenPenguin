package com.guo.goldenpenguin.util;

import android.content.Context;

/**
 * dip px转换
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 17 11
 */
public class DensityUtil {

    /**
     * dip转换成Px
     *
     * @Description:
     * @param context
     * @param dipValue
     * @return
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-7-30
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转换成dp
     *
     * @Description:
     * @param context
     * @param pxValue
     * @return
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-7-30
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
