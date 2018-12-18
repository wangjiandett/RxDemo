package com.moa.rxdemo.utils;

import android.content.Context;

import com.moa.rxdemo.MyApplication;
import com.moa.rxdemo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 格式化时间
 *
 * @author wangjian
 * @create 2018/12/18
 */
public class DateFormatting {
    
    private static ThreadLocal<SimpleDateFormat> FULL_FORMATTER = new ThreadLocal<>();
    private static ThreadLocal<DateFormat> TIME_FORMATTER = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> DATE_YEAR_FORMATTER = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> DATE_FORMATTER = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> MONTH_FORMATTER = new ThreadLocal<>();
    private static ThreadLocal<Calendar> CALENDAR = new ThreadLocal<>();
    
    /**
     * 全格式化样式如：2018-12-18 11:11:41
     *
     * @param time 毫秒值
     * @return
     */
    public static String formatFull(long time) {
        return getFullFormatter().format(new Date(time));
    }
    
    public static String formatTime(long time) {
        return getTimeFormatter().format(new Date(time));
    }
    
    public static String formatDate(long date) {
        String month = getMonthFormatter().format(date).toUpperCase();
        Calendar calendar = getCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(date);
        
        if (calendar.get(Calendar.YEAR) == currentYear) {
            return String.format(getDateFormatter().format(date), month);
        }
        else {
            return String.format(getDateYearFormatter().format(date), month);
        }
    }
    
    /**
     * 2个时间是否同一天
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean areSameDays(long a, long b) {
        Calendar calendar = getCalendar();
        calendar.setTimeInMillis(a);
        int y1 = calendar.get(Calendar.YEAR);
        int m1 = calendar.get(Calendar.MONTH);
        int d1 = calendar.get(Calendar.DATE);
        calendar.setTimeInMillis(b);
        int y2 = calendar.get(Calendar.YEAR);
        int m2 = calendar.get(Calendar.MONTH);
        int d2 = calendar.get(Calendar.DATE);
        
        return y1 == y2 && m1 == m2 && d1 == d2;
    }
    
    /**
     * 格式化当前显示的时间
     *
     * @param context
     * @param time
     * @return
     */
    public static String formatShowDate(Context context, long time) {
        // 年月日时分秒
        String ymdHM = context.getString(R.string.date_format_y_m_d_H_M);
        // 月日
        String MONTH_AND_DAY = context.getString(R.string.date_format_m_d);
        // 时分
        String HOUR_AND_MINUTE = context.getString(R.string.date_format_H_M);
        
        if (time == Long.MAX_VALUE) {
            return context.getString(R.string.date_format_y_m_d_H_M);
        }
        SimpleDateFormat sf = new SimpleDateFormat(ymdHM);
        long differTime = new Date().getTime() - time;
        
        long d = differTime / (1000 * 60 * 60 * 24);// 天
        if (d <= -2) {// 具体时间
            sf = new SimpleDateFormat(ymdHM);
            return sf.format(time);
        }
        else if (d == -1) {// 明天
            // sf = new SimpleDateFormat(ymdHM);
            return context.getString(R.string.date_format_torr);
        }
        else if (d == 0) {// 今天
            sf = new SimpleDateFormat(HOUR_AND_MINUTE);
            return sf.format(new Date(time));
        }
        else if (d == 1) {// 昨天
            return context.getString(R.string.date_format_yest);
        }
        else {// 具体时间
            sf = new SimpleDateFormat(MONTH_AND_DAY);
            return sf.format(time);
        }
    }
    
    private static DateFormat getTimeFormatter() {
        DateFormat dateFormat = TIME_FORMATTER.get();
        if (dateFormat == null) {
            dateFormat = android.text.format.DateFormat.getTimeFormat(MyApplication.getContext());
            TIME_FORMATTER.set(dateFormat);
        }
        return dateFormat;
    }
    
    private static Calendar getCalendar() {
        Calendar calendar = CALENDAR.get();
        if (calendar == null) {
            calendar = Calendar.getInstance();
            CALENDAR.set(calendar);
        }
        return calendar;
    }
    
    private static SimpleDateFormat getFullFormatter() {
        SimpleDateFormat res = FULL_FORMATTER.get();
        if (res == null) {
            res = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            FULL_FORMATTER.set(res);
        }
        return res;
    }
    
    private static SimpleDateFormat getDateYearFormatter() {
        SimpleDateFormat res = DATE_YEAR_FORMATTER.get();
        if (res == null) {
            res = new SimpleDateFormat("dd '%s' ''yy");
            DATE_YEAR_FORMATTER.set(res);
        }
        return res;
    }
    
    private static SimpleDateFormat getMonthFormatter() {
        SimpleDateFormat res = MONTH_FORMATTER.get();
        if (res == null) {
            res = new SimpleDateFormat("MMMM");
            MONTH_FORMATTER.set(res);
        }
        return res;
    }
    
    private static SimpleDateFormat getDateFormatter() {
        SimpleDateFormat res = DATE_FORMATTER.get();
        if (res == null) {
            res = new SimpleDateFormat("dd '%s'");
            DATE_FORMATTER.set(res);
        }
        return res;
    }
}
