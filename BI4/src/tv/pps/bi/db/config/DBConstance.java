package tv.pps.bi.db.config;

public class DBConstance {
	public final static String DB_NAME = "userbehavior.db";
	public final static String TABLE_IDENTIFICATION = "identification";// ����û������Ϣ
	public final static String TABLE_GPS = "gps";// ���gps��Ϣ
	public final static String TABLE_POI = "poi";// ���poi��Ϣ
	public final static String TABLE_APP = "app";// ���app��Ϣ
	public final static String TABLE_SEARCH_WORD = "search_word";// �����ҳ������
	public final static String TABLE_URL = "url";// ��ŷ��ʹ�����ַ
	public final static String TABLE_BOOT_TIME = "boot";// ���ϵͳ����ʱ��
	public final static String TABLE_SHUT_TIME = "shut";// ���ϵͳ�ػ�ʱ��
	public final static String TABLE_PHONE = "phone";// ��Ŵ�绰ʱ��
	public final static String TABLE_SMS = "sms";// ��ŷ�����ʱ��
	public final static String TABLE_VIDEO = "video";// ��ŵ��������������ݿ���Ϣ
	public final static String TABLE_PROCESS = "process";// ��Ž�����Ϣ
	public final static String TABLE_DEVICE_INFO = "deviceinfo";// ����豸��Ϣ
	public final static String TABLE_INFOMATION_CONTROL = "infomation_control";// �������ݷ��Ϳ��Ʊ�

	// ����TABLE_INFOMATION_CONTROL��
	public final static String CREATE_TABLE_INFOMATION_CONTROL = "create table if not exists "
			+ TABLE_INFOMATION_CONTROL
			+ " ( _id integer primary key autoincrement , type text UNIQUE , timestamp long);";
	// ����TABLE_IDENTIFICATION��
	public final static String CREATE_TABLE_IDENTIFICATION = "create table if not exists "
			+ TABLE_IDENTIFICATION
			+ " ( _id integer primary key autoincrement  , uid text , login text, platform text,mac text,model text UNIQUE );";
	// ����TABLE_GPS��
	public final static String CREATE_TABLE_GPS = "create table if not exists "
			+ TABLE_GPS
			+ "( _id integer primary key autoincrement ,  latitude double , longtitude double, altitude double,timestamp long);";
	//����TABLE_POI ��
	public final static String CREATE_TABLE_POI = "create table if not exists "
			+ TABLE_POI
			+ "( _id integer primary key autoincrement ,  latitude integer , longtitude integer,timestamp long);";
	//����TABLE_URL 
	public final static String CREATE_TABLE_URL = "create table if not exists "
			+ TABLE_URL
			+ "( _id integer primary key autoincrement , url text, keywords text, timestamp long  );";
	//����TABLE_BOOT_TIME 
	public final static String CREATE_TABLE_BOOT_TIME = "create table if not exists "
			+ TABLE_BOOT_TIME
			+ "( _id integer primary key autoincrement , boottime text, timestamp long);";
	//����TABLE_SHUT_TIME
	public final static String CREATE_TABLE_SHUT_TIME = "create table if not exists "
			+ TABLE_SHUT_TIME
			+ "( _id integer primary key autoincrement , shutdowntime text, timestamp  long);";
	//����TABLE_PHONE
	public final static String CREATE_TABLE_PHONE = "create table if not exists "
			+ TABLE_PHONE
			+ "( _id integer primary key autoincrement , teletime text, duration integer,timestamp long);";
	//����TABLE_SMS
	public final static String CREATE_TABLE_SMS = "create table if not exists "
			+ TABLE_SMS
			+ "(_id integer primary key autoincrement ,  smstime text,timestamp  long)";
	//����TABLE_VIDEO
	public final static String CREATE_TABLE_VIDEO = "create table if not exists "
			+ TABLE_VIDEO
			+ "(_id integer primary key autoincrement , provider text,watchtimestamp text, video_name text,timestamp)";
	//����TABLE_DEVICE_INFO
	public final static String CREATE_TABLE_DEVICE_INFO = "create table if not exists "
			+ TABLE_DEVICE_INFO
			+ "(_id integer primary key autoincrement,imei text, imsi text, manufacturer text, model text,screen_resolution text,os_version text,os_custermize text)";

}
