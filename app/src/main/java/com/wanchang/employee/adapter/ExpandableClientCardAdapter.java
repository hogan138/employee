package com.wanchang.employee.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.ClientCard0Item;
import com.wanchang.employee.data.entity.ClientCard1Item;
import java.util.List;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ExpandableClientCardAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

  public static final int TYPE_LEVEL_0 = 0;
  public static final int TYPE_LEVEL_1 = 1;


  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  public ExpandableClientCardAdapter(List<MultiItemEntity> data) {
    super(data);
    addItemType(TYPE_LEVEL_0, R.layout.item_client_card_lv0);
    addItemType(TYPE_LEVEL_1, R.layout.item_client_card_lv1);
  }

  @Override
  protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
    switch (helper.getItemViewType()) {
      case TYPE_LEVEL_0:
        final ClientCard0Item lv0 = (ClientCard0Item)item;

        helper.setText(R.id.tv_name, lv0.clientCard.getName());
        helper.setText(R.id.tv_client_id, "ID: "+lv0.clientCard.getClient_id());
        helper.setText(R.id.tv_province_city_county, lv0.clientCard.getProvince()+lv0.clientCard.getCity()+lv0.clientCard.getCounty());
        helper.setText(R.id.tv_tag, "".equals(lv0.clientCard.getTag()) ? "无":lv0.clientCard.getTag());
        if (lv0.clientCard.getCategory() == 10) {
          helper.setText(R.id.tv_category, "商业");
        } else if(lv0.clientCard.getCategory() == 20) {
          helper.setText(R.id.tv_category, "连锁");
        } else if(lv0.clientCard.getCategory() == 30) {
          helper.setText(R.id.tv_category, "单店");
        }

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
        final ClientCard1Item lv1 = (ClientCard1Item)item;
        helper.setText(R.id.tv_price, ""+lv1.clientCard.getPrice());
        helper.setText(R.id.tv_order_num, ""+lv1.clientCard.getOrder_num());
        helper.setText(R.id.tv_product_num, ""+lv1.clientCard.getProduct_num());
        helper.setText(R.id.tv_product_count, ""+lv1.clientCard.getProduct_count());
        break;

    }
  }
}
