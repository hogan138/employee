package com.wanchang.employee.data.entity;

import android.os.Parcel;
import java.util.List;

/**
 * Created by stormlei on 2017/11/15.
 */

public class Group implements android.os.Parcelable {


  /**
   * id : 9
   * name : 测试产品部聊天群
   * pic : upload/pic/2017/11/15/U74qlxE8YT.png
   * user_id : 1
   * owner : user_1
   * description : 测试产品部聊天群
   * group_id : 32312277401601
   * affiliations_count : 2
   * department_id : 1002
   * created_at : 1510213789
   * updated_at : 1510213789
   * user : [{"id":1,"username":"admin","password":"f7386766826c4b9f7decd6a8033ff193d6a6da7c","salt":"t4sAaWrrhteJ0jCe","mobile":"15558028980","name":"管理员","py":"gly","im_account":"user_1","im_password":"a7a962e7244b42db01c394fd5e54bad2654c67f4","group_id":100,"admin_role_id":0,"status":100,"created_at":1508402097,"updated_at":1508402097},{"id":18,"username":"salesman1","password":"2b80eab7b2057d18702c574df81d13ab4d448384","salt":"7QsaYZbP8MH2u#~@","mobile":"15000000001","name":"业务员11","py":"ywy1","im_account":"user_18","im_password":"2e09849387994206f8e2f99b7e9376751d007f51","group_id":20,"admin_role_id":0,"status":100,"created_at":1510630974,"updated_at":1510657089}]
   */

  private int id;
  private String name;
  private String pic;
  private int user_id;
  private String owner;
  private String description;
  private String group_id;
  private int affiliations_count;
  private int department_id;
  private int created_at;
  private int updated_at;
  private List<User> user;

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

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGroup_id() {
    return group_id;
  }

  public void setGroup_id(String group_id) {
    this.group_id = group_id;
  }

  public int getAffiliations_count() {
    return affiliations_count;
  }

  public void setAffiliations_count(int affiliations_count) {
    this.affiliations_count = affiliations_count;
  }

  public int getDepartment_id() {
    return department_id;
  }

  public void setDepartment_id(int department_id) {
    this.department_id = department_id;
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

  public List<User> getUser() {
    return user;
  }

  public void setUser(List<User> user) {
    this.user = user;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.name);
    dest.writeString(this.pic);
    dest.writeInt(this.user_id);
    dest.writeString(this.owner);
    dest.writeString(this.description);
    dest.writeString(this.group_id);
    dest.writeInt(this.affiliations_count);
    dest.writeInt(this.department_id);
    dest.writeInt(this.created_at);
    dest.writeInt(this.updated_at);
    dest.writeTypedList(this.user);
  }

  public Group() {
  }

  protected Group(Parcel in) {
    this.id = in.readInt();
    this.name = in.readString();
    this.pic = in.readString();
    this.user_id = in.readInt();
    this.owner = in.readString();
    this.description = in.readString();
    this.group_id = in.readString();
    this.affiliations_count = in.readInt();
    this.department_id = in.readInt();
    this.created_at = in.readInt();
    this.updated_at = in.readInt();
    this.user = in.createTypedArrayList(User.CREATOR);
  }

  public static final Creator<Group> CREATOR = new Creator<Group>() {
    @Override
    public Group createFromParcel(Parcel source) {
      return new Group(source);
    }

    @Override
    public Group[] newArray(int size) {
      return new Group[size];
    }
  };
}
