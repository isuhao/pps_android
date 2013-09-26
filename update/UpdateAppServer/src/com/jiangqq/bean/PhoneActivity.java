package com.jiangqq.bean;

import java.io.Serializable;

/**
 * ͨ����¼ ��绰��ʱ�����绰��ʱ��
 * @author jiangqingqing
 * @time 2013/09/03 16:43
 */
public class PhoneActivity implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1511698170778903832L;
	private String start_timestamp ;  //��ͨ�绰ʱ�������ʽ����20130601130122����YYYYmmddhhMMss��
	private int duration;  // ʹ��ʱ������
	/**
	 * 
	 */
	public PhoneActivity() {
		super();
	}
	/**
	 * @param start_timestamp
	 * @param duration
	 */
	public PhoneActivity(String start_timestamp, int duration) {
		super();
		this.start_timestamp = start_timestamp;
		this.duration = duration;
	}
	/**
	 * @return the start_timestamp
	 */
	public String getStart_timestamp() {
		return start_timestamp;
	}
	/**
	 * @param start_timestamp the start_timestamp to set
	 */
	public void setStart_timestamp(String start_timestamp) {
		this.start_timestamp = start_timestamp;
	}
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PhoneActivity [start_timestamp=" + start_timestamp
				+ ", duration=" + duration + "]";
	}
    
	
	
	
}
