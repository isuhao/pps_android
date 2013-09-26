package tv.pps.bi.db;

import java.util.ArrayList;
import java.util.List;

import tv.pps.bi.db.config.DBConstance;
import tv.pps.bi.proto.biz.CallLogService;
import tv.pps.bi.proto.biz.GPSInfoService;
import tv.pps.bi.proto.biz.MessageInfoService;
import tv.pps.bi.proto.biz.ShutdownInfoService;
import tv.pps.bi.proto.biz.URLService;
import tv.pps.bi.proto.model.Bootup;
import tv.pps.bi.proto.model.GPS;
import tv.pps.bi.proto.model.PhoneActivity;
import tv.pps.bi.proto.model.SMS;
import tv.pps.bi.proto.model.Shutdown;
import tv.pps.bi.proto.model.URLInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBOperation {

	public final static String TAG = "DBOperation";
      
	SQLiteDatabase db;
	DBHelper helper;
	Context context;
	public static DBOperation operation;
	int count = 0;
	public DBOperation(Context context){
		this.context=context;
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
		Log.i(TAG, "���ݿ�" + DBConstance.DB_NAME + "�����ɹ�");
	}
	public static DBOperation getDBOperation(Context context){
		synchronized(DBOperation.class){
		
		if(operation ==null){
			operation = new DBOperation(context);
		}
		}
		return operation;
	}
	
	public void close(){
		//helper.close();
		if(db!=null)
		db.close();
		if(helper!=null)
		helper.close();
		//Log.i(TAG, "�ر����ݿ�"+(++count)+"��");
	}
	public  boolean insertIntoTable(Context context ,String table, ContentValues cv) {//�������ݱ�
		long rowid = db.insert(table, null, cv);
		if(rowid!=-1){ 
			return true;
		} else {
			return false;
		}
	}

	public  boolean deleteTable(Context context, String table) {//ɾ�����ݱ�
		int id = db.delete(table, null, null);
		if (id != -1) {
			return true;
		} else {
			return false;
		}
	}

	public  boolean updateTable(Context context, String table,
			ContentValues cv) {//�������ݱ�
		int id = db.update(table, cv, null, null);
		if (id != -1) {
			return true;
		} else {
			return false;
		}
	}

	public  Cursor queryTable(Context context, String table) {//��ѯ���ݱ�
		Cursor c = null;
		c = db.query(table, null, null, null, null, null, " timestamp desc");
		if (c != null && c.getCount() > 0) {
			return c;
		}
		return null;
	}

	public  long queryTimestamp(Context context, String table) {//��ѯ���е�ʱ����ֶ�
		Cursor c = null;
		c = db.query(table, null, null, null, null, null, " timestamp desc");
		if(c.getCount()==0){
			return 0L;
		}else{
			c.moveToNext();
			long result = c.getLong(c.getColumnIndex("timestamp"));
			if(c!=null)
				c.close();
			return result;
		}
	}

	public  long queryTimestampInControlTable(Context context, String type) {
		// ��ѯcontrol table��timestamp�ֶ�
		Cursor c = null;
		c = db.query(DBConstance.TABLE_INFOMATION_CONTROL,
				new String[] { "timestamp" }, " type = ? ",
				new String[] { type }, null, null, " timestamp asc");
		if (null != c && c.getCount() > 0) {
			c = db.query(DBConstance.TABLE_INFOMATION_CONTROL,
					new String[] { "timestamp" }, " type = ? ",
					new String[] { type }, null, null, " timestamp asc");
			if (c != null) {
				c.moveToNext();
				long result = c.getLong(c.getColumnIndex("timestamp"));
				if(c!=null)
					c.close();
				return result;
			}
		}
		return 0L;
	}

	public  boolean updateTimestampInControlTable(Context context, String type,
			long timestamp) {// ����control table ��timestamp�ֶ�
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
	
	public  void initializeTableControl(Context context) {//��ʼ�����Ʊ�
		String table = DBConstance.TABLE_INFOMATION_CONTROL;
		String[] type = { "gps", "url", "boot", "shut", "phone", "sms" };
		long timestamp = 0L;
		ContentValues cv = new ContentValues();
		for (int i = 0; i < type.length; i++) {
			cv.put("type", type[i]);
			cv.put("timestamp", timestamp);
			db.insert(table, null, cv);
		}
		Log.i(TAG, DBConstance.TABLE_INFOMATION_CONTROL + "���ݱ��ʼ���ɹ�");
	}
	
	public void updateTableControl(String table, String type, String value) {
		//���¿��Ʊ��ʱ�����Ϣ
		ContentValues cv = new ContentValues();
		cv.put(type, value);
		updateTable(context, table, cv);
	}
	public void deleteRecordsInTable(String table) {
		//ɾ�����ݱ��е���������
		deleteTable(context, table);
	}
	
	public void insertUrlIntoTable() {//����url���ݱ�
		URLService info = new URLService();
		long timestamp = queryTimestampInControlTable(context, "url");
		ArrayList<URLInfo> list = info.getSystemBrowserUrl(context, timestamp);
		int size = 0;
		if (list != null)
			size = list.size();
		Log.v(TAG, "�ϴβ�������һ��urlʱ��--" + timestamp + "--��¼���� = " + size);
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
	public List<URLInfo> queryUrlInTable() {//��ѯurl���ݱ�
		String table = DBConstance.TABLE_URL;
		URLInfo info = null;
		ArrayList<URLInfo> obj = new ArrayList<URLInfo>();
		Cursor c = queryTable(context, table);
		if (c != null) {
			while (c.moveToNext()) {
				info = new URLInfo();
				info.setUrl(c.getString(c.getColumnIndex("url")));
				info.setKeywords(c.getString(c.getColumnIndex("keywords")));
				info.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
				obj.add(info);
			}
		}
		if (c != null) {
			c.close();
		}
		return obj;
	}

	public void insertShutTimeIntoTable() {
		// ����һ��shutdown �������ݱ�TABLE_SHUT_TIME
		ShutdownInfoService bootInfo = new ShutdownInfoService();
		long timestamp = queryTimestampInControlTable(context,"shut");
		Shutdown shut = bootInfo.getShutdownTime(context, timestamp);
		String str = " ��=null";
		if (shut == null)
			str = " ==null";
		Log.v(TAG, "�ϴβ�������һ���ػ�ʱ��--" + timestamp + "--�ػ�ʱ��  " + str);
		if (shut != null) {
			String table = DBConstance.TABLE_SHUT_TIME;
			ContentValues cv = new ContentValues();
			cv.put("shutdowntime", shut.getShutdowntime());
			cv.put("timestamp", shut.getTimestamp());
			insertIntoTable(context, table, cv);
		}
	}

	public List<Shutdown> queryShutTimeInTable() {
		// �����ݱ�TABLE_SHUT_TIME�в�ѯ���shutdown����
		String table = DBConstance.TABLE_SHUT_TIME;
		Shutdown shut = null;
		ArrayList<Shutdown> obj = new ArrayList<Shutdown>();
		Cursor c = queryTable(context, table);
		if (c != null) {
			while (c.moveToNext()) {
				shut = new Shutdown();
				shut.setShutdowntime(c.getString(c.getColumnIndex("shutdowntime")));
				shut.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
				obj.add(shut);
			}
		}
		if (c != null) {
			c.close();
		}
		return obj;
	}

	public void insertBootTimeIntoTable() {
		// ����һ��boot �������ݱ�TABLE_BOOT_TIME
		ShutdownInfoService bootInfo = new ShutdownInfoService();
		long timestamp = queryTimestampInControlTable(context,"boot");
		Bootup boot = bootInfo.getBootUpTime(timestamp);
		String str = "  ��=null";
		if (boot == null)
	    	str = "  ==null";
		Log.v(TAG,"�ϴβ�������һ������ʱ��--" + timestamp + "--����ʱ�� " + str);
		if (boot != null) {
			String table = DBConstance.TABLE_BOOT_TIME;
			ContentValues cv = new ContentValues();
			cv.put("boottime", boot.getBoottime());
			cv.put("timestamp", boot.getTimestamp());
			insertIntoTable(context, table, cv);
		}
	}

	public List<Bootup> queryTableBootTime() {
		// �����ݱ�TABLE_BOOT_TIME�в�ѯ���boot����
		String table = DBConstance.TABLE_BOOT_TIME;
		Bootup boot = null;
		ArrayList<Bootup> obj = new ArrayList<Bootup>();
		Cursor c = queryTable(context, table);
		if (c != null) {
			while (c.moveToNext()) {
				boot = new Bootup();
				boot.setBoottime(c.getString(c.getColumnIndex("boottime")));
				boot.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
				obj.add(boot);
			}
		}
		if (c != null) {
			c.close();
		}
		return obj;
	}

	public void insertTableSMS() {
		// �����ݱ�TABLE_SMS �в�����SMS����
		MessageInfoService message = new MessageInfoService();
		long timestamp = queryTimestampInControlTable(context, "sms");
		ArrayList<SMS> smsList = message.getMessageInfo(context, timestamp);
		Log.v(TAG, "�ϴβ�������һ������ʱ��--" + timestamp + "--�������� = "+ smsList.size());
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

	public List<SMS> queryTableSMS() {
		// �����ݱ�TABLE_SMS��ѯ���SMS����
		String table = DBConstance.TABLE_SMS;
		SMS sms = null;
		ArrayList<SMS> obj = new ArrayList<SMS>();
		Cursor c = queryTable(context, table);
		if (c != null) {
			while (c.moveToNext()) {
				sms = new SMS();
				sms.setSmstime(c.getString(c.getColumnIndex("smstime")));
				sms.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
				obj.add(sms);
			}
		}
		if (c != null) {
			c.close();
		}
		return obj;
	}

	public void insertTablePhone() {// �������ݵ��绰��
		CallLogService calllog = new CallLogService();
		long timestamp = queryTimestampInControlTable(context,"phone");
		ArrayList<PhoneActivity> phones = calllog.getCallLogInfo(context,timestamp);
		Log.v(TAG, "�ϴβ�������һ���绰ʱ��--" + timestamp + "-- �绰��¼���� = "+ phones.size());
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

	public List<PhoneActivity> queryTablePhone() {
		// ��ѯ�绰��¼���ݱ�
		String table = DBConstance.TABLE_PHONE;
		PhoneActivity phone = null;
		ArrayList<PhoneActivity> obj = new ArrayList<PhoneActivity>();
		Cursor c = queryTable(context, table);
		if (c != null) {
			while (c.moveToNext()) {
				phone = new PhoneActivity();
				phone.setStart_timestamp(c.getString(c.getColumnIndex("teletime")));
				phone.setDuration(c.getInt(c.getColumnIndex("duration")));
				phone.setTimestamp(c.getLong(c.getColumnIndex("timestamp")));
				obj.add(phone);
			}
		}
		if (c != null) {
			c.close();
		}
		return obj;
	}

	public void insertTableGPS() {// ����һ��GPS����GPS��
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

	public List<GPS> queryTableGPS() {
		// GPS���в�ѯ���GPS����
		String table = DBConstance.TABLE_GPS;
		GPS gps = null;
		ArrayList<GPS> obj = new ArrayList<GPS>();
		Cursor cursor = queryTable(context, table);
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
		if (cursor != null) {
			cursor.close();
		}
		return obj;
	}

}
