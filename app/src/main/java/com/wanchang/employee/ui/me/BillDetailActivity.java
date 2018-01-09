package com.wanchang.employee.ui.me;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Order;
import com.wanchang.employee.ui.base.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BillDetailActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.rv_credit_bill)
  RecyclerView mCreditBillRv;

  private BaseQuickAdapter<Order.OrderProductListBean, BaseViewHolder> mAdapter;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_credit_bill_list;
  }

  @Override
  protected void initData() {

    mCreditBillRv.setLayoutManager(new LinearLayoutManager(mContext));
    mCreditBillRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_f2)).size(36).build());
    mCreditBillRv.setAdapter(mAdapter = new BaseQuickAdapter<Order.OrderProductListBean, BaseViewHolder>(R.layout.item_bill_detail_list) {
      @Override
      protected void convert(final BaseViewHolder helper, Order.OrderProductListBean item) {
        SuperTextView topStv = helper.getView(R.id.stv_top);
        topStv.setLeftString("¥ "+item.getPrice());
        topStv.setRightTopString("订单号："+item.getOrder_id());
        topStv.setRightBottomString(getTime(item.getCreated_at()));
        SuperTextView bottomStv = helper.getView(R.id.stv_bottom);
        bottomStv.setLeftString(item.getTitle());
        bottomStv.setRightString("x"+item.getCount());
      }
    });

    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        startActivity(new Intent(mContext, BillDetailActivity.class)
            .putExtra("credit_bill_id", mAdapter.getItem(position).getId()));
      }
    });
  }

  private String getTime(long time) {
    return new SimpleDateFormat("yyyy/MM/dd").format(new Date(time*1000));
  }

  @Override
  protected void initView() {
    mTitle.setText("账单明细");

    loadData();
  }


  private void loadData() {
    OkGo.<String>get(MallAPI.ORDER_PRODUCT)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<Order.OrderProductListBean> productList = JSON.parseArray(jsonObj.getString("items"), Order.OrderProductListBean.class);
              mAdapter.setNewData(productList);
            }
          }
        });
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
