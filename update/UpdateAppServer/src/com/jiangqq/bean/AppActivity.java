package com.jiangqq.bean;

import java.io.Serializable;

/**
 * APP��ʹ�û������
 * 
 * @author jiangqingqing
 * @time 2013/09/03 14:36
 */
public class AppActivity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5260065781778678511L;
	private String start_timestamp; // APP��ʱ�������ʽ����20130601130122����YYYYmmddhhMMss��
	private int duration; // ʹ��ʱ������

	public AppActivity() {
		super();
	}

	public AppActivity(String start_timestamp, int duration) {
		super();
		this.start_timestamp = start_timestamp;
		this.duration = duration;
	}

	public String getStart_timestamp() {
		return start_timestamp;
	}

	public void setStart_timestamp(String start_timestamp) {
		this.start_timestamp = start_timestamp;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "AppActivity [start_timestamp=" + start_timestamp
				+ ", duration=" + duration + "]";
	}
}
