package com.wanchang.employee.ui.salesman.me;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.TabEntity;
import com.wanchang.employee.ui.base.BaseActivity;
import java.util.ArrayList;

public class ProductPriceListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.tl)
  CommonTabLayout mTabLayout;

  private String[] mTitles = {"已设置", "未设置"};
  private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
  private ArrayList<Fragment> mFragments = new ArrayList<>();

  private String clientId;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_coupon_list;
  }

  @Override
  protected void initData() {
    clientId = getIntent().getStringExtra("client_id");

    for (int i = 0; i < mTitles.length; i++) {
      mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
      if (i == 0) {
        mFragments.add(PriceSetCardFragment.newInstance(clientId));
      } else {
        mFragments.add(PriceUnSetCardFragment.newInstance(clientId));
      }

    }
    mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);

    mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
      @Override
      public void onTabSelect(int position) {

      }

      @Override
      public void onTabReselect(int position) {

      }
    });
  }

  @Override
  protected void initView() {
    mTitle.setText("一客一价");
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
