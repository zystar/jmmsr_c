package com.jmmsr.register;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.jmmsr.Url;

public class RegisterService {
	/** 
     * ��֤�û�ע���Ƿ�Ϸ� 
     * ����ֵ�������Ƿ�ɹ� 
     */  
    public static boolean check(String name, String password, String sex, String age) {  
        String path= "http://192.168.0.10:8080/jmmsr_s/LoginServlet";
        //���û������������HashMap��   
        Map<String,String> params=new HashMap<String,String>();  
        params.put("name", name);  
        params.put("password", password);         
        params.put("sex", sex);         
        params.put("age", age);         
        try {  
            return sendGETRequest(path,params,"UTF-8");  
        } catch (MalformedURLException e) {  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        }  
        return false;  
    }  
    private static boolean sendGETRequest(String path,  
        Map<String, String> params,String encode) throws MalformedURLException, IOException {  
        StringBuilder url=new StringBuilder(path);  
        url.append("?");  
        for(Map.Entry<String, String> entry:params.entrySet())  
        {  
            url.append(entry.getKey()).append("=");  
            url.append(URLEncoder.encode(entry.getValue(),encode));  
            url.append("&");  
        }  
        //ɾ�����һ��&   
        url.deleteCharAt(url.length()-1);  
        HttpURLConnection conn=(HttpURLConnection)new URL(url.toString()).openConnection();  
        conn.setConnectTimeout(5000);  
        conn.setRequestMethod("GET");  
        if(conn.getResponseCode()==200)  //����Ƿ�����������������
         {  
           return true;  
         }  
           return false;  
         }
}
