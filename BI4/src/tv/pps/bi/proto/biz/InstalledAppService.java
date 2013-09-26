package tv.pps.bi.proto.biz;

import java.util.ArrayList;
import java.util.List;

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
		List<App> appinfos 		= new ArrayList<App>();
		DBAPPManager db 					= DBAPPManager.getDBManager(mContext);
		for (int i = 0; i < packageInfos.size(); i++) {
			PackageInfo temp 			= packageInfos.get(i);
			ApplicationInfo appInfo 	= temp.applicationInfo;
			App app_info			= new App();
			String appName 				= temp.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
			String packagename      	= temp.packageName;
			if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				List<AppActivity> appdatas 	= db.getData(packagename);//ͨ��appname��ȡapp��ʹ�����
				app_info.setName(appName);// Ӧ������
				app_info.setVersion(temp.versionName);// �汾����
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
}
