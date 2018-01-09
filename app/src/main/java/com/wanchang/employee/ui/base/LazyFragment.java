package com.wanchang.employee.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by stormlei on 2017/5/15.
 */

public abstract class LazyFragment extends BaseFragment {
  /**
   * 是否对用户可见
   */
  protected boolean mIsVisible;
  /**
   * 是否加载完成
   * 当执行完onViewCreated方法后即为true
   */
  protected boolean mIsPrepare;

  /**
   * 是否加载完成
   * 当执行完onViewCreated方法后即为true
   */
  protected boolean mIsImmersion;

  //protected ImmersionBar mImmersionBar;


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mIsPrepare = true;
    mIsImmersion = true;
    onLazyLoad();
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
//    if (mImmersionBar != null)
//      mImmersionBar.destroy();
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);

    if (getUserVisibleHint()) {
      mIsVisible = true;
      onVisible();
    } else {
      mIsVisible = false;
      onInvisible();
    }
  }

  /**
   * 用户可见时执行的操作
   */
  protected void onVisible() {
    onLazyLoad();
  }

  private void onLazyLoad() {
    if (mIsVisible && mIsPrepare) {
      mIsPrepare = false;
      loadData();
    }
    if (mIsVisible && mIsImmersion) {
      initImmersionBar();
    }
  }

  /**
   * 初始化沉浸式
   */
  protected void initImmersionBar() {
//    mImmersionBar = ImmersionBar.with(this);
//    mImmersionBar.statusBarDarkFont(true).init();
  }

  /**
   * 用户不可见执行
   */
  protected void onInvisible() {

  }

  protected abstract void loadData();

}
