package com.pps.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.activity.R;
import com.pps.bean.UpdateInformation;
import com.pps.common.MemoryStatus;

/**
 * �������d����
 * 
 * @author jiangqingqing
 * @time 2013/7/11 15:59
 */
public class UpdateAppManager {

	private Context mContext;
	private ProgressBar mProgressBar; // ��ʾ���ڽ���Bar
	private TextView update_tv;
	
	private Dialog mDialog;

	private final static int DOWNLOAD_COMPLETE = 1;// ���
	private final static int DOWNLOAD_NOMEMORY = -1;// �ڴ��쳣
	private final static int DOWNLOAD_FAIL = -2;// ʧ��
	private final static int DOWNLOAD_PROGRESS = 0; // ��������-��ʾ����->update

	private int appName = 0;// Ӧ������
	private String appUrl = null;// Ӧ��������ַ
	private File updateDir = null;// �ļ�Ŀ¼
	private File updateFile = null;// �����ļ�

	private Handler updateHandler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			// ���سɹ�
			case DOWNLOAD_COMPLETE:
				//���سɹ����رս��ȿ�
				if(null!=mDialog&&mDialog.isShowing())
				{
				      mDialog.dismiss();
				}
				
				Log.d("update", "���سɹ���֪ͨ����ʾ���سɹ������װ");
				// ȷ����дȨ��
				String cmd = "chmod 777 " + updateFile.getPath();
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// �����װPendingIntent
				Uri uri = Uri.fromFile(updateFile);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.setDataAndType(uri,
						"application/vnd.android.package-archive");
				mContext.startActivity(installIntent);
				break;

			// ���ؿռ䲻��
			case DOWNLOAD_NOMEMORY:
				Log.d("update", "����ʧ�ܣ��ڴ治����������");
				//������������
				if(null!=mDialog&&mDialog.isShowing())
				{
				      mDialog.dismiss();
				}
				//������ʾ��Ҫ����Ҫ��������
			    showTipDown(mContext);
				break;

