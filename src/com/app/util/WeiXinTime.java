package com.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeiXinTime {
	
	public String getTime(){
    	String[] weekDays = {"������", "����һ", "���ڶ�", "������", "������", "������", "������"}; 
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date()); 

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; 
        if (w < 0){
        	w = 0; 
        } 
        
    	SimpleDateFormat nowtime=new SimpleDateFormat("yyyy-MM-dd");
    	return "�����ǣ�"+nowtime.format(new Date())+"\t"+weekDays[w];
    	
	}
}
