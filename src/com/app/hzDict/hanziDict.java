package com.app.hzDict;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * �����ֵ䣬���Բ�ѯ���ֵ�ƴ�������׺ͱʻ�
 * 
 */
public class hanziDict {
	/** ������Сunicodeֵ */
	public static final char HAN_MIN = 'һ';
	/** �������unicodeֵ */
	public static final char HAN_MAX = '��';
	/** �������ݣ���"һ"��"��" */
	public static final String[] HAN_DATA = new String[HAN_MAX - HAN_MIN + 1];
	/** ���������ļ� */
	private static final String HAN_DATA_FILE = "data.txt";
	/** ���������ļ����� */
	private static final Charset FILE_CHARSET = Charset.forName("utf-8");

	/** ƴ�������±� */
	private static final int INDEX_PY = 0;
	/** ���������±� */
	private static final int INDEX_BS = 1;
	/** �ʻ������±� */
	private static final int INDEX_BH = 2;
	/** ƴ�����ݣ�������ĸע�����±� */
	private static final int INDEX_PY_HAN = 0;
	/** ƴ�����ݣ�Ӣ����ĸע�����±� */
	private static final int INDEX_PY_EN = 1;

	static {
		try {
			loadHanData();
		} catch (IOException e) {
			System.err.println("���뺺�����ݴ���" + e.getMessage());
		}
	}

	/**
	 * ��ȡ���ֱʻ����� "��"�ıʻ�Ϊ"134"<br>
	 * 12345 ��Ӧ "����Ʋ����"
	 * 
	 * @param str
	 *            ��������
	 * @return String
	 */
	public static String getBH(String str) {
		if (str == null || str.equals("")) {
			return "";
		}

		return getBH(str.charAt(0));
	}

	/**
	 * ��ȡ���ֱʻ����� "��"�ıʻ�Ϊ"134"<br>
	 * 12345 ��Ӧ "����Ʋ����"
	 * 
	 * @param ch
	 *            ����
	 * @return String
	 */
	public static String getBH(char ch) {
		if (isHan(ch)) {
			return HAN_DATA[ch - HAN_MIN].split("\\|")[INDEX_BH];
		}
		return "";
	}

	/**
	 * ��ȡ���ֲ��ף����û�в��ף�����""
	 * 
	 * @param str
	 *            ��������
	 * @return String
	 */
	public static String getBS(String str) {
		if (str == null || str.equals("")) {
			return "";
		}

		return getBS(str.charAt(0));
	}

	/**
	 * ��ȡ���ֲ��ף����û�в��ף�����""
	 * 
	 * @param ch
	 *            ����
	 * @return String
	 */
	public static String getBS(char ch) {
		if (isHan(ch)) {
			return HAN_DATA[ch - HAN_MIN].split("\\|")[INDEX_BS];
		}
		return "";
	}

	/**
	 * ���ص������ֵĶ����б����������Ƕ��
	 * 
	 * @param ch
	 *            ����
	 * @param useHanFormat
	 *            true=������ĸע������y����false=Ӣ����ĸע������yi1
	 * @return List
	 */
	public static List<String> getPY(char ch, boolean useHanFormat) {
		List<String> list = new ArrayList<String>();
		if (isHan(ch)) {
			int i = useHanFormat ? INDEX_PY_HAN : INDEX_PY_EN;
			String pyStr = HAN_DATA[ch - HAN_MIN].split("\\|")[INDEX_PY];
			for (String py : pyStr.split(";")) {
				list.add(py.split(",")[i]);
			}
		}
		return list;
	}

