package tv.pps.bi.proto.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tv.pps.bi.db.DBAPPManager;
import tv.pps.bi.proto.model.App;
import tv.pps.bi.proto.model.AppActivity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * ��ȡAPP��Ϣ  
 * @author jiangqingqing
 * 
 */
public class InstalledAppService {
	private Context mContext;
  public InstalledAppService(Context pContext) {
	  this.mContext=pContext;
	
  }
  
  /**
	 * ��ѯ��װapp�Ļ�����Ϣ Appinstalled_app = 8; ��װ��Щapp �Լ�app�Ļ�����Ϣ��ʹ�����
	 * @return  APP��Ϣ����ļ���
	 */
  public List<App> getUserInstalled_app()
  {
	  	
	    PackageManager packM= mContext.getPackageManager();
		List<PackageInfo> packageInfos 	=  packM.getInstalledPackages(0);
		List<App> appinfos 				= new ArrayList<App>();
		DBAPPManager db 					= DBAPPManager.getDBManager(mContext);
//		db.createView();//������ͼ
		int count = db.DBCount();
		if(count<=300&&count>=0){
		}else{
			db.deleteDBData(count);
		}
		for (int i = 0; i < packageInfos.size(); i++) {
			PackageInfo temp 			= packageInfos.get(i);
			ApplicationInfo appInfo 	= temp.applicationInfo;
			App app_info			= new App();
			String appName 				= temp.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
			String packagename      	= temp.packageName;
			if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				List<AppActivity> appdatas 	= db.getData(packagename);//ͨ��appname��ȡapp��ʹ�����
				app_info.setName(replaceBlank(appName));
				app_info.setVersion(temp.versionName!=null?temp.versionName:"1.0");// �汾����
				app_info.setActivity(appdatas);// app��ʹ�����list����
				appinfos.add(app_info);
			}
		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  return appinfos;
  }
  
  /**
   * ȥ���ַ����еĿո񡢻س������з����Ʊ��
   * @param str
   * @return
   */
	private static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}
