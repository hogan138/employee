package com.wanchang.employee.ui.classify;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanchang.employee.R;
import com.wanchang.employee.eventbus.ManufactureMessageEvent;
import com.wanchang.employee.ui.base.BaseFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;


public class ProductFilterTwoFragment extends BaseFragment {

    @BindView(R.id.tv_topbar_title)
    TextView mTitleTv;
    @BindView(R.id.rv_manufacture)
    RecyclerView mManufactureRv;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;


    public static ProductFilterTwoFragment newInstance(List<String> manufactures, String selManuf) {
        Bundle args = new Bundle();
        args.putStringArrayList("manufs", (ArrayList<String>) manufactures);
        args.putString("selManuf", selManuf);
        ProductFilterTwoFragment fragment = new ProductFilterTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_product_filter_two;
    }

    @Override
    protected void initData() {
        List<String> data = getArguments().getStringArrayList("manufs");
        final String seldata = getArguments().getString("selManuf", "");
        mManufactureRv.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_manufacture_list, data) {

            @Override
            protected void convert(BaseViewHolder helper, String item) {
                if (seldata.equals(item)) {
                    helper.setVisible(R.id.iv_sel, true);
                    helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_336));
                } else {
                    helper.setVisible(R.id.iv_sel, false);
                    helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_4d));
                }
                helper.setText(R.id.tv_name, item);
            }
        });
        mManufactureRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext).color(
            getResources().getColor(R.color.color_e5)).build());
        mManufactureRv.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String txt = mAdapter.getItem(position);
                EventBus.getDefault().post(new ManufactureMessageEvent(txt));
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected void initView() {
        mTitleTv.setText("厂商");
    }

    @OnClick(R.id.tv_topbar_left)
    public void onGoBack() {
        getFragmentManager().popBackStack();
    }
}
