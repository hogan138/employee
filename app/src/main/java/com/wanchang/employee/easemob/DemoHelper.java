package com.wanchang.employee.easemob;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;
import com.blankj.utilcode.util.LogUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMessage.Type;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseEmojiconInfoProvider;
import com.hyphenate.easeui.EaseUI.EaseSettingsProvider;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.model.EaseNotifier.EaseNotificationInfoProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.ContactTemp;
import com.wanchang.employee.easemob.db.DemoDBManager;
import com.wanchang.employee.easemob.domain.EmojiconExampleGroupData;
import com.wanchang.employee.easemob.parse.UserProfileManager;
import com.wanchang.employee.easemob.receiver.CallReceiver;
import com.wanchang.employee.easemob.ui.ChatActivity;
import com.wanchang.employee.easemob.ui.VideoCallActivity;
import com.wanchang.employee.easemob.ui.VoiceCallActivity;
import com.wanchang.employee.ui.MainActivity;
import java.util.List;
import java.util.Map;

public class DemoHelper {

  protected static final String TAG = "DemoHelper";

  private EaseUI easeUI;

  /**
   * EMEventListener
   */
  protected EMMessageListener messageListener = null;

  private Map<String, EaseUser> contactList;

  private UserProfileManager userProManager;

  private static DemoHelper instance = null;

  public boolean isVoiceCalling;
  public boolean isVideoCalling;

  private String username;

  private Context appContext;

  private CallReceiver callReceiver;


  private DemoHelper() {
  }

  public synchronized static DemoHelper getInstance() {
    if (instance == null) {
      instance = new DemoHelper();
    }
    return instance;
  }

  /**
   * init helper
   *
   * @param context application context
   */
  public void init(Context context) {

    EMOptions options = initChatOptions();
    //use default options if options is null
    if (EaseUI.getInstance().init(context, options)) {
      appContext = context;

      //debug mode, you'd better set it to false, if you want release your MallApp officially.
      EMClient.getInstance().setDebugMode(true);
      //get easeui instance
      easeUI = EaseUI.getInstance();
      //to set user's profile and avatar
      setEaseUIProviders();
      //initialize profile manager
      getUserProfileManager().init(context);

      // enabled fixed sample rate
      EMClient.getInstance().callManager().getCallOptions().enableFixedVideoResolution(true);

      // Offline call push
      EMClient.getInstance().callManager().getCallOptions().setIsSendPushIfOffline(true);

      setGlobalListeners();
    }
  }


  private EMOptions initChatOptions() {
    Log.d(TAG, "init HuanXin Options");

    EMOptions options = new EMOptions();
    // set if accept the invitation automatically
    options.setAcceptInvitationAlways(false);
    // set if you need read ack
    options.setRequireAck(true);
    // set if you need delivery ack
    options.setRequireDeliveryAck(false);

    //you need apply & set your own id if you want to use google cloud messaging.
    //options.setGCMNumber("324169311137");
    //you need apply & set your own id if you want to use Mi push notification
    options.setMipushConfig("2882303761517426801", "5381742660801");

    return options;
  }

  protected void setEaseUIProviders() {
    // set profile provider if you want easeUI to handle avatar and nickname
    easeUI.setUserProfileProvider(new EaseUserProfileProvider() {

      @Override
      public EaseUser getUser(String username) {
        return getUserInfo(username);
      }
    });

    //set options
    easeUI.setSettingsProvider(new EaseSettingsProvider() {

      @Override
      public boolean isSpeakerOpened() {
        return true;
      }

      @Override
      public boolean isMsgVibrateAllowed(EMMessage message) {
        return true;
      }

      @Override
      public boolean isMsgSoundAllowed(EMMessage message) {
        return true;
      }

      @Override
      public boolean isMsgNotifyAllowed(EMMessage message) {
        if(message == null){
          return true;
        }
        String chatUsename = null;
        List<String> notNotifyIds = null;
        // get user or group id which was blocked to show message notifications
        if (message.getChatType() == ChatType.Chat) {
          chatUsename = message.getFrom();
        } else {
          chatUsename = message.getTo();
        }
        notNotifyIds = DemoDBManager.getInstance().getBlockIds();
        if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
          return true;
        } else {
          return false;
        }
      }
    });

