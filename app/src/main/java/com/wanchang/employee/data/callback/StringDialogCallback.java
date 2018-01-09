package com.wanchang.employee.data.callback;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.wanchang.employee.app.Constants;
import com.wanchang.employee.app.MallApp;
import com.wanchang.employee.ui.LoginActivity;
import com.wanchang.employee.util.ACache;


public abstract class StringDialogCallback extends StringCallback {

//    private ProgressDialog dialog;
    private Activity activity;

    public StringDialogCallback(Activity activity) {
        this.activity = activity;
//        dialog = new ProgressDialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setMessage("请求网络中...");
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
//        if (dialog != null && !dialog.isShowing()) {
//            dialog.show();
//        }
        request
            .headers("access-token", MallApp.getInstance().getToken())
            .headers("client", "20");

        String department_id = ACache.get(activity).getAsString(Constants.KEY_DEPARTMENT_ID);
        if (!TextUtils.isEmpty(department_id)) {
            request.headers("department-id", "600");
        }
        String role_id = ACache.get(activity).getAsString(Constants.KEY_ROLE_ID);
        if (!TextUtils.isEmpty(role_id)) {
            request.headers("role-id", "602");
        }
    }

    @Override
    public void onFinish() {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
    }

    @Override
    public void onSuccess(Response<String> response) {
        if (response.code() == 400) {
            JSONObject jsonObj = JSON.parseObject(response.body());
            ToastUtils.showShort(jsonObj.getString("message"));
        }
        if (response.code() == 401) {
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        new ApiException(response);
    }
}
