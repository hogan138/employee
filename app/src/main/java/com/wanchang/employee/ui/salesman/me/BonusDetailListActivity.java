package com.wanchang.employee.ui.salesman.me;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.wanchang.employee.data.entity.Bonus;
import com.wanchang.employee.ui.base.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BonusDetailListActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_product)
  RecyclerView mProductRv;


  private BaseQuickAdapter<Bonus, BaseViewHolder> mProductAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_bonus_detail_list;
  }

  @Override
  protected void initData() {
    mProductRv.setLayoutManager(new LinearLayoutManager(mContext));
    mProductRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<Bonus, BaseViewHolder>(R.layout.item_bonus_detail_list) {
      @Override
      protected void convert(final BaseViewHolder helper, Bonus item) {
        if (item.getCategory() == 10) {
          helper.setText(R.id.tv_bonus_category, "下单");
          helper.setText(R.id.tv_bonus_price, "+"+item.getBonus());
          helper.setGone(R.id.ll_order_product, true);
          helper.setText(R.id.tv_product_name, item.getData().getTitle());
          helper.setText(R.id.tv_product_count, "x "+item.getData().getCount());
          helper.setText(R.id.tv_explain, "订单号："+item.getOrder_id());
        } else if (item.getCategory() == 20) {
          helper.setText(R.id.tv_bonus_category, "退货");
          helper.setText(R.id.tv_bonus_price, "-"+item.getBonus());
          helper.setGone(R.id.ll_order_product, true);
          helper.setText(R.id.tv_product_name, item.getData().getTitle());
          helper.setText(R.id.tv_product_count, "x "+item.getData().getCount());
          helper.setText(R.id.tv_explain, "订单号："+item.getOrder_id());
        } else if (item.getCategory() == 30) {
          helper.setText(R.id.tv_bonus_category, "奖励");
          helper.setText(R.id.tv_bonus_price, "+"+item.getBonus());
          helper.setGone(R.id.ll_order_product, false);
          helper.setText(R.id.tv_explain, item.getExplain());
        } else if (item.getCategory() == 40) {
          helper.setText(R.id.tv_bonus_category, "惩罚");
          helper.setText(R.id.tv_bonus_price, "-"+item.getBonus());
          helper.setGone(R.id.ll_order_product, false);
          helper.setText(R.id.tv_explain, item.getExplain());
        }
        helper.setText(R.id.tv_time, new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date(item.getCreated_at()*1000)));
      }
    });
  }



  @Override
  protected void initView() {
    mTitleTv.setText("佣金明细");

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
    OkGo.<String>get(MallAPI.BONUS)
        .tag(this)
        .params("expand", "data")
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<Bonus> productList = JSON.parseArray(jsonObj.getString("items"), Bonus.class);
              JSONObject metaObj = JSON.parseObject(jsonObj.getString("_meta"));
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mProductAdapter.setNewData(productList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+productList.size());
                if (currentPage > metaObj.getIntValue("pageCount")) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mProductAdapter.addData(productList);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }
        });
  }



  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
