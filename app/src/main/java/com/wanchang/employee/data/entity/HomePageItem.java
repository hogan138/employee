package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/12/11.
 */

public class HomePageItem {


  /**
   * id : 3
   * index_list_id : 40
   * title : 推荐商品4
   * pic : upload/pic/2017/12/08/zNuD7UDdqv.png
   * link_type : product
   * link_data : 1
   * order_id : 0
   * product_sku_id : 1
   * status : 100
   * created_at : 1512720458
   * updated_at : 1512720458
   * product_sku : {"id":1,"title":"小儿氨酚黄那敏颗粒","pic":"upload/pic/image/default.jpg","manufacture_name":"亚宝药业四川制药有限公司","specs":"6g*15袋","packaing":30,"unit":"袋","validity":"0000-00-00"}
   * price : 0
   * promotion_list : [{"id":4,"title":"2017整单立减","category":50,"key":"69035debc44fa45ac43bf5c7a8a055c56ed4c6fa","product_sku_id":1}]
   * cmd : product:1
   */

  private int id;
  private int index_list_id;
  private String title;
  private String pic;
  private String link_type;
  private String link_data;
  private int order_id;
  private int product_sku_id;
  private int status;
  private ProductSkuBean product_sku;
  private float price;
  private String cmd;
  private List<Promotion> promotion_list;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIndex_list_id() {
    return index_list_id;
  }

  public void setIndex_list_id(int index_list_id) {
    this.index_list_id = index_list_id;
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

  public String getLink_type() {
    return link_type;
  }

  public void setLink_type(String link_type) {
    this.link_type = link_type;
  }

  public String getLink_data() {
    return link_data;
  }

  public void setLink_data(String link_data) {
    this.link_data = link_data;
  }

  public int getOrder_id() {
    return order_id;
  }

  public void setOrder_id(int order_id) {
    this.order_id = order_id;
  }

  public int getProduct_sku_id() {
    return product_sku_id;
  }

  public void setProduct_sku_id(int product_sku_id) {
    this.product_sku_id = product_sku_id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public ProductSkuBean getProduct_sku() {
    return product_sku;
  }

  public void setProduct_sku(ProductSkuBean product_sku) {
    this.product_sku = product_sku;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public String getCmd() {
    return cmd;
  }

  public void setCmd(String cmd) {
    this.cmd = cmd;
  }

  public List<Promotion> getPromotion_list() {
    return promotion_list;
  }

  public void setPromotion_list(List<Promotion> promotion_list) {
    this.promotion_list = promotion_list;
  }

  public static class ProductSkuBean {

    /**
     * id : 1
     * title : 小儿氨酚黄那敏颗粒
     * pic : upload/pic/image/default.jpg
     * manufacture_name : 亚宝药业四川制药有限公司
     * specs : 6g*15袋
     * packaing : 30
     * unit : 袋
     * validity : 0000-00-00
     */

    private int id;
    private String title;
    private String pic;
    private String manufacture_name;
    private String specs;
    private int packaing;
    private String unit;
    private String validity;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
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
  }
}
