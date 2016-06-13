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
        
        /* ����״̬�� */
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* ���ر����� */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      
        setContentView(R.layout.activity_hello);
        
        final Intent i = new Intent();
		i.setClass(HelloActivity.this, MainActivity.class);
		
		
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
		   @Override
		   public void run(){
			   startActivity(i); //ִ��
			   HelloActivity.this.finish();//ִ����ɺ����ٴ�activity
		   }
		};
		t.schedule(tt, 1000 * 4); //2���
       
    }

}