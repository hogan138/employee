package com.wanchang.employee.data.entity;

import android.os.Parcel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stormlei on 2017/6/30.
 */

public class Promotion implements android.os.Parcelable {


  /**
   * id : 6
   * title : 测试促销
   * pic : upload/pic/2017/11/27/TkFjwYwuGB.jpg,upload/pic/2017/11/27/P4RhNYzd5c.png,upload/pic/2017/11/27/TkFjwYwuGB.jpg,upload/pic/2017/11/27/P4RhNYzd5c.png
   * pic_app : upload/pic/2017/11/27/TkFjwYwuGB.jpg,upload/pic/2017/11/27/P4RhNYzd5c.png,upload/pic/2017/11/27/TkFjwYwuGB.jpg,upload/pic/2017/11/27/P4RhNYzd5c.png
   * category : 10
   * type : 10
   * discount_type : 0
   * rules : [{"condition":1000,"data":{"gift_type":10,"gift_name":"本品","count":1}},{"condition":2000,"data":{"gift_type":20,"gift_name":"小儿氨酚黄那敏颗粒","count":1,"data":1}}]
   * composition : 0
   * start_at : 1511762998
   * end_at : 1512022198
   * client_type : 20
   * create_page : 0
   * remark : 212123232
   * key : a732a481323b3d5e3ac1af3c881ecdace65bd5e9
   * status : 10
   * created_at : 1511763067
   * updated_at : 1512357685
   */

  private int id;
  private String title;
  private String pic;
  private String pic_app;
  private int category;
  private int type;
  private int discount_type;
  private String rules;
  private int composition;
  private long start_at;
  private long end_at;
  private int client_type;
  private int create_page;
  private String remark;
  private String key;
  private int status;
  private int created_at;
  private int updated_at;
  private String cmd;
  private List<ProductListBean> product_list;


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

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public String getPic_app() {
    return pic_app;
  }

  public void setPic_app(String pic_app) {
    this.pic_app = pic_app;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getDiscount_type() {
    return discount_type;
  }

  public void setDiscount_type(int discount_type) {
    this.discount_type = discount_type;
  }

  public String getRules() {
    return rules;
  }

  public void setRules(String rules) {
    this.rules = rules;
  }

  public int getComposition() {
    return composition;
  }

  public void setComposition(int composition) {
    this.composition = composition;
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

  public int getClient_type() {
    return client_type;
  }

  public void setClient_type(int client_type) {
    this.client_type = client_type;
  }

  public int getCreate_page() {
    return create_page;
  }

  public void setCreate_page(int create_page) {
    this.create_page = create_page;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
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

  public String getCmd() {
    return cmd;
  }

  public void setCmd(String cmd) {
    this.cmd = cmd;
  }

  public List<ProductListBean> getProduct_list() {
    return product_list;
  }

  public void setProduct_list(List<ProductListBean> product_list) {
    this.product_list = product_list;
  }

  public static class ProductListBean {

    /**
     * product_sku_id : 1
     * title : 小儿氨酚黄那敏颗粒
     * pic : upload/pic/image/default.jpg
     * manufacture_name : 亚宝药业四川制药有限公司
     * specs : 6g*15袋
     * packaing : 30
     * unit : 袋
     * validity : 0000-00-00
     * price : 10
     */

    private int product_sku_id;
    private String title;
    private String pic;
    private String manufacture_name;
    private String specs;
    private int packaing;
    private String unit;
    private String validity;
    private float price;

    public int getProduct_sku_id() {
      return product_sku_id;
    }

    public void setProduct_sku_id(int product_sku_id) {
      this.product_sku_id = product_sku_id;
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

    public String getManufacture_name() {
      return manufacture_name;
    }

    public void setManufacture_name(String manufacture_name) {
      this.manufacture_name = manufacture_name;
    }

    public String getSpecs() {
      return specs;
    }

    public void setSpecs(String specs) {
      this.specs = specs;
    }

    public int getPackaing() {
      return packaing;
    }

    public void setPackaing(int packaing) {
      this.packaing = packaing;
    }

    public String getUnit() {
      return unit;
    }

    public void setUnit(String unit) {
      this.unit = unit;
    }

    public String getValidity() {
      return validity;
    }

    public void setValidity(String validity) {
      this.validity = validity;
    }

    public float getPrice() {
      return price;
    }

    public void setPrice(float price) {
      this.price = price;
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.title);
    dest.writeString(this.pic);
    dest.writeString(this.pic_app);
    dest.writeInt(this.category);
    dest.writeInt(this.type);
    dest.writeInt(this.discount_type);
    dest.writeString(this.rules);
    dest.writeInt(this.composition);
    dest.writeLong(this.start_at);
    dest.writeLong(this.end_at);
    dest.writeInt(this.client_type);
    dest.writeInt(this.create_page);
    dest.writeString(this.remark);
    dest.writeString(this.key);
    dest.writeInt(this.status);
    dest.writeInt(this.created_at);
    dest.writeInt(this.updated_at);
    dest.writeString(this.cmd);
    dest.writeList(this.product_list);
  }

  public Promotion() {
  }

  protected Promotion(Parcel in) {
    this.id = in.readInt();
    this.title = in.readString();
    this.pic = in.readString();
    this.pic_app = in.readString();
    this.category = in.readInt();
    this.type = in.readInt();
    this.discount_type = in.readInt();
    this.rules = in.readString();
    this.composition = in.readInt();
    this.start_at = in.readLong();
    this.end_at = in.readLong();
    this.client_type = in.readInt();
    this.create_page = in.readInt();
    this.remark = in.readString();
    this.key = in.readString();
    this.status = in.readInt();
    this.created_at = in.readInt();
    this.updated_at = in.readInt();
    this.cmd = in.readString();
    this.product_list = new ArrayList<ProductListBean>();
    in.readList(this.product_list, ProductListBean.class.getClassLoader());
  }

  public static final Creator<Promotion> CREATOR = new Creator<Promotion>() {
    @Override
    public Promotion createFromParcel(Parcel source) {
      return new Promotion(source);
    }

    @Override
    public Promotion[] newArray(int size) {
      return new Promotion[size];
    }
  };
}
