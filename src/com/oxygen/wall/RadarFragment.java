package com.oxygen.wall;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfigeration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

public class RadarFragment extends Fragment {

	Activity activity;
	public MapView mapView;
	BaiduMap baiduMap;
	BaiduMapOptions options;
	MapStatus mMapStatus;
	RelativeLayout mapViewContainer;
	public LocationClient mLocationClient;
	public BDLocationListener myListener;
	public double mCurrentLantitude;
	public double mCurrentLongitude;
	public float mCurrentAccracy;
	MyLocationData locData;
	BitmapDescriptor mCurrentMarker;
	MyLocationConfigeration config;

	// public MyOrientationListener myOrientationListener;
	// public float myDirection;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mCurrentLantitude = savedInstanceState.getDouble("Lantitude");
			mCurrentLongitude = savedInstanceState.getDouble("Longitude");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myListener = new MyLocationListener();
		View view = inflater.inflate(R.layout.radar, container, false);
		mapViewContainer = (RelativeLayout) view
				.findViewById(R.id.mapView_container);
		mMapStatus = new MapStatus.Builder()
				.target(new LatLng(mCurrentLantitude, mCurrentLongitude))
				.zoom(16f).build();// 设置地图显示的起始位置和默认缩放比例
		options = new BaiduMapOptions().zoomControlsEnabled(false).mapStatus(
				mMapStatus);// 不显示缩放控件
		mapView = new MapView(activity, options);
		mapViewContainer.addView(mapView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mapView.removeViewAt(1);// 去掉 BaiDu LOGO
		mapView.removeViewAt(2);// 去掉 比例尺
		mCurrentMarker = BitmapDescriptorFactory
				.fromResource(R.drawable.route_map_icon_nav);//定位点图标
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baiduMap = mapView.getMap();// MapView的管理器
		config = new MyLocationConfigeration(
				LocationMode.FOLLOWING, false, mCurrentMarker);
		baiduMap.setMyLocationConfigeration(config);
		// initMyOrientation();//获取方向
		initMyLocation();// 初始化LocationClient,设置监听器和定位参数实例

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		if (mCurrentLantitude != 0.0 || mCurrentLantitude != 39.98871) {
			outState.putDouble("Lantitude", mCurrentLantitude);
			outState.putDouble("Longitude", mCurrentLongitude);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();// MapView需要重写Activity的生命周期方法
		baiduMap.setMyLocationEnabled(true);
		 if (!mLocationClient.isStarted()) {
		mLocationClient.start();
		 }
		// myOrientationListener.start();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 baiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		// myOrientationListener.stop();
		mapView.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}

	public void initMyLocation() {
		mLocationClient = new LocationClient(activity.getApplicationContext());
		mLocationClient.registerLocationListener(myListener);// 注册监听函数
		// 定位参数的相关配置
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开GPS
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		mLocationClient.setLocOption(option);
	}

	// 重写定位监听的回调方法
		public class MyLocationListener implements BDLocationListener {
			@Override
			// BDLocation封装了定位SDK的定位结果，在BDLocationListener的onReceive方法中获取。
			public void onReceiveLocation(BDLocation location) {
				if (location == null || mapView == null)// mapView 销毁后不在处理新接收的位置
					return;
				// 构造定位数据对象，从location中获取
				locData = new MyLocationData.Builder()
						.accuracy(location.getRadius())
						.latitude(location.getLatitude())
						.longitude(location.getLongitude()).build();

				baiduMap.setMyLocationData(locData);
				// 更新当前坐标数据
				mCurrentAccracy = location.getRadius();
				mCurrentLantitude = location.getLatitude();
				mCurrentLongitude = location.getLongitude();
			}
		}
	
		public void getAroundWalls(){
			List<WallInfo> aroundWalls = new ArrayList<WallInfo>();
			aroundWalls.add(new WallInfo(1, 007, new LatLng(39.98871, 116.43234), 1000));
		}

		public void initOverlay(){
			
		}
}
