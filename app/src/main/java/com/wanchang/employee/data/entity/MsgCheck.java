package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/12/15.
 */

public class MsgCheck {


  /**
   * id : 2159
   * user_id : 81
   * role_id : 1000
   * from_user_id : 0
   * title : 今天有1个商品跨阶段了，请及时处理
   * data_type : 90
   * data_id : 0
   * ext : product_sku_list
   * model :
   * reject_model :
   * status : 0
   * reject_reason :
   * check_user_id : 0
   * read : 1
   * status_push : 10
   * created_at : 1514451118
   * updated_at : 1514451118
   */

  private int id;
  private int user_id;
  private int role_id;
  private int from_user_id;
  private String title;
  private int data_type;
  private String data_id;
  private String ext;
  private String model;
  private String reject_model;
  private int status;
  private String reject_reason;
  private int check_user_id;
  private int read;
  private int status_push;
  private long created_at;
  private long updated_at;
  private String type;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public int getRole_id() {
    return role_id;
  }

  public void setRole_id(int role_id) {
    this.role_id = role_id;
  }

  public int getFrom_user_id() {
    return from_user_id;
  }

  public void setFrom_user_id(int from_user_id) {
    this.from_user_id = from_user_id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getData_type() {
    return data_type;
  }

  public void setData_type(int data_type) {
    this.data_type = data_type;
  }

  public String getData_id() {
    return data_id;
  }

  public void setData_id(String data_id) {
    this.data_id = data_id;
  }

  public String getExt() {
    return ext;
  }

  public void setExt(String ext) {
    this.ext = ext;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getReject_model() {
    return reject_model;
  }

  public void setReject_model(String reject_model) {
    this.reject_model = reject_model;
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

  public int getCheck_user_id() {
    return check_user_id;
  }

  public void setCheck_user_id(int check_user_id) {
    this.check_user_id = check_user_id;
  }

  public int getRead() {
    return read;
  }

  public void setRead(int read) {
    this.read = read;
  }

  public int getStatus_push() {
    return status_push;
  }

  public void setStatus_push(int status_push) {
    this.status_push = status_push;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
