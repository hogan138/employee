package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/5/17.
 */

public class Category1Item {
  private int id;
  private String title;
  private int parent;
  private int order_id;
  private List<RecommendProduct> recommend_products;
  private List<Category2Item> list;

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

  public int getParent() {
    return parent;
  }

  public void setParent(int parent) {
    this.parent = parent;
  }

  public int getOrder_id() {
    return order_id;
  }

  public void setOrder_id(int order_id) {
    this.order_id = order_id;
  }

  public List<Category2Item> getList() {
    return list;
  }

  public void setList(List<Category2Item> list) {
    this.list = list;
  }

  public List<RecommendProduct> getRecommend_products() {
    return recommend_products;
  }

  public void setRecommend_products(
      List<RecommendProduct> recommend_products) {
    this.recommend_products = recommend_products;
  }
}
