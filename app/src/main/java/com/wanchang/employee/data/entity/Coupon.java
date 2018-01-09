package com.wanchang.employee.data.entity;

import android.os.Parcel;

/**
 * Created by stormlei on 2017/11/23.
 */

public class Coupon implements android.os.Parcelable {


  /**
   * id : 1
   * category : 100
   * name : 测试优惠券
   * value : 100
   * condition : 10000
   * key : AD7FBA6AA9DA366619CB01E7D94057AD56997FF3
   */

  private int id;
  private int category;
  private String name;
  private int value;
  private int condition;
  private long start_at;
  private long end_at;
  private String key;
  private String usage;
  private int tooked;
  private int count_per_client;
  private int received;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getCondition() {
    return condition;
  }

  public void setCondition(int condition) {
    this.condition = condition;
  }

  public long getStart_at() {
    return start_at;
  }

  public void setStart_at(long start_at) {
    this.start_at = start_at;
  }

  public long getEnd_at() {
    return end_at;
  }

  public void setEnd_at(long end_at) {
    this.end_at = end_at;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getUsage() {
    return usage;
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public int getTooked() {
    return tooked;
  }

  public void setTooked(int tooked) {
    this.tooked = tooked;
  }

  public int getCount_per_client() {
    return count_per_client;
  }

  public void setCount_per_client(int count_per_client) {
    this.count_per_client = count_per_client;
  }

  public int getReceived() {
    return received;
  }

  public void setReceived(int received) {
    this.received = received;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeInt(this.category);
    dest.writeString(this.name);
    dest.writeInt(this.value);
    dest.writeInt(this.condition);
    dest.writeLong(this.start_at);
    dest.writeLong(this.end_at);
    dest.writeString(this.key);
    dest.writeString(this.usage);
    dest.writeInt(this.tooked);
    dest.writeInt(this.count_per_client);
    dest.writeInt(this.received);
  }

  public Coupon() {
  }

  protected Coupon(Parcel in) {
    this.id = in.readInt();
    this.category = in.readInt();
    this.name = in.readString();
    this.value = in.readInt();
    this.condition = in.readInt();
    this.start_at = in.readLong();
    this.end_at = in.readLong();
    this.key = in.readString();
    this.usage = in.readString();
    this.tooked = in.readInt();
    this.count_per_client = in.readInt();
    this.received = in.readInt();
  }

  public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {
    @Override
    public Coupon createFromParcel(Parcel source) {
      return new Coupon(source);
    }

    @Override
    public Coupon[] newArray(int size) {
      return new Coupon[size];
    }
  };
}
