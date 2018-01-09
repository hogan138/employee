package com.wanchang.employee.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.User;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.me.SettingActivity;
import com.wanchang.employee.util.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWorkFragment extends BaseFragment {

  @BindView(R.id.civ_avatar)
  CircleImageView mAvatarCiv;
  @BindView(R.id.tv_name)
  TextView mNameTv;

  @BindView(R.id.rv_dep)
  RecyclerView mDepRv;

  private BaseQuickAdapter<String, BaseViewHolder> mDepAdapter;

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
    mDepRv.setLayoutManager(new GridLayoutManager(mContext, 3));
    mDepRv.setAdapter(mDepAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_me_dep) {
      @Override
      protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title, item);
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
    OkGo.<String>get(MallAPI.USER+"/profile")
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              User user = JSON.parseObject(jsonObj.getString("user_info"), User.class);
              GlideApp.with(mContext).load(MallAPI.IMG_SERVER+user.getPic()).placeholder(R.drawable.ic_default_image).into(mAvatarCiv);
              mNameTv.setText(user.getName());

              JSONObject salesmanObj = JSON.parseObject(jsonObj.getString("salesman_info"));

              List<String> data = new ArrayList<>();
              JSONArray jsonDepArray = salesmanObj.getJSONArray("dep_list");
              for (int i = 0; i < jsonDepArray.size(); i++) {
                JSONObject depObj = jsonDepArray.getJSONObject(i);
                data.add(depObj.getString("name"));
              }
              mDepAdapter.setNewData(data);

            }
          }
        });
  }


  @OnClick(R.id.iv_setting)
  public void goSetting() {
    startActivity(new Intent(mContext, SettingActivity.class));
  }

}
