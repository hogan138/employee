package com.wanchang.employee.ui.classify;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Category1Item;
import com.wanchang.employee.data.entity.ExpandableCategory1Item;
import com.wanchang.employee.data.entity.ExpandableCategory2Item;
import com.wanchang.employee.ui.base.BaseFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.ArrayList;
import java.util.List;


public class ProductFilterTwoFragmentBackup extends BaseFragment {

    @BindView(R.id.rv_category)
    RecyclerView mCategoryRv;

    public static ProductFilterTwoFragmentBackup newInstance() {
        Bundle args = new Bundle();
        ProductFilterTwoFragmentBackup fragment = new ProductFilterTwoFragmentBackup();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_product_filter_two;
    }

    @Override
    protected void initData() {
        loadClassifyData();
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        getFragmentManager().popBackStack();
    }

    private void loadClassifyData() {
        OkGo.<String>get(MallAPI.PRODUCT_CATEGORY)
            .tag(this)
            .execute(new StringDialogCallback(mContext) {

                @Override
                public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                        List<Category1Item> item1List = new ArrayList<Category1Item>();
                        JSONObject bodyObj = JSON.parseObject(response.body());
                        JSONArray itemsArray = bodyObj.getJSONArray("items");
                        for (int i = 0; i < itemsArray.size(); i++) {
                            Category1Item item1Obj = itemsArray.getObject(i, Category1Item.class);
                            item1List.add(item1Obj);
                        }


                        ArrayList<MultiItemEntity> res = new ArrayList<MultiItemEntity>();
                        for (int i = 0; i < item1List.size(); i++) {
                            ExpandableCategory1Item lv0 = new ExpandableCategory1Item(item1List.get(i).getTitle(), item1List.get(i).getId());
                            lv0.addSubItem(new ExpandableCategory2Item(item1List.get(i).getList()));
                            res.add(lv0);
                        }

//                        String productCategory2 = ACache.get(mContext).getAsString(Constants.KEY_PRODUCT_CATEGORY2);
//                        LogUtils.e(productCategory2+"--nnnnnnnn");
//                        int lv0Pos = -1;
//                        int lv1Pos = -1;
//                        if (!TextUtils.isEmpty(productCategory2) && productCategory2.split("---").length == 2) {
//                            lv0Pos = Integer.parseInt(productCategory2.split("---")[0]);
//                            lv1Pos = Integer.parseInt(productCategory2.split("---")[1]);
//                        }

                        //mCategoryRv.setAdapter(new ExpandableCategoryAdapter(lv0Pos, lv1Pos ,getActivity(), res, ProductFilterTwoFragment.this));
                        mCategoryRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
                            getResources().getColor(R.color.color_dc)).build());
                        mCategoryRv.setLayoutManager(new LinearLayoutManager(mContext));
                    }
                }
            });
    }
}
