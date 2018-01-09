package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2018/1/9.
 */

public class ClientCard {


  /**
   * client_id : 10
   * name : 帮帮药店
   * province : 浙江省
   * city : 杭州市
   * county : 西湖区
   * category : 30
   * product_num : 4
   * product_count : 260
   * order_num : 7
   * price : 16170.00
   * tag : 社区药店
   */

  private int client_id;
  private String name;
  private String province;
  private String city;
  private String county;
  private int category;
  private int product_num;
  private String product_count;
  private int order_num;
  private String price;
  private String tag;

  public int getClient_id() {
    return client_id;
  }

  public void setClient_id(int client_id) {
    this.client_id = client_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
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

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }
}
