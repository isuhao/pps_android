package tv.pps.bi.proto;

import java.util.List;

import tv.pps.bi.proto.model.App;
import tv.pps.bi.proto.model.DeviceInfo;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.ProcessProto;
import tv.pps.bi.proto.model.ThirdPartyVideoActivity;
import tv.pps.bi.proto.model.WindowProto;

/**
 * ��ȡ�û���Ϊ������Ϣ�ӿ�
 * @author jiangqingqing
 * �ýӿ�ֻ�����巽��,����ģ����ȡ�ӿڷ������ϲο�
 * @time  2013/09/03  18:46
 */
public interface AchieveUserActivityManagerInterface {

	/**
	 * Ψһ��ʾ�ÿͻ��˵������û�id
	 * @return ����uid
	 */
	public String getUserUid();
	
	/**
	 * ע���û���¼id
	 * @return �û���¼ID
	 */
	public String getUserLogin(); 
	
	/**
	 * ��ȡֵ��pps_ios | pps_android | pps_pc | iqiyi_ios | iqiyi_android | iqiyi_pc
	 * @return �û�ʹ��ƽ̨
	 */
	public String getUserPlatform();
	/**
	 * �豸mac��ַ
	 * @return �豸mac��ַ
	 */
	public String getUserMac();
	/**
	 * �豸�ͺ�
	 * @return �豸�ͺ�
	 */
	public String getUserModel();
	/**
	 * GPS���꣬mobile onlyc
	 * @return GPS����
	 */
	public GPS getUserGPS();
	
	/**
	 * ��ͼPOI��Ϣ��gps���긽��300���ڵĵ�ͼ��Ϣ��mobile only
	 * @return ��ͼPOI��Ϣ��gps���긽��300���ڵĵ�ͼ��Ϣ
	 */
	public List<String> getUserPoi();
	
	/**
	 * ��װ��APP��Ϣ�Լ�ʹ�������mobile only
	 * @return ��װ��APP��Ϣ�Լ�ʹ�����
	 */
	public List<App> getUserInstalled_app();
	
	/**
	 * �ڰٶȵ�������վ�������ؼ���
	 * @return �ڰٶȵ�������վ�������ؼ���
	 */
	public List<String> getUserSearch_keyword();
	
    /**
     * ������Щ��ҳ
     * @return ������Щ��ҳ
     */
	public List<String> getUserUrl();
	/**
	 * ����ʱ�������ʽ����20130601130122����YYYYmmddhhMMss��
	 * @return ����ʱ���
	 */
	public List<String> getUserBoot_timestamp();
	
	/**
	 * �ػ�ʱ�������ʽ����20130601130122����YYYYmmddhhMMss��
	 * @return �ػ�ʱ���
	 */
	public List<String> getUserShutdown_timestamp();
	
	/**
	 * ��绰��ʱ�䣬ʱ����mobile only
	 * @return ��绰��ʱ��
	 */
	public List<PhoneActivity> getUserPhone_activity();
	/**
	 * ���ŷ���ʱ�������ʽ����20130601130122����YYYYmmddhhMMss����mobile only
	 * @return ���ŷ���ʱ���
	 */
	public List<String> getUserSms_sent_timestamp();
	/**
	 * ���ſ�Ⱦ�Ʒ�Ͽ�����Ƶ���
	 * @return ���ſ�Ⱦ�Ʒ�Ͽ�����Ƶ���
	 */
	public List<ThirdPartyVideoActivity>  getUserThird_party_video_activity();
	
	/**
	 * ��������Щ���̣���������, client only
	 * @return ��������Щ���̣���������
	 */
	public List<ProcessProto> getUserProcess();
	/**
	 * ����ڵı�������, client only
	 * @return ����ڵı�������
	 */
	public List<WindowProto> getUserWindow();
	/**
	 * �ֻ���Ϣ, mobile only
	 * @return �ֻ���Ϣ
	 */
	public DeviceInfo getUserDevice_info();
	
	
	
	public void close();
}
