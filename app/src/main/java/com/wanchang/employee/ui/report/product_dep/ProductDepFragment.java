package com.wanchang.employee.ui.report.product_dep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.flyco.tablayout.SlidingTabLayout;
import com.wanchang.employee.R;
import com.wanchang.employee.ui.base.BaseFragment;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDepFragment extends BaseFragment {

  @BindView(R.id.tl)
  SlidingTabLayout tabLayout;
  @BindView(R.id.vp)
  ViewPager vp;


  private final String[] mTitles = {"商品销售", "业务员业绩", "促销统计"};


  private static final String BUNDLE_ARGS = "bundle_args";

  public ProductDepFragment() {
    // Required empty public constructor
  }

  public static ProductDepFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    ProductDepFragment fragment = new ProductDepFragment();
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
    mFragments.add(Card0Fragment.newInstance(""));
    mFragments.add(Card1Fragment.newInstance(""));
    mFragments.add(Card2Fragment.newInstance(""));

    tabLayout.setViewPagerBugFix(vp, mTitles, getChildFragmentManager(), mFragments);
  }

  @Override
  protected void initView() {

  }

}
