package tv.pps.bi.proto.model;

import java.io.Serializable;

/**
 * ��������Ƶ�������Ĳ��ż�¼ʵ����
 * 
 * @author jiangqingqing
 * @time 2013/09/03 14:30
 */
public class ThirdPartyVideoActivity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3238551858605459475L;
	private String provider; // ��ѡֵ: ��youku|tudou|xunlei�ȡ�
	private String timestamp; // �ۿ���Ƶʱ�������ʽ����20130601130122����YYYYmmddhhMMss��
	private String video_name; // �ۿ���Ƶ���ƣ���缯���Ƶ�

	public ThirdPartyVideoActivity() {
		super();
	}

	public ThirdPartyVideoActivity(String provider, String timestamp,
			String video_name) {
		super();
		this.provider = provider;
		this.timestamp = timestamp;
		this.video_name = video_name;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getVideo_name() {
		return video_name;
	}

	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}

	@Override
	public String toString() {
		return "ThirdPartyVideoActivity [provider=" + provider + ", timestamp="
				+ timestamp + ", video_name=" + video_name + "]";
	}

}
