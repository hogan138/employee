package com.wanchang.employee.easemob.db;

import android.text.TextUtils;
import com.blankj.utilcode.util.LogUtils;
import com.facebook.stetho.common.LogUtil;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.ContactTemp;
import com.wanchang.employee.easemob.Constant;
import com.wanchang.employee.easemob.db.model.BlockMsg;
import com.wanchang.employee.easemob.db.model.Contact;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DemoDBManager {
    static private DemoDBManager dbMgr = new DemoDBManager();

    private DemoDBManager(){

    }
    
    public static synchronized DemoDBManager getInstance(){
        if(dbMgr == null){
            dbMgr = new DemoDBManager();
        }
        return dbMgr;
    }


    /**
     * get contact list
     *
     * @return
     */
    synchronized public Map<String, EaseUser> getContactList() {
        Map<String, EaseUser> users = new Hashtable<String, EaseUser>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> contacts = realm.where(Contact.class).findAll();
        for (Contact contact : contacts) {
            String username = contact.getUsername();
            String nickname = contact.getNickname();
            String avatar = contact.getAvatar();
            EaseUser user = new EaseUser(username);
            user.setNickname(nickname);
            user.setAvatar(avatar);
            user.setInitialLetter("");
            users.put(username, user);
        }
        realm.close();
        return users;
    }

    /**
     * get friend list
     *
     * @return
     */
    synchronized public Map<String, EaseUser> getFriendList() {
        Map<String, EaseUser> users = new Hashtable<String, EaseUser>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> contacts = realm.where(Contact.class).equalTo("isFriend", true).findAll();
        for (Contact contact : contacts) {
            String username = contact.getUsername();
            String nickname = contact.getNickname();
            String avatar = contact.getAvatar();
            EaseUser user = new EaseUser(username);
            user.setNickname(nickname);
            user.setAvatar(avatar);
            if (username.equals(Constant.NEW_FRIENDS_USERNAME) || username.equals(Constant.GROUP_USERNAME)
                || username.equals(Constant.CHAT_ROOM)|| username.equals(Constant.CHAT_ROBOT)) {
                user.setInitialLetter("");
            } else {
                EaseCommonUtils.setUserInitialLetter(user);
            }
            users.put(username, user);
        }
        realm.close();
        return users;
    }

    /**
     * save contact list
     *
     * @param contactTempList
     */
    synchronized public void saveContactList(List<ContactTemp> contactTempList) {
        Realm realm = Realm.getDefaultInstance();

        for (ContactTemp contactTemp : contactTempList) {
            String username = contactTemp.getUsername();
            if (TextUtils.isEmpty(username))
                break;
            String nickname = contactTemp.getNickname();
            String avatar = contactTemp.getAvatar();
            boolean isFriend = contactTemp.isFriend();

            String nicknameStr = "";
            if (TextUtils.isEmpty(nickname)) {
                nicknameStr = username;
            } else {
                nicknameStr = nickname;
            }

            String avatarStr = "";
            if (avatar.startsWith("https")) {
                avatarStr = avatar;
                //avatarStr = avatar + "@72h_72w_1e_1c";
            } else {
                avatarStr = MallAPI.IMG_SERVER + avatar;
                //avatarStr = MallAPI.IMG_SERVER + avatar + "@72h_72w_1e_1c";
            }

            Contact contactObj = new Contact();
            contactObj.setUsername(username);
            contactObj.setNickname(nicknameStr);
            contactObj.setAvatar(avatarStr);
            contactObj.setFriend(isFriend);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(contactObj);
            realm.commitTransaction();
            LogUtils.e("save or update contact success ::::::::", contactObj + "");
        }
        realm.close();
    }

    /**
     * save a contact
     * @param contactTemp
     */
    synchronized public void saveContact(ContactTemp contactTemp){
        String username = contactTemp.getUsername();
        if (TextUtils.isEmpty(username))
            return;
        String nickname = contactTemp.getNickname();
        String avatar = contactTemp.getAvatar();
        boolean isFriend = contactTemp.isFriend();

        String nicknameStr = "";
        if (TextUtils.isEmpty(nickname)) {
            nicknameStr = username;
        } else {
            nicknameStr = nickname;
        }

        String avatarStr = "";
        if (avatar.startsWith("https")) {
            avatarStr = avatar;
            //avatarStr = avatar + "@72h_72w_1e_1c";
        } else {
            avatarStr = MallAPI.IMG_SERVER + avatar;
            //avatarStr = MallAPI.IMG_SERVER + avatar + "@72h_72w_1e_1c";
        }

        Realm realm = Realm.getDefaultInstance();
        Contact contactObj = new Contact();
        contactObj.setUsername(username);
        contactObj.setNickname(nicknameStr);
        contactObj.setAvatar(avatarStr);
        contactObj.setFriend(isFriend);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(contactObj);
        realm.commitTransaction();
        LogUtils.e("save or update contact success ::::::::", contactObj + "");

        realm.close();
    }

    /**
     * delete db
     *
     */
    synchronized public void deleteDB(){
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.delete(Contact.class);
        realm.delete(BlockMsg.class);
        realm.commitTransaction();

        LogUtils.e("delete contact blockMsg success ::::::::", "success");

        realm.close();
    }


    /**
     * get block msg list
     *
     * @return
     */
    synchronized public List<String> getBlockIds(){
        Realm realm = Realm.getDefaultInstance();

        List<String> disabledIds = new ArrayList<>();
        RealmResults<BlockMsg> blockMsgs = realm.where(BlockMsg.class).equalTo("isMsgBlock", true).findAll();
        for (BlockMsg blockMsg : blockMsgs) {
            disabledIds.add(blockMsg.getBlockId());
        }

        realm.close();
        return disabledIds;
    }


    synchronized public void setBlockMsg(String blockId, boolean isMsgBlock){
        Realm realm = Realm.getDefaultInstance();

        BlockMsg blockMsgObj = new BlockMsg();
        blockMsgObj.setBlockId(blockId);
        blockMsgObj.setMsgBlock(isMsgBlock);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(blockMsgObj);
        realm.commitTransaction();
        LogUtils.e("save or update blockMsg success ::::::::", blockMsgObj + "");

        realm.close();
    }

    synchronized public boolean isBlockMsg(String blockId) {
        Realm realm = Realm.getDefaultInstance();

        BlockMsg blockMsg = realm.where(BlockMsg.class).equalTo("blockId", blockId).findFirst();
        LogUtil.e("query blockMsg success ::::::::", blockMsg + "");
        if (blockMsg != null && blockMsg.isMsgBlock()) {
            realm.close();
            return true;
        }
        realm.close();
        return false;
    }

}
