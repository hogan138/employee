package com.wanchang.employee.web.hybrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.github.mzule.activityrouter.annotation.Router;


@Router("share")
public class ShareActivity extends AppCompatActivity {

    private static final String TAG = ShareActivity.class.getSimpleName();

    private String type, id, title, pic, desc, url, wx_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    protected void initData() {
        String params = getIntent().getStringExtra("params");
        if (TextUtils.isEmpty(params)) return;
        JSONObject jsonObj = JSON.parseObject(params);
        type = jsonObj.getString("type");
        id = jsonObj.getString("id");
        title = jsonObj.getString("title");
        pic = jsonObj.getString("pic");
        desc = jsonObj.getString("desc");
        url = jsonObj.getString("url");
        wx_url = jsonObj.getString("wx_url");
        LogUtils.e(TAG, type);
        LogUtils.e(TAG, id);
        LogUtils.e(TAG, title);
        LogUtils.e(TAG, pic);
        LogUtils.e(TAG, desc);
        LogUtils.e(TAG, url);
        LogUtils.e(TAG, wx_url);
    }


    private void initView() {
        Intent data = new Intent();
        data.putExtra("type", type);
        data.putExtra("id", id);
        data.putExtra("title", title);
        data.putExtra("pic", pic);
        data.putExtra("desc", desc);
        data.putExtra("url", url);
        data.putExtra("wx_url", wx_url);
        setResult(RESULT_OK, data);
        finish();
    }
}


