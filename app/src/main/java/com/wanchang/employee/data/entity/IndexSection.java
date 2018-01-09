package com.wanchang.employee.data.entity;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.wanchang.employee.data.entity.IndexList.ItemListBean;

/**
 * Created by stormlei on 2017/7/4.
 */

public class IndexSection extends SectionEntity<IndexList.ItemListBean> {

  public IndexSection(boolean isHeader, String header) {
    super(isHeader, header);
  }

  public IndexSection(ItemListBean itemListBean) {
    super(itemListBean);
  }
}
