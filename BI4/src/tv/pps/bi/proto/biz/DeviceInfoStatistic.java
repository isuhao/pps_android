package tv.pps.bi.proto.biz;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class DeviceInfoStatistic {// ok

	private Context mContext;

	public DeviceInfoStatistic(Context pContext) {
		this.mContext = pContext;
	}

	public String getDeviceInfo() {

		String str = "model:" + getModel() + "\nplatform:" + getPlatform()
				+ "\nIMEI:" + getIMEI() + "\nIMSI:" + getIMSI()
				+ "\nmanufactorer:" + getManufacturer()
				+ "\nScreen_resolution:" + getScreen_resolution()
				+ "\nOS_VERSION:" + getOS_version() + "\nOS_CUSTOMIZE:"
				+ getOS_custermize() + "\nMAC:" + getMacAddress();
		return str;

	}

	public String getModel() {// ��ȡmodel
		return android.os.Build.MODEL;
	}

	public String getPlatform() {// ��ȡplatform
		return "pps_android";
	}

	public String getIMEI() {
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String str = tm.getDeviceId();
		if (str == null || str.equals(""))
			return "no IMEI";
		return str;
	}

	public String getIMSI() {
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String str = tm.getSubscriberId();
		if (str == null || str.equals(""))
			return "no IMSI";
		return str;
	}

	public String getManufacturer() {
		return android.os.Build.MANUFACTURER;

	}

	public String getScreen_resolution() {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		return dm.widthPixels + " * " + dm.heightPixels;
	}

	@SuppressWarnings("deprecation")
	public String getOS_version() {
		return android.os.Build.VERSION.SDK;
	}

	public String getOS_custermize() {

		return "root";
	}

	public String getMacAddress() {// ��ȡmac ��ַ
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec(
					"cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// ȥ�ո�
					break;
				}
			}
		} catch (IOException ex) {
			macSerial = "null";
			ex.printStackTrace();
		}
		return macSerial;
	}
}
