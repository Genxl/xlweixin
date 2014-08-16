package com.app.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.net.URLConnection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import org.apache.log4j.Logger;
//import java.util.Scanner;

//import com.app.util.MySqlDB;

import net.sf.json.JSONObject;
/**
 * 得到未来6天的天气(含今天)
 * @author Chewl
 *
 */
public class Weather { 
    HttpURLConnection  connectionData = null; 
    StringBuffer sb = new StringBuffer();
    BufferedReader br;
    JSONObject jsonData; 
    JSONObject info; 
    Logger logger = Logger.getLogger(getClass());
     
    public String getWeatherDetail(String Cityid){ 
        // 解析本机ip地址 
    	String buff = "";
        // 连接中央气象台的API 
        URL url = null;
		try {
			url = new URL("http://www.weather.com.cn/data/cityinfo/" + Cityid + ".html");
//			logger.info("123456789abc  "+url);
	        connectionData = (HttpURLConnection) url.openConnection();
	        connectionData.setRequestProperty("Accept-Charset","UTF-8");//解决中文乱码  
	        connectionData.setRequestProperty("Content-type","UTF-8");//解决中文乱码  
	        connectionData.setRequestProperty("contentType","UTF-8");//解决中文乱码 
//	        connectionData.setDoOutput(true);  
	        connectionData.setDoInput(true);  
//	        connectionData.setUseCaches(false);  
	        connectionData.setRequestMethod("GET");   
//	        connectionData.setConnectTimeout(0);
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = connectionData.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                sb.append(str);
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            connectionData.disconnect();  
		} catch (ConnectException ce) {
			// TODO Auto-generated catch block
			logger.error("Connect Time out "+ce);
			ce.printStackTrace();
        } catch (Exception e) {
			// TODO Auto-generated catch block
        	logger.error("Time out "+e);
			e.printStackTrace();
		}
        
        String datas = sb.toString();   
//        logger.info("1234567890  "+datas);   
        jsonData = JSONObject.fromObject(datas); 
        info = jsonData.getJSONObject("weatherinfo");
        
        Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_YEAR, 0);
    	Date date = cal.getTime();
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
    	buff = info.getString("city").toString()+"  "+sf.format(date)+"  "+getWeek(cal.get(Calendar.DAY_OF_WEEK))+"\n"
//    		+"发布时间："+info.getString("fchh").toString()+"\n"
    		+"天气情况："+info.getString("weather").toString()+"\n"
    		+"温     度："+info.getString("temp2").toString()+"~"+info.getString("temp1").toString();
    
//        //得到1到6天的天气情况
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        for(int i=1;i<=6;i++){
//    	//得到未来6天的日期
//    	Calendar cal = Calendar.getInstance();
//    	cal.add(Calendar.DAY_OF_YEAR, i-1);
//    	Date date = cal.getTime();
//    	SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
//    	
//    	Map<String,Object> map = new HashMap<String, Object>();
//    	map.put("city", info.getString("city").toString());//城市
//    	map.put("date_y", sf.format(date));//日期
//    	map.put("week", getWeek(cal.get(Calendar.DAY_OF_WEEK)));//星期
//    	map.put("fchh", info.getString("fchh").toString());//发布时间
//    	map.put("weather", info.getString("weather"+i).toString());//天气
//    	map.put("temp", info.getString("temp"+i).toString());//温度
//    	map.put("wind", info.getString("wind"+i).toString());//风况
//    	map.put("fl", info.getString("fl"+i).toString());//风速
//    	map.put("index", info.getString("index").toString());// 今天的穿衣指数 
//    	map.put("index_uv", info.getString("index_uv").toString());// 紫外指数 
//    	map.put("index_tr", info.getString("index_tr").toString());// 旅游指数 
//    	map.put("index_co", info.getString("index_co").toString());// 舒适指数 
//    	map.put("index_cl", info.getString("index_cl").toString());// 晨练指数 
//    	map.put("index_xc", info.getString("index_xc").toString());//洗车指数 
//    	map.put("index_d", info.getString("index_d").toString());//天气详细穿衣指数 
//    	list.add(map);
//        }
//        //控制台打印出天气
//       for(int j=0;j<list.size();j++){
//    	   Map<String,Object> wMap = list.get(j);
//    	   logger.info("123  "+wMap.get("city")+" "+wMap.get("temp"));
////    	   System.out.println(wMap.get("city")+"\t"+wMap.get("date_y")+"\t"+wMap.get("week")+"\t"
////    			   +wMap.get("weather")+"\t"+wMap.get("temp")+"\t"+wMap.get("index_uv"));
//       }
		return buff;
       
    } 
    private String getWeek(int iw){
    	String weekStr = "";
    	switch (iw) {
		case 1:weekStr = "星期天";break;
		case 2:weekStr = "星期一";break;
		case 3:weekStr = "星期二";break;
		case 4:weekStr = "星期三";break;
		case 5:weekStr = "星期四";break;
		case 6:weekStr = "星期五";break;
		case 7:weekStr = "星期六";break;
		default:
			break;
		}
    	return weekStr;
    }
//    public static void main(String[] args) {
//    	String citycode = null;
//    	MySqlDB conn = null;
//    	PreparedStatement pstmt = null;
//    	ResultSet rs = null;
//    	Scanner sc = new Scanner(System.in);
//    	System.out.println("请输入：");
//    	String cityname = sc.nextLine();
//    	if(cityname.endsWith("天气")){
//    		cityname = cityname.substring(0, cityname.indexOf("天气"));
//        	System.out.println(cityname);
//    	}
//    	try {
//			conn = new MySqlDB();
//			String sql = "select citycode from weather where cityname = '"+cityname+"'";
//			pstmt = conn.preparedStatement(sql);
//			rs = pstmt.executeQuery();
//			while(rs.next()){
//				System.out.println(rs.getString(1));
//				citycode = rs.getString(1);
//			}
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//        try { 
//            new Weather(citycode);
//        } catch (Exception e) { 
//            e.printStackTrace(); 
//        } 
//    } 
} 