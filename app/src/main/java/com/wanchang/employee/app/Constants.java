package com.wanchang.employee.app;

/**
 * Created by stormlei on 2017/5/4.
 */

public class Constants {

  //================= group ====================
  public static final int GROUP_CLIENT = 10;
  public static final int GROUP_SALESMAN = 20;

  //================= promotion category ====================
  public static final String PROMOTION_CATEGORY_10 = "有买有赠";
  public static final String PROMOTION_CATEGORY_20 = "打折";
  public static final String PROMOTION_CATEGORY_30 = "限时秒杀";
  public static final String PROMOTION_CATEGORY_40 = "特价专区";
  public static final String PROMOTION_CATEGORY_50 = "整单立减";
  public static final String PROMOTION_CATEGORY_60 = "单品合计满减";
  public static final String PROMOTION_CATEGORY_70 = "优惠券";


  //================= ACache ====================
  public static final String KEY_LOGIN_INFO = "login_info_key";
  public static final String KEY_GUIDE_SHOWED = "guide_showed_key";
  public static final String KEY_NEW_MSG_1 = "new_msg_1_key";
  public static final String KEY_NEW_MSG_2 = "new_msg_2_key";
  public static final String KEY_NEW_MSG_3 = "new_msg_3_key";
  public static final String KEY_CLIENT_ID = "client_id_key";
  public static final String KEY_CLIENT_NAME = "client_name_key";
  public static final String KEY_DEPARTMENT_TYPE = "department_type_key";
  public static final String KEY_DEPARTMENT_ID = "department_id_key";
  public static final String KEY_ROLE_ID = "role_id_key";

  //================= refresh state ====================
  public static final int STATE_NORMAL = 0;
  public static final int STATE_REFRESH = 1;
  public static final int STATE_MORE = 2;
  public static final int PAGE_SIZE = 20;

  public static final String APP_EXIT_ACTION = "com.wanchang.client.intent.action.APP_EXIT";

}
