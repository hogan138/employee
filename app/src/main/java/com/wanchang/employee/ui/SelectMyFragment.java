package com.wanchang.employee.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.ui.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectMyFragment extends BaseFragment {


  private static final String BUNDLE_ARGS = "bundle_args";

  public SelectMyFragment() {
    // Required empty public constructor
  }

  public static SelectMyFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    SelectMyFragment fragment = new SelectMyFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_select_my;
  }

  @Override
  protected void initData() {
  }

  @Override
  protected void initView() {

  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) showFragment();
  }

  private void showFragment() {
    MyFragment myFragment = MyFragment.newInstance("client");
    MySalesmanFragment mySalesmanFragment = MySalesmanFragment.newInstance("salesman");
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    if (MallApp.getInstance().getGroupId() == Constants.GROUP_CLIENT) {
      ft.replace(R.id.fl_my_container, myFragment);
    } else {
      ft.replace(R.id.fl_my_container, mySalesmanFragment);
    }
    ft.commit();
  }

}
