package com.oxygen.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 方向传感器监听类实现
 * @author oxygen
 * 
 */
public class MyOrientationListener implements SensorEventListener {

	private Context context;
	private SensorManager sensorManager;
	private Sensor aSensor;
	private Sensor mSensor;
	float[] accelerometerValues = new float[3];
	float[] magneticFieldValues = new float[3];
	private float lastX=0f;

	private OnOrientationListener onOrientationListener;//声明内部监听接口类对象

	/**
	 * Constructor
	 * @param context
	 */
	public MyOrientationListener(Context context) {
		this.context = context;
	}

	/**
	* @param 
	* @return void
	* @Description 	开始判断方向 
	*/
	public void start() {
		// 获得传感器管理器
		sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null) {// 获得加速度和磁力传感器	
			aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			mSensor = sensorManager
					.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		}
		if (aSensor != null && mSensor != null) {// 注册两个传感器
			sensorManager.registerListener(this, aSensor,
					SensorManager.SENSOR_DELAY_UI);
			sensorManager.registerListener(this, mSensor,
					SensorManager.SENSOR_DELAY_UI);
		}

	}

	/**
	* @param 
	* @return void
	* @Description 停止方向检测  
	*/
	public void stop() {
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {

		if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			magneticFieldValues = sensorEvent.values;
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			accelerometerValues = sensorEvent.values;
		float[] values = new float[3];// [0]为Z轴旋转方向,[1]为X轴前后,[2]为Y轴左右
		float[] R = new float[9];
		SensorManager.getRotationMatrix(R, null, accelerometerValues,
				magneticFieldValues);
		SensorManager.getOrientation(R, values);//加速度传感器和磁场传感器矩阵方向坐标转换
		values[0] = (float) Math.toDegrees(values[0]);//度数转换
		float x = values[0];
		if (x!=0f&&Math.abs(x - lastX) > 2.0) {//设置触发监听事件的方向改变阈值
			onOrientationListener.onOrientationChanged(x);
		}
		lastX = x;
	}

	/**
	* @param @param onOrientationListener
	* @return void
	* @Description 设置监听事件  
	*/
	public void setOnOrientationListener(
			OnOrientationListener onOrientationListener) {
		this.onOrientationListener = onOrientationListener;
	}

	/**
	* @ClassName OnOrientationListener
	* @Description 内部监听器接口及回调方法声明
	*/
	public interface OnOrientationListener {
		void onOrientationChanged(float x);
	}

}
