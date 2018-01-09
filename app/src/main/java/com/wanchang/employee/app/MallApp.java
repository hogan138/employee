package com.wanchang.employee.app;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.wanchang.employee.data.entity.LoginInfo;
import com.wanchang.employee.easemob.DemoHelper;
import com.wanchang.employee.ui.LoginActivity;
import com.wanchang.employee.ui.classify.ProductDetailActivity;
import com.wanchang.employee.ui.classify.ProductListActivity;
import com.wanchang.employee.ui.eventbus.RefreshPushMessageEvent;
import com.wanchang.employee.ui.home.HotSalesListActivity;
import com.wanchang.employee.ui.home.NewProductListActivity;
import com.wanchang.employee.ui.home.PromotionListActivity;
import com.wanchang.employee.ui.me.OrderDetailActivity;
import com.wanchang.employee.ui.me.OrderListActivity;
import com.wanchang.employee.ui.push.OrderMsgListActivity;
import com.wanchang.employee.ui.push.PromotionDetailActivity;
import com.wanchang.employee.ui.push.PromotionMsgListActivity;
import com.wanchang.employee.ui.push.SystemMsgListActivity;
import com.wanchang.employee.util.ACache;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import java.util.logging.Level;
import okhttp3.OkHttpClient;
import org.greenrobot.eventbus.EventBus;


/**
 * Created by stormlei on 2017/5/4.
 */

public class MallApp extends Application {

  private static MallApp instance ;

  public static MallApp getInstance() {
    return instance;
  }

  static {
    //设置全局的Header构建器
    SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
      @Override
      public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
        //layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
        return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
      }
    });
    //设置全局的Footer构建器
    SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
      @Override
      public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
        //指定为经典Footer，默认是 BallPulseFooter
        return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
      }
    });
  }

  @Override
  public void onCreate() {
    MultiDex.install(this);
    super.onCreate();
    instance = this;

    // utilcode
    Utils.init(this);
    LogUtils.getConfig().setLogSwitch(true);
    // okgo
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
      //log打印级别，决定了log显示的详细程度
    loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
      //log颜色级别，决定了log在控制台显示的颜色
    loggingInterceptor.setColorLevel(Level.INFO);
    builder.addInterceptor(loggingInterceptor);
    OkGo.getInstance().init(this)
        .setOkHttpClient(builder.build());
    // init demo helper
    DemoHelper.getInstance().init(this);
    // Initialize Realm
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration.Builder()
        .name("wanchang_employee.realm")
//        .schemaVersion(1)
//        .migration(new MyMigration())
        .build();
    Realm.setDefaultConfiguration(config);
    Stetho.initialize(
        Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
            .build());
    //zxing
    ZXingLibrary.initDisplayOpinion(this);

    //umeng
    final PushAgent mPushAgent = PushAgent.getInstance(this);
    mPushAgent.setDebugMode(true);
    //mPushAgent.setDisplayNotificationNumber(0);
    mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
    mPushAgent.setMuteDurationSeconds(3);

    UmengMessageHandler messageHandler = new UmengMessageHandler() {
      @Override
      public Notification getNotification(Context context, UMessage uMessage) {
        LogUtils.e(uMessage.extra);
        if ("10".equals(uMessage.extra.get("category"))) {
          ACache.get(instance).put(Constants.KEY_NEW_MSG_1, "1");
        }
        if ("20".equals(uMessage.extra.get("category"))) {
          ACache.get(instance).put(Constants.KEY_NEW_MSG_2, "2");
        }
        if ("30".equals(uMessage.extra.get("category"))) {
          ACache.get(instance).put(Constants.KEY_NEW_MSG_3, "3");
        }
        EventBus.getDefault().post(new RefreshPushMessageEvent());
        return super.getNotification(context, uMessage);
      }
    };
    mPushAgent.setMessageHandler(messageHandler);
    /**
     * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
     * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
     * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
     * */
    UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

      @Override
      public void handleMessage(Context context, UMessage uMessage) {
        super.handleMessage(context, uMessage);
        LogUtils.e(uMessage.extra);
        if (AppUtils.isAppForeground()) {
          MallApp.getInstance().cmdNavigation(context, uMessage.extra.get("cmd"));
        }
      }
    };
    //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
    //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
    mPushAgent.setNotificationClickHandler(notificationClickHandler);


    //注册推送服务 每次调用register都会回调该接口
    mPushAgent.register(new IUmengRegisterCallback() {
      @Override
      public void onSuccess(String deviceToken) {
        LogUtils.e("device token: " + deviceToken);
      }

      @Override
      public void onFailure(String s, String s1) {
        LogUtils.e("register failed: " + s + " " +s1);
      }
    });

