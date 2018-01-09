package com.wanchang.employee.ui.classify;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import com.wanchang.employee.R;
import com.wanchang.employee.ui.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageTextFragment extends BaseFragment {

  @BindView(R.id.webView)
  WebView mWebView;

  private static final String BUNDLE_ARGS = "bundle_args";

  public ImageTextFragment() {
    // Required empty public constructor
  }

  public static ImageTextFragment newInstance(String params) {
    Bundle args = new Bundle();
    args.putString(BUNDLE_ARGS, params);
    ImageTextFragment fragment = new ImageTextFragment();
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
    return R.layout.fragment_product_detail_image_text;
  }

  @Override
  protected void initData() {
    WebSettings webSetting = mWebView.getSettings();
    webSetting.setDomStorageEnabled(true);
    webSetting.setJavaScriptEnabled(true);
    webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

    mWebView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        webView.loadUrl(url);
        return true;
      }
    });
  }

  @Override
  protected void initView() {
    mWebView.loadUrl("http://www.baidu.com");
  }

}
