/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moa.rxdemo.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;


/**
 * Log日志打印操作
 *
 * @author Weiss
 */
public class LogUtils {

    private static final boolean DEBUG = true;

    /**
     * 获取当前类名
     * @return
     */
    private static String getClassName() {
    
        String result = "LogUtils";
        
        // 这里的数组的index，即2，是根据你工具类的层级取的值，可根据需求改变
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        if(thisMethodStack != null){
            result = thisMethodStack.getClassName();
            if(!TextUtils.isEmpty(result)){
                int lastIndex = result.lastIndexOf(".");
                result = result.substring(lastIndex + 1, result.length());
            }
        }
    
        return result;
    }
    
    /**
     * 该参数需要根据实际情况来设置才能准确获取期望的调用信息，比如：
     * 在Java中，该参数应该为3
     * 在一般Android中，该参数为4
     * 你需要自己打印的看看，调用showAllElementsInfo()就可以了。
     */
    private static final int INDEX = 6;
    
    private static String getPrefix() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[INDEX];
        String className = stackTraceElement.getClassName();
        int classNameStartIndex = className.lastIndexOf(".") + 1;
        className = className.substring(classNameStartIndex);
        String methodName = stackTraceElement.getMethodName();
        int methodLine = stackTraceElement.getLineNumber();
        String format = "%s-%s(L:%d)";
        return String.format(Locale.CHINESE, format, className, methodName, methodLine);
    }
    
    public static String showAllElementsInfo() {
        String print = "";
        int count = 0;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            count++;
            print += String.format("ClassName:%s " +
                    "\nMethodName:%s " +
                    "\nMethodLine:%d " +
                    "\n当前是第%d个 " +
                    "\n---------------------------- " +
                    "\n ",
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                stackTraceElement.getLineNumber(),
                count);
        }
        return print;
    }
    
    
    
    public static void w(String logString) {
        if (DEBUG) {
            Log.w(getClassName(), logString);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String msg) {
        if (DEBUG) {
            Log.d(getClassName(), msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void i(String msg) {
        if (DEBUG) {
            Log.i(getClassName(), msg);
        }
    }
    /**
     * error log
     *
     * @param msg
     */
    public static void e(String msg) {
        if (DEBUG) {
            Log.e(getClassName(), msg);
        }
    }

    public static void i(String tag, String logString) {
        if (DEBUG) {
            Log.i(tag, logString);
        }
    }

    public static void w(String tag, String logString) {
        if (DEBUG) {
            Log.w(tag, logString);
        }
    }

}
