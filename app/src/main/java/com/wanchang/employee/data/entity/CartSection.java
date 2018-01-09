package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by stormlei on 2017/7/4.
 */

public class CartSection extends SectionEntity<Cart_ItemList> {

  private int type;
  private int is_promotion_satisfy;
  private String key;

  public CartSection(boolean isHeader, String header, int type, int is_promotion_satisfy, String key) {
    super(isHeader, header);
    this.type = type;
    this.is_promotion_satisfy = is_promotion_satisfy;
    this.key = key;
  }

  public CartSection(Cart_ItemList cart_itemList) {
    super(cart_itemList);
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public int getIs_promotion_satisfy() {
    return is_promotion_satisfy;
  }

  public void setIs_promotion_satisfy(int is_promotion_satisfy) {
    this.is_promotion_satisfy = is_promotion_satisfy;
  }
}
