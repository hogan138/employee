package com.wanchang.employee.data.entity;

import android.os.Parcel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stormlei on 2017/11/23.
 */

public class ProductDetail implements android.os.Parcelable {


  /**
   * sku : {"id":2371,"product_id":791,"batch_number":"","validity":"0000-00-00","stock":9999,"status":100,"created_at":1510765530,"updated_at":1510765530}
   * product : {"id":791,"fcategory_id":1,"scategory_id":10,"title":"小儿氨酚黄那敏颗粒","pic":"upload/pic/2017/03/22/4020401303d5315228c2ec9bda6c8b1ef4be3c89.jpg","manufacture_id":0,"manufacture_name":"华润三九(北京)药业有限公司","category_dosage":"","category_chinese_medicine":"","category_prescription":"","category_insurance":"","specs":"6g*10袋","packaing":30,"piece_packaing":360,"unit":"盒","auth_doc_num":"国药准字H11022051","retail_price":"0.00","min_purchase_count":1,"mul_purchase":1,"stock_mode":0,"stock_warn":0,"oprice":"0.00","order_id":0,"sales":0,"views":0,"recommend":0,"status":100,"need_check":0,"created_at":0,"updated_at":0}
   * price : {"id":78212,"product_id":791,"product_sku_id":2371,"client_category":20,"area_id":330100,"price":"90.20","bonus_category":20,"bonus_price":"72.16","bonus_rate":0,"created_at":1510766902,"updated_at":1510766902}
   * promotion_list : [{"title":"2017打折","category":20},{"title":"2017限时秒杀","category":30},{"title":"2017特价专区","category":40},{"title":"2017整单立减","category":50}]
   * coupon_list : [{"id":1,"category":100,"name":"测试优惠券","value":100,"condition":10000,"key":"AD7FBA6AA9DA366619CB01E7D94057AD56997FF3"},{"id":2,"category":10,"name":"满100-10","value":10,"condition":100,"key":"5902734C0665304EE3D5E0C4AB5EE17F2549FAA2"},{"id":3,"category":30,"name":"满200-25","value":25,"condition":200,"key":"F6A9473C69A575451BC86829C328B9AB231753AE"},{"id":4,"category":40,"name":"满300-35","value":35,"condition":300,"key":"97AB3DB591FBEC52EA36A3B8E03595EEE8A941B8"},{"id":5,"category":10,"name":"测试优惠券产品部","value":100,"condition":10000,"key":"0AED7119B556AA53A30999CF23196BCC353B7D93"},{"id":6,"category":40,"name":"测试优惠券生产尝试","value":1000,"condition":10000,"key":"26489A69FEECCC0BEED19FA7016BF07DCB0A0CA3"},{"id":7,"category":50,"name":"测试优惠券商品","value":100,"condition":10000,"key":"EF1E8F400C16D72B9A82025D24A0A20ADCE982AA"}]
   * shopping_cart_count : 0
   */

  private SkuBean product_sku;
  private ProductBean product;
  private PriceBean price;
  private int shopping_cart_count;
  private List<Promotion> promotion_list;
  private List<Coupon> coupon_list;

  public SkuBean getProduct_sku() {
    return product_sku;
  }

  public void setProduct_sku(SkuBean product_sku) {
    this.product_sku = product_sku;
  }

  public ProductBean getProduct() {
    return product;
  }

  public void setProduct(ProductBean product) {
    this.product = product;
  }

  public PriceBean getPrice() {
    return price;
  }

  public void setPrice(PriceBean price) {
    this.price = price;
  }

  public int getShopping_cart_count() {
    return shopping_cart_count;
  }

  public void setShopping_cart_count(int shopping_cart_count) {
    this.shopping_cart_count = shopping_cart_count;
  }

  public List<Promotion> getPromotion_list() {
    return promotion_list;
  }

  public void setPromotion_list(List<Promotion> promotion_list) {
    this.promotion_list = promotion_list;
  }

  public List<Coupon> getCoupon_list() {
    return coupon_list;
  }

  public void setCoupon_list(List<Coupon> coupon_list) {
    this.coupon_list = coupon_list;
  }

  public static class SkuBean implements android.os.Parcelable {

    /**
     * id : 2371
     * product_id : 791
     * batch_number :
     * validity : 0000-00-00
     * stock : 9999
     * status : 100
     * created_at : 1510765530
     * updated_at : 1510765530
     */

    private int id;
    private int product_id;
    private String batch_number;
    private String validity;
    private int stock;
    private int status;
    private int created_at;
    private int updated_at;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getProduct_id() {
      return product_id;
    }

