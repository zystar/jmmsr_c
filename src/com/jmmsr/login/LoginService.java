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
	 * ��֤�û���¼�Ƿ�Ϸ� ����ֵ�������Ƿ�ɹ�
	 */
	public static String check(String name, String pass) {
		// ���û������������HashMap��
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
//        /** ����һ��Uri **/ 
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
		// ɾ�����һ��&
		url.deleteCharAt(url.length() - 1);
		HttpURLConnection conn = (HttpURLConnection) new URL(url.toString())
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		
		//��������
        HttpPost httpRequest  = new HttpPost(path1);
		HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
		
		if (conn.getResponseCode() == 200) { // ����Ƿ�����������������
			
			 //�����ֽ��������Ͱ�װ�İ�����
            byte[] data =new byte[2048];
            //�ȰѴӷ������������ת�����ֽ�����
            data =EntityUtils.toByteArray((HttpEntity)httpResponse.getEntity()); 
            //�ٴ����ֽ���������������  
            ByteArrayInputStream bais = new ByteArrayInputStream(data); 
             //���ֽ��������ݰ�װ��  
            DataInputStream dis = new DataInputStream(bais); 
              //���ֽ������е����ݻ�ԭ��ԭ���ĸ����������ͣ��������£� 
            result=new String(dis.readUTF());
		}
		return result;
	}
}
