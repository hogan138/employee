package com.wanchang.employee.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Message;
import com.wanchang.employee.easemob.ui.ConversationListFragment;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.eventbus.RefreshPushMessageEvent;
import com.wanchang.employee.ui.push.OrderMsgListActivity;
import com.wanchang.employee.ui.push.PromotionMsgListActivity;
import com.wanchang.employee.ui.push.SystemMsgListActivity;
import com.wanchang.employee.util.ACache;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends BaseFragment {


  @BindView(R.id.tv_topbar_left)
  TextView mLeftTv;
  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  private ConversationListFragment conversationListFragment;


  private static final String BUNDLE_ARGS = "bundle_args";

  public ConversationFragment() {
    // Required empty public constructor
  }

  public static ConversationFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    ConversationFragment fragment = new ConversationFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_conversation;
  }


  @Override
  protected void initData() {
    conversationListFragment = new ConversationListFragment();

    getFragmentManager()
        .beginTransaction()
        .replace(R.id.conversation_list_container, conversationListFragment)
        .commit();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(RefreshPushMessageEvent event) {
    LogUtils.e("push event");
    getHeader();
  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    LogUtils.e("vvvvvvvvvv--"+hidden);
    if (!hidden) refresh();
  }


  @Override
  public void onResume() {
    super.onResume();

    refresh();
  }

  /**
   * refresh ui
   */
  public void refresh() {
    if (conversationListFragment != null) {
      conversationListFragment.refresh();

      getHeader();
    }
  }

  private View header;

  private void getHeader() {
    EaseConversationList conversationListView = conversationListFragment.getConversationListView();
    LogUtils.e("-------"+conversationListView);
    if (conversationListView == null) return;
    if (header == null) {
      header = getLayoutInflater().inflate(R.layout.view_conversation_header, null);
    }
    if (conversationListView.getHeaderViewsCount() != 0) {
      conversationListView.removeHeaderView(header);
    }
    conversationListView.addHeaderView(header);
    final RelativeLayout promotionRl = header.findViewById(R.id.rl_promotion);
    final TextView promotionTitleTv = header.findViewById(R.id.tv_promotion_title);
    final TextView promotionContentTv = header.findViewById(R.id.tv_promotion_content);
    final TextView promotionTimeTv = header.findViewById(R.id.tv_promotion_time);
    ImageView promotionIv = header.findViewById(R.id.iv_20);
    final RelativeLayout systemRl = header.findViewById(R.id.rl_system);
    final TextView systemTitleTv = header.findViewById(R.id.tv_system_title);
    final TextView systemContentTv = header.findViewById(R.id.tv_system_content);
    final TextView systemTimeTv = header.findViewById(R.id.tv_system_time);
    ImageView systemIv = header.findViewById(R.id.iv_10);
    final RelativeLayout orderRl = header.findViewById(R.id.rl_order);
    final TextView orderTitleTv = header.findViewById(R.id.tv_order_title);
    final TextView orderContentTv = header.findViewById(R.id.tv_order_content);
    final TextView orderTimeTv = header.findViewById(R.id.tv_order_time);
    ImageView orderIv = header.findViewById(R.id.iv_30);
    if ("1".equals(ACache.get(mContext).getAsString(Constants.KEY_NEW_MSG_1))) {
      systemIv.setVisibility(View.VISIBLE);
    } else {
      systemIv.setVisibility(View.GONE);
    }
    if ("2".equals(ACache.get(mContext).getAsString(Constants.KEY_NEW_MSG_2))) {
      promotionIv.setVisibility(View.VISIBLE);
    } else {
      promotionIv.setVisibility(View.GONE);
    }
    if ("3".equals(ACache.get(mContext).getAsString(Constants.KEY_NEW_MSG_3))) {
      orderIv.setVisibility(View.VISIBLE);
    } else {
      orderIv.setVisibility(View.GONE);
    }
    promotionRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getActivity(), PromotionMsgListActivity.class));
      }
    });
    systemRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getActivity(), SystemMsgListActivity.class));
      }
    });
    orderRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getActivity(), OrderMsgListActivity.class));
      }
    });
    OkGo.<String>get(MallAPI.MESSAGE_LATEST)
        .tag(this)
        .execute(new StringDialogCallback(getActivity()) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              Message msg1 = JSON.parseObject(jsonObj.getString("promotion"), Message.class);
              if (msg1.getTitle() != null) {
                //promotionTitleTv.setText(msg1.getTitle());
                promotionContentTv.setText(msg1.getTitle());
                promotionTimeTv.setText(new SimpleDateFormat("MM-dd HH:mm").format(new Date(msg1.getSent_at()*1000)));
              }
              Message msg2 = JSON.parseObject(jsonObj.getString("system"), Message.class);
              if (msg2.getTitle() != null) {
                //systemTitleTv.setText(msg2.getTitle());
                systemContentTv.setText(msg2.getTitle());
                systemTimeTv.setText(new SimpleDateFormat("MM-dd HH:mm").format(new Date(msg2.getSent_at()*1000)));
              }
              Message msg3 = JSON.parseObject(jsonObj.getString("order"), Message.class);
              if (msg3.getTitle() != null) {
                //orderTitleTv.setText(msg3.getTitle());
                orderContentTv.setText(msg3.getTitle());
                orderTimeTv.setText(new SimpleDateFormat("MM-dd HH:mm").format(new Date(msg3.getSent_at() * 1000)));
              }
            }
          }
        });
  }

  @Override
  protected void initView() {
    mLeftTv.setVisibility(View.GONE);
    mTitleTv.setText("消息");
  }

}
