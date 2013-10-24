package tv.pps.bi.db;

import java.util.ArrayList;
import java.util.List;

import tv.pps.bi.db.config.DBConstance;
import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.proto.biz.CallLogService;
import tv.pps.bi.proto.biz.GPSInfoService;
import tv.pps.bi.proto.biz.MessageInfoService;
import tv.pps.bi.proto.biz.ShutdownInfoService;
import tv.pps.bi.proto.biz.URLService;
import tv.pps.bi.proto.model.Bootup;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.NetTime;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.SMS;
import tv.pps.bi.proto.model.SendTime;
import tv.pps.bi.proto.model.Shutdown;
import tv.pps.bi.proto.model.URLInfo;
import tv.pps.bi.utils.LogUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBOperation {

	public final static String TAG = "DBOperation";
	SQLiteDatabase db;
	DBHelper helper;
	Context context;
	public static DBOperation operation;
	int count = 0;

	public DBOperation(Context context) {
		this.context = context;
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
		LogUtils.i(TagConstance.TAG_DATABASE, "���ݿ�" + DBConstance.DB_NAME + "�򿪻򴴽��ɹ�");
	}

	public static DBOperation getDBOperation(Context context) {
		synchronized (operation) {

			if (operation == null) {
				operation = new DBOperation(context);
			}
		}
		return operation;
	}

	public void close() {

		try {
			if (db != null)
				db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtils.i(TagConstance.TAG_DATABASE, "�ر����ݿ�" + DBConstance.DB_NAME);
	}

	
	/**
	 * ����Ͽ���� ʵ����в���
	 * @auther jiangqingqing
	 * @param pNetTime
	 * @return
	 */
	public boolean insertNetTime(NetTime pNetTime)
	{
	    try {
	    	ContentValues mContentValues=new ContentValues();
		    mContentValues.put("net_time", pNetTime.getNettime());
		    mContentValues.put("net_flag", pNetTime.getFlag());
		    long result = db.insert(DBConstance.TABLE_NET_INFO, null, mContentValues);
		    if(result!=-1)
		    {
		    	return true;
		    }
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "netinfo" + "�����ݲ����쳣");
		}
		
		return false;
	}
	

	/**
	 * ����ɾ��
	 * @auther jiangqingqing
	 * @param pNetTime
	 * @return
	 */
	public boolean deleteNetTime(NetTime pNetTime)
	{
		
		 try {
			int result= db.delete(DBConstance.TABLE_NET_INFO, "net_flag = ?", new String[]{String.valueOf(pNetTime.getFlag())});
		    if(result!=0)
		    {
		    	return true;
		    }
		 } catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "netinfo" + "������ɾ���쳣");
		}
		 return false;
	}
	
	
	/**
	 * 
	 * @auther jiangqingqing
	 * @param pNetTime
	 * @return
	 */
	public boolean updateNetTime(NetTime pNetTime)
	{
		try {
			ContentValues mContentValues=new ContentValues();
			mContentValues.put("net_time", pNetTime.getNettime());
		    mContentValues.put("net_flag", pNetTime.getFlag());
			int result= db.update(DBConstance.TABLE_NET_INFO, mContentValues, "net_flag = ?", new String[]{String.valueOf(pNetTime.getFlag())});
			if(result!=0)
			{
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "netinfo" + "�����ݸ����쳣");
		}
		return false;
	}
	
	
	/**
	 * ɾ�����е�����仯���е�����
	 * @auther jiangqingqing
	 * @return
	 */
			
	public boolean delteAllNetTime()
	{
		try {
		  int result=db.delete(DBConstance.TABLE_NET_INFO, null, null);
		  if(result!=0)
		  {
			  return true;
		  }	
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "netinfo" + "������ɾ���쳣");
		}
		return false;
	}
	
	
	/**
	 * ��Ͷ�ݼ�¼ ���뵽���ݿ���
	 * @auther jiangqingqing
	 * @param pSendTime Ͷ������ʵ����
	 * @return
	 */
	public boolean insertSendTime(SendTime pSendTime)
	{
		try {
			ContentValues mContentValues=new ContentValues(1);
			mContentValues.put("send_time", String.valueOf(pSendTime.getSendtime()));
			long result=db.insert(DBConstance.TABLE_SEND_DATA, null, mContentValues);
			if(result!=-1)
			{
				return true;
			}
			
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "send_data" + "�����ݲ����쳣");
		}
		return false;
	}
	

	/**
	 * ɾ��Ͷ�����ݼ�¼��
	 * @auther jiangqingqing
	 * @return
	 */
	public boolean deleteSendTime()
	{
		try {
			long result= db.delete(DBConstance.TABLE_SEND_DATA, null, null);
			if(result!=0)
			{
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "send_data" + "������ɾ���쳣");
		}
		return false;
	}
	
	
	/**
	 * ��ȡ
	 * @return
	 */
	public SendTime getSendTime()
	{
		try {
			List<SendTime> mList=new ArrayList<SendTime>();
			SendTime mSendTime;
			Cursor mCursor= db.query(DBConstance.TABLE_SEND_DATA, new String[]{"send_time"}, null, null, null, null, null);
		    if(mCursor!=null&&mCursor.getCount()>=0)
		    {
		    	while(mCursor.moveToNext())
		    	{
		    		mSendTime=new SendTime();
		    		mSendTime.setSendtime(Long.valueOf(mCursor.getString(mCursor.getColumnIndex("send_time"))));
		    		mList.add(mSendTime);
		    	}
		    	return mList.get(mList.size()-1);
		    }
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "send_data" + "�����ݲ�ѯ�쳣");
		}
		
		return null;
	}
	
	/**
	 * ��������
	 * 
	 * @param context
	 * @param table
	 * @param cv
	 * @return
	 */
	public boolean insertIntoTable(Context context, String table,
			ContentValues cv) {// �������ݱ�
		db.beginTransaction();
		try {
			long rowid = db.insert(table, null, cv);
			if (rowid != -1) {
				db.setTransactionSuccessful();
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, table + "�����ݲ����쳣");
		} finally {
			db.endTransaction();
		}
		return false;
	}

	/**
	 * ɾ�����ݱ�
	 * 
	 * @param context
	 * @param table
	 * @return
	 */
	public boolean deleteTable(Context context, String table) {
		db.beginTransaction();
		try {
			int id = db.delete(table, null, null);
			if (id != -1) {
				db.setTransactionSuccessful();
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, table + "��ɾ���쳣");
		} finally {
			db.endTransaction();
		}
		return false;
	}

	/**
	 * �������ݱ�
	 * 
	 * @param context
	 * @param table
	 * @param cv
	 * @return
	 */
	public boolean updateTable(Context context, String table, ContentValues cv) {
		db.beginTransaction();
		try {
			int id = db.update(table, cv, null, null);
			if (id != -1) {
				db.setTransactionSuccessful();
				return true;
			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, table + "������쳣");
		} finally {
			db.endTransaction();
		}
		return false;
	}


	/**
	 * ��ѯ���ݱ�
	 * 
	 * @param context
	 * @param table
	 * @return
	 */
	public Cursor queryTable(Context context, String table) {

		return db.query(table, null, null, null, null, null, " timestamp desc");

	}

	/**
	 * ��ѯ���е�ʱ����ֶ�
	 * 
	 * @param context
	 * @param table
	 * @return
	 */
	public long queryTimestamp(Context context, String table) {
		Cursor c = null;
		try {
			c = db.query(table, null, null, null, null, null, " timestamp desc");
			if (c.getCount() == 0) {
				try {
					if (c != null)
						c.close();
				} catch (Exception e) {
					LogUtils.i(TagConstance.TAG_DATABASE, table + "���α�ر��쳣");
				}
				return 0L;
			} else {
				c.moveToNext();
				long result = c.getLong(c.getColumnIndex("timestamp"));
				try {
					if (c != null)
						c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}
		} catch (Exception e1) {
			LogUtils.i(TagConstance.TAG_DATABASE, table + "���ѯʱ����쳣");
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0L;
	}

	/**
	 * ��ѯcontrol table��timestamp�ֶ�
	 * 
	 * @param context
	 * @param type
	 * @return
	 */
	public long queryTimestampInControlTable(Context context, String type) {

		Cursor c = null;
		try {
			c = db.query(DBConstance.TABLE_INFOMATION_CONTROL,
					new String[] { "timestamp" }, " type = ? ",
					new String[] { type }, null, null, " timestamp asc");

			if (null != c && c.getCount() > 0) {

				c.moveToNext();
				long result = c.getLong(c.getColumnIndex("timestamp"));
				try {
					if (c != null)
						c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;

			}
		} catch (Exception e) {
			LogUtils.i(TagConstance.TAG_DATABASE, "control���ѯʱ����쳣");
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0L;
	}

	/**
	 * ���¿��Ʊ��timestamp�ֶ�
	 * 
	 * @param context
	 * @param type
	 * @param timestamp
	 * @return
	 */
	public boolean updateTimestampInControlTable(Context context, String type,
			long timestamp) {
		String table = DBConstance.TABLE_INFOMATION_CONTROL;
		ContentValues cv = new ContentValues();
		cv.put("timestamp", timestamp);
		int id = db.update(table, cv, " type = ? ", new String[] { type });
		if (id != -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��ʼ�����Ʊ�
	 * 
	 * @param context
	 */
	public void initializeTableControl(Context context) {
		String table = DBConstance.TABLE_INFOMATION_CONTROL;
		String[] type = { "gps", "url", "boot", "shut", "phone", "sms" };
		long timestamp = 0L;
		ContentValues cv = new ContentValues();
		for (int i = 0; i < type.length; i++) {
			cv.put("type", type[i]);
			cv.put("timestamp", timestamp);
			db.insert(table, null, cv);
		}
		
		//��ʼ��Ͷ��ʱ��
	    insertSendTime(new SendTime(System.currentTimeMillis()));	
	   
		
		
		LogUtils.i(TagConstance.TAG_DATABASE, DBConstance.TABLE_INFOMATION_CONTROL + "���ݱ��ʼ���ɹ�");
	}

	/**
	 * ���¿��Ʊ��ʱ���
	 * 
	 * @param table
	 * @param type
	 * @param value
	 */
	public void updateTableControl(String table, String type, String value) {

		ContentValues cv = new ContentValues();
		cv.put(type, value);
		updateTable(context, table, cv);
	}

	/**
	 * ɾ�����ݱ��е���������
	 * 
	 * @param table
	 */
	public void deleteRecordsInTable(String table) {

		deleteTable(context, table);
	}

	/**
	 * �������ݵ�url���ݱ�
	 */
	public void insertUrlIntoTable() {
		URLService info = new URLService();
		long timestamp = queryTimestampInControlTable(context, "url");
		ArrayList<URLInfo> list = info.getSystemBrowserUrl(context, timestamp);
		int size = 0;
		if (list != null)
			size = list.size();
		LogUtils.v(TagConstance.TAG_DATABASE, "�ϴβ�������һ��urlʱ��--" + timestamp + "--��¼���� = " + size);
		if (list != null) {
			for (URLInfo url : list) {
				String table = DBConstance.TABLE_URL;
				ContentValues cv = new ContentValues();
				cv.put("url", url.getUrl());
				cv.put("keywords", url.getKeywords());
				cv.put("timestamp", url.getTimestamp());
				insertIntoTable(context, table, cv);
			}
		}
	}

	/**
	 * ��ѯurl���ݱ�
	 * 
	 * @return
	 */

	public List<URLInfo> queryUrlInTable() {
		String table = DBConstance.TABLE_URL;
		URLInfo info = null;
		ArrayList<URLInfo> obj = new ArrayList<URLInfo>();
		Cursor c = null;
		try {
			c = queryTable(context, table);
			if (c != null) {
				while (c.moveToNext()) {
					info = new URLInfo();
					info.setUrl(c.getString(c.getColumnIndex("url")));
					info.setKeywords(c.getString(c.getColumnIndex("keywords")));
					info.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(info);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * ����һ��shutdown �������ݱ�TABLE_SHUT_TIME
	 */
	public void insertShutTimeIntoTable() {

		ShutdownInfoService bootInfo = new ShutdownInfoService();
		long timestamp = queryTimestampInControlTable(context, "shut");
		Shutdown shut = bootInfo.getShutdownTime(context, timestamp);
		String str = " ��=null";
		if (shut == null)
			str = " ==null";
		LogUtils.v(TagConstance.TAG_DATABASE, "�ϴβ�������һ���ػ�ʱ��--" + timestamp + "--�ػ�ʱ��  " + str);
		if (shut != null) {
			String table = DBConstance.TABLE_SHUT_TIME;
			ContentValues cv = new ContentValues();
			cv.put("shutdowntime", shut.getShutdowntime());
			cv.put("timestamp", shut.getTimestamp());
			insertIntoTable(context, table, cv);
		}
	}

	/**
	 * �����ݱ�TABLE_SHUT_TIME�в�ѯ���shutdown����
	 * 
	 * @return
	 */
	public List<Shutdown> queryShutTimeInTable() {

		String table = DBConstance.TABLE_SHUT_TIME;
		Shutdown shut = null;
		ArrayList<Shutdown> obj = new ArrayList<Shutdown>();
		Cursor c = null;
		try {
			c = db.query(table, null, null, null, null, null, " timestamp desc");
			if (c != null) {
				while (c.moveToNext()) {
					shut = new Shutdown();
					shut.setShutdowntime(c.getString(c
							.getColumnIndex("shutdowntime")));
					shut.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(shut);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * ����һ��boot �������ݱ�TABLE_BOOT_TIME
	 */
	public void insertBootTimeIntoTable() {

		ShutdownInfoService bootInfo = new ShutdownInfoService();
		long timestamp = queryTimestampInControlTable(context, "boot");
		Bootup boot = bootInfo.getBootUpTime(timestamp);
		String str = "";
		if (boot == null)
			str = "";
		LogUtils.v(TagConstance.TAG_DATABASE, "�ϴβ�������һ������ʱ��--" + timestamp + "--����ʱ�� " + str);
		if (boot != null) {
			String table = DBConstance.TABLE_BOOT_TIME;
			ContentValues cv = new ContentValues();
			cv.put("boottime", boot.getBoottime());
			cv.put("timestamp", boot.getTimestamp());
			insertIntoTable(context, table, cv);
		}
	}

	/**
	 * �����ݱ�TABLE_BOOT_TIME�в�ѯ���boot����
	 * 
	 * @return
	 */
	public List<Bootup> queryTableBootTime() {

		String table = DBConstance.TABLE_BOOT_TIME;
		Bootup boot = null;
		ArrayList<Bootup> obj = new ArrayList<Bootup>();
		Cursor c = null;

		try {
			c = queryTable(context, table);

			if (c != null) {
				while (c.moveToNext()) {
					boot = new Bootup();
					boot.setBoottime(c.getString(c.getColumnIndex("boottime")));
					boot.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(boot);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * �����ݱ�TABLE_SMS �в�����SMS����
	 */
	public void insertTableSMS() {

		MessageInfoService message = new MessageInfoService();
		long timestamp = queryTimestampInControlTable(context, "sms");
		ArrayList<SMS> smsList = message.getMessageInfo(context, timestamp);
		LogUtils.v(TagConstance.TAG_DATABASE, "�ϴβ�������һ������ʱ��--" + timestamp + "--�������� = " + smsList.size());
		if (smsList != null) {
			for (SMS sms : smsList) {
				String table = DBConstance.TABLE_SMS;
				ContentValues cv = new ContentValues();
				cv.put("smstime", sms.getSmstime());
				cv.put("timestamp", sms.getTimestamp());
				insertIntoTable(context, table, cv);
			}
		}
	}

	/**
	 * �����ݱ�TABLE_SMS��ѯ���SMS����
	 * 
	 * @return
	 */
	public List<SMS> queryTableSMS() {

		String table = DBConstance.TABLE_SMS;
		SMS sms = null;
		ArrayList<SMS> obj = new ArrayList<SMS>();
		Cursor c = null;
		try {
			c = queryTable(context, table);
			if (c != null) {
				while (c.moveToNext()) {
					sms = new SMS();
					sms.setSmstime(c.getString(c.getColumnIndex("smstime")));
					sms.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(sms);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * �������ݵ��绰��
	 */
	public void insertTablePhone() {
		CallLogService calllog = new CallLogService();
		long timestamp = queryTimestampInControlTable(context, "phone");
		ArrayList<PhoneActivity> phones = calllog.getCallLogInfo(context,
				timestamp);
		LogUtils.v(TagConstance.TAG_DATABASE,
				"�ϴβ�������һ���绰ʱ��--" + timestamp + "-- �绰��¼���� = " + phones.size());
		if (phones != null) {
			for (PhoneActivity phone : phones) {
				String table = DBConstance.TABLE_PHONE;
				ContentValues cv = new ContentValues();
				cv.put("teletime", phone.getStart_timestamp());
				cv.put("duration", phone.getDuration());
				cv.put("timestamp", phone.getTimestamp());
				insertIntoTable(context, table, cv);
			}
		}
	}

	/**
	 * ��ѯ�绰��¼���ݱ�
	 * 
	 * @return
	 */
	public List<PhoneActivity> queryTablePhone() {

		String table = DBConstance.TABLE_PHONE;
		PhoneActivity phone = null;
		ArrayList<PhoneActivity> obj = new ArrayList<PhoneActivity>();
		Cursor c = null;
		try {
			c = queryTable(context, table);
			if (c != null) {
				while (c.moveToNext()) {
					phone = new PhoneActivity();
					phone.setStart_timestamp(c.getString(c
							.getColumnIndex("teletime")));
					phone.setDuration(c.getInt(c.getColumnIndex("duration")));
					phone.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
					obj.add(phone);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * ����һ��GPS����GPS��
	 */
	public void insertTableGPS() {
		GPSInfoService gps = new GPSInfoService(context);
		GPS obj = gps.getLocations(context);
		if (obj != null) {
			String table = DBConstance.TABLE_GPS;
			ContentValues cv = new ContentValues();
			cv.put("latitude", obj.getLatitude());
			cv.put("longtitude", obj.getLongitude());
			cv.put("altitude", obj.getAltitude());
			cv.put("timestamp", obj.getTimestamp());
			insertIntoTable(context, table, cv);
		}
	}

	/**
	 * GPS���в�ѯ���GPS����
	 * 
	 * @return
	 */
	public List<GPS> queryTableGPS() {

		String table = DBConstance.TABLE_GPS;
		GPS gps = null;
		ArrayList<GPS> obj = new ArrayList<GPS>();
		Cursor cursor = null;
		try {
			cursor = queryTable(context, table);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					gps = new GPS();
					gps.setLatitude(cursor.getDouble(cursor
							.getColumnIndex("latitude")));
					gps.setLongitude(cursor.getDouble(cursor
							.getColumnIndex("longtitude")));
					gps.setAltitude(cursor.getDouble(cursor
							.getColumnIndex("altitude")));
					gps.setTimestamp(cursor.getLong(cursor
							.getColumnIndex("timestamp")));
					obj.add(gps);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cursor != null) {
					cursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

}
