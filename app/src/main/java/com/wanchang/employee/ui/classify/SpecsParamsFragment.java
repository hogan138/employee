package com.wanchang.employee.ui.classify;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.wanchang.employee.R;
import com.wanchang.employee.data.entity.ProductDetail;
import com.wanchang.employee.ui.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecsParamsFragment extends BaseFragment {

  @BindView(R.id.tv_validity)
  TextView mValidityTv;
  @BindView(R.id.tv_specs)
  TextView mSpecsTv;
  @BindView(R.id.tv_manufacture)
  TextView mManufactureTv;
  @BindView(R.id.tv_packaing)
  TextView mPackaingTv;
  @BindView(R.id.tv_auth_doc_num)
  TextView mDocNumTv;
  @BindView(R.id.tv_prescription)
  TextView mPrescriptionTv;
  @BindView(R.id.tv_insurance)
  TextView mInsuranceTv;
  @BindView(R.id.tv_dosage)
  TextView mDosageTv;
  @BindView(R.id.tv_unit)
  TextView mUnitTv;

  @BindView(R.id.tv_contain_numb)
  TextView mContainNumbTv;
  @BindView(R.id.tv_return_dateline)
  TextView mReturnDatelineTv;



  private ProductDetail productDetail;

  private static final String BUNDLE_ARGS = "bundle_args";

  public SpecsParamsFragment() {
    // Required empty public constructor
  }

  public static SpecsParamsFragment newInstance(ProductDetail params) {
    Bundle args = new Bundle();
    args.putParcelable(BUNDLE_ARGS, params);
    SpecsParamsFragment fragment = new SpecsParamsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //ImmersionBar.setTitleBar(mContext, mToolbar);
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_specs_params;
  }

  @Override
  protected void initData() {
    productDetail = getArguments().getParcelable(BUNDLE_ARGS);
  }

  @Override
  protected void initView() {
    mValidityTv.setText(productDetail.getProduct_sku().getValidity());
    mSpecsTv.setText(productDetail.getProduct().getSpecs());
    mManufactureTv.setText(productDetail.getProduct().getManufacture_name());
    mPackaingTv.setText(productDetail.getProduct().getPackaing()+"/"+productDetail.getProduct().getPiece_packaing());
    mDocNumTv.setText(productDetail.getProduct().getAuth_doc_num());
    mPrescriptionTv.setText(productDetail.getProduct().getCategory_prescription());
    mInsuranceTv.setText(productDetail.getProduct().getCategory_insurance());
    mDosageTv.setText(productDetail.getProduct().getCategory_dosage());
    mUnitTv.setText(productDetail.getProduct().getUnit());

    mContainNumbTv.setText(productDetail.getProduct().getContain_numb() == 0 ? "不含麻" : "含麻");
    mReturnDatelineTv.setText(productDetail.getProduct().getReturn_dateline()+"个月");
  }

}
