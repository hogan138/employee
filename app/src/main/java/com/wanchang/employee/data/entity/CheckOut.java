package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/8/31.
 */

public class CheckOut {

  private ShoppingCart shopping_cart;
  private CouponListBean coupon_list;
  private float balance;
  private List<Express> express_type_list;
  private Address address;
  private SummaryBean summary;
  private List<PayMethod> pay_type_list;

  public ShoppingCart getShopping_cart() {
    return shopping_cart;
  }

  public void setShopping_cart(ShoppingCart shopping_cart) {
    this.shopping_cart = shopping_cart;
  }

  public CouponListBean getCoupon_list() {
    return coupon_list;
  }

  public void setCoupon_list(CouponListBean coupon_list) {
    this.coupon_list = coupon_list;
  }

  public float getBalance() {
    return balance;
  }

  public void setBalance(float balance) {
    this.balance = balance;
  }

  public List<Express> getExpress_type_list() {
    return express_type_list;
  }

  public void setExpress_type_list(
      List<Express> express_type_list) {
    this.express_type_list = express_type_list;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public SummaryBean getSummary() {
    return summary;
  }

  public void setSummary(SummaryBean summary) {
    this.summary = summary;
  }

  public static class CouponListBean {
    private List<Coupon> can_use;
    private List<Coupon> un_use;

    public List<Coupon> getCan_use() {
      return can_use;
    }

    public void setCan_use(List<Coupon> can_use) {
      this.can_use = can_use;
    }

    public List<Coupon> getUn_use() {
      return un_use;
    }

    public void setUn_use(List<Coupon> un_use) {
      this.un_use = un_use;
    }
  }

  public static class SummaryBean {
    private int count;
    private float total_origin;
    private float total;
    private float total_cut;
    private float balance_cut;
    private float coupon_cut;

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }

    public float getTotal_origin() {
      return total_origin;
    }

    public void setTotal_origin(float total_origin) {
      this.total_origin = total_origin;
    }

    public float getTotal() {
      return total;
    }

    public void setTotal(float total) {
      this.total = total;
    }

    public float getTotal_cut() {
      return total_cut;
    }

    public void setTotal_cut(float total_cut) {
      this.total_cut = total_cut;
    }

    public float getBalance_cut() {
      return balance_cut;
    }

    public void setBalance_cut(float balance_cut) {
      this.balance_cut = balance_cut;
    }

    public float getCoupon_cut() {
      return coupon_cut;
    }

    public void setCoupon_cut(float coupon_cut) {
      this.coupon_cut = coupon_cut;
    }
  }

  public List<PayMethod> getPay_type_list() {
    return pay_type_list;
  }

  public void setPay_type_list(List<PayMethod> pay_type_list) {
    this.pay_type_list = pay_type_list;
  }
}
