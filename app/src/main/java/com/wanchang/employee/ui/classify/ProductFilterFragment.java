package com.wanchang.employee.ui.classify;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.eventbus.ManufactureMessageEvent;
import com.wanchang.employee.ui.base.BaseFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout.OnSelectListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ProductFilterFragment extends BaseFragment {

    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerContent;

    @BindView(R.id.tfl_tag)
    TagFlowLayout mTagFlowLayout;

    @BindView(R.id.tv_dosage_all)
    TextView mDosageAllTv;
    @BindView(R.id.tfl_dosage)
    TagFlowLayout mDosageFlowLayout;

    @BindView(R.id.tv_chinese_medicine_all)
    TextView mChineseMedicineAllTv;
    @BindView(R.id.tfl_chinese_medicine)
    TagFlowLayout mChineseMedicineFlowLayout;

    @BindView(R.id.tv_prescription_all)
    TextView mPrescriptionAllTv;
    @BindView(R.id.tfl_prescription)
    TagFlowLayout mPrescriptionFlowLayout;

    @BindView(R.id.tv_insurance_all)
    TextView mInsuranceAllTv;
    @BindView(R.id.tfl_insurance)
    TagFlowLayout mInsuranceFlowLayout;

    @BindView(R.id.tv_manufacture_all)
    TextView mManufactureAllTv;



    private TagAdapter<String> mTagAdapter;
    private List<String> productTags;
    private String tag = "";

    private TagAdapter<String> mDosageAdapter;
    private List<String> dosageTags;
    private List<String> limitDosageTags;


    private TagAdapter<String> mChineseMedicineAdapter;
    private List<String> chineseMedicineTags;
    private List<String> limitChineseMedicineTags;


    private TagAdapter<String> mPrescriptionAdapter;
    private List<String> prescriptionTags;
    private List<String> limitPrescriptionTags;


    private TagAdapter<String> mInsuranceAdapter;
    private List<String> insuranceTags;
    private List<String> limitInsuranceTags;


    private List<String> manufactureList;


    CallBackValue callBackValue;


    private int mDosagePos = -1;
    private int mChineseMedicinePos = -1;
    private int mPrescriptionPos = -1;
    private int mInsurancePos = -1;


    public static ProductFilterFragment newInstance() {
        Bundle args = new Bundle();
        ProductFilterFragment fragment = new ProductFilterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_product_filter;
    }

    @Override
    protected void initData() {
        callBackValue = (CallBackValue) getActivity();
    }

    @Override
    protected void initView() {
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawerContent = (FrameLayout) getActivity().findViewById(R.id.drawer_content);

        loadFilterData();

        mTagFlowLayout.setOnSelectListener(new OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size() > 0) {
                    tag = productTags.get(selectPosSet.iterator().next());
                }
            }
        });
        mDosageFlowLayout.setOnSelectListener(new OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size() > 0) {
                    mDosagePos = selectPosSet.iterator().next();
                    String category_dosage = dosageTags.get(selectPosSet.iterator().next());
                    mDosageAllTv.setText(category_dosage);
                    mDosageAllTv.setTextColor(getResources().getColor(R.color.color_336));
                } else {
                    mDosagePos = -1;
                    mDosageAllTv.setText("全部");
                    mDosageAllTv.setTextColor(getResources().getColor(R.color.color_80));
                }
            }
        });
        mChineseMedicineFlowLayout.setOnSelectListener(new OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size() > 0) {
                    mChineseMedicinePos = selectPosSet.iterator().next();
                    String category_chinese_medicine = chineseMedicineTags.get(selectPosSet.iterator().next());
                    mChineseMedicineAllTv.setText(category_chinese_medicine);
                    mChineseMedicineAllTv.setTextColor(getResources().getColor(R.color.color_336));
                } else {
                    mChineseMedicinePos = -1;
                    mChineseMedicineAllTv.setText("全部");
                    mChineseMedicineAllTv.setTextColor(getResources().getColor(R.color.color_80));
                }
            }
        });
        mPrescriptionFlowLayout.setOnSelectListener(new OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size() > 0) {
                    mPrescriptionPos = selectPosSet.iterator().next();
                    String category_prescription = prescriptionTags.get(selectPosSet.iterator().next());
                    mPrescriptionAllTv.setText(category_prescription);
                    mPrescriptionAllTv.setTextColor(getResources().getColor(R.color.color_336));
                } else {
                    mPrescriptionPos = -1;
                    mPrescriptionAllTv.setText("全部");
                    mPrescriptionAllTv.setTextColor(getResources().getColor(R.color.color_80));
                }
            }
        });
        mInsuranceFlowLayout.setOnSelectListener(new OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.size() > 0) {
                    mInsurancePos = selectPosSet.iterator().next();
                    String category_insurance = insuranceTags.get(selectPosSet.iterator().next());
                    mInsuranceAllTv.setText(category_insurance);
                    mInsuranceAllTv.setTextColor(getResources().getColor(R.color.color_336));
                } else {
                    mInsurancePos = -1;
                    mInsuranceAllTv.setText("全部");
                    mInsuranceAllTv.setTextColor(getResources().getColor(R.color.color_80));
                }
            }
        });
    }

    private void loadFilterData() {
        OkGo.<String>get(MallAPI.PRODUCT_FILTER_LIST)
            .tag(this)
            .execute(new StringDialogCallback(mContext) {

                @Override
                public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200) {
                        JSONObject jsonObj = JSON.parseObject(response.body());
                        productTags = JSON.parseArray(jsonObj.getString("tag_list"), String.class);
                        mTagFlowLayout.setAdapter(mTagAdapter = new TagAdapter<String>(productTags) {
                            @Override
                            public View getView(FlowLayout parent, int position, String s) {
                                TextView tv = (TextView) LayoutInflater.from(mContext)
                                    .inflate(R.layout.tv_tag, mTagFlowLayout, false);
                                tv.setText(s);
                                return tv;
                            }
                        });

                        manufactureList = JSON.parseArray(jsonObj.getString("manufacture_list"), String.class);


                        dosageTags = JSON.parseArray(jsonObj.getString("category_dosage_list"), String.class);
                        limitDosageTags = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            limitDosageTags.add(dosageTags.get(i));
                        }
                        mDosageFlowLayout.setAdapter(mDosageAdapter = new TagAdapter<String>(limitDosageTags) {
                            @Override
                            public View getView(FlowLayout parent, int position, String s) {
                                TextView tv = (TextView) LayoutInflater.from(mContext)
                                    .inflate(R.layout.tv_tag, mDosageFlowLayout, false);
                                tv.setText(s);
                                return tv;
                            }
                        });

                        chineseMedicineTags = JSON.parseArray(jsonObj.getString("category_chinese_medicine_list"), String.class);
                        limitChineseMedicineTags = new ArrayList<>();
                        for (int i = 0; i < 2; i++) {
                            limitChineseMedicineTags.add(chineseMedicineTags.get(i));
                        }
                        mChineseMedicineFlowLayout.setAdapter(mChineseMedicineAdapter = new TagAdapter<String>(limitChineseMedicineTags) {
                            @Override
                            public View getView(FlowLayout parent, int position, String s) {
                                TextView tv = (TextView) LayoutInflater.from(mContext)
                                    .inflate(R.layout.tv_tag, mChineseMedicineFlowLayout, false);
                                tv.setText(s);
                                return tv;
                            }
                        });

                        prescriptionTags = JSON.parseArray(jsonObj.getString("category_prescription"), String.class);
                        limitPrescriptionTags = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            limitPrescriptionTags.add(prescriptionTags.get(i));
                        }
                        mPrescriptionFlowLayout.setAdapter(mPrescriptionAdapter = new TagAdapter<String>(limitPrescriptionTags) {
                            @Override
                            public View getView(FlowLayout parent, int position, String s) {
                                TextView tv = (TextView) LayoutInflater.from(mContext)
                                    .inflate(R.layout.tv_tag, mPrescriptionFlowLayout, false);
                                tv.setText(s);
                                return tv;
                            }
                        });

                        insuranceTags = JSON.parseArray(jsonObj.getString("category_insurance"), String.class);
                        limitInsuranceTags = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            limitInsuranceTags.add(insuranceTags.get(i));
                        }
                        mInsuranceFlowLayout.setAdapter(mInsuranceAdapter = new TagAdapter<String>(limitInsuranceTags) {
                            @Override
                            public View getView(FlowLayout parent, int position, String s) {
                                TextView tv = (TextView) LayoutInflater.from(mContext)
                                    .inflate(R.layout.tv_tag, mInsuranceFlowLayout, false);
                                tv.setText(s);
                                return tv;
                            }
                        });
                    }
                }
            });
    }

    @OnClick(R.id.ll_manufacture)
    public void onManufactureAll() {
        showNext();
    }

    private boolean isDosageAll;

    @OnClick(R.id.ll_dosage)
    public void onDosageAll() {
        mDosageAdapter.setSelectedList(new HashSet<Integer>());
        if (!isDosageAll) {
            limitDosageTags.clear();
            limitDosageTags.addAll(dosageTags);
            isDosageAll = true;
            Drawable drawablelright = getResources().getDrawable(R.drawable.up);
            drawablelright.setBounds(0, 0, drawablelright.getMinimumWidth(), drawablelright.getMinimumHeight());
            mDosageAllTv.setCompoundDrawables(null, null, drawablelright, null);
        } else {
            List<String> tempTags = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                tempTags.add(limitDosageTags.get(i));
            }
            limitDosageTags.clear();
            limitDosageTags.addAll(tempTags);
            isDosageAll = false;
            Drawable drawablelright = getResources().getDrawable(R.drawable.down);
            drawablelright.setBounds(0, 0, drawablelright.getMinimumWidth(), drawablelright.getMinimumHeight());
            mDosageAllTv.setCompoundDrawables(null, null, drawablelright, null);
        }
