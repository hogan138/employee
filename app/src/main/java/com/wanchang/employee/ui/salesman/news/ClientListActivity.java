package com.wanchang.employee.ui.salesman.news;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.wanchang.employee.R;
import com.wanchang.employee.adapter.ClientListAdapter;
import com.wanchang.employee.data.entity.Client;
import com.wanchang.employee.ui.base.BaseActivity;
import java.util.List;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

public class ClientListActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.indexableLayout)
  IndexableLayout indexableLayout;

  private ClientListAdapter mAdapter;

  private List<Client> data;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_client_list;
  }

  @Override
  protected void initData() {
    data = getIntent().getParcelableArrayListExtra("clientList");

  }

  @Override
  protected void initView() {
    mTitleTv.setText("客户("+data.size()+")");

    indexableLayout.setLayoutManager(new GridLayoutManager(this, 1));
    mAdapter = new ClientListAdapter(this);
    indexableLayout.setAdapter(mAdapter);
    indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
    indexableLayout.setOverlayStyle_Center();
    mAdapter.setDatas(data);

    mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<Client>() {
      @Override
      public void onItemClick(View v, int originalPosition, int currentPosition, Client entity) {
        if (originalPosition >= 0) {
          startActivity(new Intent(mContext, ClientDetailActivity.class).putExtra("client", entity));
          //ToastUtils.showShortToast("选中:" + entity.getName() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
        } else {
          //ToastUtils.showShortToast("选中Header:" + entity.getName() + "  当前位置:" + currentPosition);
        }
      }
    });
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }


}
