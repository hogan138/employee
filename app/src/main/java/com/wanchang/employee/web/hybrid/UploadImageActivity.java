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


@Router("uploadImage")
public class UploadImageActivity extends AppCompatActivity {

    private static final String TAG = UploadImageActivity.class.getSimpleName();

    private String callbackName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    protected void initData() {
        callbackName = getIntent().getStringExtra("callback");
        String params = getIntent().getStringExtra("params");
        if (TextUtils.isEmpty(params)) return;
        JSONObject jsonObj = JSON.parseObject(params);
        int count = jsonObj.getInteger("count");
        JSONArray sizeType = jsonObj.getJSONArray("sizeType");
        JSONArray sourceType = jsonObj.getJSONArray("sourceType");
        LogUtils.e(TAG, callbackName);
        LogUtils.e(TAG, count);
        LogUtils.e(TAG, sizeType);
        LogUtils.e(TAG, sourceType);

        //ImagePicker.getInstance().setSelectLimit(count);
    }


    private void initView() {
        Intent data = new Intent();
        data.putExtra("callback_name", callbackName);
        setResult(RESULT_OK, data);
        finish();
    }
}


