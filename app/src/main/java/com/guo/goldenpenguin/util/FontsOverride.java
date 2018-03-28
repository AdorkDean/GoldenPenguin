package com.guo.goldenpenguin.util;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 *  字体反射库
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/7 下午3:58
 */
public final class FontsOverride {

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    /**
     * 替换系统字体
     * @param staticTypefaceFieldName
     * @param newTypeface
     */
    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