//    //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
//    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//      @Override
//      public void onViewInitFinished(boolean arg0) {
//        // TODO Auto-generated method stub
//        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//        LogUtils.e("QbSdk", " onViewInitFinished is " + arg0);
//      }
//
//      @Override
//      public void onCoreInitFinished() {
//        // TODO Auto-generated method stub
//      }
//    };
//    //x5内核初始化接口
//    QbSdk.initX5Environment(getApplicationContext(),  cb);
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  public boolean isLogin() {
    LoginInfo loginInfo = (LoginInfo) ACache.get(this).getAsObject(Constants.KEY_LOGIN_INFO);
    return loginInfo != null && !"".equals(loginInfo.getAccess_token());
  }

  public String getToken() {
    LoginInfo loginInfo = (LoginInfo) ACache.get(this).getAsObject(Constants.KEY_LOGIN_INFO);
    if (loginInfo != null) {
      return loginInfo.getAccess_token();
    }else {
      return "";
    }
  }

  public int getUserId() {
    LoginInfo loginInfo = (LoginInfo) ACache.get(this).getAsObject(Constants.KEY_LOGIN_INFO);
    if (loginInfo != null && loginInfo.getUser() != null) {
      return loginInfo.getUser().getId();
    }else {
      return 1;
    }
  }

  public int getGroupId() {
    LoginInfo loginInfo = (LoginInfo) ACache.get(this).getAsObject(Constants.KEY_LOGIN_INFO);
    if (loginInfo != null && loginInfo.getUser() != null) {
      return loginInfo.getUser().getGroup_id();
    }else {
      return Constants.GROUP_CLIENT;
    }
  }

  public void cmdNavigation(Context mContext, String cmd) {
    String[] cmdArray = cmd.split("/");
    if (cmdArray.length >= 2) {
      String type = cmdArray[0];
      if ("product_list".equals(type)) {
        openActivity(mContext, new Intent(mContext, ProductListActivity.class), false);
      } else if ("product_list_hot".equals(type)) {
        openActivity(mContext, new Intent(mContext, HotSalesListActivity.class), true);
      } else if ("product_list_new".equals(type)) {
        openActivity(mContext, new Intent(mContext, NewProductListActivity.class), true);
      } else if ("product".equals(type)) {
        int id = Integer.parseInt(cmdArray[1]);
        openActivity(mContext, new Intent(mContext, ProductDetailActivity.class).putExtra("product_sku_id", id), false);
      } else if ("promotion_list".equals(type)) {
        openActivity(mContext, new Intent(mContext, PromotionListActivity.class), true);
      } else if ("promotion".equals(type)) {
        String key = cmdArray[1];
        openActivity(mContext, new Intent(mContext, PromotionDetailActivity.class).putExtra("key", key), true);
      } else if ("order_list".equals(type)) {
        openActivity(mContext, new Intent(mContext, OrderListActivity.class), true);
      } else if ("order".equals(type)) {
        String id = cmdArray[1];
        openActivity(mContext, new Intent(mContext, OrderDetailActivity.class).putExtra("order_id", id), true);
      } else if ("message_list_10".equals(type)) {
        openActivity(mContext, new Intent(mContext, SystemMsgListActivity.class), true);
      } else if ("message_list_20".equals(type)) {
        openActivity(mContext, new Intent(mContext, PromotionMsgListActivity.class), true);
      } else if ("message_list_30".equals(type)) {
        openActivity(mContext, new Intent(mContext, OrderMsgListActivity.class), true);
      } else if ("product_tag".equals(type)) {
        openActivity(mContext, new Intent(mContext, ProductListActivity.class), true);
      }
    }

  }

  public void openActivity(Context mContext, Intent intent, boolean requireLogin) {
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    if (requireLogin) {
      if (MallApp.getInstance().isLogin()) {
        startActivity(intent);
      } else {
        startActivity(new Intent(mContext, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
      }
    } else {
      startActivity(intent);
    }
  }

  public int getShopId() {
    int shop_id = 1;
//    LoginInfo loginInfo = (LoginInfo) ACache.get(this).getAsObject(Constants.KEY_LOGIN_INFO);
//    if (loginInfo != null) {
//      shop_id = loginInfo.getUser().getId();
//    }
//    String shopId = ACache.get(this).getAsString(Constants.KEY_SHOP_ID);
//    if (shopId != null) {
//      shop_id = Integer.parseInt(shopId);
//    }
    return shop_id;
  }

  public String getShopName() {
    String shop_name = "天天好大药房";
//    LoginInfo loginInfo = (LoginInfo) ACache.get(this).getAsObject(Constants.KEY_LOGIN_INFO);
//    if (loginInfo != null) {
//      shop_name = loginInfo.getUser().getName();
//    }
//    String shopName = ACache.get(this).getAsString(Constants.KEY_SHOP_NAME);
//    if (shopName != null) {
//      shop_name = shopName;
//    }
    return shop_name;
  }

}
