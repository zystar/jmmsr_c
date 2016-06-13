package com.jmmsr;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class CameraActivity extends Activity {

	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private File picture;
	private Button btnSave;
	private Button tp_btn2;
	int ScreenWidth;
	int ScreenHeight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_camera);
	
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//屏幕放向

		setupViews();
	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			camera.stopPreview();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void setupViews(){
		surfaceView = (SurfaceView)findViewById(R.id.camera_preview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(surfaceCallback);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		btnSave = (Button)findViewById(R.id.tp_btn1);//拍照
		
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				takePic();
			}
		});
	}
	
	private boolean takePic(){
		
//		camera.stopPreview();
		camera.takePicture(null, null, pictureCallback);
		return true;
	}

	// Photo call back
	Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
		// @Override
		public void onPictureTaken(byte[] data, Camera camera) {
			new SavePictureTask().execute(data);
			camera.startPreview();
		}
	};

	// save pic
	class SavePictureTask extends AsyncTask<byte[], String, String> {
		@Override
		protected String doInBackground(byte[]... params) {
			String fname = DateFormat.format("yyyyMMddhhmmss", new Date())
					.toString() + ".jpg";
			
			picture = new File(Environment.getExternalStorageDirectory() + "/"
					+ fname);
			
			String path = picture.getPath();

			try {
				FileOutputStream fos = new FileOutputStream(path); // Get
																				// file
																				// output
																				// stream
				fos.write(params[0]); // Written to the file
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//存储照片后页面跳转，而不是在点击拍照按钮后再跳转
			Intent i = new Intent(CameraActivity.this, AddTextForImgActivity.class);
			i.putExtra("path", path);
			startActivity(i);
			return null;
		}
	}
	
	// SurfaceHodler Callback handle to open the camera, off camera and photo
	// size changes
	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

		public void surfaceCreated(SurfaceHolder holder) {
			camera = Camera.open(); // Turn on the camera
			try {
				camera.setPreviewDisplay(holder); // Set Preview
			} catch (IOException e) {
				camera.release();// release camera
				camera = null;
			}
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Camera.Parameters parameters = camera.getParameters(); // Camera
																	// parameters
																	// to obtain
			parameters.flatten();
			parameters.setPictureFormat(PixelFormat.JPEG);// Setting Picture
															// Format
			// parameters.set("rotation", 180); // Arbitrary rotation
			camera.setDisplayOrientation(90);//画面旋转角度
			
//			List<Size> SupportedPreviewSizes = parameters.getSupportedPreviewSizes();// 获取支持预览照片的尺寸
//			Size previewSize = SupportedPreviewSizes.get(0);// 从List取出Size
//			parameters.setPreviewSize(previewSize.width, previewSize.height); // Set Photo Size
			
//			List<Size> supportedPictureSizes = parameters.getSupportedPictureSizes();// 获取支持保存图片的尺寸
//			Size pictureSize = supportedPictureSizes.get(0);// 从List取出Size
//			parameters.setPictureSize(pictureSize.width, pictureSize.height);// 设置照片的大小
			
			camera.setParameters(parameters); // Setting camera parameters
			camera.startPreview(); // Start Preview
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			camera.stopPreview();// stop preview
			camera.release(); // Release camera resources
			camera = null;
		}
	};

}
