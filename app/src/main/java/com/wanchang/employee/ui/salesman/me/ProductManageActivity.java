package com.wanchang.employee.ui.salesman.me;

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
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mcxtzhang.lib.AnimShopButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Product;
import com.wanchang.employee.data.entity.Promotion;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;

public class ProductManageActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_product)
  RecyclerView mProductRv;


  private BaseQuickAdapter<Product, BaseViewHolder> mProductAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_product_manage;
  }

  @Override
  protected void initData() {
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
        //helper.setText(R.id.tv_product_price, "".equals(MallApp.getInstance().getToken()) ? "登录可见" : "¥"+item.getPrice());
        if ("".equals(MallApp.getInstance().getToken())) {
          helper.setText(R.id.tv_product_price, "登录可见");
        } else {
          if (item.getPrice() == 0) {
            helper.setText(R.id.tv_product_price, "暂无销售");
          } else {
            helper.setText(R.id.tv_product_price, "¥"+item.getPrice());
          }
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

        helper.setText(R.id.tv_product_specs, "规格："+item.getSpecs());

        AnimShopButton animShopBtn = helper.getView(R.id.btn_product_number);
        animShopBtn.setVisibility(View.GONE);
      }
    });

    mProductAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        startActivity(new Intent(mContext, ProductDetail_SalesmanActivity.class)
            .putExtra("product_sku_id", mProductAdapter.getItem(position).getProduct_sku_id()));
      }
    });
  }



  @Override
  protected void initView() {
    mTitleTv.setText("商品管理");

    loadData();

    refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_MORE;
        currentPage++;
        loadData();
      }

      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_REFRESH;
        currentPage = 1;
        loadData();
      }
    });
  }


  private void loadData() {
    OkGo.<String>get(MallAPI.PRODUCT_LIST)
        .tag(this)
        .params("salesman_limit", "1")
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<Product> productList = JSON.parseArray(response.body(), Product.class);
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mProductAdapter.setNewData(productList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+productList.size());
                if (productList.size() == 0) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mProductAdapter.addData(productList);
                  refreshLayout.finishLoadmore();
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
}
