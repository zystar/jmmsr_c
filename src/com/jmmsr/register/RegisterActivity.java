package com.jmmsr.register;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jmmsr.R;
import com.jmmsr.R.layout;
import com.jmmsr.login.LoginService;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText username;
	private EditText password1;
	private EditText password2;
	private EditText sex;
	private EditText age;
	private Button register_btn;
    private ImageView iv_back2; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		
		username = (EditText)findViewById(R.id.uername);
		password1 = (EditText)findViewById(R.id.password1);
		password2 = (EditText)findViewById(R.id.password2);
		sex = (EditText)findViewById(R.id.sex);
		age = (EditText)findViewById(R.id.age);
		register_btn = (Button)findViewById(R.id.register_btn);
        iv_back2 = (ImageView)findViewById(R.id.iv_back2);
        
        iv_back2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		register_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Log.e("hehehe", password1.getText().toString());
				Log.e("hehehe", password2.getText().toString());
				
				if((password1.getText().toString()).equals(password2.getText().toString())){
					new Thread(runnable).start();
				}else{
					Toast.makeText(RegisterActivity.this, "密码有误", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
	}
	
	Runnable runnable = new Runnable(){
        @Override
        public void run() {
        	Looper.prepare();
        	register();
            Looper.loop(); 
        }
    };
     /** 
      * 用户登录的方法 
      */  
    public void register()  
    {    
        //取得用户输入的账号和密码   
        String n=username.getText().toString();  
        String p=password1.getText().toString();  
        String s=sex.getText().toString();  
        String a=age.getText().toString();  
        boolean result=RegisterService.check(n,p,s,a);  
        if(result)  
        {  
            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();  
        }else  
        {  
            Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();  
        }     
    }
}
