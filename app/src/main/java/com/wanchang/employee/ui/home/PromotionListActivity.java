package com.wanchang.employee.ui.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Promotion;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.ui.classify.ProductDetailActivity;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;

public class PromotionListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.rv_promotion_1)
  RecyclerView m1Rv;

  private BaseQuickAdapter<Promotion, BaseViewHolder> m1Adapter;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_promotion_list;
  }

  @Override
  protected void initData() {
    m1Rv.setLayoutManager(new LinearLayoutManager(mContext));
    m1Rv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_f2)).size(36).build());
    m1Rv.setAdapter(m1Adapter = new BaseQuickAdapter<Promotion, BaseViewHolder>(R.layout.item_promotion_1) {

      @Override
      protected void convert(BaseViewHolder helper, Promotion item) {
        ImageView picIv = helper.getView(R.id.iv_pic);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER + item.getPic()).placeholder(R.drawable.ic_default_image).into(picIv);

        TextView goTv = helper.getView(R.id.tv_go);
        goTv.setAlpha(0.5f);

        RecyclerView m2Rv = helper.getView(R.id.rv_promotion_2);
        m2Rv.setLayoutManager(new GridLayoutManager(mContext, 3));
        final BaseQuickAdapter<Promotion.ProductListBean, BaseViewHolder> m2Adapter;
        m2Rv.setAdapter(m2Adapter = new BaseQuickAdapter<Promotion.ProductListBean, BaseViewHolder>(R.layout.item_promotion_2, item.getProduct_list()) {

          @Override
          protected void convert(BaseViewHolder helper, Promotion.ProductListBean item) {
            ImageView mPic = helper.getView(R.id.iv_pic);
            GlideApp.with(mContext).load(MallAPI.IMG_SERVER + item.getPic()).placeholder(R.drawable.ic_default_image).into(mPic);
            helper.setText(R.id.tv_title, item.getTitle());
            String validity = item.getValidity();
            if (validity.startsWith("0")) {
              helper.setVisible(R.id.tv_validity, false);
            } else {
              helper.setVisible(R.id.tv_validity, true);
            }
            helper.setText(R.id.tv_validity, "有效期至:"+validity);
            if (item.getPrice() == 0) {
              helper.setText(R.id.tv_price, "暂无销售");
            } else {
              helper.setText(R.id.tv_price, "¥"+item.getPrice());
            }
          }
        });

        m2Adapter.setOnItemClickListener(new OnItemClickListener() {
          @Override
          public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            int id = m2Adapter.getItem(position).getProduct_sku_id();
            openActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("product_sku_id", id), false);
          }
        });
      }
    });

    m1Adapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MallApp.getInstance().cmdNavigation(mContext, m1Adapter.getItem(position).getCmd());
      }
    });
  }

  @Override
  protected void initView() {
    mTitleTv.setText("促销专区");

    OkGo.<String>get(MallAPI.PROMOTION)
        .tag(this)
        .params("per-page", "100")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<Promotion> promotionList = JSON.parseArray(response.body(), Promotion.class);
              m1Adapter.setNewData(promotionList);
            }
          }
        });
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
