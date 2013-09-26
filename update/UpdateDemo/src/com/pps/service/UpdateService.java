package com.pps.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.activity.R;
import com.pps.bean.UpdateInformation;
import com.pps.common.MemoryStatus;

/**
 * ���к�̨��������Ĺ�����
 * @author jiangqingqing
 * @time 2013/08/05 15:35
 */
public class UpdateService extends Service {
	private Context mContext;
	private LayoutInflater mLayoutInflater;
    //���ؽ��ȿ�
    private View mDialogView;
    private TextView tv_update_progress_schedule;
    private ProgressBar progressbar_update_progress;
    private TextView tv_update_progress_contenxt;
    private Dialog mDialog;
    //����ʧ�� ���ѿ�
    private View mTipDialogView;
    private Button btn_update_dialog_import_commit;
    private Button btn_update_dialog_import_cancel;
    private Button btn_update_dialog_import_force;
    private Dialog mTipDialog;
    
	private final static int DOWNLOAD_COMPLETE = 1;// ���
	private final static int DOWNLOAD_NOMEMORY = -1;// �ڴ��쳣
	private final static int DOWNLOAD_FAIL = -2;// ʧ��
	private final static int DOWNLOAD_PROGRESS = 0; // ��������-��ʾ����->update
	private final static int DOWNLOAD_CANCEL = 3; // ��������

