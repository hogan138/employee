package com.wanchang.employee.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by stormlei on 2017/9/1.
 */

public class Order implements Serializable {


  /**
   * id : 1709011201496862
   * user_id : 1
   * user_name : 天天
   * shop_id : 1
   * shop_name : 天天好大药房
   * order_user_id : 1
   * order_user_name : 天天
   * total_o : 206.35
   * total : 206.35
   * total_cut : 0.00
   * pay_method : 0
   * ship_method : 0
   * ship_name : 曹田
   * ship_tel : 15558028980
   * ship_province : 浙江省
   * ship_city : 杭州市
   * ship_county : 西湖区
   * ship_address : 蒋村街道西溪国际E308
   * ip : 3232235521
   * client : 20
   * product_count : 10
   * dai : 0
   * status : 10
   * created_at : 1504238509
   * updated_at : 1504238509
   * order_product_list : [{"id":164,"order_id":"1709011201496862","product_id":47959,"fcategory_id":9,"scategory_id":68,"title":"氨酚伪麻美芬片Ⅱ/氨麻苯美片(白加黑)","pic":"upload/pic/2017/06/07/f32adff423d640de409ea472af82058672b7061b.jpg","manufacture_id":0,"manufacture_name":"拜耳医药保健有限公司启东分公司","specs":"325mg*30mg*15mg*10s/325mg*30mg*15mg*5s","packaing":"400盒","unit":"","auth_doc_num":"国药准字H10940250/H10940251","retail_price":"0.00","batch_number":"","price_o":"5.37","price":"5.37","count":2,"total":"10.74","status":10,"created_at":1504238509,"updated_at":1504238509},{"id":165,"order_id":"1709011201496862","product_id":791,"fcategory_id":1,"scategory_id":10,"title":"小儿氨酚黄那敏颗粒","pic":"upload/pic/2017/03/22/4020401303d5315228c2ec9bda6c8b1ef4be3c89.jpg","manufacture_id":0,"manufacture_name":"华润三九(北京)药业有限公司","specs":"6g*10袋","packaing":"120盒","unit":"","auth_doc_num":"国药准字H11022051","retail_price":"0.00","batch_number":"","price_o":"7.47","price":"7.47","count":5,"total":"37.35","status":10,"created_at":1504238509,"updated_at":1504238509},{"id":166,"order_id":"1709011201496862","product_id":790,"fcategory_id":1,"scategory_id":10,"title":"小儿复方氨酚烷胺片","pic":"upload/pic/2016/07/18/e4aab0f47a180c9a061bfc8aa5bd30ba3e6865e3.jpg","manufacture_id":0,"manufacture_name":"葵花药业集团(唐山)生物制药有限公司","specs":"复方12片","packaing":"10盒","unit":"","auth_doc_num":"国药准字H13023866","retail_price":"0.00","batch_number":"","price_o":"1.98","price":"1.98","count":5,"total":"9.90","status":10,"created_at":1504238509,"updated_at":1504238509},{"id":167,"order_id":"1709011201496862","product_id":789,"fcategory_id":1,"scategory_id":10,"title":"复方氨酚烷胺胶囊","pic":"upload/pic/2016/07/11/22ab3de02eba4a4a52e5065513bedbb3764272c5.jpg","manufacture_id":0,"manufacture_name":"浙江亚峰药厂有限公司","specs":"10s","packaing":"10盒","unit":"","auth_doc_num":"国药准字H33020485","retail_price":"0.00","batch_number":"","price_o":"7.37","price":"7.37","count":2,"total":"14.74","status":10,"created_at":1504238509,"updated_at":1504238509},{"id":168,"order_id":"1709011201496862","product_id":23065,"fcategory_id":1,"scategory_id":27,"title":"醋酸泼尼松龙注射液","pic":"upload/pic/2016/08/19/e0b9beb00f668f3e7ba80b4c80b7a997af6f6355.jpg","manufacture_id":0,"manufacture_name":"华中药业股份有限公司","specs":"5ml:0.125g","packaing":"25支","unit":"","auth_doc_num":"国药准字H42021216","retail_price":"0.00","batch_number":"","price_o":"2.11","price":"2.11","count":8,"total":"16.88","status":10,"created_at":1504238509,"updated_at":1504238509},{"id":169,"order_id":"1709011201496862","product_id":619,"fcategory_id":1,"scategory_id":10,"title":"洛芬葡锌那敏片","pic":"upload/pic/2016/05/24/f09930acf6e96b001628ef219312e357948710a6.jpg","manufacture_id":0,"manufacture_name":"山西星火维敏制药有限公司","specs":"复方 12s","packaing":"200盒","unit":"","auth_doc_num":"国药准字H10970339","retail_price":"0.00","batch_number":"","price_o":"9.21","price":"9.21","count":3,"total":"27.63","status":10,"created_at":1504238509,"updated_at":1504238509},{"id":170,"order_id":"1709011201496862","product_id":256,"fcategory_id":1,"scategory_id":10,"title":"小儿氨酚黄那敏颗粒","pic":"upload/pic/2016/07/19/4f58bfd51e556ccb24e514f010b6fb8f0ccb363d.jpg","manufacture_id":0,"manufacture_name":"葵花药业集团(衡水)得菲尔有限公司","specs":"2g*15袋","packaing":"1盒","unit":"","auth_doc_num":"国药准字H13024176","retail_price":"29.70","batch_number":"","price_o":"14.85","price":"14.85","count":2,"total":"29.70","status":10,"created_at":1504238509,"updated_at":1504238509},{"id":171,"order_id":"1709011201496862","product_id":47937,"fcategory_id":9,"scategory_id":68,"title":"复方福尔可定口服溶液(澳特斯)","pic":"upload/pic/2017/02/11/0c6f1bb71a617ac293179f110f52d6ea2af73ab4.jpg","manufacture_id":0,"manufacture_name":"澳美制药厂","specs":"10ml*150袋","packaing":"99999盒","unit":"","auth_doc_num":"医药产品注册证HC20150060","retail_price":"0.00","batch_number":"","price_o":"9.73","price":"9.73","count":2,"total":"19.46","status":10,"created_at":1504238509,"updated_at":1504238509},{"id":172,"order_id":"1709011201496862","product_id":47317,"fcategory_id":6,"scategory_id":58,"title":"免水洗抗菌凝露","pic":"upload/pic/2016/08/29/5cf8abc6fa88872d93131d0d3f1c50135c0161fb.jpg","manufacture_id":0,"manufacture_name":"淳安县千岛湖佳蔚日用品有限公司","specs":"30ml","packaing":"20瓶","unit":"","auth_doc_num":"(2006)卫妆准字08-XK-0035","retail_price":"11.90","batch_number":"","price_o":"5.95","price":"5.95","count":1,"total":"5.95","status":10,"created_at":1504238509,"updated_at":1504238509},{"id":173,"order_id":"1709011201496862","product_id":47329,"fcategory_id":6,"scategory_id":58,"title":"冰王痘克清痘修痕液","pic":"upload/pic/2016/03/01/47a39668e99e68edc9b3642075cdf9dc0564619f.jpg","manufacture_id":0,"manufacture_name":"平舆冰王生物工程有限公司","specs":"30ml","packaing":"1盒","unit":"","auth_doc_num":"卫妆准字（1993）第25-XK-0037号","retail_price":"68.00","batch_number":"","price_o":"34.00","price":"34.00","count":1,"total":"34.00","status":10,"created_at":1504238509,"updated_at":1504238509}]
   */

