package com.jiangqq.bean;

public class UpdateInformation {
	public int localVersion = 1;// ���ذ汾
	public int serverVersion = 1;// �������汾
	public int serverFlag = 0;// ��������־
	public int lastForce = 0;// ֮ǰǿ�������汾
	public String Durl = "";// ��������ȡ��ַ
	public String MD5 = "";// ��������ȡ��ַMD5ֵ
	public String upgradeinfo = "";// ������Ϣ
	public String downloadDir = ".pps/PPStv_update";// ����Ŀ¼

	public UpdateInformation() {
		super();
	}

	public UpdateInformation(int localVersion, int serverFlag, int lastForce,
			String durl, String mD5, String upgradeinfo, String downloadDir) {
		super();
		this.localVersion = localVersion;
		this.serverFlag = serverFlag;
		this.lastForce = lastForce;
		Durl = durl;
		MD5 = mD5;
		this.upgradeinfo = upgradeinfo;
		this.downloadDir = downloadDir;
	}

	public int getLocalVersion() {
		return localVersion;
	}

	public void setLocalVersion(int localVersion) {
		this.localVersion = localVersion;
	}

	public  int getServerVersion() {
		return serverVersion;
	}

	public  void setServerVersion(int serverVersion) {
		this.serverVersion = serverVersion;
	}

	public int getServerFlag() {
		return serverFlag;
	}

	public void setServerFlag(int serverFlag) {
		this.serverFlag = serverFlag;
	}

	public int getLastForce() {
		return lastForce;
	}

	public void setLastForce(int lastForce) {
		this.lastForce = lastForce;
	}

	public String getDurl() {
		return Durl;
	}

	public void setDurl(String durl) {
		Durl = durl;
	}

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}

	public String getUpgradeinfo() {
		return upgradeinfo;
	}

	public void setUpgradeinfo(String upgradeinfo) {
		this.upgradeinfo = upgradeinfo;
	}

	public String getDownloadDir() {
		return downloadDir;
	}

	public void setDownloadDir(String downloadDir) {
		this.downloadDir = downloadDir;
	}

	@Override
	public String toString() {
		return "UpdateInformation [localVersion=" + localVersion
				+ ", serverFlag=" + serverFlag + ", lastForce=" + lastForce
				+ ", Durl=" + Durl + ", MD5=" + MD5 + ", upgradeinfo="
				+ upgradeinfo + ", downloadDir=" + downloadDir + "]";
	}

}
