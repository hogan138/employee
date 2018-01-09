package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/7/4.
 */

public class Cart_GiftList {
  /**
   * gift_type : 10
   * data : 1
   * gift_name : 测试赠品
   * count : 105
   */

  private int gift_type;
  private String data;
  private String gift_name;
  private int count;

  public int getGift_type() {
    return gift_type;
  }

  public void setGift_type(int gift_type) {
    this.gift_type = gift_type;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getGift_name() {
    return gift_name;
  }

  public void setGift_name(String gift_name) {
    this.gift_name = gift_name;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
