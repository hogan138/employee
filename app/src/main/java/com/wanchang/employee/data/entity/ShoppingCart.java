package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/7/4.
 */

public class ShoppingCart {

  private List<Cart_DataList> data_list;
  private Cart_Summary summary;
  private int checked_all;

  public List<Cart_DataList> getData_list() {
    return data_list;
  }

  public void setData_list(List<Cart_DataList> data_list) {
    this.data_list = data_list;
  }

  public Cart_Summary getSummary() {
    return summary;
  }

  public void setSummary(Cart_Summary summary) {
    this.summary = summary;
  }

  public int getChecked_all() {
    return checked_all;
  }

  public void setChecked_all(int checked_all) {
    this.checked_all = checked_all;
  }
}
