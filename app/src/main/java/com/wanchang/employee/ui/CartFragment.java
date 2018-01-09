package com.wanchang.employee.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mcxtzhang.lib.AnimShopButton;
import com.mylhyl.circledialog.CircleDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanchang.employee.R;
import com.wanchang.employee.adapter.SectionCartAdapter;
import com.wanchang.employee.adapter.SectionCartAdapter.CountAddDelListener;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.CartSection;
import com.wanchang.employee.data.entity.Cart_DataList;
import com.wanchang.employee.data.entity.Cart_ItemList;
import com.wanchang.employee.data.entity.Promotion;
import com.wanchang.employee.data.entity.ShoppingCart;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.cart.ConfirmOrderActivity;
import com.wanchang.employee.ui.classify.ProductDetailActivity;
import com.wanchang.employee.ui.home.GetCouponListActivity;
import com.wanchang.employee.ui.push.PromotionDetailActivity;
import com.wanchang.employee.ui.salesman.home.SelectClientActivity;
import com.wanchang.employee.util.ACache;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.ArrayList;
import java.util.List;
import me.shaohui.bottomdialog.BaseBottomDialog;
import me.shaohui.bottomdialog.BottomDialog;
import me.shaohui.bottomdialog.BottomDialog.ViewListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseFragment {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.tv_topbar_client)
  TextView mTopBarClientTv;
  @BindView(R.id.tv_topbar_modify)
  TextView mCartModifyTv;
  @BindView(R.id.ll_cart_normal)
  LinearLayout mNormalLl;
  @BindView(R.id.rl_cart_modify)
  RelativeLayout mModifyRl;
  @BindView(R.id.refreshLayout)
  RefreshLayout refreshLayout;
  @BindView(R.id.rv_shopping_cart)
  RecyclerView mCartRv;


  @BindView(R.id.cb_check_all_edit)
  CheckBox mCheckAllEditCb;
  @BindView(R.id.cb_check_all)
  CheckBox mCheckAllCb;
  @BindView(R.id.tv_total_price)
  TextView mTotalPriceTv;
  @BindView(R.id.tv_ototal_price)
  TextView mOtotalPriceTv;
  @BindView(R.id.tv_settlement_count)
  TextView mSettelCountTv;

  private SectionCartAdapter sectionCartAdapter;

  //private int loadState = Constants.STATE_NORMAL;


  private static final String BUNDLE_ARGS = "bundle_args";
  private int cartMode = 0;

  private int shoppingCount;

  public CartFragment() {
    // Required empty public constructor
  }

  public static CartFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    CartFragment fragment = new CartFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_cart;
  }

  @Override
  protected void initData() {
    //ImmersionBar.setTitleBar(mContext, mToolbar);
    //ImmersionBar.with(this).statusBarDarkFont(true).init();
  }

  @Override
  protected void initView() {
    mCartRv.setLayoutManager(new LinearLayoutManager(mContext));
    mCartRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_f3)).build());
    sectionCartAdapter = new SectionCartAdapter(R.layout.item_cart_section_content, R.layout.def_cart_section_head, null);
    mCartRv.setAdapter(sectionCartAdapter);

    sectionCartAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LogUtils.e("----"+position);
        CartSection cartSection = sectionCartAdapter.getItem(position);
        if (cartSection.isHeader) {
          //Toast.makeText(SectionUseActivity.this, mySection.header, Toast.LENGTH_LONG).show();
        } else {
          //Toast.makeText(SectionUseActivity.this, mySection.t.getName(), Toast.LENGTH_LONG).show();
          startActivity(new Intent(mContext, ProductDetailActivity.class)
              .putExtra("product_sku_id", cartSection.t.getProduct_sku_id()));
        }
      }
    });
    sectionCartAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
          case R.id.cb_check:
            CheckBox checkBox = (CheckBox) view;
            if (cartMode == 1) {
              sectionCartAdapter.setCheckPos(checkBox, position);
              int itemListNumber = sectionCartAdapter.getItemCount() / 2;
              LogUtils.e("------"+itemListNumber);
              if (checkBox.isChecked()) {
                checkNumber++;
              }
              if (!checkBox.isChecked()) {
                checkNumber--;
              }
              LogUtils.e("00000==="+checkNumber);
              if (checkNumber == itemListNumber) {
                mCheckAllEditCb.setChecked(true);
              } else {
                mCheckAllEditCb.setChecked(false);
              }
              return;
            }
            cartChangeChecked(sectionCartAdapter.getItem(position).t.getId(), checkBox.isChecked() ? 1 : 0);
            break;
          case R.id.tv_product_promotion:
            if (cartMode == 1) {
              return;
            }
            Cart_ItemList carItem = sectionCartAdapter.getItem(position).t;
            openPromotion(carItem);
            break;
          case R.id.stv_promotion_category:
            if (cartMode == 1) {
              return;
            }
            //ToastUtils.showShort("凑单...");
            String key = sectionCartAdapter.getItem(position).getKey();
            openActivity(new Intent(mContext, PromotionDetailActivity.class).putExtra("key", key), true);
            break;

        }
      }
    });

    sectionCartAdapter.setOnCountAddDelListener(new CountAddDelListener() {

      @Override
      public void onAddClick(View view, int position) {
        AnimShopButton animShopBtn = (AnimShopButton) view;
        LogUtils.e("my_report--"+position);
        Cart_ItemList carItem = sectionCartAdapter.getItem(position).t;
        int realCount = animShopBtn.getCount() - 1;
        animShopBtn.setCount(realCount);
        int count = realCount + carItem.getPackaing();
        cartChangeCount(count, carItem.getId());
      }

      @Override
      public void onDelClick(View view, int position) {
        AnimShopButton animShopBtn = (AnimShopButton) view;
        Cart_ItemList carItem = sectionCartAdapter.getItem(position).t;
        int realCount = animShopBtn.getCount() + 1;
        if (realCount <= carItem.getPackaing()) {
          animShopBtn.setCount(realCount);
          ToastUtils.showShort("最低购买数量为"+realCount);
          return;
        }
        animShopBtn.setCount(realCount);
        int count = realCount - carItem.getPackaing();
        cartChangeCount(count, carItem.getId());
      }
    });


    refreshLayout.setEnableLoadmore(false);
    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        //loadState = Constants.STATE_REFRESH;
        loadData();
      }
    });
  }

  private BaseBottomDialog promotionDialog;
  public void openPromotion(final Cart_ItemList carItem) {
    promotionDialog = BottomDialog.create(getFragmentManager())
        .setViewListener(new ViewListener() {
          @Override
          public void bindView(View v) {
            initPromotionView(v, carItem.getPromotion_list(), carItem.getId());
          }
        })
        .setHeight(700)
        .setLayoutRes(R.layout.dialog_promotion_layout)      // dialog layout
        .show();
  }

  private void initPromotionView(View v, List<Promotion> promotionList, final int car_id) {
    RelativeLayout closeRl = v.findViewById(R.id.rl_close);
    closeRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        promotionDialog.dismiss();
      }
    });
    RecyclerView promotionRv = v.findViewById(R.id.rv_promotion);
    final BaseQuickAdapter<Promotion, BaseViewHolder> mPromotionAdapter;
    promotionRv.setAdapter(mPromotionAdapter = new BaseQuickAdapter<Promotion, BaseViewHolder>(R.layout.item_promotion_list, promotionList) {

      @Override
      protected void convert(BaseViewHolder helper, Promotion item) {
        if (item.getCategory() == 10) {
          helper.setGone(R.id.tv_promotion_category, true);
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_10);
        } else if(item.getCategory() == 20) {
          helper.setGone(R.id.tv_promotion_category, true);
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_20);
        } else if(item.getCategory() == 30) {
          helper.setGone(R.id.tv_promotion_category, true);
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_30);
        } else if(item.getCategory() == 40) {
          helper.setGone(R.id.tv_promotion_category, true);
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_40);
        } else if(item.getCategory() == 50) {
          helper.setGone(R.id.tv_promotion_category, true);
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_50);
        } else if(item.getCategory() == 60) {
          helper.setGone(R.id.tv_promotion_category, true);
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_60);
        } else if(item.getCategory() == 70) {
          helper.setGone(R.id.tv_promotion_category, true);
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_70);
        } else {
          helper.setGone(R.id.tv_promotion_category, false);
        }
        helper.setText(R.id.tv_promotion_title, item.getTitle());
        helper.setVisible(R.id.iv_right_arrow, false);
      }
    });
    promotionRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).margin(32).build());
    promotionRv.setLayoutManager(new LinearLayoutManager(mContext));
    mPromotionAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        int promotion_id = mPromotionAdapter.getItem(position).getId();
        cartChangePromotion(promotion_id, car_id);
        promotionDialog.dismiss();
      }
    });
  }

  /**
   * 购物车修改促销
   * @param promotion_id
   * @param cart_id
   */
  private void cartChangePromotion(int promotion_id, int cart_id) {
    OkGo.<String>post(MallAPI.SHOPPING_CART_CHANGE_PROMOTION)
        .tag(this)
        .params("promotion_id", promotion_id)
        .params("id", cart_id)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              handResult(response);
            }
          }
        });
  }

  /**
   * 购物车修改数量
   * @param count
   * @param cart_id
   */
  private void cartChangeCount(int count, int cart_id) {
    if (cartMode == 1) {
      LogUtils.e(cart_id+"======"+count);
      ACache.get(mContext).put(""+cart_id, count);
      return;
    }
    OkGo.<String>post(MallAPI.SHOPPING_CART_CHANGE_COUNT)
        .tag(this)
        .params("id", cart_id)
        .params("count", count)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              handResult(response);
            }
          }
        });
  }

  /**
   * 购物车选中取消
   * @param cart_id
   * @param checked
   */
  private void cartChangeChecked(int cart_id, int checked) {

    OkGo.<String>post(MallAPI.SHOPPING_CART_CHANGE_CHECKED)
        .tag(this)
        .params("id", cart_id)
        .params("checked", checked)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              handResult(response);
            }
          }
        });
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) loadData();
  }

  /**
   * refresh ui
   */
  public void refresh() {
    loadData();
  }

  private void loadData() {
    if (MallApp.getInstance().getGroupId() == Constants.GROUP_CLIENT) {
      mTopBarClientTv.setVisibility(View.GONE);
    } else {
      mTopBarClientTv.setVisibility(View.VISIBLE);
      String client_name = ACache.get(mContext).getAsString(Constants.KEY_CLIENT_NAME);
      if (!TextUtils.isEmpty(client_name)) {
        mTopBarClientTv.setText(client_name);
      }
    }
    OkGo.<String>get(MallAPI.SHOPPING_CART)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              handResult(response);
            }
          }
        });
  }

  private void handResult(Response<String> response) {
    ShoppingCart shoppingCart = JSON.parseObject(response.body(), ShoppingCart.class);

    View header = getLayoutInflater().inflate(R.layout.view_cart_header, null);
    if (shoppingCart.getData_list().size() == 0) {
      sectionCartAdapter.removeAllHeaderView();
    } else {
      sectionCartAdapter.removeAllHeaderView();
      sectionCartAdapter.addHeaderView(header);
    }

    mCheckAllCb.setChecked(shoppingCart.getChecked_all() == 1 ? true : false);
    mTotalPriceTv.setText("实付款：¥"+shoppingCart.getSummary().getTotal());
    mOtotalPriceTv.setText("总额：¥"+shoppingCart.getSummary().getTotal_origin());
    mSettelCountTv.setText("结算("+shoppingCart.getSummary().getCount()+")");

    shoppingCount = shoppingCart.getSummary().getCount();

    sectionCartAdapter.setNewData(getData(shoppingCart));
    refreshLayout.finishRefresh();

    header.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        //ToastUtils.showShort("coupon");
        openActivity(new Intent(mContext, GetCouponListActivity.class), true);
      }
    });
  }


  private List<CartSection> getData(ShoppingCart shoppingCart) {
    List<CartSection> list = new ArrayList<>();
    List<Cart_DataList> dataList = shoppingCart.getData_list();
    for (int i = 0; i < dataList.size(); i++) {
      list.add(new CartSection(true, dataList.get(i).getPromotion()!=null ? dataList.get(i).getPromotion().getTitle() : "",
          dataList.get(i).getType(), dataList.get(i).getIs_promotion_satisfy(), dataList.get(i).getPromotion()!=null ? dataList.get(i).getPromotion().getKey() : ""));
      for (int j = 0; j < dataList.get(i).getShopping_cart_item_list().size(); j++) {
        list.add(new CartSection(dataList.get(i).getShopping_cart_item_list().get(j)));
      }
    }
    return list;
  }

  /**
   * 购物车全选
   */
  @OnClick(R.id.cb_check_all)
  public void onCheckAll() {
    OkGo.<String>post(MallAPI.SHOPPING_CART_CHECK_ALL)
        .tag(this)
        .params("checked", mCheckAllCb.isChecked() ? 1 : 0)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              handResult(response);
            }
          }
        });
  }

  /**
   * 购物车删除
   */
  @OnClick(R.id.tv_cart_delete)
  public void onCartDel() {
    boolean[] itemStatus = sectionCartAdapter.getItemStatus();
    List<Integer> idList = new ArrayList<>();
    for (int i = 0; i < itemStatus.length; i++) {
      if (itemStatus[i]) {
        idList.add(sectionCartAdapter.getItem(i).t.getId());
        itemStatus[i] = false;
      }
    }
    if (idList.size() == 0) {
      ToastUtils.showShort("您还没有选择商品哦！");
    } else {
      handleCartDel(idList);
    }
  }

  private void handleCartDel(final List<Integer> idList) {
    new CircleDialog.Builder(getActivity())
        .setTitle("提示")
        .setText("您确定要删除吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            String ids = "";
            for (int i = 0; i < idList.size()-1; i++) {
              ids += idList.get(i) + ",";
            }
            ids += idList.get(idList.size()-1);
            LogUtils.e("-----"+ids);
            OkGo.<String>post(MallAPI.SHOPPING_CART_REMOVE)
                .tag(this)
                .params("ids", ids)
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                      handResult(response);
                      mCheckAllEditCb.setChecked(false);
                    }
                  }
                });
          }
        }).show();
  }

  /**
   * 购物车全选编辑模式
   */
  @OnClick(R.id.cb_check_all_edit)
  public void onCheckAllEdit() {
    List<CartSection> itemList = sectionCartAdapter.getData();
    for (int i = 0; i < itemList.size(); i++) {
      if (!itemList.get(i).isHeader) {
        sectionCartAdapter.getItemStatus()[i] = mCheckAllEditCb.isChecked();
        sectionCartAdapter.notifyDataSetChanged();
      }

    }
    if (mCheckAllEditCb.isChecked()) {
      checkNumber = sectionCartAdapter.getItemCount() / 2;
    } else {
      checkNumber = 0;
    }
  }


  private int checkNumber = 0;
  /**
   * 购物车编辑
   */
  @OnClick(R.id.tv_topbar_modify)
  public void onCartModify() {
    if (cartMode == 0) {
      mCartModifyTv.setText("完成");
      mNormalLl.setVisibility(View.GONE);
      mModifyRl.setVisibility(View.VISIBLE);

      cartMode = 1;
      sectionCartAdapter.setCartMode(cartMode);
      mCheckAllEditCb.setChecked(false);
      checkNumber = 0;
    } else {
      cartMode = 0;
      sectionCartAdapter.setCartMode(cartMode);

      mCartModifyTv.setText("编辑");
      mNormalLl.setVisibility(View.VISIBLE);
      mModifyRl.setVisibility(View.GONE);
      handleComplete();
    }
  }

  private void handleComplete() {
    List<CartSection> itemList = sectionCartAdapter.getData();
    LogUtils.e("-----"+itemList.size());
    JSONObject jsonObj = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    try {
      jsonObj.put("data_list", jsonArray);
      for (int i = 0; i < itemList.size(); i++) {
        if (!itemList.get(i).isHeader) {
          JSONObject jsonObjItem = new JSONObject();
          jsonObjItem.put("id", itemList.get(i).t.getId());
          if (ACache.get(mContext).getAsObject(""+itemList.get(i).t.getId()) == null) {
            jsonObjItem.put("count", itemList.get(i).t.getCount());
          } else {
            jsonObjItem.put("count", (int)ACache.get(mContext).getAsObject(""+itemList.get(i).t.getId()));
            ACache.get(mContext).remove(""+itemList.get(i).t.getId());
          }
          jsonArray.put(jsonObjItem);
          sectionCartAdapter.getItemStatus()[i] = false;
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    LogUtils.e("000000===="+jsonObj);
    OkGo.<String>post(MallAPI.SHOPPING_CART_BATCH_COMPLETE)
        .tag(this)
        .upJson(jsonObj)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              handResult(response);
            }
          }
        });
  }



  /**
   * 结算
   */
  @OnClick(R.id.tv_settlement_count)
  public void onSettlement() {
    if (shoppingCount <= 0) {
      ToastUtils.showShort("您还没有选择商品哦");
      return;
    }
    startActivity(new Intent(mContext, ConfirmOrderActivity.class));
  }


  @OnClick(R.id.tv_topbar_client)
  public void onSelectClient() {
    openActivity(new Intent(mContext, SelectClientActivity.class), true);
  }


}
