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

public class OrderListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.tl)
  CommonTabLayout mTabLayout;

  private String[] mTitles = {"所有订单", "待支付", "待收货", "已完成"};
  private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
  private ArrayList<Fragment> mFragments = new ArrayList<>();

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_coupon_list;
  }

  @Override
  protected void initData() {
    for (int i = 0; i < mTitles.length; i++) {
      mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
      if (i == 0) {
        mFragments.add(OrderCardFragment.newInstance(-100));
      } else if (i == 1){
        mFragments.add(OrderCardFragment.newInstance(10));
      } else if (i == 2){
        mFragments.add(OrderCardFragment.newInstance(40));
      } else {
        mFragments.add(OrderCardFragment.newInstance(100));
      }

    }
    mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);

    int position = getIntent().getIntExtra("position", 0);
    mTabLayout.setCurrentTab(position);
  }

  @Override
  protected void initView() {
    mTitle.setText("我的订单");
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
