package tv.pps.bi.proto;

import java.util.ArrayList;
import java.util.List;


import tv.pps.bi.proto.model.App;
import tv.pps.bi.proto.model.AppActivity;
import tv.pps.bi.proto.model.DeviceInfo;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.ProcessActivity;
import tv.pps.bi.proto.model.ProcessProto;
import tv.pps.bi.proto.model.ThirdPartyVideoActivity;
import tv.pps.bi.proto.model.UserActivity;
import tv.pps.bi.proto.model.WindowActivity;
import tv.pps.bi.proto.model.WindowProto;
import tv.pps.bi.utils.LogUtils;
import user_activity.UserActivityData;

/**
 * ���й���ProtoBuff��Ϣ
 * @author jiangqingqing
 * @time 2013/09/03 14��11
 */
public class ProtoBuffUserActivityService {


	/**
	 * @deprecated ������Ϣ����,ȫ�����뵽getConstructorData(UserActivity pUserActivity)�н��л�ȡ
	 */
	private List<ProcessProto> mProcesses=new ArrayList<ProcessProto>();
	/**
	 * @deprecated ������Ϣ����,ȫ�����뵽getConstructorData(UserActivity pUserActivity)�н��л�ȡ
	 */
	private List<ProcessActivity> mProcessActivities = new ArrayList<ProcessActivity>();
	/**
	 * @deprecated �ֻ��豸��Ϣ������ȫ�����뵽getConstructorData(UserActivity pUserActivity)�н��л�ȡ
	 */
	private DeviceInfo mDeviceInfo = new DeviceInfo("111", "222", "333", "444",
			"555", "666", "root");
	/**
	 * @deprecated ��������Ƶ�ͻ��˲�����ʷ��¼����,ȫ�����뵽getConstructorData(UserActivity pUserActivity)�н��л�ȡ
	 */
	private List<ThirdPartyVideoActivity> mThirdPartyVideoActivities=new ArrayList<ThirdPartyVideoActivity>();
	/**
	 * @deprecated �ֻ�������APP��Ϣ����,ȫ�����뵽getConstructorData(UserActivity pUserActivity)�н��л�ȡ 
	 */
	private List<App> mApps=new ArrayList<App>();
	/**
	 * @deprecated APP���е������Ϣ����,ȫ�����뵽getConstructorData(UserActivity pUserActivity)�н��л�ȡ
	 */
	private List<AppActivity> mAppActivities=new ArrayList<AppActivity>();
	
	
	private UserActivityData.UserActivity.Builder builder_UserActivity;

	/**
	 * ���캯��,builder_UserActivity��ʼ��,��ʼ����protobuff��ʽ����
	 */
	public ProtoBuffUserActivityService() {
		builder_UserActivity=UserActivityData.UserActivity.newBuilder();
	}

