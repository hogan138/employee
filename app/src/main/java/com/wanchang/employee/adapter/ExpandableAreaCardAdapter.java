package com.wanchang.employee.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.AreaCard0Item;
import com.wanchang.employee.data.entity.AreaCard1Item;
import java.util.List;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ExpandableAreaCardAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

  public static final int TYPE_LEVEL_0 = 0;
  public static final int TYPE_LEVEL_1 = 1;


  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  public ExpandableAreaCardAdapter(List<MultiItemEntity> data) {
    super(data);
    addItemType(TYPE_LEVEL_0, R.layout.item_area_card_lv0);
    addItemType(TYPE_LEVEL_1, R.layout.item_area_card_lv1);
  }

  @Override
  protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
    switch (helper.getItemViewType()) {
      case TYPE_LEVEL_0:
        final AreaCard0Item lv0 = (AreaCard0Item)item;

        helper.setText(R.id.tv_ship_province, lv0.areaCard.getShip_province());
        helper.setText(R.id.tv_ship_city_county, lv0.areaCard.getShip_city()+" "+lv0.areaCard.getShip_county());


        helper.setImageResource(R.id.iv_lv0_icon, lv0.isExpanded() ? R.drawable.icon_arrow_up : R.drawable.icon_arrow_down);
        helper.itemView.setOnClickListener(new OnClickListener() {
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
        final AreaCard1Item lv1 = (AreaCard1Item)item;
        helper.setText(R.id.tv_price, ""+lv1.areaCard.getPrice());
        helper.setText(R.id.tv_order_client_num, ""+lv1.areaCard.getOrder_client_num());
        helper.setText(R.id.tv_buy_rate, ""+lv1.areaCard.getBuy_rate());
        helper.setText(R.id.tv_re_buy_rate, ""+lv1.areaCard.getRe_buy_rate());
        helper.setText(R.id.tv_product_num, ""+lv1.areaCard.getProduct_num());
        helper.setText(R.id.tv_product_count, ""+lv1.areaCard.getProduct_count());
        helper.setText(R.id.tv_order_num, ""+lv1.areaCard.getOrder_num());
        break;

    }
  }
}
