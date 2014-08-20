package com.app.action;

import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.app.util.MySqlDB;
import com.opensymphony.xwork2.ActionSupport;

public class adminUpload extends ActionSupport implements ServletRequestAware{
	
	/**
	 * 
	 */
	Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1L;
//	private File fileName;//这里的"fileName"一定要与表单中的文件域名相同  
//	private String fileNameFileName;//格式同上"fileName"+FileName
	private String title;
	private String description;
	private String url;
	private String picurl;
	private String type;

//	public File getFileName() {
//		return fileName;
//	}
//
//	public void setFileName(File fileName) {
//		this.fileName = fileName;
//	}
//
//	public String getFileNameFileName() {
//		return fileNameFileName;
//	}
//
//	public void setFileNameFileName(String fileNameFileName) {
//		this.fileNameFileName = fileNameFileName;
//	}

	public String getTitle() {
		return title;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public String execute() throws Exception{
		MySqlDB conn = null;
	    PreparedStatement pstmt = null;
//		System.out.println(getFileName());
//		System.out.println(getFileNameFileName());
		System.out.println(getTitle());
		System.out.println(getDescription());
		System.out.println(getUrl());
		System.out.println(getPicurl());
		System.out.println(getType());
		String savePath = ServletActionContext.getServletContext().getRealPath("/images");
		System.out.println("图片保存路径"+savePath);
		logger.info("abcdefg "+savePath);
		conn = new MySqlDB();
        String sql = "insert into news(type,title,description,picurl,url) values (?,?,?,?,?)";
        pstmt = conn.preparedStatement(sql);
        pstmt.setString(1, getType());
        pstmt.setString(2, getTitle());
        pstmt.setString(3, getDescription());
        pstmt.setString(4, getPicurl());
        pstmt.setString(5, getUrl());
        pstmt.execute();
//		FileOutputStream fos=new FileOutputStream(savePath+"//"+getFileNameFileName());  
//        FileInputStream fis=new FileInputStream(getFileName());  
//        byte []buffers=new byte[1024];  
//        int len=0;  
//        while((len=fis.read(buffers))!=-1){  
//            fos.write(buffers,0,len);  
//        }
		return SUCCESS;
	}

}
