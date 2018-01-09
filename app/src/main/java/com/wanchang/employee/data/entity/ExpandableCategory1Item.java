package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableCategoryAdapter;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ExpandableCategory1Item extends AbstractExpandableItem<ExpandableCategory2Item> implements MultiItemEntity {

  public String title;
  public int id;

  public ExpandableCategory1Item(String title, int id) {
    this.title = title;
    this.id = id;
  }

  @Override
  public int getItemType() {
    return ExpandableCategoryAdapter.TYPE_LEVEL_0;
  }

  @Override
  public int getLevel() {
    return 0;
  }

}
