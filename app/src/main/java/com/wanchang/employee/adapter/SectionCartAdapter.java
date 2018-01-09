package com.wanchang.employee.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.CartSection;
import com.wanchang.employee.data.entity.Cart_ItemList;
import com.wanchang.employee.util.ACache;
import com.wanchang.employee.util.GlideApp;
import java.util.List;

/**
 * Created by stormlei on 2017/7/4.
 */

public class SectionCartAdapter extends BaseSectionQuickAdapter<CartSection, BaseViewHolder> {

  private CountAddDelListener mCountAddDelListener;
  private int cartMode = 0;
  private boolean[] itemStatus;

  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param layoutResId The layout resource id of each item.
   * @param sectionHeadResId The section head layout id for each item
   * @param data A new list is created out of this one to avoid mutable list
   */
  public SectionCartAdapter(int layoutResId, int sectionHeadResId,
      List<CartSection> data) {
    super(layoutResId, sectionHeadResId, data);

    itemStatus = new boolean[100];
  }

  public boolean[] getItemStatus() {
    return itemStatus;
  }

  /**
   * 购物车模式变换
   * @param cartMode
   */
  public void setCartMode(int cartMode) {
    this.cartMode = cartMode;
    if (cartMode == 1) {
      notifyDataSetChanged();
    }

  }

  /**
   * 购物车项状态保持
   * @param cb
   * @param position
   */
  public void setCheckPos(CheckBox cb, int position) {
    itemStatus[position] = cb.isChecked();
  }


  @Override
  protected void convertHead(BaseViewHolder helper, CartSection item) {
    SuperTextView superTextView = helper.getView(R.id.stv_promotion_category);
    TextView tempTv = helper.getView(R.id.tv_temp);
    if (item.getType() == 10) {
      superTextView.setVisibility(View.VISIBLE);
      superTextView.setLeftString(item.header);
      tempTv.setVisibility(View.GONE);
    } else {
      superTextView.setVisibility(View.GONE);
      tempTv.setVisibility(View.VISIBLE);
    }
//    if (10 == item.getType()) {
//      superTextView.setLeftIcon(mContext.getResources().getDrawable(R.drawable.icon_product_gift));
//    } else if(20 == item.getType()) {
//      superTextView.setLeftIcon(mContext.getResources().getDrawable(R.drawable.icon_product_discount));
//    } else {
//      superTextView.setLeftIcon(mContext.getResources().getDrawable(R.drawable.icon_product_reduct));
//    }
    helper.addOnClickListener(R.id.stv_promotion_category);
  }

