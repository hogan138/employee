package com.wanchang.employee.ui.classify;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.blankj.utilcode.util.LogUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wanchang.employee.R;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.entity.TabEntity;
import com.wanchang.employee.ui.LoginActivity;
import com.wanchang.employee.ui.MainActivity;
import com.wanchang.employee.ui.base.BaseActivity;
import com.wanchang.employee.ui.classify.ProductFilterFragment.CallBackValue;
import com.wanchang.employee.ui.home.SearchHotActivity;
import java.util.ArrayList;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ProductListActivity extends BaseActivity implements CallBackValue {

  @BindView(R.id.tv_topbar_left)
  TextView mLeftTv;
  @BindView(R.id.edt_topbar_title)
  TextView mTitleEdt;
  @BindView(R.id.iv_cart)
  ImageView mCartIv;
  @BindView(R.id.tl)
  CommonTabLayout mTabLayout;
  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;
  @BindView(R.id.drawer_content)
  FrameLayout mDrawerContent;
  @BindView(R.id.tv_filter_list)
  TextView mFilterListTv;


  private String[] mTitles = {"综合", "销量", "人气"};
  private int[] mIconUnselectIds = {0, R.drawable.updown_d, R.drawable.updown_d};
  private int[] mIconSelectIds = {0, R.drawable.updown_down, R.drawable.updown_down};
  private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
  private ArrayList<Fragment> mFragments = new ArrayList<>();


  private int fcategory_id;
  private int scategory_id;
  private String keyword;

  private Badge mQBadgeView;

  private ProductCardFragment mProductCard0Fragment;
  private ProductCardFragment mProductCard1Fragment;
  private ProductCardFragment mProductCard2Fragment;
  //private ProductCardFragment mProductCard3Fragment;

  private int pagePosition = 0;
  private boolean isClick;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_product_list;
  }

  @Override
  protected void initData() {
    fcategory_id = getIntent().getIntExtra("fcategory_id", -1);
    scategory_id = getIntent().getIntExtra("scategory_id", -1);

    keyword = getIntent().getStringExtra("keyword");
    if (!TextUtils.isEmpty(keyword)) {
      mTitleEdt.setText(keyword);
    } else {
      mTitleEdt.setText("");
    }

    mQBadgeView = new QBadgeView(mContext).bindTarget(mCartIv);
    mQBadgeView.setExactMode(true);


    for (int i = 0; i < mTitles.length; i++) {
      mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
      if (i == 0) {
        mFragments.add(mProductCard0Fragment = ProductCardFragment.newInstance(mQBadgeView, fcategory_id, scategory_id, keyword));
      } else if (i == 1){
        mFragments.add(mProductCard1Fragment = ProductCardFragment.newInstance(mQBadgeView, fcategory_id, scategory_id, keyword));
      } else {
        mFragments.add(mProductCard2Fragment = ProductCardFragment.newInstance(mQBadgeView, fcategory_id, scategory_id, keyword));
      }

    }
    mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);

    mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
      @Override
      public void onTabSelect(int position) {
        pagePosition = position;
        if (position == 1) {
          mProductCard1Fragment.setOrderBy("sales desc");
        } else if(position == 2) {
          mProductCard2Fragment.setOrderBy("views desc");
        }
        isClick = false;
      }

      @Override
      public void onTabReselect(int position) {
        pagePosition = position;

        ImageView iv = mTabLayout.getIconView(position);
        if (!isClick) {
          iv.setImageResource(R.drawable.updown_up);
          isClick = true;
          if (position == 0) {
            iv.setImageResource(0);
            mProductCard0Fragment.setOrderBy("");
          } else if (position == 1) {
            mProductCard1Fragment.setOrderBy("sales asc");
          } else if(position == 2) {
            mProductCard2Fragment.setOrderBy("views asc");
          }
        } else {
          iv.setImageResource(R.drawable.updown_down);
          isClick = false;
          if (position == 0) {
            iv.setImageResource(0);
            mProductCard0Fragment.setOrderBy("");
          } else if (position == 1) {
            mProductCard1Fragment.setOrderBy("sales desc");
          } else if(position == 2) {
            mProductCard2Fragment.setOrderBy("views desc");
          }
        }

      }
    });
  }



  @Override
  protected void initView() {

  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    keyword = intent.getStringExtra("keyword");
    if (!TextUtils.isEmpty(keyword)) {
      mTitleEdt.setText(keyword);
    } else {
      mTitleEdt.setText("");
    }
    ((ProductCardFragment)mFragments.get(pagePosition)).setKeyword(keyword);
  }

  ProductFilterFragment filterFragment = null;

  @OnClick(R.id.tv_filter_list)
  public void onFilterList() {
    mDrawerLayout.openDrawer(mDrawerContent);
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    if (filterFragment == null) {
      filterFragment = ProductFilterFragment.newInstance();
      fragmentTransaction.add(R.id.drawer_content, filterFragment);
    } else {
      fragmentTransaction.show(filterFragment);
    }
    fragmentTransaction.commit();
  }

  @OnClick(R.id.iv_cart)
  public void onOpenCart() {
    if (MallApp.getInstance().isLogin()) {
      startActivity(new Intent(mContext, MainActivity.class).putExtra("go_cart", true));
    } else {
      startActivity(new Intent(mContext, LoginActivity.class));
    }
  }


  @Override
  public void onBackPressed() {
    LogUtils.e("11111111"+getSupportFragmentManager().getBackStackEntryCount());
    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
      boolean drawerState =  mDrawerLayout.isDrawerOpen(mDrawerContent);
      if (drawerState) {
        mDrawerLayout.closeDrawers();
        return;
      }
    }
    super.onBackPressed();
  }


  @Override
  public void sendMessageValue(String tag, String category_dosage, String category_chinese_medicine,
      String category_prescription, String category_insurance, String manufacture_id) {
    if ("".equals(category_dosage)&&"".equals(category_chinese_medicine)
        &&"".equals(category_prescription)&&"".equals(category_insurance)&&"".equals(manufacture_id)) {
      mFilterListTv.setTextColor(getResources().getColor(R.color.color_33));
    }else {
      mFilterListTv.setTextColor(getResources().getColor(R.color.color_336));
    }
    ((ProductCardFragment)mFragments.get(pagePosition)).setFilter(tag, category_dosage,
        category_chinese_medicine, category_prescription, category_insurance, manufacture_id);
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }

  @OnClick(R.id.edt_topbar_title)
  public void onGoSearch() {
    openActivity(new Intent(mContext, SearchHotActivity.class).putExtra("keyword", keyword), true);
  }
}
