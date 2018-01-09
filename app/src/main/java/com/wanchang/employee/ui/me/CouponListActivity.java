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

public class CouponListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.tl)
  CommonTabLayout mTabLayout;

  private String[] mTitles = {"未使用", "使用记录", "已过期"};
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
        mFragments.add(CouponCardFragment.newInstance(100));
      } else if (i == 1){
        mFragments.add(CouponCardFragment.newInstance(0));
      } else {
        mFragments.add(CouponCardFragment.newInstance(-1));
      }

    }
    mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
  }

  @Override
  protected void initView() {
    mTitle.setText("我的优惠券");
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