    //set emoji icon provider
    easeUI.setEmojiconInfoProvider(new EaseEmojiconInfoProvider() {

      @Override
      public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
        EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
        for (EaseEmojicon emojicon : data.getEmojiconList()) {
          if (emojicon.getIdentityCode().equals(emojiconIdentityCode)) {
            return emojicon;
          }
        }
        return null;
      }

      @Override
      public Map<String, Object> getTextEmojiconMapping() {
        return null;
      }
    });

    //set notification options, will use default if you don't set it
    easeUI.getNotifier().setNotificationInfoProvider(new EaseNotificationInfoProvider() {

      @Override
      public String getTitle(EMMessage message) {
        //you can update title here
        return null;
      }

      @Override
      public int getSmallIcon(EMMessage message) {
        //you can update icon here
        return 0;
      }

      @Override
      public String getDisplayedText(EMMessage message) {
        // be used on notification bar, different text according the message type.
        String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
        if (message.getType() == Type.TXT) {
          ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
        }
        EaseUser user = getUserInfo(message.getFrom());
        if (user != null) {
          if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
            return String.format(appContext.getString(R.string.at_your_in_group), user.getNick());
          }
          return user.getNick() + ": " + ticker;
        } else {
          if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
            return String
                .format(appContext.getString(R.string.at_your_in_group), message.getFrom());
          }
          return message.getFrom() + ": " + ticker;
        }
      }

      @Override
      public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
        // here you can customize the text.
        // return fromUsersNum + "contacts send " + messageNum + "messages to you";
        return null;
      }

      @Override
      public Intent getLaunchIntent(EMMessage message) {
        // you can set what activity you want display when user click the notification
        Intent intent = new Intent(appContext, ChatActivity.class);
        // open calling activity if there is call
        if (isVideoCalling) {
          intent = new Intent(appContext, VideoCallActivity.class);
        } else if (isVoiceCalling) {
          intent = new Intent(appContext, VoiceCallActivity.class);
        } else {
          ChatType chatType = message.getChatType();
          if (chatType == ChatType.Chat) { // single chat message
            intent.putExtra("userId", message.getFrom());
            intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
          } else { // group chat message
            // message.getTo() is the group id
            intent.putExtra("userId", message.getTo());
            if (chatType == ChatType.GroupChat) {
              intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
            } else {
              intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
            }

          }
        }
        return intent;
      }
    });
  }

  EMConnectionListener connectionListener;

  /**
   * set global listener
   */
  protected void setGlobalListeners() {

    // create the global connection listener
    connectionListener = new EMConnectionListener() {
      @Override
      public void onDisconnected(int error) {
        LogUtils.e("global listener", "onDisconnect " + error);
        if (error == EMError.USER_REMOVED) {
          onUserException(Constant.ACCOUNT_REMOVED);
        } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
          onUserException(Constant.ACCOUNT_CONFLICT);
        } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
          onUserException(Constant.ACCOUNT_FORBIDDEN);
        } else if (error == EMError.USER_KICKED_BY_CHANGE_PASSWORD) {
          onUserException(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD);
        } else if (error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
          onUserException(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE);
        }
      }

      @Override
      public void onConnected() {
        // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
        asyncFetchGroupsFromServer(null);
      }
    };

    IntentFilter callFilter = new IntentFilter(
        EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
    if (callReceiver == null) {
      callReceiver = new CallReceiver();
    }

    //register incoming call receiver
    appContext.registerReceiver(callReceiver, callFilter);
    //register connection listener
    EMClient.getInstance().addConnectionListener(connectionListener);
    //register message event listener
    registerMessageListener();

  }

  /**
   * user met some exception: conflict, removed or forbidden
   */
  protected void onUserException(String exception) {
    LogUtils.e(TAG, "onUserException: " + exception);
    Intent intent = new Intent(appContext, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    intent.putExtra(exception, true);
    appContext.startActivity(intent);
  }

  private EaseUser getUserInfo(String username) {
    // To get instance of EaseUser, here we get it from the user list in memory
    // You'd better cache it if you get it from your server
    EaseUser user = null;
    if(username.equals(EMClient.getInstance().getCurrentUser()))
      return getUserProfileManager().getCurrentUserInfo();
    user = DemoDBManager.getInstance().getContactList().get(username);
//    if(user == null && getRobotList() != null){
//      user = getRobotList().get(username);
//    }

    // if user is not in your contacts, set inital letter for him/her
    if (user == null) {
      user = new EaseUser(username);
      EaseCommonUtils.setUserInitialLetter(user);
    }
    return user;
  }

  /**
   * Global listener
   * If this event already handled by an activity, you don't need handle it again
   * activityList.size() <= 0 means all activities already in background or not in Activity Stack
   */
  protected void registerMessageListener() {
    messageListener = new EMMessageListener() {
      private BroadcastReceiver broadCastReceiver = null;

      @Override
      public void onMessageReceived(List<EMMessage> messages) {
        for (EMMessage message : messages) {
          LogUtils.d(TAG, "onMessageReceived id : " + message.getMsgId());
          // in background, do not refresh UI, notify it in notification bar
          if (!easeUI.hasForegroundActivies()) {
            getNotifier().onNewMsg(message);
            saveContact(message);
          }
        }
      }

      @Override
      public void onCmdMessageReceived(List<EMMessage> messages) {
        for (EMMessage message : messages) {
          LogUtils.d(TAG, "receive command message");
          //get message body
          EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
          final String action = cmdMsgBody.action();//获取自定义action
          //red packet code : 处理红包回执透传消息
//          if (!easeUI.hasForegroundActivies()) {
//            if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
//              RedPacketUtil.receiveRedPacketAckMessage(message);
//              broadcastManager
//                  .sendBroadcast(new Intent(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION));
//            }
//          }

          if (action.equals("__Call_ReqP2P_ConferencePattern")) {
            String title = message.getStringAttribute("em_apns_ext", "conference call");
            Toast.makeText(appContext, title, Toast.LENGTH_LONG).show();
          }
          //end of red packet code
          //获取扩展属性 此处省略
          //maybe you need get extension of your message
          //message.getStringAttribute("");
          LogUtils.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));
        }
      }

      @Override
      public void onMessageRead(List<EMMessage> messages) {
      }

      @Override
      public void onMessageDelivered(List<EMMessage> message) {
      }

      @Override
      public void onMessageRecalled(List<EMMessage> messages) {
        for (EMMessage msg : messages) {
          if(msg.getChatType() == ChatType.GroupChat && EaseAtMessageHelper.get().isAtMeMsg(msg)){
            EaseAtMessageHelper.get().removeAtMeGroup(msg.getTo());
          }
          EMMessage msgNotification = EMMessage.createReceiveMessage(Type.TXT);
          EMTextMessageBody txtBody = new EMTextMessageBody(String.format(appContext.getString(R.string.msg_recall_by_user), msg.getFrom()));
          msgNotification.addBody(txtBody);
          msgNotification.setFrom(msg.getFrom());
          msgNotification.setTo(msg.getTo());
          msgNotification.setUnread(false);
          msgNotification.setMsgTime(msg.getMsgTime());
          msgNotification.setLocalTime(msg.getMsgTime());
          msgNotification.setChatType(msg.getChatType());
          msgNotification.setAttribute(Constant.MESSAGE_TYPE_RECALL, true);
          EMClient.getInstance().chatManager().saveMessage(msgNotification);
        }
      }

      @Override
      public void onMessageChanged(EMMessage message, Object change) {
        LogUtils.d(TAG, "change:");
        LogUtils.d(TAG, "change:" + change);
      }
    };

    EMClient.getInstance().chatManager().addMessageListener(messageListener);
  }

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

  /**
   * if ever logged in
   */
  public boolean isLoggedIn() {
    return EMClient.getInstance().isLoggedInBefore();
  }

  /**
   * logout
   *
   * @param unbindDeviceToken
   *            whether you need unbind your device token
   * @param callback
   *            callback
   */
  public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
    endCall();
    Log.d(TAG, "logout: " + unbindDeviceToken);
    EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

      @Override
      public void onSuccess() {
        Log.d(TAG, "logout: onSuccess");
        reset();
        if (callback != null) {
          callback.onSuccess();
        }

      }

      @Override
      public void onProgress(int progress, String status) {
        if (callback != null) {
          callback.onProgress(progress, status);
        }
      }

      @Override
      public void onError(int code, String error) {
        Log.d(TAG, "logout: onSuccess");
        reset();
        if (callback != null) {
          callback.onError(code, error);
        }
      }
    });
  }

  /**
   * get instance of EaseNotifier
   */
  public EaseNotifier getNotifier() {
    return easeUI.getNotifier();
  }

  public UserProfileManager getUserProfileManager() {
    if (userProManager == null) {
      userProManager = new UserProfileManager();
    }
    return userProManager;
  }

  void endCall() {
    try {
      EMClient.getInstance().callManager().endCall();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get group list from server
   * This method will save the sync state
   * @throws HyphenateException
   */
  public synchronized void asyncFetchGroupsFromServer(final EMCallBack callback){
    new Thread(){
      @Override
      public void run(){
        try {
          List<EMGroup> groups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
          if(callback != null){
            callback.onSuccess();
          }
        } catch (HyphenateException e) {
          if(callback != null){
            callback.onError(e.getErrorCode(), e.toString());
          }
        }

      }
    }.start();
  }


  synchronized void reset(){
    DemoDBManager.getInstance().deleteDB();
    getUserProfileManager().reset();
  }


  public void pushActivity(Activity activity) {
    easeUI.pushActivity(activity);
  }

  public void popActivity(Activity activity) {
    easeUI.popActivity(activity);
  }

}
