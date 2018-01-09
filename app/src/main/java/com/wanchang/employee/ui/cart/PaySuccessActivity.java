package com.wanchang.employee.ui.cart;

import android.content.Intent;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.wanchang.employee.R;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.ui.me.OrderDetailActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaySuccessActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;
  @BindView(R.id.tv_topbar_right)
  TextView mRightTv;


  @BindView(R.id.tv_order_id)
  TextView mOrderIdTv;
  @BindView(R.id.tv_pay_method)
  TextView mPayMethodTv;

  @BindView(R.id.tv_price)
  TextView mPriceTv;
  @BindView(R.id.tv_order_time)
  TextView mTimeTv;


  private String orderId;
  private String payMethod;
  private String price;
  private long time;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_pay_success;
  }

  @Override
  protected void initData() {
    orderId = getIntent().getStringExtra("order_id");
    payMethod = getIntent().getStringExtra("pay_method");
    price = getIntent().getStringExtra("price");
    time = getIntent().getLongExtra("time", 0);

    mOrderIdTv.setText(orderId);
    mPayMethodTv.setText("授信支付");
    mPriceTv.setText("成功支付￥"+price);
    mTimeTv.setText(new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date(time*1000)));
  }

  @Override
  protected void initView() {
    mTitleTv.setText("支付订单");
    mRightTv.setText("完成");


  }

  @OnClick(R.id.tv_topbar_right)
  public void onRight() {
    startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("order_id", orderId));
    finish();
  }

  @OnClick(R.id.tv_order_info)
  public void onOrderDetail() {
    startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("order_id", orderId));
    finish();
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}