	private int appName = 0;// Ӧ������
	private String appUrl = null;// Ӧ��������ַ
	private int appUpdateMsg = 0;// ������ʶ
	private File updateDir = null;// �ļ�Ŀ¼
	private File updateFile = null;// �����ļ�
	private boolean isCancel = false; // ���ñ�־λ���ж��첽�����Ƿ�ȡ��
	private Handler updateHandler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_COMPLETE:// ���سɹ�
				// ���سɹ����رս��ȿ�
				if (null != mDialog && mDialog.isShowing()) {
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
				// ���а�װ
				Uri uri = Uri.fromFile(updateFile);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.setDataAndType(uri,
						"application/vnd.android.package-archive");
				installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(installIntent);
				// �رշ���
				stopSelf();
				break;
			case DOWNLOAD_NOMEMORY:// ���ؿռ䲻��
				Log.d("update", "����ʧ�ܣ��ڴ治����������");
				// ������������
				if (null != mDialog && mDialog.isShowing()) {
					mDialog.dismiss();
				}
				// ������ʾ��Ҫ����Ҫ��������
				initTipDialog();
				break;
			case DOWNLOAD_FAIL: // ����ʧ��
				Log.d("update", "����ʧ�ܣ����س�����������");
				// ����ʧ�ܣ����½������س���
				if (null != mDialog && mDialog.isShowing()) {
					mDialog.dismiss();
				}
				// ������ʾ��Ҫ����Ҫ��������
				initTipDialog();
				break;
			case DOWNLOAD_PROGRESS: // ���½���
				List<String> array = (ArrayList<String>) msg.obj;
				tv_update_progress_schedule.setText(array.get(0)+"%");
				progressbar_update_progress.setProgress(Integer.valueOf(array.get(0)));
				tv_update_progress_contenxt.setText("�ļ���С ("+array.get(1)+")");
				break;
//			case DOWNLOAD_CANCEL: // ����ȡ��
//				if (null != mDialog && mDialog.isShowing()) {
//					mDialog.dismiss();
//					isCancel = false;// ���°ѳ�����ʶ�ĳ�false;
//					stopSelf();
//					if (appUpdateMsg == 1) {
//						// ǿ�Ƹ��¹����У�����������ظ��£��Ǿ͹ر�����Ӧ��
//						android.os.Process.killProcess(android.os.Process
//								.myPid());
//					}
//				}				
//				break;
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		mContext = this;
		
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mLayoutInflater = LayoutInflater.from(mContext);
		// ��ȡӦ�����������ص�ַ
		appName = intent.getIntExtra("appname", 0);
		appUrl = intent.getStringExtra("appurl");
		// ��ȡ��ʾ��ǿ������������������
		appUpdateMsg = intent.getIntExtra("update_msg", 0);
		// ���и���
		updateApp();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * ��������APP
	 */
	public void updateApp() {
		
		mDialog = new Dialog(mContext, R.style.updateDialogTheme);
		mDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		mDialogView = mLayoutInflater.inflate(R.layout.update_progress_dialog_tv, null);
		tv_update_progress_schedule=(TextView)mDialogView.findViewById(R.id.tv_update_progress_schedule);
		progressbar_update_progress=(ProgressBar)mDialogView.findViewById(R.id.progressbar_update_progress);
		tv_update_progress_contenxt=(TextView)mDialogView.findViewById(R.id.tv_update_progress_contenxt);
		mDialog.setContentView(mDialogView);
		mDialog.setCancelable(true);
		mDialog.show();
		// ��������
		new UpdateThread().execute();
	}

	// �������ݵ��첽����
	class UpdateThread extends AsyncTask<Void, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			Log.d("update", "������ַ:" + appUrl);
			// �������غ���
			int downloadStatus = downloadUpdateFile(appUrl);
			// ���سɹ�
			if (downloadStatus == DOWNLOAD_COMPLETE) {
				Message message = updateHandler.obtainMessage();
				message.what = DOWNLOAD_COMPLETE;
				updateHandler.sendMessage(message);
			}
			// �ڴ�����
			if (downloadStatus == DOWNLOAD_NOMEMORY) {
				Message message = updateHandler.obtainMessage();
				message.what = DOWNLOAD_NOMEMORY;
				updateHandler.sendMessage(message);
			}
			// ��������
			if (downloadStatus == DOWNLOAD_FAIL) {
				Message message = updateHandler.obtainMessage();
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

//				// �ж��Ƿ��Ѿ���ȡ������
//				if (isCancel) {
//					// ���ͳ������ص���Ϣ��handler�н��д���
//					Message msg = updateHandler.obtainMessage();
//					msg.what = DOWNLOAD_CANCEL;
//					updateHandler.sendMessage(msg);
//					return DOWNLOAD_CANCEL; // ֱ�ӷ����ж�����
//				}

				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				// ÿ�λ������»�ȡ�õ����������ܴ�С
				// ���з�ֹ�������ж�֮��ͻȻ�����ϣ�Ҫ���ص��ļ����ݴ�С��ȡ����
				updateTotalSize = httpConnection.getContentLength();
				// ���н���֪ͨ
				if ((downloadCount == 0)
						|| (int) (totalSize * 100 / updateTotalSize) >= downloadCount) {
					downloadCount += 1;

					// ������ʵʱ����Ϣ ���ͳ�ȥ����handler�н��д���
					int position = (int) (totalSize * 100 / updateTotalSize); 
					String result = String.format("%.2f",
							(totalSize / 1024.0 / 1024.0))
							+ "M"
							+ "/"
							+ String.format("%.2f",
									(updateTotalSize / 1024.0 / 1024.0)) + "M";
					// ����һ�����϶��󣬵�һ�������ؽ��ȣ��ڶ��������صĽ�����Ϣ
					List<String> array = new ArrayList<String>(2);
					array.add(String.valueOf(position));
					array.add(result);
					Message msg = updateHandler.obtainMessage();
					msg.what = DOWNLOAD_PROGRESS;
					msg.obj = array;
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
			
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}
		// Ĭ�Ϸ��ش���
		return DOWNLOAD_FAIL;
	}

	/**
	 * �жϴ洢�ռ��Ƿ����ĺ������ռ��С���ڡ�Դ�ļ���С��1M��ʱ����true,���ҽ��д����ļ�
	 * 
	 * @param fileSize
	 *            Ҫ���ڵ�Դ�ļ��Ĵ�С
	 * @return
	 */
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
				// �����ļ�
				createFile(false);
				return true;
			}
		}
	}

	/**
	 * �����ڲ��ռ�״�������ļ�
	 * 
	 * @param sd_availabled
	 *            true����sdcard�д���, false�����ڲ��洢�д���
	 */
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
	 * ����ʧ��,�����Ƿ�������������
	 */
	private void initTipDialog()
	{
		mTipDialog=new Dialog(mContext, R.style.updateDialogTheme);
		mTipDialogView=mLayoutInflater.inflate(R.layout.update_dialog_important_tv, null);
		mTipDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		btn_update_dialog_import_commit=(Button)mTipDialogView.findViewById(R.id.btn_update_dialog_import_commit);
		btn_update_dialog_import_cancel=(Button)mTipDialogView.findViewById(R.id.btn_update_dialog_import_cancel);
		btn_update_dialog_import_force=(Button)mTipDialogView.findViewById(R.id.btn_update_dialog_import_force);	    
        mTipDialog.setContentView(mTipDialogView);
		mTipDialog.setCancelable(false);
	    mTipDialog.show();
		//�ж�����ǿ������������������.����ʾ���������������˳���ť
		if(appUpdateMsg==1)  //ǿ��
		{
			btn_update_dialog_import_cancel.setVisibility(View.GONE);
			btn_update_dialog_import_force.setVisibility(View.VISIBLE);
		}else if(appUpdateMsg==2) //����
		{
			btn_update_dialog_import_cancel.setVisibility(View.VISIBLE);
			btn_update_dialog_import_force.setVisibility(View.GONE);
		}
		
		btn_update_dialog_import_commit.setOnClickListener(new SetOnClickListener());
		btn_update_dialog_import_cancel.setOnClickListener(new SetOnClickListener());
		btn_update_dialog_import_force.setOnClickListener(new SetOnClickListener());
	}
	
	/**
	 * ����ʧ�����ѿ򣬰�ť��������������
	 * @author jiangqingqing
	 *
	 */
	private class SetOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_update_dialog_import_commit:	
				if(mTipDialog!=null&&mTipDialog.isShowing())
				{
					mTipDialog.dismiss();
				}
				updateApp();				
				break;
			case R.id.btn_update_dialog_import_cancel:
				if(mTipDialog!=null&&mTipDialog.isShowing())
				{
					mTipDialog.dismiss();
					stopSelf();
				}
				break;			
			case R.id.btn_update_dialog_import_force:
				if(mTipDialog!=null&&mTipDialog.isShowing())
				{
					mTipDialog.dismiss();
				}
				//�˳�Ӧ��
				//isCancel = true;
				stopSelf();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
				break;
		}
		}
		
	}

}
