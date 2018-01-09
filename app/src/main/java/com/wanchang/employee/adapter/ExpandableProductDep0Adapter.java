package com.wanchang.employee.adapter;

import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.ProductDep00Item;
import com.wanchang.employee.data.entity.ProductDep01Item;
import com.wanchang.employee.util.GlideApp;
import java.util.List;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ExpandableProductDep0Adapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

  public static final int TYPE_LEVEL_0 = 0;
  public static final int TYPE_LEVEL_1 = 1;


  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  public ExpandableProductDep0Adapter(List<MultiItemEntity> data) {
    super(data);
    addItemType(TYPE_LEVEL_0, R.layout.item_product_dep_0_lv0);
    addItemType(TYPE_LEVEL_1, R.layout.item_product_dep_0_lv1);
  }

  @Override
  protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
    switch (helper.getItemViewType()) {
      case TYPE_LEVEL_0:
        final ProductDep00Item lv0 = (ProductDep00Item)item;

        ImageView productIv = helper.getView(R.id.iv_product_img);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER + lv0.productDep0.getPic())
            .placeholder(R.drawable.ic_default_image).into(productIv);
        helper.setText(R.id.tv_product_title, lv0.productDep0.getTitle());
        helper.setText(R.id.tv_product_id, "ID："+lv0.productDep0.getId());
        helper.setText(R.id.tv_product_manufacture, lv0.productDep0.getManufacture_name());
        helper.setText(R.id.tv_product_specs, "规格："+lv0.productDep0.getSpecs());


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
        final ProductDep01Item lv1 = (ProductDep01Item)item;
        helper.setText(R.id.tv_price, lv1.productDep0.getPrice());
        helper.setText(R.id.tv_re_buy_rate, lv1.productDep0.getRe_buy_rate());
        helper.setText(R.id.tv_product_count, lv1.productDep0.getProduct_count());
        helper.setText(R.id.tv_order_client_num, ""+lv1.productDep0.getOrder_client_num());
        helper.setText(R.id.tv_buy_rate, lv1.productDep0.getBuy_rate());
        break;

    }
  }
}
