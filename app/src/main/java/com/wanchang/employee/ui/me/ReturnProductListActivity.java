package com.wanchang.employee.ui.me;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.TabEntity;
import com.wanchang.employee.ui.base.BaseActivity;
import java.util.ArrayList;

public class ReturnProductListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.tl)
  CommonTabLayout mTabLayout;

  private String[] mTitles = {"退货申请", "申请记录"};
  private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
  private ArrayList<Fragment> mFragments = new ArrayList<>();

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_return_product_list;
  }

  @Override
  protected void initData() {
    for (int i = 0; i < mTitles.length; i++) {
      mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
      if (i == 0) {
        mFragments.add(ReturnProductApplyFragment.newInstance());
      } else {
        mFragments.add(ReturnProductHistoryFragment.newInstance());
      }

    }
    mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
  }

  @Override
  protected void initView() {
    mTitle.setText("退货售后");
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
