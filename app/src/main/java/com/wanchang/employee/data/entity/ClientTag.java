package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2018/1/14.
 */

public class ClientTag {


  /**
   * id : 11
   * tag : 诊所
   * order_id : 0
   * created_at : 1515487850
   * updated_at : 1515487850
   */

  private int id;
  private String tag;
  private int order_id;
  private int created_at;
  private int updated_at;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public int getOrder_id() {
    return order_id;
  }

  public void setOrder_id(int order_id) {
    this.order_id = order_id;
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
