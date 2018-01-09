package com.wanchang.employee.ui.me;

import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mcxtzhang.lib.AnimShopButton;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Order;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;

public class OrderProductListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;
  @BindView(R.id.tv_topbar_right)
  TextView mRightTv;


  @BindView(R.id.tv_gift)
  TextView mGiftTv;

  @BindView(R.id.rv_product)
  RecyclerView mProductRv;

  private BaseQuickAdapter<Order.OrderProductListBean, BaseViewHolder> mProductAdapter;

  private String orderId;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_order_product_list;
  }

  @Override
  protected void initData() {

    orderId = getIntent().getStringExtra("order_id");

    mProductRv.setLayoutManager(new LinearLayoutManager(mContext));
    mProductRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<Order.OrderProductListBean, BaseViewHolder>(R.layout.item_order_product_list) {
      @Override
      protected void convert(final BaseViewHolder helper, final Order.OrderProductListBean item) {
        if (item.getIs_gift() == 1) {
          helper.setGone(R.id.tv_is_gift, true);
          helper.setAlpha(R.id.tv_is_gift, 0.5f);
        } else {
          helper.setGone(R.id.tv_is_gift, false);
        }
        ImageView productIv = helper.getView(R.id.iv_product_img);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
        helper.setText(R.id.tv_product_title, item.getTitle());
        helper.setText(R.id.tv_product_manufacture, item.getManufacture_name());
        String validity = item.getValidity();
        if (validity.startsWith("0")) {
          helper.setVisible(R.id.tv_product_validity, false);
        } else {
          helper.setVisible(R.id.tv_product_validity, true);
        }
        helper.setText(R.id.tv_product_validity, "有效期至:"+validity);
        helper.setText(R.id.tv_product_price, "¥"+item.getPrice());
        TextView oPriceTv = helper.getView(R.id.tv_product_oprice);
        oPriceTv.setText("¥"+item.getPrice_o());
        oPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.tv_product_specs, "规格："+item.getSpecs());

//        List<Promotion> promotionList = item.getPromotion_list();
//        if (promotionList != null && promotionList.size() >= 1) {
//          helper.setVisible(R.id.tv_product_promotion, true);
//          int promotionCategory = promotionList.get(0).getCategory();
//          if (promotionCategory == 10) {
//            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_10);
//          } else if(promotionCategory == 20) {
//            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_20);
//          } else if(promotionCategory == 30) {
//            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_30);
//          } else if(promotionCategory == 40) {
//            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_40);
//          } else if(promotionCategory == 50) {
//            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_50);
//          } else if(promotionCategory == 60) {
//            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_60);
//          } else if(promotionCategory == 70) {
//            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_70);
//          }
//        } else {
//          helper.setVisible(R.id.tv_product_promotion, false);
//        }

        final AnimShopButton animShopBtn = helper.getView(R.id.btn_product_number);
        animShopBtn.setVisibility(View.GONE);
      }
    });

//    mProductAdapter.setOnItemClickListener(new OnItemClickListener() {
//      @Override
//      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//        startActivity(new Intent(mContext, ProductDetailActivity.class)
//            .putExtra("product_sku_id", mProductAdapter.getItem(position).getProduct_sku_id()));
//      }
//    });
  }

  @Override
  protected void initView() {
    mTitleTv.setText("商品清单");

    loadData();
  }


  private void loadData() {
    OkGo.<String>get(MallAPI.ORDER+"/"+orderId)
        .tag(this)
        .params("expand", "product,gift")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              Order order = JSON.parseObject(response.body(), Order.class);
              List<Order.OrderProductListBean> dataList = order.getProduct();
              mProductAdapter.setNewData(dataList);
              mRightTv.setText("共"+dataList.size()+"件");
              mGiftTv.setText("赠品 "+ ("".equals(order.getGift()) ? "无": order.getGift()));
            }
          }
        });
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
