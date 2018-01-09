package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableAreaCardAdapter;

/**
 * Created by stormlei on 2017/6/30.
 */

public class AreaCard0Item extends AbstractExpandableItem<AreaCard1Item> implements MultiItemEntity {

  public AreaCard areaCard;

  public AreaCard0Item(AreaCard areaCard) {
    this.areaCard = areaCard;
  }

  @Override
  public int getItemType() {
    return ExpandableAreaCardAdapter.TYPE_LEVEL_0;
  }

  @Override
  public int getLevel() {
    return 0;
  }

}
