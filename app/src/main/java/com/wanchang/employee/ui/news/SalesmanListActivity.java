package com.wanchang.employee.ui.news;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.ContactTemp;
import com.wanchang.employee.data.entity.User;
import com.wanchang.employee.easemob.Constant;
import com.wanchang.employee.easemob.db.DemoDBManager;
import com.wanchang.employee.easemob.ui.ChatActivity;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;

public class SalesmanListActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_salesman)
  RecyclerView mSalesmanRv;

  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  private BaseQuickAdapter<User, BaseViewHolder> mAdapter;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_salesman_list;
  }

  @Override
  protected void initData() {


  }

  @Override
  protected void initView() {
    mTitleTv.setText("业务员列表");

    mSalesmanRv.setLayoutManager(new LinearLayoutManager(mContext));
    mSalesmanRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
        .color(getResources().getColor(R.color.color_e6)).build());
    mSalesmanRv.setAdapter(mAdapter = new BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_salesman_list) {

      @Override
      protected void convert(BaseViewHolder helper, User item) {
        ImageView picIv = helper.getView(R.id.pic_civ);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER + item.getPic()).placeholder(R.drawable.avatar88x88).into(picIv);
        helper.setText(R.id.tv_name, item.getName());
      }
    });

    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        User user = mAdapter.getItem(position);
        onSendMsg(user);
      }
    });

    refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_MORE;
        currentPage++;
        getSalesmanList();
      }

      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_REFRESH;
        currentPage = 1;
        getSalesmanList();
      }
    });

    getSalesmanList();
  }

  public void getSalesmanList() {
    OkGo.<String>get(MallAPI.USER_SALESMAN_LIST)
        .tag(this)
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<User> products = JSON.parseArray(response.body(), User.class);
              mAdapter.setNewData(products);
              refreshLayout.finishRefresh();
            }
          }
        });
  }

  public void onSendMsg(User user) {
    ContactTemp ct = new ContactTemp();
    ct.setUsername(user.getIm_account());
    ct.setNickname(user.getName());
    ct.setAvatar(user.getPic());
    ct.setFriend(false);
    DemoDBManager.getInstance().saveContact(ct);
    startActivity(new Intent(mContext, ChatActivity.class)
        .putExtra(Constant.EXTRA_USER_ID, user.getIm_account()));
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }


}
