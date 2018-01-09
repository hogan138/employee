package com.wanchang.employee.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.LogUtils;
import com.wanchang.employee.R;
import com.wanchang.employee.app.MallApp;

public class SplashActivity extends AppCompatActivity {

  RelativeLayout splashRl;

  private String cmd;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    setContentView(R.layout.activity_splash);

    initView();
  }

  private void initView() {
    splashRl = findViewById(R.id.rl_splash);
    startAnim();
  }

  @Override
  protected void onResume() {
    super.onResume();

    Bundle bun = getIntent().getExtras();
    if (bun != null) {
      cmd = bun.getString("cmd");
      LogUtils.e("-----"+cmd);
    }
  }

  private void startAnim() {
    AlphaAnimation alpha = new AlphaAnimation(0.3f, 1.0f);
    alpha.setDuration(2000);
    alpha.setAnimationListener(new AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        jumpNextPage();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });

    splashRl.startAnimation(alpha);
  }

  private void jumpNextPage() {
    if ("".equals(MallApp.getInstance().getToken())) {
      startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    } else {
      startActivity(new Intent(SplashActivity.this, MainActivity.class).putExtra("cmd", cmd));
    }
    finish();
  }

}
