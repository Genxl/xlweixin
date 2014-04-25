package com.app.message.req;

/**
 * 音频信息
 * */
public class VoiceMessage extends BaseMessage {
	//音频ID
	private String MediaId;
	//音频格式
	private String Format;
	
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	
}
