package com.wanchang.employee.ui.me;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Batch;
import com.wanchang.employee.data.entity.Order;
import com.wanchang.employee.data.entity.Order.OrderProductListBean;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import me.shaohui.bottomdialog.BaseBottomDialog;
import me.shaohui.bottomdialog.BottomDialog;
import me.shaohui.bottomdialog.BottomDialog.ViewListener;
import org.json.JSONArray;
import org.json.JSONException;

public class ApplyReturnProductActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.iv_product_img)
  ImageView mProductIv;
  @BindView(R.id.tv_product_title)
  TextView mProductTitleTv;
  @BindView(R.id.tv_product_manufacture)
  TextView mProductManuTv;
  @BindView(R.id.tv_product_validity)
  TextView mProductValidityTv;
  @BindView(R.id.tv_product_specs)
  TextView mProductSpecsTv;
  @BindView(R.id.tv_product_price)
  TextView mProductPriceTv;

  @BindView(R.id.rv_batch)
  RecyclerView mBatchRv;

  @BindView(R.id.tv_total_price)
  TextView mTotalPriceTv;

  @BindView(R.id.stv_return_reason)
  SuperTextView mReasonStv;


  @BindView(R.id.edt_return_explain)
  EditText mExplainEdt;


  private BaseQuickAdapter<Batch, BaseViewHolder> mBatchAdapter;

  private Order.OrderProductListBean product;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_apply_return_product;
  }

  @Override
  protected void initData() {
    product = (OrderProductListBean) getIntent().getSerializableExtra("orderProduct");

    GlideApp.with(mContext).load(MallAPI.IMG_SERVER+product.getPic()).placeholder(R.drawable.ic_default_image).into(mProductIv);
    mProductTitleTv.setText(product.getTitle());
    mProductManuTv.setText(product.getManufacture_name());
//    String validity = product.getValidity();
//    if (validity.startsWith("0")) {
//      mProductValidityTv.setVisibility(View.INVISIBLE);
//    } else {
//      mProductValidityTv.setVisibility(View.VISIBLE);
//    }
//    mProductValidityTv.setText("有效期至:"+validity);
    mProductSpecsTv.setText("规格："+product.getSpecs());
    mProductPriceTv.setText("￥"+product.getPrice());

    mBatchRv.setLayoutManager(new LinearLayoutManager(mContext));
    mBatchRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mBatchRv.setAdapter(mBatchAdapter = new BaseQuickAdapter<Batch, BaseViewHolder>(R.layout.item_batch_list) {
      @Override
      protected void convert(BaseViewHolder helper, final Batch item) {
        helper.setText(R.id.tv_batch, "批次号："+item.getBatch_id());
        final AnimShopButton animShopBtn = helper.getView(R.id.btn_number);
        animShopBtn.setMaxCount(item.getCount());
        animShopBtn.setOnAddDelListener(new IOnAddDelListener() {
          @Override
          public void onAddSuccess(int i) {
            getTotalPrice();
          }

          @Override
          public void onAddFailed(int i, FailType failType) {

          }

          @Override
          public void onDelSuccess(int i) {
            getTotalPrice();
          }

          @Override
          public void onDelFaild(int i, FailType failType) {

          }
        });
      }
    });
  }

  private void getTotalPrice() {
    int count = 0;
    for (int i = 0; i < mBatchAdapter.getItemCount(); i++) {
      AnimShopButton animShopBtn = (AnimShopButton) mBatchAdapter.getViewByPosition(mBatchRv, i, R.id.btn_number);
      count += animShopBtn.getCount();
    }
    return_price = new DecimalFormat(".00").format(count*Float.parseFloat(product.getPrice()));
    mTotalPriceTv.setText("退款金额："+return_price+"元");
  }

  @Override
  protected void initView() {
    mTitle.setText("申请退货");

    OkGo.<String>get(MallAPI.RETURN_PRODUCT_BATCH)
        .tag(this)
        .params("order_product_id", product.getId())
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<Batch> batchList = JSON.parseArray(jsonObj.getString("batch_list"), Batch.class);
              mBatchAdapter.setNewData(batchList);
            }
            if (response.code() == 400) {
              finish();
            }
          }
        });
  }

  private BaseBottomDialog reasonDialog;
  @OnClick(R.id.stv_return_reason)
  public void onReturnReason() {
    reasonDialog = BottomDialog.create(getSupportFragmentManager())
        .setViewListener(new ViewListener() {
          @Override
          public void bindView(View v) {
            initReasonView(v);
          }
        })
        .setLayoutRes(R.layout.dialog_return_reason_layout)      // dialog layout
        .show();
  }

  private void initReasonView(View v) {
    RelativeLayout closeRl = v.findViewById(R.id.rl_close);
    closeRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        reasonDialog.dismiss();
      }
    });
    RecyclerView couponRv = v.findViewById(R.id.rv_coupon);
    couponRv.setLayoutManager(new LinearLayoutManager(mContext));
    couponRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    final BaseQuickAdapter<String, BaseViewHolder> mCouponAdapter;
    List<String> couponList = new ArrayList<>();
    couponList.add("商品破损");
    couponList.add("质量召回");
    couponList.add("质量原因");
    couponList.add("商品滞销");
    couponList.add("卖家协商");
    couponRv.setAdapter(mCouponAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_return_reason_list, couponList) {

      @Override
      protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title, item);
      }
    });
    mCouponAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mReasonStv.setRightString(mCouponAdapter.getItem(position));
        reason = position+1;
        reasonDialog.dismiss();
      }
    });
  }

  private int reason = 1;
  private String return_price = "0";

  @OnClick(R.id.tv_submit)
  public void onSubmit() {
    List<Batch> itemList = mBatchAdapter.getData();
    LogUtils.e("-----"+itemList.size());
    org.json.JSONObject jsonObj = new org.json.JSONObject();
    JSONArray jsonArray = new JSONArray();
    try {
      jsonObj.put("order_product_id", product.getId());
      jsonObj.put("explain", mExplainEdt.getText().toString().trim());
      jsonObj.put("reason", reason);
      jsonObj.put("return_price", return_price);
      jsonObj.put("batch_info", jsonArray);
      for (int i = 0; i < itemList.size(); i++) {
        org.json.JSONObject jsonObjItem = new org.json.JSONObject();
        jsonObjItem.put("batch_id", itemList.get(i).getBatch_id());
        AnimShopButton animShopBtn = (AnimShopButton) mBatchAdapter.getViewByPosition(mBatchRv, i, R.id.btn_number);
        jsonObjItem.put("count", animShopBtn.getCount());
        jsonArray.put(jsonObjItem);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    LogUtils.e("000000===="+jsonObj);
    OkGo.<String>post(MallAPI.RETURN_PRODUCT_RETURN)
        .tag(this)
        .upJson(jsonObj)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            ToastUtils.showShort("申请成功");
            finish();
          }
        });
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
