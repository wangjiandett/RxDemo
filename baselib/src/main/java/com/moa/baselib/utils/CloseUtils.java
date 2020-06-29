package com.moa.baselib.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭流
 *
 * Created by：wangjian on 2018/12/20 14:24
 */
public class CloseUtils {
    
    private CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 关闭IO, 先打开的先关闭，后打开的后关闭
     * 另一种情况：看依赖关系，如果流a依赖流b，应该先关闭流a，再关闭流b
     * https://blog.csdn.net/shijinupc/article/details/7191655
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
