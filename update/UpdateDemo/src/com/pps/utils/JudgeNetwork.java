package com.pps.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * �������Ӽ����
 * @author jiangqingqing
 * @time 2013/08/06  11:34
 */
public class JudgeNetwork {
	
	/**
	 * �ж��Ƿ�����������
	 * @param pContext
	 * @return true->�У� false->��
	 */
	public boolean isNetworkConnected(Context pContext) {  
	    if (pContext != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	/**
	 * �ж�wifi�Ƿ����
	 * @param pContext
	 * @return
	 */
	public boolean isWifiConnected(Context pContext) {  
	    if (pContext != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	        if (mWiFiNetworkInfo != null) {  
	            return mWiFiNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	/**
	 * �ж�MOBILE�����Ƿ����
	 * @param pContext
	 * @return 
	 */
	public boolean isMobileConnected(Context pContext) {  
	    if (pContext != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mMobileNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	        if (mMobileNetworkInfo != null) {  
	            return mMobileNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
    /**
     * ��ȡ�������ӵ����� 
     * @param pContext
     * @return  �������ӵ���������
     */
	public static int getConnectedType(Context pContext) {  
	    if (pContext != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
	            return mNetworkInfo.getType();  
	        }  
	    }  
	    return -1;  
	}
}
