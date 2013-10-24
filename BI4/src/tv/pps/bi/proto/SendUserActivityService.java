package tv.pps.bi.proto;

import org.apache.commons.codec.binary.Base64;

import tv.pps.bi.db.DBAPPManager;
import tv.pps.bi.db.DBOperation;
import tv.pps.bi.db.config.DBConstance;
import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.db.config.URL4BIConfig;
import tv.pps.bi.proto.model.SendTime;
import tv.pps.bi.proto.model.UserActivity;
import tv.pps.bi.utils.LogUtils;
import tv.pps.bi.utils.ProtoNetWorkManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Ͷ���û���Ϊ���� ������
 * @author jiangqingqing
 * @time 2013/10/24
 */
public class SendUserActivityService extends IntentService{

	private MessageToEntityService mMsgService; // ��ȡ�û���Ϊ��Ϣ ����װ��ʵ�����
	private ProtoBuffUserActivityService mProtoBuffUserActivityService;
	private UserActivity mUserActivity;
	private byte[] infoBytes;                   //protobuff��ʽ���ĵ��ֽ�����
	private Base64 base64;
	private Context mContext;
	public SendUserActivityService()
	{
		super("Ͷ������...");
	}
	

	@Override
	public void onCreate() {
		mContext=this;
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return super.onBind(intent);
	}

