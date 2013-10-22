package com.pps.customsuspend;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

public class FootView extends ImageView {

	private float mTouchX; //�������x����
	private float mTouchY; //�������y����
	private float x;       //x����
	private float y;       //y����
	private int   startX;     
	private int   startY;
	
	boolean isShow = false;  
	private OnClickListener mOnClickListener;
	private int controlledSpace = 20;   
	
	private int imgId=R.drawable.ic_launcher;
	private int screenWidth;  // WindowManager��ͼĬ�Ͽ��
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams windowManagerParams=new WindowManager.LayoutParams();
	public FootView(Context context) {
		super(context);
		initView(context);
	}

	public FootView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	/**
	 * ��ʼ������
	 * @param pContext
	 */
	public void initView(Context pContext)
	{
		mWindowManager=(WindowManager)pContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
	 	this.setImageResource(imgId);
		screenWidth=mWindowManager.getDefaultDisplay().getWidth();
	 	windowManagerParams.type=LayoutParams.TYPE_PHONE;
	 	windowManagerParams.format=PixelFormat.RGBA_8888; // ����ɫ͸��
	 	windowManagerParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL|LayoutParams.FLAG_NOT_FOCUSABLE;
	 	//����������Ļ�����Ϸ�
	 	windowManagerParams.gravity=Gravity.LEFT|Gravity.TOP;
	 	//����Ļ���Ϸ�Ϊ���
	 	windowManagerParams.x=0;
	 	windowManagerParams.y=20;
	 	windowManagerParams.width=LayoutParams.WRAP_CONTENT;
	 	windowManagerParams.height=LayoutParams.WRAP_CONTENT;
	 	
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		x=event.getRawX();
		y=event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTouchX=event.getX();
			mTouchY=event.getY();
			startX=(int)event.getRawX();
			startY=(int)event.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			updataView();
			break;
			
		case MotionEvent.ACTION_UP:
		    if(Math.abs(x-startX)>controlledSpace&&Math.abs(y-startY)>controlledSpace)
		    {
		    	if(mOnClickListener!=null)
		    	{
		    		mOnClickListener.onClick(this);
		    	}
		    }
		    Log.i("jiangqq", "x="+x+" startX+"+startX+" y="+y+" startY="+startY);  
		    if(x<screenWidth/2)
		    {
		    	x=0;
		    }else if (x>screenWidth/2) {
				x=screenWidth;
			}
		    updataView();
		    
		    break;
		}
		
		return super.onTouchEvent(event);
	}
	
	/**
	 * ����ͼƬ��Դ
	 * @param resId
	 */
	public void setImageId(int resId)
	{
		this.imgId=resId;
	}
	/**
	 * ��Ӽ�����
	 */
	public void setOnClickListener(OnClickListener pOnClickListener)
	{
		this.mOnClickListener=pOnClickListener;
	}
	/**
	 * ���ظô���
	 */
	public void hide()
	{
		if(isShow)
		{
			mWindowManager.removeView(this);
			isShow=false;
		}
	}
	/**
	 * ��ʾ�ô���
	 */
	public void show()
	{
		if(!isShow)
		{
			mWindowManager.addView(this, windowManagerParams);
			isShow=true;
		}
	}
	
	/**
	 * ������ͼλ��
	 */
	private void updataView()
	{
		windowManagerParams.x=(int)(x-mTouchX);
		windowManagerParams.y=(int)(y-mTouchY);
		mWindowManager.updateViewLayout(this, windowManagerParams);
	}
}
