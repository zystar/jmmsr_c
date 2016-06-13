package com.jmmsr;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

public class AddTextForImgActivity extends Activity {

	ImageView imgView;
	RelativeLayout relativeLayout1;
	View relativeLayout2;//点击后弹出的布局
	int clickImgX;
	int clickImgY;
	ImageView saveIv;
	Button pop_view_btn1;
	Button pop_view_btn2;
	String p;
//	int flag;//当点击右半个屏幕时，设置为1
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_add_text_for_img);

		//初始化
		init();

		Intent i = getIntent();
		final String path = i.getStringExtra("path");
		p = path;
		
		//旋转90度
		Matrix matrix=new Matrix();
//		ImageView imgView = (ImageView)findViewById(R.id.imgview);
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		matrix.postRotate(90);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
		imgView.setImageBitmap(bitmap);
		
		saveIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveIv.setVisibility(8);
				//调用截屏方法
				BitmapDrawable bd = new BitmapDrawable(shot());
				imgView.setBackgroundDrawable(bd);
				
				//保存截屏
				String fname = DateFormat.format("yyyyMMddhhmmss", new Date())
						.toString() + ".jpg";
//				String fname = ShareData.getPicName();
				Log.e("fname", fname);
				File picture = new File(Environment.getExternalStorageDirectory() + "/PhotoDiary/"
						+ fname);
				
				String path2 = picture.getPath();
				Log.e("path2", path2);

				try {
					FileOutputStream fos = new FileOutputStream(path2); // Get
																					// file
																					// output
																					// stream
					shot().compress(Bitmap.CompressFormat.JPEG, 100, fos); // Written to the file
					fos.flush();
					fos.close();
					Toast.makeText(AddTextForImgActivity.this, "已保存", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//在这里添加删除原来照片的功能
				File tmpPicture = new File(path);
				Log.e("path", path);
				tmpPicture.delete();
			}
		});
	}
	
	public void init(){
		saveIv = (ImageView)findViewById(R.id.save_iv);
		imgView = (ImageView)findViewById(R.id.imgview);
		relativeLayout1 = (RelativeLayout)findViewById(R.id.relativeLayout1);
//		pop_view_btn1 = (Button)findViewById(R.id.pop_view_btn1);
//		pop_view_btn2 = (Button)findViewById(R.id.pop_view_btn2);
	}
	@Override
	public void onBackPressed(){
		finish();
		File tmpPicture2 = new File(p);
		Log.e("p", p);
		tmpPicture2.delete();
	}
	
	// 实现onTouchEvent方法
    public boolean onTouchEvent(MotionEvent event) {
        // 如果是按下操作
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            getXY((int)event.getX(), (int)event.getY());
    		Toast.makeText(AddTextForImgActivity.this, "x坐标："+clickImgX+"\ny坐标："+clickImgY, Toast.LENGTH_SHORT).show();
    		
    		//获得屏幕宽高
    		WindowManager wm = this.getWindowManager();
    	    final int screenWidth = wm.getDefaultDisplay().getWidth();
//    	    int screenHeight = wm.getDefaultDisplay().getHeight();
    	    
    		
//    		relativeLayout1 = (RelativeLayout)findViewById(R.id.relativeLayout1);
    		LayoutInflater inflater = getLayoutInflater();
    		relativeLayout2 = inflater.inflate(R.layout.pop_view, null);

			final EditText et = (EditText) relativeLayout2.findViewById(R.id.pop_view_et1);
    		pop_view_btn1 = (Button)relativeLayout2.findViewById(R.id.pop_view_btn1);
    		pop_view_btn2 = (Button)relativeLayout2.findViewById(R.id.pop_view_btn2);
//    		LinearLayout pop_view_linearlayout = (LinearLayout)relativeLayout2.findViewById(R.id.pop_view_linearlayout);
    		pop_view_btn1.setPadding(7, 0, 7, 0);
    		pop_view_btn2.setPadding(7, 0, 7, 0);
    		
    		final RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
    				150,
    				LayoutParams.WRAP_CONTENT
    				);
    		p.leftMargin = clickImgX;
    		p.topMargin = clickImgY-50;
    		
    		final LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(
    				LayoutParams.WRAP_CONTENT,
    				40
    				);
    		final LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(
    				LayoutParams.WRAP_CONTENT,
    				40
    				);
    		p2.setMargins(20, 5, 5, 0);
    		p3.setMargins(0, 5, 5, 0);
    		pop_view_btn1.setLayoutParams(p2);
    		pop_view_btn2.setLayoutParams(p3);
    		
    		if(screenWidth/clickImgX <= 2){
    			et.setBackgroundResource(R.drawable.pop_bg7);
    			et.setPadding(10, 0, 30, 0);
//    			flag = 1;
    			p.leftMargin = clickImgX-150;
        		p.topMargin = clickImgY-50;
        		p2.setMargins(0, 5, 5, 0);
        		pop_view_btn1.setLayoutParams(p2);
//        		pop_view_btn1.setPadding(10, 10, 10, 10);
//        		pop_view_btn1.setWidth(80);
        		pop_view_btn2.setLayoutParams(p2);
//        		pop_view_btn2.setPadding(10, 10, 10, 10);
    	    }
    		
    		
    		relativeLayout1.addView(relativeLayout2,p);
    		
    		//确定
    		pop_view_btn1.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
//    				EditText et = (EditText) relativeLayout2.findViewById(R.id.pop_view_et1);
    				
    				String s = et.getText().toString();
    				relativeLayout1.removeView(relativeLayout2);
    				LayoutInflater inflater2 = getLayoutInflater();
    				relativeLayout2 = inflater2.inflate(R.layout.pop_view2, null);
    				TextView tv = (TextView)relativeLayout2.findViewById(R.id.pop_view2_tv1);
    				tv.setText(s);
    				
    				//如果点击右侧屏幕
    				if(screenWidth/clickImgX <= 2){
    					tv.setBackgroundResource(R.drawable.pop_bg7);
    					tv.setPadding(10, 0, 30, 0);
    	    			p.leftMargin = clickImgX-150;
    	        		p.topMargin = clickImgY-50;
    				}
    				relativeLayout1.addView(relativeLayout2,p);
    			}
    		});
    		//取消
    		pop_view_btn2.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				relativeLayout1.removeView(relativeLayout2);
    			}
    		});
    		
        }
        return super.onTouchEvent(event);
    }
 // 获取到坐标，进行判断
    private void getXY(int x, int y) {
       clickImgX = x;
       clickImgY = y;
    }
    
    /** 
     * 截屏方法 
     * @return 
     */  
    private Bitmap shot() {  
        View view = getWindow().getDecorView();  
        Display display = this.getWindowManager().getDefaultDisplay();  
        view.layout(0, 0, display.getWidth(), display.getHeight());  
        view.setDrawingCacheEnabled(true);//允许当前窗口保存缓存信息，这样getDrawingCache()方法才会返回一个Bitmap   
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());  
        return bmp;  
    }  
}
