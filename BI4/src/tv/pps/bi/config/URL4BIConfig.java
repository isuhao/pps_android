package tv.pps.bi.config;

/**
 * URL�ӿ�
 * @author jiangqingqing
 * 
 */
public class URL4BIConfig {
    
	/**
	 * Ͷ�ݽӿ�
	 */
	//public static final String DELIVER_URL="http://c.uaa.iqiyi.com/m.gif";
	
	/**
	 * ���Խӿ�
	 */
	public static final String DELIVER_URL="http://c.uaa.iqiyi.com/t.gif";
	
	// ��������ȡ�Ƿ����ƶ��û���Ϊ��־��URL
	public static String BI4_SIGN_URL="";  

	public static String getBI4_SIGN_URL() {
		return BI4_SIGN_URL;
	}

	public static void setBI4_SIGN_URL(String bI4_SIGN_URL) {
		BI4_SIGN_URL = bI4_SIGN_URL;
	} 
	
}
