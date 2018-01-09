package com.wanchang.employee.data.entity;

import android.os.Parcel;
import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by stormlei on 2017/11/15.
 */

public class Client implements IndexableEntity, android.os.Parcelable {


  /**
   * id : 1
   * category : 10
   * user_id : 2
   * name : 测试客户11
   * py : cskh11
   * erp_id : 0
   * erp_no :
   * pic : upload/pic/2017/11/16/HXg-F9XJOb.jpg
   * province_id : 330000
   * province : 浙江省
   * city_id : 330100
   * city : 杭州市
   * county_id : 330106
   * county : 西湖区
   * address : 拱墅区
   * bind_mobile : 13567163681
   * tel : 13567163681
   * lon : 120.092972
   * lat : 30.169327
   * medicare : 0
   * public_price : 0
   * billing : 0
   * billing_type :
   * invoice_type :
   * invoice_info :
   * license_validity : 1519899342
   * business_license_validity : 0
   * gps_validity : 0
   * purchase_proxy_validity : 0
   * purchase_contract_validity : 0
   * receiving_proxy_validity : 0
   * comment :
   * status : 100
   * created_at : 0
   * updated_at : 1513907109
   * need_check : 0
   * credit : {"id":3,"client_id":1,"balance":"0.00","credit_amount":"1000000.00","temporary_credit":"5000000.00","credit_used":"11116.75","bill_periods":60,"bill_date":12,"repayment_date":27,"last_bill_month":1512057600,"created_at":1509465600,"updated_at":1512437720,"credit_remain":"4988883.25"}
   * user : {"id":2,"username":"erp1","mobile":"15558028981","name":"客户1","id_card":"","pic":"upload/pic/2017/11/15/KV8BofcX7R.png","py":"kh1","im_account":"user_2","im_password":"60c1aba71209b2b8e7ed74279102294301b43fd5","group_id":10,"admin_role_id":0,"status":100,"created_at":1509171113,"updated_at":1513482682}
   */

  private int id;
  private int category;
  private int user_id;
  private String name;
  private String py;
  private int erp_id;
  private String erp_no;
  private String pic;
  private int province_id;
  private String province;
  private int city_id;
  private String city;
  private int county_id;
  private String county;
  private String address;
  private String bind_mobile;
  private String tel;
  private double lon;
  private double lat;
  private int medicare;
  private int public_price;
  private int billing;
  private String billing_type;
  private String invoice_type;
  private String invoice_info;
  private int license_validity;
  private int business_license_validity;
  private int gps_validity;
  private int purchase_proxy_validity;
  private int purchase_contract_validity;
  private int receiving_proxy_validity;
  private String comment;
  private int status;
  private int created_at;
  private int updated_at;
  private int need_check;
  private CreditBean credit;
  private User user;


  @Override
  public String getFieldIndexBy() {
    return name;
  }

  @Override
  public void setFieldIndexBy(String indexField) {
    this.name = indexField;
  }

  @Override
  public void setFieldPinyinIndexBy(String pinyin) {

  }


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

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPy() {
    return py;
  }

  public void setPy(String py) {
    this.py = py;
  }

  public int getErp_id() {
    return erp_id;
  }

  public void setErp_id(int erp_id) {
    this.erp_id = erp_id;
  }

  public String getErp_no() {
    return erp_no;
  }

