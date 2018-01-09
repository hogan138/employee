package com.wanchang.employee.ui.salesman.me;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mylhyl.circledialog.CircleDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.AreaRecruit;
import com.wanchang.employee.ui.base.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;

public class AreaRecruitListActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_area_recruit)
  RecyclerView mProductRv;


  private BaseQuickAdapter<AreaRecruit, BaseViewHolder> mProductAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_area_recruit_list;
  }

  @Override
  protected void initData() {
    mProductRv.setLayoutManager(new LinearLayoutManager(mContext));
    mProductRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<AreaRecruit, BaseViewHolder>(R.layout.item_area_recruit_list) {
      @Override
      protected void convert(final BaseViewHolder helper, final AreaRecruit item) {
        helper.setText(R.id.tv_department_name, item.getDepartment().getName());

        if (item.getIs_checked() == 0 && item.getStatus() == 1) {
          helper.setText(R.id.tv_apply_status, "申请");
          helper.setTextColor(R.id.tv_apply_status, getResources().getColor(R.color.color_336));
        } else {
          helper.setText(R.id.tv_apply_status, "已申请");
          helper.setTextColor(R.id.tv_apply_status, getResources().getColor(R.color.color_99));
        }

        String[] categoryArray = item.getClient_category().split(",");
        if (categoryArray.length >= 1) {
          if ("10".equals(categoryArray[0])) {
            helper.setText(R.id.tv_client_category, "商业");
          } else if ("20".equals(categoryArray[0])) {
            helper.setText(R.id.tv_client_category, "连锁");
          } else if ("30".equals(categoryArray[0])) {
            helper.setText(R.id.tv_client_category, "单店");
          }
        }
        helper.setText(R.id.tv_area, "招商区域："+item.getProvince()+" "+item.getCity()+" "+item.getCounty());
        helper.setText(R.id.tv_comment, item.getComment());

        helper.addOnClickListener(R.id.tv_apply_status);
      }
    });

    mProductAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int area_id = mProductAdapter.getItem(position).getId();
        AreaApply(area_id);
      }
    });
  }

  private void AreaApply(final int recruit_id) {
    new CircleDialog.Builder(this)
        .setTitle("提示")
        .setText("您确定要申请吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.AREA_RECRUIT_APPLY)
                .tag(this)
                .params("recruit_id", recruit_id)
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                      loadData();
                    }
                  }
                });
          }
        }).show();
  }


  @Override
  protected void initView() {
    mTitleTv.setText("招商平台");

    loadData();

    refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_MORE;
        currentPage++;
        loadData();
      }

      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_REFRESH;
        currentPage = 1;
        loadData();
      }
    });
  }


  private void loadData() {
    OkGo.<String>get(MallAPI.AREA_RECRUIT)
        .tag(this)
        .params("expand", "department,is_checked")
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<AreaRecruit> productList = JSON.parseArray(jsonObj.getString("items"), AreaRecruit.class);
              JSONObject metaObj = JSON.parseObject(jsonObj.getString("_meta"));
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mProductAdapter.setNewData(productList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+productList.size());
                if (currentPage > metaObj.getIntValue("pageCount")) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mProductAdapter.addData(productList);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }
        });
  }



  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