//        for (int i = 0; i < limitDosageTags.size(); i++) {
//            if (mDosageAllTv.getText().toString().equals(limitDosageTags.get(i))) {
//                mDosageAdapter.setSelectedList(i);
//            }
//        }
        if (mDosagePos != -1)
            mDosageAdapter.setSelectedList(mDosagePos);
        mDosageAdapter.notifyDataChanged();
    }


    private boolean isChineseMedicineAll;

    @OnClick(R.id.ll_chinese_medicine)
    public void onChineseMedicineAll() {
        mChineseMedicineAdapter.setSelectedList(new HashSet<Integer>());
        if (!isChineseMedicineAll) {
            limitChineseMedicineTags.clear();
            limitChineseMedicineTags.addAll(chineseMedicineTags);
            isChineseMedicineAll = true;
            Drawable drawablelright = getResources().getDrawable(R.drawable.up);
            drawablelright.setBounds(0, 0, drawablelright.getMinimumWidth(), drawablelright.getMinimumHeight());
            mChineseMedicineAllTv.setCompoundDrawables(null, null, drawablelright, null);
        } else {
            List<String> tempTags = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                tempTags.add(limitChineseMedicineTags.get(i));
            }
            limitChineseMedicineTags.clear();
            limitChineseMedicineTags.addAll(tempTags);
            isChineseMedicineAll = false;
            Drawable drawablelright = getResources().getDrawable(R.drawable.down);
            drawablelright.setBounds(0, 0, drawablelright.getMinimumWidth(), drawablelright.getMinimumHeight());
            mChineseMedicineAllTv.setCompoundDrawables(null, null, drawablelright, null);
        }
        if (mChineseMedicinePos != -1)
            mChineseMedicineAdapter.setSelectedList(mChineseMedicinePos);
        mChineseMedicineAdapter.notifyDataChanged();
    }


    private boolean isPrescriptionAll;

    @OnClick(R.id.ll_prescription)
    public void onPrescriptionAll() {
        mPrescriptionAdapter.setSelectedList(new HashSet<Integer>());
        if (!isPrescriptionAll) {
            limitPrescriptionTags.clear();
            limitPrescriptionTags.addAll(prescriptionTags);
            isPrescriptionAll = true;
            Drawable drawablelright = getResources().getDrawable(R.drawable.up);
            drawablelright.setBounds(0, 0, drawablelright.getMinimumWidth(), drawablelright.getMinimumHeight());
            mPrescriptionAllTv.setCompoundDrawables(null, null, drawablelright, null);
        } else {
            List<String> tempTags = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                tempTags.add(limitPrescriptionTags.get(i));
            }
            limitPrescriptionTags.clear();
            limitPrescriptionTags.addAll(tempTags);
            isPrescriptionAll = false;
            Drawable drawablelright = getResources().getDrawable(R.drawable.down);
            drawablelright.setBounds(0, 0, drawablelright.getMinimumWidth(), drawablelright.getMinimumHeight());
            mPrescriptionAllTv.setCompoundDrawables(null, null, drawablelright, null);
        }
        if (mPrescriptionPos != -1)
            mPrescriptionAdapter.setSelectedList(mPrescriptionPos);
        mPrescriptionAdapter.notifyDataChanged();
    }

    private boolean isInsuranceAll;

    @OnClick(R.id.ll_insurance)
    public void onInsuranceAll() {
        mInsuranceAdapter.setSelectedList(new HashSet<Integer>());
        if (!isInsuranceAll) {
            limitInsuranceTags.clear();
            limitInsuranceTags.addAll(insuranceTags);
            isInsuranceAll = true;
            Drawable drawablelright = getResources().getDrawable(R.drawable.up);
            drawablelright.setBounds(0, 0, drawablelright.getMinimumWidth(), drawablelright.getMinimumHeight());
            mInsuranceAllTv.setCompoundDrawables(null, null, drawablelright, null);
        } else {
            List<String> tempTags = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                tempTags.add(limitInsuranceTags.get(i));
            }
            limitInsuranceTags.clear();
            limitInsuranceTags.addAll(tempTags);
            isInsuranceAll = false;
            Drawable drawablelright = getResources().getDrawable(R.drawable.down);
            drawablelright.setBounds(0, 0, drawablelright.getMinimumWidth(), drawablelright.getMinimumHeight());
            mInsuranceAllTv.setCompoundDrawables(null, null, drawablelright, null);
        }
        if (mInsurancePos != -1)
            mInsuranceAdapter.setSelectedList(mInsurancePos);
        mInsuranceAdapter.notifyDataChanged();
    }


    @OnClick(R.id.tv_filter_reset)
    public void onReset() {
        mManufactureAllTv.setText("全部");
        mManufactureAllTv.setTextColor(getResources().getColor(R.color.color_80));
        mTagAdapter.setSelectedList(new HashSet<Integer>());
        mDosageAdapter.setSelectedList(new HashSet<Integer>());
        mDosageAllTv.setText("全部");
        mDosageAllTv.setTextColor(getResources().getColor(R.color.color_80));
        mChineseMedicineAdapter.setSelectedList(new HashSet<Integer>());
        mChineseMedicineAllTv.setText("全部");
        mChineseMedicineAllTv.setTextColor(getResources().getColor(R.color.color_80));
        mPrescriptionAdapter.setSelectedList(new HashSet<Integer>());
        mPrescriptionAllTv.setText("全部");
        mPrescriptionAllTv.setTextColor(getResources().getColor(R.color.color_80));
        mInsuranceAdapter.setSelectedList(new HashSet<Integer>());
        mInsuranceAllTv.setText("全部");
        mInsuranceAllTv.setTextColor(getResources().getColor(R.color.color_80));

        mDosagePos = -1;
        mChineseMedicinePos = -1;
        mPrescriptionPos = -1;
        mInsurancePos = -1;

    }

    @OnClick(R.id.tv_filter_ok)
    public void onOK() {
        mDrawerLayout.closeDrawer(mDrawerContent);
        String manufacture_id = mManufactureAllTv.getText().toString();
        if ("全部".equals(manufacture_id))
            manufacture_id = "";
        String category_dosage = mDosageAllTv.getText().toString();
        if ("全部".equals(category_dosage))
            category_dosage = "";
        String category_chinese_medicine = mChineseMedicineAllTv.getText().toString();
        if ("全部".equals(category_chinese_medicine))
            category_chinese_medicine = "";
        String category_prescription = mPrescriptionAllTv.getText().toString();
        if ("全部".equals(category_prescription))
            category_prescription = "";
        String category_insurance = mInsuranceAllTv.getText().toString();
        if ("全部".equals(category_insurance))
            category_insurance = "";
        callBackValue.sendMessageValue(tag, category_dosage, category_chinese_medicine, category_prescription, category_insurance, manufacture_id);

    }

    public interface CallBackValue{
        void sendMessageValue(String tag, String category_dosage, String category_chinese_medicine,
            String category_prescription, String category_insurance, String manufacture_id);
    }


    private void showNext() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(this);
        ProductFilterTwoFragment filterTwoFragment = ProductFilterTwoFragment.newInstance(manufactureList, mManufactureAllTv.getText().toString());
        //fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
        fragmentTransaction.add(R.id.drawer_content, filterTwoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ManufactureMessageEvent event) {
        LogUtils.e("manufacture event");
        mManufactureAllTv.setText(event.getManufacture());
        mManufactureAllTv.setTextColor(getResources().getColor(R.color.color_336));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
