package com.lwc.coordinatorlayoutorbehavior;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * 定位test
 */

public class GPSLocationActivity extends AppCompatActivity {

    private TextView mTextView;
    private LocationClient mLocationClient;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLocate = true;
    private MyLocationListener mMyLocationListener;

    //位置提醒
//    BDNotifyListener  myBDNotifyListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化位置
        mLocationClient = new LocationClient(this.getApplicationContext());
        mLocationClient.registerLocationListener(mMyLocationListener = new MyLocationListener());
//        mLocationClient.registerNotify(myListener);
        //注册监听函数

        //初始化地图--需在setContentView之前
        SDKInitializer.initialize(this.getApplicationContext());

        setContentView(R.layout.activity_gpslocation);

        mTextView = (TextView) this.findViewById(R.id.tv_position);
        //地图
        mMapView = (MapView) this.findViewById(R.id.mapView);
        //地图控制器
        mBaiduMap = mMapView.getMap();
        //地图上显示我
        mBaiduMap.setMyLocationEnabled(true);


        //请求权限
        List<String> permissionList = new ArrayList<>();
        if ((ContextCompat.checkSelfPermission(GPSLocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if ((ContextCompat.checkSelfPermission(GPSLocationActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if ((ContextCompat.checkSelfPermission(GPSLocationActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            //请求定位
            requestLocation();
        }


    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    /**
     * 实时更新
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //
        option.setScanSpan(1000);
        //获取地址信息
        option.setIsNeedAddress(true);

        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        mLocationClient.setLocOption(option);
    }

    /**
     * 实现定位监听
     */
    private class MyLocationListener extends BDAbstractLocationListener {

        //这是在子线程中
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
                    currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
                    currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
                    currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
                    currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
                    currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
                    currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");

                    currentPosition.append("定位方式：");
                    if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                        currentPosition.append("GPS定位");
                    } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                        currentPosition.append("网络定位");
                    }

                    mTextView.setText(currentPosition);
                }
            });

            //移动到我的位置
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation ||
                    bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            }

        }


    }

    /**
     * 移动到我的位置
     *
     * @param bdLocation
     */
    private void navigateTo(BDLocation bdLocation) {
        //是否首次调用
        if (isFirstLocate) {
            //定位经纬度
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            //设置缩放级别
            update = MapStatusUpdateFactory.zoomTo(16f);
            mBaiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }

        //地图上显示我的位置
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(bdLocation.getLatitude());
        locationBuilder.longitude(bdLocation.getLongitude());
        MyLocationData myLocationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(myLocationData);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须全部同意所有权限才能使用！", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(mMyLocationListener);
        mLocationClient.stop();
        mMapView.onDestroy();
        //地图上显示我
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