	/**
	 * ��ȡ����ɹ��û���Ϊprotobuff����
	 * @param pUserActivity  ��Ҫ���й����ʵ�������
	 * @return byte[] ����ɹ�֮���protobuff����
	 */
	public static int count = 0;
	public byte[] getConstructorData(UserActivity pUserActivity)
	{
		LogUtils.i("jiangqingqing", "��"+(++count)+"�η������� == "+pUserActivity.toString());
		builder_UserActivity.setUid(pUserActivity.getUid()); //Ψһ��ʾ�ÿͻ��˵������û�id��
		builder_UserActivity.setLogin(pUserActivity.getLogin()); //ע���û���¼id
		builder_UserActivity.setPlatform(pUserActivity.getPlatform());//��ȡֵ��pps_ios | pps_android | pps_pc | iqiyi_ios | iqiyi_android | iqiyi_pc
		builder_UserActivity.setMac(pUserActivity.getMac());//�豸mac��ַ
        builder_UserActivity.setModel(pUserActivity.getModel());//�豸�ͺ�
        // GPS���꣬mobile only
		GPS mGps=pUserActivity.getGps();
		if(null!=mGps){
		UserActivityData.GPS.Builder builder_Gps=UserActivityData.GPS.newBuilder();
		builder_Gps.setAltitude(mGps.getAltitude());
		builder_Gps.setLatitude(mGps.getLatitude());
		builder_Gps.setLongitude(mGps.getLongitude());
		builder_UserActivity.setGps(builder_Gps);
		}
				
		//��ͼPOI��Ϣ��gps���긽��300���ڵĵ�ͼ��Ϣ��mobile only
		List<String> mPois=pUserActivity.getPoi();
		int mPois_Size=mPois!=null?mPois.size():0;
		for (int i=0;i<mPois_Size;i++) {
			builder_UserActivity.addPoi(mPois.get(i));
		}

		//��װ��APP��Ϣ�Լ�ʹ�������mobile only
	    List<App> mApps=pUserActivity.getInstalled_app();
	    if(null!=mApps&&mApps.size()>0)
	    {
	       
	       int mApps_Size=mApps.size();
	       for(int i=0;i<mApps_Size;i++)
	       {
	    	   UserActivityData.App.Builder builder_APP=UserActivityData.App.newBuilder();
	    	   builder_APP.setName(mApps.get(i).getName());
	    	   builder_APP.setVersion(mApps.get(i).getVersion());
	    	   
	    	   List<AppActivity> mAppActivities=mApps.get(i).getActivity();
	    	   if(null!=mAppActivities&&mAppActivities.size()>0)
	    	   {
	    		  int mAppActivity_Size=mAppActivities.size();
	    		  
	    		  for(int index=0;index<mAppActivity_Size;index++)
	    		  {
	    			  UserActivityData.AppActivity.Builder builder_AppActivity=UserActivityData.AppActivity.newBuilder();
	    			  builder_AppActivity.setStartTimestamp(mAppActivities.get(index).getStart_timestamp());
	    			  builder_AppActivity.setDuration(mAppActivities.get(index).getDuration());
	    			  builder_APP.addActivity(builder_AppActivity);

	    		  }
	    	   }
	    	   builder_UserActivity.addInstalledApp(builder_APP);
	       }			
		}
	    
	    //�ڰٶȵ�������վ�������ؼ���
	    List<String> mSearch_keywords=pUserActivity.getSearch_keyword();
	    int mSearch_keywords_Size=mSearch_keywords!=null?mSearch_keywords.size():0;
	    for(int i=0;i<mSearch_keywords_Size;i++)
	    {
	    	
	    	builder_UserActivity.addSearchKeyword(mSearch_keywords.get(i));
	    }
	    
	    //��ҳ�����¼
	    List<String> mUrls =pUserActivity.getUrl();
	    int mUrls_Size=mUrls!=null?mUrls.size():0;
	    for(int i=0;i>mUrls_Size;i++)
	    {
	    	builder_UserActivity.addUrl(mUrls.get(i));
	    }
	    
	    //����ʱ�������ʽ����20130601130122����YYYYmmddhhMMss��
	    List<String> mBoot_timestamps=pUserActivity.getBoot_timestamp();
	    int mBoot_timestamps_Size=mBoot_timestamps!=null?mBoot_timestamps.size():0;
	    for(int i=0;i<mBoot_timestamps_Size;i++)
	    {
	    	builder_UserActivity.addBootTimestamp(mBoot_timestamps.get(i));
	    }
	    
	    //�ػ�ʱ�������ʽ����20130601130122����YYYYmmddhhMMss��
	    List<String> mShutdown_timestamps = pUserActivity.getShutdown_timestamp();
	    int mShutdown_timestamps_Size=mShutdown_timestamps!=null?mShutdown_timestamps.size():0;
	    for(int i=0;i<mShutdown_timestamps_Size;i++)
	    {
	    	builder_UserActivity.addShutdownTimestamp(mShutdown_timestamps.get(i));	
	    }
	    
	    //��绰��ʱ�䣬ʱ����mobile only
	    List<PhoneActivity> mPhoneActivities = pUserActivity.getPhone_activity();
	    if(null!=mPhoneActivities&&mPhoneActivities.size()>0)
	    {
	    	UserActivityData.PhoneActivity.Builder builder_PhoneActivity=UserActivityData.PhoneActivity.newBuilder();
	    	int mPhoneActivities_Size=mPhoneActivities.size();
	    	for(int i=0;i<mPhoneActivities_Size;i++)
	    	{
	    		builder_PhoneActivity.setStartTimestamp(mPhoneActivities.get(i).getStart_timestamp());
	    		builder_PhoneActivity.setDuration(mPhoneActivities.get(i).getDuration());
	    		builder_UserActivity.addPhoneActivity(builder_PhoneActivity);
	    	}
	    }

	    //���ŷ���ʱ�������ʽ����20130601130122����YYYYmmddhhMMss����mobile only
	    List<String> mSms_sent_timestamps = pUserActivity.getSms_sent_timestamp();
	    int mSms_sent_timestamps_Size=mSms_sent_timestamps!=null?mSms_sent_timestamps.size():0;
        for(int i=0;i<mSms_sent_timestamps_Size;i++)
        {
        	builder_UserActivity.addSmsSentTimestamp(mSms_sent_timestamps.get(i));
        }
	    
        //���ſ�Ⱦ�Ʒ�Ͽ�����Ƶ���
        List<ThirdPartyVideoActivity> mThirdPartyVideoActivitys=pUserActivity.getThird_party_video_activity();
        if(null!=mThirdPartyVideoActivitys&&mThirdPartyVideoActivitys.size()>0)
        {
        	UserActivityData.ThirdPartyVideoActivity.Builder builder_ThirdPartyVideo=UserActivityData.ThirdPartyVideoActivity.newBuilder();
        	int mThirdPartyVideoActivitys_Size=mThirdPartyVideoActivitys.size();
        	for(int i=0;i<mThirdPartyVideoActivitys_Size;i++)
        	{
        		builder_ThirdPartyVideo.setProvider(mThirdPartyVideoActivitys.get(i).getProvider());
        		builder_ThirdPartyVideo.setTimestamp(mThirdPartyVideoActivitys.get(i).getTimestamp());
        		builder_ThirdPartyVideo.setVideoName(mThirdPartyVideoActivitys.get(i).getVideo_name());
        		builder_UserActivity.addThirdPartyVideoActivity(builder_ThirdPartyVideo);
        	}   	
        } 
         
        //��������Щ���̣���������, client only
         List<ProcessProto> mProcesses=pUserActivity.getProcess();
         if(null!=mProcesses&&mProcesses.size()>0)
         {
        	 
        	 int mProcesses_Size=mProcesses.size();
        	 for(int i=0;i<mProcesses_Size;i++)
        	 {
        		 UserActivityData.Process.Builder builder_Process=UserActivityData.Process.newBuilder();
        		 builder_Process.setName(mProcesses.get(i).getName());
        		 List<ProcessActivity>  mProcessActivities= mProcesses.get(i).getProcessActivities();
        		 if(null!=mProcessActivities&&mProcessActivities.size()>0)
        		 {
        			 UserActivityData.ProcessActivity.Builder builder_ProcessActivity=UserActivityData.ProcessActivity.newBuilder();
        			 int mProcessActivities_Size=mProcessActivities.size();
        			 for(int index=0;index<mProcessActivities_Size;index++)
        			 {
        				 builder_ProcessActivity.setStartTimestamp(mProcessActivities.get(index).getStart_timestamp());
        				 builder_ProcessActivity.setDuration(mProcessActivities.get(index).getDuration());
        				 builder_Process.addActivity(builder_ProcessActivity);
        			 }
        		 }
        		 builder_UserActivity.addProcess(builder_Process);
        	}
         }
        
        //����ڵı�������, client only
         List<WindowProto> mWindows=  pUserActivity.getWindow();
         if(null!=mWindows&&mWindows.size()>0)
         {
        	 
        	 int mWindows_Size=mWindows.size();
        	 for(int i=0;i<mWindows_Size;i++)
        	 {
        		 UserActivityData.Window.Builder builder_Window=UserActivityData.Window.newBuilder();
        		 builder_Window.setName(mWindows.get(i).getName());
        		 List<WindowActivity> mWindowActivities= mWindows.get(i).getActivity();
        		 if(null!=mWindowActivities&&mWindowActivities.size()>0)
        		 {
        			 UserActivityData.WindowActivity.Builder builder_WindowActivity=UserActivityData.WindowActivity.newBuilder();
        			 int mWindowActivities_Size=mWindowActivities.size();
        			 for(int index=0;index<mWindowActivities_Size;index++)
        			 {
        				 builder_WindowActivity.setStartTimestamp(mWindowActivities.get(index).getStart_timestamp());
        				 builder_WindowActivity.setDuration(mWindowActivities.get(index).getDuration());
        				 builder_Window.addActivity(builder_WindowActivity);
        			 }
        		 }
        		 builder_UserActivity.addWindow( builder_Window);
        	 }
        	 
         }
        //�ֻ���Ϣ, mobile only
        DeviceInfo mDeviceInfo= pUserActivity.getDevice_info();
        if(null!=mDeviceInfo)
        {
            UserActivityData.DeviceInfo.Builder builder_DeviceInfo=UserActivityData.DeviceInfo.newBuilder();
            builder_DeviceInfo.setImei(mDeviceInfo.getImei());
            builder_DeviceInfo.setImsi(mDeviceInfo.getImsi());
            builder_DeviceInfo.setManufacturer(mDeviceInfo.getManufacturer());
            builder_DeviceInfo.setModel(mDeviceInfo.getModel());
            builder_DeviceInfo.setScreenResolution(mDeviceInfo.getScreen_resolution());
            builder_DeviceInfo.setOsVersion(mDeviceInfo.getOs_version());
            builder_DeviceInfo.setOsCustermize(mDeviceInfo.getOs_custermize());
            builder_UserActivity.setDeviceInfo(builder_DeviceInfo); 
        }
        
		UserActivityData.UserActivity info_UserActivity=builder_UserActivity.build();
		return info_UserActivity.toByteArray();
	}

	
	
	
	
