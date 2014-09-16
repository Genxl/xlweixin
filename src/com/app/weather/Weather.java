package com.app.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.app.message.resp.Article;
import com.app.message.resp.NewsMessage;
import com.app.util.MessageUtil;

public class Weather { 
    HttpURLConnection  connectionData = null; 
    StringBuffer sb = new StringBuffer();
    BufferedReader br;
    JSONObject jsonData;
    JSONArray resultArray;
    JSONArray indexArray;
    JSONArray weatherArray;
    JSONObject info; 
    JSONObject resultObject;
    JSONObject indexObject;
    JSONObject weatherObject;
    
    Logger logger = Logger.getLogger(getClass());
     
    public String getWeatherDetailFromAPI(String Cityid,String fromUserName,String toUserName){
    	
    	NewsMessage newsMessage = new NewsMessage();
    	newsMessage.setToUserName(fromUserName);  
    	newsMessage.setFromUserName(toUserName);  
    	newsMessage.setCreateTime(new Date().getTime());  
    	newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
    	newsMessage.setFuncFlag(0);
    	
    	String respMessage = null;
    	
        // 解析本机ip地址 
//    	String buff = "";
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
    	
    	List<Article> articleList = new ArrayList<Article>();
    	
    	Article articleCity = new Article();
    	articleCity.setTitle(info.getString("city").toString()+"天气预报");
    	articleCity.setDescription("");
    	articleCity.setPicUrl("");
    	articleCity.setUrl("");
    	articleList.add(articleCity);
    	
    	Article articleTime = new Article();
    	articleTime.setTitle(sf.format(date)+"  "+getWeek(cal.get(Calendar.DAY_OF_WEEK)));
    	articleTime.setDescription("");
    	articleTime.setPicUrl("");
    	articleTime.setUrl("");
    	articleList.add(articleTime);
    	
    	Article articleTempAndWeather = new Article();
    	articleTempAndWeather.setTitle(info.getString("temp2").toString()+"~"+info.getString("temp1").toString()+"\n"+info.getString("weather").toString());
    	articleTempAndWeather.setDescription("");
    	articleTempAndWeather.setPicUrl("http://xlweixin.ngrok.com/MyWeiXin/images/day/01.png");
    	articleTempAndWeather.setUrl("");
    	articleList.add(articleTempAndWeather);
    	
    	System.out.println(articleList.size());
    	System.out.println(articleList.get(0).getTitle());
    	// 设置图文消息个数  
        newsMessage.setArticleCount(articleList.size());  
        // 设置图文消息包含的图文集合  
        newsMessage.setArticles(articleList);  
        // 将图文消息对象转换成xml字符串  
        respMessage = MessageUtil.newsMessageToXml(newsMessage);
        return respMessage;
    	
//    	buff = info.getString("city").toString()+"  "+sf.format(date)+"  "+getWeek(cal.get(Calendar.DAY_OF_WEEK))+"\n"
//    		+"发布时间："+info.getString("fchh").toString()+"\n"
//    		+"天气情况："+info.getString("weather").toString()+"\n"
//    		+"温     度："+info.getString("temp2").toString()+"~"+info.getString("temp1").toString();
    
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
//		return buff;
       
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
    
    @SuppressWarnings("deprecation")
	public String getWeatherDetailFromBaiDuWeatherAPI(String City,String fromUserName,String toUserName){
    	
    	NewsMessage newsMessage = new NewsMessage();
    	newsMessage.setToUserName(fromUserName);  
    	newsMessage.setFromUserName(toUserName);  
    	newsMessage.setCreateTime(new Date().getTime());  
    	newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
    	newsMessage.setFuncFlag(0);
    	
    	String respMessage = null;
    	
        // 连接百度天气预报的API 
        URL url = null;
		try {
			//对城市名进行编码
			City = java.net.URLEncoder.encode(City, "UTF-8");
			url = new URL("http://api.map.baidu.com/telematics/v3/weather?location="+City+"&output=json&ak=R0Gh5BSWAD51sEGL9jPcD6mY");
	        connectionData = (HttpURLConnection) url.openConnection();
	        connectionData.setRequestProperty("Accept-Charset","UTF-8");//解决中文乱码  
	        connectionData.setRequestProperty("Content-type","UTF-8");//解决中文乱码  
	        connectionData.setRequestProperty("contentType","UTF-8");//解决中文乱码 
	        connectionData.setDoInput(true);  
	        connectionData.setRequestMethod("GET");   
//	        System.out.println(url);
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
//        System.out.println(datas);
        jsonData = JSONObject.fromObject(datas); 
        resultArray = jsonData.getJSONArray("results");
        Date date = new Date();
        String picurl = "";
        if(date.getHours() >= 6 &&date.getHours()< 18){
        	picurl = "dayPictureUrl";
        }else{
        	picurl = "nightPictureUrl";
        }
        List<Article> articleList = new ArrayList<Article>();
    	
//        System.out.println(jsonArray.size());
        for (int i = 0; i < resultArray.size(); i++) {
        	resultObject = resultArray.getJSONObject(i);
        	Article articleCity = new Article();
        	articleCity.setTitle(resultObject.getString("currentCity").toString()+"天气预报");
        	articleCity.setDescription("");
        	articleCity.setPicUrl("");
        	articleCity.setUrl("");
        	articleList.add(articleCity);
//        	indexArray = resultObject.getJSONArray("index");
        	weatherArray = resultObject.getJSONArray("weather_data");
        	
//        	for(int j = 0;j < indexArray.size(); j++){
//        		indexObject = indexArray.getJSONObject(j);
//        		System.out.println(indexObject.getString("title"));
//        	}
        	
        	weatherObject = weatherArray.getJSONObject(0);
    		Article articleInfo1 = new Article();
    		articleInfo1.setTitle(weatherObject.getString("date")+"\r\n"+weatherObject.getString("weather")+"\t"+weatherObject.getString("wind"));
    		articleInfo1.setDescription("");
    		articleInfo1.setPicUrl(weatherObject.getString(picurl));
    		articleInfo1.setUrl("");
    		articleList.add(articleInfo1);
    		
    		Article[] article = new Article[weatherArray.size()];
        	for(int k = 1;k < weatherArray.size(); k++){
        		weatherObject = weatherArray.getJSONObject(k);
        		article[k] = new Article();
        		article[k].setTitle(weatherObject.getString("date")+"\r\n"+weatherObject.getString("weather")+"\t"+weatherObject.getString("wind")+"\t"+weatherObject.getString("temperature"));
        		article[k].setDescription("");
        		article[k].setPicUrl(weatherObject.getString(picurl));
        		article[k].setUrl("");
        		articleList.add(article[k]);
//        		System.out.println(weatherObject.getString("date"));
        	}
        } 	
//    	System.out.println(articleList.size());
//    	System.out.println(articleList.get(0).getTitle());
    	// 设置图文消息个数  
        newsMessage.setArticleCount(articleList.size());  
        // 设置图文消息包含的图文集合  
        newsMessage.setArticles(articleList);  
        // 将图文消息对象转换成xml字符串  
        respMessage = MessageUtil.newsMessageToXml(newsMessage);
        return respMessage;
        
    } 
//    public static void main(String[] args) {
//    	Weather weather = new Weather();
//    	weather.getWeatherDetailFromBaiDuWeatherAPI("北京", null, null);
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