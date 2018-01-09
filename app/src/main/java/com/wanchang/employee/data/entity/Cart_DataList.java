package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/7/4.
 */

public class Cart_DataList {

  /**
   * type : promotion
   * title : 每满10赠1
   * is_reach : 1
   * promotion_hint :
   * count : 355
   * ototal : 1727.05
   * total : 1727.05
   * cut : 0
   * item_list : [{"title":"氨酚伪麻美芬片Ⅱ/氨麻苯美片(白加黑)","pic":"upload/pic/2017/06/07/f32adff423d640de409ea472af82058672b7061b.jpg","manufacture_name":"拜耳医药保健有限公司启东分公司","specs":"325mg*30mg*15mg*10s/325mg*30mg*15mg*5s","packaing":"400盒","unit":"","auth_doc_num":"国药准字H10940250/H10940251","retail_price":"0.00","min_purchase_count":1,"mul_purchase":1,"sales":0,"views":0,"recommend":0,"id":1,"product_id":47959,"count":300,"checked":1,"status":1,"stock":9999,"price":"5.37","total":"1611.00","promotion_list":[{"id":2,"title":"满1000打8折"},{"id":1,"title":"每满10赠1"}]},{"title":"小儿化痰止咳颗粒","pic":"upload/pic/2016/07/11/b190aafcb72c4c4b11f5fbba268390fea64c694f.jpg","manufacture_name":"葵花药业集团(重庆)有限公司","specs":"3g*10袋","packaing":"99999盒","unit":"","auth_doc_num":"国药准字Z51021817","retail_price":"0.00","min_purchase_count":1,"mul_purchase":1,"sales":0,"views":0,"recommend":0,"id":2,"product_id":47958,"count":55,"checked":1,"status":1,"stock":9999,"price":"2.11","total":"116.05","promotion_list":[{"id":2,"title":"满1000打8折"},{"id":1,"title":"每满10赠1"}]}]
   * gift_list : [{"gift_type":10,"data":"1","gift_name":"测试赠品","count":105}]
   */

  private int id;
  private int type;
  private int promotion_id;
  private Promotion promotion;
  private String hint_text;
  private int is_promotion_satisfy;
  private int line_count;
  private float line_oprice;
  private float line_price;
  private float line_price_cut;
  private List<Cart_ItemList> shopping_cart_item_list;
  private List<Cart_GiftList> gift_list;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getPromotion_id() {
    return promotion_id;
  }

  public void setPromotion_id(int promotion_id) {
    this.promotion_id = promotion_id;
  }

  public Promotion getPromotion() {
    return promotion;
  }

  public void setPromotion(Promotion promotion) {
    this.promotion = promotion;
  }

  public String getHint_text() {
    return hint_text;
  }

  public void setHint_text(String hint_text) {
    this.hint_text = hint_text;
  }

  public int getIs_promotion_satisfy() {
    return is_promotion_satisfy;
  }

  public void setIs_promotion_satisfy(int is_promotion_satisfy) {
    this.is_promotion_satisfy = is_promotion_satisfy;
  }

  public int getLine_count() {
    return line_count;
  }

  public void setLine_count(int line_count) {
    this.line_count = line_count;
  }

  public float getLine_oprice() {
    return line_oprice;
  }

  public void setLine_oprice(float line_oprice) {
    this.line_oprice = line_oprice;
  }

  public float getLine_price() {
    return line_price;
  }

  public void setLine_price(float line_price) {
    this.line_price = line_price;
  }

  public float getLine_price_cut() {
    return line_price_cut;
  }

  public void setLine_price_cut(float line_price_cut) {
    this.line_price_cut = line_price_cut;
  }

  public List<Cart_ItemList> getShopping_cart_item_list() {
    return shopping_cart_item_list;
  }

  public void setShopping_cart_item_list(
      List<Cart_ItemList> shopping_cart_item_list) {
    this.shopping_cart_item_list = shopping_cart_item_list;
  }

  public List<Cart_GiftList> getGift_list() {
    return gift_list;
  }

  public void setGift_list(List<Cart_GiftList> gift_list) {
    this.gift_list = gift_list;
  }
}
