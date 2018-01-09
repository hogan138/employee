package com.wanchang.employee.ui.me;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.ui.base.BaseActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class BindMobileActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;
  @BindView(R.id.tv_topbar_right)
  TextView mRightTv;

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



  @Override
  protected int getLayoutResId() {
    return R.layout.activity_bind_mobile;
  }

  @Override
  protected void initData() {
    readyCaptcha();
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

  @Override
  protected void initView() {
    mTitleTv.setText("绑定手机");
    mRightTv.setVisibility(View.VISIBLE);
    mRightTv.setText("保存");
  }

  @OnClick(R.id.tv_topbar_right)
  public void onBindMobile() {
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
    OkGo.<String>put(MallAPI.USER+"/update")
        .tag(this)
        .params("mobile", account)
        .params("captcha_id", captcha_id)
        .params("captcha", captcha)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              ToastUtils.showShort("绑定成功");
              finish();
            }
          }
        });
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
