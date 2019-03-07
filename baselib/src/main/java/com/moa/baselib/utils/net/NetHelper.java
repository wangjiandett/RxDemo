package com.moa.baselib.utils.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

import com.moa.baselib.BaseApp;
import com.moa.baselib.base.dispatcher.Runtimes;
import com.moa.baselib.utils.LogUtils;

/**
 * 监听网络变化
 *
 * @author wangjian
 * @create 2017/6/30
 */
public class NetHelper {
    
    private ConnectivityManager mConnectivityManager;
    private ConnectivityManager.NetworkCallback mNetworkCallback;
    private BroadcastReceiver netBroadcastReceiver;
    
    public static ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) BaseApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }
    
    public static boolean isNetConnected() {
        boolean isConnected = false;
        ConnectivityManager manager = getConnectivityManager();
        if (manager != null) {
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnected();
        }
        
        return isConnected;
    }
    
    /**
     * 网络变化监听
     */
    public void registerNetChangeReceiver(Context context, final NetCallback netCallback) {
        // 7.0以前使用callback方式监听网络
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mNetworkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    LogUtils.d("network onAvailable");
                    if (netCallback != null) {
                        Runtimes.postToMainThread(new Runnable() {
                            @Override
                            public void run() {
                                netCallback.isNetAvailable(true);
                            }
                        });
                    }
                }
                
                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    LogUtils.d("network onLost");
                    if (netCallback != null) {
                        Runtimes.postToMainThread(new Runnable() {
                            @Override
                            public void run() {
                                netCallback.isNetAvailable(false);
                            }
                        });
                    }
                }
            };
            
            mConnectivityManager = NetHelper.getConnectivityManager();
            if (mConnectivityManager != null) {
                mConnectivityManager.registerDefaultNetworkCallback(mNetworkCallback);
            }
        }
        else {
            // 7.0以下使用广播监听网络
            netBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent != null) {
                        // 网络改变时修改提示文字
                        if (TextUtils.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
                            boolean isConnected = isNetConnected();
                            LogUtils.d("Network isConnected:" + isConnected);
                            if (netCallback != null) {
                                netCallback.isNetAvailable(isConnected);
                            }
                        }
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(netBroadcastReceiver, intentFilter);
        }
    }
    
    public void unRegisterNetChangeReceiver(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (mConnectivityManager != null) {
                mConnectivityManager.unregisterNetworkCallback(mNetworkCallback);
            }
        }
        else {
            if (netBroadcastReceiver != null) {
                context.unregisterReceiver(netBroadcastReceiver);
            }
        }
    }
    
    public interface NetCallback {
        void isNetAvailable(boolean available);
    }
}
