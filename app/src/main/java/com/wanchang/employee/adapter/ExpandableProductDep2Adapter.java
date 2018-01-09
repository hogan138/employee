package com.wanchang.employee.adapter;

import android.view.View;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.entity.ProductDep20Item;
import com.wanchang.employee.data.entity.ProductDep21Item;
import java.util.List;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ExpandableProductDep2Adapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

  public static final int TYPE_LEVEL_0 = 0;
  public static final int TYPE_LEVEL_1 = 1;


  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  public ExpandableProductDep2Adapter(List<MultiItemEntity> data) {
    super(data);
    addItemType(TYPE_LEVEL_0, R.layout.item_product_dep_2_lv0);
    addItemType(TYPE_LEVEL_1, R.layout.item_product_dep_2_lv1);
  }

  @Override
  protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
    switch (helper.getItemViewType()) {
      case TYPE_LEVEL_0:
        final ProductDep20Item lv0 = (ProductDep20Item)item;

        helper.setText(R.id.tv_title, lv0.productDep2.getTitle());
        helper.setText(R.id.tv_promotion_id, "ID："+lv0.productDep2.getPromotion_id());
        if (lv0.productDep2.getCategory() == 10) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_10);
        } else if(lv0.productDep2.getCategory() == 20) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_20);
        } else if(lv0.productDep2.getCategory() == 30) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_30);
        } else if(lv0.productDep2.getCategory() == 40) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_40);
        } else if(lv0.productDep2.getCategory() == 50) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_50);
        } else if(lv0.productDep2.getCategory() == 60) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_60);
        } else if(lv0.productDep2.getCategory() == 70) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_70);
        }


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
        final ProductDep21Item lv1 = (ProductDep21Item)item;
        helper.setText(R.id.tv_price, lv1.productDep2.getPrice());
        helper.setText(R.id.tv_product_count, ""+lv1.productDep2.getProduct_count());
        helper.setText(R.id.tv_order_num, ""+lv1.productDep2.getOrder_num());
        helper.setText(R.id.tv_product_num, ""+lv1.productDep2.getProduct_num());
        helper.setText(R.id.tv_client_num, ""+lv1.productDep2.getClient_num());
        break;

    }
  }
}