  public void setErp_no(String erp_no) {
    this.erp_no = erp_no;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public int getProvince_id() {
    return province_id;
  }

  public void setProvince_id(int province_id) {
    this.province_id = province_id;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public int getCity_id() {
    return city_id;
  }

  public void setCity_id(int city_id) {
    this.city_id = city_id;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getCounty_id() {
    return county_id;
  }

  public void setCounty_id(int county_id) {
    this.county_id = county_id;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getBind_mobile() {
    return bind_mobile;
  }

  public void setBind_mobile(String bind_mobile) {
    this.bind_mobile = bind_mobile;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public int getMedicare() {
    return medicare;
  }

  public void setMedicare(int medicare) {
    this.medicare = medicare;
  }

  public int getPublic_price() {
    return public_price;
  }

  public void setPublic_price(int public_price) {
    this.public_price = public_price;
  }

  public int getBilling() {
    return billing;
  }

  public void setBilling(int billing) {
    this.billing = billing;
  }

  public String getBilling_type() {
    return billing_type;
  }

  public void setBilling_type(String billing_type) {
    this.billing_type = billing_type;
  }

  public String getInvoice_type() {
    return invoice_type;
  }

  public void setInvoice_type(String invoice_type) {
    this.invoice_type = invoice_type;
  }

  public String getInvoice_info() {
    return invoice_info;
  }

  public void setInvoice_info(String invoice_info) {
    this.invoice_info = invoice_info;
  }

  public int getLicense_validity() {
    return license_validity;
  }

  public void setLicense_validity(int license_validity) {
    this.license_validity = license_validity;
  }

  public int getBusiness_license_validity() {
    return business_license_validity;
  }

  public void setBusiness_license_validity(int business_license_validity) {
    this.business_license_validity = business_license_validity;
  }

  public int getGps_validity() {
    return gps_validity;
  }

  public void setGps_validity(int gps_validity) {
    this.gps_validity = gps_validity;
  }

  public int getPurchase_proxy_validity() {
    return purchase_proxy_validity;
  }

  public void setPurchase_proxy_validity(int purchase_proxy_validity) {
    this.purchase_proxy_validity = purchase_proxy_validity;
  }

  public int getPurchase_contract_validity() {
    return purchase_contract_validity;
  }

  public void setPurchase_contract_validity(int purchase_contract_validity) {
    this.purchase_contract_validity = purchase_contract_validity;
  }

  public int getReceiving_proxy_validity() {
    return receiving_proxy_validity;
  }

  public void setReceiving_proxy_validity(int receiving_proxy_validity) {
    this.receiving_proxy_validity = receiving_proxy_validity;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
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

  public int getNeed_check() {
    return need_check;
  }

  public void setNeed_check(int need_check) {
    this.need_check = need_check;
  }

  public CreditBean getCredit() {
    return credit;
  }

  public void setCredit(CreditBean credit) {
    this.credit = credit;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public static class CreditBean implements android.os.Parcelable {

    /**
     * id : 3
     * client_id : 1
     * balance : 0.00
     * credit_amount : 1000000.00
     * temporary_credit : 5000000.00
     * credit_used : 11116.75
     * bill_periods : 60
     * bill_date : 12
     * repayment_date : 27
     * last_bill_month : 1512057600
     * created_at : 1509465600
     * updated_at : 1512437720
     * credit_remain : 4988883.25
     */

    private int id;
    private int client_id;
    private String balance;
    private String credit_amount;
    private String temporary_credit;
    private String credit_used;
    private int bill_periods;
    private int bill_date;
    private int repayment_date;
    private int last_bill_month;
    private int created_at;
    private int updated_at;
    private String credit_remain;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getClient_id() {
      return client_id;
    }

    public void setClient_id(int client_id) {
      this.client_id = client_id;
    }

    public String getBalance() {
      return balance;
    }

    public void setBalance(String balance) {
      this.balance = balance;
    }

    public String getCredit_amount() {
      return credit_amount;
    }

    public void setCredit_amount(String credit_amount) {
      this.credit_amount = credit_amount;
    }

    public String getTemporary_credit() {
      return temporary_credit;
    }

    public void setTemporary_credit(String temporary_credit) {
      this.temporary_credit = temporary_credit;
    }

    public String getCredit_used() {
      return credit_used;
    }

    public void setCredit_used(String credit_used) {
      this.credit_used = credit_used;
    }

    public int getBill_periods() {
      return bill_periods;
    }

    public void setBill_periods(int bill_periods) {
      this.bill_periods = bill_periods;
    }

    public int getBill_date() {
      return bill_date;
    }

    public void setBill_date(int bill_date) {
      this.bill_date = bill_date;
    }

    public int getRepayment_date() {
      return repayment_date;
    }

    public void setRepayment_date(int repayment_date) {
      this.repayment_date = repayment_date;
    }

    public int getLast_bill_month() {
      return last_bill_month;
    }

    public void setLast_bill_month(int last_bill_month) {
      this.last_bill_month = last_bill_month;
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

    public String getCredit_remain() {
      return credit_remain;
    }

    public void setCredit_remain(String credit_remain) {
      this.credit_remain = credit_remain;
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.id);
      dest.writeInt(this.client_id);
      dest.writeString(this.balance);
      dest.writeString(this.credit_amount);
      dest.writeString(this.temporary_credit);
      dest.writeString(this.credit_used);
      dest.writeInt(this.bill_periods);
      dest.writeInt(this.bill_date);
      dest.writeInt(this.repayment_date);
      dest.writeInt(this.last_bill_month);
      dest.writeInt(this.created_at);
      dest.writeInt(this.updated_at);
      dest.writeString(this.credit_remain);
    }

    public CreditBean() {
    }

    protected CreditBean(Parcel in) {
      this.id = in.readInt();
      this.client_id = in.readInt();
      this.balance = in.readString();
      this.credit_amount = in.readString();
      this.temporary_credit = in.readString();
      this.credit_used = in.readString();
      this.bill_periods = in.readInt();
      this.bill_date = in.readInt();
      this.repayment_date = in.readInt();
      this.last_bill_month = in.readInt();
      this.created_at = in.readInt();
      this.updated_at = in.readInt();
      this.credit_remain = in.readString();
    }

    public static final Creator<CreditBean> CREATOR = new Creator<CreditBean>() {
      @Override
      public CreditBean createFromParcel(Parcel source) {
        return new CreditBean(source);
      }

      @Override
      public CreditBean[] newArray(int size) {
        return new CreditBean[size];
      }
    };
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeInt(this.category);
    dest.writeInt(this.user_id);
    dest.writeString(this.name);
    dest.writeString(this.py);
    dest.writeInt(this.erp_id);
    dest.writeString(this.erp_no);
    dest.writeString(this.pic);
    dest.writeInt(this.province_id);
    dest.writeString(this.province);
    dest.writeInt(this.city_id);
    dest.writeString(this.city);
    dest.writeInt(this.county_id);
    dest.writeString(this.county);
    dest.writeString(this.address);
    dest.writeString(this.bind_mobile);
    dest.writeString(this.tel);
    dest.writeDouble(this.lon);
    dest.writeDouble(this.lat);
    dest.writeInt(this.medicare);
    dest.writeInt(this.public_price);
    dest.writeInt(this.billing);
    dest.writeString(this.billing_type);
    dest.writeString(this.invoice_type);
    dest.writeString(this.invoice_info);
    dest.writeInt(this.license_validity);
    dest.writeInt(this.business_license_validity);
    dest.writeInt(this.gps_validity);
    dest.writeInt(this.purchase_proxy_validity);
    dest.writeInt(this.purchase_contract_validity);
    dest.writeInt(this.receiving_proxy_validity);
    dest.writeString(this.comment);
    dest.writeInt(this.status);
    dest.writeInt(this.created_at);
    dest.writeInt(this.updated_at);
    dest.writeInt(this.need_check);
    dest.writeParcelable(this.credit, flags);
    dest.writeParcelable(this.user, flags);
  }

  public Client() {
  }

  protected Client(Parcel in) {
    this.id = in.readInt();
    this.category = in.readInt();
    this.user_id = in.readInt();
    this.name = in.readString();
    this.py = in.readString();
    this.erp_id = in.readInt();
    this.erp_no = in.readString();
    this.pic = in.readString();
    this.province_id = in.readInt();
    this.province = in.readString();
    this.city_id = in.readInt();
    this.city = in.readString();
    this.county_id = in.readInt();
    this.county = in.readString();
    this.address = in.readString();
    this.bind_mobile = in.readString();
    this.tel = in.readString();
    this.lon = in.readDouble();
    this.lat = in.readDouble();
    this.medicare = in.readInt();
    this.public_price = in.readInt();
    this.billing = in.readInt();
    this.billing_type = in.readString();
    this.invoice_type = in.readString();
    this.invoice_info = in.readString();
    this.license_validity = in.readInt();
    this.business_license_validity = in.readInt();
    this.gps_validity = in.readInt();
    this.purchase_proxy_validity = in.readInt();
    this.purchase_contract_validity = in.readInt();
    this.receiving_proxy_validity = in.readInt();
    this.comment = in.readString();
    this.status = in.readInt();
    this.created_at = in.readInt();
    this.updated_at = in.readInt();
    this.need_check = in.readInt();
    this.credit = in.readParcelable(CreditBean.class.getClassLoader());
    this.user = in.readParcelable(User.class.getClassLoader());
  }

  public static final Creator<Client> CREATOR = new Creator<Client>() {
    @Override
    public Client createFromParcel(Parcel source) {
      return new Client(source);
    }

    @Override
    public Client[] newArray(int size) {
      return new Client[size];
    }
  };
}
