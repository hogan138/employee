package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/5/17.
 */

public class MenuItem {
  private String title;
  private List<MenuItemData> data;
  private String access_token;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<MenuItemData> getData() {
    return data;
  }

  public void setData(List<MenuItemData> data) {
    this.data = data;
  }

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }
}
