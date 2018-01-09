package com.wanchang.employee.web;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.mzule.activityrouter.router.Routers;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wanchang.employee.R;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.ui.base.BaseFragment;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class WebViewFragment extends BaseFragment {

    @BindView(R.id.iv_topbar_left)
    ImageView mLeftIv;
    @BindView(R.id.tv_topbar_title)
    TextView mTitleTv;
    @BindView(R.id.iv_topbar_right_1)
    ImageView mRightIv1;
    @BindView(R.id.iv_topbar_right_2)
    ImageView mRightIv2;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.progress)
    ProgressBar mProgress;

    private RxPermissions rxPermissions;


    private String webUrl;
    private String callbackName;


    public static WebViewFragment newInstance(String url, String callbackName) {
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("callbackName", callbackName);
        WebViewFragment mWebViewFragment = new WebViewFragment();
        mWebViewFragment.setArguments(args);
        return mWebViewFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        webUrl = bundle.getString("url");
        LogUtils.e(TAG, "webUrl: " + webUrl);
        callbackName = bundle.getString("callbackName");
        LogUtils.e(TAG, "callbackName: " + callbackName);

        rxPermissions = new RxPermissions(mContext);

        rxPermissions
            .request(permission.READ_PHONE_STATE)
            .subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (aBoolean) {

                    }else {
                        ToastUtils.showShort("电话权限未打开");
                    }
                }
            });
    }

    @Override
    protected void initView() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setUserAgentString(webSetting.getUserAgentString() + " android/qingpai/1.2.3");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                Log.e(TAG, "shouldOverrideUrlLoading: " + url);
                if (url.startsWith("qingpai://go")) {
                    Routers.open(mContext, url);
                } else if (url.startsWith("qingpai://replace")) {
                    Routers.open(mContext, url);
                    mContext.finish();
                } else if (url.startsWith("qingpai://back")) {
                    goBack();
                } else if (url.startsWith("qingpai://share")) {
                    Routers.openForResult(mContext, url, WebViewActivity.REQUEST_CODE_SHARE);
                } else if (url.startsWith("qingpai://previewImage")) {
                    Routers.openForResult(mContext, url, WebViewActivity.REQUEST_CODE_PREVIEW_IMAGE);
                } else if (url.startsWith("qingpai://uploadImage")) {
                    Routers.openForResult(mContext, url, WebViewActivity.REQUEST_CODE_UPLOAD_IMAGE);
                } else if (url.startsWith("qingpai://updateHeader")) {
                    handleHeader(url);
                } else if (url.startsWith("qingpai://setHeaderTitle")) {
                    handleHeaderTitle(url);
                } else if (url.startsWith("qingpai://resultGo")) {
                    handleResultGo(url);
                } else if (url.startsWith("qingpai://resultBack")) {
                    handleResultBack(url);
                } else if (url.startsWith("qingpai://getUserInfo")) {
                    handleUserInfo(url);
                } else if (url.startsWith("qingpai://getNetwork")) {
                    handleNetwork(url);
                } else if (url.startsWith("qingpai://getLocation")) {
                    handleLocation(url);
                } else if (url.startsWith("qingpai://scan")) {
                    handleScan(url);
                } else {
                    webView.loadUrl(url);
                }
                return true;
            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgress.setVisibility(View.GONE);
                } else {
                    mProgress.setProgress(newProgress);
                }

            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                mTitleTv.setText(s);
            }
        });

        mWebView.loadUrl(webUrl);

    }


    private void handleHeader(String url) {
        String params = Uri.parse(url).getQueryParameter("params");
        JSONObject jsonObj = JSON.parseObject(params);
        LogUtils.e(TAG, jsonObj);

        JSONArray jsonArrayLeft = jsonObj.getJSONArray("left");
        for (int i = 0; i < jsonArrayLeft.size(); i++) {
            JSONObject jsonObjLeft = jsonArrayLeft.getJSONObject(i);
            String callbackLeft = jsonObjLeft.getString("callback");
            String iconLeft = jsonObjLeft.getString("icon");
            if ("back".equals(iconLeft)) {

            }
        }
        JSONArray jsonArrayRight = jsonObj.getJSONArray("right");
        for (int i = 0; i < jsonArrayRight.size(); i++) {
            JSONObject jsonObjRight = jsonArrayRight.getJSONObject(i);
            String callbackRight = jsonObjRight.getString("callback");
            String iconRight = jsonObjRight.getString("icon");
            if ("search".equals(iconRight)) {
                mRightIv1.setVisibility(View.VISIBLE);
            } else if("http://img".equals(iconRight)) {
                mRightIv2.setVisibility(View.VISIBLE);
            } else {

            }
        }
        String title = jsonObj.getString("title");
        String subTitle = jsonObj.getString("subTitle");
        mTitleTv.setText(title+"-"+subTitle);

    }
    private void handleHeaderTitle(String url) {
        String params = Uri.parse(url).getQueryParameter("params");
        mTitleTv.setText(params);
    }

    private void handleResultGo(String url) {
        String callbackName = Uri.parse(url).getQueryParameter("callback");
        String params = Uri.parse(url).getQueryParameter("params");
        JSONObject jsonObj = JSON.parseObject(params);
        String type = jsonObj.getString("type");
        String to = jsonObj.getString("to");
        if ("web".equals(type)) {
            mContext.startActivityForResult(new Intent(mContext, WebViewActivity.class)
                .putExtra(WebViewActivity.WEB_URL, to)
                .putExtra(WebViewActivity.CALLBACK_NAME, callbackName), WebViewActivity.REQUEST_CODE_RESULT_BACK);
        } else {

        }
    }
    private void handleResultBack(String url) {
        String params = Uri.parse(url).getQueryParameter("params");
        Intent data = new Intent();
        data.putExtra("key_params", params);
        data.putExtra("callback_name", callbackName);
        mContext.setResult(Activity.RESULT_OK, data);
        mContext.finish();
    }
    public void handleForResult(String callbackName, String params) {
        LogUtils.e(TAG, callbackName+"----"+params);
        mWebView.loadUrl("javascript:" + callbackName + "('" + getResultRes(params) + "')");
    }
    public void handleUpload(String callbackName, String[] cloud_key, String[] url) {
        mWebView.loadUrl("javascript:" + callbackName + "('" + getUploadRes(cloud_key, url) + "')");
    }
    private void handleUserInfo(String url) {
        String callbackName = Uri.parse(url).getQueryParameter("callback");
        mWebView.loadUrl("javascript:" + callbackName + "('" + getUserRes() + "')");
    }
    private void handleNetwork(String url) {
        String callbackName = Uri.parse(url).getQueryParameter("callback");
        mWebView.loadUrl("javascript:" + callbackName + "('" + getNetworkRes() + "')");
    }
    private void handleLocation(String url) {
        String callbackName = Uri.parse(url).getQueryParameter("callback");
        mWebView.loadUrl("javascript:" + callbackName + "('" + getLocationRes() + "')");
    }
    private void handleScan(String url) {
        callbackName = Uri.parse(url).getQueryParameter("callback");
        rxPermissions
            .request(Manifest.permission.CAMERA)
            .subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(@NonNull Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        mContext.startActivityForResult(new Intent(mContext, ZxingCustomActivity.class), WebViewActivity.REQUEST_CODE_SCAN);
                    }else {
                        ToastUtils.showShort("相机权限未打开");
                    }
                }
            });

    }
    public void handleScanResult(String result) {
        mWebView.loadUrl("javascript:" + callbackName + "('" + getScanRes(result) + "')");
    }


    private String getScanRes(String result) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", true);
        jsonObj.put("data", result);
        jsonObj.put("message", "");
        return jsonObj.toString();
    }
    private String getResultRes(String params) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", true);
        jsonObj.put("data", JSON.parseObject(params));
        jsonObj.put("message", "");
        return jsonObj.toString();
    }
    private String getUploadRes(String[] cloud_key, String[] url) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", true);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cloud_key.length; i++) {
            JSONObject dataObj = new JSONObject();
            dataObj.put("cloud_pic", cloud_key[i]);
            dataObj.put("url", url[i]);
            jsonArray.add(dataObj);
        }
        jsonObj.put("data", jsonArray);
        jsonObj.put("message", "");
        return jsonObj.toString();
    }
    private String getUserRes() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", true);
        jsonObj.put("data", MallApp.getInstance().getToken());
        jsonObj.put("message", "");
        return jsonObj.toString();
    }
    private String getNetworkRes() {
        String network = "";
        if (NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_WIFI) {
            network = "wifi";
        } else if (NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_4G) {
            network = "4g";
        } else if (NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_3G) {
            network = "3g";
        } else if (NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_2G) {
            network = "2g";
        } else if (NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_NO) {
            network = "none";
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", true);
        JSONObject dataObj = new JSONObject();
        dataObj.put("networkType", network);
        jsonObj.put("data", dataObj);
        jsonObj.put("message", "");
        return jsonObj.toString();
    }
    private String getLocationRes() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", true);
        JSONObject dataObj = new JSONObject();
//        String lat = mCache.getAsString(AppConfig.LAT);
//        String lon = mCache.getAsString(AppConfig.LON);
//        LogUtils.e(TAG, lat + "-----" + lon);
//        dataObj.put("lat", lat == null ? "0" : lat);
//        dataObj.put("lon", lon == null ? "0" : lon);
        dataObj.put("lat", "0");
        dataObj.put("lon", "0");
        jsonObj.put("data", dataObj);
        jsonObj.put("message", "");
        return jsonObj.toString();
    }



    @OnClick(R.id.iv_topbar_left)
    public void goBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            mContext.finish();
        }
    }

    @OnClick(R.id.iv_topbar_left_close)
    public void closePage() {
        mContext.finish();
    }

    @OnClick(R.id.iv_topbar_right_1)
    public void onClickRight1() {
        ToastUtils.showShort("search");
    }

    @OnClick(R.id.iv_topbar_right_2)
    public void onClickRight2() {
        ToastUtils.showShort("add");
    }

    public void onBackPressed() {
        goBack();
    }
}