  @Override
  protected void convert(final BaseViewHolder helper, CartSection item) {
    Cart_ItemList itemList = item.t;
    if (cartMode == 1) {
      helper.setChecked(R.id.cb_check, itemStatus[helper.getLayoutPosition()-1]).addOnClickListener(R.id.cb_check);
    } else {
      helper.setChecked(R.id.cb_check, itemList.getChecked() == 1).addOnClickListener(R.id.cb_check);
    }
    ImageView productIv = helper.getView(R.id.iv_product_img);
    GlideApp.with(mContext).load(MallAPI.IMG_SERVER + itemList.getPic()).placeholder(R.drawable.ic_default_image).into(productIv);
    helper.setText(R.id.tv_product_title, itemList.getTitle());
    helper.setText(R.id.tv_product_manufacture, itemList.getManufacture_name());

    String validity = itemList.getValidity();
    if (validity.startsWith("0")) {
      helper.setVisible(R.id.tv_product_validity, false);
    } else {
      helper.setVisible(R.id.tv_product_validity, true);
    }
    helper.setText(R.id.tv_product_validity, "有效期至: "+itemList.getValidity());

    if (itemList.getPrice() == 0) {
      helper.setText(R.id.tv_product_price, "暂无销售");
    } else {
      helper.setText(R.id.tv_product_price, "¥"+itemList.getPrice());
    }
    TextView oPriceTv = helper.getView(R.id.tv_product_oprice);
    oPriceTv.setText("¥"+itemList.getPrice());
    oPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    helper.setText(R.id.tv_product_specs, "规格："+itemList.getSpecs());
    helper.setGone(R.id.tv_product_promotion, itemList.getPromotion_list() != null && itemList.getPromotion_list().size() > 0 && itemList.getStatus() == 100)
        .addOnClickListener(R.id.tv_product_promotion);


    final AnimShopButton animShopBtn = helper.getView(R.id.btn_product_number);
    animShopBtn.setMaxCount(9999);
    if (cartMode == 1) {
      if (ACache.get(mContext).getAsObject(""+itemList.getId()) == null) {
        animShopBtn.setCount(itemList.getCount());
      } else {
        animShopBtn.setCount((int)ACache.get(mContext).getAsObject(""+itemList.getId()));
      }
    } else {
      //LogUtils.e("-----"+itemList.getCount());
      animShopBtn.setCount(itemList.getCount());
    }
    animShopBtn.invalidate();
    animShopBtn.setOnAddDelListener(new IOnAddDelListener() {
      @Override
      public void onAddSuccess(int i) {
        mCountAddDelListener.onAddClick(animShopBtn, helper.getLayoutPosition()-1);
      }

      @Override
      public void onAddFailed(int i, FailType failType) {

      }

      @Override
      public void onDelSuccess(int i) {
        mCountAddDelListener.onDelClick(animShopBtn, helper.getLayoutPosition()-1);
      }

      @Override
      public void onDelFaild(int i, FailType failType) {

      }
    });


    if (itemList.getStatus() == 0) {
      helper.setGone(R.id.tv_invalid, true);
      helper.setAlpha(R.id.tv_invalid, 0.5f);
      if (cartMode == 0) {
        helper.itemView.setClickable(false);
        helper.getView(R.id.cb_check).setClickable(false);
        animShopBtn.setVisibility(View.GONE);
        helper.getView(R.id.tv_product_promotion).setClickable(false);
      } else {
        helper.itemView.setClickable(false);
        helper.getView(R.id.cb_check).setClickable(true);
        animShopBtn.setVisibility(View.GONE);
        helper.getView(R.id.tv_product_promotion).setClickable(false);
      }
      helper.setTextColor(R.id.tv_product_title, mContext.getResources().getColor(R.color.color_99));
      helper.setTextColor(R.id.tv_product_manufacture, mContext.getResources().getColor(R.color.color_99));
      helper.setTextColor(R.id.tv_product_validity, mContext.getResources().getColor(R.color.color_99));
      helper.setTextColor(R.id.tv_product_price, mContext.getResources().getColor(R.color.color_99));
      oPriceTv.setTextColor(mContext.getResources().getColor(R.color.color_99));
      helper.setTextColor(R.id.tv_product_specs, mContext.getResources().getColor(R.color.color_99));
    } else {
      helper.setGone(R.id.tv_invalid, false);
      helper.itemView.setClickable(true);
      helper.getView(R.id.cb_check).setClickable(true);
      animShopBtn.setVisibility(View.VISIBLE);
      helper.getView(R.id.tv_product_promotion).setClickable(true);

      helper.setTextColor(R.id.tv_product_title, mContext.getResources().getColor(R.color.color_1a));
      helper.setTextColor(R.id.tv_product_manufacture, mContext.getResources().getColor(R.color.color_4d));
      helper.setTextColor(R.id.tv_product_validity, mContext.getResources().getColor(R.color.color_80));
      helper.setTextColor(R.id.tv_product_price, mContext.getResources().getColor(R.color.color_336));
      oPriceTv.setTextColor(mContext.getResources().getColor(R.color.color_b3));
      helper.setTextColor(R.id.tv_product_specs, mContext.getResources().getColor(R.color.color_b3));
    }

  }

  public interface CountAddDelListener {
    void onAddClick(View view, int position);
    void onDelClick(View view, int position);
  }

  public void setOnCountAddDelListener(CountAddDelListener listener){
    this.mCountAddDelListener = listener;
  }
}
