package com.wanchang.employee.ui.push;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Article;
import com.wanchang.employee.ui.base.BaseActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleDetailActivity extends BaseActivity{

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;
  @BindView(R.id.tv_title)
  TextView mArtileTitleTv;
  @BindView(R.id.tv_time)
  TextView mArtileTimeTv;
  @BindView(R.id.webView)
  WebView mWebView;

  private String id;

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_article_detail;
  }

  @Override
  protected void initData() {
    id = getIntent().getStringExtra("id");
  }

  @Override
  protected void initView() {
    mTitleTv.setText("公告");

    WebSettings webSetting = mWebView.getSettings();
    webSetting.setDomStorageEnabled(true);
    webSetting.setJavaScriptEnabled(true);
    webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

    loadData();
  }


  private void loadData() {
    OkGo.<String>get(MallAPI.ARTICLE+"/"+id)
        .tag(this)
        .params("expand", "content")
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              Article article = JSON.parseObject(response.body(), Article.class);
              mArtileTitleTv.setText(article.getTitle());
              mArtileTimeTv.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(article.getCreated_at()*1000)));
              mWebView.loadData(article.getContent().getContent(), null, null);
            }
          }
        });
  }

  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }
}
