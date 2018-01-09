package com.wanchang.employee.ui.me;

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
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.mylhyl.circledialog.CircleDialog;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.MyCollection;
import com.wanchang.employee.data.entity.Promotion;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.ui.classify.ProductDetailActivity;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;

public class CollectionListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.rv_product)
  RecyclerView mProductRv;

  private BaseQuickAdapter<MyCollection, BaseViewHolder> mProductAdapter;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_collection_list;
  }

  @Override
  protected void initData() {

    mProductRv.setLayoutManager(new LinearLayoutManager(mContext));
    mProductRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<MyCollection, BaseViewHolder>(R.layout.item_collection_list) {
      @Override
      protected void convert(final BaseViewHolder helper, final MyCollection item) {
        ImageView productIv = helper.getView(R.id.iv_product_img);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getData().getProduct().getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
        helper.setText(R.id.tv_product_title, item.getData().getProduct().getTitle());
        helper.setText(R.id.tv_product_manufacture, item.getData().getProduct().getManufacture_name());
        String validity = item.getData().getProduct_sku().getValidity();
        if (validity.startsWith("0")) {
          helper.setVisible(R.id.tv_product_validity, false);
        } else {
          helper.setVisible(R.id.tv_product_validity, true);
        }
        helper.setText(R.id.tv_product_validity, "有效期至:"+validity);
        helper.setText(R.id.tv_product_price, "¥"+item.getData().getPrice().getPrice());
        TextView oPriceTv = helper.getView(R.id.tv_product_oprice);
        oPriceTv.setText("¥"+item.getData().getProduct().getOprice());
        oPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        List<Promotion> promotionList = item.getData().getPromotion_list();
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
        animShopBtn.setCount(0);
        animShopBtn.setOnAddDelListener(new IOnAddDelListener() {
          @Override
          public void onAddSuccess(int i) {
            int realCount = animShopBtn.getCount() - 1;
            animShopBtn.setCount(realCount);
            AddShoppingCart(item.getData().getProduct_sku().getId(), item.getData().getProduct().getPackaing());
          }

          @Override
          public void onAddFailed(int i, FailType failType) {

          }

          @Override
          public void onDelSuccess(int i) {
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
            .putExtra("product_sku_id", mProductAdapter.getItem(position).getData().getProduct_sku().getId()));
      }
    });

    mProductAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        delCollection(mProductAdapter.getItem(position).getId());
        return false;
      }
    });
  }

  private void delCollection(final int id) {
    new CircleDialog.Builder(this)
        .setTitle("提示")
        .setText("您确定要删除吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>delete(MallAPI.COLLECTION+"/"+id)
                .tag(this)
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                      ToastUtils.showShort("已删除");
                      loadData();
                    }
                  }
                });
          }
        }).show();

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
              ToastUtils.showShort("已加入购物车");
            }
          }
        });
  }

  @Override
  protected void initView() {
    mTitle.setText("我的收藏");

    loadData();
  }


  private void loadData() {
    OkGo.<String>get(MallAPI.COLLECTION)
        .tag(this)
        .params("expand", "data")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<MyCollection> productList = JSON.parseArray(jsonObj.getString("items"), MyCollection.class);
              mProductAdapter.setNewData(productList);
            }
          }
        });
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
