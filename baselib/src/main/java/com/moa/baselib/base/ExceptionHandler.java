package com.moa.baselib.base;

import android.content.Context;

import com.moa.baselib.BaseApp;
import com.moa.baselib.utils.DateFormatting;
import com.moa.baselib.utils.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * 处理程序崩溃时的异常
 *
 * @author wangjian
 * @create 2018/12/17
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    public static void handUncaughtException(Context context){
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(context));
    }

    private Context mContext;
    
    public ExceptionHandler(Context context) {
        this.mContext = context;
    }
    
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        
        // 保存crash日志到文件中
        String time = DateFormatting.formatFull(System.currentTimeMillis());
        try {
            File crashFile = Files.getLogFile("crash-" + time + ".txt");
            FileOutputStream e = new FileOutputStream(crashFile);
            PrintStream printStream = new PrintStream(e);
            ex.printStackTrace(printStream);
            printStream.close();
            
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        } catch (SecurityException var9) {
            var9.printStackTrace();
        } finally {
            // 异常退出时，清空所有activity
            BaseApp.finishAllActivity();
        }
    }
}
