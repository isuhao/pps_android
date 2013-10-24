package tv.pps.bi.proto.model;

import java.io.Serializable;

/**
 * ��¼�����������ʵ����
 * @author jiangqingqing
 *
 */
public class NetTime implements Serializable{

	private static final long serialVersionUID = 1L;
    
	private long nettime;
    private int flag; //�Ƿ�������־ ; 1������  2,û������
	public NetTime() {
		super();
	}
	public NetTime(long nettime, int flag) {
		super();
		this.nettime = nettime;
		this.flag = flag;
	}
	public long getNettime() {
		return nettime;
	}
	public void setNettime(long nettime) {
		this.nettime = nettime;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "NetTime [nettime=" + nettime + ", flag=" + flag + "]";
	}
    
	
	
}
