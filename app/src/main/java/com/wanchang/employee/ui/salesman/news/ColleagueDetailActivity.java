package com.wanchang.employee.ui.salesman.news;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.Colleague;
import com.wanchang.employee.data.entity.ContactTemp;
import com.wanchang.employee.easemob.Constant;
import com.wanchang.employee.easemob.db.DemoDBManager;
import com.wanchang.employee.easemob.ui.ChatActivity;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;

public class ColleagueDetailActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.civ_pic)
  CircleImageView mPicCiv;
  @BindView(R.id.tv_colleague_name)
  TextView mNameTv;
  @BindView(R.id.tv_colleague_mobile)
  TextView mMobileTv;


  private Colleague mColleague;

  private String mobile;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_colleague_detail;
  }

  @Override
  protected void initData() {
    mColleague = getIntent().getParcelableExtra("colleague");
  }

  @Override
  protected void initView() {
    mTitleTv.setText("同事详情");

    GlideApp.with(mContext).load(MallAPI.IMG_SERVER + mColleague.getPic()).placeholder(R.drawable.avatar104x104).into(mPicCiv);
    mNameTv.setText(mColleague.getName());
    mMobileTv.setText(mColleague.getMobile());

    mobile = mColleague.getMobile();
  }


  @OnClick(R.id.tv_send_msg)
  public void onSendMsg() {
    ContactTemp ct = new ContactTemp();
    ct.setUsername(mColleague.getIm_account());
    ct.setNickname(mColleague.getName());
    ct.setAvatar(mColleague.getPic());
    ct.setFriend(false);
    DemoDBManager.getInstance().saveContact(ct);
    startActivity(new Intent(mContext, ChatActivity.class)
        .putExtra(Constant.EXTRA_USER_ID, mColleague.getIm_account()));
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
