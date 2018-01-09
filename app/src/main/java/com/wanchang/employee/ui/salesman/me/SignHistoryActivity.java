package com.wanchang.employee.ui.salesman.me;

import android.Manifest;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wanchang.employee.R;
import com.wanchang.employee.data.api.MallAPI;
import com.wanchang.employee.data.callback.StringDialogCallback;
import com.wanchang.employee.data.entity.Sign;
import com.wanchang.employee.ui.base.BaseActivity;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignHistoryActivity extends BaseActivity implements LocationSource, AMapLocationListener {

  @BindView(R.id.tv_topbar_title)
  TextView mTitleTv;


  private AMap aMap;
  private OnLocationChangedListener mListener;
  private AMapLocationClient mlocationClient;
  private AMapLocationClientOption mLocationOption;

  @BindView(R.id.tv_sign_count)
  TextView mSignCountTv;

  @BindView(R.id.rv_sign)
  RecyclerView mSignRv;

  private BaseQuickAdapter<Sign.ChartDetailBean, BaseViewHolder> mAdapter;


  @Override
  protected int getLayoutResId() {
    return R.layout.activity_sign_history;
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

    mSignRv.setLayoutManager(new LinearLayoutManager(mContext));
    mSignRv.setAdapter(mAdapter = new BaseQuickAdapter<Sign.ChartDetailBean, BaseViewHolder>(android.R.layout.simple_list_item_1) {

      @Override
      protected void convert(BaseViewHolder helper, Sign.ChartDetailBean item) {
        helper.setText(android.R.id.text1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(item.getCreated_at()*1000))+" "+item.getName());
      }
    });
  }

  @Override
  protected void initView() {
    mTitleTv.setText("签到历史");


  }

  private void getSignHistory() {
    OkGo.<String>get(MallAPI.SIGN_CHART)
        .tag(this)
        .execute(new StringDialogCallback(mContext) {

          @Override
          public void onSuccess(Response<String> response) {
            super.onSuccess(response);
            if (response.code() == 200) {
              Sign sign = JSON.parseObject(response.body(), Sign.class);
              mSignCountTv.setText("30日内签到"+sign.getTotal_count()+"次");
              mAdapter.setNewData(sign.getChart_detail());

              addMarks(sign.getChart_info());
            }
          }
        });
  }

  private void addMarks(List<Sign.ChartInfoBean> chartInfoBeanList) {
    for (Sign.ChartInfoBean item : chartInfoBeanList) {
      Marker marker = aMap.addMarker(new MarkerOptions());
      marker.setTitle(item.getName() + " " + item.getCount()+"次");
      LatLng latLng = new LatLng(item.getLat(), item.getLon());
      marker.setPosition(latLng);
      marker.showInfoWindow();
    }
  }

  private List<Marker> markerList = new ArrayList<Marker>();
  private void addMarkersToMap(List<Sign.ChartInfoBean> chartInfoBeanList) {
    ArrayList<MarkerOptions> markerOptionList = new ArrayList<MarkerOptions>();
    for (Sign.ChartInfoBean item : chartInfoBeanList) {
      if (item.getLat() < 200) {
        MarkerOptions markerOption = new MarkerOptions();
        LatLng latLng = new LatLng(item.getLat(), item.getLon());
        markerOption.title(item.getName()+" "+item.getCount()+"次").position(latLng)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));
        markerOptionList.add(markerOption);
      }

    }
    if (markerList != null) {
      for (Marker marker : markerList) {
        marker.remove();
      }
    }
    //在地图上添一组图片标记（marker）对象，并设置是否改变地图状态以至于所有的marker对象都在当前地图可视区域范围内显示。
    markerList = aMap.addMarkers(markerOptionList, true);

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

        getSignHistory();
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
}
