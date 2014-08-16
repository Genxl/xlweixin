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
 * �õ�δ��6�������(������)
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
        // ��������ip��ַ 
    	String buff = "";
        // ������������̨��API 
        URL url = null;
		try {
			url = new URL("http://www.weather.com.cn/data/cityinfo/" + Cityid + ".html");
//			logger.info("123456789abc  "+url);
	        connectionData = (HttpURLConnection) url.openConnection();
	        connectionData.setRequestProperty("Accept-Charset","UTF-8");//�����������  
	        connectionData.setRequestProperty("Content-type","UTF-8");//�����������  
	        connectionData.setRequestProperty("contentType","UTF-8");//����������� 
//	        connectionData.setDoOutput(true);  
	        connectionData.setDoInput(true);  
//	        connectionData.setUseCaches(false);  
	        connectionData.setRequestMethod("GET");   
//	        connectionData.setConnectTimeout(0);
  
            // �����ص�������ת�����ַ���  
            InputStream inputStream = connectionData.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                sb.append(str);
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // �ͷ���Դ  
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
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy��MM��dd��");
    	buff = info.getString("city").toString()+"  "+sf.format(date)+"  "+getWeek(cal.get(Calendar.DAY_OF_WEEK))+"\n"
//    		+"����ʱ�䣺"+info.getString("fchh").toString()+"\n"
    		+"���������"+info.getString("weather").toString()+"\n"
    		+"��     �ȣ�"+info.getString("temp2").toString()+"~"+info.getString("temp1").toString();
    
//        //�õ�1��6����������
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        for(int i=1;i<=6;i++){
//    	//�õ�δ��6�������
//    	Calendar cal = Calendar.getInstance();
//    	cal.add(Calendar.DAY_OF_YEAR, i-1);
//    	Date date = cal.getTime();
//    	SimpleDateFormat sf = new SimpleDateFormat("yyyy��MM��dd��");
//    	
//    	Map<String,Object> map = new HashMap<String, Object>();
//    	map.put("city", info.getString("city").toString());//����
//    	map.put("date_y", sf.format(date));//����
//    	map.put("week", getWeek(cal.get(Calendar.DAY_OF_WEEK)));//����
//    	map.put("fchh", info.getString("fchh").toString());//����ʱ��
//    	map.put("weather", info.getString("weather"+i).toString());//����
//    	map.put("temp", info.getString("temp"+i).toString());//�¶�
//    	map.put("wind", info.getString("wind"+i).toString());//���
//    	map.put("fl", info.getString("fl"+i).toString());//����
//    	map.put("index", info.getString("index").toString());// ����Ĵ���ָ�� 
//    	map.put("index_uv", info.getString("index_uv").toString());// ����ָ�� 
//    	map.put("index_tr", info.getString("index_tr").toString());// ����ָ�� 
//    	map.put("index_co", info.getString("index_co").toString());// ����ָ�� 
//    	map.put("index_cl", info.getString("index_cl").toString());// ����ָ�� 
//    	map.put("index_xc", info.getString("index_xc").toString());//ϴ��ָ�� 
//    	map.put("index_d", info.getString("index_d").toString());//������ϸ����ָ�� 
//    	list.add(map);
//        }
//        //����̨��ӡ������
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
		case 1:weekStr = "������";break;
		case 2:weekStr = "����һ";break;
		case 3:weekStr = "���ڶ�";break;
		case 4:weekStr = "������";break;
		case 5:weekStr = "������";break;
		case 6:weekStr = "������";break;
		case 7:weekStr = "������";break;
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
//    	System.out.println("�����룺");
//    	String cityname = sc.nextLine();
//    	if(cityname.endsWith("����")){
//    		cityname = cityname.substring(0, cityname.indexOf("����"));
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