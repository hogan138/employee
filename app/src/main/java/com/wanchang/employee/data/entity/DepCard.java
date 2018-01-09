package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2018/1/9.
 */

public class DepCard {


  /**
   * department_id : 1002
   * name : 万昌产品部
   * order_client_num : 5
   * product_num : 10
   * product_count : 470
   * order_num : 29
   * price : 38678.00
   * buy_rate : 3.87
   */

  private int department_id;
  private String name;
  private int order_client_num;
  private int product_num;
  private String product_count;
  private int order_num;
  private String price;
  private String buy_rate;

  public int getDepartment_id() {
    return department_id;
  }

  public void setDepartment_id(int department_id) {
    this.department_id = department_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getOrder_client_num() {
    return order_client_num;
  }

  public void setOrder_client_num(int order_client_num) {
    this.order_client_num = order_client_num;
  }

  public int getProduct_num() {
    return product_num;
  }

  public void setProduct_num(int product_num) {
    this.product_num = product_num;
  }

  public String getProduct_count() {
    return product_count;
  }

  public void setProduct_count(String product_count) {
    this.product_count = product_count;
  }

  public int getOrder_num() {
    return order_num;
  }

  public void setOrder_num(int order_num) {
    this.order_num = order_num;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getBuy_rate() {
    return buy_rate;
  }

  public void setBuy_rate(String buy_rate) {
    this.buy_rate = buy_rate;
  }
}
