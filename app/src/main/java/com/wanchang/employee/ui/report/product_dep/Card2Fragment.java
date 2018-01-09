package com.wanchang.employee.ui.report.product_dep;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wanchang.employee.R;
import com.wanchang.employee.adapter.ExpandableProductDep2Adapter;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.ProductDep2;
import com.wanchang.employee.data.entity.ProductDep20Item;
import com.wanchang.employee.data.entity.ProductDep21Item;
import com.wanchang.employee.ui.base.BaseFragment;
import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration.VisibilityProvider;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zyyoona7.lib.EasyPopup;
import com.zyyoona7.lib.HorizontalGravity;
import com.zyyoona7.lib.VerticalGravity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Card2Fragment extends BaseFragment {

  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv)
  RecyclerView mRv;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  private ExpandableProductDep2Adapter mAdapter;


  private static final String BUNDLE_ARGS = "bundle_args";


  private long time_start = getTimesmorning();
  private long time_end = getTimesnight();


  //获得当天0点时间
  public long getTimesmorning(){
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    return (cal.getTimeInMillis()/1000);
  }
  //获得当天24点时间
  public long getTimesnight(){
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    return (cal.getTimeInMillis()/1000);
  }


  private HeaderViewHolder headerViewHolder;


  public Card2Fragment() {
    // Required empty public constructor
  }

  public static Card2Fragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    Card2Fragment fragment = new Card2Fragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_refresh_rv;
  }

  @Override
  protected void initData() {
    mAdapter = new ExpandableProductDep2Adapter(null);
    mRv.setLayoutManager(new LinearLayoutManager(mContext));
    mRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).visibilityProvider(
        new VisibilityProvider() {
          @Override
          public boolean shouldHideDivider(int position, RecyclerView parent) {
            if (position == 0) return true;
            if (mAdapter.getItem(position) instanceof ProductDep21Item) {
              return true;
            } else {
              return false;
            }
          }
        }).build());
    mRv.setAdapter(mAdapter);
  }

  @Override
  protected void initView() {
    View headerView = getLayoutInflater().inflate(R.layout.view_product_dep_card2_header, null);
    mAdapter.addHeaderView(headerView);
    headerViewHolder = new HeaderViewHolder(headerView);


    headerViewHolder.mStartTimeTv.setText(new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime()));
    headerViewHolder.mEndTimeTv.setText(new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime()));


    refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_MORE;
        currentPage++;
        getProductChart();
      }

      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_REFRESH;
        currentPage = 1;
        getProductHeader();
        getProductChart();
      }
    });

    getProductHeader();
    getProductChart();
  }

  private void getProductChart() {
    OkGo.<String>get(MallAPI.CHART_PROMOTION_CHART)
        .tag(this)
        .params("time_start", time_start)
        .params("time_end", time_end)
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());

              ArrayList<MultiItemEntity> res = new ArrayList<MultiItemEntity>();
              List<ProductDep2> productList = JSON.parseArray(jsonObj.getString("items"), ProductDep2.class);
              for (int i = 0; i < productList.size(); i++) {
                ProductDep20Item lv0 = new ProductDep20Item(productList.get(i));
                lv0.addSubItem(new ProductDep21Item(productList.get(i)));
                res.add(lv0);
              }

              JSONObject metaObj = JSON.parseObject(jsonObj.getString("_meta"));
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mAdapter.setNewData(res);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                if (currentPage > metaObj.getIntValue("pageCount")) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                  loadState = Constants.STATE_NORMAL;
                } else {
                  mAdapter.addData(res);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }
        });
  }

  private boolean is15;
  public void getProductHeader() {
    OkGo.<String>get(MallAPI.CHART_PROMOTION_HEADER)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              headerViewHolder.mPromotionNumTv.setText(jsonObj.getString("promotion_num"));
              headerViewHolder.mProductNumTv.setText(jsonObj.getString("promotion_product_num"));
              headerViewHolder.mOrderNumTv.setText(jsonObj.getString("order_num"));
              headerViewHolder.mPriceTv.setText(jsonObj.getString("price"));
            }
          }

          @Override
          public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
            if (is15) {
              request.params("time_start", getBeforeDay(Calendar.getInstance().getTime()).getTime()/1000);
              request.params("time_end", System.currentTimeMillis()/1000);
            }
          }
        });
  }

  public static Date getBeforeDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -15);
    date = calendar.getTime();
    return date;
  }


  class HeaderViewHolder {
    @BindView(R.id.tv_top_time)
    TextView mTopTimeTv;

    @BindView(R.id.tv_promotion_num)
    TextView mPromotionNumTv;
    @BindView(R.id.tv_promotion_product_num)
    TextView mProductNumTv;
    @BindView(R.id.tv_order_num)
    TextView mOrderNumTv;
    @BindView(R.id.tv_price)
    TextView mPriceTv;

    @BindView(R.id.tv_start_time)
    TextView mStartTimeTv;
    @BindView(R.id.tv_end_time)
    TextView mEndTimeTv;

    public HeaderViewHolder(View headerView) {
      ButterKnife.bind(this, headerView);
    }

    @OnClick(R.id.tv_top_time)
    public void topTime(View view) {
      final EasyPopup mCirclePop = new EasyPopup(mContext)
          .setContentView(R.layout.layout_report_top_time)
          //是否允许点击PopupWindow之外的地方消失
          .setFocusAndOutsideEnable(true)
          .createPopup();
      mCirclePop.showAtAnchorView(view, VerticalGravity.BELOW, HorizontalGravity.CENTER, 0 ,0);

      mCirclePop.getView(R.id.tv_7).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          mCirclePop.dismiss();
          is15 = false;
          mTopTimeTv.setText("最近7天");
          getProductHeader();
        }
      });
      mCirclePop.getView(R.id.tv_15).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          mCirclePop.dismiss();
          is15 = true;
          mTopTimeTv.setText("最近15天");
          getProductHeader();
        }
      });
    }


    @OnClick(R.id.tv_start_time)
    public void startTime() {
      TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date,View v) {//选中事件回调
          headerViewHolder.mStartTimeTv.setText(getTime(date));
          time_start = TimeUtils.string2Millis(getTime(date)+" 00:00:00", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"))/1000;
          getProductChart();
        }
      }).setType(new boolean[]{true, true, true, false, false, false}).build();
      pvTime.show();
    }

    @OnClick(R.id.tv_end_time)
    public void endTime() {
      TimePickerView pvTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date,View v) {//选中事件回调
          headerViewHolder.mEndTimeTv.setText(getTime(date));
          time_end = TimeUtils.string2Millis(getTime(date)+" 23:59:59", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"))/1000;
          getProductChart();
        }
      }).setType(new boolean[]{true, true, true, false, false, false}).build();
      pvTime.show();
    }
  }

  public String getTime(Date date) {//可根据需要自行截取数据显示
    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
    return format.format(date);
  }
}
