package com.wanchang.employee.ui.salesman.news;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.adapter.ExpandableAddressBookAdapter;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.AddressBook0Item;
import com.wanchang.employee.data.entity.AddressBook1Item;
import com.wanchang.employee.data.entity.Client;
import com.wanchang.employee.data.entity.Colleague;
import com.wanchang.employee.data.entity.Group;
import com.wanchang.employee.ui.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class AddressBookActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.stv_client_list)
  SuperTextView mClientCountStv;

  @BindView(R.id.rv_organization)
  RecyclerView mOrganizationRv;

  private ExpandableAddressBookAdapter mAdapter;

  private List<Group> groupList;
  private List<Client> clientList;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_address_book;
  }

  @Override
  protected void initData() {
    mAdapter = new ExpandableAddressBookAdapter(null);
    mOrganizationRv.setAdapter(mAdapter);
//    mOrganizationRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
//        getResources().getColor(R.color.color_e5)).build());
    mOrganizationRv.setLayoutManager(new LinearLayoutManager(mContext));
  }

  @Override
  protected void initView() {
    mTitleTv.setText("通讯录");

    getAddressBook();
  }

  public void getAddressBook() {
    OkGo.<String>get(MallAPI.USER_ADDRESS_BOOK)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());

              groupList = JSON.parseArray(jsonObj.getString("group_list"), Group.class);
              clientList = JSON.parseArray(jsonObj.getString("client_list"), Client.class);

              mClientCountStv.setLeftString("客户(" +jsonObj.getJSONArray("client_list").size() + ")");

              ArrayList<MultiItemEntity> res = new ArrayList<MultiItemEntity>();
              JSONArray items0Array = jsonObj.getJSONArray("organization");
              for (int i = 0; i < items0Array.size(); i++) {
                String title0 = items0Array.getJSONObject(i).getString("name");
                JSONArray items1Array = items0Array.getJSONObject(i).getJSONArray("user_list");
                AddressBook0Item lv0 = new AddressBook0Item(title0, items1Array.size());
                for (int j = 0; j < items1Array.size(); j++) {
                  lv0.addSubItem(new AddressBook1Item(JSON.parseObject(items1Array.getString(j), Colleague.class)));
                }
                res.add(lv0);
              }
              mAdapter.setNewData(res);
            }
          }
        });
  }


  @OnClick(R.id.stv_group_list)
  public void onGoGroupList() {
    startActivity(new Intent(mContext, GroupListActivity.class)
        .putParcelableArrayListExtra("groupList", (ArrayList<? extends Parcelable>) groupList));
  }

  @OnClick(R.id.stv_client_list)
  public void onGoClientList() {
    startActivity(new Intent(mContext, ClientListActivity.class)
        .putParcelableArrayListExtra("clientList", (ArrayList<? extends Parcelable>) clientList));
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }


}
