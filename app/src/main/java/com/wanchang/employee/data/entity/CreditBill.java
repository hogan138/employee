package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/12/14.
 */

public class CreditBill {


  /**
   * id : 5
   * client_id : 1
   * bill_start : 1509465600
   * bill_end : 1513008000
   * price : 11116.75
   * repayment_date : 1514304000
   * status : 1
   * created_at : 1513049044
   * updated_at : 1513049044
   */

  private int id;
  private int client_id;
  private long bill_start;
  private long bill_end;
  private String price;
  private long repayment_date;
  private int status;
  private int created_at;
  private int updated_at;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getClient_id() {
    return client_id;
  }

  public void setClient_id(int client_id) {
    this.client_id = client_id;
  }

  public long getBill_start() {
    return bill_start;
  }

  public void setBill_start(long bill_start) {
    this.bill_start = bill_start;
  }

  public long getBill_end() {
    return bill_end;
  }

  public void setBill_end(long bill_end) {
    this.bill_end = bill_end;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public long getRepayment_date() {
    return repayment_date;
  }

  public void setRepayment_date(long repayment_date) {
    this.repayment_date = repayment_date;
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
