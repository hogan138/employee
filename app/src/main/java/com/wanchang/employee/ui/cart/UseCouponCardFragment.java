package com.wanchang.employee.ui.cart;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.Coupon;
import com.wanchang.employee.ui.base.BaseFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UseCouponCardFragment extends BaseFragment {


  @BindView(R.id.rv_coupon)
  RecyclerView mCouponRv;


  public BaseQuickAdapter<Coupon, BaseViewHolder> mCouponAdapter;

  private int current;
  private List<Coupon> couponList;
  private int selected_coupon_id;

  public UseCouponCardFragment() {
    // Required empty public constructor
  }

  public static UseCouponCardFragment newInstance(int position, List<Coupon> couponList, int selected_coupon_id) {
    Bundle args = new Bundle();
    args.putInt("position", position);
    args.putParcelableArrayList("couponList", (ArrayList<? extends Parcelable>) couponList);
    args.putInt("selected_coupon_id", selected_coupon_id);
    UseCouponCardFragment fragment = new UseCouponCardFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_use_coupon_card;
  }

  @Override
  protected void initData() {
    current = getArguments().getInt("position");
    couponList = getArguments().getParcelableArrayList("couponList");
    selected_coupon_id = getArguments().getInt("selected_coupon_id");

    mCouponRv.setLayoutManager(new LinearLayoutManager(mContext));
    mCouponRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mCouponRv.setAdapter(mCouponAdapter = new BaseQuickAdapter<Coupon, BaseViewHolder>(R.layout.item_use_coupon_list, couponList) {
      @Override
      protected void convert(BaseViewHolder helper, Coupon item) {
        if (current == 0) {
          if (selected_coupon_id == item.getId()) {
            helper.setBackgroundRes(R.id.rl_coupon_root, R.drawable.voucher_s);
          } else {
            helper.setBackgroundRes(R.id.rl_coupon_root, R.drawable.voucher_n);
          }
          helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_33));
        } else {
          helper.setBackgroundRes(R.id.rl_coupon_root, R.drawable.voucher_d);
          helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_99));
        }
        helper.setText(R.id.tv_value, item.getValue()+"");
        helper.setText(R.id.tv_condition, "满"+item.getCondition()+"可用");
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_time, getTime(item.getStart_at())+"—"+getTime(item.getEnd_at()));
        helper.setText(R.id.tv_usage, item.getUsage());
      }
    });

    mCouponAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (current == 0) {
          Intent data = new Intent();
          if (selected_coupon_id == mCouponAdapter.getItem(position).getId()) {
            view.setBackgroundResource(R.drawable.voucher_n);
          } else {
            view.setBackgroundResource(R.drawable.voucher_s);
            data.putExtra("coupon", mCouponAdapter.getItem(position));
          }
          mContext.setResult(Activity.RESULT_OK, data);
          mContext.finish();
        }
      }
    });
  }

  @Override
  protected void initView() {

  }

  private String getTime(long time) {
    return new SimpleDateFormat("yyyy.MM.dd").format(new Date(time*1000));
  }

}
