package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/12/22.
 */

public class Bonus {


  /**
   * id : 52
   * user_id : 29
   * order_id : 201712111558078623
   * category : 10
   * data_id : 485
   * bonus : 279.90
   * explain :
   * status : 50
   * created_at : 1513044233
   * updated_at : 1513044233
   * data : {"title":"小儿氨酚黄那敏颗粒","order_id":"201712111558078623","count":12}
   */

  private int id;
  private int user_id;
  private String order_id;
  private int category;
  private String data_id;
  private String bonus;
  private String explain;
  private int status;
  private long created_at;
  private int updated_at;
  private DataBean data;

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

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public String getData_id() {
    return data_id;
  }

  public void setData_id(String data_id) {
    this.data_id = data_id;
  }

  public String getBonus() {
    return bonus;
  }

  public void setBonus(String bonus) {
    this.bonus = bonus;
  }

  public String getExplain() {
    return explain;
  }

  public void setExplain(String explain) {
    this.explain = explain;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
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

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public static class DataBean {

    /**
     * title : 小儿氨酚黄那敏颗粒
     * order_id : 201712111558078623
     * count : 12
     */

    private String title;
    private String order_id;
    private int count;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getOrder_id() {
      return order_id;
    }

    public void setOrder_id(String order_id) {
      this.order_id = order_id;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }
  }
}