			// ����ʧ��
			case DOWNLOAD_FAIL:
				Log.d("update", "����ʧ�ܣ����س�����������");
				// ����ʧ�ܣ����½������س���
				if(null!=mDialog&&mDialog.isShowing())
				{
				      mDialog.dismiss();
				}
				//������ʾ��Ҫ����Ҫ��������
			    showTipDown(mContext);
				break;
			case DOWNLOAD_PROGRESS: // ���½���
				//���سɹ���
				List<String> array=(ArrayList<String>)msg.obj;
				mProgressBar.setProgress(Integer.parseInt(array.get(0)));
				update_tv.setText(array.get(1));
				break;
			}
		}

	};

	/**
	 * ��ʼ�� ���캯��
	 * 
	 * @param pContext
	 */
	public UpdateAppManager(Context pContext,int pAppName,String pAppUrl) {
		this.mContext = pContext;
		this.appName=pAppName;
		this.appUrl=pAppUrl;
	}

	/**
	 * ��������APP
	 */
	public void updateApp() {
		// �������ؽ��ȿ�
		
		mDialog=new Dialog(mContext);
		mDialog.setTitle("��������...");		
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.update_progress_tv, null);
		mProgressBar = (ProgressBar) view.findViewById(R.id.update_progress);
		update_tv = (TextView) view.findViewById(R.id.update_tv);
		mDialog.setContentView(view);
		mDialog.setCancelable(false);
		mDialog.show();
		// ��������
		new UpdateThread().execute();
	}

	// �������ݵ��߳�
	class UpdateThread extends AsyncTask<Void, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			Log.d("update", "������ַ:" + appUrl);
			// �������غ���
			int downloadStatus = downloadUpdateFile(appUrl);
			// ���سɹ�
			if (downloadStatus == DOWNLOAD_COMPLETE) {
				
				Message message=updateHandler.obtainMessage();
				message.what = DOWNLOAD_COMPLETE;
				updateHandler.sendMessage(message);
			}
			// �ڴ�����
			if (downloadStatus == DOWNLOAD_NOMEMORY) {
				Message message=updateHandler.obtainMessage();
				message.what = DOWNLOAD_NOMEMORY;
				updateHandler.sendMessage(message);
			}
			// ��������
			if (downloadStatus == DOWNLOAD_FAIL) {
				Message message=updateHandler.obtainMessage();
				message.what = DOWNLOAD_FAIL;
				updateHandler.sendMessage(message);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (true) {
				Log.d("update", "���������ļ�����ִ����");
			}
		}

		// �������дһ����̨���ȱ仯�ĺ���

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

	}

	// �����ļ�
	private int downloadUpdateFile(String downloadUrl) {
			int downloadCount = 0;// ���ش�С
			int currentSize = 0;// ��ǰ��С
			long totalSize = 0;// �ܴ�С->��ǰ�Ѿ����صĴ�С
			long updateTotalSize = 0;// ��������С

			HttpURLConnection httpConnection = null;
			InputStream is = null;
			FileOutputStream fos = null;

			try {
				URL url = new URL(downloadUrl);
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setRequestProperty("User-Agent", "PPStvHttpClient");
				if (currentSize > 0) {
					httpConnection.setRequestProperty("RANGE", "bytes="
							+ currentSize + "-");
				}
				httpConnection.setConnectTimeout(10000);
				httpConnection.setReadTimeout(20000);
				updateTotalSize = httpConnection.getContentLength();// �õ����������ܴ�С
				// �ڴ��쳣
				if (!MemoryAvailable(updateTotalSize)) {
					if (httpConnection != null) {
						httpConnection.disconnect();
					}
					return DOWNLOAD_NOMEMORY;
				}
				// �����쳣
				if (httpConnection.getResponseCode() == 404) {
					if (httpConnection != null) {
						httpConnection.disconnect();
					}
					return DOWNLOAD_FAIL;
				}
				// ��ȡ����
				is = httpConnection.getInputStream();
				fos = new FileOutputStream(updateFile, false);
				byte buffer[] = new byte[4096];
				int readsize = 0;
				// ѭ����ȡ����
				while ((readsize = is.read(buffer)) > 0) {
					fos.write(buffer, 0, readsize);
					totalSize += readsize;
					// Ϊ�˷�ֹƵ����֪ͨ����Ӧ�óԽ����ٷֱ�����5��֪ͨһ��
					if ((downloadCount == 0)
							|| (int) (totalSize * 100 / updateTotalSize) >= downloadCount) {
						downloadCount += 5;
						
						//������ʵʱ����Ϣ ���ͳ�ȥ����handler�н��д���
						int position=(int) (totalSize * 100 / updateTotalSize);
						String result=String.format("%.2f",(totalSize / 1024.0 / 1024.0))+ "M"+ "/"+ String.format("%.2f",(updateTotalSize / 1024.0 / 1024.0))+ "M";
						List<String> array=new ArrayList<String>();
						array.add(String.valueOf(position));
						array.add(result);
						Message msg=updateHandler.obtainMessage();
						msg.what=DOWNLOAD_PROGRESS;
						msg.obj=array;
						updateHandler.sendMessage(msg);								  
					}
				}
				// ����ܴ�С���ڻ��ߵ����������Ĵ�С
				if (totalSize >= updateTotalSize) {
					Log.d("update", "��ȡ����˵��ܴ�С:" + totalSize);
					Log.d("update", "�����ļ��Ĵ�С:" + updateTotalSize);
					return DOWNLOAD_COMPLETE;
				} else {
					return DOWNLOAD_FAIL;
				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (httpConnection != null) {
					httpConnection.disconnect();
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// Ĭ�Ϸ��ش���
			return DOWNLOAD_FAIL;
		}

	// �жϴ洢�ռ��Ƿ����ĺ������ռ��С���ڡ�Դ�ļ���С��1M��ʱ����true
	private boolean MemoryAvailable(long fileSize) {
		Log.d("update", "�ļ���С:" + fileSize);
		// ���ڰ�װ��1M�ռ�,��ԭ��������������1M
		fileSize += (1024 << 10);
		// SDcard�Ѿ�װ��
		if (MemoryStatus.externalMemoryAvailable()) {
			// �ⲿ�洢�ռ䲻��
			if ((MemoryStatus.getAvailableExternalMemorySize() <= fileSize)) {
				// ���ڲ��ռ����
				if ((MemoryStatus.getAvailableInternalMemorySize() > fileSize)) {
					// �����ļ�
					createFile(false);
					return true;
				} else {
					return false;
				}
			}
			// �ⲿ�ռ����
			else {
				// �����ļ�
				createFile(true);
				return true;
			}
		}
		// SDcardδװ��
		else {
			// �ڲ��洢�ռ䲻��
			if (MemoryStatus.getAvailableInternalMemorySize() <= fileSize) {
				return false;
			} else {
				// ����Ŀ¼
				createFile(false);
				return true;
			}
		}
	}

	// �����ڲ��ռ�״�������ļ�·��
	private void createFile(boolean sd_available) {
		if (sd_available) {
			// �����ļ�
			// ����Ŀ¼���ļ�
			// sdcard/PPStv_updateĿ¼
			updateDir = new File(Environment.getExternalStorageDirectory(),
					UpdateInformation.downloadDir);
		} else {
			// �����ļ�
			// ����Ŀ¼���ļ�
			// ��SD��ʱ����ʱ�ļ�д���ڲ��洢����
			// data/data/filesĿ¼
			updateDir = mContext.getFilesDir();

		}
		// �ļ���
		updateFile = new File(updateDir.getPath(), mContext.getResources()
				.getString(appName) + ".apk");
		// �������Ŀ¼������
		if (!updateDir.exists()) {
			updateDir.mkdirs();
		}
		// ��������ļ������ڣ�������
		if (!updateFile.exists()) {
			try {
				updateFile.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			// ����֮ǰ�������ļ���ɾ����
			updateFile.delete();
			try {
				updateFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    /**
     * ������ʾ�Ƿ�Ҫ������������
     * @param pContext
     */
	private void showTipDown(Context pContext)
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(pContext);
		builder.setTitle("��Ҫ��������������?");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
                  		updateApp();//��������APK������		
			}
		}).setNegativeButton("ȡ��", null);
		builder.create().show();
	}
}
