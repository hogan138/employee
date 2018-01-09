package com.wanchang.employee.ui.salesman.news;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.Group;
import com.wanchang.employee.easemob.Constant;
import com.wanchang.employee.easemob.ui.ChatActivity;
import com.wanchang.employee.ui.base.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;

public class GroupListActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.rv_group)
  RecyclerView mGroupRv;


  private BaseQuickAdapter<Group, BaseViewHolder> mAdapter;
  private List<Group> data;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_group_list;
  }

  @Override
  protected void initData() {
    data = getIntent().getParcelableArrayListExtra("groupList");
    mGroupRv.setAdapter(mAdapter = new BaseQuickAdapter<Group, BaseViewHolder>(R.layout.item_group_list, data) {

      @Override
      protected void convert(BaseViewHolder helper, Group item) {
        helper.setText(R.id.tv_group_name, item.getName()+"("+item.getUser().size()+")");
      }
    });
    mGroupRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mGroupRv.setLayoutManager(new LinearLayoutManager(mContext));

    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Group group = mAdapter.getItem(position);
        startActivity(new Intent(mContext, ChatActivity.class)
            .putExtra(Constant.EXTRA_USER_ID, group.getGroup_id())
            .putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP));
      }
    });
  }

  @Override
  protected void initView() {
    mTitleTv.setText("群聊("+data.size()+")");
  }


  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }


}
