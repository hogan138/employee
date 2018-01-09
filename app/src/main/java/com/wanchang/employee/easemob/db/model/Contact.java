package com.wanchang.employee.easemob.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by stormlei on 2017/5/5.
 */

public class Contact extends RealmObject {

  @PrimaryKey
  private String username;
  private String nickname;
  private String avatar;
  private boolean isFriend;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public boolean isFriend() {
    return isFriend;
  }

  public void setFriend(boolean friend) {
    isFriend = friend;
  }
}
