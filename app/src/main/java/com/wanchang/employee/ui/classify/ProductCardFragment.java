package com.wanchang.employee.ui.classify;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Product;
import com.wanchang.employee.data.entity.Promotion;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;
import q.rorbin.badgeview.Badge;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCardFragment extends BaseFragment {


  @BindView(R.id.refreshLayout)
  SmartRefreshLayout refreshLayout;
  @BindView(R.id.rv_product_list)
  RecyclerView mProductListRv;


  private static Badge mQBadgeView;

  public BaseQuickAdapter<Product, BaseViewHolder> mProductListAdapter;


  private static final String ARGS_FCATEGORY_ID = "args_fcategory_id";
  private static final String ARGS_SCATEGORY_ID = "args_scategory_id";
  private static final String ARGS_KEYWORD = "args_keyword";


  private int fcategory_id;
  private int scategory_id;
  private String category_dosage;
  private String category_chinese_medicine;
  private String category_prescription;
  private String category_insurance;
  private String manufacture_id;
  private String keyword;
  private String tag;
  private String order_by;


  private int loadState = Constants.STATE_NORMAL;
  private int currentPage = 1;


  private EditText searchEdt;


  public ProductCardFragment() {
    // Required empty public constructor
  }

  public static ProductCardFragment newInstance(Badge badge, int fcategory_id, int scategory_id, String keyword) {
    Bundle args = new Bundle();
    mQBadgeView = badge;
    args.putInt(ARGS_FCATEGORY_ID, fcategory_id);
    args.putInt(ARGS_SCATEGORY_ID, scategory_id);
    args.putString(ARGS_KEYWORD, keyword);
    ProductCardFragment fragment = new ProductCardFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_product_card;
  }

  @Override
  protected void initData() {
    searchEdt = getActivity().findViewById(R.id.edt_topbar_title);

    fcategory_id = getArguments().getInt(ARGS_FCATEGORY_ID);
    scategory_id = getArguments().getInt(ARGS_SCATEGORY_ID);
    keyword = getArguments().getString(ARGS_KEYWORD);

    mProductListRv.setLayoutManager(new LinearLayoutManager(mContext));
    mProductListRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mProductListRv.setAdapter(mProductListAdapter = new BaseQuickAdapter<Product, BaseViewHolder>(R.layout.item_product_list) {
      @Override
      protected void convert(final BaseViewHolder helper, final Product item) {
        ImageView productIv = helper.getView(R.id.iv_product_img);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
        helper.setGone(R.id.tv_is_recommend, item.getRecommend() == 1 ? true : false);
        helper.setText(R.id.tv_product_title, item.getTitle());
        helper.setText(R.id.tv_product_manufacture, item.getManufacture_name());
        String validity = item.getValidity();
        if (validity.startsWith("0")) {
          helper.setVisible(R.id.tv_product_validity, false);
        } else {
          helper.setVisible(R.id.tv_product_validity, true);
        }
        helper.setText(R.id.tv_product_validity, "有效期至:"+validity);
        if ("".equals(MallApp.getInstance().getToken())) {
          helper.setText(R.id.tv_product_price, "登录可见");
        } else {
          if (item.getPrice() == 0) {
            helper.setText(R.id.tv_product_price, "暂无销售");
          } else {
            helper.setText(R.id.tv_product_price, "¥"+item.getPrice());
          }
        }
        TextView oPriceTv = helper.getView(R.id.tv_product_oprice);
        oPriceTv.setText("¥"+item.getOprice());
        oPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        List<Promotion> promotionList = item.getPromotion_list();
        if (promotionList != null && promotionList.size() >= 1) {
          helper.setVisible(R.id.tv_product_promotion, true);
          int promotionCategory = promotionList.get(0).getCategory();
          if (promotionCategory == 10) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_10);
          } else if(promotionCategory == 20) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_20);
          } else if(promotionCategory == 30) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_30);
          } else if(promotionCategory == 40) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_40);
          } else if(promotionCategory == 50) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_50);
          } else if(promotionCategory == 60) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_60);
          } else if(promotionCategory == 70) {
            helper.setText(R.id.tv_product_promotion, Constants.PROMOTION_CATEGORY_70);
          }
        } else {
          helper.setVisible(R.id.tv_product_promotion, false);
        }

        helper.setText(R.id.tv_product_specs, "规格："+item.getSpecs());

        final AnimShopButton animShopBtn = helper.getView(R.id.btn_product_number);
        animShopBtn.setCount(item.getShopping_cart_count());
        animShopBtn.setOnAddDelListener(new IOnAddDelListener() {
          @Override
          public void onAddSuccess(int i) {
            int realCount = animShopBtn.getCount() - 1;
            animShopBtn.setCount(realCount);
            int count = realCount + item.getPackaing();
            AddShoppingCart(item.getProduct_sku_id(), count);
          }

          @Override
          public void onAddFailed(int i, FailType failType) {

          }

          @Override
          public void onDelSuccess(int i) {
            int realCount = animShopBtn.getCount() + 1;
            if (realCount <= item.getPackaing()) {
              animShopBtn.setCount(realCount);
              ToastUtils.showShort("最低购买数量为"+realCount);
              return;
            }
            animShopBtn.setCount(realCount);
            int count = realCount - item.getPackaing();
            AddShoppingCart(item.getProduct_sku_id(), count);
          }

          @Override
          public void onDelFaild(int i, FailType failType) {

          }
        });

      }
    });
  }

  private void AddShoppingCart(int product_sku_id, int packaing) {
    OkGo.<String>post(MallAPI.SHOPPING_CART_ADD)
        .tag(this)
        .params("product_sku_id", product_sku_id)
        .params("count", packaing)
        .params("simple", 1)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              JSONObject jsonObj = JSON.parseObject(response.body());
              mQBadgeView.setBadgeNumber(jsonObj.getIntValue("shopping_cart_count"));
              loadData();
            }
          }
        });
  }

  @Override
  protected void initView() {
    refreshLayout.setEnableRefresh(false);
    refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
      @Override
      public void onLoadmore(RefreshLayout refreshlayout) {
        loadState = Constants.STATE_MORE;
        currentPage++;
        loadData();
      }
    });

    mProductListAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        startActivity(new Intent(mContext, ProductDetailActivity.class)
            .putExtra("product_sku_id", mProductListAdapter.getItem(position).getProduct_sku_id()));
      }
    });

    loadData();
  }

  protected void setFilter(String tag, String category_dosage, String category_chinese_medicine,
      String category_prescription, String category_insurance, String manufacture_id) {
    this.tag = tag;
    this.category_dosage = category_dosage;
    this.category_chinese_medicine = category_chinese_medicine;
    this.category_prescription = category_prescription;
    this.category_insurance = category_insurance;
    this.manufacture_id = manufacture_id;
    loadData();
  }

  protected void setOrderBy(String order_by) {
    this.order_by = order_by;
    loadData();
  }

  protected void setKeyword(String keyword) {
    currentPage = 1;
    this.keyword = keyword;
    loadState = Constants.STATE_NORMAL;
    loadData();
  }


  private void loadData() {
    OkGo.<String>get(MallAPI.PRODUCT_LIST)
        .tag(this)
        .params("recommend", 1)
        .params("page", currentPage)
        .params("per-page", Constants.PAGE_SIZE)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<Product> productList = JSON.parseArray(response.body(), Product.class);
              if (loadState == Constants.STATE_NORMAL) {
                mProductListAdapter.setNewData(productList);
              } else if (loadState == Constants.STATE_MORE) {
                LogUtils.e("----"+productList.size());
                if (productList.size() == 0) {
                  refreshLayout.finishLoadmore();
                  refreshLayout.setLoadmoreFinished(true);
                } else {
                  mProductListAdapter.addData(productList);
                  refreshLayout.finishLoadmore();
                }
              }
            }
          }

          @Override
          public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
            if (fcategory_id != -1) {
              request.params("fcategory_id", fcategory_id);
            }
            if (scategory_id != -1) {
              request.params("scategory_id", scategory_id);
            }
            if (!TextUtils.isEmpty(searchEdt.getText().toString().trim())) {
              request.params("keyword", searchEdt.getText().toString().trim());
            }
            if (!TextUtils.isEmpty(order_by)) {
              request.params("order-by", order_by);
            }
            if (!TextUtils.isEmpty(manufacture_id)) {
              request.params("manufacture_name", manufacture_id);
              request.params("category_dosage", category_dosage);
              request.params("category_chinese_medicine", category_chinese_medicine);
              request.params("category_prescription", category_prescription);
              request.params("category_insurance", category_insurance);
              request.params("tag", tag);
            }
          }
        });
  }

}
