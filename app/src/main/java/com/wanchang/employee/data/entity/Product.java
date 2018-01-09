package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/6/28.
 */

public class Product {

  /**
   * product_sku_id : 391
   * product_id : 131
   * batch_number :
   * validity : 正常效期
   * oprice : 87.85
   * title : 小儿氨酚黄那敏颗粒
   * pic : upload/pic/2016/10/26/5f76cb3e88f1239d5469160290212a07af2e161b.jpg
   * specs : 5g*12袋
   * packaing : 99999盒
   * min_purchase_count : 1
   * mul_purchase : 1
   * price : 52
   * promotions : [{"title":"2017打折","category":20},{"title":"2017限时秒杀","category":30},{"title":"2017特价专区","category":40},{"title":"2017整单立减","category":50}]
   */

  private int product_sku_id;
  private int product_id;
  private String manufacture_name;
  private String validity;
  private String oprice;
  private String title;
  private String pic;
  private String specs;
  private int packaing;
  private int min_purchase_count;
  private int mul_purchase;
  private int recommend;
  private float price;
  private List<Promotion> promotion_list;
  private List<Coupon> coupon_list;
  private int shopping_cart_count;
  private String price_limit;
  private String client_price;
  private float bonus;

  public int getProduct_sku_id() {
    return product_sku_id;
  }

  public void setProduct_sku_id(int product_sku_id) {
    this.product_sku_id = product_sku_id;
  }

  public int getProduct_id() {
    return product_id;
  }

  public void setProduct_id(int product_id) {
    this.product_id = product_id;
  }

  public String getManufacture_name() {
    return manufacture_name;
  }

  public void setManufacture_name(String manufacture_name) {
    this.manufacture_name = manufacture_name;
  }

  public String getValidity() {
    return validity;
  }

  public void setValidity(String validity) {
    this.validity = validity;
  }

  public String getOprice() {
    return oprice;
  }

  public void setOprice(String oprice) {
    this.oprice = oprice;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public String getSpecs() {
    return specs;
  }

  public void setSpecs(String specs) {
    this.specs = specs;
  }

  public int getPackaing() {
    return packaing;
  }

  public void setPackaing(int packaing) {
    this.packaing = packaing;
  }

  public int getMin_purchase_count() {
    return min_purchase_count;
  }

  public void setMin_purchase_count(int min_purchase_count) {
    this.min_purchase_count = min_purchase_count;
  }

  public int getMul_purchase() {
    return mul_purchase;
  }

  public void setMul_purchase(int mul_purchase) {
    this.mul_purchase = mul_purchase;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public List<Promotion> getPromotion_list() {
    return promotion_list;
  }

  public void setPromotion_list(List<Promotion> promotion_list) {
    this.promotion_list = promotion_list;
  }

  public List<Coupon> getCoupon_list() {
    return coupon_list;
  }

  public void setCoupon_list(List<Coupon> coupon_list) {
    this.coupon_list = coupon_list;
  }

  public int getShopping_cart_count() {
    return shopping_cart_count;
  }

  public void setShopping_cart_count(int shopping_cart_count) {
    this.shopping_cart_count = shopping_cart_count;
  }

  public String getPrice_limit() {
    return price_limit;
  }

  public void setPrice_limit(String price_limit) {
    this.price_limit = price_limit;
  }

  public String getClient_price() {
    return client_price;
  }

  public void setClient_price(String client_price) {
    this.client_price = client_price;
  }

  public float getBonus() {
    return bonus;
  }

  public void setBonus(float bonus) {
    this.bonus = bonus;
  }

  public int getRecommend() {
    return recommend;
  }

  public void setRecommend(int recommend) {
    this.recommend = recommend;
  }
}
