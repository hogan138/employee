package com.wanchang.employee.data.entity;

import android.os.Parcel;
import java.io.Serializable;

/**
 * Created by stormlei on 2017/11/20.
 */

public class User implements android.os.Parcelable, Serializable {


  /**
   * id : 29
   * username : salesman2
   * mobile :
   * name : 业务员2
   * ID_card :
   * pic : upload/pic/2017/11/17/AlHtR3edwK.png
   * py : ywy2
   * im_account : user_29
   * im_password : 1505a5346a41fd7817130890d1f001e0255f7c12
   * group_id : 20
   * admin_role_id : 0
   * status : 100
   * created_at : 1510886663
   * updated_at : 1510886663
   */

  private int id;
  private String username;
  private String mobile;
  private String name;
  private String id_card;
  private String pic;
  private String py;
  private String im_account;
  private String im_password;
  private int group_id;
  private int admin_role_id;
  private int status;
  private long created_at;
  private long updated_at;

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

  public String getId_card() {
    return id_card;
  }

  public void setId_card(String id_card) {
    this.id_card = id_card;
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.username);
    dest.writeString(this.mobile);
    dest.writeString(this.name);
    dest.writeString(this.id_card);
    dest.writeString(this.pic);
    dest.writeString(this.py);
    dest.writeString(this.im_account);
    dest.writeString(this.im_password);
    dest.writeInt(this.group_id);
    dest.writeInt(this.admin_role_id);
    dest.writeInt(this.status);
    dest.writeLong(this.created_at);
    dest.writeLong(this.updated_at);
  }

  public User() {
  }

  protected User(Parcel in) {
    this.id = in.readInt();
    this.username = in.readString();
    this.mobile = in.readString();
    this.name = in.readString();
    this.id_card = in.readString();
    this.pic = in.readString();
    this.py = in.readString();
    this.im_account = in.readString();
    this.im_password = in.readString();
    this.group_id = in.readInt();
    this.admin_role_id = in.readInt();
    this.status = in.readInt();
    this.created_at = in.readLong();
    this.updated_at = in.readLong();
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override
    public User createFromParcel(Parcel source) {
      return new User(source);
    }

    @Override
    public User[] newArray(int size) {
      return new User[size];
    }
  };
}
