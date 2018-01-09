package com.wanchang.employee.ui.salesman.me;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.ui.base.BaseActivity;

public class AddClientActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.edt_name)
  TextView mNameEdt;
  @BindView(R.id.edt_tel)
  TextView mTelEdt;
  @BindView(R.id.edt_desc)
  TextView mDescEdt;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_add_client;
  }

  @Override
  protected void initData() {
  }

  @Override
  protected void initView() {
    mTitleTv.setText("新增客户");
  }


  @OnClick(R.id.tv_submit)
  public void onSubmit() {
    OkGo.<String>post(MallAPI.CLIENT_SIGNUP)
        .tag(this)
        .params("name", mNameEdt.getText().toString().trim())
        .params("mobile", mTelEdt.getText().toString().trim())
        .params("explain", mDescEdt.getText().toString().trim())
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200 || response.code() == 201) {
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
