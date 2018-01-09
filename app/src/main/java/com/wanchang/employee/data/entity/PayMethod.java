package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/12/20.
 */

public class PayMethod {


  /**
   * id : 7
   * name : 供应链金融
   * pay_type : 0
   * status : 100
   * message :
   * data_list : [{"pay_type":101,"name":"蚂蚁金服","status":100,"message":""}]
   */

  private int id;
  private String name;
  private int pay_type;
  private int status;
  private String message;
  private List<DataListBean> data_list;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPay_type() {
    return pay_type;
  }

  public void setPay_type(int pay_type) {
    this.pay_type = pay_type;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<DataListBean> getData_list() {
    return data_list;
  }

  public void setData_list(List<DataListBean> data_list) {
    this.data_list = data_list;
  }

  public static class DataListBean {

    /**
     * pay_type : 101
     * name : 蚂蚁金服
     * status : 100
     * message :
     */

    private int pay_type;
    private String name;
    private int status;
    private String message;

    public int getPay_type() {
      return pay_type;
    }

    public void setPay_type(int pay_type) {
      this.pay_type = pay_type;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}