	/**
	 * ���غ����ַ���ע��������ַ������ַ����Ǻ��֣���ôʹ��ԭ�ַ���<br>
	 * ע�⣺�����ж��ע���ĺ��֣�����ȡ��һ��ע���� <br>
	 * �磺"���������Ϊ123��" ���صĽ��Ϊ��"j��n ni��n de sh��u r�� w��i 123 w��n ��"
	 * 
	 * @param str
	 *            �����ַ���
	 * @param useHanFormat
	 *            true=������ĸע������y����false=Ӣ����ĸע������yi1
	 * @return
	 */
	public static String getPY(String str, boolean useHanFormat) {
		if (str == null) {
			return "";
		}
		boolean lastBlank = true;
		StringBuffer sb = new StringBuffer();
		for (char ch : str.toCharArray()) {
			if (isHan(ch)) {
				List<String> pyList = getPY(ch, useHanFormat);
				if (!pyList.isEmpty()) {
					if (!lastBlank) {
						sb.append(" ");
					}
					sb.append(pyList.get(0)).append(' ');
					lastBlank = true;
				}
			} else {
				sb.append(ch);
				lastBlank = false;
			}
		}
		return sb.toString();
	}

	/**
	 * ����Ƿ���
	 * 
	 * @param ch
	 * @return
	 */
	private static boolean isHan(char ch) {
		if (ch >= HAN_MIN && ch <= HAN_MAX) {
			return true;
		}
		return false;
	}

	private static void loadHanData() throws IOException {
		InputStream in = hanziDict.class.getResourceAsStream(HAN_DATA_FILE);

		if (in == null) {
			throw new IOException(HAN_DATA_FILE + "���������ļ������ڣ�");
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in, FILE_CHARSET));
			String line = null;
			int index = 0;
			while ((line = br.readLine()) != null) {
				HAN_DATA[index++] = line;
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * ʹ�ò���
	 */
	public static void main(String[] args) {
		char ch = '֣';
		System.out.println(ch + "��ƴ������ʽע����Ϊ��" + hanziDict.getPY(ch, true));
		System.out.println(ch + "��ƴ����Ӣʽע����Ϊ��" + hanziDict.getPY(ch, false));
		System.out.println(ch + "�Ĳ���Ϊ��������������" + hanziDict.getBS(ch));
		System.out.println(ch + "�Ĳ��ױʻ�Ϊ����������" + hanziDict.getBH(hanziDict.getBS(ch)));
		System.out.println(ch + "�ıʻ�˳��Ϊ����������" + hanziDict.getBH(ch));
		System.out.println(ch + "�ıʻ���Ϊ������������" + hanziDict.getBH(ch).length());
		System.out.println();
		ch = '��';
		System.out.println(ch + "��ƴ������ʽע����Ϊ��" + hanziDict.getPY(ch, true));
		System.out.println(ch + "��ƴ����Ӣʽע����Ϊ��" + hanziDict.getPY(ch, false));
		System.out.println(ch + "�Ĳ���Ϊ��������������" + hanziDict.getBS(ch));
		System.out.println(ch + "�Ĳ��ױʻ�Ϊ����������" + hanziDict.getBH(hanziDict.getBS(ch)));
		System.out.println(ch + "�ıʻ�˳��Ϊ����������" + hanziDict.getBH(ch));
		System.out.println(ch + "�ıʻ���Ϊ������������" + hanziDict.getBH(ch).length());
		System.out.println();
		ch = 'a';
		System.out.println(ch + "��ƴ������ʽע����Ϊ��" + hanziDict.getPY(ch, true));
		System.out.println(ch + "��ƴ����Ӣʽע����Ϊ��" + hanziDict.getPY(ch, false));
		System.out.println(ch + "�Ĳ���Ϊ��������������" + hanziDict.getBS(ch));
		System.out.println(ch + "�Ĳ��ױʻ�Ϊ����������" + hanziDict.getBH(hanziDict.getBS(ch)));
		System.out.println(ch + "�ıʻ�˳��Ϊ����������" + hanziDict.getBH(ch));
		System.out.println(ch + "�ıʻ���Ϊ������������" + hanziDict.getBH(ch).length());
		System.out.println();

		String str = "���������Ϊ123��";
		System.out.println(str + " ��ƴ������ʽ��Ϊ��" + getPY(str, true));
		System.out.println(str + " ��ƴ����Ӣʽ��Ϊ��" + getPY(str, false));
	}
}
