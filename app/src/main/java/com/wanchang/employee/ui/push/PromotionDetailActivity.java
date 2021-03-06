package com.wanchang.employee.ui.push;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Product;
import com.wanchang.employee.data.entity.Promotion;
import com.wanchang.employee.ui.MainActivity;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.ui.classify.ProductDetailActivity;
import com.wanchang.employee.util.GlideApp;
import com.youth.banner.loader.ImageLoader;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class PromotionDetailActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.iv_pic)
  ImageView mPicIv;

  @BindView(R.id.tv_go)
  TextView mGoTv;

  @BindView(R.id.rv_product)
  RecyclerView mProductRv;

  @BindView(R.id.iv_cart)
  ImageView mCartIv;

  private Badge mQBadgeView;

  private BaseQuickAdapter<Product, BaseViewHolder> mProductAdapter;

  private String key;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_promotion_view;
  }

  @Override
  protected void initData() {
    key = getIntent().getStringExtra("key");

    mQBadgeView = new QBadgeView(mContext).bindTarget(mCartIv);
    mQBadgeView.setExactMode(true);

    mProductRv.setLayoutManager(new LinearLayoutManager(mContext));
    mProductRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<Product, BaseViewHolder>(R.layout.item_product_list) {
      @Override
      protected void convert(final BaseViewHolder helper, final Product item) {
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
        if (item.getPrice() == 0) {
          helper.setText(R.id.tv_product_price, "暂无销售");
        } else {
          helper.setText(R.id.tv_product_price, "¥"+item.getPrice());
        }
        TextView oPriceTv = helper.getView(R.id.tv_product_oprice);
        oPriceTv.setText("¥"+item.getOprice());
        oPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        List<Promotion> promotionList = item.getPromotion_list();
        if (promotionList != null && promotionList.size() >= 1) {
          helper.setVisible(R.id.tv_product_promotion, true);
          int promotionCategory = promotionList.get(0).getCategory();
          if (promotionCategory == 10) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_10);
          } else if(promotionCategory == 20) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_20);
          } else if(promotionCategory == 30) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_30);
          } else if(promotionCategory == 40) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_40);
          } else if(promotionCategory == 50) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_50);
          } else if(promotionCategory == 60) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_60);
          } else if(promotionCategory == 70) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_70);
          }
        } else {
          helper.setVisible(R.id.tv_product_promotion, false);
        }

        final AnimShopButton animShopBtn = helper.getView(R.id.btn_product_number);
        animShopBtn.setCount(item.getShopping_cart_count());
        animShopBtn.setOnAddDelListener(new IOnAddDelListener() {
          @Override
          public void onAddSuccess(int i) {
            int realCount = animShopBtn.getCount() - 1;
            animShopBtn.setCount(realCount);
            int count = realCount + item.getPackaing();
            AddShoppingCart(item.getProduct_sku_id(), count);
          }

          @Override
          public void onAddFailed(int i, FailType failType) {

          }

          @Override
          public void onDelSuccess(int i) {
            int realCount = animShopBtn.getCount() + 1;
            if (realCount <= item.getPackaing()) {
              animShopBtn.setCount(realCount);
              ToastUtils.showShort("最低购买数量为"+realCount);
              return;
            }
            animShopBtn.setCount(realCount);
            int count = realCount - item.getPackaing();
            AddShoppingCart(item.getProduct_sku_id(), count);
          }

          @Override
          public void onDelFaild(int i, FailType failType) {

          }
        });
      }
    });

    mProductAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        startActivity(new Intent(mContext, ProductDetailActivity.class)
            .putExtra("product_sku_id", mProductAdapter.getItem(position).getProduct_sku_id()));
      }
    });
  }

  private void AddShoppingCart(int product_sku_id, int packaing) {
    OkGo.<String>post(MallAPI.SHOPPING_CART_ADD)
        .tag(this)
        .params("product_sku_id", product_sku_id)
        .params("count", packaing)
        .params("simple", 1)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              mQBadgeView.setBadgeNumber(jsonObj.getIntValue("shopping_cart_count"));
              loadData();
            }
          }
        });
  }

  @Override
  protected void initView() {
    mTitle.setText("促销");

    mGoTv.setAlpha(0.5f);
    loadData();
  }


  private void loadData() {
    OkGo.<String>get(MallAPI.PROMOTION_VIEW)
        .tag(this)
        .params("key", key)
        .params("per-page", "100")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              Promotion promotion = JSON.parseObject(jsonObj.getString("promotion"), Promotion.class);
              List<Product> productList = JSON.parseArray(jsonObj.getString("product_list"), Product.class);

              String pic = promotion.getPic_app();
              GlideApp.with(mContext).load(MallAPI.IMG_SERVER + pic).placeholder(R.drawable.ic_default_image).into(mPicIv);

              mGoTv.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date(promotion.getEnd_at()*1000))+" 结束");

              mProductAdapter.setNewData(productList);
            }
          }
        });
  }

  private static class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
      GlideApp.with(context).load(path).placeholder(R.drawable.ic_default_image).into(imageView);
    }
  }

  @OnClick(R.id.iv_cart)
  public void onOpenCart() {
    openActivity(new Intent(mContext, MainActivity.class).putExtra("go_cart", true), true);
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
