package com.wanchang.employee.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wanchang.employee.R;
import com.wanchang.employee.ui.base.BaseActivity;


public class ZxingCustomActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;
  @BindView(R.id.tv_topbar_right)
  TextView mRightTv;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_zxing_custom;
  }

  @Override
  protected void initData() {
    /**
     * 执行扫面Fragment的初始化操作
     */
    CaptureFragment captureFragment = new CaptureFragment();
    // 为二维码扫描界面设置定制化界面
    CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);

    captureFragment.setAnalyzeCallback(analyzeCallback);
    /**
     * 替换我们的扫描控件
     */ getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
  }

  @Override
  protected void initView() {
    mTitleTv.setText("扫一扫");
    mRightTv.setVisibility(View.VISIBLE);
    mRightTv.setText("打开闪光灯");
  }


  /**
   * 二维码解析回调函数
   */
  CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
      Intent resultIntent = new Intent();
      Bundle bundle = new Bundle();
      bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
      bundle.putString(CodeUtils.RESULT_STRING, result);
      resultIntent.putExtras(bundle);
      ZxingCustomActivity.this.setResult(RESULT_OK, resultIntent);
      ZxingCustomActivity.this.finish();
    }

    @Override
    public void onAnalyzeFailed() {
      Intent resultIntent = new Intent();
      Bundle bundle = new Bundle();
      bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
      bundle.putString(CodeUtils.RESULT_STRING, "");
      resultIntent.putExtras(bundle);
      ZxingCustomActivity.this.setResult(RESULT_OK, resultIntent);
      ZxingCustomActivity.this.finish();
    }
  };


  @OnClick(R.id.tv_topbar_left)
  public void goBack() {
    finish();
  }


  @OnClick(R.id.tv_topbar_right)
  public void openLight() {
    if ("关闭闪光灯".equals(mRightTv.getText().toString())) {
      mRightTv.setText("打开闪光灯");
      CodeUtils.isLightEnable(false);
    }else {
      mRightTv.setText("关闭闪光灯");
      CodeUtils.isLightEnable(true);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    CodeUtils.isLightEnable(false);
  }
}
