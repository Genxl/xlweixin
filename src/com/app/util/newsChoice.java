package com.app.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.message.resp.Article;
import com.app.message.resp.NewsMessage;
import com.app.news.getNews;
import com.app.news.news;

public class newsChoice {
	
	public String getNewsChoose(String choice,String fromUserName,String toUserName){
		String respMessage = null;
		String type = null;
		if(choice.equals("A")||choice.equals("a")){
			type = "1";
		}
		if(choice.equals("B")||choice.equals("b")){
			type = "2";
		}
		if(choice.equals("C")||choice.equals("c")){
			type = "3";
		}
		if(choice.equals("D")||choice.equals("d")){
			type = "4";
		}
		if(choice.equals("E")||choice.equals("e")){
			type = "5";
		}
		if(choice.equals("F")||choice.equals("f")){
			type = "6";
		}
		if(choice.equals("G")||choice.equals("g")){
			type = "7";
		}
		if(choice.equals("H")||choice.equals("h")){
			type = "8";
		}
		
		NewsMessage newsMessage = new NewsMessage();
    	newsMessage.setToUserName(fromUserName);  
    	newsMessage.setFromUserName(toUserName);  
    	newsMessage.setCreateTime(new Date().getTime());  
    	newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
    	newsMessage.setFuncFlag(0);
    	
    	List<Article> articleList = new ArrayList<Article>();
    	Article[] article = new Article[4];
    	
    	getNews news = new getNews();
    	try {
			List<news> list = news.getNews(type);
			for(int i=0;i<list.size();i++){
        		article[i] = new Article();
        		article[i].setTitle(list.get(i).getTitle());
        		article[i].setDescription("");
        		article[i].setPicUrl(list.get(i).getPicurl());
        		article[i].setUrl(list.get(i).getUrl());
        		articleList.add(article[i]);
        		// 设置图文消息个数  
                newsMessage.setArticleCount(articleList.size());  
                // 设置图文消息包含的图文集合  
                newsMessage.setArticles(articleList);  
        		respMessage = MessageUtil.newsMessageToXml(newsMessage);
        	}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respMessage;
		
	}
}
