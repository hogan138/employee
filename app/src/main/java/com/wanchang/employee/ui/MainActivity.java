package com.wanchang.employee.ui;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.umeng.message.PushAgent;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.ContactTemp;
import com.wanchang.employee.data.entity.DepRole;
import com.wanchang.employee.data.entity.TabEntity;
import com.wanchang.employee.easemob.Constant;
import com.wanchang.employee.easemob.DemoHelper;
import com.wanchang.employee.easemob.db.DemoDBManager;
import com.wanchang.employee.easemob.runtimepermissions.PermissionsManager;
import com.wanchang.employee.easemob.runtimepermissions.PermissionsResultAction;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.ACache;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

  @BindView(R.id.tl)
  CommonTabLayout mTabLayout;

  private String[] mTitles = {"消息", "通讯录", "报表", "我的"};
  private int[] mIconUnselectIds = {R.drawable.tabbar_message_n, R.drawable.tabbar_address_n,
      R.drawable.tabbar_statement_n, R.drawable.tabbar_my_n};
  private int[] mIconSelectIds = {R.drawable.tabbar_message_s, R.drawable.tabbar_address_s,
      R.drawable.tabbar_statement_s, R.drawable.tabbar_my_s};
  private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
  private ArrayList<Fragment> mFragments = new ArrayList<>();

  // user logged into another device
  public boolean isConflict = false;
  // user account was removed
  private boolean isCurrentAccountRemoved = false;

  private BroadcastReceiver appExitReceiver;

  private ConversationFragment conversationFragment;

  //protected ImmersionBar mImmersionBar;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //make sure activity will not in background if user is logged into another device or removed
    if (getIntent() != null &&
        (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) ||
            getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
            getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false))) {
//      DemoHelper.getInstance().logout(false,null);
//      finish();
//      startActivity(new Intent(this, LoginActivity.class));
      sendBroadcast(new Intent(Constants.APP_EXIT_ACTION));
      return;
    } else if (getIntent() != null && getIntent().getBooleanExtra("isConflict", false)) {
//      finish();
//      startActivity(new Intent(this, LoginActivity.class));
      sendBroadcast(new Intent(Constants.APP_EXIT_ACTION));
      return;
    }
    registerExit();

    //mImmersionBar = ImmersionBar.with(this);
    //mImmersionBar.init();
  }

  private void registerExit() {
    appExitReceiver =  new BroadcastReceiver() {

      @Override
      public void onReceive(Context context, Intent intent) {
        ACache.get(mContext).clear();
        DemoHelper.getInstance().logout(true, null);
        finish();
      }
    };
    registerReceiver(appExitReceiver, new IntentFilter(Constants.APP_EXIT_ACTION));
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_main;
  }

  @Override
  protected void initData() {
    LogUtils.e("-------"+MallApp.getInstance().getGroupId());
    LogUtils.e("-------"+DeviceUtils.getManufacturer()+","+DeviceUtils.getModel());
    LogUtils.e("-------"+MallApp.getInstance().getUserId());
    LogUtils.e("-------"+PushAgent.getInstance(mContext).getRegistrationId());
    // runtime permission for android 6.0, just require all permissions here for simple
    requestPermissions();

    String depType = ACache.get(mContext).getAsString(Constants.KEY_DEPARTMENT_TYPE);
    if (TextUtils.isEmpty(depType)) {
      saveDepRole();
    }
  }

  private void saveDepRole() {
    OkGo.<String>get(MallAPI.USER_DEPARTMENT_LIST)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<DepRole> depRoleList = JSON.parseArray(response.body(), DepRole.class);
              if (depRoleList.size() >= 1) {

                ACache.get(mContext).put(Constants.KEY_DEPARTMENT_TYPE, depRoleList.get(0).getDepartment().getType()+"");
                ACache.get(mContext).put(Constants.KEY_DEPARTMENT_ID, depRoleList.get(0).getDepartment_id()+"");
                ACache.get(mContext).put(Constants.KEY_ROLE_ID, depRoleList.get(0).getRole_id()+"");

                ACache.get(mContext).put(Constants.KEY_DEPARTMENT_NAME, depRoleList.get(0).getDepartment().getName()+"");
                ACache.get(mContext).put(Constants.KEY_ROLE_NAME, depRoleList.get(0).getRole().getName()+"");

              }
            }
          }
        });
  }

  @Override
  protected void initView() {
    for (int i = 0; i < mTitles.length; i++) {
      mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
      if (i == 0) {
        mFragments.add(conversationFragment = ConversationFragment.newInstance("news"));
      } else if (i == 1) {
        mFragments.add(AddressBookFragment.newInstance("address_book"));
      } else if (i == 2) {
        mFragments.add(SelectReportFragment.newInstance("report"));
      } else {
        mFragments.add(MyWorkFragment.newInstance("my"));
      }

    }
    mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);

    showExceptionDialogFromIntent(getIntent());

    String cmd = getIntent().getStringExtra("cmd");
    if (!TextUtils.isEmpty(cmd)) {
      MallApp.getInstance().cmdNavigation(mContext, cmd);
    }
  }

  EMMessageListener messageListener = new EMMessageListener() {

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
      // notify new message
      for (EMMessage message : messages) {
        DemoHelper.getInstance().getNotifier().onNewMsg(message);
        saveContact(message);
      }
      refreshUIWithMessage();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
      //red packet code : 处理红包回执透传消息
//        for (EMMessage message : messages) {
//          EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
//          final String action = cmdMsgBody.action();//获取自定义action
//          if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
//            RedPacketUtil.receiveRedPacketAckMessage(message);
//          }
//        }
      //end of red packet code
      refreshUIWithMessage();
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
    }

    @Override
    public void onMessageDelivered(List<EMMessage> message) {
    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {
      refreshUIWithMessage();
    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {
    }
  };

  private void saveContact(EMMessage message) {
    String username = message.getUserName();
    String nickname = message.getStringAttribute(Constant.EXTRA_FROM_NAME, "");
    String avatar = message.getStringAttribute(Constant.EXTRA_FROM_AVATAR, "");
    ContactTemp contactTemp = new ContactTemp();
    contactTemp.setUsername(username);
    contactTemp.setNickname(nickname);
    contactTemp.setAvatar(avatar);
    contactTemp.setFriend(false);
    DemoDBManager.getInstance().saveContact(contactTemp);
  }

  private void refreshUIWithMessage() {
    runOnUiThread(new Runnable() {
      public void run() {
        // refresh unread count
        updateUnreadLabel();
        if (mTabLayout.getCurrentTab() == 0) {
          // refresh conversation list
          if (conversationFragment != null) {
            conversationFragment.refresh();
          }
        }
      }
    });
  }

  /**
   * update unread message count
   */
  public void updateUnreadLabel() {
    int count = getUnreadMsgCountTotal();
    if (count > 0) {
      mTabLayout.setMsgMargin(2, -8, 0);
      mTabLayout.showMsg(0, count);
    } else {
      mTabLayout.hideMsg(0);
    }
  }

  /**
   * get unread message count
   *
   * @return
   */
  public int getUnreadMsgCountTotal() {
    return EMClient.getInstance().chatManager().getUnreadMsgsCount();
  }

  @Override
  protected void onResume() {
    super.onResume();

    LogUtils.e("---------");

//    if (mTabLayout.getCurrentTab() == 0) {
//      // refresh conversation list
//      if (conversationFragment != null) {
//        conversationFragment.refresh();
//      }
//    }

    if (!isConflict && !isCurrentAccountRemoved) {
      updateUnreadLabel();
    }

    // unregister this event listener when this activity enters the
    // background
    DemoHelper sdkHelper = DemoHelper.getInstance();
    sdkHelper.pushActivity(this);

    EMClient.getInstance().chatManager().addMessageListener(messageListener);
  }

  @Override
  protected void onStop() {
    EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    DemoHelper sdkHelper = DemoHelper.getInstance();
    sdkHelper.popActivity(this);

    super.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    if (exceptionBuilder != null) {
      exceptionBuilder.create().dismiss();
      exceptionBuilder = null;
      isExceptionDialogShow = false;
    }

    unregisterReceiver(appExitReceiver);

//    if (mImmersionBar != null)
//      mImmersionBar.destroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    LogUtils.e(TAG, 111111);
    outState.putBoolean("isConflict", isConflict);
    outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
    super.onSaveInstanceState(outState);
    outState.clear();
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    LogUtils.e(TAG, 333333);
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    LogUtils.e(TAG, 222222);
    super.onNewIntent(intent);
    setIntent(intent);
    showExceptionDialogFromIntent(intent);

    String cmd = intent.getStringExtra("cmd");
    if (!TextUtils.isEmpty(cmd)) {
      MallApp.getInstance().cmdNavigation(mContext, cmd);
    }

    boolean goCart = intent.getBooleanExtra("go_cart", false);
    LogUtils.e(goCart+"===");
    if (goCart) {
      mTabLayout.setCurrentTab(3);
    }

    boolean goIndex = intent.getBooleanExtra("go_index", false);
    LogUtils.e(goIndex+"===");
    if (goIndex) {
      mTabLayout.setCurrentTab(0);
    }

//    boolean isAppExit = intent.getBooleanExtra("app_exit", false);
//    LogUtils.e(isAppExit+"===");
//    if (isAppExit) {
//      mTabLayout.setCurrentTab(0);
//    }
  }

  private android.app.AlertDialog.Builder exceptionBuilder;
  private boolean isExceptionDialogShow = false;

  private int getExceptionMessageId(String exceptionType) {
    if (exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
      return R.string.connect_conflict;
    } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
      return R.string.em_user_remove;
    } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
      return R.string.user_forbidden;
    }
    return R.string.Network_error;
  }

  /**
   * show the dialog when user met some exception: such as login on another device, user removed or user forbidden
   */
  private void showExceptionDialog(String exceptionType) {
    isExceptionDialogShow = true;
    String st = getResources().getString(R.string.Logoff_notification);
    if (!MainActivity.this.isFinishing()) {
      // clear up global variables
      try {
        if (exceptionBuilder == null)
          exceptionBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
        exceptionBuilder.setTitle(st);
        exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
        exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            exceptionBuilder = null;
            isExceptionDialogShow = false;
//            finish();
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);

            sendBroadcast(new Intent(Constants.APP_EXIT_ACTION));

          }
        });
        exceptionBuilder.setCancelable(false);
        exceptionBuilder.create().show();
        isConflict = true;
      } catch (Exception e) {
        LogUtils.e(TAG, "---------color conflictBuilder error" + e.getMessage());
      }
    }
  }

  private void showExceptionDialogFromIntent(Intent intent) {
    LogUtils.e(TAG, "showExceptionDialogFromIntent");
    if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
      showExceptionDialog(Constant.ACCOUNT_CONFLICT);
    } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
      showExceptionDialog(Constant.ACCOUNT_REMOVED);
    } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
      showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
    } else if (intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
        intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false)) {
//      this.finish();
//      startActivity(new Intent(this, LoginActivity.class));
      sendBroadcast(new Intent(Constants.APP_EXIT_ACTION));
    }
  }

  @Override
  public void onBackPressed() {
    exitApp();
  }

  private long exitTime = 0;

  private void exitApp() {
    if ((System.currentTimeMillis() - exitTime) > 2000) {
      ToastUtils.showShort("再按一次退出万昌医药");
      exitTime = System.currentTimeMillis();
    } else {
      finish();
    }
  }

  @TargetApi(23)
  private void requestPermissions() {
    PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
      @Override
      public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onDenied(String permission) {
        //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
  }

}
