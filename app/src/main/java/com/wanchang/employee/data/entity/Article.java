package com.wanchang.employee.data.entity;

/**
 * Created by stormlei on 2017/12/13.
 */

public class Article {


  /**
   * id : 3
   * category_id : 1
   * title : 助力在线医疗健客再收...
   * pic : upload/pic/2017/08/11/bYxfF7165a.jpg
   * order_id : 0
   * status : 100
   * created_at : 1502441810
   * updated_at : 1502441810
   * content : {"article_id":3,"content":"<p><span style=\"white-space: nowrap;\">更新记录<\/span><\/p><p><span style=\"white-space: nowrap;\">日期<span style=\"white-space:pre\">\t<\/span>更新内容<\/span><\/p><p><span style=\"white-space: nowrap;\">2017-6-8<span style=\"white-space:pre\">\t<\/span>编写<\/span><\/p><p><span style=\"white-space: nowrap;\"><br/><\/span><\/p><p><span style=\"white-space: nowrap;\"><br/><\/span><\/p><p><span style=\"white-space: nowrap;\">下单<\/span><\/p><p><span style=\"white-space: nowrap;\"><br/><\/span><\/p><p><span style=\"white-space: nowrap;\">下单<\/span><\/p><p><span style=\"white-space: nowrap;\">POST /order<\/span><\/p><p><span style=\"white-space: nowrap;\">请求参数<span style=\"white-space:pre\">\t<\/span>类型<span style=\"white-space:pre\">\t<\/span>必填<span style=\"white-space:pre\">\t<\/span>说明<span style=\"white-space:pre\">\t<\/span>备注<\/span><\/p><p><span style=\"white-space: nowrap;\">shop_id<span style=\"white-space:pre\">\t<\/span>int<span style=\"white-space:pre\">\t<\/span>✓<span style=\"white-space:pre\">\t<\/span>门店ID<span style=\"white-space:pre\">\t<\/span>-<\/span><\/p><p><span style=\"white-space: nowrap;\">address_id<span style=\"white-space:pre\">\t<\/span>int<span style=\"white-space:pre\">\t<\/span>-<span style=\"white-space:pre\">\t<\/span>收货地址ID<span style=\"white-space:pre\">\t<\/span>-<\/span><\/p><p><span style=\"white-space: nowrap;\">point<span style=\"white-space:pre\">\t<\/span>int<span style=\"white-space:pre\">\t<\/span>-<span style=\"white-space:pre\">\t<\/span>使用积分数<span style=\"white-space:pre\">\t<\/span>-<\/span><\/p><p><span style=\"white-space: nowrap;\">balance<span style=\"white-space:pre\">\t<\/span>float<span style=\"white-space:pre\">\t<\/span>-<span style=\"white-space:pre\">\t<\/span>是否使用余额<span style=\"white-space:pre\">\t<\/span>-<\/span><\/p><p><span style=\"white-space: nowrap;\">coupon_id<span style=\"white-space: pre;\">\t<\/span>int<span style=\"white-space: pre;\">\t<\/span>-<span style=\"white-space: pre;\">\t<\/span>使用的优惠券ID<span style=\"white-space: pre;\">\t<\/span>-<\/span><\/p><p><br/><\/p>","created_at":1502441810,"updated_at":1502441810}
   */

  private int id;
  private int category_id;
  private String title;
  private String pic;
  private int order_id;
  private int status;
  private long created_at;
  private long updated_at;
  private ContentBean content;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCategory_id() {
    return category_id;
  }

  public void setCategory_id(int category_id) {
    this.category_id = category_id;
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

  public ContentBean getContent() {
    return content;
  }

  public void setContent(ContentBean content) {
    this.content = content;
  }

  public static class ContentBean {

    /**
     * article_id : 3
     * content : <p><span style="white-space: nowrap;">更新记录</span></p><p><span style="white-space: nowrap;">日期<span style="white-space:pre">	</span>更新内容</span></p><p><span style="white-space: nowrap;">2017-6-8<span style="white-space:pre">	</span>编写</span></p><p><span style="white-space: nowrap;"><br/></span></p><p><span style="white-space: nowrap;"><br/></span></p><p><span style="white-space: nowrap;">下单</span></p><p><span style="white-space: nowrap;"><br/></span></p><p><span style="white-space: nowrap;">下单</span></p><p><span style="white-space: nowrap;">POST /order</span></p><p><span style="white-space: nowrap;">请求参数<span style="white-space:pre">	</span>类型<span style="white-space:pre">	</span>必填<span style="white-space:pre">	</span>说明<span style="white-space:pre">	</span>备注</span></p><p><span style="white-space: nowrap;">shop_id<span style="white-space:pre">	</span>int<span style="white-space:pre">	</span>✓<span style="white-space:pre">	</span>门店ID<span style="white-space:pre">	</span>-</span></p><p><span style="white-space: nowrap;">address_id<span style="white-space:pre">	</span>int<span style="white-space:pre">	</span>-<span style="white-space:pre">	</span>收货地址ID<span style="white-space:pre">	</span>-</span></p><p><span style="white-space: nowrap;">point<span style="white-space:pre">	</span>int<span style="white-space:pre">	</span>-<span style="white-space:pre">	</span>使用积分数<span style="white-space:pre">	</span>-</span></p><p><span style="white-space: nowrap;">balance<span style="white-space:pre">	</span>float<span style="white-space:pre">	</span>-<span style="white-space:pre">	</span>是否使用余额<span style="white-space:pre">	</span>-</span></p><p><span style="white-space: nowrap;">coupon_id<span style="white-space: pre;">	</span>int<span style="white-space: pre;">	</span>-<span style="white-space: pre;">	</span>使用的优惠券ID<span style="white-space: pre;">	</span>-</span></p><p><br/></p>
     * created_at : 1502441810
     * updated_at : 1502441810
     */

    private int article_id;
    private String content;
    private int created_at;
    private int updated_at;

    public int getArticle_id() {
      return article_id;
    }

    public void setArticle_id(int article_id) {
      this.article_id = article_id;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
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
