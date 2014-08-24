package com.oxygen.main;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfigeration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.oxygen.wall.R;
import com.oxygen.wall.WallInfo;
//import com.oxygen.map.MyOrientationListener;
//import com.oxygen.map.MyOrientationListener.OnOrientationListener;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

/**
* @ClassName RadarFragment
* @Description 在地图上显示用户附近的虚拟留言板，及相关界面交互
* @author oxygen
* @email oxygen0106@163.com
* @date 2014-8-18 下午09:31:15
*/
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
	BitmapDescriptor mCurrentMarkerIcon = BitmapDescriptorFactory
			.fromResource(R.drawable.route_map_icon_nav);// 定位点图标;
	MyLocationConfigeration config;//定位模式设置

	BitmapDescriptor wallMarkerIcon = BitmapDescriptorFactory
			.fromResource(R.drawable.wall_marker);// 留言板标记图标bitmap
	Marker[] wallMarker;//附件留言板在地图上的标记
	List<WallInfo> aroundWalls;// 附近留言板信息
	private InfoWindow mInfoWindow;

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
		View view = inflater.inflate(R.layout.radar_fragment, container, false);
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
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		baiduMap = mapView.getMap();// MapView的管理器 baiduMap
		config = new MyLocationConfigeration(LocationMode.FOLLOWING, false,
				mCurrentMarkerIcon);
		baiduMap.setMyLocationConfigeration(config);

//		initMyOrientation();//获取方向
		initMyLocation();// 初始化LocationClient,设置监听器和定位参数实例
		getAroundWalls();// 本地模拟数据
		initOverlay();// 添加图层
		wallMarkerClickListener();//留言板标记Marker监听
		
		
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
		mCurrentMarkerIcon.recycle();//回收 bitmap 资源
		wallMarkerIcon.recycle();//回收 bitmap 资源
	}

	/**
	 * @param
	 * @return void
	 * @Description 初始化 LocationClient及参数，添加定位监听
	 */
	public void initMyLocation() {
		mLocationClient = new LocationClient(activity.getApplicationContext());
		mLocationClient.registerLocationListener(myListener);// 注册监听函数
		// 定位参数的相关配置
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开GPS
		option.setCoorType("bd09ll"); // 设置坐标类型，百度坐标
		option.setScanSpan(5000);// 更新间隔时间
		mLocationClient.setLocOption(option);
	}

	/**
	 * @ClassName MyLocationListener
	 * @Description 实现内部接口BDLocationListener，重写定位监听的回调方法
	 * @author oxygen
	 * @email oxygen0106@163.com
	 * @date 2014-8-24 下午12:31:19
	 */
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

	/**
	* @param 
	* @return void
	* @Description 监听定位方向，重新MyOrientationListener传感器监听的回调方法
	*/
/*
	public void initMyOrientation(){
		myOrientationListener = new MyOrientationListener(getApplicationContext());
		myOrientationListener.setOnOrientationListener(new OnOrientationListener(){
			@Override//回调方法
			public void onOrientationChanged(float x)
			{
				myDirection = x;
				// 构造定位数据
				MyLocationData locData = new MyLocationData.Builder()
						.accuracy(mCurrentAccracy)
						// 此处设置获取到的方向信息，顺时针0-360
						.direction(myDirection)
						.latitude(mCurrentLantitude)
						.longitude(mCurrentLongitude).build();
				// 设置定位数据
				baiduMap.setMyLocationData(locData);
				// 设置自定义图标
				BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
						.fromResource(R.drawable.route_map_icon_nav);
				MyLocationConfigeration config = new MyLocationConfigeration(
						LocationMode.FOLLOWING, true, mCurrentMarker);
				baiduMap.setMyLocationConfigeration(config);
			}
		});
	}
	
*/	
	/**
	 * @param
	 * @return void
	 * @Description 获取模拟附近留言板信息 ，本地模拟
	 */
	public void getAroundWalls() {
		aroundWalls = new ArrayList<WallInfo>();
		// aroundWalls.add(new WallInfo(1, 007, new LatLng(39.98871, 116.43234),
		// 1000));//天安门坐标
		// aroundWalls.add(new WallInfo(1, 007, new LatLng(29.536881,
		// 106.611048), 1000));//信科坐标
		aroundWalls.add(new WallInfo(1, 007, new LatLng(29.533881, 106.611048),
				1000));
		aroundWalls.add(new WallInfo(2, 007, new LatLng(29.539881, 106.611048),
				1000));
		aroundWalls.add(new WallInfo(3, 007, new LatLng(29.536881, 106.608048),
				1000));
		aroundWalls.add(new WallInfo(4, 007, new LatLng(29.536881, 106.614048),
				1000));
	}

	/**
	 * @param
	 * @return void
	 * @Description 在地图上添加留言板标记，并实例化对应的Marker
	 */
	public void initOverlay() {
		int num = aroundWalls.size();
		wallMarker = new Marker[num];// 根据附近留言板数量实例化Marker数组大小
		for (int i = 0; i < num; i++) {

			OverlayOptions oo = new MarkerOptions()
					.position(aroundWalls.get(i).getlocation())
					.icon(wallMarkerIcon).zIndex(9 - i);//zIndex()在Z轴上的比例
			wallMarker[i] = (Marker) (baiduMap.addOverlay(oo));// 在地图中添加标记，并实例化Marker
		}
	}
	
	/**
	* @param @param view
	* @return void
	* @Description 清除所有地图标记覆盖  
	*/
	public void clearOverlay(View view) {
		baiduMap.clear();
	}
	
	/**
	* @param 
	* @return void
	* @Description 留言板标记Marker监听  
	*/
	public void wallMarkerClickListener(){
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				// TODO Auto-generated method stub
				Button button = new Button(activity.getApplicationContext());
				button.setBackgroundResource(R.drawable.popup);
				button.getBackground().setAlpha(200);// 设置弹窗背景Alpha值
				final LatLng markerLatLng = marker.getPosition();
				Point p = baiduMap.getProjection().toScreenLocation(markerLatLng);
				p.y -= 47;//向上移动至Marker的顶部
				LatLng llInfo = baiduMap.getProjection().fromScreenLocation(p);//转换成坐标
				OnInfoWindowClickListener listener = null;
				
				for (int i = 0; i < wallMarker.length; i++) {
					if (marker == wallMarker[i]) {
						button.setText(aroundWalls.get(i).getWallId() + "");
						listener = new OnInfoWindowClickListener() {
							public void onInfoWindowClick() {//点击弹窗事件处理
								clickInfoWindow(); //弹窗点击事件执行
							}
						};
					}
				}
				mInfoWindow = new InfoWindow(button, llInfo, listener);
				baiduMap.showInfoWindow(mInfoWindow);
				return true;
			}
		});
	}
	
	/**
	* @param 
	* @return void
	* @Description Marker弹窗点击事件执行  
	*/
	public void clickInfoWindow(){
		//TODO
		baiduMap.hideInfoWindow();
	}
}
