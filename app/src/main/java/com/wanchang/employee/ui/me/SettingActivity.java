package com.wanchang.employee.ui.me;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.allen.library.SuperTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mylhyl.circledialog.CircleDialog;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.ui.MainActivity;
import com.wanchang.employee.ui.base.BaseActivity;

public class SettingActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.stv_clear_cache)
  SuperTextView mClearCacheStv;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_setting;
  }

  @Override
  protected void initData() {

  }

  @Override
  protected void initView() {
    mTitle.setText("设置");
  }

  @OnClick(R.id.stv_modify_password)
  public void onModifyPwd() {
    startActivity(new Intent(mContext, ModifyPwdActivity.class));
  }

  @OnClick(R.id.stv_bind_mobile)
  public void onBindMobile() {
    startActivity(new Intent(mContext, BindMobileActivity.class));
  }

  @OnClick(R.id.stv_clear_cache)
  public void onClearCache() {
    new CircleDialog.Builder(this)
        .setTitle("提示")
        .setText("您确定要清除吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mClearCacheStv.setRightString("0M");
          }
        }).show();
  }

  @OnClick(R.id.tv_logout)
  public void onlogout() {
    new CircleDialog.Builder(this)
        .setTitle("提示")
        .setText("您确定要退出吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.USER_LOGOUT)
                .tag(this)
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    startActivity(new Intent(mContext, MainActivity.class).putExtra("app_exit", true));
                    sendBroadcast(new Intent(Constants.APP_EXIT_ACTION));
                  }
                });
          }
        }).show();
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
