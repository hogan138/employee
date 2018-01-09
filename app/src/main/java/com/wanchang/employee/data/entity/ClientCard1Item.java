package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableClientCardAdapter;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ClientCard1Item implements MultiItemEntity {

  public ClientCard clientCard;


  public ClientCard1Item(ClientCard clientCard) {
    this.clientCard = clientCard;
  }


  @Override
  public int getItemType() {
    return ExpandableClientCardAdapter.TYPE_LEVEL_1;
  }
}
