package com.oxygen.wall;

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
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_bar);//自定义标题栏

		titleText = (TextView) findViewById(R.id.title_tv);

		wallFrameLayout = (FrameLayout) findViewById(R.id.framelayout_wall);
		radarFrameLayout = (FrameLayout) findViewById(R.id.framelayout_radar);
		favourFrameLayout = (FrameLayout) findViewById(R.id.framelayout_favour);
		myFrameLayout = (FrameLayout) findViewById(R.id.framelayout_my);
		arFrameLayout = (FrameLayout) findViewById(R.id.framelayout_ar);
		
		wallFrameLayout.setTag(1);
		wallFrameLayout.setOnClickListener(this);
		radarFrameLayout.setTag(2);
		radarFrameLayout.setOnClickListener(this);
		favourFrameLayout.setTag(3);
		favourFrameLayout.setOnClickListener(this);
		myFrameLayout.setTag(4);
		myFrameLayout.setOnClickListener(this);
		arFrameLayout.setTag(5);
		arFrameLayout.setOnClickListener(this);

		wallImageView = (ImageView) findViewById(R.id.image_wall);
		radarImageView = (ImageView) findViewById(R.id.image_radar);
		favourImageView = (ImageView) findViewById(R.id.image_favour);
		myImageView = (ImageView) findViewById(R.id.image_my);
		arImageView = (ImageView) findViewById(R.id.image_ar);

		wallImageLine = (ImageView) findViewById(R.id.wall_image_line);
		radarImageLine = (ImageView) findViewById(R.id.radar_image_line);
		favourImageLine = (ImageView) findViewById(R.id.favour_image_line);
		myImageLine = (ImageView) findViewById(R.id.my_image_line);

		radarFragment = new RadarFragment();

		// 默认初始化WallFragmnt
		titleText.setText("Wall");
		fragmentManager = getSupportFragmentManager();

		wallFragment = fragmentManager.findFragmentById(R.id.wallfragment);
		// radarFragment = fragmentManager.findFragmentById(R.id.radarfragment);//改为动态加载
		favourFragment = fragmentManager.findFragmentById(R.id.favourfragment);
		myFragment = fragmentManager.findFragmentById(R.id.myfragment);

		fragmentTransaction = fragmentManager.beginTransaction()
				.hide(wallFragment).hide(favourFragment).hide(myFragment);
		fragmentTransaction.show(wallFragment).commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int tag = (Integer) v.getTag();
		// fragmentTransaction只能commit()一次,此处重新获得一个局部对象来commit()
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
				fragmentTransaction.add(R.id.container, radarFragment);
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
