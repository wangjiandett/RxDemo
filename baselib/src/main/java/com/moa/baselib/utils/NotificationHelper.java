package com.moa.baselib.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.moa.baselib.BaseApp;

import static java.lang.System.currentTimeMillis;

/**
 * 通知栏管理适配
 * <p>
 * Created by：wangjian on 2018/9/26 15:46
 */
public class NotificationHelper {
    
    public static NotificationCompat.Builder getNotificationBuilder(Intent intent, int notifId, int logoRes) {
        Context context = BaseApp.getAppContext();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
            String.valueOf(notifId));
        
        PendingIntent contentIntent = PendingIntent.getActivity(context, notifId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT);
        
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), logoRes);
        
        builder.setWhen(currentTimeMillis());
        // 状态小图标
        builder.setSmallIcon(logoRes);
        // 显示的大图标
        builder.setLargeIcon(bitmap);
        //builder.setTicker(tickerText);
        //builder.setContentTitle(TApplication.getAppContext().getString(R.string.foreground_notice_head_title));
        //builder.setContentText(tickerText);
        
        //int defaults = NotificationCompat.FLAG_NO_CLEAR;
        // defaults |=NotificationCompat.FLAG_ONGOING_EVENT;
        //builder.setDefaults(defaults);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setContentIntent(contentIntent);
        
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setCategory(NotificationCompat.CATEGORY_CALL);
        //builder.setColor(TApplication.getInstance().getResources().getColor(R.color.color_1298ff));
        // 兼容26，否则通知不显示
        builder.setChannelId(context.getPackageName());
        return builder;
    }
    
    public static void notifyNotice(Notification notification, int notifId, String channelName) {
        NotificationManager notificationManager = (NotificationManager) BaseApp.getAppContext().getSystemService(
            Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            // 8.0添加channel，否则通知不显示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(BaseApp.getAppContext().getPackageName(),
                    channelName, NotificationManager.IMPORTANCE_DEFAULT);
                channel.setSound(null, null);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(notifId, notification);
        }
    }
    
    public static void cancelNotification(int notifId) {
        NotificationManager notificationManager = (NotificationManager) BaseApp.getAppContext().getSystemService(
            Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(notifId);
        }
    }
    
    public static void cancelAllNotification() {
        NotificationManager notificationManager = (NotificationManager) BaseApp.getAppContext().getSystemService(
            Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }
}
