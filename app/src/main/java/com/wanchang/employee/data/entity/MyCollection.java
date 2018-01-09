package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/12/14.
 */

public class MyCollection {

  private int id;
  private int data_type;
  private int data_id;
  private ProductDetail data;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getData_type() {
    return data_type;
  }

  public void setData_type(int data_type) {
    this.data_type = data_type;
  }

  public int getData_id() {
    return data_id;
  }

  public void setData_id(int data_id) {
    this.data_id = data_id;
  }

  public ProductDetail getData() {
    return data;
  }

  public void setData(ProductDetail data) {
    this.data = data;
  }
}
