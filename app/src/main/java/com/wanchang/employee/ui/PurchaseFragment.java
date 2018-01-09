package com.wanchang.employee.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sunfusheng.marqueeview.MarqueeView;
import com.sunfusheng.marqueeview.MarqueeView.OnItemClickListener;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.HomePageItem;
import com.wanchang.employee.data.entity.Promotion;
import com.wanchang.employee.data.entity.TabEntity;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.ui.home.ControlProductListActivity;
import com.wanchang.employee.ui.home.GetCouponListActivity;
import com.wanchang.employee.ui.home.HomeCardFragment;
import com.wanchang.employee.ui.home.HotSalesListActivity;
import com.wanchang.employee.ui.home.NewProductListActivity;
import com.wanchang.employee.ui.home.PromotionListActivity;
import com.wanchang.employee.ui.home.SearchHotActivity;
import com.wanchang.employee.ui.push.ArticleDetailActivity;
import com.wanchang.employee.ui.salesman.home.SelectClientActivity;
import com.wanchang.employee.util.ACache;
import com.wanchang.employee.util.GlideApp;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseFragment extends BaseFragment {

  @BindView(R.id.topbar_10)
  Toolbar mToolbar10;

  @BindView(R.id.topbar_20)
  Toolbar mToolbar20;
  @BindView(R.id.tv_topbar_title)
  TextView mClientNameTv;


  @BindView(R.id.refreshLayout)
  RefreshLayout refreshLayout;

  @BindView(R.id.banner_slider)
  Banner mSliderBanner;

  @BindView(R.id.marqueeView)
  MarqueeView marqueeView;

  @BindView(R.id.iv_recommend_product_1)
  ImageView mRecommend1Iv;
  @BindView(R.id.iv_recommend_product_2)
  ImageView mRecommend2Iv;
  @BindView(R.id.iv_recommend_product_3)
  ImageView mRecommend3Iv;
  @BindView(R.id.iv_recommend_product_4)
  ImageView mRecommend4Iv;

  @BindView(R.id.banner_recommend_promotion)
  Banner mPromotionBanner;

  @BindView(R.id.iv_hot)
  ImageView mHotIv;
  @BindView(R.id.tl_hot)
  CommonTabLayout mHotTabLayout;
  private String[] mHotTitles = {"销量排行", "热销专区", "厂家推荐"};


  @BindView(R.id.iv_sale)
  ImageView mSaleIv;
  @BindView(R.id.tl_sale)
  CommonTabLayout mSaleTabLayout;
  private String[] mSaleTitles = {"优惠满减", "打折促销", "买就送"};

  @BindView(R.id.iv_new)
  ImageView mNewIv;
  @BindView(R.id.tl_new)
  CommonTabLayout mNewTabLayout;
  private String[] mNewTitles = {"销量排行", "热销专区", "厂家推荐"};


  @BindView(R.id.banner_ad_mid)
  Banner mAdMidBanner;


  @BindView(R.id.rv_product)
  RecyclerView mProductRv;

  public BaseQuickAdapter<HomePageItem, BaseViewHolder> mProductAdapter;


  //protected ImmersionBar mImmersionBar;

  private static final String BUNDLE_ARGS = "bundle_args";

  public PurchaseFragment() {
    // Required empty public constructor
  }

  public static PurchaseFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    PurchaseFragment fragment = new PurchaseFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_purchase;
  }


  @Override
  protected void initData() {
    mProductRv.setNestedScrollingEnabled(false);
    mProductRv.setLayoutManager(new LinearLayoutManager(mContext));
    mProductRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).build());
    mProductRv.setAdapter(mProductAdapter = new BaseQuickAdapter<HomePageItem, BaseViewHolder>(R.layout.item_product_list) {
      @Override
      protected void convert(BaseViewHolder helper, final HomePageItem item) {
        ImageView productIv = helper.getView(R.id.iv_product_img);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER+item.getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
        helper.setText(R.id.tv_product_title, item.getTitle());
        helper.setText(R.id.tv_product_manufacture, item.getProduct_sku()!=null ? item.getProduct_sku().getManufacture_name():"");
        String validity = item.getProduct_sku()!=null ? item.getProduct_sku().getValidity() : "";
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
        //oPriceTv.setText("0.00".equals(item.getOprice()) ? "登录可见" : "¥"+item.getOprice());
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

        final AnimShopButton animShopBtn = helper.getView(R.id.btn_product_number);
        animShopBtn.setCount(0);
        animShopBtn.setOnAddDelListener(new IOnAddDelListener() {
          @Override
          public void onAddSuccess(int i) {
            int realCount = animShopBtn.getCount() - 1;
            animShopBtn.setCount(realCount);
            AddShoppingCart(item.getProduct_sku().getId(), item.getProduct_sku().getPackaing());
          }

          @Override
          public void onAddFailed(int i, FailType failType) {

          }

          @Override
          public void onDelSuccess(int i) {

          }

          @Override
          public void onDelFaild(int i, FailType failType) {

          }
        });
      }
    });
    mProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HomePageItem homePageItem = mProductAdapter.getItem(position);
        MallApp.getInstance().cmdNavigation(mContext, homePageItem.getCmd());
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
              ToastUtils.showShort("已加入购物车");
            }
          }
        });
  }


  @Override
  protected void initView() {

    refreshLayout.setEnableLoadmore(false);
    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh(RefreshLayout refreshlayout) {
        getIndexList();
      }
    });

    mSliderBanner.setOnBannerListener(new OnBannerListener() {
      @Override
      public void OnBannerClick(int position) {
        String cmd = sliderCmds.get(position);
        MallApp.getInstance().cmdNavigation(mContext, cmd);
      }
    });

    marqueeView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(int position, TextView textView) {
        String id = articleCmds.get(position).split("/")[1];
        openActivity(new Intent(mContext, ArticleDetailActivity.class).putExtra("id", id), true);
      }
    });

    mPromotionBanner.setOnBannerListener(new OnBannerListener() {
      @Override
      public void OnBannerClick(int position) {
        String cmd = promotionCmds.get(position);
        MallApp.getInstance().cmdNavigation(mContext, cmd);
      }
    });

    mAdMidBanner.setOnBannerListener(new OnBannerListener() {
      @Override
      public void OnBannerClick(int position) {
        String cmd = adMidCmds.get(position);
        MallApp.getInstance().cmdNavigation(mContext, cmd);
      }
    });

    //initImmersionBar();
  }

  /**
   * 初始化沉浸式
   */
  protected void initImmersionBar() {
    //mImmersionBar = ImmersionBar.with(this);
    //mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).titleBar(mToolbar10).init();
  }

  @OnClick(R.id.ll_recommend_product_1)
  public void onGoProduct1() {
    MallApp.getInstance().cmdNavigation(mContext, recommend_product_1_cmd);
  }
  @OnClick(R.id.ll_recommend_product_2)
  public void onGoProduct2() {
    MallApp.getInstance().cmdNavigation(mContext, recommend_product_2_cmd);
  }
  @OnClick(R.id.ll_recommend_product_3)
  public void onGoProduct3() {
    MallApp.getInstance().cmdNavigation(mContext, recommend_product_3_cmd);
  }
  @OnClick(R.id.ll_recommend_product_4)
  public void onGoProduct4() {
    MallApp.getInstance().cmdNavigation(mContext, recommend_product_4_cmd);
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) getIndexList();

