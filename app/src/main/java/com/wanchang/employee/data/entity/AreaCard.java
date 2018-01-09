package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2018/1/9.
 */

public class AreaCard {

  /**
   * ship_province : 浙江省
   * ship_city : 杭州市
   * ship_county : 西湖区
   * order_client_num : 3
   * product_num : 11
   * product_count : 354
   * order_num : 18
   * price : 37834.00
   * buy_rate : 30.00
   * re_buy_rate : 25.00
   */

  private String ship_province;
  private String ship_city;
  private String ship_county;
  private int order_client_num;
  private int product_num;
  private String product_count;
  private int order_num;
  private String price;
  private String buy_rate;
  private String re_buy_rate;

  public String getShip_province() {
    return ship_province;
  }

  public void setShip_province(String ship_province) {
    this.ship_province = ship_province;
  }

  public String getShip_city() {
    return ship_city;
  }

  public void setShip_city(String ship_city) {
    this.ship_city = ship_city;
  }

  public String getShip_county() {
    return ship_county;
  }

  public void setShip_county(String ship_county) {
    this.ship_county = ship_county;
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

  public String getRe_buy_rate() {
    return re_buy_rate;
  }

  public void setRe_buy_rate(String re_buy_rate) {
    this.re_buy_rate = re_buy_rate;
  }
}
