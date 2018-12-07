package com.moa.rxdemo.utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {
    
    private CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
