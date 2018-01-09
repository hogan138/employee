package com.wanchang.employee.eventbus;

/**
 * Created by stormlei on 2017/11/2.
 */

public class ManufactureMessageEvent {

  private String manufacture;

  public ManufactureMessageEvent(String manufacture) {
    this.manufacture = manufacture;
  }

  public String getManufacture() {
    return manufacture;
  }
}
