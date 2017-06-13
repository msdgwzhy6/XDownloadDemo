package com.xm.simple.utils;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串判断
 * 作者：小民
 *
 */
public class StringUtils {
    private StringUtils(){}
    /** 字符串去尾号0处理 */
    public static String toNumberFormat(double value){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(value).replace(",","");
    }
    /** 字符串为空 */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() <= 0;
    }
    /** 字符串不为空 */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    /** 是否为数字 */
    public static boolean isNumber(String text) {
        if(isEmpty(text)){
            return false;
        }
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(text);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }
    /** 手机格式验证 */
    public static boolean isMobile(String mobiles){
        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    /** 判断邮编 */
    public static boolean isZipNO(String zipString){
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }
    /** 判断邮箱是否合法 */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
