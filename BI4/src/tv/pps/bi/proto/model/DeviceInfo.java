package tv.pps.bi.proto.model;

import java.io.Serializable;

public class DeviceInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2871230711631620507L;
	private String imei; // �ֻ�����
	private String imsi; // �ֻ���
	private String manufacturer; // ������Ӣ������
	private String model; // �ͺ�, �������ͺ�һ��
	private String screen_resolution; // �ֱ���
	private String os_version; // os�汾
	private String os_custermize; // ��ѡֵ: "root|break" root��andorid�� break��ios

	public DeviceInfo() {
		super();
	}

	public DeviceInfo(String imei, String imsi, String manufacturer,
			String model, String screen_resolution, String os_version,
			String os_custermize) {
		super();
		this.imei = imei;
		this.imsi = imsi;
		this.manufacturer = manufacturer;
		this.model = model;
		this.screen_resolution = screen_resolution;
		this.os_version = os_version;
		this.os_custermize = os_custermize;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getScreen_resolution() {
		return screen_resolution;
	}

	public void setScreen_resolution(String screen_resolution) {
		this.screen_resolution = screen_resolution;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public String getOs_custermize() {
		return os_custermize;
	}

	public void setOs_custermize(String os_custermize) {
		this.os_custermize = os_custermize;
	}

	@Override
	public String toString() {
		return "DeviceInfo [imei=" + imei + ", imsi=" + imsi
				+ ", manufacturer=" + manufacturer + ", model=" + model
				+ ", screen_resolution=" + screen_resolution + ", os_version="
				+ os_version + ", os_custermize=" + os_custermize + "]";
	}

}
