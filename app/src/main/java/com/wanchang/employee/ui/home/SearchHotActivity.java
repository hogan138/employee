package com.wanchang.employee.ui.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.ui.classify.ProductListActivity;
import java.util.List;

public class SearchHotActivity extends BaseActivity{

  @BindView(R.id.edt_topbar_title)
  EditText mSearchEdt;

  @BindView(R.id.rv_hot)
  RecyclerView mHotRv;


  public BaseQuickAdapter<String, BaseViewHolder> mAdapter;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_search_hot;
  }

  @Override
  protected void initData() {
    String keyword = getIntent().getStringExtra("keyword");
    if (!TextUtils.isEmpty(keyword)) {
      mSearchEdt.setText(keyword);
    }

    mHotRv.setLayoutManager(new GridLayoutManager(mContext, 3));
//    mHotRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
//        getResources().getColor(R.color.color_e5)).build());
    mHotRv.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search_hot_list) {
      @Override
      protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_hot, item);
      }
    });

    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(mContext, ProductListActivity.class).putExtra("keyword", mAdapter.getItem(position)));
        finish();
      }
    });
  }

  @Override
  protected void initView() {
    OkGo.<String>get(MallAPI.SEARCH_HOT)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<String> data = JSON.parseArray(response.body(), String.class);
              mAdapter.setNewData(data);
            }
          }
        });

    mSearchEdt.setOnEditorActionListener(new OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
          startActivity(new Intent(mContext, ProductListActivity.class).putExtra("keyword", mSearchEdt.getText().toString().trim()));
          finish();
        }
        return false;
      }
    });
  }

  @OnClick(R.id.tv_topbar_right)
  public void onGoBack() {
    finish();
  }
}
