package com.wanchang.employee.data.callback;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;

/**
 * Created by stormlei on 2017/5/8.
 */

public class ApiException {

  public ApiException(Response<String> response) {
    switch (response.code()) {
      case 401:
        ToastUtils.showShort("未登录");
        break;
      case 403:
        ToastUtils.showShort("没有权限");
        break;
      case 404:
        ToastUtils.showShort("请求的资源没有找到");
        break;
      case 405:
        ToastUtils.showShort("不支持此方法");
        break;
      case 422:
        ToastUtils.showShort("数据格式验证失败");
        break;
      case 500:
        ToastUtils.showShort("服务器错误");
        break;
      default:
        ToastUtils.showShort("未知错误");
        break;
    }
  }
}
