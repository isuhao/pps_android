package tv.pps.bi.proto.model;

import java.io.Serializable;
import java.util.List;

/**
 * ��װ��APP��Ϣ�Լ�ʹ�������mobile only 
 * @author jiangqingqing
 * @tiem 2013/09/03 14:37
 */
public class App implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = -4887853940868499058L;
		private  String name ;  //APP����
	     private List<AppActivity> activity ;  // APPʹ�����
	     private String version;    //����İ汾��
		public App() {
			super();
		}
		public App(String name, List<AppActivity> activity, String version) {
			super();
			this.name = name;
			this.activity = activity;
			this.version = version;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<AppActivity> getActivity() {
			return activity;
		}
		public void setActivity(List<AppActivity> activity) {
			this.activity = activity;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		@Override
		public String toString() {
			return "App [name=" + name + ", activity=" + activity
					+ ", version=" + version + "]";
		}
	       
}
