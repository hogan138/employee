package com.wanchang.employee.data.api;

/**
 * Created by stormlei on 2017/5/4.
 */

public class MallAPI {

  public static final String SERVER = "http://madmin.yanguangsoft.com";
  public static final String IMG_SERVER = "http://mup.yanguangsoft.com/";


  //upload image
  public static final String UPLOAD_IMAGE = SERVER + "/upload/image";
  //login
  public static final String AUTH_ACCOUNT_LOGIN = SERVER + "/auth/login";
  public static final String AUTH_MOBILE_LOGIN = SERVER + "/auth/mobile-login";
  public static final String AUTH_CAPTCHA = SERVER + "/auth/captcha";
  public static final String USER_LOGOUT = SERVER + "/user/user/logout";
  public static final String USER = SERVER + "/user/user";
  //classify
  public static final String PRODUCT_CATEGORY = SERVER + "/product-category";
  public static final String PRODUCT_LIST = SERVER + "/product";
  public static final String PRODUCT_FILTER_LIST = SERVER + "/product/filter-list";
  public static final String PRODUCT_VIEW = SERVER + "/product/view";
  public static final String CLIENT_COUPON_GET = SERVER + "/client-coupon/get";
  //news
  public static final String USER_SALESMAN_LIST = SERVER + "/user/salesman-list";
  //cart
  public static final String SHOP_LIST = SERVER + "/shop";
  public static final String SHOPPING_CART_ADD = SERVER + "/shopping-cart/add";
  public static final String SHOPPING_CART = SERVER + "/shopping-cart";
  public static final String SHOPPING_CART_CHANGE_CHECKED = SERVER + "/shopping-cart/change-checked";
  public static final String SHOPPING_CART_CHANGE_COUNT = SERVER + "/shopping-cart/change-count";
  public static final String SHOPPING_CART_CHECK_ALL = SERVER + "/shopping-cart/check-all";
  public static final String SHOPPING_CART_CHANGE_PROMOTION = SERVER + "/shopping-cart/change-promotion";
  public static final String SHOPPING_CART_BATCH_COMPLETE = SERVER + "/shopping-cart/batch-complete";
  public static final String SHOPPING_CART_REMOVE = SERVER + "/shopping-cart/remove";
  public static final String CHECKOUT = SERVER + "/checkout";
  public static final String ORDER = SERVER + "/order";
  public static final String ORDER_PAY_METHOD = SERVER + "/order/pay-method";
  public static final String ORDER_PAYED = SERVER + "/order/payed";
  public static final String ORDER_CANCEL = SERVER + "/order/cancel";
  public static final String ORDER_COMPLETE = SERVER + "/order/complete";
  //index
  public static final String INDEX_LIST = SERVER + "/index-list";
  public static final String SEARCH_HOT = SERVER + "/search/hot";
  public static final String BIND_DEVICE_TOKEN = SERVER + "/app/bind-device-token";
  public static final String PROMOTION = SERVER + "/promotion";
  public static final String PRODUCT_PRODUCT_CONTROL = SERVER + "/product/product-control";
  public static final String COUPON = SERVER + "/coupon";
  //me
  public static final String CLIENT_COUPON = SERVER + "/client-coupon";
  public static final String COLLECTION = SERVER + "/collection";
  public static final String CLIENT_CREDIT_BILL = SERVER + "/client-credit-bill";
  public static final String ORDER_PRODUCT = SERVER + "/order-product";
  public static final String RETURN_PRODUCT = SERVER + "/return-product";
  public static final String RETURN_PRODUCT_BATCH = SERVER + "/return-product/order-product-batch";
  public static final String RETURN_PRODUCT_RETURN = SERVER + "/return-product/return";
  //push
  public static final String PROMOTION_VIEW = SERVER + "/promotion/view";
  public static final String ARTICLE = SERVER + "/article";
  public static final String MESSAGE = SERVER + "/message";
  public static final String MESSAGE_LATEST = SERVER + "/message/latest";

  /*** salesman */
  //home
  public static final String SALESMAN_CLIENT_LIST = SERVER + "/salesman/client-list";

  public static final String USER_ADDRESS_BOOK = SERVER + "/user/address-book";
  public static final String USER_USER_INFO = SERVER + "/user/user-info";
  public static final String USER_GROUP_USER = SERVER + "/user/group-user";

  public static final String AREA_RECRUIT = SERVER + "/area-recruit";
  public static final String AREA_RECRUIT_APPLY = SERVER + "/area-recruit/apply";
  public static final String SIGN_CLIENT_LIST = SERVER + "/sign/client-list";
  public static final String SIGN = SERVER + "/sign";
  public static final String SIGN_CHART = SERVER + "/sign/chart";
  public static final String SALESMAN = SERVER + "/salesman";
  public static final String BONUS = SERVER + "/bonus";
  public static final String BONUS_APPLY = SERVER + "/bonus/apply";
  public static final String CLIENT = SERVER + "/client";
  public static final String CLIENT_SIGNUP = SERVER + "/client-signup";
  public static final String CHART_CLIENT_CHART = SERVER + "/chart/client-chart";
  public static final String CLIENT_PRODUCT_PRICE_LIST = SERVER + "/client-product-price/list";
  public static final String CLIENT_PRODUCT_PRICE_SET = SERVER + "/client-product-price/set";
  public static final String CLIENT_CREDIT_APPLY = SERVER + "/client-credit-apply";
  public static final String CHART_SALESMAN_CHART = SERVER + "/chart/salesman-chart";
  public static final String SALESMAN_DEP_LIST = SERVER + "/salesman/dep-list";



  /*** work */
  public static final String USER_DEPARTMENT_LIST = SERVER + "/user/user/department-list";
  public static final String CHART_PRODUCT_HEADER = SERVER + "/system/chart/product-header";
  public static final String CHART_PRODUCT_CHART = SERVER + "/system/chart/product-chart";
  public static final String CHART_PROMOTION_HEADER = SERVER + "/system/chart/promotion-header";
  public static final String CHART_PROMOTION_CHART = SERVER + "/system/chart/promotion-chart";
  public static final String SYS_CHART_SALESMAN_CHART = SERVER + "/system/chart/salesman-chart";
  public static final String CHART_SALESMAN_DETAIL = SERVER + "/system/chart/salesman-detail";
  public static final String CHART_AREA_CHART = SERVER + "/system/chart/area-chart";
  public static final String CHART_DEP_CHART = SERVER + "/system/chart/dep-chart";
  public static final String CHART_CLIENT_HEADER = SERVER + "/system/chart/client-header";
  public static final String SYS_CHART_CLIENT_CHART = SERVER + "/system/chart/client-chart";

  public static final String USER_USER_ADDRESS_BOOK = SERVER + "/user/user/address-book";
  public static final String GROUP_USER_INFO = SERVER + "/im/group/user-info";
  public static final String GROUP_GROUP_USER = SERVER + "/im/group/group-user";

  public static final String SYS_BIND_DEVICE_TOKEN = SERVER + "/system/app/bind-device-token";

  public static final String IM_MESSAGE_LATEST = SERVER + "/im/message/latest";
  public static final String IM_MESSAGE = SERVER + "/im/message";
  public static final String USER_USER_NOTIFICATION = SERVER + "/user/user-notification";
  public static final String USER_USER_NOTIFICATION_CHECK = SERVER + "/user/user-notification/check";


  public static final String NEWS = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
  public static final String APIKEY = "593e074aa96b18276fbe1aec8992f398";


}
