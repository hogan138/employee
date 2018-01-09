package com.wanchang.employee.ui;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.umeng.message.PushAgent;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.LoginInfo;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.ACache;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_left)
  TextView mLeftTv;
  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.edt_login_mobile)
  EditText mMobileEdt;
  @BindView(R.id.tv_login_get_captcha)
  TextView mGetCaptchaTv;
  @BindView(R.id.edt_login_captcha)
  EditText mCaptchaEdt;

  @BindColor(R.color.color_4d)
  int captchaColor;


  private static final int MAX_COUNT_TIME = 60;
  private long captcha_id;
  private Disposable mCaptchaDisposable;


  @BindView(R.id.ll_mode_1)
  LinearLayout mModel1Ll;
  @BindView(R.id.ll_mode_2)
  LinearLayout mModel2Ll;
  @BindView(R.id.tv_change_mode)
  TextView mChangeModeTv;
  @BindView(R.id.edt_account)
  EditText mAccountEdt;
  @BindView(R.id.edt_password)
  EditText mPasswordEdt;

  private int loginType = 0;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_login;
  }

  @Override
  protected void initData() {
    readyCaptcha();
  }

  @Override
  protected void initView() {
    mLeftTv.setVisibility(View.GONE);
    mTitleTv.setText("商城登录");
  }

  private void readyCaptcha() {
    RxTextView.textChanges(mMobileEdt).map(new Function<CharSequence, Boolean>() {
      @Override
      public Boolean apply(@NonNull CharSequence charSequence) throws Exception {
        return charSequence.toString().trim().length() == 11;
      }
    }).subscribe(new Consumer<Boolean>() {
      @Override
      public void accept(@NonNull Boolean aBoolean) throws Exception {
        RxView.enabled(mGetCaptchaTv).accept(aBoolean);
      }
    });

    mCaptchaDisposable = RxView
        .clicks(mGetCaptchaTv)
        .throttleFirst(MAX_COUNT_TIME, TimeUnit.SECONDS)
        .flatMap(new Function<Object, ObservableSource<Long>>() {
          @Override
          public ObservableSource<Long> apply(@NonNull Object o) throws Exception {
            RxView.enabled(mGetCaptchaTv).accept(false);
            RxTextView.text(mGetCaptchaTv).accept("倒计时"+MAX_COUNT_TIME + "S");
            mGetCaptchaTv.setTextColor(Color.GRAY);

            OkGo.<String>post(MallAPI.AUTH_CAPTCHA)
                .tag(this)
                .params("mobile", mMobileEdt.getText().toString().trim())
                .execute(new StringCallback() {

                  @Override
                  public void onSuccess(Response<String> response) {
                    JSONObject jsonObj = JSON.parseObject(response.body());
                    captcha_id = jsonObj.getLongValue("captcha_id");
                  }
                });

            return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io()).take(MAX_COUNT_TIME);
          }
        })
        .map(new Function<Long, Long>() {
          @Override
          public Long apply(Long aLong) throws Exception {
            return MAX_COUNT_TIME - (aLong + 1);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Long>() {
          @Override
          public void accept(@NonNull Long aLong) throws Exception {
            if (aLong == 0) {
              RxView.enabled(mGetCaptchaTv).accept(true);
              RxTextView.text(mGetCaptchaTv).accept("发送验证码");
              mGetCaptchaTv.setTextColor(captchaColor);
            } else {
              RxTextView.text(mGetCaptchaTv).accept("倒计时 "+aLong + "s");
            }
          }
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mCaptchaDisposable != null) {
      mCaptchaDisposable.dispose();
    }
  }

  @OnClick(R.id.tv_change_mode)
  public void onChangeMode() {
    if (loginType == 0) {
      loginType = 1;
      mModel1Ll.setVisibility(View.GONE);
      mModel2Ll.setVisibility(View.VISIBLE);
      mChangeModeTv.setText("密码登录");
    } else {
      loginType = 0;
      mModel1Ll.setVisibility(View.VISIBLE);
      mModel2Ll.setVisibility(View.GONE);
      mChangeModeTv.setText("验证码登录");
    }
  }

  private boolean isPwdShow;

  @OnClick(R.id.iv_password_show)
  public void onPwdShow() {
    if (!isPwdShow) {
      isPwdShow = true;
      mPasswordEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    } else {
      isPwdShow = false;
      mPasswordEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
  }

  @OnClick(R.id.tv_login_submit)
  public void login() {
    if (loginType == 0) {
      String account = mAccountEdt.getText().toString().trim();
      if (TextUtils.isEmpty(account)) {
        ToastUtils.showShort("账号不能为空");
        return;
      }
      String password = mPasswordEdt.getText().toString().trim();
      if (TextUtils.isEmpty(password)) {
        ToastUtils.showShort("密码不能为空");
        return;
      }
      loginApp0(account, password);
    } else {
      String account = mMobileEdt.getText().toString().trim();
      if (TextUtils.isEmpty(account)) {
        ToastUtils.showShort("手机号不能为空");
        return;
      }
      String captcha = mCaptchaEdt.getText().toString().trim();
      if (TextUtils.isEmpty(captcha)) {
        ToastUtils.showShort("验证码不能为空");
        return;
      }
      loginApp1(captcha);
    }
  }

  private void loginApp0(String account, String password) {
    OkGo.<String>post(MallAPI.AUTH_ACCOUNT_LOGIN)
        .tag(this)
        .params("username", account)
        .params("password", EncryptUtils.encryptSHA1ToString(password).toLowerCase())
        .execute(new StringDialogCallback(this) {
          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              LoginInfo loginInfo = JSON.parseObject(response.body(), LoginInfo.class);
              ACache.get(mContext).put(Constants.KEY_LOGIN_INFO, loginInfo);
              String imAccount = loginInfo.getUser().getIm_account();
              String imPassword = loginInfo.getUser().getIm_password();
              String name = loginInfo.getUser().getName();
              loginIM(imAccount, imPassword, name);
              ToastUtils.showShort("登录中...");
            }
          }
        });
  }

  private void loginApp1(String captcha) {
    OkGo.<String>post(MallAPI.AUTH_MOBILE_LOGIN)
        .tag(this)
        .params("mobile", mMobileEdt.getText().toString().trim())
        .params("captcha_id", captcha_id)
        .params("captcha", captcha)
        .execute(new StringDialogCallback(this) {
          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              LoginInfo loginInfo = JSON.parseObject(response.body(), LoginInfo.class);
              ACache.get(mContext).put(Constants.KEY_LOGIN_INFO, loginInfo);
              String imAccount = loginInfo.getUser().getIm_account();
              String imPassword = loginInfo.getUser().getIm_password();
              String name = loginInfo.getUser().getName();
              loginIM(imAccount, imPassword, name);
              ToastUtils.showShort("登录中...");
            }
          }
        });
  }

  private void loginIM(String username, String password, final String name) {
    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
      ToastUtils.showShort("IM帐号或密码为空");
      return;
    }
    EMClient.getInstance().login(username, password, new EMCallBack() {

      @Override
      public void onSuccess() {
        LogUtils.e(TAG, "login: onSuccess");
        // ** manually load all local groups and conversation
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();

        // update current user's display name for APNs
        boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(name);
        if (!updatenick) {
          LogUtils.e(TAG, "update current user nick fail");
        }

        //bindDeviceToken();

        ToastUtils.showShort("登录成功");
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
      }

      @Override
      public void onProgress(int progress, String status) {
        LogUtils.e(TAG, "login: onProgress");
      }

      @Override
      public void onError(int code, String message) {
        LogUtils.e(TAG, "login: onError: " + code);
        ToastUtils.showShort("IM登录失败: " + message);
        ACache.get(mContext).remove(Constants.KEY_LOGIN_INFO);
      }
    });
  }

  private void bindDeviceToken() {
    String manu = DeviceUtils.getManufacturer();
    int company_id;
    if ("HUAWEI".equals(manu)) {
      company_id = 20;
    } else if ("Xiaomi".equals(manu)) {
      company_id = 40;
    } else {
      company_id = -1;
    }
    OkGo.<String>post(MallAPI.BIND_DEVICE_TOKEN)
        .tag(this)
        .params("device_token", PushAgent.getInstance(mContext).getRegistrationId())
        .params("company_id", company_id)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              ToastUtils.showShort("登录成功");
              startActivity(new Intent(mContext, MainActivity.class));
              finish();
            }
          }
        });
  }

}
