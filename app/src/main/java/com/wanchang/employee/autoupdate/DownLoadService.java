package com.wanchang.employee.autoupdate;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import java.io.File;

/**
 * Created by stormlei on 2017/4/6.
 */

public class DownLoadService extends Service {

  /**
   * 目标文件存储的文件夹路径
   */
  private String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath()
      + File.separator + "wanchang_download";
  /**
   * 目标文件存储的文件名
   */
  private String destFileName = "wanchang_employee.apk";

  private Context mContext;
  private int preProgress = 0;
  private int NOTIFY_ID = 1000;
  private NotificationCompat.Builder builder;
  private NotificationManager notificationManager;

  private String download_url;


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    mContext = this;
    download_url = intent.getStringExtra("download_url");
    loadFile();
    return super.onStartCommand(intent, flags, startId);
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  /**
   * 下载文件
   */
  private void loadFile() {
    initNotification();

    OkGo.<File>get(download_url)
        .tag(this)
        .execute(new FileCallback(destFileDir, destFileName) {


          @Override
          public void onSuccess(Response<File> response) {
            cancelNotification();
            stopSelf();
            installApk(response.body());
          }

          @Override
          public void downloadProgress(Progress progress) {
            super.downloadProgress(progress);
            //LogUtils.e("++++++++++"+progress.fraction * 100);
            updateNotification((long) (progress.fraction * 100));
          }

          @Override
          public void onError(Response<File> response) {
            super.onError(response);
            ToastUtils.showShort("下载失败");
            cancelNotification();
            stopSelf();
          }
        });
  }

  /**
   * 安装软件
   */
  private void installApk(File file) {
    if (file == null) {
      return;
    }
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      Uri contentUri = FileProvider
          .getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider",
              file);
      intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
    } else {
      intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
    }
    mContext.startActivity(intent);
  }


  /**
   * 初始化Notification通知
   */
  public void initNotification() {
    builder = new NotificationCompat.Builder(mContext)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentText("0%")
        .setContentTitle(getResources().getString(R.string.app_name))
        .setProgress(100, 0, false);
    notificationManager = (NotificationManager) mContext
        .getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFY_ID, builder.build());
  }

  /**
   * 更新通知
   */
  public void updateNotification(long progress) {
    int currProgress = (int) progress;
    if (preProgress < currProgress) {
      builder.setContentText(progress + "%");
      builder.setProgress(100, (int) progress, false);
      notificationManager.notify(NOTIFY_ID, builder.build());
    }
    preProgress = (int) progress;
  }

  /**
   * 取消通知
   */
  public void cancelNotification() {
    notificationManager.cancel(NOTIFY_ID);
  }
}
