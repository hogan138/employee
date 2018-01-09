package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.adapter.ExpandableProductDep0Adapter;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ProductDep01Item implements MultiItemEntity {

  public ProductDep0 productDep0;


  public ProductDep01Item(ProductDep0 productDep0) {
    this.productDep0 = productDep0;
  }


  @Override
  public int getItemType() {
    return ExpandableProductDep0Adapter.TYPE_LEVEL_1;
  }
}
