package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableProductDep2Adapter;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ProductDep20Item extends AbstractExpandableItem<ProductDep21Item> implements MultiItemEntity {

  public ProductDep2 productDep2;

  public ProductDep20Item(ProductDep2 productDep2) {
    this.productDep2 = productDep2;
  }

  @Override
  public int getItemType() {
    return ExpandableProductDep2Adapter.TYPE_LEVEL_0;
  }

  @Override
  public int getLevel() {
    return 0;
  }

}
