package com.oxygen.map;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
* @ClassName MapApplication
* @Description 百度地图API开发Key全局初始化
* @author oxygen
* @email oxygen0106@163.com
* @date 2014-8-13 下午12:40:08
*/
public class MapApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
	}

}