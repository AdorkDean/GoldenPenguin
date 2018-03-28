package com.guo.goldenpenguin.logic;


import com.guo.goldenpenguin.beans.UserInfoBean;
import com.guo.goldenpenguin.config.UserConfiguration;

/**
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 16 50
 */
public class UserLogic {

    /**
     * 返回当前已经登录的用户信息
     *
     * @Description:
     * @return
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2014-12-29
     */
    public static UserInfoBean.DataBean getDefaultUserInfo() {
        UserInfoBean.DataBean userInfo = UserConfiguration.getConfiguration()
                .getLocalUserInfo();
        if (userInfo != null) {
            return userInfo;
        }
        return null;
    }



    /**
     * 保存用户信息
     *
     * @Description:
     * @param userInfo
     * @return
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2014-12-29
     */
    public static void saveUserInfo(UserInfoBean.DataBean userInfo) {
        UserConfiguration.getConfiguration().saveUserConfig(userInfo);
    }



    /**
     * 修改用户信息
     *
     * @Description:
     * @param key
     * @param value
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2014-12-29
     */
    public static void updateUserInfo(String key, String value) {
        UserConfiguration.getConfiguration().updateUserInfo(key, value);
    }

    /**
     * 清空用户信息
     *
     * @Description:
     * @see:
     * @since:
     * @author: ZhongGaoYong
     * @date:2014-12-29
     */
    public static void clearUserInfo()
    {
        UserConfiguration.getConfiguration().clear();
    }
}
