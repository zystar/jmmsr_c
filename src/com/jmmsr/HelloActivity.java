package com.jmmsr;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class HelloActivity extends Activity{

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /* 隐藏状态栏 */
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* 隐藏标题栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      
        setContentView(R.layout.activity_hello);
        
        final Intent i = new Intent();
		i.setClass(HelloActivity.this, MainActivity.class);
		
		
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
		   @Override
		   public void run(){
			   startActivity(i); //执行
			   HelloActivity.this.finish();//执行完成后销毁此activity
		   }
		};
		t.schedule(tt, 1000 * 4); //2秒后
       
    }

}