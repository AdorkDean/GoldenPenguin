package com.guo.goldenpenguin.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * 常用工具类
 *
 * @Description:
 * @author:ZhongGaoYong
 * @see:
 * @since:
 * @copyright www.wozhongla.com
 * @Date:2013-7-30
 */
public class GlobalTools {

    /**
     * Unknown network class
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;

    /**
     * wifi net work
     */
    public static final int NETWORK_WIFI = 1;

    /**
     * "2G" networks
     */
    public static final int NETWORK_CLASS_2_G = 2;

    /**
     * "3G" networks
     */
    public static final int NETWORK_CLASS_3_G = 3;

    /**
     * "4G" networks
     */
    public static final int NETWORK_CLASS_4_G = 4;

    /**
     * 使用32位MD5加密算法进行加密
     *
     * @param text 要加密的字符串
     * @return 加密后字符串
     * @Description:
     * @see:
     * @since:
     * @author: 黄永兴 (ZhongGaoYong@35.cn)
     * @date:2012-2-24
     */
    public static String md5Encrypt(String text) {
        // 空串就不用加密了
        if (text == null) {
            return text;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            char[] charArr = text.toCharArray();
            byte[] byteArr = new byte[charArr.length];
            for (int i = 0; i < charArr.length; i++) {
                byteArr[i] = (byte) charArr[i];
            }
            return StringUtil.bytes2HexString(md5.digest(byteArr));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * dip转换成Px
     *
     * @param context
     * @param dipValue
     * @return
     * @Description:
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
     * 判断软键盘是否弹出
     */
    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }
    /**
     * 隐藏软键盘
     *
     * @param context
     * @return
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-7-30
     */
    public static boolean hideSoftInput(Activity context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null) {
                return inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 切换输入法显示隐藏状态
     *
     * @param context
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-7-30
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-7-30
     */
    public static void openSoftInput(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏输入法
     *
     * @param context
     * @param binder  输入法所在控件的token
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-7-30
     */
    public static void hideSoftInput(Context context, IBinder binder) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, 0);
    }

    /**
     * px转换成dp
     *
     * @param context
     * @param pxValue
     * @return
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-7-30
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 存储屏幕高宽的数组
     */
    private static int[] screenSize = null;

    /**
     * 获取屏幕高宽
     *
     * @param activity
     * @return 屏幕宽高的数组 [0]宽， [1]高
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-7-30
     */
    @SuppressWarnings("deprecation")
    public static int[] getScreenSize(Activity activity) {
        if (screenSize == null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            screenSize = new int[2];
            screenSize[0] = display.getWidth();
            screenSize[1] = display.getHeight();
        }
        return screenSize;
    }

    /**
     * 清除List内容，并置为null
     *
     * @param list
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-8-2
     */
    public static void clearList(Collection<?> list) {
        if (list != null) {
            list.clear();
            list = null;
        }
    }

    /**
     * 关闭cursor
     *
     * @param cursor
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-8-2
     */
    public static void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * 取两个集合的并集
     *
     * @param c1
     * @param c2
     * @return
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2013-8-9
     */
    public static Collection<String> mixedList(Collection<String> c1, Collection<String> c2) {
        // 定义两个空的集合，分别存放最大和最小的集合，用来取交集
        Collection<String> tmpBig = new ArrayList<String>();
        Collection<String> tmpSmall = new ArrayList<String>();
        // 为最大和最小集合赋值
        if (c1.size() > c2.size()) {
            tmpBig.addAll(c1);
            tmpSmall.addAll(c2);
        } else {
            tmpBig.addAll(c2);
            tmpSmall.addAll(c1);
        }
        tmpBig.retainAll(tmpSmall);
        tmpSmall = null;
        return tmpBig;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static float sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    /**
     * 得到listview的高度
     *
     * @param listView
     * @return
     */
    public static void getLitsViewHeight(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            //mView.measure(0, 0);
            totalHeight += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        if (height != height) {
            params.height = height;
            listView.setLayoutParams(params);
//            listView.requestLayout();
        }
    }

    /**
     * listview垂直滚动距离
     * @param listView
     * @param activity
     * @param y
     */
    public static void scrollVertical(final ListView listView, Activity activity, final int y){
        if(listView == null)
            return;
        activity.runOnUiThread(new Runnable() { //执行自动化测试的时候模拟滑动需要进入UI线程操作
            @Override
            public void run() {
                invokeMethod(listView, "trackMotionScroll", new Object[]{-y, -y}, new Class[]{int.class, int.class});
            }
        });
    }

    /**
     * 遍历当前类以及父类去查找方法
     * @param object
     * @param methodName
     * @param params
     * @param paramTypes
     * @return
     */
    public static Object invokeMethod(Object object, String methodName, Object[] params, Class[] paramTypes){
        Object returnObj = null;
        if (object == null) {
            return null;
        }
        Class cls = object.getClass();
        Method method = null;
        for (; cls != Object.class; cls = cls.getSuperclass()) { //因为取的是父类的默认修饰符的方法，所以需要循环找到该方法
            try {
                method = cls.getDeclaredMethod(methodName, paramTypes);
                break;
            } catch (NoSuchMethodException e) {
//					e.printStackTrace();
            } catch (SecurityException e) {
//					e.printStackTrace();
            }
        }
        if(method != null){
            method.setAccessible(true);
            try {
                returnObj = method.invoke(object, params);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return returnObj;
    }
    /**
     * 动态设置gridview高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 2;
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight() + GlobalTools.dip2px(listView.getContext(), 5);
        }


        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(5, 5, 5, 5);
        // 设置参数
        listView.setLayoutParams(params);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    /**
     * 通过LayoutParams设置控件的宽高
     *
     * @param view     被设置的View
     * @param widthPx  宽度
     * @param heightPx 高度
     *                 FILL_PARENT = -1;
     *                 MATCH_PARENT = -1;
     *                 WRAP_CONTENT = -2;
     */
    public static void setViewWidth2HeightByLayoutParams(ImageView view, int widthPx, int heightPx) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = widthPx;
        params.height = heightPx;
        view.setLayoutParams(params);
    }

    /**
     * 设置view
     * @param view
     * @param widthPx
     * @param heightPx
     */
    public static void setViewWidth2HeightByLayoutParams(View view, int widthPx, int heightPx) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = widthPx;
        params.height = heightPx;
        view.setLayoutParams(params);
    }
    /**
     * bitmap转字节数组
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = null;
        try {
            output = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
            byte[] result = output.toByteArray();
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                output.close();
                bmp.recycle();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return null;
    }


    /**
     * 判断网络格式
     * @param context
     * @return
     */
    public static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;

            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;

            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }


    /**
     * 获取手机连接的网络类型（是WIFI还是手机网络[2G/3G/4G]）：
     * @param context
     * @return
     */
    public static int getNetWorkStatus(Context context) {
        int netWorkType = NETWORK_CLASS_UNKNOWN;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }

        return netWorkType;
    }


}
