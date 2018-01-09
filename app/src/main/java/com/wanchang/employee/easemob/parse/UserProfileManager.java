package com.wanchang.employee.easemob.parse;

import android.content.Context;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.LoginInfo;
import com.wanchang.employee.util.ACache;

public class UserProfileManager {

  /**
   * application context
   */
  protected Context appContext = null;

  /**
   * init flag: test if the sdk has been inited before, we don't need to init
   * again
   */
  private boolean sdkInited = false;

  private EaseUser currentUser;

  public UserProfileManager() {
  }

  public synchronized boolean init(Context context) {
    if (sdkInited) {
      return true;
    }
    appContext = context;
    sdkInited = true;
    return true;
  }

  public synchronized void reset() {
    currentUser = null;
  }

  public synchronized EaseUser getCurrentUserInfo() {
    if (currentUser == null) {
      String username = EMClient.getInstance().getCurrentUser();
      currentUser = new EaseUser(username);
      currentUser.setAvatar(getCurrentUserAvatar());
      String nick = getCurrentUserNick();
      currentUser.setNickname((nick != null) ? nick : username);
    }else {
      String avatarStr = getCurrentUserAvatar();
      String nickStr = getCurrentUserNick();
      if (!currentUser.getAvatar().equals(avatarStr) || !currentUser.getNickname().equals(nickStr)) {
        String username = EMClient.getInstance().getCurrentUser();
        currentUser = new EaseUser(username);
        currentUser.setAvatar(avatarStr);
        String nick = nickStr;
        currentUser.setNickname((nick != null) ? nick : username);
      }
    }
    return currentUser;
  }

  private String getCurrentUserNick() {
    LoginInfo loginInfo = (LoginInfo) ACache.get(appContext).getAsObject(Constants.KEY_LOGIN_INFO);
    String userNick = loginInfo.getUser().getName();
    return userNick;
  }

  private String getCurrentUserAvatar() {
    LoginInfo loginInfo = (LoginInfo) ACache.get(appContext).getAsObject(Constants.KEY_LOGIN_INFO);
    String userAvatar = MallAPI.IMG_SERVER + loginInfo.getUser().getPic();
    //String userAvatar = MallAPI.IMG_SERVER + loginInfo.getUser().getPic() + "@72h_72w_1e_1c";
    return userAvatar;
  }

}
