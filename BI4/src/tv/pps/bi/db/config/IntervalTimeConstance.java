package tv.pps.bi.db.config;

/**
 * ����ʱ�䳣����
 * 
 * @author jiangqingqing
 * @time 2013/09/17 14:03
 */
public class IntervalTimeConstance {

	/** �������㲥ʱ��ÿ��һ��Сʱ���п���һ�η��� */
	public static int START_LISTEN_SERVICE_TIME = 1 * 60 * 60 * 1000;// һСʱ
	/** ÿ������Сʱ����Ͷ������service */
	public static int START_DELIVER_SERVICE_TIME = 2 * 60 * 60 * 1000;// ��Сʱ
	/** ÿ����ʮ���ӽ���һ���û���Ϊ��ѯ */
	public static int START_USERINFO_SEARCH_TIME = 30 * 60 * 1000;// ��ʮ����
	/** ʮ���н���һ��ʹ��APP��Ϊ�Ĳ�ѯ */
	public static int START_APPUSEINFO_SEARCH_TIME = 10 * 1000;// ʮ����

	/**
	 * @return the sTART_LISTEN_SERVICE_TIME
	 */
	public static int getSTART_LISTEN_SERVICE_TIME() {
		return START_LISTEN_SERVICE_TIME;
	}

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
	 * @return the sTART_APPUSEINFO_SEARCH_TIME
	 */
	public static int getSTART_APPUSEINFO_SEARCH_TIME() {
		return START_APPUSEINFO_SEARCH_TIME;
	}

	
	/**
	 * ���ù㲥���������ʱ����
	 * @param sTART_LISTEN_SERVICE_TIME  ʱ���� (����)
	 */
	public static void setStartListenServiceTime(
			int sTART_LISTEN_SERVICE_TIME) {
		START_LISTEN_SERVICE_TIME = sTART_LISTEN_SERVICE_TIME;
	}

	/**
	 * ����Ͷ�����ݵ�ʱ����
	 * @param sTART_DELIVER_SERVICE_TIME ʱ���� (����)
	 */
	public static void setStartDeliverServiceTime(
			int sTART_DELIVER_SERVICE_TIME) {
		START_DELIVER_SERVICE_TIME = sTART_DELIVER_SERVICE_TIME;
	}

	/**
	 * ���ò�ѯ�û���Ϊ��Ϣ��ʱ����
	 * @param sTART_USERINFO_SEARCH_TIME ʱ����(����)
	 */
	public static void setStartUserInfoSearchTime(
			int sTART_USERINFO_SEARCH_TIME) {
		START_USERINFO_SEARCH_TIME = sTART_USERINFO_SEARCH_TIME;
	}

	/**
	 * ���ò�ѯ�û�ʹ��APP��Ϣ��ʱ����
	 * @param sTART_APPUSEINFO_SEARCH_TIME ʱ����(����)
	 */
	public static void setStartAppUseInfoSearchTime(
			int sTART_APPUSEINFO_SEARCH_TIME) {
		START_APPUSEINFO_SEARCH_TIME = sTART_APPUSEINFO_SEARCH_TIME;
	}

}
