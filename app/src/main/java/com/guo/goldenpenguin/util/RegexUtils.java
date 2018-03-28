package com.guo.goldenpenguin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:  验证工具类
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/221058
 */

public class RegexUtils {
    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile
     *            移动、联通、电信运营商的号码段
     *            <p>
     *            移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *            、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
     *            </p>
     *            <p>
     *            联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *            </p>
     *            <p>
     *            电信的号段：133、153、180（未启用）、189
     *            </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }

    /**
     * 验证Email
     *
     * @param email
     *            email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 昵称验证
     * @param nickName
     * @return
     */
    public static boolean checkNickName(String nickName){
        String regex="^[\\u4e00-\\u9fa5a-zA-Z0-9]+$";
        boolean isP= Pattern.matches(regex, nickName);
        if (isP){
            int countCh=0;
            int countNum=0;
            Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
            char c[] = nickName.toCharArray();
            for(int i=0;i<c.length;i++){
                Matcher matcher = pattern.matcher(String.valueOf(c[i]));
                if(matcher.matches()){
                    countCh++;
                }else{
                    countNum++;
                }
            }
            if (countCh>6||countNum>12) {return false;}else{
                return true;
            }

        }else{
            return isP;
        }


    }

    /**
     * 验证输入的名字是否为“中文”或者是否包含“·”
     */
    public static boolean isLegalName(String name){
        if (name.contains("·") || name.contains("?")){
            if (name.matches("^[\\u4e00-\\u9fa5]+[·?][\\u4e00-\\u9fa5]+$")){
                if( name.length()<=6) {
                    return true;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }else {
            if (name.matches("^[\\u4e00-\\u9fa5]+$")){
                if( name.length()<=6) {
                    return true;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }
    }


    /**
     * 验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id){
        if (id.toUpperCase().matches("(^\\d{17}([0-9]|X)$)")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 密码验证6-20 字母数字
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd){
        String regex = "^[a-zA-Z0-9]{6,20}$";
        return Pattern.matches(regex, pwd);
    }
}
