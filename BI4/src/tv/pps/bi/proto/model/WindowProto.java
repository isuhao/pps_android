package tv.pps.bi.proto.model;

import java.io.Serializable;
import java.util.List;


/**
 * ����ڵı�������ʵ����
 * @author jiangqingqing
 * @time 2013/09/03 14:59
 */
public class WindowProto implements Serializable{

	private static final long serialVersionUID = -6439374256059259314L;
	private String name;  //���������
    private List<WindowActivity> activity;  // ��������
    
    
	/**
	 * 
	 */
	public WindowProto() {
		super();
	}

	/**
	 * @param name
	 * @param activity
	 */
	public WindowProto(String name, List<WindowActivity> activity) {
		super();
		this.name = name;
		this.activity = activity;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the activity
	 */
	public List<WindowActivity> getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(List<WindowActivity> activity) {
		this.activity = activity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Window [name=" + name + ", activity=" + activity + "]";
	}
	
	
}
