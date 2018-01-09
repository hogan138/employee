package com.wanchang.employee.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.Category2Item;
import com.wanchang.employee.data.entity.ExpandableCategory1Item;
import com.wanchang.employee.data.entity.ExpandableCategory2Item;
import com.wanchang.employee.ui.classify.ProductFilterTwoFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout.OnSelectListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by stormlei on 2017/6/30.
 */

public class ExpandableCategoryAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

  public static final int TYPE_LEVEL_0 = 0;
  public static final int TYPE_LEVEL_1 = 1;
  private FragmentActivity activity;
  private int lv0Pos;
  private int lv1Pos;
  private ProductFilterTwoFragment filterTwoFragment;


  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param activity
   * @param data A new list is created out of this one to avoid mutable list
   */
  public ExpandableCategoryAdapter(int lv0Pos, int lv1Pos, FragmentActivity activity, List<MultiItemEntity> data,
      ProductFilterTwoFragment filterTwoFragment) {
    super(data);
    this.activity = activity;
    addItemType(TYPE_LEVEL_0, R.layout.item_product_filter_category_lv0);
    addItemType(TYPE_LEVEL_1, R.layout.item_product_filter_category_lv1);
    this.lv0Pos = lv0Pos;
    this.lv1Pos = lv1Pos;
    this.filterTwoFragment = filterTwoFragment;

    if (lv0Pos != -1) {
      expand(lv0Pos);
    }
  }

  @Override
  protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
    switch (helper.getItemViewType()) {
      case TYPE_LEVEL_0:
        final ExpandableCategory1Item lv0 = (ExpandableCategory1Item)item;
        helper.setText(R.id.tv_lv0_title, lv0.title)
            .setImageResource(R.id.iv_lv0_title, lv0.isExpanded() ? R.drawable.icon_arrow_up : R.drawable.icon_arrow_down);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            int pos = helper.getAdapterPosition();
            if (lv0.isExpanded()) {
              collapse(pos);
            } else {
              expand(pos);
            }
          }
        });
        break;
      case TYPE_LEVEL_1:
        final ExpandableCategory2Item lv1 = (ExpandableCategory2Item)item;
        final TagFlowLayout mTagFlowLayout = helper.getView(R.id.tfl_lv1_tag);

        final List<Category2Item> itemList = lv1.titles;
        final List<String> datas = new ArrayList<>();
        for (Category2Item ci : itemList) {
          datas.add(ci.getTitle());
        }

        TagAdapter<String> mTagAdapter;
        mTagFlowLayout.setAdapter(mTagAdapter = new TagAdapter<String>(datas) {
          @Override
          public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(mContext)
                .inflate(R.layout.tv_tag, mTagFlowLayout, false);
            tv.setText(s);
            return tv;
          }
        });

        if (lv0Pos != -1 && lv1Pos != -1 && lv0Pos == (helper.getLayoutPosition()-1)) {
          LogUtils.e("----"+lv1Pos);
          mTagAdapter.setSelectedList(lv1Pos);
        }

        mTagFlowLayout.setOnSelectListener(new OnSelectListener() {
          @Override
          public void onSelected(Set<Integer> selectPosSet) {
            if (selectPosSet.size() > 0) {
              LogUtils.e(itemList.get(selectPosSet.iterator().next()).getParent()-1+"===="+selectPosSet.iterator().next()+"---");
//              ACache.get(activity).put(Constants.KEY_PRODUCT_CATEGORY2, (itemList.get(selectPosSet.iterator().next()).getParent()-1)+"---"+selectPosSet.iterator().next());
//              ACache.get(activity).put(Constants.KEY_PRODUCT_CATEGORY2_NAME, datas.get(selectPosSet.iterator().next()));
//              ACache.get(activity).put(Constants.KEY_PRODUCT_CATEGORY2_ID,
//                  (itemList.get(selectPosSet.iterator().next()).getParent()+"---"+itemList.get(selectPosSet.iterator().next()).getId()));

              activity.getSupportFragmentManager().popBackStack();
            } else {
              //filterTwoFragment.onBack();
            }
          }
        });
        break;

    }
  }
}
