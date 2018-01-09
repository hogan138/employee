package com.wanchang.employee.ui.salesman.me;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureSupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mylhyl.circledialog.CircleDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Client;
import com.wanchang.employee.ui.base.BaseActivity;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import java.util.List;

public class SignActivity extends BaseActivity implements LocationSource, AMapLocationListener {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;

  @BindView(R.id.tv_topbar_right)
  TextView mRightTv;


  private AMap aMap;
  private OnLocationChangedListener mListener;
  private AMapLocationClient mlocationClient;
  private AMapLocationClientOption mLocationOption;


  @BindView(R.id.tv_loc_address)
  TextView mLocAddressTv;

  @BindView(R.id.rv_client)
  RecyclerView mClientRv;

  private BaseQuickAdapter<Client, BaseViewHolder> mAdapter;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_sign;
  }

  @Override
  protected void initData() {
    RxPermissions rxPermissions = new RxPermissions(mContext);
    rxPermissions
        .request(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)
        .subscribe(new Consumer<Boolean>() {
          @Override
          public void accept(@NonNull Boolean aBoolean) throws Exception {
            if (aBoolean) {
              setUpMapIfNeeded();
            }else {
              ToastUtils.showShort("获取位置权限失败，请手动开启");
            }
          }
        });

    mClientRv.setLayoutManager(new LinearLayoutManager(mContext));
    mClientRv.setAdapter(mAdapter = new BaseQuickAdapter<Client, BaseViewHolder>(R.layout.item_sign_list) {

      @Override
      protected void convert(BaseViewHolder helper, Client item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.addOnClickListener(R.id.tv_sign);
      }
    });

    mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int id = mAdapter.getItem(position).getId();
        switch (view.getId()) {
          case R.id.tv_sign:
            addSign(id);
            break;
        }
      }
    });
  }

  private void addSign(final int id) {
    new CircleDialog.Builder(this)
        .setTitle("提示")
        .setText("您确定要签到吗？")
        .setNegative("取消", new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        })
        .setPositive("确定", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            OkGo.<String>post(MallAPI.SIGN)
                .tag(this)
                .params("client_id", id)
                .execute(new StringDialogCallback(mContext) {

                  @Override
                  public void onSuccess(Response<String> response) {
                    super.onSuccess(response);
                    if (response.code() == 200 || response.code() == 201) {
                      ToastUtils.showShort("签到成功");
                    }
                  }
                });
          }
        }).show();

  }

  @Override
  protected void initView() {
    mTitleTv.setText("签到");

    Drawable rightDrawable = getResources().getDrawable(R.drawable.nav_sign_history);
    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
    mRightTv.setCompoundDrawables(null, null, rightDrawable, null);
  }

  private void getSignClient(double lat, double lon) {
    OkGo.<String>get(MallAPI.SIGN_CLIENT_LIST)
        .tag(this)
        .params("lat", lat)
        .params("lon", lon)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              List<Client> clientList = JSON.parseArray(response.body(), Client.class);
              mAdapter.setNewData(clientList);
            }
          }
        });
  }


  private void setUpMapIfNeeded() {
    if (aMap == null) {
      TextureSupportMapFragment supportMapFragment = (TextureSupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
      aMap = supportMapFragment.getMap();

      configMap();
    }
  }

  private void configMap() {
    aMap.setLocationSource(this);
    aMap.getUiSettings().setMyLocationButtonEnabled(false);
    aMap.getUiSettings().setZoomControlsEnabled(false);
    aMap.getUiSettings().setZoomGesturesEnabled(true);
    aMap.setMyLocationEnabled(true);
//    MyLocationStyle myLocationStyle = new MyLocationStyle();
//    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.home_maparrow));
//    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
//    aMap.setMyLocationStyle(myLocationStyle);
  }


  /**
   * 激活定位
   */
  @Override
  public void activate(OnLocationChangedListener onLocationChangedListener) {
    mListener = onLocationChangedListener;
    if (mlocationClient == null) {
      mlocationClient = new AMapLocationClient(mContext);
      mLocationOption = new AMapLocationClientOption();
      //设置定位监听
      mlocationClient.setLocationListener(this);
      //设置为高精度定位模式
      mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
      //获取最近3s内精度最高的一次定位结果：
      //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
      //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
      mLocationOption.setOnceLocation(true);
      //SDK默认采用连续定位模式，时间间隔2000ms。设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
      //mLocationOption.setInterval(1000);
      mLocationOption.setLocationCacheEnable(false);
      //设置定位参数
      mlocationClient.setLocationOption(mLocationOption);
      // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
      // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
      // 在定位结束后，在合适的生命周期调用onDestroy()方法
      // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
      mlocationClient.startLocation();
    }
  }


  /**
   * 停止定位
   */
  @Override
  public void deactivate() {
    mListener = null;
    if (mlocationClient != null) {
      mlocationClient.stopLocation();
      mlocationClient.onDestroy();
    }
    mlocationClient = null;
  }


  /**
   * 定位成功后回调函数
   */
  @Override
  public void onLocationChanged(AMapLocation amapLocation) {
    if (mListener != null && amapLocation != null) {
      LogUtils.e(amapLocation.getAddress()+amapLocation.getLatitude());
      if (amapLocation != null
          && amapLocation.getErrorCode() == 0) {
        //mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
        LatLng locLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locLatLng, 15), 1000, null);
        addLocArrow(locLatLng, R.drawable.loc_arrow);

        mLocAddressTv.setText(amapLocation.getAddress());

        getSignClient(amapLocation.getLatitude(), amapLocation.getLongitude());
      } else {
        String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
        Log.e("AmapErr",errText);
        ToastUtils.showShort(errText);
      }
    }
  }

  private Marker arrowMarker;
  private void addLocArrow(LatLng latLng, int resId) {
    //添加Marker显示定位位置
    if (arrowMarker == null) {
      //如果是空的添加一个新的,icon方法就是设置定位图标，可以自定义
      arrowMarker = aMap.addMarker(new MarkerOptions()
          .position(latLng)
          .icon(BitmapDescriptorFactory.fromResource(resId)));
    } else {
      //已经添加过了，修改位置即可
      arrowMarker.setPosition(latLng);
    }
  }

  @OnClick(R.id.tv_re_loc)
  public void onLocation() {
    LogUtils.e("locloc--"+mlocationClient);
    if (mlocationClient != null) {
      mlocationClient.startLocation();
    }
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
    deactivate();
//    if(null != mlocationClient){
//      mlocationClient.onDestroy();
//    }
  }



  @OnClick(R.id.tv_topbar_left)
  public void onGoBack() {
    finish();
  }

  @OnClick(R.id.tv_topbar_right)
  public void onGoSignHistory() {
    startActivity(new Intent(mContext, SignHistoryActivity.class));
  }
}
