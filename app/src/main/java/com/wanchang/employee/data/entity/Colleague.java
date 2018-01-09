package com.wanchang.employee.data.entity;

import android.os.Parcel;

/**
 * Created by stormlei on 2017/11/16.
 */

public class Colleague implements android.os.Parcelable {


  /**
   * department_id : 300
   * role_id : 300
   * id : 20
   * username : finance1
   * password : 45dc7bb3691ac8dc49793172d63ecafb8be13002
   * salt : MT4Vv51ZId2n2N@5
   * mobile : 15000000010
   * name : 财务1
   * pic :
   * py : cw1
   * im_account : user_20
   * im_password : 9a4cdbf3fd9c7a85678f9a96ee7efcbf31bffc53
   * group_id : 50
   * admin_role_id : 0
   * status : 100
   * created_at : 1510640830
   * updated_at : 1510640830
   * user : null
   */

  private int department_id;
  private int role_id;
  private int id;
  private String username;
  private String password;
  private String salt;
  private String mobile;
  private String name;
  private String pic;
  private String py;
  private String im_account;
  private String im_password;
  private int group_id;
  private int admin_role_id;
  private int status;
  private int created_at;
  private int updated_at;

  public int getDepartment_id() {
    return department_id;
  }

  public void setDepartment_id(int department_id) {
    this.department_id = department_id;
  }

  public int getRole_id() {
    return role_id;
  }

  public void setRole_id(int role_id) {
    this.role_id = role_id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public String getPy() {
    return py;
  }

  public void setPy(String py) {
    this.py = py;
  }

  public String getIm_account() {
    return im_account;
  }

  public void setIm_account(String im_account) {
    this.im_account = im_account;
  }

  public String getIm_password() {
    return im_password;
  }

  public void setIm_password(String im_password) {
    this.im_password = im_password;
  }

  public int getGroup_id() {
    return group_id;
  }

  public void setGroup_id(int group_id) {
    this.group_id = group_id;
  }

  public int getAdmin_role_id() {
    return admin_role_id;
  }

  public void setAdmin_role_id(int admin_role_id) {
    this.admin_role_id = admin_role_id;
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.department_id);
    dest.writeInt(this.role_id);
    dest.writeInt(this.id);
    dest.writeString(this.username);
    dest.writeString(this.password);
    dest.writeString(this.salt);
    dest.writeString(this.mobile);
    dest.writeString(this.name);
    dest.writeString(this.pic);
    dest.writeString(this.py);
    dest.writeString(this.im_account);
    dest.writeString(this.im_password);
    dest.writeInt(this.group_id);
    dest.writeInt(this.admin_role_id);
    dest.writeInt(this.status);
    dest.writeInt(this.created_at);
    dest.writeInt(this.updated_at);
  }

  public Colleague() {
  }

  protected Colleague(Parcel in) {
    this.department_id = in.readInt();
    this.role_id = in.readInt();
    this.id = in.readInt();
    this.username = in.readString();
    this.password = in.readString();
    this.salt = in.readString();
    this.mobile = in.readString();
    this.name = in.readString();
    this.pic = in.readString();
    this.py = in.readString();
    this.im_account = in.readString();
    this.im_password = in.readString();
    this.group_id = in.readInt();
    this.admin_role_id = in.readInt();
    this.status = in.readInt();
    this.created_at = in.readInt();
    this.updated_at = in.readInt();
  }

  public static final Creator<Colleague> CREATOR = new Creator<Colleague>() {
    @Override
    public Colleague createFromParcel(Parcel source) {
      return new Colleague(source);
    }

    @Override
    public Colleague[] newArray(int size) {
      return new Colleague[size];
    }
  };
}
