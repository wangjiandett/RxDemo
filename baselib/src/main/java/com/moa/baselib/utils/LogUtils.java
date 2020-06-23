package com.moa.baselib.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import com.moa.baselib.BaseApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志管理文件
 * <p>
 * Created by：wangjian on 2017/12/22 14:40
 */
public class LogUtils {

    private static final String TAG = "LogUtils";
    private static Locale mLogLocale = Locale.CHINA;

    private static final String WRITE_LOG_ENABLE = "write_log_enable";

    /**
     * 日志文件生成日期
     */
    private static SimpleDateFormat LOGFILE = new SimpleDateFormat("yyyyMMdd", mLogLocale);//HHmmss
    /**
     * 日志文件写入时间
     */
    private static SimpleDateFormat LOGTIME = new SimpleDateFormat("HH:mm:ss ", mLogLocale);

    /**
     * 默认可打印日志，后期根据是否release版本控制是否打印日志
     *
     * @return
     */
    public static boolean isLoggable() {
        return true;
    }

    /**
     * 是否可将日志写入文件
     *
     * @return
     */
    public static boolean isWriteLogsEnable() {
        return SharePreUtils.getBoolean(BaseApp.getAppContext(), WRITE_LOG_ENABLE, false);
    }

    /**
     * 日志写入文件开关
     *
     * @param writeLogsEnable 是否写入文件
     */
    public static void setWriteLogsEnable(boolean writeLogsEnable) {
        SharePreUtils.saveBoolean(BaseApp.getAppContext(), WRITE_LOG_ENABLE, writeLogsEnable);
    }


    /**
     * 拼接日志内容
     *
     * @param msg 日志内容
     * @return
     */
    private static String getLog(String msg) {
        return getFileLineMethod() + msg;
    }

    public static void v(String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.v(TAG, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(log, log);
            }
        }
    }

    public static void v(String tag, String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.v(tag, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(log, log);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.d(tag, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(log, log);
            }
        }

    }

    public static void d(String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.d(TAG, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(log, log);
            }
        }
    }

    public static void i(String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.i(TAG, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(log, log);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.i(tag, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(log, log);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.w(tag, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(log, log);
            }
        }
    }

    public static void w(String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.w(TAG, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(log, log);
            }
        }
    }


    public static void e(String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.e(TAG, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(TAG, log);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (isLoggable()) {
            String log = getLog(msg);
            Log.e(tag, log);

            if (isWriteLogsEnable()) {
                writeLogMessage(tag, log);
            }
        }
    }

    private static String getFileLineMethod() {
        StackTraceElement[] traceElements = ((new Exception()).getStackTrace());
        String method = "";
        if(traceElements.length > 4){
            StackTraceElement traceElement = traceElements[4];
            method = "[" +//
                    traceElement.getFileName() +//
                    " | " +//
                    traceElement.getLineNumber() +//
                    " | " +//
                    traceElement.getMethodName() +//
                    "] ";
        }

        return method;
    }

    public static void writeLogMessage(String tag, String msg) {
        writeLogMessage(null, tag, msg);
    }

    /**
     * 打开日志文件并写入日志，此方法会绕过日志开关直接写文件
     *
     * @param fileName 日志文件名
     * @param tag      tag
     * @param msg      日志信息
     */
    public static void writeLogMessage(String fileName, String tag, String msg) {
        Date date = new Date();
        String logDate = LOGFILE.format(date);
        String logMsg = //
                logDate + " "//
                        + LOGTIME.format(date)//
                        + "[" + AppUtils.getVersion(BaseApp.getAppContext()) + "] "//
                        + tag//
                        + " msg:\n\n"//
                        + msg;

        // 拼接文件名
        fileName = TextUtils.isEmpty(fileName) ? "log" : fileName + "-" + logDate + ".txt";

        // 写入文件
        FileUtil.writeMsg2File(logMsg, fileName, true);
    }

    /**
     * 保存手机信息到日志中
     * 此方法在app打开的时候写入一次即可
     */
    public static void writePhoneInfo2File() {

        String phoneInfo = "\n==============System Info==============\n" + "\n Product: " + Build.PRODUCT;
        phoneInfo += "\n CPU_ABI: " + Build.CPU_ABI;
        phoneInfo += "\n TAGS: " + Build.TAGS;
        phoneInfo += "\n VERSION_CODES.BASE: " + Build.VERSION_CODES.BASE;
        phoneInfo += "\n 手机型号: " + Build.MODEL;
        phoneInfo += "\n SDK版本: " + Build.VERSION.SDK;
        phoneInfo += "\n Android系统版本号: " + Build.VERSION.RELEASE;
        phoneInfo += "\n DEVICE: " + Build.DEVICE;
        phoneInfo += "\n DISPLAY: " + Build.DISPLAY;
        phoneInfo += "\n 手机厂商: " + Build.BRAND;
        phoneInfo += "\n BOARD: " + Build.BOARD;
        phoneInfo += "\n FINGERPRINT: " + Build.FINGERPRINT;
        phoneInfo += "\n ID: " + Build.ID;
        phoneInfo += "\n MANUFACTURER: " + Build.MANUFACTURER;
        phoneInfo += "\n USER: " + Build.USER;
        phoneInfo += "\n 手机当前系统语言: " + Locale.getDefault().getLanguage();


        FileUtil.writeMsg2File(phoneInfo, "system_info.txt", false);
        Log.d(TAG, phoneInfo);
    }


}
