package com.wanchang.employee.ui.push;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.MsgCheck;
import com.wanchang.employee.ui.base.BaseFragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MsgCheckCard0Fragment extends BaseFragment {

  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv)
  RecyclerView mRv;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;


  private static final String BUNDLE_ARGS = "bundle_args";


  private BaseQuickAdapter<MsgCheck, BaseViewHolder> mAdapter;


  public MsgCheckCard0Fragment() {
    // Required empty public constructor
  }

  public static MsgCheckCard0Fragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    MsgCheckCard0Fragment fragment = new MsgCheckCard0Fragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_msg_check;
  }

  @Override
  protected void initData() {
    mRv.setLayoutManager(new LinearLayoutManager(mContext));
//    mSystemRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
//        getResources().getColor(R.color.color_e5)).build());
    mRv.setAdapter(mAdapter = new BaseQuickAdapter<MsgCheck, BaseViewHolder>(R.layout.item_msg_check_card0) {
      @Override
      protected void convert(BaseViewHolder helper, MsgCheck item) {
        helper.setText(R.id.tv_date, getTime(item.getCreated_at()));
        helper.setText(R.id.tv_title, item.getType());
        helper.setText(R.id.tv_content, item.getTitle());
        if (item.getStatus() == -1) {
          helper.setText(R.id.tv_status, "已拒绝");
          helper.setTextColor(R.id.tv_status, getResources().getColor(R.color.color_80));
        } else if (item.getStatus() == 1) {
          helper.setText(R.id.tv_status, "审核");
          helper.setTextColor(R.id.tv_status, getResources().getColor(R.color.color_336));
        } else if (item.getStatus() == 100) {
          helper.setText(R.id.tv_status, "已通过");
          helper.setTextColor(R.id.tv_status, getResources().getColor(R.color.color_80));
        } else {
          helper.setText(R.id.tv_status, "只读");
          helper.setTextColor(R.id.tv_status, getResources().getColor(R.color.color_1a));
        }


        if (item.getStatus() == -1) {
          helper.setGone(R.id.ll_reject, true);
          helper.setText(R.id.tv_reject_reason, "原因："+item.getReject_reason());
        } else {
          helper.setGone(R.id.ll_reject, false);
          helper.setText(R.id.tv_reject_reason, "");
        }
      }
    });

    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mAdapter.getItem(position).getStatus() == 1) {
          int id = mAdapter.getItem(position).getId();
          showDialog(id);
        }

      }
    });
  }

  private void showDialog(final int msgId) {
    final String[] items = {"通过", "拒绝"};
    new CircleDialog.Builder(mContext)
        .configDialog(new ConfigDialog() {
          @Override
          public void onConfig(DialogParams params) {
            //增加弹出动画
            //params.animStyle = R.style.dialogWindowAnim;
          }
        })
        .setTitle("请选择审核结果")
        .setTitleColor(getResources().getColor(R.color.color_e94))
        .setItems(items, new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
              case 0:
                checkNotify(msgId, 100, "");
                break;
              case 1:
                showRejectDialog(msgId);
                break;
            }
          }
        })
        .setNegative("取消", null)
        .configNegative(new ConfigButton() {
          @Override
          public void onConfig(ButtonParams params) {
            //取消按钮字体颜色
            params.textColor = getResources().getColor(R.color.color_aff);
          }
        })
        .show();


  }

  private void showRejectDialog(final int msgId) {
    new CircleDialog.Builder(mContext)
        .setCanceledOnTouchOutside(false)
        .setCancelable(true)
        .setTitle("拒绝原因")
        .setInputHint("请输入拒绝原因")
        .configInput(new ConfigInput() {
          @Override
          public void onConfig(InputParams params) {
//                                params.inputBackgroundResourceId = R.drawable.bg_input;
          }
        })
        .setNegative("取消", null)
        .setPositiveInput("确定", new OnInputClickListener() {
          @Override
          public void onClick(String text, View v) {
            checkNotify(msgId, -1, text);
          }
        })
        .show();
  }

  private void checkNotify(int msgId, int status, String reject_reason) {
    OkGo.<String>post(MallAPI.USER_USER_NOTIFICATION_CHECK)
        .tag(this)
        .params("id", msgId)
        .params("status", status)
        .params("reject_reason", reject_reason)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              loadData();
            }
          }
        });
  }

  @Override
  protected void initView() {

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

    loadData();
  }

  private void loadData() {
    OkGo.<String>get(MallAPI.USER_USER_NOTIFICATION)
        .tag(this)
        .params("expand", "type")
        .params("status", "")
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<MsgCheck> systemList = JSON.parseArray(jsonObj.getString("items"), MsgCheck.class);
              JSONObject metaObj = JSON.parseObject(jsonObj.getString("_meta"));
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mAdapter.setNewData(systemList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+systemList.size());
                if (currentPage > metaObj.getIntValue("pageCount")) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                  loadState = Constants.STATE_NORMAL;
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

}
