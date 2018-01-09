package com.wanchang.employee.ui.salesman.me;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.wanchang.employee.R;
import com.wanchang.employee.ui.base.BaseActivity;

public class ClientReturnActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_client_return;
  }

  @Override
  protected void initData() {

  }



  @Override
  protected void initView() {
    mTitleTv.setText("客户回款");


  }





  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
