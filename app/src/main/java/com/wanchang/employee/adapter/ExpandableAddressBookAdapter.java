package com.wanchang.employee.adapter;

import android.content.Intent;
import android.view.View;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.AddressBook0Item;
import com.wanchang.employee.data.entity.AddressBook1Item;
import com.wanchang.employee.ui.salesman.news.ColleagueDetailActivity;
import com.wanchang.employee.util.GlideApp;
import java.util.List;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ExpandableAddressBookAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

  public static final int TYPE_LEVEL_0 = 0;
  public static final int TYPE_LEVEL_1 = 1;


  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param data A new list is created out of this one to avoid mutable list
   */
  public ExpandableAddressBookAdapter(List<MultiItemEntity> data) {
    super(data);
    addItemType(TYPE_LEVEL_0, R.layout.item_address_book_lv0);
    addItemType(TYPE_LEVEL_1, R.layout.item_address_book_lv1);
  }

  @Override
  protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
    switch (helper.getItemViewType()) {
      case TYPE_LEVEL_0:
        final AddressBook0Item lv0 = (AddressBook0Item)item;
        helper.setText(R.id.tv_lv0_title, lv0.title+"("+lv0.count+")")
            .setImageResource(R.id.iv_lv0_icon, lv0.isExpanded() ? R.drawable.contact_down : R.drawable.contact_right);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            int pos = helper.getAdapterPosition();
            if (lv0.isExpanded()) {
              collapse(pos, false);
            } else {
              expand(pos, false);
            }
          }
        });
        break;
      case TYPE_LEVEL_1:
        final AddressBook1Item lv1 = (AddressBook1Item)item;
        SuperTextView lv1Stv = helper.getView(R.id.stv_lv1_title);
        lv1Stv.setLeftString(lv1.colleague.getName());
        de.hdodenhof.circleimageview.CircleImageView picCiv = helper.getView(R.id.civ_pic);
        GlideApp.with(mContext).load(MallAPI.IMG_SERVER+lv1.colleague.getPic()).placeholder(R.drawable.avatar88x88).into(picCiv);
        helper.setOnClickListener(R.id.stv_lv1_title, new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            int pos = helper.getAdapterPosition();
            LogUtils.e("===="+pos);
            mContext.startActivity(new Intent(mContext, ColleagueDetailActivity.class).putExtra("colleague", lv1.colleague));
          }
        });
        break;

    }
  }
}
