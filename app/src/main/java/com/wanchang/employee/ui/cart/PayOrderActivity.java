package com.wanchang.employee.ui.cart;

import android.content.Intent;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Order;
import com.wanchang.employee.data.entity.PayMethod;
import com.wanchang.employee.ui.base.BaseActivity;
import java.util.List;

public class PayOrderActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.tv_price)
  TextView mPriceTv;

  @BindView(R.id.rl_credit)
  RelativeLayout mCreditRl;

  @BindView(R.id.stv_credit)
  SuperTextView mCreditStv;

  @BindView(R.id.cb_credit)
  CheckBox mCreditCb;
  @BindView(R.id.cb_mayi)
  CheckBox mMayiCb;
  @BindView(R.id.cb_alipay)
  CheckBox mAlipayCb;
  @BindView(R.id.cb_unionpay)
  CheckBox mUnionpayCb;
  @BindView(R.id.cb_wechat)
  CheckBox mWechatCb;

  private BaseQuickAdapter<PayMethod.DataListBean, BaseViewHolder> mAdapter;

  private String orderId;

  private String price;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_pay_order;
  }

  @Override
  protected void initData() {
    orderId = getIntent().getStringExtra("orderId");
  }

  @Override
  protected void initView() {
    mTitleTv.setText("支付订单");
    OkGo.<String>post(MallAPI.ORDER_PAY_METHOD)
        .tag(this)
        .params("order_id", orderId)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              price = jsonObj.getString("price");
              mPriceTv.setText("需要支付 " + price + " 元");

              List<PayMethod> payMethodList = JSON.parseArray(jsonObj.getString("pay_method_list"), PayMethod.class);
              for (int i = 0; i < payMethodList.size(); i++) {
                if (payMethodList.get(i).getPay_type() == 10) {
                  mCreditStv.setLeftBottomString(payMethodList.get(i).getMessage());
                  if (payMethodList.get(i).getStatus() == 100) {
                    mCreditRl.setClickable(true);
                  } else {
                    mCreditRl.setClickable(false);
                  }
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

  @OnClick(R.id.rl_credit)
  public void onCredit() {
    LogUtils.e("+++++"+mCreditCb.isChecked());
    if (mCreditCb.isChecked()) {
      mCreditCb.setChecked(false);
    } else {
      mCreditCb.setChecked(true);
      mMayiCb.setChecked(false);
      mAlipayCb.setChecked(false);
      mUnionpayCb.setChecked(false);
      mWechatCb.setChecked(false);
    }
  }

  @OnClick(R.id.rl_mayi)
  public void onMayi() {
    LogUtils.e("+++++"+mMayiCb.isChecked());
    if (mMayiCb.isChecked()) {
      mMayiCb.setChecked(false);
    } else {
      mCreditCb.setChecked(false);
      mMayiCb.setChecked(true);
      mAlipayCb.setChecked(false);
      mUnionpayCb.setChecked(false);
      mWechatCb.setChecked(false);
    }
  }

  @OnClick(R.id.rl_alipay)
  public void onAliPay() {
    LogUtils.e("----"+mAlipayCb.isChecked());
    if (mAlipayCb.isChecked()) {
      mAlipayCb.setChecked(false);
    } else {
      mCreditCb.setChecked(false);
      mMayiCb.setChecked(false);
      mAlipayCb.setChecked(true);
      mUnionpayCb.setChecked(false);
      mWechatCb.setChecked(false);
    }
  }

  @OnClick(R.id.rl_unionpay)
  public void onUnionpay() {
    LogUtils.e("----"+mUnionpayCb.isChecked());
    if (mUnionpayCb.isChecked()) {
      mUnionpayCb.setChecked(false);
    } else {
      mCreditCb.setChecked(false);
      mMayiCb.setChecked(false);
      mAlipayCb.setChecked(false);
      mUnionpayCb.setChecked(true);
      mWechatCb.setChecked(false);
    }
  }

  @OnClick(R.id.rl_wechat)
  public void onWechat() {
    if (mWechatCb.isChecked()) {
      mWechatCb.setChecked(false);
    } else {
      mCreditCb.setChecked(false);
      mMayiCb.setChecked(false);
      mAlipayCb.setChecked(false);
      mUnionpayCb.setChecked(false);
      mWechatCb.setChecked(true);
    }
  }

  @OnClick(R.id.tv_pay)
  public void onGoPay() {
    if (!mCreditCb.isChecked() && !mMayiCb.isChecked() && !mAlipayCb.isChecked() && !mUnionpayCb.isChecked() && !mWechatCb.isChecked()) {
      ToastUtils.showShort("至少选择一种支付方式");
    } else {
      if (mCreditCb.isChecked()) {
        goCreditPay();
      } else if (mMayiCb.isChecked()) {
        goMayiPay();
      } else if (mAlipayCb.isChecked()) {
        goAliPay();
      } else if (mUnionpayCb.isChecked()) {
        goUnionPay();
      } else {
        goWeChatPay();
      }
    }
  }

  private void goCreditPay() {
    //ToastUtils.showShort("credit");
    OkGo.<String>post(MallAPI.ORDER_PAYED)
        .tag(this)
        .params("order_id", orderId)
        .params("pay_method", "10")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              Order order = JSON.parseObject(jsonObj.getString("order_info"), Order.class);
              startActivity(new Intent(mContext, PaySuccessActivity.class)
                  .putExtra("order_id", orderId)
                  .putExtra("pay_method", "10")
                  .putExtra("price", order.getTotal())
                  .putExtra("time", order.getCreated_at())
              );
              finish();
            }
          }
        });

  }

  private void goMayiPay() {
    //ToastUtils.showShort("mayi");
    OkGo.<String>post(MallAPI.ORDER_PAYED)
        .tag(this)
        .params("order_id", orderId)
        .params("pay_method", "101")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              Order order = JSON.parseObject(jsonObj.getString("order_info"), Order.class);
              startActivity(new Intent(mContext, PaySuccessActivity.class)
                  .putExtra("order_id", orderId)
                  .putExtra("pay_method", "101")
                  .putExtra("price", order.getTotal())
                  .putExtra("time", order.getCreated_at())
              );
              finish();
            }
          }
        });
  }

  private void goAliPay() {
    //ToastUtils.showShort("alipay");
    OkGo.<String>post(MallAPI.ORDER_PAYED)
        .tag(this)
        .params("order_id", orderId)
        .params("pay_method", "20")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              Order order = JSON.parseObject(jsonObj.getString("order_info"), Order.class);
              startActivity(new Intent(mContext, PaySuccessActivity.class)
                  .putExtra("order_id", orderId)
                  .putExtra("pay_method", "20")
                  .putExtra("price", order.getTotal())
                  .putExtra("time", order.getCreated_at())
              );
              finish();
            }
          }
        });
  }

  private void goUnionPay() {
    //ToastUtils.showShort("union");
    OkGo.<String>post(MallAPI.ORDER_PAYED)
        .tag(this)
        .params("order_id", orderId)
        .params("pay_method", "40")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              Order order = JSON.parseObject(jsonObj.getString("order_info"), Order.class);
              startActivity(new Intent(mContext, PaySuccessActivity.class)
                  .putExtra("order_id", orderId)
                  .putExtra("pay_method", "40")
                  .putExtra("price", order.getTotal())
                  .putExtra("time", order.getCreated_at())
              );
              finish();
            }
          }
        });
  }

  private void goWeChatPay() {
    //ToastUtils.showShort("wechat");


    OkGo.<String>post(MallAPI.ORDER_PAYED)
        .tag(this)
        .params("order_id", orderId)
        .params("pay_method", "30")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              Order order = JSON.parseObject(jsonObj.getString("order_info"), Order.class);
              startActivity(new Intent(mContext, PaySuccessActivity.class)
                  .putExtra("order_id", orderId)
                  .putExtra("pay_method", "30")
                  .putExtra("price", order.getTotal())
                  .putExtra("time", order.getCreated_at())
              );
              finish();
            }
          }
        });
  }




}
