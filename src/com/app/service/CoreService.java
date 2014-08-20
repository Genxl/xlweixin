package com.app.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.weaver.NewConstructorTypeMunger;

import sun.rmi.runtime.Log;

import com.app.message.resp.Article;
import com.app.message.resp.NewsMessage;
import com.app.message.resp.TextMessage;
import com.app.news.getNews;
import com.app.news.news;
import com.app.util.MessageUtil;
import com.app.util.MySqlDB;
import com.app.util.MyWeiXinUtil;
import com.app.util.newsChoice;
import com.app.weather.Weather;

/**
 * ���Ĵ�����
 */
public class CoreService {
	/**
	 * ����΢�ŷ���������
	 */
	Logger logger = Logger.getLogger(getClass());
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
            	MyWeiXinUtil mywxutil = new MyWeiXinUtil();
//            	respContent.setLength(0);
//              respContent.append("�����͵����ı���Ϣ��"); 
                if(!content.equals("1") || !content.equals("2") || !content.equals("3") || !content.endsWith("����") || !mywxutil.isQqFace(content)
                		|| !content.equals("A")||!content.equals("a")
                		|| !content.equals("B")||!content.equals("b")
                		|| !content.equals("C")||!content.equals("c")
                		|| !content.equals("D")||!content.equals("d")
                		|| !content.equals("E")||!content.equals("e")
                		|| !content.equals("F")||!content.equals("f")
                		|| !content.equals("G")||!content.equals("g")
                		|| !content.equals("H")||!content.equals("h")){
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
                	respContent.append("��ʲô����������ֱ�ӻظ���ĸ");
                	respContent.append("\r\n A����������\t B����������");
                	respContent.append("\r\n C����������\t D���������");
                	respContent.append("\r\n E����������\t F����������");
                	respContent.append("\r\n G����������\t H������ʱ��");
                	
                	//����ͼ����Ϣ
                	/*NewsMessage newsMessage = new NewsMessage();
                	newsMessage.setToUserName(fromUserName);  
                	newsMessage.setFromUserName(toUserName);  
                	newsMessage.setCreateTime(new Date().getTime());  
                	newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
                	newsMessage.setFuncFlag(0);
                	
                	//��ͼ��
                	List<Article> articleList = new ArrayList<Article>();
                	Article[] article = new Article[4];
                	
                	getNews news = new getNews();
                	List<news> list = news.getNews("1");
//                	article[0] = new Article();
//            		article[0].setTitle(list.get(0).getTitle());
//            		article[0].setDescription("");
//            		article[0].setPicUrl(list.get(0).getPicurl());
//            		article[0].setUrl(list.get(0).getUrl());
//            		articleList.add(article[0]);
//            		
//            		article[1] = new Article();
//            		article[1].setTitle(list.get(1).getTitle());
//            		article[1].setDescription("");
//            		article[1].setPicUrl(list.get(1).getPicurl());
//            		article[1].setUrl(list.get(1).getUrl());
//            		articleList.add(article[1]);
                	for(int i=0;i<list.size();i++){
                		article[i] = new Article();
                		article[i].setTitle(list.get(i).getTitle());
                		article[i].setDescription("");
                		article[i].setPicUrl(list.get(i).getPicurl());
                		article[i].setUrl(list.get(i).getUrl());
                		articleList.add(article[i]);
                	}
//                	article.setTitle("��֧�����������˶�����ɼ۵ĸܸ�");
//                	article.("δ���������еİ���Ͱ��Ѿ����𣬻��߱��ٿ��𣬵�ͨ���������ף�������С΢�����������ҵ���䡰��Ѫ����ʹ�䵱�ھ�Ӫҵ����Ϊ�������Ӷ���Զ�̲������Ʊ���Ƿ���");
//                	article.setPicUrl("http://pan.baidu.com/s/1jGxGgvk");
//                	article.setUrl("http://chenlin.baijia.baidu.com/article/26182");
//                	articleList.add(article);
                	
                	// ����ͼ����Ϣ����  
                    newsMessage.setArticleCount(articleList.size());  
                    // ����ͼ����Ϣ������ͼ�ļ���  
                    newsMessage.setArticles(articleList);  
                    // ��ͼ����Ϣ����ת����xml�ַ���  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                    return respMessage;*/
                    
                    //��ͼ��
//                  Article article1....n = new Article();
//                  article1.......n.setTitle("��֧�����������˶�����ɼ۵ĸܸ�");
//                	article1.......n.setDescription("δ���������еİ���Ͱ��Ѿ����𣬻��߱��ٿ��𣬵�ͨ���������ף�������С΢�����������ҵ���䡰��Ѫ����ʹ�䵱�ھ�Ӫҵ����Ϊ�������Ӷ���Զ�̲������Ʊ���Ƿ���");
//                	article1.......n.setPicUrl("http://xlweixin.duapp.com/images/mayun.jpg");
//                	article1.......n.setUrl("http://chenlin.baijia.baidu.com/article/26182");
//                	articleList.add(article1.......n);
//                	respContent.append("������...");
                }
                if(content.equals("3")){
                	respContent.setLength(0);
                	respContent.append("<a href=\"http://www.baidu.com\">����Music</a>");
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
                if(mywxutil.isQqFace(content)){
                	textMessage.setContent(content);
                }
                
                if(content.equals("A")||content.equals("a")
                		||content.equals("B")||content.equals("B")
                		||content.equals("C")||content.equals("c")
                		||content.equals("D")||content.equals("d")
                		||content.equals("E")||content.equals("e")
                		||content.equals("F")||content.equals("f")
                		||content.equals("G")||content.equals("g")
                		||content.equals("H")||content.equals("h")){
                	newsChoice newschoice = new newsChoice();
                	respMessage = newschoice.getNewsChoose(content, fromUserName, toUserName);
                	return respMessage;
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
