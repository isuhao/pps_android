package tv.pps.bi.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tv.pps.bi.proto.imp.AchieveUserActivityManagerImp;
import tv.pps.bi.proto.model.App;
import tv.pps.bi.proto.model.DeviceInfo;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.ProcessActivity;
import tv.pps.bi.proto.model.ProcessProto;
import tv.pps.bi.proto.model.ThirdPartyVideoActivity;
import tv.pps.bi.proto.model.UserActivity;
import tv.pps.bi.proto.model.WindowActivity;
import tv.pps.bi.proto.model.WindowProto;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * ����ȡ�Ľ��н��з�װ���û���Ϊʵ�����
 * 
 * @author jiangqingqing
 * @time 2013/09/03 17:50
 */
public class MessageToEntityService {

	private UserActivity mUserActivity;
	private Context mContext;
	private AchieveUserActivityManagerInterface mActivityManagerInterface;
	private SharedPreferences mSharedPreferences;

	public void close() {
		mActivityManagerInterface.close();
	}

	// ��ʼ���û���Ϊ����
	@SuppressWarnings("deprecation")
	public MessageToEntityService(Context pContext) {

		this.mContext = pContext;
		mUserActivity = new UserActivity();
		mSharedPreferences = mContext.getSharedPreferences("protobuff",
				Context.MODE_WORLD_READABLE);
		mActivityManagerInterface = new AchieveUserActivityManagerImp(mContext);
	}

	private String getUid(SharedPreferences pSharedPreferences) {
		return pSharedPreferences
				.getString("uid", UUID.randomUUID().toString());
	}

	private String getLoginId(SharedPreferences pSharedPreferences) {
		return pSharedPreferences.getString("login", "");
	}

	/**
	 * �ѻ�ȡ�����û���Ϊ��Ϣ���з�װ����׼UserActivity�У�Ȼ����й���Protobuff��ʽ����
	 * 
	 * @return ��װ�õ��û���Ϊ����
	 */
	public UserActivity getMsgUserEntity() {

		mUserActivity.setUid(getUid(mSharedPreferences)); // Ψһ��ʾ�ÿͻ��˵������û�id���ͻ���Ϊ��flash
															// cookie
															// id���ƶ���Ϊopen udid
		mUserActivity.setLogin(getLoginId(mSharedPreferences)); // ע���û���¼id
		mUserActivity.setPlatform(mActivityManagerInterface.getUserPlatform()); // ��ȡֵ��pps_ios
																				// |
																				// pps_android
																				// |
																				// pps_pc
																				// |
																				// iqiyi_ios
																				// |
																				// iqiyi_android
																				// |
																				// iqiyi_pc
		mUserActivity.setMac(mActivityManagerInterface.getUserMac()); // �豸mac��ַ
		mUserActivity.setModel(mActivityManagerInterface.getUserModel()); // �豸�ͺ�

		// GPS����
		GPS mGps = mActivityManagerInterface.getUserGPS();
		if (null != mGps) {
			mUserActivity.setGps(mGps);
		}

		// ��ͼPOI��Ϣ��gps���긽��300���ڵĵ�ͼ��Ϣ
		List<String> mPoiLists = mActivityManagerInterface.getUserPoi();
		mUserActivity.setPoi(mPoiLists);

		// ��װ��APP��Ϣ�Լ�ʹ�����
		List<App> mAppsLists = mActivityManagerInterface.getUserInstalled_app();
		if (null != mAppsLists) {
			mUserActivity.setInstalled_app(mAppsLists);
		}

		// �ڰٶȵ�������վ�������ؼ���
		List<String> keywordLists = mActivityManagerInterface
				.getUserSearch_keyword();
		if (null != keywordLists) {
			mUserActivity.setSearch_keyword(keywordLists);
		}

		// ������ҳ��ʷ��¼
		List<String> urlLists = mActivityManagerInterface.getUserUrl();
		if (null != urlLists) {
			mUserActivity.setUrl(urlLists);

		}

		// ����ʱ���
		List<String> boot_timestampLists = mActivityManagerInterface
				.getUserBoot_timestamp();
		if (null != boot_timestampLists) {
			mUserActivity.setBoot_timestamp(boot_timestampLists);
		}

		// �ػ�ʱ���
		List<String> shutdown_timestampLists = mActivityManagerInterface
				.getUserShutdown_timestamp();
		if (null != shutdown_timestampLists) {
			mUserActivity.setShutdown_timestamp(shutdown_timestampLists);
		}

		// ��绰��ʱ�䣬ʱ��
		List<PhoneActivity> phone_activityLists = mActivityManagerInterface
				.getUserPhone_activity();
		if (null != phone_activityLists) {
			mUserActivity.setPhone_activity(phone_activityLists);
		}

		// ���ŷ���ʱ���
		List<String> sms_sent_timestampLists = mActivityManagerInterface
				.getUserShutdown_timestamp();
		if (null != sms_sent_timestampLists) {
			mUserActivity.setSms_sent_timestamp(sms_sent_timestampLists);
		}
		// ��������Ƶ���ͻ��˵Ĳ�����ʷ��¼
		// getThirdVideoActivity();
		// ��������Ƶ���ͻ��˵Ĳ�����ʷ��¼
		// �������Ƶ�����ʱ����Ϣ
		// getProcessActivity();
		// ����ڵı�������
		// getWindowActivity();

		// �ֻ���Ϣ
		DeviceInfo mDeviceInfo = mActivityManagerInterface.getUserDevice_info();
		if (null != mDeviceInfo) {
			mUserActivity.setDevice_info(mDeviceInfo);
		}
		mActivityManagerInterface.close();  //���йر����ݿ�
		return mUserActivity;
	}

	/**
	 * ��������Ƶ���ͻ��˵Ĳ�����ʷ��¼
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void getThirdVideoActivity() {
		// ��������Ƶ���ͻ��˵Ĳ�����ʷ��¼
		List<ThirdPartyVideoActivity> thirdLists = new ArrayList<ThirdPartyVideoActivity>();
		mUserActivity.setThird_party_video_activity(thirdLists);
	}

	/**
	 * �������Ƶ�����ʱ����Ϣ
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void getProcessActivity() {
		// �������Ƶ�����ʱ����Ϣ
		List<ProcessProto> processLists = new ArrayList<ProcessProto>();
		List<ProcessActivity> processActivities = new ArrayList<ProcessActivity>();
		ProcessActivity mProcessActivity = new ProcessActivity();
		mProcessActivity.setStart_timestamp("12333333");
		mProcessActivity.setDuration(2335223);
		processActivities.add(mProcessActivity);
		ProcessProto mProcessProto = new ProcessProto();
		mProcessProto.setName("PPS_Android");
		mProcessProto.setProcessActivities(processActivities);
		processLists.add(mProcessProto);
		mUserActivity.setProcess(processLists);
	}

	/**
	 * ����ڵı�������
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void getWindowActivity() {
		// /����ڵı�������
		List<WindowProto> windowLists = new ArrayList<WindowProto>();
		List<WindowActivity> windowActivities = new ArrayList<WindowActivity>();
		WindowActivity activity = new WindowActivity();
		activity.setStart_timestamp("1314564");
		activity.setDuration(12333);
		windowActivities.add(activity);
		WindowProto mWindowProto = new WindowProto();
		mWindowProto.setName("PPS_Android");
		mWindowProto.setActivity(windowActivities);
		windowLists.add(mWindowProto);
		mUserActivity.setWindow(windowLists);
	}
}
