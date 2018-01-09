package com.wanchang.employee.ui;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Category1Item;
import com.wanchang.employee.data.entity.Category2Item;
import com.wanchang.employee.data.entity.RecommendProduct;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.classify.ProductDetailActivity;
import com.wanchang.employee.ui.classify.ProductListActivity;
import com.wanchang.employee.ui.home.SearchHotActivity;
import com.wanchang.employee.util.GlideApp;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends BaseFragment {
  @BindView(R.id.tv_topbar_left)
  TextView mLeftTv;
  @BindView(R.id.tv_topbar_right)
  TextView mRightTv;

  @BindView(R.id.rv_category_1)
  RecyclerView mCategory1Rv;
  @BindView(R.id.rv_category_2)
  RecyclerView mCategory2Rv;

  private BaseQuickAdapter<Category1Item, BaseViewHolder> mCategory1Adapter;
  private BaseQuickAdapter<Category2Item, BaseViewHolder> mCategory2Adapter;
  private BaseQuickAdapter<RecommendProduct, BaseViewHolder> mRecProductAdapter;

  private int mSelectedPos = 0;

  private View recProductView;
  private RecyclerView mRecProductRv;


  private static final String BUNDLE_ARGS = "bundle_args";

  public ClassifyFragment() {
    // Required empty public constructor
  }

  public static ClassifyFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    ClassifyFragment fragment = new ClassifyFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_classify;
  }

  @Override
  protected void initData() {
    mCategory1Rv.setLayoutManager(new LinearLayoutManager(mContext));
    mCategory1Rv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_dc)).build());
    mCategory1Rv.setAdapter(mCategory1Adapter = new BaseQuickAdapter<Category1Item, BaseViewHolder>(R.layout.item_product_category1) {

      @Override
      protected void convert(BaseViewHolder helper, Category1Item item) {
        helper.setText(R.id.item_category1_title_tv, item.getTitle());
        if (mSelectedPos == helper.getLayoutPosition()) {
          helper.setBackgroundRes(R.id.item_category1_title_tv, R.drawable.bg_category1_title);
          helper.setTextColor(R.id.item_category1_title_tv, getResources().getColor(R.color.colorPrimary));
        }else {
          helper.setBackgroundColor(R.id.item_category1_title_tv, getResources().getColor(R.color.color_f4));
          helper.setTextColor(R.id.item_category1_title_tv, getResources().getColor(R.color.color_66));
        }

      }
    });
    mCategory1Adapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        List<Category2Item> item2List = mCategory1Adapter.getItem(position).getList();
        mCategory2Adapter.setNewData(item2List);

        List<RecommendProduct> recProductList = mCategory1Adapter.getItem(position).getRecommend_products();
        if (recProductList.size() == 0) {
          recProductView.setVisibility(View.GONE);
        }else {
          recProductView.setVisibility(View.VISIBLE);
          mRecProductAdapter.setNewData(recProductList);
        }

        mSelectedPos = position;
        mCategory1Adapter.notifyDataSetChanged();
      }
    });
  }

  @Override
  protected void initView() {
    Drawable drawableleft = getResources().getDrawable(R.drawable.nav_scan);
    drawableleft.setBounds(0, 0, drawableleft.getMinimumWidth(), drawableleft.getMinimumHeight());
    mLeftTv.setCompoundDrawables(drawableleft, null, null, null);
    mLeftTv.setVisibility(View.INVISIBLE);
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) loadData();
  }

  private void loadData() {
    OkGo.<String>get(MallAPI.PRODUCT_CATEGORY)
        .tag(this)
        .params("recommend_product", 1)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<Category1Item> item1List = new ArrayList<Category1Item>();
              JSONObject bodyObj = JSON.parseObject(response.body());
              JSONArray itemsArray = bodyObj.getJSONArray("items");
              for (int i = 0; i < itemsArray.size(); i++) {
                Category1Item item1Obj = itemsArray.getObject(i, Category1Item.class);
                item1List.add(item1Obj);
              }
              mCategory1Adapter.setNewData(item1List);


              mCategory2Rv.setLayoutManager(new GridLayoutManager(mContext, 3));
              mCategory2Rv.setAdapter(mCategory2Adapter = new BaseQuickAdapter<Category2Item, BaseViewHolder>(R.layout.item_product_category2,
                  mCategory1Adapter.getItem(0).getList()) {
                @Override
                protected void convert(BaseViewHolder helper, Category2Item item) {
                  helper.setText(R.id.item_category2_title_tv, item.getTitle());
                }
              });

              recProductView = LayoutInflater
                  .from(mContext).inflate(R.layout.view_recommend_product, null);
              mCategory2Adapter.addHeaderView(recProductView);
              mRecProductRv = (RecyclerView) recProductView.findViewById(R.id.rv_recommend_product);
              mRecProductRv.setLayoutManager(new GridLayoutManager(mContext, 3));
              if (mCategory1Adapter.getItem(0).getRecommend_products().size() == 0) {
                recProductView.setVisibility(View.GONE);
              }
              mRecProductRv.setAdapter(mRecProductAdapter = new BaseQuickAdapter<RecommendProduct, BaseViewHolder>(R.layout.item_recommend_product,
                  mCategory1Adapter.getItem(0).getRecommend_products()) {
                @Override
                protected void convert(BaseViewHolder helper, RecommendProduct item) {
                  ImageView iconIv = helper.getView(R.id.item_recommend_img_iv);
                  GlideApp.with(mContext).load(MallAPI.IMG_SERVER + item.getPic()).placeholder(R.drawable.ic_default_image).into(iconIv);
                  helper.setText(R.id.item_recommend_title_tv, item.getTitle());
                }
              });



              mCategory2Adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                  Category2Item category2Item = mCategory2Adapter.getItem(position);
                  startActivity(new Intent(mContext, ProductListActivity.class)
                      .putExtra("fcategory_id", category2Item.getParent())
                      .putExtra("scategory_id", category2Item.getId())
                  );
                }
              });

              mRecProductAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                  RecommendProduct recProduct = mRecProductAdapter.getItem(position);
                  //ToastUtils.showShortToast(recProduct.getTitle());
                  startActivity(new Intent(mContext, ProductDetailActivity.class)
                      .putExtra("product_sku_id", recProduct.getId()));
                }
              });

            }
          }
        });
  }

  @OnClick(R.id.edt_topbar_title)
  public void onGoSearch() {
    openActivity(new Intent(mContext, SearchHotActivity.class), true);
  }

}