    public void setProduct_id(int product_id) {
      this.product_id = product_id;
    }

    public String getBatch_number() {
      return batch_number;
    }

    public void setBatch_number(String batch_number) {
      this.batch_number = batch_number;
    }

    public String getValidity() {
      return validity;
    }

    public void setValidity(String validity) {
      this.validity = validity;
    }

    public int getStock() {
      return stock;
    }

    public void setStock(int stock) {
      this.stock = stock;
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
      dest.writeInt(this.id);
      dest.writeInt(this.product_id);
      dest.writeString(this.batch_number);
      dest.writeString(this.validity);
      dest.writeInt(this.stock);
      dest.writeInt(this.status);
      dest.writeInt(this.created_at);
      dest.writeInt(this.updated_at);
    }

    public SkuBean() {
    }

    protected SkuBean(Parcel in) {
      this.id = in.readInt();
      this.product_id = in.readInt();
      this.batch_number = in.readString();
      this.validity = in.readString();
      this.stock = in.readInt();
      this.status = in.readInt();
      this.created_at = in.readInt();
      this.updated_at = in.readInt();
    }

    public static final Creator<SkuBean> CREATOR = new Creator<SkuBean>() {
      @Override
      public SkuBean createFromParcel(Parcel source) {
        return new SkuBean(source);
      }

      @Override
      public SkuBean[] newArray(int size) {
        return new SkuBean[size];
      }
    };
  }

  public static class ProductBean implements android.os.Parcelable {

    /**
     * id : 791
     * fcategory_id : 1
     * scategory_id : 10
     * title : 小儿氨酚黄那敏颗粒
     * pic : upload/pic/2017/03/22/4020401303d5315228c2ec9bda6c8b1ef4be3c89.jpg
     * manufacture_id : 0
     * manufacture_name : 华润三九(北京)药业有限公司
     * category_dosage :
     * category_chinese_medicine :
     * category_prescription :
     * category_insurance :
     * specs : 6g*10袋
     * packaing : 30
     * piece_packaing : 360
     * unit : 盒
     * auth_doc_num : 国药准字H11022051
     * retail_price : 0.00
     * min_purchase_count : 1
     * mul_purchase : 1
     * stock_mode : 0
     * stock_warn : 0
     * oprice : 0.00
     * order_id : 0
     * sales : 0
     * views : 0
     * recommend : 0
     * status : 100
     * need_check : 0
     * created_at : 0
     * updated_at : 0
     */

    private int id;
    private int fcategory_id;
    private int scategory_id;
    private String title;
    private String pic;
    private int manufacture_id;
    private String manufacture_name;
    private String category_dosage;
    private String category_chinese_medicine;
    private String category_prescription;
    private String category_insurance;
    private String specs;
    private int packaing;
    private int piece_packaing;
    private String unit;
    private String auth_doc_num;
    private String retail_price;
    private int min_purchase_count;
    private int mul_purchase;
    private int stock_mode;
    private int stock_warn;
    private String oprice;
    private int order_id;
    private int sales;
    private int views;
    private int recommend;
    private int return_dateline;
    private int contain_numb;
    private int status;
    private int need_check;
    private int created_at;
    private int updated_at;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getFcategory_id() {
      return fcategory_id;
    }

    public void setFcategory_id(int fcategory_id) {
      this.fcategory_id = fcategory_id;
    }

    public int getScategory_id() {
      return scategory_id;
    }

