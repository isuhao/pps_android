package tv.pps.bi.db.config;

public class IntervalTimeConstance {

	/**�������㲥ʱ��ÿ��һ��Сʱ���п���һ�η���*/
	public static final int  START_LISTEN_SERVICE_TIME 	= 60*1000;//1*60*60*1000;//һСʱ
	
	/**ÿ������Сʱ����Ͷ������service*/
	public static  int START_DELIVER_SERVICE_TIME 	= 2*60*1000;//2*60*60*1000;//��Сʱ
	
	/**ÿ����ʮ���ӽ���һ���û���Ϊ��ѯ*/
	public static  int START_USERINFO_SEARCH_TIME 	= 1*60*1000;//30*60*1000;//��ʮ����
	
	/**ʮ���н���һ���û���Ϊ�Ĳ�ѯ*/
	public static final int START_APPUSEINFO_SEARCH_TIME= 10*1000;//ʮ����
	
	/**
	 * @return the sTART_DELIVER_SERVICE_TIME
	 */
	public static int getSTART_DELIVER_SERVICE_TIME() {
		return START_DELIVER_SERVICE_TIME;
	}

	/**
	 * @return the sTART_USERINFO_SEARCH_TIME
	 */
	public static int getSTART_USERINFO_SEARCH_TIME() {
		return START_USERINFO_SEARCH_TIME;
	}

	/**
	 * @param sTART_DELIVER_SERVICE_TIME the sTART_DELIVER_SERVICE_TIME to set
	 */
	public static void setStartDeliverServiceTime(int sTART_DELIVER_SERVICE_TIME) {
		START_DELIVER_SERVICE_TIME = sTART_DELIVER_SERVICE_TIME;
	}

	/**
	 * @param sTART_USERINFO_SEARCH_TIME the sTART_USERINFO_SEARCH_TIME to set
	 */
	public static void setStartUserInfoSearchTime(int sTART_USERINFO_SEARCH_TIME) {
		START_USERINFO_SEARCH_TIME = sTART_USERINFO_SEARCH_TIME;
	}
}
