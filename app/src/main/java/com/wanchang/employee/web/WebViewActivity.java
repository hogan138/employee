package com.wanchang.employee.web;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.ui.base.BaseActivity;
import java.io.File;
import java.util.ArrayList;


public class WebViewActivity extends BaseActivity {

    public static final String WEB_URL = "web_url";
    public static final String CALLBACK_NAME = "callback_name";

    public static final int REQUEST_CODE_SHARE = 0x200;
    public static final int REQUEST_CODE_PREVIEW_IMAGE = 0x201;
    public static final int REQUEST_CODE_UPLOAD_IMAGE = 0x202;
    public static final int REQUEST_CODE_IMAGE_PICKER = 0x203;
    public static final int REQUEST_CODE_RESULT_BACK = 0x204;
    public static final int REQUEST_CODE_SCAN = 0x205;



    private WebViewFragment mWebViewFragment;
    private String url;
    private String callbackName;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra(WEB_URL);
        callbackName = getIntent().getStringExtra(CALLBACK_NAME);
        if (TextUtils.isEmpty(url)) {
            url = "http://api.i.jkbs365.com/hybrid/example/";
        }
    }

    @Override
    protected void initView() {
        mWebViewFragment = WebViewFragment.newInstance(url, callbackName);
        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.container_webview, mWebViewFragment)
            .commit();
    }

    @Override
    public void onBackPressed() {
        mWebViewFragment.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SHARE) {
                String type = data.getStringExtra("type");
                String id = data.getStringExtra("id");
                String title = data.getStringExtra("title");
                String pic = data.getStringExtra("pic");
                String desc = data.getStringExtra("desc");
                String url = data.getStringExtra("url");
                String wx_url = data.getStringExtra("wx_url");
                showShare(type, id, title, pic, desc, url, wx_url);

            } else if (requestCode == REQUEST_CODE_PREVIEW_IMAGE) {
                int current = data.getIntExtra("current", 0);
                ArrayList<String> urls = (ArrayList<String>) data.getSerializableExtra("urls");
//                ArrayList<ImageInfo> images = new ArrayList<>();
//                for (int i = 0; i < urls.size(); i++) {
//                    ImageInfo imageInfo = new ImageInfo();
//                    imageInfo.thumbnailUrl = urls.get(i);
//                    imageInfo.bigImageUrl = urls.get(i);
//                    images.add(imageInfo);
//                }
//                Intent intentPreview = new Intent(this, ImagePreviewActivity.class);
//                intentPreview.putExtra(ImagePreviewActivity.IMAGE_INFO, images);
//                intentPreview.putExtra(ImagePreviewActivity.CURRENT_ITEM, current);
//                startActivity(intentPreview);

            } else if (requestCode == REQUEST_CODE_UPLOAD_IMAGE) {
                callbackName = data.getStringExtra("callback_name");
//                Intent intent = new Intent(mContext, ImageGridActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER);

            } else if (requestCode == REQUEST_CODE_RESULT_BACK) {
                String params = data.getStringExtra("key_params");
                String callbackName = data.getStringExtra("callback_name");
                mWebViewFragment.handleForResult(callbackName, params);

            } else if (requestCode == REQUEST_CODE_SCAN) {
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        mWebViewFragment.handleScanResult(result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        ToastUtils.showShort("解析二维码失败");
                    }
                }
            }
        } else if (resultCode == 433) {
//            if (data != null && requestCode == REQUEST_CODE_IMAGE_PICKER) {
//                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                if (images != null && images.size() > 0) {
//                    ArrayList<File> files = new ArrayList<>();
//                    for (int i = 0; i < images.size(); i++) {
//                        File newFile = CompressHelper.getDefault(mContext).compressToFile(new File(images.get(i).path));
//                        files.add(newFile);
//                    }
//                    formUpload(files);
//                }
//            } else {
//                ToastUtils.showShort("没有选择图片");
//            }
        } else {

        }
    }

    private void formUpload(ArrayList<File> files) {
        final ProgressDialog mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        OkGo.<String>post(MallAPI.UPLOAD_IMAGE)
            .tag(this)
            .addFileParams("upfile", files)
            .execute(new StringDialogCallback(mContext) {

                @Override
                public void onStart(Request<String, ? extends Request> request) {
                    super.onStart(request);
                    mProgressDialog.setMessage("正在上传中...");
                    mProgressDialog.show();
                }

                @Override
                public void onSuccess(Response<String> response) {
                    ToastUtils.showShort("上传成功");
                    mProgressDialog.dismiss();
//                    String cloud_key = responseData.data.getCloud_key();
//                    String url = responseData.data.getUrl();
//                    mWebViewFragment.handleUpload(callbackName, cloud_key, url);
                }

                @Override
                public void onError(com.lzy.okgo.model.Response<String> response) {
                    super.onError(response);
                    ToastUtils.showShort("上传失败");
                    mProgressDialog.dismiss();
                }

                @Override
                public void uploadProgress(Progress progress) {
                    super.uploadProgress(progress);
                    mProgressDialog.setProgress((int) (progress.fraction * 10000));
                }
            });
    }

    private void showShare(String type, String id, String title, String pic, String desc, String url, String wx_url) {
        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(title);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(desc);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(pic);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        // 启动分享GUI
        oks.show(mContext);
    }
}
