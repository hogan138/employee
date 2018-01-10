package com.wanchang.employee.ui.work;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.DepRole;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.ACache;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;

public class SelectRoleActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv)
  RecyclerView mRv;

  private BaseQuickAdapter<DepRole, BaseViewHolder> mAdapter;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_select_role;
  }

  @Override
  protected void initData() {
  }

  @Override
  protected void initView() {
    mTitleTv.setText("切换角色");

    mRv.setLayoutManager(new LinearLayoutManager(mContext));
    mRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).build());
    mRv.setAdapter(mAdapter = new BaseQuickAdapter<DepRole, BaseViewHolder>(R.layout.item_select_role_list) {
      @Override
      protected void convert(BaseViewHolder helper, DepRole item) {
        helper.setText(R.id.tv_dep_role, item.getDepartment().getName()+"-"+item.getRole().getName());
        if (TextUtils.isEmpty(ACache.get(mContext).getAsString(Constants.KEY_ROLE_ID))) {
          helper.setTextColor(R.id.tv_dep_role, getResources().getColor(R.color.color_33));
        } else {
          if ((""+item.getRole_id()).equals(ACache.get(mContext).getAsString(Constants.KEY_ROLE_ID))) {
            helper.setTextColor(R.id.tv_dep_role, getResources().getColor(R.color.color_336));
          } else {
            helper.setTextColor(R.id.tv_dep_role, getResources().getColor(R.color.color_33));
          }
        }
      }
    });

    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DepRole depRole = mAdapter.getItem(position);
        String department_type = depRole.getDepartment().getType()+"";
        String department_id = depRole.getDepartment_id()+"";
        String role_id = depRole.getRole_id()+"";
        LogUtils.e(department_type+"-----"+department_id+"-----"+role_id);
        ACache.get(mContext).put(Constants.KEY_DEPARTMENT_TYPE, department_type);
        ACache.get(mContext).put(Constants.KEY_DEPARTMENT_ID, department_id);
        ACache.get(mContext).put(Constants.KEY_ROLE_ID, role_id);
        setResult(RESULT_OK);
        finish();
      }
    });

    refreshLayout.setEnableLoadmore(false);
    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        getRoleList();
      }
    });

    getRoleList();
  }

  private void getRoleList() {
    OkGo.<String>get(MallAPI.USER_DEPARTMENT_LIST)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<DepRole> depRoleList = JSON.parseArray(response.body(), DepRole.class);
              mAdapter.setNewData(depRoleList);
              refreshLayout.finishRefresh();
            }
          }
        });
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }


}
