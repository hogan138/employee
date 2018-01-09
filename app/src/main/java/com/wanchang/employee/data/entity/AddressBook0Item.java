package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableAddressBookAdapter;

/**
 * Created by stormlei on 2017/6/30.
 */

public class AddressBook0Item extends AbstractExpandableItem<AddressBook1Item> implements MultiItemEntity {

  public String title;
  public int count;

  public AddressBook0Item(String title, int count) {
    this.title = title;
    this.count = count;
  }

  @Override
  public int getItemType() {
    return ExpandableAddressBookAdapter.TYPE_LEVEL_0;
  }

  @Override
  public int getLevel() {
    return 0;
  }

}
