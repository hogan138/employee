package com.wanchang.employee.adapter;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.ProductDep10Item;
import com.wanchang.employee.data.entity.ProductDep11Item;
import com.wanchang.employee.ui.report.SalesmanReportActivity;
import com.wanchang.employee.util.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ExpandableProductDep1Adapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

  public static final int TYPE_LEVEL_0 = 0;
  public static final int TYPE_LEVEL_1 = 1;


  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  public ExpandableProductDep1Adapter(List<MultiItemEntity> data) {
    super(data);
    addItemType(TYPE_LEVEL_0, R.layout.item_product_dep_1_lv0);
    addItemType(TYPE_LEVEL_1, R.layout.item_product_dep_1_lv1);
  }

  @Override
  protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
    switch (helper.getItemViewType()) {
      case TYPE_LEVEL_0:
        final ProductDep10Item lv0 = (ProductDep10Item)item;

        CircleImageView productIv = helper.getView(R.id.iv_pic);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER + lv0.productDep1.getPic())
            .placeholder(R.drawable.ic_default_image).into(productIv);
        helper.setText(R.id.tv_name, lv0.productDep1.getName());
        helper.setText(R.id.tv_id, "ID："+lv0.productDep1.getId());
        helper.setText(R.id.tv_mobile, "手机号："+lv0.productDep1.getMobile());
        helper.setText(R.id.tv_id_card, "身份证："+lv0.productDep1.getId_card());


        helper.setImageResource(R.id.iv_lv0_icon, lv0.isExpanded() ? R.drawable.icon_arrow_up : R.drawable.icon_arrow_down);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            int pos = helper.getAdapterPosition();
            if (lv0.isExpanded()) {
              collapse(pos, false);
            } else {
              expand(pos, false);
            }
          }
        });
        break;
      case TYPE_LEVEL_1:
        final ProductDep11Item lv1 = (ProductDep11Item)item;
        helper.setText(R.id.tv_client_num, ""+lv1.productDep1.getClient_num());
        helper.setText(R.id.tv_product_count, lv1.productDep1.getProduct_count());
        helper.setText(R.id.tv_order_num, ""+lv1.productDep1.getOrder_num());
        helper.setText(R.id.tv_price, ""+lv1.productDep1.getPrice());
        helper.setText(R.id.tv_product_num, ""+lv1.productDep1.getProduct_num());
        helper.setOnClickListener(R.id.tv_scan_detail, new OnClickListener() {
          @Override
          public void onClick(View view) {
            mContext.startActivity(new Intent(mContext, SalesmanReportActivity.class)
                .putExtra("salesman_id", lv1.productDep1.getId()));
          }
        });
        break;

    }
  }
}
