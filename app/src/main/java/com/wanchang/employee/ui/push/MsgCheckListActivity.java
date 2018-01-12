package com.wanchang.employee.ui.push;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.flyco.tablayout.SlidingTabLayout;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.ui.work.SelectRoleActivity;
import com.wanchang.employee.util.ACache;
import java.util.ArrayList;

public class MsgCheckListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.tl)
  SlidingTabLayout tabLayout;
  @BindView(R.id.vp)
  ViewPager vp;


  private final String[] mTitles = {"全部", "待审核"};


  private MsgCheckCard0Fragment msgChard0Fragment;
  private MsgCheckCard1Fragment msgChard1Fragment;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_msg_check_list;
  }

  @Override
  protected void initData() {
    ACache.get(mContext).remove(Constants.KEY_NEW_MSG_2);

  }

  @Override
  protected void initView() {
    //mTitle.setText("消息审核");

    String depName = ACache.get(mContext).getAsString(Constants.KEY_DEPARTMENT_NAME);
    String roleName = ACache.get(mContext).getAsString(Constants.KEY_ROLE_NAME);

    mTitleTv.setText(depName+"-"+roleName);

    ArrayList<Fragment> mFragments = new ArrayList<>();
    mFragments.add(msgChard0Fragment = MsgCheckCard0Fragment.newInstance(""));
    mFragments.add(msgChard1Fragment = MsgCheckCard1Fragment.newInstance(""));

    tabLayout.setViewPager(vp, mTitles, mContext, mFragments);

  }

  @OnClick(R.id.tv_topbar_title)
  public void selectRole() {
    startActivityForResult(new Intent(mContext, SelectRoleActivity.class), 200);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 200 && resultCode == mContext.RESULT_OK) {

      msgChard0Fragment.loadData();
      msgChard1Fragment.loadData();

      String depName = ACache.get(mContext).getAsString(Constants.KEY_DEPARTMENT_NAME);
      String roleName = ACache.get(mContext).getAsString(Constants.KEY_ROLE_NAME);

      mTitleTv.setText(depName+"-"+roleName);
    }
  }



  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