	/**
	 * �����ֻ���APP����Ϣ
	 * @return builder_App
	 * @deprecated ������getConstructorData(UserActivity pUserActivity)�н��й���,���������ҷ���
	 */
	public UserActivityData.App.Builder getAppBuilder()
	{
		UserActivityData.App.Builder builder_App=UserActivityData.App.newBuilder();
	    //��ȡ�ֻ�APP��ʹ��ʱ��
		//��ȡ����
		if(null!=mApps&&mApps.size()>=0)
		{
			int count=mApps.size();
			for(int i=0;i<count;i++)
			{
				builder_App.setName(mApps.get(i).getName());
				builder_App.setVersion(mApps.get(i).getVersion());
				mAppActivities=mApps.get(i).getActivity();
				if(null!=mAppActivities&&mAppActivities.size()>=0)
				{
				  	for (AppActivity mAppActivity : mAppActivities) {
						UserActivityData.AppActivity.Builder builder=UserActivityData.AppActivity.newBuilder();
						builder.setStartTimestamp(mAppActivity.getStart_timestamp());
						builder.setDuration(mAppActivity.getDuration());
						builder_App.addActivity(builder);
					}
				}
			}
		}
		
		return builder_App;
	}
	
	
	/**
	 * ��ȡ��������Ƶ�������Ĳ��ż�¼
	 * @return builder_ThirdPartyVideo
	 * @deprecated ������getConstructorData(UserActivity pUserActivity)�н��й���,���������ҷ���
	 */
	public UserActivityData.ThirdPartyVideoActivity.Builder getThirdPartyVideoBuilder()
	{
		UserActivityData.ThirdPartyVideoActivity.Builder builder_ThirdPartyVideo=UserActivityData.ThirdPartyVideoActivity.newBuilder();
        //��ȡ���ż�¼���󼯺���Ϣ
		//��ȡ����
		if(null!=mThirdPartyVideoActivities&&mThirdPartyVideoActivities.size()>=0)
		{
			
		}
		return builder_ThirdPartyVideo;
	}
	
	
	
	
	/**
	 * �����ֻ���Ϣ
	 * 
	 * @return builder_DeviceInfo
	 * @deprecated ������getConstructorData(UserActivity pUserActivity)�н��й���,���������ҷ���
	 */
	public UserActivityData.DeviceInfo.Builder getDeviceInfoBuilder() {
		UserActivityData.DeviceInfo.Builder builder_DeviceInfo = UserActivityData.DeviceInfo
				.newBuilder();
		// ��ȡ��Ϣ
		// ����
		if (null != mDeviceInfo) {
			builder_DeviceInfo.setImei(mDeviceInfo.getImei());
			builder_DeviceInfo.setImsi(mDeviceInfo.getImsi());
			builder_DeviceInfo.setManufacturer(mDeviceInfo.getManufacturer());
			builder_DeviceInfo.setModel(mDeviceInfo.getModel());
			builder_DeviceInfo.setScreenResolution(mDeviceInfo
					.getScreen_resolution());
			builder_DeviceInfo.setOsVersion(mDeviceInfo.getOs_version());
			builder_DeviceInfo.setOsCustermize(mDeviceInfo.getOs_custermize());
		}
		return builder_DeviceInfo;
	}

