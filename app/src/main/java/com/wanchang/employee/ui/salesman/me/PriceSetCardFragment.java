package com.wanchang.employee.ui.salesman.me;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
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
import com.wanchang.employee.data.entity.Product;
import com.wanchang.employee.ui.base.BaseFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;
import me.shaohui.bottomdialog.BaseBottomDialog;
import me.shaohui.bottomdialog.BottomDialog;
import me.shaohui.bottomdialog.BottomDialog.ViewListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PriceSetCardFragment extends BaseFragment {


  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_order)
  RecyclerView mOrderRv;

  public BaseQuickAdapter<Product, BaseViewHolder> mOrderAdapter;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;

  private String clientId;


  public PriceSetCardFragment() {
    // Required empty public constructor
  }

  public static PriceSetCardFragment newInstance(String clientId) {
    Bundle args = new Bundle();
    args.putString("clientId", clientId);
    PriceSetCardFragment fragment = new PriceSetCardFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_order_card;
  }

  @Override
  protected void initData() {
    clientId = getArguments().getString("clientId");

    mOrderRv.setLayoutManager(new LinearLayoutManager(mContext));
    mOrderRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mOrderRv.setAdapter(mOrderAdapter = new BaseQuickAdapter<Product, BaseViewHolder>(R.layout.item_price_set_card_list) {
      @Override
      protected void convert(BaseViewHolder helper, Product item) {
        helper.setText(R.id.tv_product_title, item.getTitle());
        helper.setText(R.id.tv_product_manufacture, item.getManufacture_name());
        String validity = item.getValidity();
        if (validity.startsWith("0")) {
          helper.setVisible(R.id.tv_product_validity, false);
        } else {
          helper.setVisible(R.id.tv_product_validity, true);
        }
        helper.setText(R.id.tv_product_validity, "有效期至:"+validity);
        helper.setText(R.id.tv_product_specs, "规格："+item.getSpecs());
        helper.setText(R.id.tv_product_oprice, "原价：¥"+item.getOprice());
        String source = "一客一价：￥<font color='#e12929'>"+item.getClient_price()+"</font>";
        TextView mClientPrice = helper.getView(R.id.tv_client_price);
        mClientPrice.setText(Html.fromHtml(source));

        helper.addOnClickListener(R.id.tv_price_op);
      }
    });

    mOrderAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Product product = mOrderAdapter.getItem(position);
        switch (view.getId()) {
          case R.id.tv_price_op:
            setPrice(product);
            break;
        }
      }
    });
  }

  private BaseBottomDialog priceDialog;
  private void setPrice(final Product product) {
    priceDialog = BottomDialog.create(getFragmentManager())
        .setViewListener(new ViewListener() {
          @Override
          public void bindView(View v) {
            initPriceView(v, product);
          }
        })
        .setHeight(600)
        .setLayoutRes(R.layout.dialog_product_price_layout)      // dialog layout
        .show();
  }

  private void initPriceView(View v, final Product product) {
    TextView closeTv = v.findViewById(R.id.tv_close);
    TextView saveTv = v.findViewById(R.id.tv_save);
    final EditText priceEdt = v.findViewById(R.id.edt_price);
    TextView oPriceTv = v.findViewById(R.id.tv_oprice);
    TextView priceLimitTv = v.findViewById(R.id.tv_price_limit);
    closeTv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        priceDialog.dismiss();
      }
    });
    saveTv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        priceDialog.dismiss();
        OkGo.<String>post(MallAPI.CLIENT_PRODUCT_PRICE_SET)
            .tag(this)
            .params("client_id", clientId)
            .params("product_id", product.getProduct_id())
            .params("product_sku_id", product.getProduct_sku_id())
            .params("price", priceEdt.getText().toString().trim())
            .execute(new StringDialogCallback(mContext) {

              @Override
              public void onSuccess(Response<String> response) {
                super.onSuccess(response);
                if (response.code() == 200) {
                  ToastUtils.showShort("已修改");
                  loadData();
                }
              }
            });
      }
    });
    priceEdt.setText(product.getClient_price());
    oPriceTv.setText("佣金 ￥"+product.getBonus());
    priceLimitTv.setText("设置价格不能低于最低价 ￥"+product.getPrice_limit());
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
  }


  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    LogUtils.e("vvvvvv");
    if (!hidden) loadData();
  }

  private void loadData() {
    OkGo.<String>get(MallAPI.CLIENT_PRODUCT_PRICE_LIST)
        .tag(this)
        .params("client_id", clientId)
        .params("is_set", "1")
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              List<Product> orderList = JSON.parseArray(jsonObj.getString("items"), Product.class);
              JSONObject metaObj = JSON.parseObject(jsonObj.getString("_meta"));
              if (loadState == Constants.STATE_NORMAL || loadState == Constants.STATE_REFRESH) {
                mOrderAdapter.setNewData(orderList);
                refreshLayout.finishRefresh();
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+orderList.size());
                if (currentPage > metaObj.getIntValue("pageCount")) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mOrderAdapter.addData(orderList);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }
        });
  }

}
