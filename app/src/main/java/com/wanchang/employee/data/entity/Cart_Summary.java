package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/7/4.
 */

public class Cart_Summary {
  private float total_origin;
  private float total;
  private float total_cut;
  private int count;

  public float getTotal_origin() {
    return total_origin;
  }

  public void setTotal_origin(float total_origin) {
    this.total_origin = total_origin;
  }

  public float getTotal() {
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  public float getTotal_cut() {
    return total_cut;
  }

  public void setTotal_cut(float total_cut) {
    this.total_cut = total_cut;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
