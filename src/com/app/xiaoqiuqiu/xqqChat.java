package com.app.xiaoqiuqiu;

import java.io.IOException;
import java.util.Random;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

public class xqqChat {
	
	private final static String APP_KEY = "Fo4fELFdzdSd";
	private final static String APP_SECRET = "KUO1B9KGGDFQUTcOyO5T";
	
	public String chatWithXQQ(String key, String names) {
		String realm = "xiaoi.com";
		String method = "POST";
		String uri = "/ask.do";
		byte[] b = new byte[20];
		new Random().nextBytes(b);
		String nonce = new String(Hex.encodeHex(b));
		String HA1 = DigestUtils.shaHex(StringUtils.join(new String[] {
				APP_KEY, realm, APP_SECRET }, ":"));
		String HA2 = DigestUtils.shaHex(StringUtils.join(new String[] { method,
				uri }, ":"));
		String sign = DigestUtils.shaHex(StringUtils.join(new String[] { HA1,
	    nonce, HA2 }, ":"));
		String str = null;
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod("http://nlp.xiaoi.com/ask.do");
		pm.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
	    	"utf-8");
		pm.addRequestHeader("X-Auth", "app_key=\"Fo4fELFdzdSd\", nonce=\""
				+ nonce + "\", signature=\"" + sign + "\"");
		pm.setParameter("platform", "weixin");
		pm.setParameter("type", "0");
		pm.setParameter("userId", names);
		pm.setParameter("question", key);
		int re_code;
		try {
			re_code = hc.executeMethod(pm);
			if (re_code == 200) {
				str = pm.getResponseBodyAsString();
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		str = str.replaceAll("\r\n", "").trim();
		return str;
	}
}
