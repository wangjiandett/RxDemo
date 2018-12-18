package com.moa.rxdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class SharePreUtils {
    
    private static final String SP_NAME = "config";
    private static SharedPreferences sp;
    
    private static void getSharedPreferences(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }
    
    public static void saveBoolean(Context context, String key, boolean value) {
        getSharedPreferences(context);
        sp.edit().putBoolean(key, value).apply();
    }
    
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        getSharedPreferences(context);
        return sp.getBoolean(key, defValue);
    }
    
    public static void saveString(Context context, String key, String value) {
        getSharedPreferences(context);
        sp.edit().putString(key, value).apply();
    }
    
    public static String getString(Context context, String key, String defValue) {
        getSharedPreferences(context);
        return sp.getString(key, defValue);
    }
    
    public static void saveLong(Context context, String key, long value) {
        getSharedPreferences(context);
        sp.edit().putLong(key, value).apply();
    }
    
    public static long getLong(Context context, String key, long defValue) {
        getSharedPreferences(context);
        return sp.getLong(key, defValue);
    }
    
    public static void saveInt(Context context, String key, int value) {
        getSharedPreferences(context);
        sp.edit().putInt(key, value).apply();
    }
    
    public static int getInt(Context context, String key, int defValue) {
        getSharedPreferences(context);
        return sp.getInt(key, defValue);
    }
    
    /**
     * 针对复杂类型存储<对象>
     */
    public static void setObject(Context context, String key, Object object) {
        getSharedPreferences(context);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.apply();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static <T> T getObject(Context context, String key) {
        getSharedPreferences(context);
        
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            if (!TextUtils.isEmpty(objectVal)) {
                byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(bais);
                    T t = (T) ois.readObject();
                    return t;
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bais.close();
                        if (ois != null) {
                            ois.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
    
}