  private String id;
  private int client_id;
  private String client_name;
  private int user_id;
  private String user_name;
  private int shop_id;
  private String shop_name;
  private int order_user_id;
  private String order_user_name;
  private String total_o;
  private String total_cut;
  private String coupon_cut;
  private String balance_cut;
  private String total;
  private int pay_method;
  private int pay_status;
  private long pay_dateline;
  private int ship_method;
  private String ship_name;
  private String ship_tel;
  private String ship_province;
  private String ship_city;
  private String ship_county;
  private String ship_address;
  private long ip;
  private int client;
  private int product_count;
  private int dai;
  private int status;
  private long cancel_at;
  private long created_at;
  private long updated_at;
  private List<OrderProductListBean> order_product_list;
  private List<OrderProductListBean> product;
  private String gift;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getUser_id() {
    return user_id;
  }

  public int getClient_id() {
    return client_id;
  }

  public void setClient_id(int client_id) {
    this.client_id = client_id;
  }

  public String getClient_name() {
    return client_name;
  }

  public void setClient_name(String client_name) {
    this.client_name = client_name;
  }

  public String getCoupon_cut() {
    return coupon_cut;
  }

  public void setCoupon_cut(String coupon_cut) {
    this.coupon_cut = coupon_cut;
  }

  public String getBalance_cut() {
    return balance_cut;
  }

