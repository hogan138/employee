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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.CreditBill;
import com.wanchang.employee.ui.base.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CreditBillListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.rv_credit_bill)
  RecyclerView mCreditBillRv;

  private BaseQuickAdapter<CreditBill, BaseViewHolder> mAdapter;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_credit_bill_list;
  }

  @Override
  protected void initData() {

    mCreditBillRv.setLayoutManager(new LinearLayoutManager(mContext));
    mCreditBillRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).size(36).build());
    mCreditBillRv.setAdapter(mAdapter = new BaseQuickAdapter<CreditBill, BaseViewHolder>(R.layout.item_credit_bill_list) {
      @Override
      protected void convert(final BaseViewHolder helper, CreditBill item) {
        helper.setText(R.id.tv_price, "¥ "+item.getPrice());
        helper.setText(R.id.tv_date, "记账周期 " + getTime(item.getBill_start())+"-"+getTime(item.getBill_end()));
        helper.setText(R.id.tv_repayment_date, "最后还款日：" + new SimpleDateFormat("yyyy年MM月dd日").format(new Date(item.getRepayment_date()*1000)));
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
    mTitle.setText("回款账单");

    loadData();
  }


  private void loadData() {
    OkGo.<String>get(MallAPI.CLIENT_CREDIT_BILL)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<CreditBill> productList = JSON.parseArray(jsonObj.getString("items"), CreditBill.class);
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
