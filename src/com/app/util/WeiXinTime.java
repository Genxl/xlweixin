package com.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeiXinTime {
	
	public String getTime(){
    	String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}; 
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date()); 

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; 
        if (w < 0){
        	w = 0; 
        } 
        
    	SimpleDateFormat nowtime=new SimpleDateFormat("yyyy-MM-dd");
    	return "今天是："+nowtime.format(new Date())+"\t"+weekDays[w];
    	
	}
}
