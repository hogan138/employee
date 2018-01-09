package com.wanchang.employee.ui.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
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
import com.wanchang.employee.data.entity.ReturnProduct;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReturnProductHistoryFragment extends BaseFragment {


  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_order)
  RecyclerView mOrderRv;

  public BaseQuickAdapter<ReturnProduct, BaseViewHolder> mAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;


  public ReturnProductHistoryFragment() {
    // Required empty public constructor
  }

  public static ReturnProductHistoryFragment newInstance() {
    Bundle args = new Bundle();
    ReturnProductHistoryFragment fragment = new ReturnProductHistoryFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_return_product_card;
  }

  @Override
  protected void initData() {

    mOrderRv.setLayoutManager(new LinearLayoutManager(mContext));
    mOrderRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).size(36).build());
    mOrderRv.setAdapter(mAdapter = new BaseQuickAdapter<ReturnProduct, BaseViewHolder>(R.layout.item_return_product_history_list) {
      @Override
      protected void convert(BaseViewHolder helper, ReturnProduct item) {
        helper.setText(R.id.tv_order_id, "退货单号: "+item.getId());
        ImageView productIv = helper.getView(R.id.iv_product_img);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getProduct().getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
        helper.setText(R.id.tv_product_title, item.getProduct().getTitle());
        helper.setText(R.id.tv_product_manufacture, item.getProduct().getManufacture_name());
        TextView mProductCountTv = helper.getView(R.id.tv_product_count);
        mProductCountTv.setText("x"+item.getCount());
//            String validity = item.getProduct().getValidity();
//            if (validity.startsWith("0")) {
//              helper.setVisible(R.id.tv_product_validity, false);
//            } else {
//              helper.setVisible(R.id.tv_product_validity, true);
//            }
//            helper.setText(R.id.tv_product_validity, "有效期至:"+validity);
        helper.setGone(R.id.tv_product_validity, false);
        helper.setText(R.id.tv_product_specs, "规格："+item.getProduct().getSpecs());
        if (item.getStatus() == -1) {
          helper.setText(R.id.tv_return_status, "审核拒绝");
        } else if (item.getStatus() == 50) {
          helper.setText(R.id.tv_return_status, "退款中");
        } else if (item.getStatus() == 100) {
          helper.setText(R.id.tv_return_status, "已退款");
        } else {
          helper.setText(R.id.tv_return_status, "审核中");
        }

        helper.addOnClickListener(R.id.ll_return_status);
      }
    });

    mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
          case R.id.ll_return_status:
            startActivity(new Intent(mContext, ReturnProductDetailActivity.class)
                .putExtra("returnProduct", mAdapter.getItem(position))
            );
            break;
        }
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
    OkGo.<String>get(MallAPI.RETURN_PRODUCT)
        .tag(this)
        .params("expand", "product")
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<ReturnProduct> orderList = JSON.parseArray(jsonObj.getString("items"), ReturnProduct.class);
              JSONObject metaObj = JSON.parseObject(jsonObj.getString("_meta"));
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mAdapter.setNewData(orderList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+orderList.size());
                if (currentPage > metaObj.getIntValue("pageCount")) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mAdapter.addData(orderList);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }
        });
  }

}
