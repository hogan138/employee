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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PromotionMsgListActivity extends BaseActivity{

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
    ACache.get(mContext).remove(Constants.KEY_NEW_MSG_2);

    mSystemRv.setLayoutManager(new LinearLayoutManager(mContext));
//    mSystemRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
//        getResources().getColor(R.color.color_e5)).build());
    mSystemRv.setAdapter(mAdapter = new BaseQuickAdapter<Message, BaseViewHolder>(R.layout.item_promotion_msg_list) {
      @Override
      protected void convert(BaseViewHolder helper, Message item) {
        helper.setText(R.id.tv_date, getTime(item.getSent_at()));
        String extra = item.getExtra();
        if ("10".equals(extra)) {
          helper.setText(R.id.tv_category, Constants.PROMOTION_CATEGORY_10);
        } else if("20".equals(extra)) {
          helper.setText(R.id.tv_category, Constants.PROMOTION_CATEGORY_20);
        } else if("30".equals(extra)) {
          helper.setText(R.id.tv_category, Constants.PROMOTION_CATEGORY_30);
        } else if("40".equals(extra)) {
          helper.setText(R.id.tv_category, Constants.PROMOTION_CATEGORY_40);
        } else if("50".equals(extra)) {
          helper.setText(R.id.tv_category, Constants.PROMOTION_CATEGORY_50);
        } else if("60".equals(extra)) {
          helper.setText(R.id.tv_category, Constants.PROMOTION_CATEGORY_60);
        } else if("70".equals(extra)) {
          helper.setText(R.id.tv_category, Constants.PROMOTION_CATEGORY_70);
        }
        helper.setText(R.id.tv_title, item.getTitle());
        String pics = item.getPic();
        if ("".equals(pics)) {
          helper.setGone(R.id.rv_product,  false);
        } else {
          helper.setGone(R.id.rv_product,  true);
          RecyclerView rv = helper.getView(R.id.rv_product);
          rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
          String[] picArray = pics.split(",");
          List<String> data = Arrays.asList(picArray);
          rv.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_confirm_order, data) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
              ImageView picIv = helper.getView(R.id.iv_pic);
              GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item).placeholder(R.drawable.ic_default_image).into(picIv);
            }
          });
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
    mTitle.setText("促销资讯");

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
        .params("category", 20)
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
