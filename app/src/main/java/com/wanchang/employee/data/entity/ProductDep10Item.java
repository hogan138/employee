package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableProductDep1Adapter;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ProductDep10Item extends AbstractExpandableItem<ProductDep11Item> implements MultiItemEntity {

  public ProductDep1 productDep1;

  public ProductDep10Item(ProductDep1 productDep1) {
    this.productDep1 = productDep1;
  }

  @Override
  public int getItemType() {
    return ExpandableProductDep1Adapter.TYPE_LEVEL_0;
  }

  @Override
  public int getLevel() {
    return 0;
  }

}
