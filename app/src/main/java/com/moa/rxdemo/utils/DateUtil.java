package com.moa.rxdemo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/1/5 17:01
 */
public class DateUtil {
    
    private static final String FULL_PATTERN = "yyyyMMddHHmmss";
    private static Locale LOCALE = Locale.CHINESE;
    
    public static String getFullDateStr(long date) {
        DateFormat formatter = new SimpleDateFormat(FULL_PATTERN, LOCALE);
        return formatter.format(new Date(date));
    }
    
}
