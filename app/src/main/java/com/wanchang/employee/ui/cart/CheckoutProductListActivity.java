package com.wanchang.employee.ui.cart;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.wanchang.employee.R;
import com.wanchang.employee.adapter.SectionCheckoutAdapter;
import com.wanchang.employee.data.entity.CartSection;
import com.wanchang.employee.data.entity.Cart_DataList;
import com.wanchang.employee.data.entity.CheckOut;
import com.wanchang.employee.data.entity.ShoppingCart;
import com.wanchang.employee.ui.base.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.ArrayList;
import java.util.List;

public class CheckoutProductListActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.rv_shopping_cart)
  RecyclerView mCartRv;

  private SectionCheckoutAdapter sectionCartAdapter;

  private String response;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_checkout_product_list;
  }

  @Override
  protected void initData() {
    response = getIntent().getStringExtra("response");

    mCartRv.setLayoutManager(new LinearLayoutManager(mContext));
    mCartRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
        getResources().getColor(R.color.color_f3)).build());
    sectionCartAdapter = new SectionCheckoutAdapter(R.layout.item_checkout_section_content, R.layout.def_checkout_section_head, null);
    mCartRv.setAdapter(sectionCartAdapter);



  }

  @Override
  protected void initView() {
    mTitleTv.setText("商品清单");

    handResult(response);
  }


  private void handResult(String response) {
    CheckOut checkout = JSON.parseObject(response, CheckOut.class);
    ShoppingCart shoppingCart = checkout.getShopping_cart();
    sectionCartAdapter.setNewData(getData(shoppingCart));

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

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
