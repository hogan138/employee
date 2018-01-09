package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2018/1/3.
 */

public class DepRole {


  /**
   * department_id : 1002
   * name : 产品
   * role_id : 1000
   * department : {"id":1002,"name":"万昌产品部","type":0,"erp_bh":"4321","last_bill_date":1512057600,"created_at":1510213789,"updated_at":1513839714}
   * role : {"id":1000,"name":"产品","department_type":0,"created_at":1510212071,"updated_at":1510212071}
   */

  private int department_id;
  private String name;
  private int role_id;
  private DepartmentBean department;
  private RoleBean role;

  public int getDepartment_id() {
    return department_id;
  }

  public void setDepartment_id(int department_id) {
    this.department_id = department_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRole_id() {
    return role_id;
  }

  public void setRole_id(int role_id) {
    this.role_id = role_id;
  }

  public DepartmentBean getDepartment() {
    return department;
  }

  public void setDepartment(DepartmentBean department) {
    this.department = department;
  }

  public RoleBean getRole() {
    return role;
  }

  public void setRole(RoleBean role) {
    this.role = role;
  }

  public static class DepartmentBean {

    /**
     * id : 1002
     * name : 万昌产品部
     * type : 0
     * erp_bh : 4321
     * last_bill_date : 1512057600
     * created_at : 1510213789
     * updated_at : 1513839714
     */

    private int id;
    private String name;
    private int type;
    private String erp_bh;
    private long last_bill_date;
    private long created_at;
    private long updated_at;

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

    public long getLast_bill_date() {
      return last_bill_date;
    }

    public void setLast_bill_date(long last_bill_date) {
      this.last_bill_date = last_bill_date;
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
  }

  public static class RoleBean {

    /**
     * id : 1000
     * name : 产品
     * department_type : 0
     * created_at : 1510212071
     * updated_at : 1510212071
     */

    private int id;
    private String name;
    private int department_type;
    private long created_at;
    private long updated_at;

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

    public int getDepartment_type() {
      return department_type;
    }

    public void setDepartment_type(int department_type) {
      this.department_type = department_type;
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
  }
}
