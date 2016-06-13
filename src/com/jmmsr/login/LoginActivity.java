package com.jmmsr.login;

import com.jmmsr.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {  
    private EditText userName;  
    private EditText passWord;  
    private Button loginBtn; 
    private TextView tv1; 
    private ImageView iv_back; 
    Context mContext = this;
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);          
        userName=(EditText)this.findViewById(R.id.login_uername);  
        passWord=(EditText)this.findViewById(R.id.login_password);         
        loginBtn=(Button)this.findViewById(R.id.login_btn);       
        tv1 = (TextView)findViewById(R.id.tv1);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        
        loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        new Thread(runnable).start();
			}
		});
        
        iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        //��ת��ע��ҳ��
        tv1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, com.jmmsr.register.RegisterActivity.class);
				startActivity(i);
			}
		});
    }  
    
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
        	Looper.prepare();
            login();
            Looper.loop(); 
        }
    };
     /** 
      * �û���¼�ķ��� 
      */  
    public void login()  
    {    
        //ȡ���û�������˺ź�����   
        String name=userName.getText().toString();  
        String pass=passWord.getText().toString();  
        String result=LoginService.check(name,pass);  
        if(result.equals("success"))  
        {  
            Toast.makeText(getApplicationContext(),"��½�ɹ�",Toast.LENGTH_SHORT).show();  
        }else if(result.equals("password_wrong"))
        {  
            Toast.makeText(getApplicationContext(),"�������",Toast.LENGTH_SHORT).show();  
        }else if(result.equals("error")){
        	Toast.makeText(getApplicationContext(),"��¼ʧ��",Toast.LENGTH_SHORT).show();
        }
    }  
}