package com.wanchang.employee.ui.salesman.me;

import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.ui.base.BaseActivity;

public class ClientCreditApplyActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.edt_price)
  EditText mPriceEdt;


  private String clientId;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_client_credit_apply;
  }

  @Override
  protected void initData() {
    clientId = getIntent().getStringExtra("client_id");
  }

  @Override
  protected void initView() {
    mTitleTv.setText("申请临时额度");

  }

  @OnClick(R.id.tv_submit)
  public void onSubmit() {
    OkGo.<String>post(MallAPI.CLIENT_CREDIT_APPLY)
        .tag(this)
        .params("client_id", clientId)
        .params("credit_apply", mPriceEdt.getText().toString().trim())
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200 || response.code() == 201) {
              ToastUtils.showShort("已提交");
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
