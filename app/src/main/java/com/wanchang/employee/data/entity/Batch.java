package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/12/17.
 */

public class Batch {

  private int id;
  private int order_product_id;
  private int batch_id;
  private int count;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getOrder_product_id() {
    return order_product_id;
  }

  public void setOrder_product_id(int order_product_id) {
    this.order_product_id = order_product_id;
  }

  public int getBatch_id() {
    return batch_id;
  }

  public void setBatch_id(int batch_id) {
    this.batch_id = batch_id;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