	@SuppressWarnings("static-access")
	@Override
	protected void onHandleIntent(Intent intent) {
		mMsgService = new MessageToEntityService(mContext);
		mUserActivity = mMsgService.getMsgUserEntity();
		//mMsgService.close();//�ر����ݿ�
		mProtoBuffUserActivityService = new ProtoBuffUserActivityService();
		infoBytes = mProtoBuffUserActivityService
				.getConstructorData(mUserActivity);
		LogUtils.v(TagConstance.TAG_SENDDATA, "Ҫpost��ProtoBuff����Ϊ:" + new String(infoBytes));
		// ���м���
		// base64 = new Base64();
		// post_str = new String(base64.encodeBase64Chunked(infoBytes));
		// ���м��� �ϴ�
					base64 = new Base64();
					int flag=0; //���ʹ�����־λ
					boolean result=false ;
					
					DBOperation operation = new DBOperation(mContext);
					long send_time=System.currentTimeMillis();
					SendTime mSendTime=operation.getSendTime();
					if(mSendTime!=null&&(send_time-operation.getSendTime().getSendtime()<10*60*1000))
					{
						LogUtils.v(TagConstance.TAG_SENDDATA, "ʮ����֮���Ѿ�Ͷ�ݹ����ݣ���ʱ��Ͷ��");
					}else {
						while(flag<5) //�˳�ѭ����ʱ����1,���ѭ����������.2,���ͳɹ�
						{
							result= ProtoNetWorkManager
									.postUserActivityByByte(
											base64.encodeBase64Chunked(infoBytes),URL4BIConfig.DELIVER_URL);
							flag++;
							if(result)
								break;
						}
						
//						result= ProtoNetWorkManager
//								.postUserActivityByByte(
//										base64.encodeBase64Chunked(infoBytes),ProtoNetWorkManager.DELIVER_URL);
						if (result) {
							//ɾ�����ͳɹ������ݱ��е�����,���Ҽ�¼�µ�ǰ���ͳɹ���ʱ�� ��ӵ����ݿ���
							LogUtils.i(TagConstance.TAG_SENDDATA, "�������ݷ��ͳɹ���׼��ɾ�����ݿ��е����ݱ�");
							DBAPPManager manager=DBAPPManager.getDBManager(mContext); 	
							manager.delete();
							operation.deleteRecordsInTable(DBConstance.TABLE_GPS);  //ɾ��GPS��Ϣ����
							operation.deleteRecordsInTable(DBConstance.TABLE_URL);     //ɾ����ҳ�����¼��Ϣ����
							operation.deleteRecordsInTable(DBConstance.TABLE_BOOT_TIME); //ɾ��ϵͳ����ʱ����Ϣ����
							operation.deleteRecordsInTable(DBConstance.TABLE_SHUT_TIME);
							operation.deleteRecordsInTable(DBConstance.TABLE_PHONE); //ɾ����绰ʱ�����Ϣ����
							operation.deleteRecordsInTable(DBConstance.TABLE_SMS); //ɾ����������Ϣ����
							//��Ͷ�����ݵļ�¼���뵽���ݿ���
							operation.deleteSendTime();
						    boolean insert_flag=operation.insertSendTime(new SendTime(send_time));
							if(insert_flag)
							{
								LogUtils.v(TagConstance.TAG_SENDDATA, "Ͷ�ݼ�¼�������ݿ�ɹ�");
							}
							
							//LogUtils.i(TagConstance.TAG_SENDDATA, "���������Ϊ:"+operation.getSendTime().getSendtime());
							LogUtils.i(TagConstance.TAG_SENDDATA, "�ɹ�ɾ�����ݿ��е����ݱ�");
							LogUtils.v(TagConstance.TAG_SENDDATA, "Post���󷵻ؽ��Ϊ���ɹ�");
					}
					}
				
					operation.close();
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
			int flag=0; //���ʹ�����־λ
			boolean result=false ;
			
			DBOperation operation = new DBOperation(mContext);
			long send_time=System.currentTimeMillis();
			SendTime mSendTime=operation.getSendTime();
			if(mSendTime!=null&&(send_time-operation.getSendTime().getSendtime()<10*60*1000))
			{
				LogUtils.v(TagConstance.TAG_SENDDATA, "ʮ����֮���Ѿ�Ͷ�ݹ����ݣ���ʱ��Ͷ��");
			}else {
				while(flag<5) //�˳�ѭ����ʱ����1,���ѭ����������.2,���ͳɹ�
				{
					result= ProtoNetWorkManager
							.postUserActivityByByte(
									base64.encodeBase64Chunked(infoBytes),URL4BIConfig.DELIVER_URL);
					flag++;
					if(result)
						break;
				}
				
//				result= ProtoNetWorkManager
//						.postUserActivityByByte(
//								base64.encodeBase64Chunked(infoBytes),ProtoNetWorkManager.DELIVER_URL);
				if (result) {
					//ɾ�����ͳɹ������ݱ��е�����,���Ҽ�¼�µ�ǰ���ͳɹ���ʱ�� ��ӵ����ݿ���
					LogUtils.i(TagConstance.TAG_SENDDATA, "�������ݷ��ͳɹ���׼��ɾ�����ݿ��е����ݱ�");
					DBAPPManager manager=DBAPPManager.getDBManager(mContext); 	
					manager.delete();
					operation.deleteRecordsInTable(DBConstance.TABLE_GPS);  //ɾ��GPS��Ϣ����
					operation.deleteRecordsInTable(DBConstance.TABLE_URL);     //ɾ����ҳ�����¼��Ϣ����
					operation.deleteRecordsInTable(DBConstance.TABLE_BOOT_TIME); //ɾ��ϵͳ����ʱ����Ϣ����
					operation.deleteRecordsInTable(DBConstance.TABLE_SHUT_TIME);
					operation.deleteRecordsInTable(DBConstance.TABLE_PHONE); //ɾ����绰ʱ�����Ϣ����
					operation.deleteRecordsInTable(DBConstance.TABLE_SMS); //ɾ����������Ϣ����
					//��Ͷ�����ݵļ�¼���뵽���ݿ���
					operation.deleteSendTime();
				    boolean insert_flag=operation.insertSendTime(new SendTime(send_time));
					if(insert_flag)
					{
						LogUtils.v(TagConstance.TAG_SENDDATA, "Ͷ�ݼ�¼�������ݿ�ɹ�");
					}
					
					//LogUtils.i(TagConstance.TAG_SENDDATA, "���������Ϊ:"+operation.getSendTime().getSendtime());
					LogUtils.i(TagConstance.TAG_SENDDATA, "�ɹ�ɾ�����ݿ��е����ݱ�");
					LogUtils.v(TagConstance.TAG_SENDDATA, "Post���󷵻ؽ��Ϊ���ɹ�");
			}
			}
		
			operation.close();
		}
	};
}
