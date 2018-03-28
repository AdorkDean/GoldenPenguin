package com.guo.goldenpenguin.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * @author Tienfook
 * @version 2011-8-19 下午04:14:48 Toast提示工具类
 */
public class ToastUtil {

	/**
	 * 长时间
	 * 
	 * @param context
	 * @param message
	 */
	public static void longToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间 控制位置
	 * 
	 * @param context
	 * @param message
	 */
	public static void longToast(Context context, String message, int xOffset, int yOffset) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, xOffset, yOffset);
		toast.show();
	}

	/**
	 * 长时间 中间位置显示
	 * 
	 * @param context
	 * @param message
	 */
	public static void longToastCenter(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, new Toast(context).getXOffset() / 2, new Toast(context).getYOffset() / 2);
		toast.show();
	}

	/**
	 * 短时间
	 *
	 * @param context
	 * @param message
	 */
	public static void shortToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void shortToast(Context context, int message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间 控制位置
	 * 
	 * @param context
	 * @param message
	 */
	public static void shortToast(Context context, String message, int xOffset, int yOffset) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, xOffset, yOffset);
		toast.show();
	}

	/**
	 * 短时间 中间位置显示
	 * 
	 * @param context
	 * @param message
	 */
	public static void shortToastCenter(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, new Toast(context).getXOffset() / 2, new Toast(context).getYOffset() / 2);
		toast.show();
	}


}
