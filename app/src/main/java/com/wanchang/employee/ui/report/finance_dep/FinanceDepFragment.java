package com.wanchang.employee.ui.report.finance_dep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.flyco.tablayout.SlidingTabLayout;
import com.wanchang.employee.R;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.report.product_dep.Card0Fragment;
import com.wanchang.employee.ui.report.product_dep.Card1Fragment;
import com.wanchang.employee.ui.report.product_dep.Card2Fragment;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinanceDepFragment extends BaseFragment {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;
  @BindView(R.id.tv_topbar_left)
  TextView mLeftTv;

  @BindView(R.id.tl)
  SlidingTabLayout tabLayout;
  @BindView(R.id.vp)
  ViewPager vp;


  private final String[] mTitles = {"地区销售统计", "产品部业绩", "商品销售", "客户统计", "业务员业绩", "促销统计"};


  private static final String BUNDLE_ARGS = "bundle_args";

  public FinanceDepFragment() {
    // Required empty public constructor
  }

  public static FinanceDepFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    FinanceDepFragment fragment = new FinanceDepFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_product_dep_report;
  }

  @Override
  protected void initData() {
    ArrayList<Fragment> mFragments = new ArrayList<>();
    mFragments.add(CardAreaFragment.newInstance(""));
    mFragments.add(CardDepFragment.newInstance(""));
    mFragments.add(Card0Fragment.newInstance(""));
    mFragments.add(CardClientFragment.newInstance(""));
    mFragments.add(Card1Fragment.newInstance(""));
    mFragments.add(Card2Fragment.newInstance(""));

    tabLayout.setViewPager(vp, mTitles, mContext, mFragments);
  }

  @Override
  protected void initView() {
    mLeftTv.setVisibility(View.GONE);
    mTitleTv.setText("报表");
  }

}
