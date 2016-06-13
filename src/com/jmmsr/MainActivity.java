package com.jmmsr;


import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;
	ImageView iv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		//创建文件夹
		File PhotoDiary = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PhotoDiary");
		Log.e("hehe",Environment.getExternalStorageDirectory().getAbsolutePath() );
		if (!PhotoDiary.exists()) {
			PhotoDiary.mkdirs();
		}
		  
		init();

		//跳转到登录页面
		iv1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, com.jmmsr.login.LoginActivity.class);
				startActivity(i);
			}
		});
		//拍照页面
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,
						com.jmmsr.CameraActivity.class);
				startActivity(i);
			}
		});
		//相册
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,
						com.jmmsr.PhotoAlbumActivity.class);
				startActivity(i);
			}
		});
	}
	
	public void init(){
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		btn3 = (Button)findViewById(R.id.btn3);
		btn4 = (Button)findViewById(R.id.btn4);
		iv1 = (ImageView)findViewById(R.id.iv1);

	}

}
