package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/12/21.
 */

public class AreaRecruit {


  /**
   * id : 17
   * department_id : 1002
   * province : 浙江省
   * province_id : 330000
   * city : 杭州市
   * city_id : 330100
   * county :
   * county_id : 0
   * client_category : 10,30
   * comment :
   * status : 0
   * created_at : 1511417265
   * updated_at : 1511417265
   * department : {"id":1002,"name":"万昌产品部","type":0,"erp_bh":"123","created_at":1510213789,"updated_at":1513580570}
   * is_checked : 0
   */

  private int id;
  private int department_id;
  private String province;
  private int province_id;
  private String city;
  private int city_id;
  private String county;
  private int county_id;
  private String client_category;
  private String comment;
  private int status;
  private int created_at;
  private int updated_at;
  private DepartmentBean department;
  private int is_checked;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDepartment_id() {
    return department_id;
  }

  public void setDepartment_id(int department_id) {
    this.department_id = department_id;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public int getProvince_id() {
    return province_id;
  }

  public void setProvince_id(int province_id) {
    this.province_id = province_id;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getCity_id() {
    return city_id;
  }

  public void setCity_id(int city_id) {
    this.city_id = city_id;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public int getCounty_id() {
    return county_id;
  }

  public void setCounty_id(int county_id) {
    this.county_id = county_id;
  }

  public String getClient_category() {
    return client_category;
  }

  public void setClient_category(String client_category) {
    this.client_category = client_category;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
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

  public DepartmentBean getDepartment() {
    return department;
  }

  public void setDepartment(DepartmentBean department) {
    this.department = department;
  }

  public int getIs_checked() {
    return is_checked;
  }

  public void setIs_checked(int is_checked) {
    this.is_checked = is_checked;
  }

  public static class DepartmentBean {

    /**
     * id : 1002
     * name : 万昌产品部
     * type : 0
     * erp_bh : 123
     * created_at : 1510213789
     * updated_at : 1513580570
     */

    private int id;
    private String name;
    private int type;
    private String erp_bh;
    private int created_at;
    private int updated_at;

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

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }

    public String getErp_bh() {
      return erp_bh;
    }

    public void setErp_bh(String erp_bh) {
      this.erp_bh = erp_bh;
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
}
