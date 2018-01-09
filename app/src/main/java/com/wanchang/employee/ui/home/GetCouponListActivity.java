package com.wanchang.employee.ui.home;

import android.content.Intent;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Coupon;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.ui.classify.ProductListActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GetCouponListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_coupon)
  RecyclerView mCouponRv;


  public BaseQuickAdapter<Coupon, BaseViewHolder> mCouponAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_get_coupon_list;
  }

  @Override
  protected void initData() {
    mCouponRv.setLayoutManager(new LinearLayoutManager(mContext));
//    mCouponRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
//        getResources().getColor(R.color.color_e5)).build());
    mCouponRv.setAdapter(mCouponAdapter = new BaseQuickAdapter<Coupon, BaseViewHolder>(R.layout.item_get_coupon_list) {
      @Override
      protected void convert(BaseViewHolder helper, Coupon item) {
        helper.setBackgroundRes(R.id.rl_coupon_root, R.drawable.voucher_get_n);
        helper.setText(R.id.tv_value, item.getValue()+"");
        helper.setText(R.id.tv_condition, "满"+item.getCondition()+"可用");
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_time, getTime(item.getStart_at())+"-"+getTime(item.getEnd_at()));
        helper.setText(R.id.tv_usage, item.getUsage());
        if (item.getReceived() == 0) {
          helper.setGone(R.id.tv_coupon_op, false);
        } else {
          helper.setGone(R.id.tv_coupon_op, true);
        }
        if (item.getReceived() < item.getCount_per_client()) {
          helper.setTextColor(R.id.tv_get_coupon, getResources().getColor(R.color.color_1a));
        } else {
          helper.setTextColor(R.id.tv_get_coupon, getResources().getColor(R.color.color_99));
        }

        helper.addOnClickListener(R.id.tv_get_coupon);
        helper.addOnClickListener(R.id.tv_coupon_op);
      }
    });

    mCouponAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
          case R.id.tv_get_coupon:
            getCoupon(mCouponAdapter.getItem(position).getKey());
            break;
          case R.id.tv_coupon_op:
            openActivity(new Intent(mContext, ProductListActivity.class), true);
            break;
        }
      }
    });
  }

  private void getCoupon(String key) {
    OkGo.<String>get(MallAPI.CLIENT_COUPON_GET)
        .tag(this)
        .params("key", key)
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

  @Override
  protected void initView() {
    mTitle.setText("领券中心");

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
    OkGo.<String>get(MallAPI.COUPON)
        .tag(this)
        .params("status", 50)
        .params("pub_type", 20)
        .params("expand", "usage,usage_all,received")
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<Coupon> couponList = JSON.parseArray(jsonObj.getString("items"), Coupon.class);
              JSONObject metaObj = JSON.parseObject(jsonObj.getString("_meta"));
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mCouponAdapter.setNewData(couponList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+couponList.size());
                if (currentPage > metaObj.getIntValue("pageCount")) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mCouponAdapter.addData(couponList);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }
        });
  }

  private String getTime(long time) {
    return new SimpleDateFormat("yyyy.MM.dd").format(new Date(time*1000));
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
