package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableAddressBookAdapter;

/**
 * Created by stormlei on 2017/6/30.
 */

public class AddressBook1Item implements MultiItemEntity {

  public Colleague colleague;


  public AddressBook1Item(Colleague colleague) {
    this.colleague = colleague;
  }


  @Override
  public int getItemType() {
    return ExpandableAddressBookAdapter.TYPE_LEVEL_1;
  }
}
