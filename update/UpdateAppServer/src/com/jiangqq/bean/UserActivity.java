package com.jiangqq.bean;

import java.io.Serializable;
import java.util.List;


/**
 * �û���Ϊʵ����
 * @author jiangqingqing
 * @time 2013/09/03 14:51
 */
public class UserActivity implements Serializable{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 920047676404880367L;
		private String uid;  // <����> Ψһ��ʾ�ÿͻ��˵������û�id���ͻ���Ϊ��flash cookie id���ƶ���Ϊopen udid
	    private String login;  //ע���û���¼id
	    private String platform; // ��ȡֵ��pps_ios | pps_android | pps_pc | iqiyi_ios | iqiyi_android | iqiyi_pc
	    private String mac;  //�豸mac��ַ
	    private String model;  //�豸�ͺ�
	    private GPS gps;  // GPS���꣬mobile only
	    private List<String> poi;  //��ͼPOI��Ϣ��gps���긽��300���ڵĵ�ͼ��Ϣ��mobile only
	    private List<App> installed_app; // ��װ��APP��Ϣ�Լ�ʹ�������mobile only  
	    private List<String> search_keyword; // �ڰٶȵ�������վ�������ؼ���
	    private List<String> url; //������Щ��ҳ
	    private List<String> boot_timestamp; //����ʱ�������ʽ����20130601130122����YYYYmmddhhMMss��
	    private List<String> shutdown_timestamp; //�ػ�ʱ�������ʽ����20130601130122����YYYYmmddhhMMss��
	    private List<PhoneActivity> phone_activity; //��绰��ʱ�䣬ʱ����mobile only
	    private List<String> sms_sent_timestamp ; //���ŷ���ʱ�������ʽ����20130601130122����YYYYmmddhhMMss����mobile only
	    private List<ThirdPartyVideoActivity>  third_party_video_activity ;  //���ſ�Ⱦ�Ʒ�Ͽ�����Ƶ���
	    private List<ProcessProto> process; //��������Щ���̣���������, client only
	    private List<WindowProto> window; //����ڵı�������, client only
	    private DeviceInfo device_info;	//�ֻ���Ϣ, mobile only 
		/**
		 * 
		 */
		public UserActivity() {
			super();
		}
		/**
		 * @param uid
		 * @param login
		 * @param platform
		 * @param mac
		 * @param model
		 * @param gps
		 * @param poi
		 * @param installed_app
		 * @param search_keyword
		 * @param url
		 * @param boot_timestamp
		 * @param shutdown_timestamp
		 * @param phone_activity
		 * @param sms_sent_timestamp
		 * @param third_party_video_activity
		 * @param process
		 * @param window
		 * @param device_info
		 */
		public UserActivity(String uid, String login, String platform,
				String mac, String model, GPS gps, List<String> poi,
				List<App> installed_app, List<String> search_keyword,
				List<String> url, List<String> boot_timestamp,
				List<String> shutdown_timestamp,
				List<PhoneActivity> phone_activity,
				List<String> sms_sent_timestamp,
				List<ThirdPartyVideoActivity> third_party_video_activity,
				List<ProcessProto> process, List<WindowProto> window,
				DeviceInfo device_info) {
			super();
			this.uid = uid;
			this.login = login;
			this.platform = platform;
			this.mac = mac;
			this.model = model;
			this.gps = gps;
			this.poi = poi;
			this.installed_app = installed_app;
			this.search_keyword = search_keyword;
			this.url = url;
			this.boot_timestamp = boot_timestamp;
			this.shutdown_timestamp = shutdown_timestamp;
			this.phone_activity = phone_activity;
			this.sms_sent_timestamp = sms_sent_timestamp;
			this.third_party_video_activity = third_party_video_activity;
			this.process = process;
			this.window = window;
			this.device_info = device_info;
		}
		/**
		 * @return the uid
		 */
		public String getUid() {
			return uid;
		}
		/**
		 * @param uid the uid to set
		 */
		public void setUid(String uid) {
			this.uid = uid;
		}
		/**
		 * @return the login
		 */
		public String getLogin() {
			return login;
		}
		/**
		 * @param login the login to set
		 */
		public void setLogin(String login) {
			this.login = login;
		}
		/**
		 * @return the platform
		 */
		public String getPlatform() {
			return platform;
		}
		/**
		 * @param platform the platform to set
		 */
		public void setPlatform(String platform) {
			this.platform = platform;
		}
		/**
		 * @return the mac
		 */
		public String getMac() {
			return mac;
		}
		/**
		 * @param mac the mac to set
		 */
		public void setMac(String mac) {
			this.mac = mac;
		}
		/**
		 * @return the model
		 */
		public String getModel() {
			return model;
		}
		/**
		 * @param model the model to set
		 */
		public void setModel(String model) {
			this.model = model;
		}
		/**
		 * @return the gps
		 */
		public GPS getGps() {
			return gps;
		}
		/**
		 * @param gps the gps to set
		 */
		public void setGps(GPS gps) {
			this.gps = gps;
		}
		/**
		 * @return the poi
		 */
		public List<String> getPoi() {
			return poi;
		}
		/**
		 * @param poi the poi to set
		 */
		public void setPoi(List<String> poi) {
			this.poi = poi;
		}
		/**
		 * @return the installed_app
		 */
		public List<App> getInstalled_app() {
			return installed_app;
		}
		/**
		 * @param installed_app the installed_app to set
		 */
		public void setInstalled_app(List<App> installed_app) {
			this.installed_app = installed_app;
		}
		/**
		 * @return the search_keyword
		 */
		public List<String> getSearch_keyword() {
			return search_keyword;
		}
		/**
		 * @param search_keyword the search_keyword to set
		 */
		public void setSearch_keyword(List<String> search_keyword) {
			this.search_keyword = search_keyword;
		}
		/**
		 * @return the url
		 */
		public List<String> getUrl() {
			return url;
		}
		/**
		 * @param url the url to set
		 */
		public void setUrl(List<String> url) {
			this.url = url;
		}
		/**
		 * @return the boot_timestamp
		 */
		public List<String> getBoot_timestamp() {
			return boot_timestamp;
		}
		/**
		 * @param boot_timestamp the boot_timestamp to set
		 */
		public void setBoot_timestamp(List<String> boot_timestamp) {
			this.boot_timestamp = boot_timestamp;
		}
		/**
		 * @return the shutdown_timestamp
		 */
		public List<String> getShutdown_timestamp() {
			return shutdown_timestamp;
		}
		/**
		 * @param shutdown_timestamp the shutdown_timestamp to set
		 */
		public void setShutdown_timestamp(List<String> shutdown_timestamp) {
			this.shutdown_timestamp = shutdown_timestamp;
		}
		/**
		 * @return the phone_activity
		 */
		public List<PhoneActivity> getPhone_activity() {
			return phone_activity;
		}
		/**
		 * @param phone_activity the phone_activity to set
		 */
		public void setPhone_activity(List<PhoneActivity> phone_activity) {
			this.phone_activity = phone_activity;
		}
		/**
		 * @return the sms_sent_timestamp
		 */
		public List<String> getSms_sent_timestamp() {
			return sms_sent_timestamp;
		}
		/**
		 * @param sms_sent_timestamp the sms_sent_timestamp to set
		 */
		public void setSms_sent_timestamp(List<String> sms_sent_timestamp) {
			this.sms_sent_timestamp = sms_sent_timestamp;
		}
		/**
		 * @return the third_party_video_activity
		 */
		public List<ThirdPartyVideoActivity> getThird_party_video_activity() {
			return third_party_video_activity;
		}
		/**
		 * @param third_party_video_activity the third_party_video_activity to set
		 */
		public void setThird_party_video_activity(
				List<ThirdPartyVideoActivity> third_party_video_activity) {
			this.third_party_video_activity = third_party_video_activity;
		}
		/**
		 * @return the process
		 */
		public List<ProcessProto> getProcess() {
			return process;
		}
		/**
		 * @param process the process to set
		 */
		public void setProcess(List<ProcessProto> process) {
			this.process = process;
		}
		/**
		 * @return the window
		 */
		public List<WindowProto> getWindow() {
			return window;
		}
		/**
		 * @param window the window to set
		 */
		public void setWindow(List<WindowProto> window) {
			this.window = window;
		}
		/**
		 * @return the device_info
		 */
		public DeviceInfo getDevice_info() {
			return device_info;
		}
		/**
		 * @param device_info the device_info to set
		 */
		public void setDevice_info(DeviceInfo device_info) {
			this.device_info = device_info;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "UserActivity [uid=" + uid + ", login=" + login
					+ ", platform=" + platform + ", mac=" + mac + ", model="
					+ model + ", gps=" + gps + ", poi=" + poi
					+ ", installed_app=" + installed_app + ", search_keyword="
					+ search_keyword + ", url=" + url + ", boot_timestamp="
					+ boot_timestamp + ", shutdown_timestamp="
					+ shutdown_timestamp + ", phone_activity=" + phone_activity
					+ ", sms_sent_timestamp=" + sms_sent_timestamp
					+ ", third_party_video_activity="
					+ third_party_video_activity + ", process=" + process
					+ ", window=" + window + ", device_info=" + device_info
					+ "]";
		}
		
	    
}
