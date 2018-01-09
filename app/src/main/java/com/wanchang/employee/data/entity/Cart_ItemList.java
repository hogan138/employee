package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/7/4.
 */

public class Cart_ItemList {


  /**
   * id : 3
   * product_id : 791
   * product_sku_id : 2371
   * count : 270
   * promotion_id : 0
   * checked : 1
   * status : 100
   * price : 90.2
   * title : 小儿氨酚黄那敏颗粒
   * pic : upload/pic/2017/03/22/4020401303d5315228c2ec9bda6c8b1ef4be3c89.jpg
   * manufacture_name : 华润三九(北京)药业有限公司
   * specs : 6g*10袋
   * packaing : 30
   * unit : 盒
   * min_purchase_count : 1
   * mul_purchase : 1
   * validity : 0000-00-00
   * stock : 9999
   */

  private int id;
  private int product_id;
  private int product_sku_id;
  private int count;
  private int promotion_id;
  private int checked;
  private int status;
  private double price;
  private String title;
  private String pic;
  private String manufacture_name;
  private String specs;
  private int packaing;
  private String unit;
  private String validity;
  private int stock;
  private List<Promotion> promotion_list;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getProduct_id() {
    return product_id;
  }

  public void setProduct_id(int product_id) {
    this.product_id = product_id;
  }

  public int getProduct_sku_id() {
    return product_sku_id;
  }

  public void setProduct_sku_id(int product_sku_id) {
    this.product_sku_id = product_sku_id;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getPromotion_id() {
    return promotion_id;
  }

  public void setPromotion_id(int promotion_id) {
    this.promotion_id = promotion_id;
  }

  public int getChecked() {
    return checked;
  }

  public void setChecked(int checked) {
    this.checked = checked;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
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

  public String getManufacture_name() {
    return manufacture_name;
  }

  public void setManufacture_name(String manufacture_name) {
    this.manufacture_name = manufacture_name;
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

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }


  public String getValidity() {
    return validity;
  }

  public void setValidity(String validity) {
    this.validity = validity;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public List<Promotion> getPromotion_list() {
    return promotion_list;
  }

  public void setPromotion_list(List<Promotion> promotion_list) {
    this.promotion_list = promotion_list;
  }
}
