package com.wanchang.employee.ui.push;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
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
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Message;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.ACache;
import com.wanchang.employee.util.GlideApp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderMsgListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitle;

  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_system_msg)
  RecyclerView mSystemRv;


  public BaseQuickAdapter<Message, BaseViewHolder> mAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_system_msg_list;
  }

  @Override
  protected void initData() {
    ACache.get(mContext).remove(Constants.KEY_NEW_MSG_3);

    mSystemRv.setLayoutManager(new LinearLayoutManager(mContext));
//    mSystemRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
//        getResources().getColor(R.color.color_e5)).build());
    mSystemRv.setAdapter(mAdapter = new BaseQuickAdapter<Message, BaseViewHolder>(R.layout.item_order_msg_list) {
      @Override
      protected void convert(BaseViewHolder helper, Message item) {
        helper.setText(R.id.tv_date, getTime(item.getSent_at()));
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getContent());
        JSONObject jsonObj = JSON.parseObject(item.getExtra());
        helper.setText(R.id.tv_extra, jsonObj.getString("text"));
        ImageView picIv = helper.getView(R.id.iv_pic);
        if ("".equals(item.getPic())) {
          helper.setGone(R.id.iv_pic,  false);
        } else {
          helper.setGone(R.id.iv_pic,  true);
          GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getPic()).placeholder(R.drawable.ic_default_image).into(picIv);
        }
      }
    });

    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String cmd = mAdapter.getItem(position).getCmd();
        MallApp.getInstance().cmdNavigation(mContext, cmd);
      }
    });
  }

  @Override
  protected void initView() {
    mTitle.setText("订单助手");

    loadData();

    refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_MORE;
        currentPage++;
        loadData();
      }

      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_REFRESH;
        currentPage = 1;
        loadData();
      }
    });
  }

  private void loadData() {
    OkGo.<String>get(MallAPI.MESSAGE)
        .tag(this)
        .params("category", 30)
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<Message> systemList = JSON.parseArray(jsonObj.getString("items"), Message.class);
              JSONObject metaObj = JSON.parseObject(jsonObj.getString("_meta"));
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mAdapter.setNewData(systemList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+systemList.size());
                if (currentPage > metaObj.getIntValue("pageCount")) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mAdapter.addData(systemList);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }
        });
  }

  private String getTime(long time) {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time*1000));
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
