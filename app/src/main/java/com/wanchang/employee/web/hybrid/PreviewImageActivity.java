package com.wanchang.employee.web.hybrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.github.mzule.activityrouter.annotation.Router;


@Router("previewImage")
public class PreviewImageActivity extends AppCompatActivity {

    private static final String TAG = PreviewImageActivity.class.getSimpleName();

    private int current;
    private JSONArray urls;

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
        current = jsonObj.getInteger("current");
        urls = jsonObj.getJSONArray("urls");
        LogUtils.e(TAG, current);
        LogUtils.e(TAG, urls);
    }


    private void initView() {
        Intent data = new Intent();
        data.putExtra("current", current);
        data.putExtra("urls", urls);
        setResult(RESULT_OK, data);
        finish();
    }
}


