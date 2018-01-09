package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/12/15.
 */

public class Message {

  private int id;
  private int category;
  private String title;
  private String pic;
  private String content;
  private String extra;
  private String data_id;
  private String cmd;
  private long sent_at;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getExtra() {
    return extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }

  public String getData_id() {
    return data_id;
  }

  public void setData_id(String data_id) {
    this.data_id = data_id;
  }

  public String getCmd() {
    return cmd;
  }

  public void setCmd(String cmd) {
    this.cmd = cmd;
  }

  public long getSent_at() {
    return sent_at;
  }

  public void setSent_at(long sent_at) {
    this.sent_at = sent_at;
  }
}
