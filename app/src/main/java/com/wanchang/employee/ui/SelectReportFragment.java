package com.wanchang.employee.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.DepRole;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.report.finance_dep.FinanceDepFragment;
import com.wanchang.employee.ui.report.product_dep.ProductDepFragment;
import com.wanchang.employee.util.ACache;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectReportFragment extends BaseFragment {


  private static final String BUNDLE_ARGS = "bundle_args";

  public SelectReportFragment() {
    // Required empty public constructor
  }

  public static SelectReportFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    SelectReportFragment fragment = new SelectReportFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_select_report;
  }

  @Override
  protected void initData() {
    showFragment();
  }

  @Override
  protected void initView() {

  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    //if (!hidden) showFragment();
  }

  private void showFragment() {
    OkGo.<String>get(MallAPI.USER_DEPARTMENT_LIST)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<DepRole> depRoleList = JSON.parseArray(response.body(), DepRole.class);
              if (depRoleList.size() >= 1) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                if (depRoleList.get(0).getDepartment().getType() == 0) {   //产品部
                  ft.replace(R.id.fl_my_container, ProductDepFragment.newInstance("product_department"));
                } else if (depRoleList.get(0).getDepartment().getType() == 300) {  //财务部
                  ft.replace(R.id.fl_my_container, FinanceDepFragment.newInstance("finance_department"));
                } else if (depRoleList.get(0).getDepartment().getType() == 400) {  //营销部
                  ft.replace(R.id.fl_my_container, FinanceDepFragment.newInstance("finance_department"));
                  //ft.replace(R.id.fl_my_container, MarketingDepFragment.newInstance("marketing_department"));
                } else if (depRoleList.get(0).getDepartment().getType() == 600) {  //业务中心
                  ft.replace(R.id.fl_my_container, FinanceDepFragment.newInstance("finance_department"));
                  //ft.replace(R.id.fl_my_container, BusinessCenterDepFragment.newInstance("business_center_department"));
                }
                ft.commit();

                ACache.get(mContext).put(Constants.KEY_DEPARTMENT_ID, depRoleList.get(0).getDepartment_id()+"");
                ACache.get(mContext).put(Constants.KEY_ROLE_ID, depRoleList.get(0).getRole_id()+"");
              }
            }
          }
        });

  }

}
