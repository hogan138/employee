package com.wanchang.employee.ui.salesman.news;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.Client;
import com.wanchang.employee.data.entity.ContactTemp;
import com.wanchang.employee.easemob.Constant;
import com.wanchang.employee.easemob.db.DemoDBManager;
import com.wanchang.employee.easemob.ui.ChatActivity;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClientDetailActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.civ_pic)
  CircleImageView mPicCiv;
  @BindView(R.id.tv_client_name)
  TextView mNameTv;
  @BindView(R.id.tv_client_mobile)
  TextView mMobileTv;
  @BindView(R.id.tv_zone)
  TextView mZoneTv;


  private Client mClient;

  private String mobile;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_client_detail;
  }

  @Override
  protected void initData() {
    mClient = getIntent().getParcelableExtra("client");
  }

  @Override
  protected void initView() {
    mTitleTv.setText("客户详情");

    GlideApp.with(mContext).load(MallAPI.IMG_SERVER + mClient.getPic()).placeholder(R.drawable.avatar136x136).into(mPicCiv);
    mNameTv.setText(mClient.getUser().getName());
    mMobileTv.setText(mClient.getUser().getMobile());
    mZoneTv.setText(mClient.getProvince()+mClient.getCity()+mClient.getCounty());

    mobile = mClient.getUser().getMobile();
  }


  @OnClick(R.id.tv_send_msg)
  public void onSendMsg() {
    LogUtils.e(mClient.toString());
    ContactTemp ct = new ContactTemp();
    ct.setUsername(mClient.getUser().getIm_account());
    ct.setNickname(mClient.getUser().getName());
    ct.setAvatar(mClient.getUser().getPic());
    ct.setFriend(false);
    DemoDBManager.getInstance().saveContact(ct);
    startActivity(new Intent(mContext, ChatActivity.class)
        .putExtra(Constant.EXTRA_USER_ID, mClient.getUser().getIm_account()));
  }


  @OnClick(R.id.rl_call)
  public void onCall() {
    if (TextUtils.isEmpty(mobile)) {
      ToastUtils.showShort("号码不能为空");
      return;
    }
    PhoneUtils.dial(mobile);
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }


}
