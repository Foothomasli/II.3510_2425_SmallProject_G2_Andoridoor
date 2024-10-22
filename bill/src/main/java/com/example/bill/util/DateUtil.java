package com.example.bill.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * @author LQ
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public static String getNowDateTime(String formatStr) {
        String format = formatStr;
        if (TextUtils.isEmpty(format)) {
            format = "yyyyMMddHHmmss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }



    public static String getDate(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 将当前日期时间按照指定格式输出格式化后的日期时间字符串
        return sdf.format(date);
    }

    public static String getMonth(Calendar calendar) {
        Date date = calendar.getTime();
        // 创建一个日期格式化的工具
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 将当前日期时间按照指定格式输出格式化后的日期时间字符串
        return sdf.format(date);
    }

    public static Date formatString(String strTime) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(strTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
