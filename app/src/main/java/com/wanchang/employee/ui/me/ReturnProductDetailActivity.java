package com.wanchang.employee.ui.me;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.ReturnProduct;
import com.wanchang.employee.ui.base.BaseActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReturnProductDetailActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.tv_order_id)
  TextView mOrderIdTv;
  @BindView(R.id.tv_order_time)
  TextView mTimeTv;
  @BindView(R.id.tv_return_status)
  TextView mStatusTv;
  @BindView(R.id.tv_reject_reason)
  TextView mRejectTv;
  @BindView(R.id.tv_price)
  TextView mPriceTv;
  @BindView(R.id.tv_reason)
  TextView mReasonTv;
  @BindView(R.id.tv_explain)
  TextView mExplainTv;


  private ReturnProduct mReturnProduct;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_return_product_detail;
  }

  @Override
  protected void initData() {
    mReturnProduct = (ReturnProduct) getIntent().getSerializableExtra("returnProduct");
  }

  @Override
  protected void initView() {
    mTitle.setText("退货详情");

    mOrderIdTv.setText("退货单号: "+mReturnProduct.getId());
    mTimeTv.setText("申请时间: "+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(mReturnProduct.getCreated_at()*1000)));

    if (mReturnProduct.getStatus() == -1) {
      mStatusTv.setText("审核拒绝");
      mRejectTv.setVisibility(View.VISIBLE);
      mRejectTv.setText(mReturnProduct.getReject_reason());
    } else if (mReturnProduct.getStatus() == 50) {
      mStatusTv.setText("退款中");
    } else if (mReturnProduct.getStatus() == 100) {
      mStatusTv.setText("已退款");
    } else {
      mStatusTv.setText("审核中");
    }

    mPriceTv.setText("￥"+mReturnProduct.getReturn_price());
    if (mReturnProduct.getReason() == 1) {
      mReasonTv.setText("商品破损");
    } else if (mReturnProduct.getReason() == 2) {
      mReasonTv.setText("质量召回");
    } else if (mReturnProduct.getReason() == 3) {
      mReasonTv.setText("质量原因");
    } else if (mReturnProduct.getReason() == 4) {
      mReasonTv.setText("商品滞销");
    } else if (mReturnProduct.getReason() == 5) {
      mReasonTv.setText("与卖家协商");
    }

    mExplainTv.setText(mReturnProduct.getExplain());
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
