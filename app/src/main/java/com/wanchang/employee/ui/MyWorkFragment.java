package com.wanchang.employee.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mylhyl.circledialog.CircleDialog;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.DepRole;
import com.wanchang.employee.data.entity.User;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.me.BindMobileActivity;
import com.wanchang.employee.ui.me.ModifyPwdActivity;
import com.wanchang.employee.ui.me.SettingActivity;
import com.wanchang.employee.util.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWorkFragment extends BaseFragment {

  @BindView(R.id.civ_avatar)
  CircleImageView mAvatarCiv;
  @BindView(R.id.tv_name)
  TextView mNameTv;

  @BindView(R.id.stv_clear_cache)
  SuperTextView mClearCacheStv;

  @BindView(R.id.rv_dep)
  RecyclerView mDepRv;

  private BaseQuickAdapter<DepRole, BaseViewHolder> mDepAdapter;

  private static final String BUNDLE_ARGS = "bundle_args";

  public MyWorkFragment() {
    // Required empty public constructor
  }

  public static MyWorkFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    MyWorkFragment fragment = new MyWorkFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_work_my;
  }

  @Override
  protected void initData() {
    mDepRv.setLayoutManager(new GridLayoutManager(mContext, 4));
    mDepRv.setAdapter(mDepAdapter = new BaseQuickAdapter<DepRole, BaseViewHolder>(R.layout.item_me_dep) {
      @Override
      protected void convert(BaseViewHolder helper, DepRole item) {
        helper.setText(R.id.tv_title, item.getDepartment().getName());
      }
    });
  }

  @Override
  protected void initView() {

  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) loadData();
  }


  private void loadData() {
    getDepList();
    OkGo.<String>get(MallAPI.USER+"/"+ MallApp.getInstance().getUserId())
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              User user = JSON.parseObject(response.body(), User.class);
              GlideApp.with(mContext).load(MallAPI.IMG_SERVER+user.getPic()).placeholder(R.drawable.ic_default_image).into(mAvatarCiv);
              mNameTv.setText(user.getName());
            }
          }
        });
  }

  private void getDepList() {
    OkGo.<String>get(MallAPI.USER_DEPARTMENT_LIST)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<DepRole> depRoleList = JSON.parseArray(response.body(), DepRole.class);
              mDepAdapter.setNewData(depRoleList);
            }
          }
        });
  }

  @OnClick(R.id.stv_modify_password)
  public void onModifyPwd() {
    startActivity(new Intent(mContext, ModifyPwdActivity.class));
  }

  @OnClick(R.id.stv_bind_mobile)
  public void onBindMobile() {
    startActivity(new Intent(mContext, BindMobileActivity.class));
  }

  @OnClick(R.id.stv_clear_cache)
  public void onClearCache() {
    new CircleDialog.Builder(mContext)
        .setTitle("提示")
        .setText("您确定要清除吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            mClearCacheStv.setRightString("0M");
          }
        }).show();
  }

  @OnClick(R.id.tv_logout)
  public void onlogout() {
    new CircleDialog.Builder(mContext)
        .setTitle("提示")
        .setText("您确定要退出吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.USER_LOGOUT)
                .tag(this)
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    startActivity(new Intent(mContext, MainActivity.class).putExtra("app_exit", true));
                    mContext.sendBroadcast(new Intent(Constants.APP_EXIT_ACTION));
                  }
                });
          }
        }).show();
  }


  @OnClick(R.id.iv_setting)
  public void goSetting() {
    startActivity(new Intent(mContext, SettingActivity.class));
  }

}
