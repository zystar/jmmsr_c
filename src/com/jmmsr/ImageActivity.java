package com.jmmsr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ImageActivity extends Activity{
	

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /* 隐藏状态栏 */
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* 隐藏标题栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image);

    	Intent i = getIntent();
    	String path = i.getStringExtra("p");
    	
    	ImageView ai_imageview = (ImageView)findViewById(R.id.ai_imageview);
    	
    	//为ImageView设置要显示的图片
		Bitmap bm=BitmapFactory.decodeFile(path);
		ai_imageview.setImageBitmap(bm);
        
    }

}