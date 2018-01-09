package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/12/21.
 */

public class Sign {


  /**
   * chart_info : [{"id":18,"name":"杭州惠民连锁大药房","lon":563,"lat":5622,"count":1}]
   * chart_detail : [{"id":10,"client_id":18,"name":"杭州惠民连锁大药房","created_at":1513856131}]
   * total_count : 1
   */

  private int total_count;
  private List<ChartInfoBean> chart_info;
  private List<ChartDetailBean> chart_detail;

  public int getTotal_count() {
    return total_count;
  }

  public void setTotal_count(int total_count) {
    this.total_count = total_count;
  }

  public List<ChartInfoBean> getChart_info() {
    return chart_info;
  }

  public void setChart_info(List<ChartInfoBean> chart_info) {
    this.chart_info = chart_info;
  }

  public List<ChartDetailBean> getChart_detail() {
    return chart_detail;
  }

  public void setChart_detail(List<ChartDetailBean> chart_detail) {
    this.chart_detail = chart_detail;
  }

  public static class ChartInfoBean {

    /**
     * id : 18
     * name : 杭州惠民连锁大药房
     * lon : 563
     * lat : 5622
     * count : 1
     */

    private int id;
    private String name;
    private double lon;
    private double lat;
    private int count;

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

    public double getLon() {
      return lon;
    }

    public void setLon(double lon) {
      this.lon = lon;
    }

    public double getLat() {
      return lat;
    }

    public void setLat(double lat) {
      this.lat = lat;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }
  }

  public static class ChartDetailBean {

    /**
     * id : 10
     * client_id : 18
     * name : 杭州惠民连锁大药房
     * created_at : 1513856131
     */

    private int id;
    private int client_id;
    private String name;
    private long created_at;

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

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public long getCreated_at() {
      return created_at;
    }

    public void setCreated_at(long created_at) {
      this.created_at = created_at;
    }
  }
}