    public void setScategory_id(int scategory_id) {
      this.scategory_id = scategory_id;
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

    public int getManufacture_id() {
      return manufacture_id;
    }

    public void setManufacture_id(int manufacture_id) {
      this.manufacture_id = manufacture_id;
    }

    public String getManufacture_name() {
      return manufacture_name;
    }

    public void setManufacture_name(String manufacture_name) {
      this.manufacture_name = manufacture_name;
    }

    public String getCategory_dosage() {
      return category_dosage;
    }

    public void setCategory_dosage(String category_dosage) {
      this.category_dosage = category_dosage;
    }

    public String getCategory_chinese_medicine() {
      return category_chinese_medicine;
    }

    public void setCategory_chinese_medicine(String category_chinese_medicine) {
      this.category_chinese_medicine = category_chinese_medicine;
    }

    public String getCategory_prescription() {
      return category_prescription;
    }

    public void setCategory_prescription(String category_prescription) {
      this.category_prescription = category_prescription;
    }

    public String getCategory_insurance() {
      return category_insurance;
    }

    public void setCategory_insurance(String category_insurance) {
      this.category_insurance = category_insurance;
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

    public int getPiece_packaing() {
      return piece_packaing;
    }

    public void setPiece_packaing(int piece_packaing) {
      this.piece_packaing = piece_packaing;
    }

    public String getUnit() {
      return unit;
    }

    public void setUnit(String unit) {
      this.unit = unit;
    }

    public String getAuth_doc_num() {
      return auth_doc_num;
    }

    public void setAuth_doc_num(String auth_doc_num) {
      this.auth_doc_num = auth_doc_num;
    }

    public String getRetail_price() {
      return retail_price;
    }

    public void setRetail_price(String retail_price) {
      this.retail_price = retail_price;
    }

    public int getMin_purchase_count() {
      return min_purchase_count;
    }

    public void setMin_purchase_count(int min_purchase_count) {
      this.min_purchase_count = min_purchase_count;
    }

    public int getMul_purchase() {
      return mul_purchase;
    }

    public void setMul_purchase(int mul_purchase) {
      this.mul_purchase = mul_purchase;
    }

    public int getStock_mode() {
      return stock_mode;
    }

    public void setStock_mode(int stock_mode) {
      this.stock_mode = stock_mode;
    }

    public int getStock_warn() {
      return stock_warn;
    }

    public void setStock_warn(int stock_warn) {
      this.stock_warn = stock_warn;
    }

    public String getOprice() {
      return oprice;
    }

    public void setOprice(String oprice) {
      this.oprice = oprice;
    }

    public int getOrder_id() {
      return order_id;
    }

    public void setOrder_id(int order_id) {
      this.order_id = order_id;
    }

    public int getSales() {
      return sales;
    }

    public void setSales(int sales) {
      this.sales = sales;
    }

    public int getViews() {
      return views;
    }

    public void setViews(int views) {
      this.views = views;
    }

    public int getRecommend() {
      return recommend;
    }

    public void setRecommend(int recommend) {
      this.recommend = recommend;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public int getNeed_check() {
      return need_check;
    }

    public void setNeed_check(int need_check) {
      this.need_check = need_check;
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

    public int getReturn_dateline() {
      return return_dateline;
    }

    public void setReturn_dateline(int return_dateline) {
      this.return_dateline = return_dateline;
    }

    public int getContain_numb() {
      return contain_numb;
    }

    public void setContain_numb(int contain_numb) {
      this.contain_numb = contain_numb;
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.id);
      dest.writeInt(this.fcategory_id);
      dest.writeInt(this.scategory_id);
      dest.writeString(this.title);
      dest.writeString(this.pic);
      dest.writeInt(this.manufacture_id);
      dest.writeString(this.manufacture_name);
      dest.writeString(this.category_dosage);
      dest.writeString(this.category_chinese_medicine);
      dest.writeString(this.category_prescription);
      dest.writeString(this.category_insurance);
      dest.writeString(this.specs);
      dest.writeInt(this.packaing);
      dest.writeInt(this.piece_packaing);
      dest.writeString(this.unit);
      dest.writeString(this.auth_doc_num);
      dest.writeString(this.retail_price);
      dest.writeInt(this.min_purchase_count);
      dest.writeInt(this.mul_purchase);
      dest.writeInt(this.stock_mode);
      dest.writeInt(this.stock_warn);
      dest.writeString(this.oprice);
      dest.writeInt(this.order_id);
      dest.writeInt(this.sales);
      dest.writeInt(this.views);
      dest.writeInt(this.recommend);
      dest.writeInt(this.status);
      dest.writeInt(this.contain_numb);
      dest.writeInt(this.return_dateline);
      dest.writeInt(this.need_check);
      dest.writeInt(this.created_at);
      dest.writeInt(this.updated_at);
    }

    public ProductBean() {
    }

    protected ProductBean(Parcel in) {
      this.id = in.readInt();
      this.fcategory_id = in.readInt();
      this.scategory_id = in.readInt();
      this.title = in.readString();
      this.pic = in.readString();
      this.manufacture_id = in.readInt();
      this.manufacture_name = in.readString();
      this.category_dosage = in.readString();
      this.category_chinese_medicine = in.readString();
      this.category_prescription = in.readString();
      this.category_insurance = in.readString();
      this.specs = in.readString();
      this.packaing = in.readInt();
      this.piece_packaing = in.readInt();
      this.unit = in.readString();
      this.auth_doc_num = in.readString();
      this.retail_price = in.readString();
      this.min_purchase_count = in.readInt();
      this.mul_purchase = in.readInt();
      this.stock_mode = in.readInt();
      this.stock_warn = in.readInt();
      this.oprice = in.readString();
      this.order_id = in.readInt();
      this.sales = in.readInt();
      this.views = in.readInt();
      this.recommend = in.readInt();
      this.contain_numb = in.readInt();
      this.return_dateline = in.readInt();
      this.status = in.readInt();
      this.need_check = in.readInt();
      this.created_at = in.readInt();
      this.updated_at = in.readInt();
    }

    public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
      @Override
      public ProductBean createFromParcel(Parcel source) {
        return new ProductBean(source);
      }

      @Override
      public ProductBean[] newArray(int size) {
        return new ProductBean[size];
      }
    };
  }

  public static class PriceBean implements android.os.Parcelable {

    /**
     * id : 78212
     * product_id : 791
     * product_sku_id : 2371
     * client_category : 20
     * area_id : 330100
     * price : 90.20
     * bonus_category : 20
     * bonus_price : 72.16
     * bonus_rate : 0
     * created_at : 1510766902
     * updated_at : 1510766902
     */

    private int id;
    private int product_id;
    private int product_sku_id;
    private int client_category;
    private int area_id;
    private String price;
    private int bonus_category;
    private String bonus_price;
    private int bonus_rate;
    private int created_at;
    private int updated_at;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getProduct_id() {
      return product_id;
    }

    public void setProduct_id(int product_id) {
      this.product_id = product_id;
    }

    public int getProduct_sku_id() {
      return product_sku_id;
    }

    public void setProduct_sku_id(int product_sku_id) {
      this.product_sku_id = product_sku_id;
    }

    public int getClient_category() {
      return client_category;
    }

    public void setClient_category(int client_category) {
      this.client_category = client_category;
    }

    public int getArea_id() {
      return area_id;
    }

    public void setArea_id(int area_id) {
      this.area_id = area_id;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }

    public int getBonus_category() {
      return bonus_category;
    }

    public void setBonus_category(int bonus_category) {
      this.bonus_category = bonus_category;
    }

    public String getBonus_price() {
      return bonus_price;
    }

    public void setBonus_price(String bonus_price) {
      this.bonus_price = bonus_price;
    }

    public int getBonus_rate() {
      return bonus_rate;
    }

    public void setBonus_rate(int bonus_rate) {
      this.bonus_rate = bonus_rate;
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
      dest.writeInt(this.id);
      dest.writeInt(this.product_id);
      dest.writeInt(this.product_sku_id);
      dest.writeInt(this.client_category);
      dest.writeInt(this.area_id);
      dest.writeString(this.price);
      dest.writeInt(this.bonus_category);
      dest.writeString(this.bonus_price);
      dest.writeInt(this.bonus_rate);
      dest.writeInt(this.created_at);
      dest.writeInt(this.updated_at);
    }

    public PriceBean() {
    }

    protected PriceBean(Parcel in) {
      this.id = in.readInt();
      this.product_id = in.readInt();
      this.product_sku_id = in.readInt();
      this.client_category = in.readInt();
      this.area_id = in.readInt();
      this.price = in.readString();
      this.bonus_category = in.readInt();
      this.bonus_price = in.readString();
      this.bonus_rate = in.readInt();
      this.created_at = in.readInt();
      this.updated_at = in.readInt();
    }

    public static final Creator<PriceBean> CREATOR = new Creator<PriceBean>() {
      @Override
      public PriceBean createFromParcel(Parcel source) {
        return new PriceBean(source);
      }

      @Override
      public PriceBean[] newArray(int size) {
        return new PriceBean[size];
      }
    };
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.product_sku, flags);
    dest.writeParcelable(this.product, flags);
    dest.writeParcelable(this.price, flags);
    dest.writeInt(this.shopping_cart_count);
    dest.writeList(this.promotion_list);
    dest.writeTypedList(this.coupon_list);
  }

  public ProductDetail() {
  }

  protected ProductDetail(Parcel in) {
    this.product_sku = in.readParcelable(SkuBean.class.getClassLoader());
    this.product = in.readParcelable(ProductBean.class.getClassLoader());
    this.price = in.readParcelable(PriceBean.class.getClassLoader());
    this.shopping_cart_count = in.readInt();
    this.promotion_list = new ArrayList<Promotion>();
    in.readList(this.promotion_list, Promotion.class.getClassLoader());
    this.coupon_list = in.createTypedArrayList(Coupon.CREATOR);
  }

  public static final Creator<ProductDetail> CREATOR = new Creator<ProductDetail>() {
    @Override
    public ProductDetail createFromParcel(Parcel source) {
      return new ProductDetail(source);
    }

    @Override
    public ProductDetail[] newArray(int size) {
      return new ProductDetail[size];
    }
  };
}
