package com.wanchang.employee.ui.salesman.home;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.adapter.ClientListAdapter;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Client;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.util.ACache;
import java.util.List;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

public class SelectClientActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.indexableLayout)
  IndexableLayout indexableLayout;

  private ClientListAdapter mAdapter;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_select_client;
  }

  @Override
  protected void initData() {
  }

  @Override
  protected void initView() {
    mTitleTv.setText("选择客户");

    indexableLayout.setLayoutManager(new GridLayoutManager(this, 1));
    mAdapter = new ClientListAdapter(this);
    indexableLayout.setAdapter(mAdapter);
    indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
    indexableLayout.setOverlayStyle_Center();


    mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<Client>() {
      @Override
      public void onItemClick(View v, int originalPosition, int currentPosition, Client entity) {
        if (originalPosition >= 0) {
          String client_id = entity.getId()+"";
          String client_name = entity.getName();
          LogUtils.e(client_id+"-----"+client_name);
          ACache.get(mContext).put(Constants.KEY_CLIENT_ID, client_id);
          ACache.get(mContext).put(Constants.KEY_CLIENT_NAME, client_name);
          finish();
          //ToastUtils.showShortToast("选中:" + entity.getName() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
        } else {
          //ToastUtils.showShortToast("选中Header:" + entity.getName() + "  当前位置:" + currentPosition);
        }
      }
    });

    getClientList();
  }

  private void getClientList() {
    OkGo.<String>get(MallAPI.SALESMAN_CLIENT_LIST)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<Client> data = JSON.parseArray(response.body(), Client.class);
              mAdapter.setDatas(data);
            }
          }
        });
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }


}
