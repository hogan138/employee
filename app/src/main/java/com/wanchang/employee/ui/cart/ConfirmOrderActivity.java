package com.wanchang.employee.ui.cart;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kyleduo.switchbutton.SwitchButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.DialogParams;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Address;
import com.wanchang.employee.data.entity.Cart_DataList;
import com.wanchang.employee.data.entity.Cart_ItemList;
import com.wanchang.employee.data.entity.CheckOut;
import com.wanchang.employee.data.entity.Coupon;
import com.wanchang.employee.data.entity.Express;
import com.wanchang.employee.data.entity.PayMethod;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.GlideApp;
import java.util.ArrayList;
import java.util.List;
import me.shaohui.bottomdialog.BaseBottomDialog;
import me.shaohui.bottomdialog.BottomDialog;
import me.shaohui.bottomdialog.BottomDialog.ViewListener;

public class ConfirmOrderActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

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

  @BindView(R.id.stv_express_method)
  SuperTextView mExpressMethodStv;
  @BindView(R.id.stv_coupon)
  SuperTextView mCouponStv;
  @BindView(R.id.tv_balance)
  TextView mBalanceTv;

  @BindView(R.id.sb_balance)
  SwitchButton mBalanceSb;

  @BindView(R.id.stv_pay_method)
  SuperTextView mPayMethodStv;


  @BindView(R.id.edt_remark)
  EditText mRemarkEdt;

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


  private BaseQuickAdapter<Cart_ItemList, BaseViewHolder> mProductAdapter;

  private CheckOut checkout;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_confirm_order;
  }

  @Override
  protected void initData() {
    mProductRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<Cart_ItemList, BaseViewHolder>(R.layout.item_confirm_order) {

      @Override
      protected void convert(BaseViewHolder helper, Cart_ItemList item) {
        ImageView productIv = helper.getView(R.id.iv_pic);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
      }
    });
  }

  @Override
  protected void initView() {
    mTitleTv.setText("确认订单");

    getOrderInfo();

    mBalanceSb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        isBalance = b;
        getOrderInfo();
      }
    });
  }

  private void getOrderInfo() {
    OkGo.<String>post(MallAPI.CHECKOUT)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(final Response<String> response) {
            super.onSuccess(response);

            if (response.code() == 200) {
              checkout = JSON.parseObject(response.body(), CheckOut.class);

              Address address = checkout.getAddress();
              if (address != null) {
                mNameTv.setText(address.getName());
                mTelTv.setText(address.getTel());
                mAddressTv.setText(address.getProvince()+address.getCity()+address.getCounty()+address.getAddress());
                address_id = address.getId();
              }

              int count = 0;
              List<Cart_DataList> dataList = checkout.getShopping_cart().getData_list();
              List<Cart_ItemList> data = new ArrayList<>();
              for (Cart_DataList dataListItem : dataList) {
                count += dataListItem.getShopping_cart_item_list().size();
                data.addAll(dataListItem.getShopping_cart_item_list());
              }
              mProductAdapter.setNewData(data);
              mProductCountTv.setText("共"+count+"种");

              mProductCountTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                  startActivity(new Intent(mContext, CheckoutProductListActivity.class).putExtra("response", response.body()));
                }
              });

              CheckOut.SummaryBean summary = checkout.getSummary();
              mOtotalTv.setText("¥"+summary.getTotal_origin());
              mTotalCutTv.setText("-¥"+summary.getTotal_cut());
              mCouponCutTv.setText("-¥"+summary.getCoupon_cut());
              mBalanceCutTv.setText("-¥"+summary.getBalance_cut());
              String source = "合计：￥"+summary.getTotal()+"</font>";
              mTotalTv.setText(Html.fromHtml(source));

              mExpressMethodStv.setRightString(checkout.getExpress_type_list().get(0).getName());
              express_method = checkout.getExpress_type_list().get(0).getType();

              if (coupon_id != 0) {
                mCouponStv.setRightString("-￥"+checkout.getCoupon_list().getCan_use().get(0).getValue());
              } else {
                mCouponStv.setRightString(checkout.getCoupon_list().getCan_use().size()+"张可用");
              }


              mBalanceTv.setText("可用余额 "+checkout.getBalance()+"元");


              mPayMethodList = checkout.getPay_type_list();

            }

            if (response.code() == 400) {
              finish();
            }
          }

          @Override
          public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
            if (isBalance) {
              request.params("use_balance", 1);
            }
            if (coupon_id != 0) {
              request.params("coupon_id", coupon_id);
            }

          }
        });
  }

  @OnClick(R.id.stv_express_method)
  public void onExpressMethod() {
    final List<Express> expressList = checkout.getExpress_type_list();
    List<String> tempList = new ArrayList<>();
    for (Express express : expressList) {
      tempList.add(express.getName());
    }
    new CircleDialog.Builder(ConfirmOrderActivity.this)
        .configDialog(new ConfigDialog() {
          @Override
          public void onConfig(DialogParams params) {
            params.gravity = Gravity.CENTER;
          }
        })
        .setItems(tempList, new OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mExpressMethodStv.setRightString(expressList.get(i).getName());
            express_method = expressList.get(i).getType();
          }
        })
        .show();

  }

  @OnClick(R.id.stv_coupon)
  public void onCoupon() {
    startActivityForResult(new Intent(mContext, UseCouponListActivity.class)
        .putParcelableArrayListExtra("canUseCouponList",
            (ArrayList<? extends Parcelable>) checkout.getCoupon_list().getCan_use())
        .putParcelableArrayListExtra("unUseCouponList",
            (ArrayList<? extends Parcelable>) checkout.getCoupon_list().getUn_use())
        .putExtra("selected_coupon_id", coupon_id), 0x200);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == 0x200) {
      Coupon coupon = data.getParcelableExtra("coupon");
      if (coupon != null) {
        mCouponStv.setRightString("-￥"+coupon.getValue());
        coupon_id = coupon.getId();
      } else {
        mCouponStv.setRightString(checkout.getCoupon_list().getCan_use().size()+"张可用");
        coupon_id = 0;
      }
      getOrderInfo();
    }
  }

  private List<PayMethod> mPayMethodList;
  private BaseBottomDialog payMethodDialog;
  @OnClick(R.id.stv_pay_method)
  public void onPayMethod() {
    payMethodDialog = BottomDialog.create(getSupportFragmentManager())
        .setViewListener(new ViewListener() {
          @Override
          public void bindView(View v) {
            initPromotionView(v);
          }
        })
        .setLayoutRes(R.layout.dialog_pay_method_layout)      // dialog layout
        .show();
  }

  private void initPromotionView(View v) {
    RelativeLayout closeRl = v.findViewById(R.id.rl_close);
    closeRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        payMethodDialog.dismiss();
      }
    });

    RelativeLayout creditRl = v.findViewById(R.id.rl_credit);
    SuperTextView creditStv = v.findViewById(R.id.stv_credit);
    RelativeLayout mayiRl = v.findViewById(R.id.rl_mayi);
    RelativeLayout alipayRl = v.findViewById(R.id.rl_alipay);
    RelativeLayout unionpayRl = v.findViewById(R.id.rl_unionpay);
    RelativeLayout wechatRl = v.findViewById(R.id.rl_wechat);

    for (int i = 0; i < mPayMethodList.size(); i++) {
      if (mPayMethodList.get(i).getPay_type() == 10) {
        creditStv.setLeftTopString(mPayMethodList.get(i).getName());
        creditStv.setLeftBottomString(mPayMethodList.get(i).getMessage());
      }
    }

    creditRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        pay_method = 10;
        mPayMethodStv.setRightString("授信支付");
        payMethodDialog.dismiss();
      }
    });
    mayiRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        pay_method = 101;
        mPayMethodStv.setRightString("蚂蚁金融");
        payMethodDialog.dismiss();
      }
    });
    alipayRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        pay_method = 20;
        mPayMethodStv.setRightString("支付宝支付");
        payMethodDialog.dismiss();
      }
    });
    unionpayRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        pay_method = 40;
        mPayMethodStv.setRightString("银联支付");
        payMethodDialog.dismiss();
      }
    });
    wechatRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        pay_method = 30;
        mPayMethodStv.setRightString("微信支付");
        payMethodDialog.dismiss();
      }
    });

  }

//  private int getPayMethod() {
//    for (int i = 0; i < mPayMethodList.size(); i++) {
//      if (mPayMethodList.get(i).getPay_type() == 10) {
//
//      }
//    }
//    return 0;
//  }

  private int address_id;
  private boolean isBalance;
  private int coupon_id;
  private int express_method;
  private int pay_method;

  @OnClick(R.id.tv_submit)
  public void onSubmit() {
    OkGo.<String>post(MallAPI.ORDER)
        .tag(this)
        .params("address_id", address_id)
        .params("use_balance", isBalance ? 1 : -1)
        .params("coupon_id", coupon_id)
        .params("remarks", mRemarkEdt.getText().toString().trim())
        .params("ship_method", express_method)
        .params("pay_method", pay_method)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              finish();
            }
          }
        });

  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
