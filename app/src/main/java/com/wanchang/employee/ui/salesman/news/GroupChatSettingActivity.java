package com.wanchang.employee.ui.salesman.news;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
import java.util.List;

public class GroupChatSettingActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  private String imGroupId;

  @BindView(R.id.rv_group_member)
  RecyclerView mGroupMemberRv;
  private BaseQuickAdapter<User, BaseViewHolder> mAdapter;


  @BindView(R.id.sb_block_msg)
  SwitchButton mBlockMsgSb;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_group_chat_setting;
  }

  @Override
  protected void initData() {
    imGroupId = getIntent().getStringExtra("im_group_id");
    LogUtils.e("======"+imGroupId);

    mGroupMemberRv.setAdapter(mAdapter = new BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_group_member_list) {

      @Override
      protected void convert(BaseViewHolder helper, User item) {
        ImageView picIv = helper.getView(R.id.civ_pic);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getPic()).placeholder(R.drawable.avatar88x88).into(picIv);
        helper.setText(R.id.tv_name, item.getName());
      }
    });
    mGroupMemberRv.setLayoutManager(new GridLayoutManager(mContext, 5));


    mBlockMsgSb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
          DemoDBManager.getInstance().setBlockMsg(imGroupId, true);
        } else {
          DemoDBManager.getInstance().setBlockMsg(imGroupId, false);
        }
      }
    });

    if (DemoDBManager.getInstance().isBlockMsg(imGroupId)) {
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
    OkGo.<String>get(MallAPI.GROUP_GROUP_USER)
        .tag(this)
        .params("im_group_id", imGroupId)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<User> userList = JSON.parseArray(jsonObj.getString("user"), User.class);
              mAdapter.setNewData(userList);
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
              .getInstance().chatManager().getConversation(imGroupId, EaseCommonUtils.getConversationType(
              Constant.CHATTYPE_GROUP), true).clearAllMessages();

        }
      }
    }, true).show();
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }


}
