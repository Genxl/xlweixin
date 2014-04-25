package com.app.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import sun.rmi.runtime.Log;

import com.app.message.resp.TextMessage;
import com.app.util.MessageUtil;
import com.app.util.MySqlDB;
import com.app.weather.Weather;

/**
 * ���Ĵ�����
 */
public class CoreService {
	/**
	 * ����΢�ŷ���������
	 */
	public static String processRequest(HttpServletRequest request){
		String respMessage = null;
		
		//Ĭ�Ϸ��ص��ı�����
		StringBuffer respContent = new StringBuffer("�������쳣�����Ժ����ԣ�");
		
		try {
			//XML�������
			Map<String,String> requestMap = MessageUtil.parseXml(request);
			
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			String content = requestMap.get("Content");
			String msgType = requestMap.get("MsgType");
			
			//�ظ��ı���Ϣ  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            
            //�ı���Ϣ  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { 
//            	respContent.setLength(0);
//              respContent.append("�����͵����ı���Ϣ��"); 
                if(!content.equals("1") || !content.equals("2") || !content.equals("3") || !content.endsWith("����")){
                	respContent.setLength(0);
                	respContent.append("��ӭʹ��΢��ƽ̨��");
                	respContent.append("\r\n1����ǰʱ��");
                	respContent.append("\r\n2����ͼ��");
                	respContent.append("\r\n3��������");
                	respContent.append("\r\n4��������");
                	respContent.append("\r\n��������ֱ������������Ϣ");
                }
                if(content.equals("1")){
                	respContent.setLength(0);
                	String[] weekDays = {"������", "����һ", "���ڶ�", "������", "������", "������", "������"}; 
                    Calendar cal = Calendar.getInstance(); 
                    cal.setTime(new Date()); 

                    int w = cal.get(Calendar.DAY_OF_WEEK) - 1; 
                    if (w < 0){
                    	w = 0; 
                    } 
                	SimpleDateFormat nowtime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                	respContent.append("��ǰʱ�䣺"+nowtime.format(new Date()));
                	respContent.append("\r\n");
                	respContent.append("�����ǣ�"+weekDays[w]);
                }
                if(content.equals("2")){
                	respContent.setLength(0);
                	respContent.append("����Music");
                }
                if(content.equals("3")){
                	respContent.setLength(0);
                	respContent.append("������...");
                }
                if(content.equals("4")){
                	respContent.setLength(0);
                	respContent.append("����Ҫ��ѯ�����������?");
                	respContent.append("\r\n�ظ�����������+����");
                	respContent.append("\r\n���磺�»�����");
                }
                if(content.endsWith("����")){
                	respContent.setLength(0);
                	String citycode = null;
                	content = content.substring(0, content.indexOf("����"));
                	MySqlDB conn = null;
                	PreparedStatement pstmt = null;
                	ResultSet rs = null;
                	conn = new MySqlDB();
        			String sql = "select citycode from weather where cityname = '"+content+"'";
        			pstmt = conn.preparedStatement(sql);
        			rs = pstmt.executeQuery();
        			if(rs.next()){
        				citycode = rs.getString(1);
        				Weather weather = new Weather();
            			String buff = weather.getWeatherDetail(citycode);
            			respContent.append(buff);
        			}else{
        				respContent.append("�Բ���û��������ѯ�ĳ���������");
        			}
                }
            }  
            // ͼƬ��Ϣ  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
            	respContent.setLength(0);
                respContent.append("�����͵���ͼƬ��Ϣ��");  
            }  
            // ����λ����Ϣ  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
            	respContent.setLength(0);
                respContent.append("�����͵��ǵ���λ����Ϣ��");  
            }  
            // ������Ϣ  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { 
            	respContent.setLength(0);
                respContent.append("�����͵���������Ϣ��");  
            }  
            // ��Ƶ��Ϣ  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
            	respContent.setLength(0);
                respContent.append("�����͵�����Ƶ��Ϣ��");  
            }  
            // �¼�����  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // �¼�����  
                String eventType = requestMap.get("Event");  
                // ����  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { 
                	respContent.setLength(0);
                    respContent.append("лл���Ĺ�ע��");  
                }  
                // ȡ������  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ  
                }  
                // �Զ���˵�����¼�  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                    // TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ  
                }  
            }  
  
            textMessage.setContent(respContent.toString());  
            respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respMessage;
	}
}
