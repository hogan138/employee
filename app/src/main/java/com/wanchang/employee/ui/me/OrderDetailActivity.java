package com.wanchang.employee.ui.me;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mylhyl.circledialog.CircleDialog;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Order;
import com.wanchang.employee.data.entity.Order.OrderProductListBean;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.GlideApp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderDetailActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.tv_order_status)
  TextView mOrderStatusTv;
  @BindView(R.id.tv_order_dateline)
  TextView mOrderDatelineTv;

  @BindView(R.id.tv_address_name)
  TextView mNameTv;
  @BindView(R.id.tv_address_tel)
  TextView mTelTv;
  @BindView(R.id.tv_address_address)
  TextView mAddressTv;

  @BindView(R.id.rv_product)
  RecyclerView mProductRv;
  @BindView(R.id.tv_product_count)
  TextView mProductCountTv;

  @BindView(R.id.stv_order_id)
  SuperTextView mOrderIdStv;
  @BindView(R.id.stv_order_user)
  SuperTextView mOrderUserStv;
  @BindView(R.id.stv_order_time)
  SuperTextView mOrderTimeStv;
  @BindView(R.id.stv_pay_method)
  SuperTextView mPayMethodStv;
  @BindView(R.id.stv_express_method)
  SuperTextView mExpressMethodStv;

  @BindView(R.id.tv_ototal)
  TextView mOtotalTv;
  @BindView(R.id.tv_total_cut)
  TextView mTotalCutTv;
  @BindView(R.id.tv_coupon_cut)
  TextView mCouponCutTv;
  @BindView(R.id.tv_balance_cut)
  TextView mBalanceCutTv;
  @BindView(R.id.tv_total)
  TextView mTotalTv;

  @BindView(R.id.tv_cancel_order)
  TextView mCancelOrderTv;
  @BindView(R.id.tv_pay)
  TextView mOrderPayTv;
  @BindView(R.id.tv_shouhuo)
  TextView mShouHuoTv;

  private String orderId;


  private BaseQuickAdapter<Order.OrderProductListBean, BaseViewHolder> mProductAdapter;


  private int payMethod;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_order_detail;
  }

  @Override
  protected void initData() {
    orderId = getIntent().getStringExtra("order_id");
  }

  @Override
  protected void initView() {
    mTitleTv.setText("订单详情");

    getOrderInfo();
  }

  private void getOrderInfo() {
    OkGo.<String>get(MallAPI.ORDER+"/"+orderId)
        .tag(this)
        .params("expand", "product")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              Order order = JSON.parseObject(response.body(), Order.class);

              if (order.getStatus() == 10) {
                mOrderStatusTv.setText("待支付");
              } else if (order.getStatus() == 11) {
                mOrderStatusTv.setText("待审核");
              } else if (order.getStatus() == 20) {
                mOrderStatusTv.setText("待发货");
              } else if (order.getStatus() == 40) {
                mOrderStatusTv.setText("待收货");
              } else if (order.getStatus() == 100) {
                mOrderStatusTv.setText("已完成");
              } else if (order.getStatus() == -1) {
                mOrderStatusTv.setText("审核拒绝");
              } else if (order.getStatus() == 0) {
                mOrderStatusTv.setText("已取消");
              }

              if (order.getStatus() == 10) {
                mOrderDatelineTv.setGravity(View.VISIBLE);
                mOrderDatelineTv.setText("本订单将于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(order.getCancel_at()*1000))+"关闭");
              } else {
                mOrderDatelineTv.setVisibility(View.GONE);
              }

              mNameTv.setText(order.getShip_name());
              mTelTv.setText(order.getShip_tel());
              mAddressTv.setText(order.getShip_province()+order.getShip_city()+order.getShip_county()+order.getShip_address());

              List<OrderProductListBean> dataList = order.getProduct();

              mProductCountTv.setText("共"+dataList.size()+"件");
              mProductRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
              mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<Order.OrderProductListBean,
                  BaseViewHolder>(R.layout.item_confirm_order, dataList) {

                @Override
                protected void convert(BaseViewHolder helper, Order.OrderProductListBean item) {
                  ImageView productIv = helper.getView(R.id.iv_pic);
                  GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
                }
              });

              mProductCountTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                  startActivity(new Intent(mContext, OrderProductListActivity.class)
                      .putExtra("order_id", orderId)
                  );
                }
              });

              mOrderIdStv.setLeftString("订单号："+order.getId());
              mOrderTimeStv.setLeftString("下单时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(order.getCreated_at()*1000)));
              mOrderUserStv.setLeftString("下单人："+order.getOrder_user_name());
              mPayMethodStv.setLeftString("支付方式：授信支付");
              mExpressMethodStv.setLeftString("配送方式：快递送货");

              mOtotalTv.setText("¥"+order.getTotal_o());
              mTotalCutTv.setText("¥"+order.getTotal_cut());
              mCouponCutTv.setText("¥"+order.getCoupon_cut());
              mBalanceCutTv.setText("¥"+order.getBalance_cut());
              String source = "实付款：<font color='#e12929'>¥"+order.getTotal()+"</font>";
              mTotalTv.setText(Html.fromHtml(source));


              payMethod = order.getPay_method();


              if (order.getStatus() == 10) {
                mCancelOrderTv.setVisibility(View.VISIBLE);
                mOrderPayTv.setVisibility(View.VISIBLE);
                mShouHuoTv.setVisibility(View.GONE);
              } else if(order.getStatus() == 40) {
                mCancelOrderTv.setVisibility(View.GONE);
                mOrderPayTv.setVisibility(View.GONE);
                mShouHuoTv.setVisibility(View.VISIBLE);
              } else {
                mCancelOrderTv.setVisibility(View.GONE);
                mOrderPayTv.setVisibility(View.GONE);
                mShouHuoTv.setVisibility(View.GONE);
              }

            }
          }
        });
  }

  @OnClick(R.id.tv_cancel_order)
  public void onCancelOrder() {
    new CircleDialog.Builder(this)
        .setTitle("提示")
        .setText("您确定要取消吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.ORDER_CANCEL)
                .tag(this)
                .params("order_id", orderId)
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                      getOrderInfo();
                    }
                  }
                });
          }
        }).show();
  }

  @OnClick(R.id.tv_pay)
  public void onOrderPay() {
    new CircleDialog.Builder(this)
        .setTitle("提示")
        .setText("您确定要支付吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.ORDER_PAYED)
                .tag(this)
                .params("order_id", orderId)
                .params("pay_method", payMethod)
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                      getOrderInfo();
                    }
                  }
                });
          }
        }).show();
  }

  @OnClick(R.id.tv_shouhuo)
  public void onShouHuo() {
    new CircleDialog.Builder(this)
        .setTitle("提示")
        .setText("您确定要收货吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.ORDER_COMPLETE)
                .tag(this)
                .params("order_id", orderId)
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                      getOrderInfo();
                    }
                  }
                });
          }
        }).show();
  }



  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