	/**
	 * ��ȡ���������Ϣ
	 * @deprecated ������getConstructorData(UserActivity pUserActivity)�н��й���,���������ҷ���
	 * @return builder_Process
	 */
	@SuppressWarnings("unused")
	private UserActivityData.Process.Builder getProcessBuilder() {
		UserActivityData.Process.Builder builder_Process = UserActivityData.Process
				.newBuilder();
        
		if (null != mProcesses && mProcesses.size() >= 0) {
			int count = mProcesses.size();
			for (int i = 0; i < count; i++) {
				builder_Process.setName(mProcesses.get(i).getName());
			 	// ��ȡ����������Ϣ����
				mProcessActivities = mProcesses.get(i).getProcessActivities();
				if (null != mProcessActivities
						&& mProcessActivities.size() >= 0) {
					for (ProcessActivity mProcessActivity : mProcessActivities) {
						UserActivityData.ProcessActivity.Builder builder = UserActivityData.ProcessActivity
								.newBuilder();
						builder.setStartTimestamp(mProcessActivity
								.getStart_timestamp());
						builder.setDuration(mProcessActivity.getDuration());
						builder_Process.addActivity(builder);
					}
				}
			}
		}
		return builder_Process;
	}

	/**
	 * ��ȡ����������Ϣ Builder ����(���̴�ʱ���,��ʱ��)
	 * @deprecated ������getConstructorData(UserActivity pUserActivity)�н��й���,���������ҷ���
	 * @return
	 */
	@SuppressWarnings("unused")
	private UserActivityData.ProcessActivity.Builder getProcessActivityBuilder() {
		// �������
		UserActivityData.ProcessActivity.Builder builder_ProcessActivity = UserActivityData.ProcessActivity
				.newBuilder();
		for (ProcessActivity processActivity : mProcessActivities) {
			builder_ProcessActivity.setStartTimestamp(processActivity
					.getStart_timestamp());
			builder_ProcessActivity.setDuration(processActivity.getDuration());
		}
		builder_ProcessActivity.build();

		return builder_ProcessActivity;
	}

}
