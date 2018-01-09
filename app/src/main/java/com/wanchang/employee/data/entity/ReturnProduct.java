package com.wanchang.employee.data.entity;

import java.io.Serializable;

/**
 * Created by stormlei on 2017/12/17.
 */

public class ReturnProduct implements Serializable {


  /**
   * id : 201712171559276400
   * order_id : 201712111535072496
   * order_product_id : 464
   * count : 3
   * product_price : 0.00
   * return_price : 161.70
   * reason : 3
   * explain : jjkk
   * bonus_bill : 1
   * status : 1
   * reject_reason :
   * created_at : 1513497567
   * updated_at : 1513497567
   * product : {"id":464,"order_id":"201712111535072496","product_id":788,"product_sku_id":2362,"fcategory_id":0,"scategory_id":0,"title":"复方北豆根氨酚那敏片","pic":"upload/pic/2017/06/04/96063cd65cdc147846cdab067fbce791ccc0389c.jpg","manufacture_id":1,"manufacture_name":"江西南大博仕制药有限公司","specs":"12s /10","packaing":10,"unit":"盒","auth_doc_num":"","batch_number":"","price_o":"63.90","price":"53.90","count":20,"total_o":"1278.00","total":"1078.00","total_cut":"0.00","coupon_cut":"0.00","manager_cut":"0.00","department_id":0,"salesman_id":18,"repayment_date":1512057600,"return_date":1513929443,"bonus_bill":100,"status":40,"created_at":1512977707,"updated_at":1513146038}
   */

  private String id;
  private String order_id;
  private int order_product_id;
  private int count;
  private String product_price;
  private String return_price;
  private int reason;
  private String explain;
  private int bonus_bill;
  private int status;
  private String reject_reason;
  private long created_at;
  private int updated_at;
  private ProductBean product;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public int getOrder_product_id() {
    return order_product_id;
  }

  public void setOrder_product_id(int order_product_id) {
    this.order_product_id = order_product_id;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getProduct_price() {
    return product_price;
  }

  public void setProduct_price(String product_price) {
    this.product_price = product_price;
  }

  public String getReturn_price() {
    return return_price;
  }

  public void setReturn_price(String return_price) {
    this.return_price = return_price;
  }

  public int getReason() {
    return reason;
  }

  public void setReason(int reason) {
    this.reason = reason;
  }

  public String getExplain() {
    return explain;
  }

  public void setExplain(String explain) {
    this.explain = explain;
  }

  public int getBonus_bill() {
    return bonus_bill;
  }

  public void setBonus_bill(int bonus_bill) {
    this.bonus_bill = bonus_bill;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getReject_reason() {
    return reject_reason;
  }

  public void setReject_reason(String reject_reason) {
    this.reject_reason = reject_reason;
  }

  public long getCreated_at() {
    return created_at;
  }

  public void setCreated_at(long created_at) {
    this.created_at = created_at;
  }

  public int getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(int updated_at) {
    this.updated_at = updated_at;
  }

  public ProductBean getProduct() {
    return product;
  }

  public void setProduct(ProductBean product) {
    this.product = product;
  }

  public static class ProductBean implements Serializable {

    /**
     * id : 464
     * order_id : 201712111535072496
     * product_id : 788
     * product_sku_id : 2362
     * fcategory_id : 0
     * scategory_id : 0
     * title : 复方北豆根氨酚那敏片
     * pic : upload/pic/2017/06/04/96063cd65cdc147846cdab067fbce791ccc0389c.jpg
     * manufacture_id : 1
     * manufacture_name : 江西南大博仕制药有限公司
     * specs : 12s /10
     * packaing : 10
     * unit : 盒
     * auth_doc_num :
     * batch_number :
     * price_o : 63.90
     * price : 53.90
     * count : 20
     * total_o : 1278.00
     * total : 1078.00
     * total_cut : 0.00
     * coupon_cut : 0.00
     * manager_cut : 0.00
     * department_id : 0
     * salesman_id : 18
     * repayment_date : 1512057600
     * return_date : 1513929443
     * bonus_bill : 100
     * status : 40
     * created_at : 1512977707
     * updated_at : 1513146038
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
    private int packaing;
    private String unit;
    private String auth_doc_num;
    private String batch_number;
    private String price_o;
    private String price;
    private int count;
    private String total_o;
    private String total;
    private String total_cut;
    private String coupon_cut;
    private String manager_cut;
    private int department_id;
    private int salesman_id;
    private int repayment_date;
    private int return_date;
    private int bonus_bill;
    private int status;
    private int created_at;
    private int updated_at;

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

    public String getAuth_doc_num() {
      return auth_doc_num;
    }

    public void setAuth_doc_num(String auth_doc_num) {
      this.auth_doc_num = auth_doc_num;
    }

    public String getBatch_number() {
      return batch_number;
    }

    public void setBatch_number(String batch_number) {
      this.batch_number = batch_number;
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

    public String getCoupon_cut() {
      return coupon_cut;
    }

    public void setCoupon_cut(String coupon_cut) {
      this.coupon_cut = coupon_cut;
    }

    public String getManager_cut() {
      return manager_cut;
    }

    public void setManager_cut(String manager_cut) {
      this.manager_cut = manager_cut;
    }

    public int getDepartment_id() {
      return department_id;
    }

    public void setDepartment_id(int department_id) {
      this.department_id = department_id;
    }

    public int getSalesman_id() {
      return salesman_id;
    }

    public void setSalesman_id(int salesman_id) {
      this.salesman_id = salesman_id;
    }

    public int getRepayment_date() {
      return repayment_date;
    }

    public void setRepayment_date(int repayment_date) {
      this.repayment_date = repayment_date;
    }

    public int getReturn_date() {
      return return_date;
    }

    public void setReturn_date(int return_date) {
      this.return_date = return_date;
    }

    public int getBonus_bill() {
      return bonus_bill;
    }

    public void setBonus_bill(int bonus_bill) {
      this.bonus_bill = bonus_bill;
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
  }
}
