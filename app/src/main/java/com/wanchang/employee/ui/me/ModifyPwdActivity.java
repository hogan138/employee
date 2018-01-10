package com.wanchang.employee.ui.me;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.ui.base.BaseActivity;

public class ModifyPwdActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;
  @BindView(R.id.tv_topbar_right)
  TextView mRightTv;


  @BindView(R.id.edt_new_password)
  EditText mPasswordEdt;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_modify_password;
  }

  @Override
  protected void initData() {

  }

  @Override
  protected void initView() {
    mTitleTv.setText("修改密码");
    mRightTv.setVisibility(View.VISIBLE);
    mRightTv.setText("保存");
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

  @OnClick(R.id.tv_topbar_right)
  public void onModifyPwd() {
    OkGo.<String>post(MallAPI.USER+"/update-password")
        .tag(this)
        .params("password", EncryptUtils.encryptSHA1ToString(mPasswordEdt.getText().toString().trim()).toLowerCase())
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              ToastUtils.showShort("修改成功");
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
