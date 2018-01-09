package com.wanchang.employee.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.ButterKnife;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.lzy.okgo.OkGo;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.ui.LoginActivity;

/**
 * Created by stormlei on 2017/5/4.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

  protected static final String TAG = BaseActivity.class.getSimpleName();
  protected FragmentActivity mContext = BaseActivity.this;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    initTheme();
    super.onCreate(savedInstanceState);
    LogUtils.i(TAG, getClass().getSimpleName());
    setContentView(getLayoutResId());
    ButterKnife.bind(this);
    //初始化沉浸式
    //initImmersionBar();
  }

  protected void initImmersionBar() {
//    mImmersionBar = ImmersionBar.with(this);
//    mImmersionBar.init();

//    ImmersionBar.with(this)
//        .statusBarColor(R.color.color_ff)
//        .statusBarDarkFont(true)
//        .fitsSystemWindows(true)
//        .init();
  }

  protected void setToolBar(Toolbar toolbar, String title) {
    toolbar.setTitle(title);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });
  }


  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    initData();
    initView();
  }

  protected abstract int getLayoutResId();

  protected abstract void initData();

  protected abstract void initView();


  @Override
  protected void onDestroy() {
    super.onDestroy();
    //Activity销毁时，取消网络请求
    OkGo.getInstance().cancelTag(this);

//    if (mImmersionBar != null)
//      mImmersionBar.destroy();
  }

  private void initTheme() {
    ScreenUtils.setPortrait(this);
  }

  public void openActivity(Intent intent, boolean requireLogin) {
    if (requireLogin) {
      if (MallApp.getInstance().isLogin()) {
        startActivity(intent);
      } else {
        startActivity(new Intent(mContext, LoginActivity.class));
      }
    } else {
      startActivity(intent);
    }
  }

//  private ProgressDialog pd;
//
//  public void showLoading() {
//    if (pd != null && pd.isShowing()) return;
//    pd = new ProgressDialog(this);
//    pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
//    pd.setCanceledOnTouchOutside(false);
//    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//    pd.setMessage("请求网络中...");
//    pd.show();
//  }
//
//  public void dismissLoading() {
//    if (pd != null && pd.isShowing()) {
//      pd.dismiss();
//    }
//  }
//
//  public boolean isShowing() {
//    if (pd != null) {
//      return pd.isShowing();
//    }
//    return false;
//  }


}
