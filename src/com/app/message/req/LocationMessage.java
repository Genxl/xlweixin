package com.app.message.req;

/**
 * ������Ϣ
 * */
public class LocationMessage extends BaseMessage {
	//�����γ��
	private String Location_X;
	//����ľ���
	private String Location_Y;
	//��ͼ���Ŵ�С
	private String Scale;
	//����λ����Ϣ
	private String Lable;
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLable() {
		return Lable;
	}
	public void setLable(String lable) {
		Lable = lable;
	}
	
}
