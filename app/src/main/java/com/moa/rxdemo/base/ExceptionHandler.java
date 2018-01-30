package com.moa.rxdemo.base;

import android.content.Context;

import com.moa.rxdemo.utils.DateUtil;
import com.moa.rxdemo.utils.FileUtil;
import com.moa.rxdemo.utils.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * 处理crash异常
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    
    public ExceptionHandler(Context context) {
        this.mContext = context;
    }
    
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        
        // 保存crash日志到文件中
        String dir = Files.getCrashDir();
        if (dir != null) {
            String time = DateUtil.getFullDateStr(System.currentTimeMillis());
            try {
                File crashFile = FileUtil.createFile(dir, time+".log");
                FileOutputStream e = new FileOutputStream(crashFile);
                PrintStream printStream = new PrintStream(e);
                ex.printStackTrace(printStream);
                printStream.close();
                
            } catch (FileNotFoundException var7) {
                var7.printStackTrace();
            }  catch (SecurityException var9) {
                var9.printStackTrace();
            }
        }
    }
}
