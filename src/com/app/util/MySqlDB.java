package com.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlDB {
	private Connection conn = null;
	  private Statement stmt = null;
	  private PreparedStatement ps = null;
	  private ResultSet rs = null;  
	  /**
	   * ���캯��������������������ݿ�ĳ�ʼ�����������������ݿ����
	   * http://xlweixin.duapp.com/coreServlet
	   * */
	  public MySqlDB() throws SQLException,ClassNotFoundException{
	    try
	    {
//	    	String url="jdbc:mysql://localhost:3306/weathercode";
//	    	Class.forName("com.mysql.jdbc.Driver");
//	    	conn = DriverManager.getConnection(url,"root","");
	    	String url="jdbc:mysql://sqld.duapp.com:4050/lrWMaGIxwLIaYldaNDbk";
		    Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection(url,"4EUWLRo9arSzSO3PY1YCpi4Q","Sj695uxdAT9oe0E8tjeu5NUy6dBdc3rR");
		    stmt = conn.createStatement();
	    }
	    catch(SQLException e)
	    {
	      System.out.println("Error occured when Connect DataBase:"+e);
	      throw e;
	    }
	    catch(ClassNotFoundException e)
	    {
	      System.out.println("Error occured when Connect DataBase:"+e);
	      throw e;
	    }
	  }
	  
	  /**
	   * ��ʼ��Ԥ����� SQL ���Ķ���
	   * */
	  public PreparedStatement preparedStatement(String sql) throws SQLException
	  {
	    try
	    {
	      ps = conn.prepareStatement(sql);
	      return ps;
	    }catch(SQLException e)
	    {
	      System.out.println("Error occured when create preparedStatement:"+e);
	      throw e;
	    }
	  }  
	  /**
	   * ִ�о�̬ SQL ��ѯ��䲢�����������ɽ���Ķ���
	   * */
	  public ResultSet executeQuery(String sql) throws SQLException
	  {
	    rs = null;
	    try {
	      rs = stmt.executeQuery(sql);
	    }
	    catch (SQLException ex) {
	      System.out.println("Error occured when query database��" + ex);
	      throw ex;
	    }
	    return rs;
	  }
	  
	  /**
	   * ִ�о�̬SQL������䲢����Ӱ����������
	   * */
	  public int executeUpdate(String sql) throws SQLException
	  {
	    try {
	      conn.setAutoCommit(false);
	      int re = stmt.executeUpdate(sql);
	      conn.commit();
	      return re;
	    }
	    catch (SQLException e) {
	      conn.rollback();
	      System.out.println("Error occured when update database��" + e);
	      throw e;
	    }
	  }  
	  /**
	   * ִ��Ԥ�����SQL��ѯ���
	   * 
	   * */
	  public ResultSet executeQuery() throws SQLException
	  {
	    try {
	      return ps.executeQuery();
	    }
	    catch (SQLException e) {
	      System.out.println("Error occured when query database��" + e);
	      throw e;
	    }
	  }
	  /**
	   * ִ��Ԥ�����SQL�������
	   * 
	   * */
	  public int executeUpdate() throws SQLException
	  {
	    try {
	      conn.setAutoCommit(false);
	      int r = ps.executeUpdate();
	      conn.commit();
	      return r;
	    }
	    catch (SQLException e) {
	      conn.rollback();
	      System.out.println("Error occured when update database��" + e);
	      throw e;
	    }
	  }
	 /**
	  * ���ݿ�رղ���
	  * */
	  public boolean closeDB() throws SQLException
	  {
	    try {
	      if (this.rs != null)
	        rs.close();
	      if (this.stmt != null)
	        this.stmt.close();
	      if (this.ps != null)
	        this.ps.close();
	      if (this.conn != null)
	        conn.close();
	      return true;
	    }
	    catch (SQLException e) {
	      System.out.println("Error occured when close database��" + e);
	      throw e;
	    }
	  }
}
