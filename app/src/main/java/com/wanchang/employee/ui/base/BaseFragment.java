package com.wanchang.employee.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.ui.LoginActivity;

/**
 * Created by stormlei on 2017/5/4.
 */

public abstract class BaseFragment extends RxFragment {

  protected static final String TAG = BaseFragment.class.getSimpleName();
  protected FragmentActivity mContext;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    //LogUtils.i(TAG, getClass().getSimpleName());
    View rootView = inflater.inflate(getLayoutResId(), container, false);
    ButterKnife.bind(this, rootView);

    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mContext = getActivity();
    initData();
    initView();
  }

  protected abstract int getLayoutResId();

  protected abstract void initData();

  protected abstract void initView();


  public void openActivity(Intent intent, boolean requireLogin) {
    if (requireLogin) {
      if (MallApp.getInstance().isLogin()) {
        startActivity(intent);
      } else {
        startActivity(new Intent(mContext, LoginActivity.class));
      }
    } else {
      startActivity(intent);
    }
  }

}
