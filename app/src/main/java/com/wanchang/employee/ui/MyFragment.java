package com.wanchang.employee.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allen.library.SuperTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.User;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.me.CollectionListActivity;
import com.wanchang.employee.ui.me.CouponListActivity;
import com.wanchang.employee.ui.me.CreditBillListActivity;
import com.wanchang.employee.ui.me.HistoryBuyListActivity;
import com.wanchang.employee.ui.me.OrderListActivity;
import com.wanchang.employee.ui.me.ReturnProductListActivity;
import com.wanchang.employee.ui.me.SettingActivity;
import com.wanchang.employee.util.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment {

  @BindView(R.id.civ_avatar)
  CircleImageView mAvatarCiv;
  @BindView(R.id.tv_name)
  TextView mNameTv;
  @BindView(R.id.tv_credit)
  TextView mCreditTv;
  @BindView(R.id.tv_balance)
  TextView mBalanceTv;

  @BindView(R.id.stv_coupon)
  SuperTextView mCouponStv;


  private static final String BUNDLE_ARGS = "bundle_args";

  public MyFragment() {
    // Required empty public constructor
  }

  public static MyFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    MyFragment fragment = new MyFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_my;
  }

  @Override
  protected void initData() {
  }

  @Override
  protected void initView() {
    loadData();
  }

  private void loadData() {
    OkGo.<String>get(MallAPI.USER+"/profile")
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              User user = JSON.parseObject(jsonObj.getString("user_info"), User.class);
              GlideApp.with(mContext).load(MallAPI.IMG_SERVER+user.getPic()).placeholder(R.drawable.ic_default_image).into(mAvatarCiv);
              mNameTv.setText(user.getName());
              JSONObject clientObj = JSON.parseObject(jsonObj.getString("client_info"));
              mCreditTv.setText(clientObj.getString("credit")+"元");
              mBalanceTv.setText(clientObj.getString("balance")+"元");
              mCouponStv.setRightString(clientObj.getString("coupon_count")+"张");
            }
          }
        });
  }

  @OnClick(R.id.iv_setting)
  public void onSetting() {
    startActivity(new Intent(mContext, SettingActivity.class));
  }

  @OnClick(R.id.ll_order_status_10)
  public void onOrder10() {
    startActivity(new Intent(mContext,OrderListActivity.class).putExtra("position", 1));
  }

  @OnClick(R.id.ll_order_status_20)
  public void onOrder40() {
    startActivity(new Intent(mContext,OrderListActivity.class).putExtra("position", 2));
  }

  @OnClick(R.id.ll_order_status_40)
  public void onOrder100() {
    startActivity(new Intent(mContext,OrderListActivity.class).putExtra("position", 3));
  }

  @OnClick(R.id.ll_return_goods)
  public void onReturnGoods() {
    startActivity(new Intent(mContext, ReturnProductListActivity.class));
  }

  @OnClick(R.id.stv_history_buy)
  public void onHistoryBuy() {
    startActivity(new Intent(mContext,HistoryBuyListActivity.class));
  }

  @OnClick(R.id.stv_coupon)
  public void onCoupon() {
    startActivity(new Intent(mContext, CouponListActivity.class));
  }

  @OnClick(R.id.stv_credit_bill)
  public void onCreditBill() {
    startActivity(new Intent(mContext, CreditBillListActivity.class));
  }

  @OnClick(R.id.ll_order_all)
  public void onOrderAll() {
    startActivity(new Intent(mContext, OrderListActivity.class));
  }

  @OnClick(R.id.stv_collection)
  public void onCollection() {
    startActivity(new Intent(mContext, CollectionListActivity.class));
  }

  @OnClick(R.id.stv_inviting_colleagues)
  public void onInvitingColleagues() {
  }

}
