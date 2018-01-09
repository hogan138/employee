package com.wanchang.employee.ui.cart;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
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
import com.wanchang.employee.data.entity.Shop;
import com.wanchang.employee.ui.base.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;

public class ShopListActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;
  @BindView(R.id.rv_shop)
  RecyclerView mShopRv;

  private BaseQuickAdapter<Shop, BaseViewHolder> mShopAdapter;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_shop_list;
  }

  @Override
  protected void initData() {
    mShopRv.setLayoutManager(new LinearLayoutManager(mContext));
    mShopRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_dc)).build());
    mShopRv.setAdapter(mShopAdapter = new BaseQuickAdapter<Shop, BaseViewHolder>(android.R.layout.simple_list_item_1) {

      @Override
      protected void convert(BaseViewHolder helper, Shop item) {
        helper.setText(android.R.id.text1, item.getName());
      }
    });

    mShopAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent data = new Intent();
        data.putExtra("shop_id", mShopAdapter.getItem(position).getId());
        data.putExtra("shop_name", mShopAdapter.getItem(position).getName());
        setResult(RESULT_OK, data);
        finish();
      }
    });
  }

  @Override
  protected void initView() {
    mTitleTv.setText("采购药店列表");

    OkGo.<String>get(MallAPI.SHOP_LIST)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<Shop> shops = JSON.parseArray(response.body(), Shop.class);
              mShopAdapter.addData(shops);
            }
          }
        });
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
