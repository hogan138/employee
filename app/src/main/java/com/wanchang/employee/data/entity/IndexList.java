package com.wanchang.employee.data.entity;

import java.util.List;

/**
 * Created by stormlei on 2017/8/9.
 */

public class IndexList {

  /**
   * id : 10
   * title : 滚动大图
   * category : 10
   * data :
   * order_id : 100
   * status : 100
   * cached_at : 0
   * created_at : 0
   * updated_at : 1500282304
   * item_list : [{"id":1,"index_list_id":10,"title":"氨酚伪麻美芬片Ⅱ/氨麻苯美片(白加黑)","pic":"upload/pic/2017/06/07/f32adff423d640de409ea472af82058672b7061b.jpg","specs":"325mg*30mg*15mg*10s/325mg*30mg*15mg*5s","promotion":"每满10赠1, 满1000打8折, ceshi, ceshi, 测试赠品","order_id":0,"link_type":10,"link_data":"47959","status":100,"created_at":1499848701,"updated_at":1500358572},{"id":4,"index_list_id":10,"title":"小儿化痰止咳颗粒","pic":"upload/pic/2016/07/11/b190aafcb72c4c4b11f5fbba268390fea64c694f.jpg","specs":"3g*10袋","promotion":"每满10赠1, 满1000打8折, ceshi, ceshi","order_id":0,"link_type":10,"link_data":"47958","status":100,"created_at":1500255585,"updated_at":1500358580}]
   */

  private int id;
  private String title;
  private int category;
  private String data;
  private int order_id;
  private int status;
  private int cached_at;
  private long created_at;
  private long updated_at;
  private List<ItemListBean> item_list;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public int getOrder_id() {
    return order_id;
  }

  public void setOrder_id(int order_id) {
    this.order_id = order_id;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getCached_at() {
    return cached_at;
  }

  public void setCached_at(int cached_at) {
    this.cached_at = cached_at;
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

  public List<ItemListBean> getItem_list() {
    return item_list;
  }

  public void setItem_list(List<ItemListBean> item_list) {
    this.item_list = item_list;
  }

  public static class ItemListBean {

    /**
     * id : 1
     * index_list_id : 10
     * title : 氨酚伪麻美芬片Ⅱ/氨麻苯美片(白加黑)
     * pic : upload/pic/2017/06/07/f32adff423d640de409ea472af82058672b7061b.jpg
     * specs : 325mg*30mg*15mg*10s/325mg*30mg*15mg*5s
     * promotion : 每满10赠1, 满1000打8折, ceshi, ceshi, 测试赠品
     * order_id : 0
     * link_type : 10
     * link_data : 47959
     * status : 100
     * created_at : 1499848701
     * updated_at : 1500358572
     */

    private int id;
    private int index_list_id;
    private String title;
    private String pic;
    private String specs;
    private String price;
    private String promotion;
    private int order_id;
    private int link_type;
    private String link_data;
    private int status;
    private long created_at;
    private long updated_at;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getIndex_list_id() {
      return index_list_id;
    }

    public void setIndex_list_id(int index_list_id) {
      this.index_list_id = index_list_id;
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

    public String getSpecs() {
      return specs;
    }

    public void setSpecs(String specs) {
      this.specs = specs;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }

    public String getPromotion() {
      return promotion;
    }

    public void setPromotion(String promotion) {
      this.promotion = promotion;
    }

    public int getOrder_id() {
      return order_id;
    }

    public void setOrder_id(int order_id) {
      this.order_id = order_id;
    }

    public int getLink_type() {
      return link_type;
    }

    public void setLink_type(int link_type) {
      this.link_type = link_type;
    }

    public String getLink_data() {
      return link_data;
    }

    public void setLink_data(String link_data) {
      this.link_data = link_data;
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
  }
}
