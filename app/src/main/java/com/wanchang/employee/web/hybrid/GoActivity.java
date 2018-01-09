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
import com.wanchang.employee.ui.MainActivity;
import com.wanchang.employee.web.WebViewActivity;


@Router({"go", "replace"})
public class GoActivity extends AppCompatActivity {

    private static final String TAG = GoActivity.class.getSimpleName();

    private String to, type, otherKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        String params = getIntent().getStringExtra("params");
        if (TextUtils.isEmpty(params)) return;
        JSONObject jsonObj = JSON.parseObject(params);
        to = jsonObj.getString("to");
        type = jsonObj.getString("type");
        otherKey = jsonObj.getString("otherkey");
        LogUtils.e(TAG, to);
        LogUtils.e(TAG, type);
        LogUtils.e(TAG, otherKey);
    }


    private void initView() {
        if ("web".equals(type)) {
            startActivity(new Intent(GoActivity.this, WebViewActivity.class).putExtra(WebViewActivity.WEB_URL, to));
        } else {
            startActivity(new Intent(GoActivity.this, MainActivity.class));
        }
        finish();
    }


}
