package com.guo.goldenpenguin.config;

import android.content.Context;

import com.guo.goldenpenguin.App;
import com.guo.goldenpenguin.beans.UserInfoBean;
import com.guo.goldenpenguin.util.PreferenceUtil;


/**
 * Account 本地存储用户内容
 * 
 * @Description:
 * @author:ZhongGaoYong
 * @see:
 * @since:
 * @copyright © www.wozhongla.com
 * @Date:2014-11-30
 */
public class UserConfiguration extends PreferenceUtil {


	// 文件名
	private static final String PREFERENCE_NAME = "common_config";
	// 用户信息类
	private UserInfoBean.DataBean mUserInfo;

	public UserConfiguration(String name) {
		super(PREFERENCE_NAME);
		mUserInfo = new UserInfoBean.DataBean();
		// TODO Auto-generated constructor stub
	}

	private static UserConfiguration mConfiguration;

	/**
	 * 单例初始化account配置文件
	 * 
	 * @Description:
	 * @return
	 * @see:
	 * @since:
	 * @author: ZhongGaoYong
	 * @date:2014-12-29
	 */
	public static UserConfiguration getConfiguration() {

		if (mConfiguration == null) {

			mConfiguration = new UserConfiguration(PREFERENCE_NAME);
		}
		return mConfiguration;
	}

	/**
	 * 获取当前已经存在的用户名密码、及其他用户信息
	 * 
	 * @Description:
	 * @return
	 * @see:
	 * @since:
	 * @author: ZhongGaoYong
	 * @date:2014-12-29
	 */
	public UserInfoBean.DataBean getLocalUserInfo() {
		return mUserInfo = getUserByConfig();
	}

	private UserInfoBean.DataBean getUserByConfig() {
		UserInfoBean.DataBean userInfo = (UserInfoBean.DataBean) readObject(App.getInstance(), "userInfo");
		return userInfo;
	}

	/**
	 * 保存account元素
	 * 
	 * @Description:
	 * @param userInfo
	 * @see:
	 * @since:
	 * @author: ZhongGaoYong
	 * @date:2014-12-29
	 */
	public void saveUserConfig(UserInfoBean.DataBean userInfo) {
		saveObject(App.getInstance(), "userInfo", userInfo);
	}

	/**
	 *
	 *
	 *
	 * 修改某一元素值
	 * 
	 * @Description:
	 * @param key
	 * @param value
	 * @see:
	 * @since:
	 * @author: ZhongGaoYong
	 * @date:2014-12-29
	 */
	public void updateUserInfo(String key, String value) {
		putString(key, value);
	}

	/**
	 * 得到某一元素值
	 * 
	 * @Description:
	 * @param key
	 * @see:
	 * @since:
	 * @author: ZhongGaoYong
	 * @date:2014-12-29
	 */
	public String getStringValue(String key) {
		return getString(key, null);
	}

	/**
	 * 清空数据
	 */
	public void clear() {
		super.clear();
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return App.getInstance();
	}
}
