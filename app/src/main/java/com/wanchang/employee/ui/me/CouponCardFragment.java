package com.wanchang.employee.ui.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
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
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.classify.ProductListActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CouponCardFragment extends BaseFragment {


  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_coupon)
  RecyclerView mCouponRv;


  public BaseQuickAdapter<Coupon, BaseViewHolder> mCouponAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  private int status;


  public CouponCardFragment() {
    // Required empty public constructor
  }

  public static CouponCardFragment newInstance(int status) {
    Bundle args = new Bundle();
    args.putInt("key_status", status);
    CouponCardFragment fragment = new CouponCardFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_coupon_card;
  }

  @Override
  protected void initData() {
    status = getArguments().getInt("key_status");

    mCouponRv.setLayoutManager(new LinearLayoutManager(mContext));
    mCouponRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mCouponRv.setAdapter(mCouponAdapter = new BaseQuickAdapter<Coupon, BaseViewHolder>(R.layout.item_coupon_list) {
      @Override
      protected void convert(BaseViewHolder helper, Coupon item) {
        if (status == 100) {
          helper.setBackgroundRes(R.id.rl_coupon_root, R.drawable.voucher_get_n);
          helper.setText(R.id.tv_coupon_op, "立即使用");
          helper.setTextColor(R.id.tv_coupon_op, getResources().getColor(R.color.color_33));
          helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_33));
        } else if (status == 0) {
          helper.setBackgroundRes(R.id.rl_coupon_root, R.drawable.voucher_used);
          helper.setVisible(R.id.tv_coupon_op, false);
          helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_99));
        } else {
          helper.setBackgroundRes(R.id.rl_coupon_root, R.drawable.voucher_expire);
          helper.setVisible(R.id.tv_coupon_op, false);
          helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_99));
        }
        helper.setText(R.id.tv_value, item.getValue()+"");
        helper.setText(R.id.tv_condition, "满"+item.getCondition()+"可用");
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_time, getTime(item.getStart_at())+"-"+getTime(item.getEnd_at()));
        helper.setText(R.id.tv_usage, item.getUsage());
      }
    });

    mCouponAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //ToastUtils.showShort("category--"+mCouponAdapter.getItem(position).getCategory());
        startActivity(new Intent(mContext, ProductListActivity.class));
      }
    });
  }

  @Override
  protected void initView() {
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
    OkGo.<String>get(MallAPI.CLIENT_COUPON)
        .tag(this)
        .params("status", status)
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<Coupon> couponList = JSON.parseArray(jsonObj.getString("items"), Coupon.class);
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mCouponAdapter.setNewData(couponList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+couponList.size());
                if (couponList.size() == 0) {
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

}