//    if (!hidden && mImmersionBar != null)
//      mImmersionBar.init();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
//    if (mImmersionBar != null)
//      mImmersionBar.destroy();
  }

  /**
   * refresh ui
   */
  public void refresh() {
    getIndexList();
  }

  private List<String> sliderCmds;
  private List<String> articleCmds;
  private String recommend_product_1_cmd;
  private String recommend_product_2_cmd;
  private String recommend_product_3_cmd;
  private String recommend_product_4_cmd;
  private List<String> promotionCmds;
  private List<String> adMidCmds;

  private void getIndexList() {
    OkGo.<String>get(MallAPI.INDEX_LIST)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              refreshLayout.finishRefresh();
              JSONObject jsonObj = JSON.parseObject(response.body());
              //client_info
              JSONObject clientObj = JSON.parseObject(jsonObj.getString("client_info"));
              if (MallApp.getInstance().getGroupId() == Constants.GROUP_CLIENT) {
                mToolbar10.setVisibility(View.VISIBLE);
                mToolbar20.setVisibility(View.GONE);
              } else {
                mToolbar10.setVisibility(View.GONE);
                mToolbar20.setVisibility(View.VISIBLE);
              }
              String client_id = ACache.get(mContext).getAsString(Constants.KEY_CLIENT_ID);
              if (TextUtils.isEmpty(client_id) || "0".equals(client_id)) {
                String clientId = clientObj.getString("id");
                String clientName = clientObj.getString("name");
                mClientNameTv.setText(clientName);
                ACache.get(mContext).put(Constants.KEY_CLIENT_ID, clientId);
                ACache.get(mContext).put(Constants.KEY_CLIENT_NAME, clientName);
              } else {
                String client_name = ACache.get(mContext).getAsString(Constants.KEY_CLIENT_NAME);
                mClientNameTv.setText(client_name);
              }
              //slider
              JSONObject sliderObj = JSON.parseObject(jsonObj.getString("slider"));
              List<HomePageItem> sliderItems = JSON.parseArray(sliderObj.getString("item_list"), HomePageItem.class);
              List<String> imageUrls = new ArrayList<>();
              sliderCmds = new ArrayList<>();
              for (HomePageItem item : sliderItems) {
                imageUrls.add(MallAPI.IMG_SERVER + item.getPic());
                sliderCmds.add(item.getCmd());
              }
              mSliderBanner.setImages(imageUrls).setImageLoader(new GlideImageLoader()).start();
              //article_list
              JSONObject articleObj = JSON.parseObject(jsonObj.getString("article_list"));
              List<HomePageItem> articleItems = JSON.parseArray(articleObj.getString("item_list"), HomePageItem.class);
              List<String> info = new ArrayList<>();
              articleCmds = new ArrayList<>();
              for (HomePageItem item : articleItems) {
                info.add(item.getTitle());
                articleCmds.add(item.getCmd());
              }
              marqueeView.startWithList(info);
              //recommend_product
              JSONObject productObj = JSON.parseObject(jsonObj.getString("recommend_product"));
              List<HomePageItem> recommendProductItems = JSON.parseArray(productObj.getString("item_list"), HomePageItem.class);
              if (recommendProductItems.size() >= 4) {
                GlideApp.with(mContext).load(MallAPI.IMG_SERVER+recommendProductItems.get(0).getPic())
                    .placeholder(R.drawable.ic_default_image).into(mRecommend1Iv);
                recommend_product_1_cmd = recommendProductItems.get(0).getCmd();
                GlideApp.with(mContext).load(MallAPI.IMG_SERVER+recommendProductItems.get(1).getPic())
                    .placeholder(R.drawable.ic_default_image).into(mRecommend2Iv);
                recommend_product_2_cmd = recommendProductItems.get(1).getCmd();
                GlideApp.with(mContext).load(MallAPI.IMG_SERVER+recommendProductItems.get(2).getPic())
                    .placeholder(R.drawable.ic_default_image).into(mRecommend3Iv);
                recommend_product_3_cmd = recommendProductItems.get(2).getCmd();
                GlideApp.with(mContext).load(MallAPI.IMG_SERVER+recommendProductItems.get(3).getPic())
                    .placeholder(R.drawable.ic_default_image).into(mRecommend4Iv);
                recommend_product_4_cmd = recommendProductItems.get(3).getCmd();
              }
              //recommend_promotion
              JSONObject recommendPromotionObj = JSON.parseObject(jsonObj.getString("recommend_promotion"));
              List<HomePageItem> recommendPromotionItems = JSON.parseArray(recommendPromotionObj.getString("item_list"), HomePageItem.class);
              List<String> promotionImageUrls = new ArrayList<>();
              promotionCmds = new ArrayList<>();
              for (HomePageItem item : recommendPromotionItems) {
                promotionImageUrls.add(MallAPI.IMG_SERVER + item.getPic());
                promotionCmds.add(item.getCmd());
              }
              mPromotionBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
              mPromotionBanner.setImages(promotionImageUrls).setImageLoader(new GlideImageLoader()).start();
              //f1
              JSONObject f1SaleObj = JSON.parseObject(jsonObj.getString("f1_sale"));
              GlideApp.with(mContext).load(MallAPI.IMG_SERVER+f1SaleObj.getString("pic"))
                  .placeholder(R.drawable.hotsell_banner).into(mHotIv);
              List<HomePageItem> f1SaleItems = JSON.parseArray(f1SaleObj.getString("item_list"), HomePageItem.class);
              JSONObject f1HotObj = JSON.parseObject(jsonObj.getString("f1_hot"));
              List<HomePageItem> f1HotItems = JSON.parseArray(f1HotObj.getString("item_list"), HomePageItem.class);
              JSONObject f1ManuObj = JSON.parseObject(jsonObj.getString("f1_manufacture"));
              List<HomePageItem> f1ManuItems = JSON.parseArray(f1ManuObj.getString("item_list"), HomePageItem.class);
              //mHotTabEntities.clear();
              //mHotFragments.clear();
              ArrayList<CustomTabEntity> mHotTabEntities = new ArrayList<>();
              ArrayList<Fragment> mHotFragments = new ArrayList<>();
              for (int i = 0; i < mHotTitles.length; i++) {
                mHotTabEntities.add(new TabEntity(mHotTitles[i], 0, 0));
                if (i == 0) {
                  mHotFragments.add(HomeCardFragment.newInstance(f1SaleObj.getString("item_list")));
                } else if (i == 1){
                  mHotFragments.add(HomeCardFragment.newInstance(f1HotObj.getString("item_list")));
                } else {
                  mHotFragments.add(HomeCardFragment.newInstance(f1ManuObj.getString("item_list")));
                }
              }
              mHotTabLayout.setTabData(mHotTabEntities, mContext, R.id.fl_change_hot, mHotFragments);
              //f2
              JSONObject f2CutObj = JSON.parseObject(jsonObj.getString("f2_cut"));
              GlideApp.with(mContext).load(MallAPI.IMG_SERVER+f2CutObj.getString("pic"))
                  .placeholder(R.drawable.sales_banner).into(mSaleIv);
              List<HomePageItem> f2CutItems = JSON.parseArray(f2CutObj.getString("item_list"), HomePageItem.class);
              JSONObject f2DiscountObj = JSON.parseObject(jsonObj.getString("f2_discount"));
              List<HomePageItem> f2DiscountItems = JSON.parseArray(f2DiscountObj.getString("item_list"), HomePageItem.class);
              JSONObject f2GiftObj = JSON.parseObject(jsonObj.getString("f2_gift"));
              List<HomePageItem> f2GiftItems = JSON.parseArray(f2GiftObj.getString("item_list"), HomePageItem.class);
              //mSaleTabEntities.clear();
              //mSaleFragments.clear();
              ArrayList<CustomTabEntity> mSaleTabEntities = new ArrayList<>();
              ArrayList<Fragment> mSaleFragments = new ArrayList<>();
              for (int i = 0; i < mSaleTitles.length; i++) {
                mSaleTabEntities.add(new TabEntity(mSaleTitles[i], 0, 0));
                if (i == 0) {
                  mSaleFragments.add(HomeCardFragment.newInstance(f2CutObj.getString("item_list")));
                } else if (i == 1){
                  mSaleFragments.add(HomeCardFragment.newInstance(f2DiscountObj.getString("item_list")));
                } else {
                  mSaleFragments.add(HomeCardFragment.newInstance(f2GiftObj.getString("item_list")));
                }
              }
              mSaleTabLayout.setTabData(mSaleTabEntities, mContext, R.id.fl_change_sale, mSaleFragments);
              //f3
              JSONObject f3SaleObj = JSON.parseObject(jsonObj.getString("f3_sale"));
              GlideApp.with(mContext).load(MallAPI.IMG_SERVER+f3SaleObj.getString("pic"))
                  .placeholder(R.drawable.new_banner).into(mNewIv);
              List<HomePageItem> f3SaleItems = JSON.parseArray(f3SaleObj.getString("item_list"), HomePageItem.class);
              JSONObject f3HotObj = JSON.parseObject(jsonObj.getString("f3_hot"));
              List<HomePageItem> f3HotItems = JSON.parseArray(f3HotObj.getString("item_list"), HomePageItem.class);
              JSONObject f3ManuObj = JSON.parseObject(jsonObj.getString("f3_manufacture"));
              List<HomePageItem> f3ManuItems = JSON.parseArray(f3ManuObj.getString("item_list"), HomePageItem.class);
              //mNewTabEntities.clear();
              //mNewFragments.clear();
              ArrayList<CustomTabEntity> mNewTabEntities = new ArrayList<>();
              ArrayList<Fragment> mNewFragments = new ArrayList<>();
              for (int i = 0; i < mNewTitles.length; i++) {
                mNewTabEntities.add(new TabEntity(mNewTitles[i], 0, 0));
                if (i == 0) {
                  mNewFragments.add(HomeCardFragment.newInstance(f3SaleObj.getString("item_list")));
                } else if (i == 1){
                  mNewFragments.add(HomeCardFragment.newInstance(f3HotObj.getString("item_list")));
                } else {
                  mNewFragments.add(HomeCardFragment.newInstance(f3ManuObj.getString("item_list")));
                }
              }
              mNewTabLayout.setTabData(mNewTabEntities, mContext, R.id.fl_change_new, mNewFragments);
              //ad_mid
              JSONObject adMidObj = JSON.parseObject(jsonObj.getString("ad_mid"));
              List<HomePageItem> adMidItems = JSON.parseArray(adMidObj.getString("item_list"), HomePageItem.class);
              List<String> adImageUrls = new ArrayList<>();
              adMidCmds = new ArrayList<>();
              for (HomePageItem item : adMidItems) {
                adImageUrls.add(MallAPI.IMG_SERVER + item.getPic());
                adMidCmds.add(item.getCmd());
              }
              mAdMidBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
              mAdMidBanner.setImages(adImageUrls).setImageLoader(new GlideImageLoader()).start();
              //product_list
              JSONObject productListObj = JSON.parseObject(jsonObj.getString("product_list"));
              List<HomePageItem> productItems = JSON.parseArray(productListObj.getString("item_list"), HomePageItem.class);
              mProductAdapter.setNewData(productItems);
            }
          }
        });
  }

  private static class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
      GlideApp.with(context).load(path).centerCrop().placeholder(R.drawable.ic_default_image)
          .into(imageView);
    }
  }

  @OnClick(R.id.tv_topbar_title)
  public void onClientList() {
    openActivity(new Intent(mContext, SelectClientActivity.class), true);
  }

  @OnClick(R.id.edt_topbar_title)
  public void onGoSearch() {
    openActivity(new Intent(mContext, SearchHotActivity.class), true);
  }

  @OnClick(R.id.iv_topbar_left)
  public void openScan() {
    startActivity(new Intent(mContext, CaptureActivity.class));
  }

  @OnClick(R.id.ll_promotion)
  public void onPromotion() {
    openActivity(new Intent(mContext, PromotionListActivity.class), true);
  }

  @OnClick(R.id.ll_new_product)
  public void onNewProduct() {
    openActivity(new Intent(mContext, NewProductListActivity.class), true);
  }

  @OnClick(R.id.ll_get_coupon)
  public void onGetCoupon() {
    openActivity(new Intent(mContext, GetCouponListActivity.class), true);
  }

  @OnClick(R.id.ll_control_product)
  public void onControlProduct() {
    openActivity(new Intent(mContext, ControlProductListActivity.class), true);
  }


  @OnClick(R.id.iv_hot)
  public void onHot() {
    openActivity(new Intent(mContext, HotSalesListActivity.class), true);
  }

  @OnClick(R.id.iv_sale)
  public void onSale() {
    openActivity(new Intent(mContext, PromotionListActivity.class), true);
  }

  @OnClick(R.id.iv_new)
  public void onNew() {
    openActivity(new Intent(mContext, NewProductListActivity.class), true);
  }

}
