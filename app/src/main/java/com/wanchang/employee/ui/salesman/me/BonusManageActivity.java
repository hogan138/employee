package com.wanchang.employee.ui.salesman.me;

import android.content.Intent;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.ui.base.BaseActivity;

public class BonusManageActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.tv_un_cash)
  TextView mUnCashTv;

  @BindView(R.id.tv_can_cash)
  TextView mCanCashTv;

  @BindView(R.id.tv_cash_total)
  TextView mCashTotalTv;


  private String canCash;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_bonus_manage;
  }

  @Override
  protected void initData() {

  }

  @Override
  protected void initView() {
    mTitleTv.setText("佣金管理");
  }

  @Override
  protected void onResume() {
    super.onResume();

    loadData();
  }

  private void loadData() {
    OkGo.<String>get(MallAPI.SALESMAN+"/"+ MallApp.getInstance().getUserId())
        .tag(this)
        .params("expand", "un_cash,can_cash")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              String unCash = jsonObj.getString("un_cash");
              canCash = jsonObj.getString("can_cash");
              String cashTotal = jsonObj.getString("cash_total");
              mUnCashTv.setText(unCash+"元");
              mCanCashTv.setText(canCash+"元");
              mCashTotalTv.setText("已提佣金："+cashTotal+"元");
            }
          }
        });
  }

  @OnClick(R.id.tv_bonus_apply)
  public void onBonusApply() {
    startActivity(new Intent(mContext, BonusApplyActivity.class)
        .putExtra("canCash", canCash));
  }


  @OnClick(R.id.tv_bonus_detail)
  public void onBonusDetail() {
    startActivity(new Intent(mContext, BonusDetailListActivity.class));
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
