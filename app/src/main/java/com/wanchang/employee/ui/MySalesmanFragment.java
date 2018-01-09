package com.wanchang.employee.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.SalesmanMeItem;
import com.wanchang.employee.data.entity.User;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.me.OrderListActivity;
import com.wanchang.employee.ui.me.ReturnProductListActivity;
import com.wanchang.employee.ui.me.SettingActivity;
import com.wanchang.employee.ui.salesman.me.AreaRecruitListActivity;
import com.wanchang.employee.ui.salesman.me.BonusManageActivity;
import com.wanchang.employee.ui.salesman.me.ClientReturnActivity;
import com.wanchang.employee.ui.salesman.me.MyClientListActivity;
import com.wanchang.employee.ui.salesman.me.MyReportActivity;
import com.wanchang.employee.ui.salesman.me.ProductManageActivity;
import com.wanchang.employee.ui.salesman.me.SignActivity;
import com.wanchang.employee.util.GlideApp;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MySalesmanFragment extends BaseFragment {

  @BindView(R.id.civ_avatar)
  CircleImageView mAvatarCiv;
  @BindView(R.id.tv_name)
  TextView mNameTv;

  @BindView(R.id.rv_dep)
  RecyclerView mDepRv;

  @BindView(R.id.rv_me)
  RecyclerView mMeRv;


  private BaseQuickAdapter<String, BaseViewHolder> mDepAdapter;

  private BaseQuickAdapter<SalesmanMeItem, BaseViewHolder> mAdapter;


  private static final String BUNDLE_ARGS = "bundle_args";


  private int isRepayment;

  public MySalesmanFragment() {
    // Required empty public constructor
  }

  public static MySalesmanFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    MySalesmanFragment fragment = new MySalesmanFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_my_salesman;
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


    mMeRv.setLayoutManager(new GridLayoutManager(mContext, 3));
    DividerItemDecoration itemDecoration = new DividerItemDecoration();
    itemDecoration.setDividerLookup(new AgileDividerLookup());
    mMeRv.addItemDecoration(itemDecoration);
    List<SalesmanMeItem> data = initItemDate();
    mMeRv.setAdapter(mAdapter = new BaseQuickAdapter<SalesmanMeItem, BaseViewHolder>(R.layout.item_salesman_me, data) {
      @Override
      protected void convert(BaseViewHolder helper, SalesmanMeItem item) {
        helper.setText(R.id.tv_title, item.getName());
        helper.setImageResource(R.id.iv_pic, item.getResId());
      }
    });

    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
          case 0:
            startActivity(new Intent(mContext, MyClientListActivity.class));
            break;
          case 1:
            if (isRepayment != 1) {
              ToastUtils.showShort("非回款经理，不能查看回款账单");
              break;
            }
            startActivity(new Intent(mContext, ClientReturnActivity.class));
            break;
          case 2:
            startActivity(new Intent(mContext, OrderListActivity.class));
            break;
          case 3:
            startActivity(new Intent(mContext, ProductManageActivity.class));
            break;
          case 4:
            startActivity(new Intent(mContext, BonusManageActivity.class));
            break;
          case 5:
            startActivity(new Intent(mContext, MyReportActivity.class));
            break;
          case 6:
            startActivity(new Intent(mContext, SignActivity.class));
            break;
          case 7:
            startActivity(new Intent(mContext, AreaRecruitListActivity.class));
            break;
          case 8:
            startActivity(new Intent(mContext, ReturnProductListActivity.class));
            break;

        }
      }
    });
  }

  @Override
  protected void initView() {
    loadData();
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

              isRepayment = salesmanObj.getIntValue("is_repayment");
            }
          }
        });
  }

  @OnClick(R.id.iv_setting)
  public void onSetting() {
    startActivity(new Intent(mContext, SettingActivity.class));
  }


  @NonNull
  private List<SalesmanMeItem> initItemDate() {
    List<SalesmanMeItem> data = new ArrayList<>();
    SalesmanMeItem e1 = new SalesmanMeItem();
    e1.setResId(R.drawable.work_01);
    e1.setName("我的客户");
    data.add(e1);
    SalesmanMeItem e2 = new SalesmanMeItem();
    e2.setResId(R.drawable.work_02);
    e2.setName("客户回款");
    data.add(e2);
    SalesmanMeItem e3 = new SalesmanMeItem();
    e3.setResId(R.drawable.work_03);
    e3.setName("订单管理");
    data.add(e3);
    SalesmanMeItem e4 = new SalesmanMeItem();
    e4.setResId(R.drawable.work_04);
    e4.setName("商品管理");
    data.add(e4);
    SalesmanMeItem e5 = new SalesmanMeItem();
    e5.setResId(R.drawable.work_05);
    e5.setName("佣金管理");
    data.add(e5);
    SalesmanMeItem e6 = new SalesmanMeItem();
    e6.setResId(R.drawable.work_06);
    e6.setName("我的报表");
    data.add(e6);
    SalesmanMeItem e7 = new SalesmanMeItem();
    e7.setResId(R.drawable.work_07);
    e7.setName("签到");
    data.add(e7);
    SalesmanMeItem e8 = new SalesmanMeItem();
    e8.setResId(R.drawable.work_08);
    e8.setName("招商平台");
    data.add(e8);
    SalesmanMeItem e9 = new SalesmanMeItem();
    e9.setResId(R.drawable.work_09);
    e9.setName("退货售后");
    data.add(e9);
    return data;
  }

  class AgileDividerLookup extends DividerItemDecoration.SimpleDividerLookup {

    @Override
    public Divider getVerticalDivider(int position) {
      return new Divider.Builder()
          .size(2)
          .color(getResources().getColor(R.color.color_e5))
          .build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
      return new Divider.Builder()
          .size(2)
          .color(getResources().getColor(R.color.color_e5))
          .build();
    }
  }

}
