package com.app.news;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.util.MySqlDB;

public class getNews {
	
	public List<news> getNews(final String type) throws Exception, ClassNotFoundException{
		Logger logger = Logger.getLogger(getClass());
		List<news> list = new ArrayList<news>();
		MySqlDB conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	conn = new MySqlDB();
		String sql = " select * from news where type='"+type+"' order by id desc limit 4";
		logger.info("select db "+sql);
		pstmt = conn.preparedStatement(sql);
		rs = pstmt.executeQuery();
		while(rs.next()){
			news news = new news();
			news.setTitle(rs.getString("title"));
			news.setDescription(rs.getString("description"));
			news.setPicurl(rs.getString("picurl"));
			news.setUrl(rs.getString("url"));
			list.add(news);
		}
		logger.info("db list"+list.size());
//		logger.info("db list new"+list.get(0).getPicurl());
		return list;
	}
}
