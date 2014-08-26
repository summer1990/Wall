package com.oxygen.main;

import com.oxygen.wall.R;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName MainActivity
 * @Description 主界面
 * @author oxygen
 * @email oxygen0106@163.com
 * @date 2014-8-14 下午1:18:37
 */
public class MainActivity extends FragmentActivity implements
		View.OnClickListener {

	public Fragment wallFragment;
	public RadarFragment radarFragment;
	public Fragment favourFragment;
	public Fragment myFragment;

	public FrameLayout wallFrameLayout;
	public FrameLayout radarFrameLayout;
	public FrameLayout favourFrameLayout;
	public FrameLayout myFrameLayout;
	public FrameLayout arFrameLayout;
	public FrameLayout container;

	public ImageView wallImageView;
	public ImageView radarImageView;
	public ImageView favourImageView;
	public ImageView myImageView;
	public ImageView arImageView;

	public ImageView wallImageLine;
	public ImageView radarImageLine;
	public ImageView favourImageLine;
	public ImageView myImageLine;

	public TextView titleText;

	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义标题栏模式
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_bar);// 加载自定义标题栏
//		ActionBar actionBar = getActionBar();//API Level 11
		
		titleText = (TextView) findViewById(R.id.title_tv);// 标题栏TextView

		wallFrameLayout = (FrameLayout) findViewById(R.id.framelayout_wall);// 菜单按钮
		radarFrameLayout = (FrameLayout) findViewById(R.id.framelayout_radar);
		favourFrameLayout = (FrameLayout) findViewById(R.id.framelayout_favour);
		myFrameLayout = (FrameLayout) findViewById(R.id.framelayout_my);
		arFrameLayout = (FrameLayout) findViewById(R.id.framelayout_ar);

		wallFrameLayout.setTag(1);// 按钮添加标签
		wallFrameLayout.setOnClickListener(this);// 按钮添加监听
		radarFrameLayout.setTag(2);
		radarFrameLayout.setOnClickListener(this);
		favourFrameLayout.setTag(3);
		favourFrameLayout.setOnClickListener(this);
		myFrameLayout.setTag(4);
		myFrameLayout.setOnClickListener(this);
		arFrameLayout.setTag(5);
		arFrameLayout.setOnClickListener(this);

		wallImageView = (ImageView) findViewById(R.id.image_wall);// 按钮图片
		radarImageView = (ImageView) findViewById(R.id.image_radar);
		favourImageView = (ImageView) findViewById(R.id.image_favour);
		myImageView = (ImageView) findViewById(R.id.image_my);
		arImageView = (ImageView) findViewById(R.id.image_ar);

		wallImageLine = (ImageView) findViewById(R.id.wall_image_line);// 按钮下划线
		radarImageLine = (ImageView) findViewById(R.id.radar_image_line);
		favourImageLine = (ImageView) findViewById(R.id.favour_image_line);
		myImageLine = (ImageView) findViewById(R.id.my_image_line);

		radarFragment = new RadarFragment();// 使用代码初始化radarFragment，动态加载

		titleText.setText("Wall");// 设置TitleBar的TextView
		fragmentManager = getSupportFragmentManager();// 获得FragmentManager
		wallFragment = fragmentManager.findFragmentById(R.id.wallfragment);// 加载Fragment
		// fragmentManager.findFragmentById(R.id.radarfragment);//改为动态加载,见上
		favourFragment = fragmentManager.findFragmentById(R.id.favourfragment);
		myFragment = fragmentManager.findFragmentById(R.id.myfragment);

		fragmentTransaction = fragmentManager.beginTransaction()
				.hide(wallFragment).hide(favourFragment)
				.hide(myFragment);//隐藏Fragment
		fragmentTransaction.show(wallFragment).commit();//默认显示wallFragment
	}

	/**
	* @Description 菜单按钮监听事件回调方法  
	*/
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int tag = (Integer) v.getTag();
		// 一个fragmentTransaction对象只能commit()一次,此处重新获得一个对象来commit()
		fragmentTransaction = fragmentManager.beginTransaction()
				.hide(wallFragment).hide(radarFragment).hide(favourFragment)
				.hide(myFragment);
		switch (tag) {
		case 1:
			clickWallFrameLayout();

			fragmentTransaction.show(wallFragment).commit();
			break;
		case 2:
			clickRadarFrameLayout();
			
			if (!radarFragment.isAdded()) {// 动态加载
				fragmentTransaction.add(R.id.fragment_container, radarFragment);
				fragmentTransaction.show(radarFragment).commit();
			} else {
				fragmentTransaction.show(radarFragment).commit();
			}
			break;
		case 3:
			clickFavourFrameLayout();

			fragmentTransaction.show(favourFragment).commit();
			break;
		case 4:
			clickMyFrameLayout();

			fragmentTransaction.show(myFragment).commit();
			break;
		case 5:
			clickARFrameLayout();
			// TODO
			break;
		}

	}

	/**
	* @param 
	* @return void
	* @Description WallFrameLayout按钮背景图片切换
	*/
	private void clickWallFrameLayout() {
		titleText.setText("Wall");

		wallFrameLayout.setSelected(true);
		wallImageView.setSelected(true);
		wallImageLine.setSelected(true);
		radarFrameLayout.setSelected(false);
		radarImageView.setSelected(false);
		radarImageLine.setSelected(false);
		favourFrameLayout.setSelected(false);
		favourImageView.setSelected(false);
		favourImageLine.setSelected(false);
		myFrameLayout.setSelected(false);
		myImageView.setSelected(false);
		myImageLine.setSelected(false);
		arImageView.setSelected(false);
	}

	/**
	* @param 
	* @return void
	* @Description RadarFrameLayout按钮背景图片切换
	*/
	private void clickRadarFrameLayout() {
		titleText.setText("Radar");

		radarFrameLayout.setSelected(true);
		radarImageView.setSelected(true);
		radarImageLine.setSelected(true);
		wallFrameLayout.setSelected(false);
		wallImageView.setSelected(false);
		wallImageLine.setSelected(false);
		favourFrameLayout.setSelected(false);
		favourImageView.setSelected(false);
		favourImageLine.setSelected(false);
		myFrameLayout.setSelected(false);
		myImageView.setSelected(false);
		myImageLine.setSelected(false);
		arImageView.setSelected(false);
	}

	/**
	* @param 
	* @return void
	* @Description FavourFrameLayout按钮背景图片切换  
	*/
	private void clickFavourFrameLayout() {
		titleText.setText("Favour");

		favourFrameLayout.setSelected(true);
		favourImageView.setSelected(true);
		favourImageLine.setSelected(true);
		wallFrameLayout.setSelected(false);
		wallImageView.setSelected(false);
		wallImageLine.setSelected(false);
		radarFrameLayout.setSelected(false);
		radarImageView.setSelected(false);
		radarImageLine.setSelected(false);
		myFrameLayout.setSelected(false);
		myImageView.setSelected(false);
		myImageLine.setSelected(false);
		arImageView.setSelected(false);
	}

	/**
	* @param 
	* @return void
	* @Description MyFrameLayout按钮背景图片切换   
	*/
	private void clickMyFrameLayout() {
		titleText.setText("My");

		myFrameLayout.setSelected(true);
		myImageView.setSelected(true);
		myImageLine.setSelected(true);
		wallFrameLayout.setSelected(false);
		wallImageView.setSelected(false);
		wallImageLine.setSelected(false);
		radarFrameLayout.setSelected(false);
		radarImageView.setSelected(false);
		radarImageLine.setSelected(false);
		favourFrameLayout.setSelected(false);
		favourImageView.setSelected(false);
		favourImageLine.setSelected(false);
		arImageView.setSelected(false);
	}

	/**
	* @param 
	* @return void
	* @Description ARFrameLayout  
	*/
	private void clickARFrameLayout() {
		titleText.setText("AR");

		arImageView.setSelected(true);
		myFrameLayout.setSelected(false);
		myImageView.setSelected(false);
		myImageLine.setSelected(false);
		wallFrameLayout.setSelected(false);
		wallImageView.setSelected(false);
		wallImageLine.setSelected(false);
		radarFrameLayout.setSelected(false);
		radarImageView.setSelected(false);
		radarImageLine.setSelected(false);
		favourFrameLayout.setSelected(false);
		favourImageView.setSelected(false);
		favourImageLine.setSelected(false);
	}

}
