package com.oxygen.main;

import com.oxygen.wall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
* @ClassName MyFragment
* @Description 用户
* @author oxygen
* @email oxygen0106@163.com
* @date 2014-8-18 下午09:47:52
*/
public class MyFragment extends Fragment {
	 private TextView tv;  
	  
	    @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
	        return inflater.inflate(R.layout.my_fragment, container, false);  
	    }  
	  
	    @Override  
	    public void onActivityCreated(Bundle savedInstanceState) {  
	        super.onActivityCreated(savedInstanceState);  
	        tv = (TextView) getView().findViewById(R.id.tv);  
	        tv.setText("My");  
	    } 
}

