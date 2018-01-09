package com.wanchang.employee.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanchang.employee.R;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.HomePageItem;
import com.wanchang.employee.ui.base.BaseFragment;
import com.wanchang.employee.util.GlideApp;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeCardFragment extends BaseFragment {

  @BindView(R.id.rv_home)
  RecyclerView mHomeRv;


  public BaseQuickAdapter<HomePageItem, BaseViewHolder> mAdapter;


  public HomeCardFragment() {
    // Required empty public constructor
  }

  public static HomeCardFragment newInstance(String data) {
    Bundle args = new Bundle();
    args.putString("key_data", data);
    HomeCardFragment fragment = new HomeCardFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_home_card;
  }

  @Override
  protected void initData() {
    String key_data = getArguments().getString("key_data");
    List<HomePageItem> data = JSON.parseArray(key_data, HomePageItem.class);

    mHomeRv.setLayoutManager(new GridLayoutManager(mContext, 3));
    mHomeRv.setAdapter(mAdapter = new BaseQuickAdapter<HomePageItem, BaseViewHolder>(R.layout.item_home_list, data) {
      @Override
      protected void convert(BaseViewHolder helper, HomePageItem item) {
        ImageView mPic = helper.getView(R.id.iv_pic);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER + item.getPic()).placeholder(R.drawable.ic_default_image).into(mPic);
        helper.setText(R.id.tv_title, item.getTitle());
        String validity = item.getProduct_sku().getValidity();
        if (validity.startsWith("0")) {
          helper.setVisible(R.id.tv_validity, false);
        } else {
          helper.setVisible(R.id.tv_validity, true);
        }
        //helper.setText(R.id.tv_validity, "有效期至:"+item.getProduct_sku().getValidity());
        helper.setText(R.id.tv_validity, item.getProduct_sku().getSpecs());
        helper.setText(R.id.tv_price, "".equals(MallApp.getInstance().getToken()) ? "登录可见" : "¥"+item.getPrice());
        if ("".equals(MallApp.getInstance().getToken())) {
          helper.setText(R.id.tv_price, "登录可见");
        } else {
          if (item.getPrice() == 0) {
            helper.setText(R.id.tv_price, "暂无销售");
          } else {
            helper.setText(R.id.tv_price, "¥"+item.getPrice());
          }
        }
      }
    });
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HomePageItem homePageItem = mAdapter.getItem(position);
        MallApp.getInstance().cmdNavigation(mContext, homePageItem.getCmd());
      }
    });
  }

  @Override
  protected void initView() {
  }

}
