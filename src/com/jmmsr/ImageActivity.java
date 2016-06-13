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
        
        /* ����״̬�� */
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* ���ر����� */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image);

    	Intent i = getIntent();
    	String path = i.getStringExtra("p");
    	
    	ImageView ai_imageview = (ImageView)findViewById(R.id.ai_imageview);
    	
    	//ΪImageView����Ҫ��ʾ��ͼƬ
		Bitmap bm=BitmapFactory.decodeFile(path);
		ai_imageview.setImageBitmap(bm);
        
    }

}