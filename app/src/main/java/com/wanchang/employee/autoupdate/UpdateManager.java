package com.wanchang.employee.autoupdate;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mylhyl.circledialog.CircleDialog;
import com.wanchang.employee.data.callback.StringDialogCallback;


/**
 * Created by stormlei on 2017/4/6.
 */

public class UpdateManager {

  private FragmentActivity mContext;
  private String title;
  private String download_url;

  public UpdateManager(FragmentActivity context) {
    this.mContext = context;
  }

  /**
   * 检测更新
   */
  public void checkUpdate(final boolean isToast) {
    OkGo.<String>get("http://admin.zjwanchang.com/system/update/check")
        .tag(this)
        .params("app_id", "40")
        .params("version_code", AppUtils.getAppVersionCode())
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            JSONObject jsonObj = JSON.parseObject(response.body());
            int need_update = jsonObj.getIntValue("need_update");
            if (need_update == 1) {
              title = jsonObj.getString("title");
              download_url = jsonObj.getString("url");
              String version_info = jsonObj.getString("update_log");
              showNoticeDialog(version_info);
            } else if (need_update == 2) {
              title = jsonObj.getString("title");
              download_url = jsonObj.getString("url");
              String version_info = jsonObj.getString("update_log");
              showNoticeDialogForce(version_info);
            } else {
              if (isToast) {
                ToastUtils.showShort("已经是最新版本");
              }
            }
          }
        });
  }

  /**
   * 显示更新对话框
   */
  private void showNoticeDialog(String version_info) {
    new CircleDialog.Builder(mContext)
        .setTitle(title)
        .setText(version_info)
        .setNegative("以后再说", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("立即更新", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mContext.startService(
                new Intent(mContext, DownLoadService.class).putExtra("download_url", download_url));
          }
        }).show();
  }


  private void showNoticeDialogForce(String version_info) {
    new CircleDialog.Builder(mContext)
        .setTitle(title)
        .setText(version_info)
        .setCancelable(false)
        .setCanceledOnTouchOutside(false)
        .setPositive("立即更新", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mContext.startService(
                new Intent(mContext, DownLoadService.class).putExtra("download_url", download_url));
          }
        }).show();
  }
}
