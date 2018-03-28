package com.guo.goldenpenguin.util;


/**
 * 可以用来计算某一任务花费的时间
 * @Description:
 * @author:ZhongGaoYong 
 * @see:   
 * @since:      
 * @copyright www.wozhongla.com
 * @Date:2015-9-9
 */
public class UATick {

	private long start = 0;
	private long end = 0;
	
	public UATick() {
		
	}
	public UATick(boolean autoBegin) {
		if(autoBegin) {
			begin();
		}
	}
	
	/**
	 * 开始计时
	 * @Description:
	 * @return  当前时间戳, 毫秒
	 * @see: 
	 * @since: 
	 * @author: ZhongGaoYong 
	 * @date:2015-9-9
	 */
	 
	public long begin() {
		start = System.currentTimeMillis();
		return start;
	}
	
	/**
	 * 停止计时
	 * @Description:
	 * @return 从开始到结束的时间, 单位毫秒
	 * @see: 
	 * @since: 
	 * @author: ZhongGaoYong 
	 * @date:2015-9-9
	 */
	 
	public long end() {
		end = System.currentTimeMillis();
		return end - start;
	}
	
	 
	 
	/**
	 * 结束计时
	 * @Description:
	 * @param prefix  打印信息
	 * @return 
	 * @see: 
	 * @since: 
	 * @author: ZhongGaoYong 
	 * @date:2015-9-9
	 */
	public long end(String prefix) {
		
		long tick = end();
		if(prefix !=null) {
			XLog.d("[TimeTick=" + prefix+"] ",tick+"ms");
		}
		return tick;
	}

	/**
	 * 返回开始和结束的时间差
	 * @Description:
	 * @return
	 * @see: 
	 * @since: 
	 * @author: yangentao
	 * @date:2015-9-9
	 */
	 
	public long getTick() {
		return end - start;
	}
	
	/**
	 * 开始计时
	 * @Description:
	 * @return 计时器
	 * @see: 
	 * @since: 
	 * @author: ZhongGaoYong 
	 * @date:2015-9-9
	 */
	public static UATick go() {
		UATick t = new UATick(true);
		return t;
	}
	
}
