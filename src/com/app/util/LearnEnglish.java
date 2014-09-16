package com.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.app.message.resp.Article;
import com.app.message.resp.Music;
import com.app.message.resp.MusicMessage;
import com.app.message.resp.NewsMessage;

public class LearnEnglish {
	
	private String httpRequest(String requestUrl){
		StringBuffer buffer = new StringBuffer();
		try {
//			System.out.println("*****************"+requestUrl);
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
            httpUrlConn.connect();  
            // �����ص�������ת�����ַ���  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // �ͷ���Դ  
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
	 * ���ý�ɽ�ʰ�ÿ��Ӣ��API
	 */
	public JSONObject getEnglishObject(){
		
		String Url = "http://open.iciba.com/dsapi/";
    	String json = httpRequest(Url);
    	
    	JSONObject info = JSONObject.fromObject(json);
    	
    	return info;
	}
	
	public String getEnglishEveryDay(String fromUserName,String toUserName){
		
		NewsMessage newsMessage = new NewsMessage();
    	newsMessage.setToUserName(fromUserName);  
    	newsMessage.setFromUserName(toUserName);  
    	newsMessage.setCreateTime(new Date().getTime());  
    	newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
    	newsMessage.setFuncFlag(0);
    	
    	String respMessage = null;
    	
    	JSONObject info = getEnglishObject();
    	List<Article> articleList = new ArrayList<Article>();
    	
    	Article article = new Article();
    	article.setTitle("Ӣ��\t"+info.getString("dateline"));
    	article.setDescription(info.getString("content")+"\r\n"+info.getString("note")+"\r\n\r\n"
    			               +info.getString("translation")+"\r\n\r\n"+"�ظ�����������ȡ���ĵ�����");
    	article.setPicUrl(info.getString("picture2"));
    	article.setUrl("");
    	articleList.add(article);
    	// ����ͼ����Ϣ����  
        newsMessage.setArticleCount(articleList.size());  
        // ����ͼ����Ϣ������ͼ�ļ���  
        newsMessage.setArticles(articleList);  
        // ��ͼ����Ϣ����ת����xml�ַ���  
        respMessage = MessageUtil.newsMessageToXml(newsMessage);
    	return respMessage;
    	
	}
	
	public String getListening(String fromUserName,String toUserName){
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setToUserName(fromUserName);
		musicMessage.setFromUserName(toUserName);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
		musicMessage.setFuncFlag(0);
		
		String respMessage = null;
		
		JSONObject info = getEnglishObject();
		Music music = new Music();
		music.setTitle("����\t"+info.getString("dateline"));
		music.setDescription(info.getString("content"));
		music.setMusicUrl(info.getString("tts"));
		music.setHQMusicUrl(info.getString("tts"));
		
		musicMessage.setMusic(music);
		respMessage = MessageUtil.musicMessageToXml(musicMessage);
		return respMessage;
	}
//	public static void main(String args[]){
//		LearnEnglish le = new LearnEnglish();
//		le.getEnglishEveryDay(null, null);
//	}
}
