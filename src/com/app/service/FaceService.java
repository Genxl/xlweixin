package com.app.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.app.face.Face;

/*
 * 人脸检测服务
 */
public class FaceService {
	
	private static String httpRequest(String requestUrl){
		StringBuffer buffer = new StringBuffer();
		try {
//			System.out.println("*****************"+requestUrl);
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
            httpUrlConn.connect();  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
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
	 * 调用人脸API
	 */
	private static List<Face> faceDetect(String picUrl){
		List<Face> faces = new ArrayList<Face>();
		// 拼接Face++人脸检测的请求地址  
        String queryUrl = "http://apicn.faceplusplus.com/v2/detection/detect?api_key=API_KEY&api_secret=API_SECRET&url=URL&attribute=glass,pose,gender,age,race,smiling";
        // 对URL进行编码  
        try {
			queryUrl = queryUrl.replace("URL", java.net.URLEncoder.encode(picUrl, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        queryUrl = queryUrl.replace("API_KEY", "620f3a06fa4cab422d99b81bf113105e");  
        queryUrl = queryUrl.replace("API_SECRET", "VHk7xQIEen2AEIOy55uw_Yk5Y3ak-6iE");
//        System.out.println(queryUrl);
        // 调用人脸检测接口  
        String json = httpRequest(queryUrl);  
        // 解析返回json中的Face列表  
        JSONArray jsonArray = JSONObject.fromObject(json).getJSONArray("face");  
        // 遍历检测到的人脸  
        for (int i = 0; i < jsonArray.size(); i++) {  
            // face  
            JSONObject faceObject = (JSONObject) jsonArray.get(i);  
            // attribute  
            JSONObject attrObject = faceObject.getJSONObject("attribute");  
            // position  
            JSONObject posObject = faceObject.getJSONObject("position");  
            Face face = new Face();  
            face.setFaceId(faceObject.getString("face_id"));  
            face.setAgeValue(attrObject.getJSONObject("age").getInt("value"));  
            face.setAgeRange(attrObject.getJSONObject("age").getInt("range"));  
            face.setGenderValue(genderConvert(attrObject.getJSONObject("gender").getString("value")));  
            face.setGenderConfidence(attrObject.getJSONObject("gender").getDouble("confidence"));  
            face.setRaceValue(raceConvert(attrObject.getJSONObject("race").getString("value")));  
            face.setRaceConfidence(attrObject.getJSONObject("race").getDouble("confidence"));  
            face.setGlassValue(attrObject.getJSONObject("glass").getString("value"));
            face.setGlassConfidence(attrObject.getJSONObject("glass").getDouble("confidence"));
            face.setSmilingValue(attrObject.getJSONObject("smiling").getDouble("value"));
            face.setCenterX(posObject.getJSONObject("center").getDouble("x"));  
            face.setCenterY(posObject.getJSONObject("center").getDouble("y"));  
            faces.add(face);
        }
        Collections.sort(faces);  
		return faces;
	}
	
	/** 
     * 性别转换（英文->中文） 
     *  
     * @param gender 
     * @return 
     */  
    private static String genderConvert(String gender) {  
        String result = "男性";  
        if ("Male".equals(gender))  
            result = "男性";  
        else if ("Female".equals(gender))  
            result = "女性";  
  
        return result;  
    }  
  
    /** 
     * 人种转换（英文->中文） 
     *  
     * @param race 
     * @return 
     */  
    private static String raceConvert(String race) {  
        String result = "黄色";  
        if ("Asian".equals(race))  
            result = "黄色";  
        else if ("White".equals(race))  
            result = "白色";  
        else if ("Black".equals(race))  
            result = "黑色";  
        return result;  
    }
    
    /** 
     * 根据人脸识别结果组装消息 
     *  
     * @param faceList 人脸列表 
     * @return 
     */  
    private static String makeMessage(List<Face> faceList) {  
        StringBuffer buffer = new StringBuffer();  
        // 检测到1张脸  
        if (1 == faceList.size()) {  
            for (Face face : faceList) {
            	if(face.getAgeValue()>10){
            		if("女性".equals(face.getGenderValue().toString())){
                		buffer.append("照片上的人应该是个妹子，我猜她的年龄大概在");
                	}else{
                		buffer.append("照片上的人应该是个纯爷们，我猜他的年龄大概在");
                	}
            	}else{
            		if("女性".equals(face.getGenderValue().toString())){
                		buffer.append("照片上的人应该是个小女孩，我猜她的年龄大概在");
                	}else{
                		buffer.append("照片上的人应该是个小男孩，我猜他的年龄大概在");
                	}
            	}
                buffer.append(face.getAgeValue()).append("岁左右。").append("\n"); 
                if(!"None".equals(face.getGlassValue())){
                	buffer.append("TA还戴着一副眼镜");
                }
            }  
        }  
        // 检测到2-10张脸  
        else if (faceList.size() > 1 && faceList.size() <= 10) {  
            buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸，按脸部中心位置从左至右依次为：").append("\n");  
            for (Face face : faceList) {  
                buffer.append(face.getRaceValue()).append("人种,");  
                buffer.append(face.getGenderValue()).append(",");
                buffer.append(face.getAgeValue()).append("岁左右").append("\n");  
            }  
        }  
        // 检测到10张脸以上  
        else if (faceList.size() > 10) {  
            buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸").append("\n");  
            // 统计各人种、性别的人数  
            int asiaMale = 0;  
            int asiaFemale = 0;  
            int whiteMale = 0;  
            int whiteFemale = 0;  
            int blackMale = 0;  
            int blackFemale = 0;  
            for (Face face : faceList) {  
                if ("黄色".equals(face.getRaceValue()))  
                    if ("男性".equals(face.getGenderValue()))  
                        asiaMale++;  
                    else  
                        asiaFemale++;  
                else if ("白色".equals(face.getRaceValue()))  
                    if ("男性".equals(face.getGenderValue()))  
                        whiteMale++;  
                    else  
                        whiteFemale++;  
                else if ("黑色".equals(face.getRaceValue()))  
                    if ("男性".equals(face.getGenderValue()))  
                        blackMale++;  
                    else  
                        blackFemale++;  
            }  
            if (0 != asiaMale || 0 != asiaFemale)  
                buffer.append("黄色人种：").append(asiaMale).append("男").append(asiaFemale).append("女").append("\n");  
            if (0 != whiteMale || 0 != whiteFemale)  
                buffer.append("白色人种：").append(whiteMale).append("男").append(whiteFemale).append("女").append("\n");  
            if (0 != blackMale || 0 != blackFemale)  
                buffer.append("黑色人种：").append(blackMale).append("男").append(blackFemale).append("女").append("\n");  
        }  
        // 移除末尾空格  
        buffer = new StringBuffer(buffer.substring(0, buffer.lastIndexOf("\n")));  
        return buffer.toString();  
    }  
  
    /** 
     * 提供给外部调用的人脸检测方法 
     *  
     * @param picUrl 待检测图片的访问地址 
     * @return String 
     */  
    public static String detect(String picUrl) {  
        // 默认回复信息  
    	String result = "未识别到人脸，请换一张清晰正面照片再试！\r\n或者尝试把照片旋转";  
        List<Face> faceList = faceDetect(picUrl);  
        if (!faceList.isEmpty()) {  
            result = makeMessage(faceList);  
        }  
        return result;
    }  
  
    public static void main(String[] args) {  
        String picUrl = "http://www.faceplusplus.com.cn/wp-content/themes/faceplusplus/assets/img/demo/1.jpg?v=4";  
        System.out.println(detect(picUrl));  
    }
    
}
