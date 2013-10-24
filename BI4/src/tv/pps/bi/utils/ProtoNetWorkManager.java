package tv.pps.bi.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import tv.pps.bi.proto.model.UserActivity;


/**
 * ������������� ʹ��post����������
 * 
 * @author jiangqingqing
 * @time 2013/09/04 10:45
 */
public class ProtoNetWorkManager {

	/**
	 * ͨ��Post���������л����ʵ�������
	 * 
	 * @param pUserActivity
	 * @return
	 */
	public static String postUserActivityByEntity(UserActivity pUserActivity) {
		return "";
	}

	/**
	 * ͨ��Post�����뷢�ͼ��ܹ�����ֽ���������
	 * @param pByte  ��Ҫ���͵��ֽ�����
	 * @param pUrl   ��������ӵ�ַ
	 * @return  ���ͳɹ�����true��ʧ�ܷ���false
	 */
	public static boolean postUserActivityByByte(byte[] pByte,String pUrl)
	{
		URL mUrl=null;
		HttpURLConnection mHttpURLConnection=null;
		try {
           mUrl=new URL(pUrl);			
           mHttpURLConnection=(HttpURLConnection)mUrl.openConnection();
           mHttpURLConnection.setRequestProperty("content-type","text/html");   
           mHttpURLConnection.setRequestMethod("POST");  
           mHttpURLConnection.setDoOutput(true);  
           mHttpURLConnection.setDoInput(true);  
           mHttpURLConnection.setUseCaches(false);
           // ���ó�ʱʱ��
           mHttpURLConnection.setConnectTimeout(20*1000);
           mHttpURLConnection.getOutputStream().write(pByte);
           mHttpURLConnection.getOutputStream().flush();
           int result_code=mHttpURLConnection.getResponseCode();
           mHttpURLConnection.disconnect();
           if(result_code==200)
           {
        	   return true;
           }
		} catch (Exception e) {
		   e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * ͨ��Post�����ͼ��ܹ�����ַ�������
	 * 
	 * @param pStr
	 * @return ��������ɹ����Ľ��
	 */
	public static String postUserActivityByMsg(String pStr, String pUrl) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(pUrl);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("protobuff", pStr));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response;
			response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
