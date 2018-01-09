package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableDepCardAdapter;

/**
 * Created by stormlei on 2017/6/30.
 */

public class DepCard1Item implements MultiItemEntity {

  public DepCard depCard;


  public DepCard1Item(DepCard depCard) {
    this.depCard = depCard;
  }


  @Override
  public int getItemType() {
    return ExpandableDepCardAdapter.TYPE_LEVEL_1;
  }
}
