package com.wanchang.employee.ui.salesman.news;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseAlertDialog.AlertDialogUser;
import com.kyleduo.switchbutton.SwitchButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.User;
import com.wanchang.employee.easemob.Constant;
import com.wanchang.employee.easemob.db.DemoDBManager;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;

public class SingleChatSettingActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.civ_pic)
  CircleImageView mPicCiv;
  @BindView(R.id.tv_name)
  TextView mNameTv;

  private String imAccount;

  @BindView(R.id.sb_block_msg)
  SwitchButton mBlockMsgSb;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_single_chat_setting;
  }

  @Override
  protected void initData() {
    imAccount = getIntent().getStringExtra("im_account");
    LogUtils.e("======"+imAccount);


    mBlockMsgSb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          DemoDBManager.getInstance().setBlockMsg(imAccount, true);
        } else {
          DemoDBManager.getInstance().setBlockMsg(imAccount, false);
        }
      }
    });

    if (DemoDBManager.getInstance().isBlockMsg(imAccount)) {
      mBlockMsgSb.setCheckedNoEvent(true);
    } else {
      mBlockMsgSb.setCheckedNoEvent(false);
    }
  }

  @Override
  protected void initView() {
    mTitleTv.setText("聊天设置");

    getDetail();
  }


  public void getDetail() {
    OkGo.<String>get(MallAPI.GROUP_USER_INFO)
        .tag(this)
        .params("im_account", imAccount)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              User user = JSON.parseObject(jsonObj.getString("user"), User.class);
              GlideApp.with(mContext).load(MallAPI.IMG_SERVER + user.getPic()).placeholder(R.drawable.avatar88x88).into(mPicCiv);
              mNameTv.setText(user.getName());
            }
          }
        });
  }

  @OnClick(R.id.stv_clear_history)
  public void onClearHistory() {
    String msg = getResources().getString(com.hyphenate.easeui.R.string.Whether_to_empty_all_chats);
    new EaseAlertDialog(mContext,null, msg, null,new AlertDialogUser() {

      @Override
      public void onResult(boolean confirmed, Bundle bundle) {
        if(confirmed){
          EMClient
              .getInstance().chatManager().getConversation(imAccount, EaseCommonUtils.getConversationType(
                  Constant.CHATTYPE_SINGLE), true).clearAllMessages();

        }
      }
    }, true).show();
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }

}
