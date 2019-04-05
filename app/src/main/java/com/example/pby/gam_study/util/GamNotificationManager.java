package com.example.pby.gam_study.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.example.pby.gam_study.R;

import java.io.File;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class GamNotificationManager {

    private static GamNotificationManager mInstance;
    private Context mContext;

    public static GamNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new GamNotificationManager(context);
        }
        return mInstance;
    }

    private GamNotificationManager(Context context) {
        mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("chat", "聊天消息", NotificationManager.IMPORTANCE_DEFAULT);
            createNotificationChannel("update", "更新下载", NotificationManager.IMPORTANCE_HIGH);

        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    public void setMessageNotification(PendingIntent pendingIntent) {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(mContext, "chat")
                .setContentTitle("新消息")
                .setContentText("点击查看")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_app)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(1, notification);
    }

    public void sendDownloadNotification(int progress) {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(mContext, "update")
                .setContentTitle("正在下载")
                .setContentText(String.format("下载进度：%s", progress))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_app)
                .setAutoCancel(false)
                .setProgress(100, 0, false)
                .build();
        manager.notify(1, notification);
    }

    public void sendDownloadSuccessNotification(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(mContext, "update")
                .setContentTitle("下载完成")
                .setContentText("点击安装")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_app)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setProgress(100, 100, false)
                .build();
        manager.notify(1, notification);
    }

    public void sendDownloadFailNotification() {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(mContext, "update")
                .setContentTitle("下载失败")
                .setContentText("请稍后重试")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon_app)
                .setAutoCancel(true)
                .setProgress(100, 100, false)
                .build();
        manager.notify(1, notification);
    }
}
