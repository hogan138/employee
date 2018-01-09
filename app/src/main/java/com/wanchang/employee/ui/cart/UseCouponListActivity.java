package com.wanchang.employee.ui.cart;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.blankj.utilcode.util.LogUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.Coupon;
import com.wanchang.employee.data.entity.TabEntity;
import com.wanchang.employee.ui.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class UseCouponListActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.tl)
  CommonTabLayout mTabLayout;

  private String[] mTitles = {"可用优惠券", "不可用优惠券"};
  private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
  private ArrayList<Fragment> mFragments = new ArrayList<>();


  private List<Coupon> canUseCouponList;
  private List<Coupon> unUseCouponList;
  private int selected_coupon_id;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_use_coupon_list;
  }

  @Override
  protected void initData() {
    canUseCouponList = getIntent().getParcelableArrayListExtra("canUseCouponList");
    unUseCouponList = getIntent().getParcelableArrayListExtra("unUseCouponList");
    selected_coupon_id = getIntent().getIntExtra("selected_coupon_id", -1);

    LogUtils.e("------"+selected_coupon_id);

    for (int i = 0; i < mTitles.length; i++) {
      mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
      if (i == 0) {
        mFragments.add(UseCouponCardFragment.newInstance(0, canUseCouponList, selected_coupon_id));
      } else {
        mFragments.add(UseCouponCardFragment.newInstance(1, unUseCouponList, -1));
      }

    }
    mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
  }

  @Override
  protected void initView() {
    mTitleTv.setText("使用优惠券");
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
