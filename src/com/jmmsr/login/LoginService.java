package com.jmmsr.login;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.jmmsr.Url;

import android.util.Log;

public class LoginService {
	
	static String path1 = "http://192.168.0.10:8080/jmmsr_s/LoginServlet";
	
	/**
	 * 验证用户登录是否合法 返回值：请求是否成功
	 */
	public static String check(String name, String pass) {
		// 将用户名和密码放入HashMap中
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", name);
		params.put("passWord", pass);
		try {
			sendGETRequest(path1, params, "UTF-8");
			return sendGETRequest(path1, params, "UTF-8");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}
	
//	public class UriAPI { 
//        /** 定义一个Uri **/ 
//        public static final String HTTPCustomer ="http://10.7.86.7:8080/jmmsr_s/LoginServlet";
//    }

	private static String sendGETRequest(String path,
			Map<String, String> params, String encode)
			throws MalformedURLException, IOException {
		
		String result = "error";
		StringBuilder url = new StringBuilder(path1);
		url.append("?");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			url.append(entry.getKey()).append("=");
			url.append(URLEncoder.encode(entry.getValue(), encode));
			url.append("&");
		}
		// 删掉最后一个&
		url.deleteCharAt(url.length() - 1);
		HttpURLConnection conn = (HttpURLConnection) new URL(url.toString())
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		
		//请求数据
        HttpPost httpRequest  = new HttpPost(path1);
		HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
		
		if (conn.getResponseCode() == 200) { // 检查是否正常返回请求数据
			
			 //利用字节数组流和包装的绑定数据
            byte[] data =new byte[2048];
            //先把从服务端来的数据转化成字节数组
            data =EntityUtils.toByteArray((HttpEntity)httpResponse.getEntity()); 
            //再创建字节数组输入流对象  
            ByteArrayInputStream bais = new ByteArrayInputStream(data); 
             //绑定字节流和数据包装流  
            DataInputStream dis = new DataInputStream(bais); 
              //将字节数组中的数据还原成原来的各种数据类型，代码如下： 
            result=new String(dis.readUTF());
		}
		return result;
	}
}
