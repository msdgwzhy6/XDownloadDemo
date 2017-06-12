package com.xm.frame.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串判断
 * 作者：小民
 *
 */
public class StringUtils {
    private StringUtils(){}

    /**
     * 对字符串进行舍弃操作 -
     * @param val        要转换的值
     * @param newScale   保留位
     * @return           值
     */
    public static double toDoubleValue(double val,int newScale){
        String valueOf = String.valueOf(val);
        int position = valueOf.length() - valueOf.indexOf(".") - 1;      //? 为什么这么做。。因为你少于3位 去转，会出现 10.8 等于 10.799的bug
        if(position > newScale){
            BigDecimal bigDecimal = new BigDecimal(val);
            bigDecimal =  bigDecimal.setScale(newScale,BigDecimal.ROUND_DOWN);
            return bigDecimal.doubleValue();
        }else{
            return val;
        }
        /*
        setScale(2);//表示保留2位小数，默认用四舍五入方式
        setScale(2,BigDecimal.ROUND_DOWN);//直接删除多余的小数位  11.116约=11.11
        setScale(2,BigDecimal.ROUND_UP);//临近位非零，则直接进位；临近位为零，不进位。11.114约=11.12
        setScale(2,BigDecimal.ROUND_HALF_UP);//四舍五入 2.335约=2.33，2.3351约=2.34
        setScaler(2,BigDecimal.ROUND_HALF_DOWN);//四舍五入；2.335约=2.33，2.3351约=2.34，11.117约11.12
         */
    }
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
