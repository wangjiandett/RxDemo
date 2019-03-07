package com.moa.baselib.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 运行权限检测类
 * <p>
 * <p>
 * Created by：wangjian on 2018/4/16 13:38
 */
public class PermissionHelper {
    
    
    /**
     * 打开App时检测并请求常用权限
     * 1.通讯录权限
     * 2.sdcard权限
     *
     * @return 是否获得全部权限
     */
    public static boolean checkNormalAllPermissions(Fragment fragment, Activity context, int requestCode){
       return checkAndRequestPermissions(fragment, context, requestCode, new String[]{
           Manifest.permission.READ_CONTACTS,// 联系人
           Manifest.permission.READ_EXTERNAL_STORAGE,// sdcard
           Manifest.permission.WRITE_EXTERNAL_STORAGE,
           Manifest.permission.READ_PHONE_STATE,// 电话状态
           Manifest.permission.RECORD_AUDIO// 录音
       });
    }
    
    /**
     * 录音权限
     *
     * @return 是否获得全部权限
     */
    public static boolean checkRecordAudioPermissions(Fragment fragment, Activity context, int requestCode){
        return checkAndRequestPermissions(fragment, context, requestCode, new String[]{
            Manifest.permission.VIBRATE,// 震动
            Manifest.permission.RECORD_AUDIO// 录音
        });
    }
    
    /**
     * 检测并请求通讯录权限
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @return 是否有定位权限
     */
    public static boolean checkContactsPermission(Fragment fragment, Activity context, int requestCode) {
        return checkAndRequestPermission(fragment, context, requestCode, Manifest.permission.READ_CONTACTS);
    }
    
    /**
     * 是否有Contacts权限
     *
     * @param context
     * @return
     */
    public static boolean hasContactsPermission(Context context) {
        return hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    
    /**
     * 检测并请求CAMERA权限
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @return 是否有Camera权限
     */
    public static boolean checkCameraPermission(Fragment fragment, Activity context, int requestCode) {
        return checkAndRequestPermission(fragment, context, requestCode, Manifest.permission.CAMERA);
    }
    
    /**
     * 是否有CAMERA权限
     *
     * @param context
     * @return
     */
    public static boolean hasCameraPermission(Context context) {
        return hasPermissions(context, Manifest.permission.CAMERA);
    }
    
    /**
     * 检测并请求sdcard权限
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @return 是否有sdcard访问权限
     */
    public static boolean checkSDcardPermission(Fragment fragment, Activity context, int requestCode) {
        return checkAndRequestPermission(fragment, context, requestCode, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    
    /**
     * 是否有sdcard权限
     *
     * @param context
     * @return
     */
    public static boolean hasSDcardPermission(Context context) {
        return hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    
    /**
     * 检测并请求定位权限
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @return
     */
    public static boolean checkLocationPermission(Fragment fragment, Activity context, int requestCode) {
        return checkAndRequestPermission(fragment, context, requestCode, Manifest.permission.ACCESS_FINE_LOCATION);
    }
    
    /**
     * 是否有定位权限
     *
     * @param context
     * @return
     */
    public static boolean hasLocationPermission(Context context) {
        return hasPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION);
    }
    
    /**
     * 检测并请求打电话权限
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @return 是否有打电话权限
     */
    public static boolean checkCallPhonePermission(Fragment fragment, Activity context, int requestCode) {
        return checkAndRequestPermission(fragment, context, requestCode, Manifest.permission.CALL_PHONE);
    }
    
    /**
     * 检测并请求电话状态权限
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @return 是否有电话状态权限
     */
    public static boolean checkPhoneStatePermission(Fragment fragment, Activity context, int requestCode) {
        return checkAndRequestPermission(fragment, context, requestCode, Manifest.permission.READ_PHONE_STATE);
    }
    
    /**
     * 检测并请求录音权限
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @return 是否有电话录音权限
     */
    public static boolean checkRecordAudioPermission(Fragment fragment, Activity context, int requestCode) {
        return checkAndRequestPermission(fragment, context, requestCode, Manifest.permission.RECORD_AUDIO);
    }
    
    /**
     * 检测并请求震动权限
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @return 是否有电话震动权限
     */
    public static boolean checkVibratePermission(Fragment fragment, Activity context, int requestCode) {
        return checkAndRequestPermission(fragment, context, requestCode, Manifest.permission.VIBRATE);
    }
    
    public static boolean containsAndAcceptPermission(String[] reqPermissions, String hasPermission, int[] grands){
        if(reqPermissions != null && reqPermissions.length > 0
            && grands != null && grands.length == reqPermissions.length){
            int index = -1;
            
            for(int i=0; i<reqPermissions.length; i++){
                if(TextUtils.equals(reqPermissions[i], hasPermission)){
                    index = i;
                    break;
                }
            }
            
            if(index >= 0 && grands.length > index){
                return vertifyPermission(grands[index]);
            }
        }
        
        return false;
    }
    
    /**
     * 请求指定权限（先去检查权限，在执行请求）
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @param permission
     * @return
     */
    public static boolean checkAndRequestPermission(Fragment fragment, Activity context, int requestCode, String permission) {
        boolean hasPermission = hasPermissions(context, permission);
        if (!hasPermission) {
            if (fragment != null) {
                fragment.requestPermissions(new String[]{permission}, requestCode);
            }
            else {
                ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
            }
        }
        
        return hasPermission;
    }
    
    /**
     * 请求指定权限（先去检查权限，在执行请求）
     *
     * @param fragment
     * @param context
     * @param requestCode
     * @param permissions
     * @return 是否获得全部权限
     */
    public static boolean checkAndRequestPermissions(Fragment fragment, Activity context, int requestCode, String[] permissions) {
        
        ArrayList<String> permsList = new ArrayList<>();
        
        for (int i = 0; i < permissions.length; i++) {
            if (!hasPermissions(context, permissions[i])) {
                permsList.add(permissions[i]);
            }
        }
        
        if (permsList.size() > 0) {
            String[] pers = permsList.toArray(new String[permsList.size()]);
            if (fragment != null) {
                fragment.requestPermissions(pers, requestCode);
            }
            else {
                ActivityCompat.requestPermissions(context, pers, requestCode);
            }
        }
        
        return permsList.size() <= 0;
    }
    
    /**
     * 判断是否获得所有授权
     *
     * @return
     */
    public static boolean vertifyPermission(int[] grantResults) {
        if (grantResults != null) {
            for (int result: grantResults){
                // 有一个权限没有获取到就返回false
                 if(!vertifyPermission(result)){
                     return false;
                 }
            }
            // 全部权限都获取到
            return true;
        }
        return false;
    }
    
    /**
     * 判断是否获得所有授权
     *
     * @return
     */
    public static boolean vertifyPermission(int grantResult) {
        if (grantResult != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
    
    /**
     * 判断是否拥有权限
     *
     * @param permission
     * @return
     */
    public static boolean hasPermissions(Context context, String permission) {
        
        boolean check;
        
        if (Build.VERSION.SDK_INT >= 23) {
            //判断是否拥有指定权限
            check = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }
        else {
            check = PermissionChecker.checkPermission(context, permission, Process.myPid(), Process.myUid(),
                context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        }
        
        return check;
    }
}
