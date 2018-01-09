package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableCategoryAdapter;
import java.util.List;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ExpandableCategory2Item implements MultiItemEntity {

  public ExpandableCategory2Item(List<Category2Item> titles) {
    this.titles = titles;
  }

  public List<Category2Item> titles;

  @Override
  public int getItemType() {
    return ExpandableCategoryAdapter.TYPE_LEVEL_1;
  }
}
