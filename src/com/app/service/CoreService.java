package com.app.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.app.hzDict.nameTogether;
import com.app.laughing.LaughingService;
import com.app.message.resp.TextMessage;
import com.app.util.LearnEnglish;
import com.app.util.MessageUtil;
import com.app.util.MySqlDB;
import com.app.util.MyWeiXinUtil;
import com.app.util.WeiXinTime;
import com.app.util.newsChoice;
import com.app.weather.Weather;
import com.app.xiaoqiuqiu.xqqChat;

/**
 * 核心处理类
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 */
	Logger logger = Logger.getLogger(getClass());
	public static String processRequest(HttpServletRequest request){
		String respMessage = null;
		
		//默认返回的文本内容
		StringBuffer respContent = new StringBuffer("请求处理异常，请稍后重试！");
		
		try {
			//XML请求解析
			Map<String,String> requestMap = MessageUtil.parseXml(request);
			
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			String content = requestMap.get("Content");
			String msgType = requestMap.get("MsgType");
			
			//回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            
            //文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            	MyWeiXinUtil mywxutil = new MyWeiXinUtil();
            	if(content.equals("?")||content.equals("？")){
                	WeiXinTime wxt = new WeiXinTime();
                	respContent.setLength(0);
                	respContent.append("欢迎使用XL的微信公众平台！");
                	respContent.append("\r\n"+wxt.getTime());
                	respContent.append("\r\n1、冷笑话");
                	respContent.append("\r\n2、看图文");
                	respContent.append("\r\n3、查天气");
                	respContent.append("\r\n4、每日英语");
                	respContent.append("\r\n5、人脸检测");
                	respContent.append("\r\n6、姓名匹配");
                	respContent.append("\r\n其他、请直接输入文字信息");
                }
            	else if(content.equals("1")){
                	respContent.setLength(0);
                	LaughingService laughing = new LaughingService();
                	respContent.append(laughing.getLaugh());
                }
            	else if(content.equals("2")){
                	respContent.setLength(0);
                	respContent.append("看什么我做主！请直接回复字母");
                	respContent.append("\r\n A、国内新闻\t B、国际新闻");
                	respContent.append("\r\n C、经济新闻\t D、社会新闻");
                	respContent.append("\r\n E、娱乐新闻\t F、体育新闻");
                	respContent.append("\r\n G、感悟人生\t H、轻松时刻");
                	
                	//创建图文消息
                	/*NewsMessage newsMessage = new NewsMessage();
                	newsMessage.setToUserName(fromUserName);  
                	newsMessage.setFromUserName(toUserName);  
                	newsMessage.setCreateTime(new Date().getTime());  
                	newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
                	newsMessage.setFuncFlag(0);
                	
                	//单图文
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
//                	article.setTitle("“支付宝”将成撬动阿里股价的杠杆");
//                	article.setDescription("未来，当上市的阿里巴巴已经亏损，或者濒临亏损，但通过关联交易，即可由小微金服这样的企业向其“输血”，使其当期经营业绩变为优良，从而来远程操纵其股票的涨幅。");
//                	article.setPicUrl("http://pan.baidu.com/s/1jGxGgvk");
//                	article.setUrl("http://chenlin.baijia.baidu.com/article/26182");
//                	articleList.add(article);
                	
                	// 设置图文消息个数  
                    newsMessage.setArticleCount(articleList.size());  
                    // 设置图文消息包含的图文集合  
                    newsMessage.setArticles(articleList);  
                    // 将图文消息对象转换成xml字符串  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
                    return respMessage;*/
                    
                    //多图文
//                  Article article1....n = new Article();
//                  article1.......n.setTitle("“支付宝”将成撬动阿里股价的杠杆");
//                	article1.......n.setDescription("未来，当上市的阿里巴巴已经亏损，或者濒临亏损，但通过关联交易，即可由小微金服这样的企业向其“输血”，使其当期经营业绩变为优良，从而来远程操纵其股票的涨幅。");
//                	article1.......n.setPicUrl("http://xlweixin.duapp.com/images/mayun.jpg");
//                	article1.......n.setUrl("http://chenlin.baijia.baidu.com/article/26182");
//                	articleList.add(article1.......n);
//                	respContent.append("开发中...");
                }
            	else if(content.equals("3")){
                	respContent.setLength(0);
                	respContent.append("您需要查询哪里的天气呢?");
                	respContent.append("\r\n回复：城市名称+天气");
                	respContent.append("\r\n例如：新会天气");
                }
            	else if(content.equals("4")){
                	LearnEnglish le = new LearnEnglish();
                	respMessage = le.getEnglishEveryDay(fromUserName, toUserName);
                	return respMessage;
                }
            	else if(content.equals("听力")){
            		System.out.println("听力");
            		LearnEnglish le = new LearnEnglish();
                	respMessage = le.getListening(fromUserName, toUserName);
                	return respMessage;
            	}
            	else if(content.equals("5")){
                	respContent.setLength(0);
                	respContent.append("人脸检测使用指南").append("\n\n");  
                	respContent.append("发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息").append("\n");  
                	respContent.append("赶紧来试试吧");  
                }
            	else if(content.equals("6")){
            		respContent.setLength(0);
            		respContent.append("人生因爱情而改变，他是你最爱的人吗？你们相爱会有好的结局吗？发送CS+姓名(男)配姓名(女)，如CS 郭靖配黄蓉");
            	}
            	else if(content.trim().toUpperCase().startsWith("CS") && content.contains("配")){
            		respContent.setLength(0);
            		nameTogether nt = new nameTogether();
//            		System.out.println(content.trim().toUpperCase().replaceAll("CS", "").trim());
            		respContent.append(nt.getTestResult(content.trim().toUpperCase().replaceAll("CS", "").trim()));
            		respContent.append("\r\n");
            		respContent.append("以上仅供娱乐");
            	}
            	else if(content.endsWith("天气")){
                	respContent.setLength(0);
                	String citycode = null;
                	content = content.substring(0, content.indexOf("天气"));
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
//        				respMessage = weather.getWeatherDetailFromAPI(citycode,fromUserName, toUserName);
        				respMessage = weather.getWeatherDetailFromBaiDuWeatherAPI(content, fromUserName, toUserName);
        				return respMessage;
//            			respContent.append(buff);
        			}else{
        				respContent.append("对不起！没有您所查询的城市天气！");
        			}
                }
            	else if(mywxutil.isQqFace(content)){
                	textMessage.setContent(content);
                }
                
            	else if(content.equals("A")||content.equals("a")
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
            	else{
            		xqqChat chat = new xqqChat();
            		respContent.setLength(0);
//            		System.out.println(chat.chatWithXQQ(content, fromUserName));
                	if("主人还没给我设置这类话题的回复，你帮我悄悄的告诉他吧~".equals(chat.chatWithXQQ(content, fromUserName))){
                		respContent.append(chat.chatWithXQQ(content, fromUserName));
                		respContent.append("\r\n");
                		respContent.append("或者回复”?“可以获得更多帮助哦\ue056");
                	}else{
                		respContent.append(chat.chatWithXQQ(content, fromUserName));
                	}
            	}
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
            	respContent.setLength(0);
//                respContent.append("您发送的是图片消息！");
            	// 取得图片地址  
                String picUrl = requestMap.get("PicUrl");  
                // 人脸检测  
                respContent.append(FaceService.detect(picUrl));  
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
            	respContent.setLength(0);
                respContent.append("您发送的是地理位置消息！");  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { 
            	respContent.setLength(0);
                respContent.append("您发送的是链接消息！");  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
            	respContent.setLength(0);
                respContent.append("您发送的是音频消息！");  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { 
                	respContent.setLength(0);
                    respContent.append("快乐要懂得分享，才能加倍的快乐");  
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                    // TODO 自定义菜单权没有开放，暂不处理该类消息  
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
