package com.app.laughing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LaughingService {
	
	private static String httpRequest(String requestUrl){
		StringBuffer buffer = new StringBuffer();
		try {
//			System.out.println("*****************"+requestUrl);
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
            httpUrlConn.connect();  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 调用笑话API
	 */
	public String getLaugh(){
        String queryUrl = "http://apix.sinaapp.com/joke/?appkey=trialuser";
        String laugh = httpRequest(queryUrl);
        laugh = laugh.replace("技术支持 方倍工作室", "")+"";
        if(laugh.length()>6){
        	laugh = laugh.substring(1, laugh.length()-5)+"";
        }
//        System.out.println(laugh);
		return laugh;
	}
	
//	public static void main(String args[]){
//		LaughingService laugh = new LaughingService();
//		laugh.getLaugh();
//	}
}
