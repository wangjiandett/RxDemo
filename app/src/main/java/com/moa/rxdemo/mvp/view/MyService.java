package com.moa.rxdemo.mvp.view;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/1/24 16:47
 */
public class MyService extends Service {
    
    Messenger serviceMessenger = null;
    Messenger clientMessenger = null;
    
    @Override
    public void onCreate() {
        super.onCreate();
        serviceMessenger = new Messenger(serviceHandler);
    }
    
    private Handler serviceHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
    
            String msgs = (String) msg.getData().get("msg");
            Log.d("eeee", "msgs="+msgs);
            
            
            clientMessenger = msg.replyTo;
            
            Message message = Message.obtain();
            message.what = 1;
            message.obj = "service received message";
    
            try {
                clientMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
    
            return false;
        }
    }) ;
    
    
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceMessenger.getBinder();
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = "client unbind service";
    
        try {
            clientMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return super.onUnbind(intent);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        clientMessenger = null;
    }
}
