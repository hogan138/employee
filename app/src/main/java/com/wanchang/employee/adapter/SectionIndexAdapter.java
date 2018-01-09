package com.wanchang.employee.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.entity.IndexList;
import com.wanchang.employee.data.entity.IndexSection;
import com.wanchang.employee.util.GlideApp;
import java.util.List;

/**
 * Created by stormlei on 2017/7/4.
 */

public class SectionIndexAdapter extends BaseSectionQuickAdapter<IndexSection, BaseViewHolder> {


  /**
   * Same as QuickAdapter#QuickAdapter(Context,int) but with
   * some initialization data.
   *
   * @param layoutResId The layout resource id of each item.
   * @param sectionHeadResId The section head layout id for each item
   * @param data A new list is created out of this one to avoid mutable list
   */
  public SectionIndexAdapter(int layoutResId, int sectionHeadResId,
      List<IndexSection> data) {
    super(layoutResId, sectionHeadResId, data);

  }

  @Override
  protected void convertHead(BaseViewHolder helper, final IndexSection item) {
    helper.setText(R.id.tv_head_title, item.header);
  }


  @Override
  protected void convert(BaseViewHolder helper, IndexSection item) {
    IndexList.ItemListBean video = item.t;
    ImageView hotIv = helper.getView(R.id.iv_pic);
    GlideApp.with(mContext).load(MallAPI.IMG_SERVER + video.getPic()).placeholder(R.drawable.ic_default_image).into(hotIv);
    helper.setText(R.id.tv_title, video.getTitle());
    helper.setText(R.id.tv_specs, video.getSpecs());
    helper.setText(R.id.tv_price, "¥ "+video.getPrice());
  }

}
