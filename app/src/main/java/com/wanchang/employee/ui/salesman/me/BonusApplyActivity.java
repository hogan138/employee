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

public class BonusApplyActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.edt_amount)
  EditText mAmountEdt;
  @BindView(R.id.tv_can_cash)
  TextView mCanCashTv;

  private String canCash;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_bonus_apply;
  }

  @Override
  protected void initData() {
    canCash = getIntent().getStringExtra("canCash");
  }



  @Override
  protected void initView() {
    mTitleTv.setText("提现");

    mCanCashTv.setText("可提现 "+canCash+"元");
  }

  @OnClick(R.id.tv_bonus_apply)
  public void onBonusApply() {
    OkGo.<String>post(MallAPI.BONUS_APPLY)
        .tag(this)
        .params("amount", mAmountEdt.getText().toString().trim())
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              ToastUtils.showShort("提现成功");
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
