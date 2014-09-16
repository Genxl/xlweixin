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
 * ����������
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
            // �����ص�������ת�����ַ���  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // �ͷ���Դ  
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
	 * ��������API
	 */
	private static List<Face> faceDetect(String picUrl){
		List<Face> faces = new ArrayList<Face>();
		// ƴ��Face++�������������ַ  
        String queryUrl = "http://apicn.faceplusplus.com/v2/detection/detect?api_key=API_KEY&api_secret=API_SECRET&url=URL&attribute=glass,pose,gender,age,race,smiling";
        // ��URL���б���  
        try {
			queryUrl = queryUrl.replace("URL", java.net.URLEncoder.encode(picUrl, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        queryUrl = queryUrl.replace("API_KEY", "620f3a06fa4cab422d99b81bf113105e");  
        queryUrl = queryUrl.replace("API_SECRET", "VHk7xQIEen2AEIOy55uw_Yk5Y3ak-6iE");
//        System.out.println(queryUrl);
        // �����������ӿ�  
        String json = httpRequest(queryUrl);  
        // ��������json�е�Face�б�  
        JSONArray jsonArray = JSONObject.fromObject(json).getJSONArray("face");  
        // ������⵽������  
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
     * �Ա�ת����Ӣ��->���ģ� 
     *  
     * @param gender 
     * @return 
     */  
    private static String genderConvert(String gender) {  
        String result = "����";  
        if ("Male".equals(gender))  
            result = "����";  
        else if ("Female".equals(gender))  
            result = "Ů��";  
  
        return result;  
    }  
  
    /** 
     * ����ת����Ӣ��->���ģ� 
     *  
     * @param race 
     * @return 
     */  
    private static String raceConvert(String race) {  
        String result = "��ɫ";  
        if ("Asian".equals(race))  
            result = "��ɫ";  
        else if ("White".equals(race))  
            result = "��ɫ";  
        else if ("Black".equals(race))  
            result = "��ɫ";  
        return result;  
    }
    
    /** 
     * ��������ʶ������װ��Ϣ 
     *  
     * @param faceList �����б� 
     * @return 
     */  
    private static String makeMessage(List<Face> faceList) {  
        StringBuffer buffer = new StringBuffer();  
        // ��⵽1����  
        if (1 == faceList.size()) {  
            for (Face face : faceList) {
            	if(face.getAgeValue()>10){
            		if("Ů��".equals(face.getGenderValue().toString())){
                		buffer.append("��Ƭ�ϵ���Ӧ���Ǹ����ӣ��Ҳ�������������");
                	}else{
                		buffer.append("��Ƭ�ϵ���Ӧ���Ǹ���ү�ǣ��Ҳ�������������");
                	}
            	}else{
            		if("Ů��".equals(face.getGenderValue().toString())){
                		buffer.append("��Ƭ�ϵ���Ӧ���Ǹ�СŮ�����Ҳ�������������");
                	}else{
                		buffer.append("��Ƭ�ϵ���Ӧ���Ǹ�С�к����Ҳ�������������");
                	}
            	}
                buffer.append(face.getAgeValue()).append("�����ҡ�").append("\n"); 
                if(!"None".equals(face.getGlassValue())){
                	buffer.append("TA������һ���۾�");
                }
            }  
        }  
        // ��⵽2-10����  
        else if (faceList.size() > 1 && faceList.size() <= 10) {  
            buffer.append("����⵽ ").append(faceList.size()).append(" ������������������λ�ô�����������Ϊ��").append("\n");  
            for (Face face : faceList) {  
                buffer.append(face.getRaceValue()).append("����,");  
                buffer.append(face.getGenderValue()).append(",");
                buffer.append(face.getAgeValue()).append("������").append("\n");  
            }  
        }  
        // ��⵽10��������  
        else if (faceList.size() > 10) {  
            buffer.append("����⵽ ").append(faceList.size()).append(" ������").append("\n");  
            // ͳ�Ƹ����֡��Ա������  
            int asiaMale = 0;  
            int asiaFemale = 0;  
            int whiteMale = 0;  
            int whiteFemale = 0;  
            int blackMale = 0;  
            int blackFemale = 0;  
            for (Face face : faceList) {  
                if ("��ɫ".equals(face.getRaceValue()))  
                    if ("����".equals(face.getGenderValue()))  
                        asiaMale++;  
                    else  
                        asiaFemale++;  
                else if ("��ɫ".equals(face.getRaceValue()))  
                    if ("����".equals(face.getGenderValue()))  
                        whiteMale++;  
                    else  
                        whiteFemale++;  
                else if ("��ɫ".equals(face.getRaceValue()))  
                    if ("����".equals(face.getGenderValue()))  
                        blackMale++;  
                    else  
                        blackFemale++;  
            }  
            if (0 != asiaMale || 0 != asiaFemale)  
                buffer.append("��ɫ���֣�").append(asiaMale).append("��").append(asiaFemale).append("Ů").append("\n");  
            if (0 != whiteMale || 0 != whiteFemale)  
                buffer.append("��ɫ���֣�").append(whiteMale).append("��").append(whiteFemale).append("Ů").append("\n");  
            if (0 != blackMale || 0 != blackFemale)  
                buffer.append("��ɫ���֣�").append(blackMale).append("��").append(blackFemale).append("Ů").append("\n");  
        }  
        // �Ƴ�ĩβ�ո�  
        buffer = new StringBuffer(buffer.substring(0, buffer.lastIndexOf("\n")));  
        return buffer.toString();  
    }  
  
    /** 
     * �ṩ���ⲿ���õ�������ⷽ�� 
     *  
     * @param picUrl �����ͼƬ�ķ��ʵ�ַ 
     * @return String 
     */  
    public static String detect(String picUrl) {  
        // Ĭ�ϻظ���Ϣ  
    	String result = "δʶ���������뻻һ������������Ƭ���ԣ�\r\n���߳��԰���Ƭ��ת";  
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
