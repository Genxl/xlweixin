package com.app.message.req;

//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//
//import com.app.util.MySqlDB;
//import com.app.weather.Weather;

public class BaseMessage {
	//开发者微信号
	private String ToUserName;
	//发送方账号
	private String FromUserName;
	//消息创建时间（长整型）
	private long CreateTime;
	//消息类型
	private String MsgType;
	//消息ID，64位整型
	private long MsgId;
	
	public String getToUserName() {
		return ToUserName;
	}
	
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	
	public String getFromUserName() {
		return FromUserName;
	}
	
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	
	public String getMsgType() {
		return MsgType;
	}
	
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
	public long getMsgId() {
		return MsgId;
	}
	
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
	
//	public static void main (String args[]) throws SQLException, Exception{
//		Scanner sc = new Scanner(System.in);
//    	System.out.println("请输入：");
//    	String content = sc.nextLine();
//		if(content.endsWith("天气")){
//        	String citycode = null;
//        	content = content.substring(0, content.indexOf("天气"));
//        	System.out.println(content);
//        	MySqlDB conn = null;
//        	PreparedStatement pstmt = null;
//        	ResultSet rs = null;
//        	conn = new MySqlDB();
//			String sql = "select citycode from weather where cityname = '"+content+"'";
//			pstmt = conn.preparedStatement(sql);
//			rs = pstmt.executeQuery();
//			while(rs.next()){
//				citycode = rs.getString(1);
//				Weather weather = new Weather();
//				List<Map<String,Object>> list = weather.getWeatherDetail(citycode);
//				for(int j=0;j<list.size();j++){
//			    	   Map<String,Object> wMap = list.get(j);
//			    	   System.out.println(wMap.get("city")+"\t"+wMap.get("date_y")+"\t"+wMap.get("week")+"\t"
//			    			   +wMap.get("weather")+"\t"+wMap.get("temp")+"\t"+wMap.get("index_uv"));
//			       }
//			}
//        }
//	}
}
