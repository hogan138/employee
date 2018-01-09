package com.wanchang.employee.data.entity;

import java.io.Serializable;

/**
 * Created by stormlei on 2017/5/8.
 */

public class LoginInfo implements Serializable {


  /**
   * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjIsImV4cCI6MTgyNTkyNTg2Nn0.PILsaBEWmKY_e-1eh7JbmqDrNVVNpH09pFNd37kKY-4
   * user : {"id":2,"username":"erp1","mobile":"15558028981","name":"客户1","py":"kh1","im_account":"user_2","im_password":"44ab86601a4643535006f677b7a0b4a5b024940c","group_id":10,"admin_role_id":0,"status":100,"created_at":1509171113,"updated_at":1509171113}
   */

  private String access_token;
  private User user;
  private int shopping_cart_count;

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getShopping_cart_count() {
    return shopping_cart_count;
  }

  public void setShopping_cart_count(int shopping_cart_count) {
    this.shopping_cart_count = shopping_cart_count;
  }
}
