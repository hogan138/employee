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
import com.wanchang.employee.data.entity.Order;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.classify.ProductDetailActivity;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReturnProductApplyFragment extends BaseFragment {


  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_order)
  RecyclerView mOrderRv;

  public BaseQuickAdapter<Order, BaseViewHolder> mOrderAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;


  public ReturnProductApplyFragment() {
    // Required empty public constructor
  }

  public static ReturnProductApplyFragment newInstance() {
    Bundle args = new Bundle();
    ReturnProductApplyFragment fragment = new ReturnProductApplyFragment();
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
    mOrderRv.setAdapter(mOrderAdapter = new BaseQuickAdapter<Order, BaseViewHolder>(R.layout.item_return_product_apply_list) {
      @Override
      protected void convert(BaseViewHolder helper, Order item) {
        helper.setText(R.id.tv_order_id, "订单号: "+item.getId());
        helper.setText(R.id.tv_order_time, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(item.getCreated_at()*1000)));

        final BaseQuickAdapter<Order.OrderProductListBean, BaseViewHolder> mProductAdapter;
        RecyclerView mProductRv = helper.getView(R.id.rv_product);
        mProductRv.setLayoutManager(new LinearLayoutManager(mContext));
        mProductRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
            getResources().getColor(R.color.color_e5)).build());
        List<Order.OrderProductListBean> dataList = item.getProduct();

        mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<Order.OrderProductListBean, BaseViewHolder>(R.layout.item_return_product_item_list, dataList) {

          @Override
          protected void convert(BaseViewHolder helper, Order.OrderProductListBean item) {
            ImageView productIv = helper.getView(R.id.iv_product_img);
            GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
            helper.setText(R.id.tv_product_title, item.getTitle());
            helper.setText(R.id.tv_product_manufacture, item.getManufacture_name());
            TextView mProductCountTv = helper.getView(R.id.tv_product_count);
            mProductCountTv.setText("x"+item.getCount());
//            String validity = item.getValidity();
//            if (validity.startsWith("0")) {
//              helper.setVisible(R.id.tv_product_validity, false);
//            } else {
//              helper.setVisible(R.id.tv_product_validity, true);
//            }
//            helper.setText(R.id.tv_product_validity, "有效期至:"+validity);
            helper.setGone(R.id.tv_product_validity, false);
            helper.setText(R.id.tv_product_specs, "规格："+item.getSpecs());

            if (item.getReturn_date() > System.currentTimeMillis()) {
              helper.setBackgroundRes(R.id.tv_return_product_op, R.drawable.tv_cc_storke_3_shape);
              helper.setTextColor(R.id.tv_return_product_op, getResources().getColor(R.color.color_cc));
              helper.getView(R.id.tv_return_product_op).setClickable(false);
            } else {
              helper.setBackgroundRes(R.id.tv_return_product_op, R.drawable.tv_red_shape);
              helper.setTextColor(R.id.tv_return_product_op, getResources().getColor(R.color.color_336));
              helper.getView(R.id.tv_return_product_op).setClickable(true);
            }

            helper.addOnClickListener(R.id.tv_return_product_op);
          }
        });

        mProductAdapter.setOnItemClickListener(new OnItemClickListener() {
          @Override
          public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Order.OrderProductListBean product = mProductAdapter.getItem(position);
            int product_sku_id = product.getProduct_sku_id();
            startActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("product_sku_id", product_sku_id));
          }
        });

        mProductAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
          @Override
          public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            switch (view.getId()) {
              case R.id.tv_return_product_op:
                Order.OrderProductListBean product = mProductAdapter.getItem(position);
                startActivity(new Intent(mContext, ApplyReturnProductActivity.class)
                    .putExtra("orderProduct", product)
                );
                break;
            }
          }
        });
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
    OkGo.<String>get(MallAPI.ORDER)
        .tag(this)
        .params("status", "100")
        .params("expand", "product")
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<Order> orderList = JSON.parseArray(jsonObj.getString("items"), Order.class);
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mOrderAdapter.setNewData(orderList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+orderList.size());
                if (orderList.size() == 0) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mOrderAdapter.addData(orderList);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }
        });
  }

}
