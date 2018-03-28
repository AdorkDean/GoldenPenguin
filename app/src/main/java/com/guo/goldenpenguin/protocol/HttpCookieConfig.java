package com.guo.goldenpenguin.protocol;

import android.content.Context;
import android.webkit.CookieManager;


import com.guo.goldenpenguin.App;
import com.guo.goldenpenguin.util.XLog;

import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 操作HTTP请求过来的COOKIE TODO 暂时没有对cookie过期进行处理
 * 
 * @Description:
 * @author:ZhongGaoYong
 * @see:
 * @since:
 * @copyright www.wozhongla.com
 * @Date:2015-2-4
 */
public class HttpCookieConfig extends PreferenceUtil {

	// 文件名
	private static final String PREFERENCE_NAME = "httpcookie_config";

	public HttpCookieConfig(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private static HttpCookieConfig instance;

	public static HttpCookieConfig getInstance() {
		if (instance == null) {
			instance = new HttpCookieConfig(PREFERENCE_NAME);
		}
		return instance;
	}

	/**
	 * 获取HttpURLConnection传递的cookie
	 * 
	 * @Description:
	 * @param connection
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-1-23
	 */
	public void getCookie(HttpURLConnection connection) {
		if (connection != null) {
			Map<String, List<String>> heads = connection.getHeaderFields();
			if (heads != null) {
				List<String> cookie = heads.get("Set-Cookie");
				XLog.d("#NET getCookie cookie count-->"+(cookie==null?0:cookie.size()));
				if (cookie != null)
					for (int i = 0; i < cookie.size(); i++) {
						String cookieStr = cookie.get(i).indexOf(";") > 0 ? cookie.get(i).substring(0, cookie.get(i).indexOf(";")) : cookie.get(i);
						String cookieKey = cookieStr.substring(0, cookieStr.lastIndexOf("="));
						String cookieValue = cookieStr.substring(cookieStr.lastIndexOf("=") + 1);
						putString(cookieKey, cookieValue);
						XLog.d(cookieStr);
					}
			}
		}
	}

	/**
	 * set cookie to httprulconnection
	 * 
	 * @Description:
	 * @param mCookieMap
	 * @see:
	 * @since:
	 * @author:www.wozhongla.com
	 * @date:2015-1-23
	 */
	@SuppressWarnings("unchecked")
	public void setCookie(HttpURLConnection connection) {

		Map<String, ?> allData = getAll();
		if (connection != null && allData != null) {
			StringBuilder builder = new StringBuilder();
			if (allData != null) {
				Iterator<?> iterator = allData.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, ?> entry = (Entry<String, ?>) iterator.next();
					builder.append(entry.getKey()).append("=").append(entry.getValue()).append(";");

				}
			}
			if (builder.length() > 0) {
				builder.delete(builder.length() - 1, builder.length());
			}
			if (builder.length() > 0) {
				connection.setRequestProperty("Cookie", builder.toString());
			}
		}
	}
	/**
	 * 清空cookie
	 * @Description:
	 * @see: 
	 * @since: 
	 * @author:www.wozhongla.com
	 * @date:2015-2-6
	 */
	public void clearCookie(){
		clear();
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return App.getInstance();
	}

	/**
	 * 获取指定key的cookie值
	 * @Description:
	 * @param key
	 * @return
	 * @see: 
	 * @since: 
	 * @author:www.wozhongla.com
	 * @date:2016-2-16
	 */
	public String getCookie(String key){
		return getString(key, null);
	}
	/**
	 * 将COOKIE保存到CookieManager、方便webView调用
	 * @Description:
	 * @param url
	 * @param cookieManager
	 * @see: 
	 * @since: 
	 * @author:www.wozhongla.com
	 * @date:2016-2-16
	 */
	public void setUserCookieToWeb(String url, CookieManager cookieManager){
		StringBuilder builder;
		Map<String, ?> allData = getAll();
		if (allData != null) {
			if (allData != null) {
				Iterator<?> iterator = allData.entrySet().iterator();
				while (iterator.hasNext()) {
					builder = new StringBuilder();
					Entry<String, ?> entry = (Entry<String, ?>) iterator.next();
					builder.append(entry.getKey()).append("=").append(entry.getValue());
					if (entry.getKey().contains("JSESSIONID")) {
//						builder.append("!-52623857");
					}
					builder.append("; ");
					cookieManager.setCookie(url, builder.toString());
				}
			}
		}
	}
}
