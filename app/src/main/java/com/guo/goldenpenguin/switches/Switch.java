package com.guo.goldenpenguin.switches;

/**
 * @Description:
 * 开关
 * li.正式、测试下的URL
 * li.正式、测试下的常量
 * li.正式、测试下的log打印开关...
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 0:05
 */
public abstract class Switch {

	/***程序环境		(true 测试环境/false正式环境)*/
	public final static boolean ISTEST = false;
	
	/**是否进行发布  (true 发布状态  false 未发布状态)**/
	public final static Boolean ISRELEASE = true;

	/**程序日志开关**/
	public final static Boolean LOG_DEBUG =! ISRELEASE;
}