  public void setBalance_cut(String balance_cut) {
    this.balance_cut = balance_cut;
  }

  public int getPay_status() {
    return pay_status;
  }

  public void setPay_status(int pay_status) {
    this.pay_status = pay_status;
  }

  public long getPay_dateline() {
    return pay_dateline;
  }

  public void setPay_dateline(long pay_dateline) {
    this.pay_dateline = pay_dateline;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public int getShop_id() {
    return shop_id;
  }

  public void setShop_id(int shop_id) {
    this.shop_id = shop_id;
  }

  public String getShop_name() {
    return shop_name;
  }

  public void setShop_name(String shop_name) {
    this.shop_name = shop_name;
  }

  public int getOrder_user_id() {
    return order_user_id;
  }

  public void setOrder_user_id(int order_user_id) {
    this.order_user_id = order_user_id;
  }

  public String getOrder_user_name() {
    return order_user_name;
  }

  public void setOrder_user_name(String order_user_name) {
    this.order_user_name = order_user_name;
  }

  public String getTotal_o() {
    return total_o;
  }

  public void setTotal_o(String total_o) {
    this.total_o = total_o;
  }

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getTotal_cut() {
    return total_cut;
  }

  public void setTotal_cut(String total_cut) {
    this.total_cut = total_cut;
  }

  public int getPay_method() {
    return pay_method;
  }

  public void setPay_method(int pay_method) {
    this.pay_method = pay_method;
  }

  public int getShip_method() {
    return ship_method;
  }

  public void setShip_method(int ship_method) {
    this.ship_method = ship_method;
  }

  public String getShip_name() {
    return ship_name;
  }

  public void setShip_name(String ship_name) {
    this.ship_name = ship_name;
  }

  public String getShip_tel() {
    return ship_tel;
  }

  public void setShip_tel(String ship_tel) {
    this.ship_tel = ship_tel;
  }

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

  public String getShip_address() {
    return ship_address;
  }

  public void setShip_address(String ship_address) {
    this.ship_address = ship_address;
  }

  public long getIp() {
    return ip;
  }

  public void setIp(long ip) {
    this.ip = ip;
  }

  public int getClient() {
    return client;
  }

  public void setClient(int client) {
    this.client = client;
  }

  public int getProduct_count() {
    return product_count;
  }

  public void setProduct_count(int product_count) {
    this.product_count = product_count;
  }

  public int getDai() {
    return dai;
  }

  public void setDai(int dai) {
    this.dai = dai;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getCancel_at() {
    return cancel_at;
  }

  public void setCancel_at(long cancel_at) {
    this.cancel_at = cancel_at;
  }

  public long getCreated_at() {
    return created_at;
  }

  public void setCreated_at(long created_at) {
    this.created_at = created_at;
  }

  public long getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(long updated_at) {
    this.updated_at = updated_at;
  }

  public List<OrderProductListBean> getOrder_product_list() {
    return order_product_list;
  }

  public void setOrder_product_list(List<OrderProductListBean> order_product_list) {
    this.order_product_list = order_product_list;
  }

  public List<OrderProductListBean> getProduct() {
    return product;
  }

  public void setProduct(
      List<OrderProductListBean> product) {
    this.product = product;
  }

  public String getGift() {
    return gift;
  }

  public void setGift(String gift) {
    this.gift = gift;
  }

  public static class OrderProductListBean implements Serializable {

    /**
     * id : 164
     * order_id : 1709011201496862
     * product_id : 47959
     * fcategory_id : 9
     * scategory_id : 68
     * title : 氨酚伪麻美芬片Ⅱ/氨麻苯美片(白加黑)
     * pic : upload/pic/2017/06/07/f32adff423d640de409ea472af82058672b7061b.jpg
     * manufacture_id : 0
     * manufacture_name : 拜耳医药保健有限公司启东分公司
     * specs : 325mg*30mg*15mg*10s/325mg*30mg*15mg*5s
     * packaing : 400盒
     * unit :
     * auth_doc_num : 国药准字H10940250/H10940251
     * retail_price : 0.00
     * batch_number :
     * price_o : 5.37
     * price : 5.37
     * count : 2
     * total : 10.74
     * status : 10
     * created_at : 1504238509
     * updated_at : 1504238509
     */

    private int id;
    private String order_id;
    private int product_id;
    private int product_sku_id;
    private int fcategory_id;
    private int scategory_id;
    private String title;
    private String pic;
    private int manufacture_id;
    private String manufacture_name;
    private String specs;
    private String packaing;
    private String unit;
    private String auth_doc_num;
    private String retail_price;
    private String batch_number;
    private String validity;
    private String price_o;
    private String price;
    private int count;
    private String total;
    private int is_gift;
    private int status;
    private int created_at;
    private int updated_at;
    private long repayment_date;
    private long return_date;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getOrder_id() {
      return order_id;
    }

    public void setOrder_id(String order_id) {
      this.order_id = order_id;
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

    public int getFcategory_id() {
      return fcategory_id;
    }

    public void setFcategory_id(int fcategory_id) {
      this.fcategory_id = fcategory_id;
    }

    public int getScategory_id() {
      return scategory_id;
    }

    public void setScategory_id(int scategory_id) {
      this.scategory_id = scategory_id;
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

    public int getManufacture_id() {
      return manufacture_id;
    }

    public void setManufacture_id(int manufacture_id) {
      this.manufacture_id = manufacture_id;
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

    public String getPackaing() {
      return packaing;
    }

    public void setPackaing(String packaing) {
      this.packaing = packaing;
    }

    public String getUnit() {
      return unit;
    }

    public void setUnit(String unit) {
      this.unit = unit;
    }

    public String getAuth_doc_num() {
      return auth_doc_num;
    }

    public void setAuth_doc_num(String auth_doc_num) {
      this.auth_doc_num = auth_doc_num;
    }

    public String getRetail_price() {
      return retail_price;
    }

    public void setRetail_price(String retail_price) {
      this.retail_price = retail_price;
    }

    public String getBatch_number() {
      return batch_number;
    }

    public void setBatch_number(String batch_number) {
      this.batch_number = batch_number;
    }

    public String getValidity() {
      return validity;
    }

    public void setValidity(String validity) {
      this.validity = validity;
    }

    public String getPrice_o() {
      return price_o;
    }

    public void setPrice_o(String price_o) {
      this.price_o = price_o;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }

    public String getTotal() {
      return total;
    }

    public void setTotal(String total) {
      this.total = total;
    }

    public int getIs_gift() {
      return is_gift;
    }

    public void setIs_gift(int is_gift) {
      this.is_gift = is_gift;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public int getCreated_at() {
      return created_at;
    }

    public void setCreated_at(int created_at) {
      this.created_at = created_at;
    }

    public int getUpdated_at() {
      return updated_at;
    }

    public void setUpdated_at(int updated_at) {
      this.updated_at = updated_at;
    }

    public long getRepayment_date() {
      return repayment_date;
    }

    public void setRepayment_date(long repayment_date) {
      this.repayment_date = repayment_date;
    }

    public long getReturn_date() {
      return return_date;
    }

    public void setReturn_date(long return_date) {
      this.return_date = return_date;
    }
  }
}
