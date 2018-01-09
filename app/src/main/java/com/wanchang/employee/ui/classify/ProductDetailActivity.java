package com.wanchang.employee.ui.classify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Coupon;
import com.wanchang.employee.data.entity.ProductDetail;
import com.wanchang.employee.data.entity.Promotion;
import com.wanchang.employee.data.entity.TabEntity;
import com.wanchang.employee.ui.MainActivity;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.ui.push.PromotionDetailActivity;
import com.wanchang.employee.util.GlideApp;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.ArrayList;
import java.util.List;
import me.shaohui.bottomdialog.BaseBottomDialog;
import me.shaohui.bottomdialog.BottomDialog;
import me.shaohui.bottomdialog.BottomDialog.ViewListener;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ProductDetailActivity extends BaseActivity {

  @BindView(R.id.tv_topbar_title)
  TextView mTopBarTitleTv;
  @BindView(R.id.banner)
  Banner mBanner;

  @BindView(R.id.tv_title)
  TextView mTitleTv;
  @BindView(R.id.tv_manufacture_spec)
  TextView mManufactureSpecTv;
  @BindView(R.id.tv_validity)
  TextView mValidityTv;
  @BindView(R.id.tv_price)
  TextView mPriceTv;
  @BindView(R.id.tv_oprice)
  TextView mOpriceTv;

  @BindView(R.id.rl_coupon)
  RelativeLayout mCouponRl;
  @BindView(R.id.tv_coupon_1)
  TextView mCoupon1Tv;
  @BindView(R.id.tv_coupon_2)
  TextView mCoupon2Tv;

  @BindView(R.id.rl_promotion)
  RelativeLayout mPromotionRl;
  @BindView(R.id.tv_promotion_category_1)
  TextView mPromotionCategory1Tv;
  @BindView(R.id.tv_promotion_title_1)
  TextView mPromotionTitle1Tv;
  @BindView(R.id.tv_promotion_category_2)
  TextView mPromotionCategory2Tv;
  @BindView(R.id.tv_promotion_title_2)
  TextView mPromotionTitle2Tv;


  @BindView(R.id.tl)
  CommonTabLayout mTabLayout;

  @BindView(R.id.iv_cart)
  ImageView mCartIconIv;
  @BindView(R.id.iv_collection)
  ImageView mCollectionIv;

  private String[] mTitles = {"规格参数", "图文详情"};
  private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
  private ArrayList<Fragment> mFragments = new ArrayList<>();

  private Badge mQBadgeView;

  private int product_sku_id;


  private ProductDetail productDetail;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_product_detail;
  }

  @Override
  protected void initData() {
    product_sku_id = getIntent().getIntExtra("product_sku_id", -1);
  }

  @Override
  protected void initView() {
    mTopBarTitleTv.setText("商品详情");
    mQBadgeView = new QBadgeView(mContext).bindTarget(mCartIconIv);
    mQBadgeView.setExactMode(true);
  }

  @Override
  protected void onResume() {
    super.onResume();

    loadData();
  }

  private void loadData() {
    OkGo.<String>get(MallAPI.PRODUCT_VIEW)
        .tag(this)
        .params("product_sku_id", product_sku_id)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              productDetail = JSON.parseObject(response.body(), ProductDetail.class);
              List<String> imageUrls = new ArrayList<>();
              imageUrls.add(MallAPI.IMG_SERVER + productDetail.getProduct().getPic());
              mBanner.setImages(imageUrls).setImageLoader(new GlideImageLoader()).isAutoPlay(false).start();
              mTitleTv.setText(productDetail.getProduct().getTitle());
              mManufactureSpecTv.setText(productDetail.getProduct().getManufacture_name()+" | 有效期至："+productDetail.getProduct_sku().getValidity());
              mValidityTv.setText("规格："+productDetail.getProduct().getSpecs());
              if (productDetail.getPrice() != null) {
                if ("".equals(MallApp.getInstance().getToken())) {
                  mPriceTv.setText("登录可见");
                } else {
                  if ("0".equals(productDetail.getPrice().getPrice())||"0.00".equals(productDetail.getPrice().getPrice())) {
                    mPriceTv.setText("暂无销售");
                  } else {
                    mPriceTv.setText("¥"+productDetail.getPrice().getPrice());
                  }
                }
                mOpriceTv.setText("￥"+productDetail.getPrice().getBonus_price());
                mOpriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
              } else {
                mPriceTv.setText("".equals(MallApp.getInstance().getToken()) ? "登录可见" : "￥0.00");
                mOpriceTv.setText("￥0.00");
                mOpriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
              }

              couponList = productDetail.getCoupon_list();
              if (couponList.size() >= 2) {
                mCouponRl.setVisibility(View.VISIBLE);
                mCoupon1Tv.setText(couponList.get(0).getName());
                mCoupon2Tv.setText(couponList.get(1).getName());
              } else {
                mCouponRl.setVisibility(View.GONE);
              }

              promotionList = productDetail.getPromotion_list();
              if (promotionList.size() >= 2) {
                mPromotionRl.setVisibility(View.VISIBLE);
                if (promotionList.get(0).getCategory() == 10) {
                  mPromotionCategory1Tv.setText(Constants.PROMOTION_CATEGORY_10);
                } else if(promotionList.get(0).getCategory() == 20) {
                  mPromotionCategory1Tv.setText(Constants.PROMOTION_CATEGORY_20);
                } else if(promotionList.get(0).getCategory() == 30) {
                  mPromotionCategory1Tv.setText(Constants.PROMOTION_CATEGORY_30);
                } else if(promotionList.get(0).getCategory() == 40) {
                  mPromotionCategory1Tv.setText(Constants.PROMOTION_CATEGORY_40);
                } else if(promotionList.get(0).getCategory() == 50) {
                  mPromotionCategory1Tv.setText(Constants.PROMOTION_CATEGORY_50);
                } else if(promotionList.get(0).getCategory() == 60) {
                  mPromotionCategory1Tv.setText(Constants.PROMOTION_CATEGORY_60);
                } else if(promotionList.get(0).getCategory() == 70) {
                  mPromotionCategory1Tv.setText(Constants.PROMOTION_CATEGORY_70);
                }
                mPromotionTitle1Tv.setText(promotionList.get(0).getTitle());
                if (promotionList.get(1).getCategory() == 10) {
                  mPromotionCategory2Tv.setText(Constants.PROMOTION_CATEGORY_10);
                } else if(promotionList.get(1).getCategory() == 20) {
                  mPromotionCategory2Tv.setText(Constants.PROMOTION_CATEGORY_20);
                } else if(promotionList.get(1).getCategory() == 30) {
                  mPromotionCategory2Tv.setText(Constants.PROMOTION_CATEGORY_30);
                } else if(promotionList.get(1).getCategory() == 40) {
                  mPromotionCategory2Tv.setText(Constants.PROMOTION_CATEGORY_40);
                } else if(promotionList.get(1).getCategory() == 50) {
                  mPromotionCategory2Tv.setText(Constants.PROMOTION_CATEGORY_50);
                } else if(promotionList.get(1).getCategory() == 60) {
                  mPromotionCategory2Tv.setText(Constants.PROMOTION_CATEGORY_60);
                } else if(promotionList.get(1).getCategory() == 70) {
                  mPromotionCategory2Tv.setText(Constants.PROMOTION_CATEGORY_70);
                }
                mPromotionTitle2Tv.setText(promotionList.get(1).getTitle());
              } else {
                mPromotionRl.setVisibility(View.GONE);
              }


              mFragments.clear();
              mTabEntities.clear();
              for (int i = 0; i < mTitles.length; i++) {
                mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
                if (i == 0) {
                  mFragments.add(SpecsParamsFragment.newInstance(productDetail));
                } else {
                  mFragments.add(ImageTextFragment.newInstance(""));
                }

              }
              mTabLayout.setTabData(mTabEntities, ProductDetailActivity.this, R.id.fl_change, mFragments);

              JSONObject jsonObj = JSON.parseObject(response.body());
              if (jsonObj.getIntValue("is_collection") == 0) {
                mCollectionIv.setImageResource(R.drawable.details_collection);
              } else {
                mCollectionIv.setImageResource(R.drawable.details_collection_s);
              }
            }
          }
        });
  }

  private static class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
      GlideApp.with(context).load(path).placeholder(R.drawable.ic_default_image).into(imageView);
    }
  }


  private List<Coupon> couponList;
  private List<Promotion> promotionList;

  private BaseBottomDialog couponDialog;
  @OnClick(R.id.rl_coupon)
  public void onCouponList() {
    couponDialog = BottomDialog.create(getSupportFragmentManager())
        .setViewListener(new ViewListener() {
          @Override
          public void bindView(View v) {
            initCouponView(v);
          }
        })
        .setHeight(600)
        .setLayoutRes(R.layout.dialog_coupons_layout)      // dialog layout
        .show();
  }


  private void initCouponView(View v) {
    RelativeLayout closeRl = v.findViewById(R.id.rl_close);
    closeRl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        couponDialog.dismiss();
      }
    });
    RecyclerView couponRv = v.findViewById(R.id.rv_coupon);
    final BaseQuickAdapter<Coupon, BaseViewHolder> mCouponAdapter;
    couponRv.setAdapter(mCouponAdapter = new BaseQuickAdapter<Coupon, BaseViewHolder>(R.layout.item_coupon_get_list, couponList) {

      @Override
      protected void convert(BaseViewHolder helper, Coupon item) {
        if (item.getTooked() == 0) {
          helper.setBackgroundRes(R.id.rl_coupon_root, R.drawable.voucher_get_n);
          helper.setText(R.id.tv_get_coupon, "立即领取");
          helper.setTextColor(R.id.tv_get_coupon, getResources().getColor(R.color.color_33));
          helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_33));
        } else {
          helper.setBackgroundRes(R.id.rl_coupon_root, R.drawable.voucher_get_d);
          helper.setText(R.id.tv_get_coupon, "已领取");
          helper.setTextColor(R.id.tv_get_coupon, getResources().getColor(R.color.color_99));
          helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_99));
        }
        helper.setText(R.id.tv_value, item.getValue()+"");
        helper.setText(R.id.tv_condition, "满"+item.getCondition()+"可用");
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_time, item.getStart_at()+"-"+item.getEnd_at());
        helper.setText(R.id.tv_usage, item.getUsage());
      }
    });
    couponRv.setLayoutManager(new LinearLayoutManager(mContext));
    mCouponAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Coupon coupon = mCouponAdapter.getItem(position);
        if (coupon.getTooked() == 0) {
          getCoupon(coupon.getKey(), view);
        } else {
          ToastUtils.showShort("已领取");
        }

      }
    });
  }

  private void getCoupon(String key, final View view) {
    OkGo.<String>get(MallAPI.CLIENT_COUPON_GET)
        .tag(this)
        .params("key", key)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              RelativeLayout rlRoot = view.findViewById(R.id.rl_coupon_root);
              rlRoot.setBackgroundResource(R.drawable.voucher_get_d);
              TextView getCouponTv = view.findViewById(R.id.tv_get_coupon);
              getCouponTv.setText("已领取");
              getCouponTv.setTextColor(getResources().getColor(R.color.color_99));
              TextView nameTv = view.findViewById(R.id.tv_name);
              nameTv.setTextColor(getResources().getColor(R.color.color_99));
            }
          }
        });
  }

  private BaseBottomDialog promotionDialog;
  @OnClick(R.id.rl_promotion)
  public void onPromotion() {
    promotionDialog = BottomDialog.create(getSupportFragmentManager())
        .setViewListener(new ViewListener() {
          @Override
          public void bindView(View v) {
            initPromotionView(v);
          }
        })
        .setHeight(600)
        .setLayoutRes(R.layout.dialog_promotion_layout)      // dialog layout
        .show();
  }


  private void initPromotionView(View v) {
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
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_10);
        } else if(item.getCategory() == 20) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_20);
        } else if(item.getCategory() == 30) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_30);
        } else if(item.getCategory() == 40) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_40);
        } else if(item.getCategory() == 50) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_50);
        } else if(item.getCategory() == 60) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_60);
        } else if(item.getCategory() == 70) {
          helper.setText(R.id.tv_promotion_category, Constants.PROMOTION_CATEGORY_70);
        }
        helper.setText(R.id.tv_promotion_title, item.getTitle());
      }
    });
    promotionRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_e5)).margin(32).build());
    promotionRv.setLayoutManager(new LinearLayoutManager(mContext));
    mPromotionAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String key = mPromotionAdapter.getItem(position).getKey();
        startActivity(new Intent(mContext, PromotionDetailActivity.class).putExtra("key", key));
        promotionDialog.dismiss();
      }
    });
  }


  @OnClick(R.id.iv_cart)
  public void onGoCart() {
    openActivity(new Intent(mContext, MainActivity.class).putExtra("go_cart", true), true);
  }

  @OnClick(R.id.iv_collection)
  public void onAddCollection() {
    OkGo.<String>post(MallAPI.COLLECTION)
        .tag(this)
        .params("data_id", product_sku_id)
        .params("data_type", 1)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200 || response.code() == 201) {
              loadData();
            }
          }
        });
  }

  @OnClick(R.id.tv_add_cart)
  public void onAddCart() {
    AddShoppingCart(productDetail.getProduct().getPackaing());
  }

  private void AddShoppingCart(int packaing) {
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
            }
          }
        });
  }



  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }

}
