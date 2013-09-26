package com.pps.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pps.bean.UpdateInformation;
import com.pps.update.UpdateAppManager;

/**
 * ���и���Ӧ�� 1��������̨�߳�.���м�����ð汾��Ϣ 2:�����Ҫ�汾���������е�����ʾ�� 3����̨�������µ�apk���� 4���Զ����а�װ
 * 
 * @author jiangqingqing
 * 
 */
public class MainActivity extends Activity {
	private Context mContext;
	private Button update_btn;
	
	
	//�������ؽ��ȶԻ������
    private LayoutInflater mLayoutInflater;
    private View mDialogView;
    private TextView tv_update_progress_schedule;
    private ProgressBar progressbar_update_progress;
    private TextView tv_update_progress_contenxt;
    private Dialog mDialog;
    
	//�㲥��Ϣ
	private static final String UPDATE_ACTION = "com.pps.receiver.update";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = MainActivity.this;
		update_btn = (Button) this.findViewById(R.id.update_btn);
		update_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 1:������̨�߳�.���м�����ð汾��Ϣ
				//new Thread(new MyCheckUpThread()).start();
				//checkVersion(mContext);
				
				//2:���͹㲥�����������
				Intent _Intent=new Intent(UPDATE_ACTION);
				mContext.sendBroadcast(_Intent);
				//initView();
				//initDialog();
			 }
		});

	}
	
	/**
	 *���в��ԶԻ���--��ʼ������Ԫ�� 
	 */
	private void initView()
	{
		mLayoutInflater=LayoutInflater.from(mContext);
		mDialogView=mLayoutInflater.inflate(R.layout.update_progress_dialog_tv, null);
		tv_update_progress_schedule=(TextView)mDialogView.findViewById(R.id.tv_update_progress_schedule);	
		progressbar_update_progress=(ProgressBar)mDialogView.findViewById(R.id.progressbar_update_progress);
		tv_update_progress_contenxt=(TextView)mDialogView.findViewById(R.id.tv_update_progress_contenxt);
		
	}
	
	/**
	 * ���в������ؽ��ȶԻ���--��ʼ���Ի���
	 */
	private void initDialog()
	{
		mDialog=new Dialog(mContext, R.style.updateDialogTheme);
		mDialog.setContentView(mDialogView);
		mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		mDialog.show();
	}
	
	
	/**
	 * ���汾��->�Զ�����
	 * 
	 * @param context
	 */
	public void checkVersion(Context pContext) {
		Log.d("update", "���ذ汾:" + UpdateInformation.localVersion);
		Log.d("update", "�������汾:" + UpdateInformation.serverVersion);
		Log.d("update", "��������־:" + UpdateInformation.serverFlag);
		Log.d("update", "֮ǰǿ�������汾��-:" + UpdateInformation.lastForce);
		Log.d("update", "������Ϣ:" + UpdateInformation.upgradeinfo);

		// Flag==1,��ʾ����
		if (UpdateInformation.localVersion < UpdateInformation.serverVersion
				&& (UpdateInformation.serverFlag == 1)) {
			if (UpdateInformation.localVersion < UpdateInformation.lastForce) {
				Log.d("update", "���ذ汾С��֮ǰǿ�������汾��,ǿ������");
				forceUpdate(pContext);
			} else {
				Log.d("update", "�������˱�־Ϊ1-��������");
				normalUpdate(pContext);
			}

		}
		// Flag==2,ǿ������
		else if (UpdateInformation.localVersion < UpdateInformation.serverVersion
				&& (UpdateInformation.serverFlag == 2)) {
			Log.d("update", "�������˱�־λ2-ǿ������");
			forceUpdate(pContext);

		}
		// ��������
		else {
			Log.d("update", "������������������Ŀ¼");
			cleanUpdateFile(pContext);
		}
	}

	/**
	 * ����ǿ��������ֻ��ȷ�����������İ�ť
	 * 
	 * @param pContext
	 */
	private void forceUpdate(final Context pContext) {
		AlertDialog.Builder builder = new AlertDialog.Builder(pContext);
		builder.setTitle("��������");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				UpdateAppManager update = new UpdateAppManager(pContext,
						R.string.app_name, UpdateInformation.Durl);
				update.updateApp();
			}
		}).setCancelable(false);
		builder.create();
		builder.show();

	}

	// ��������
	private void normalUpdate(final Context pContext) {
		AlertDialog.Builder builder = new AlertDialog.Builder(pContext);
		builder.setTitle("��������");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new UpdateAppManager(pContext, R.string.app_name,
						UpdateInformation.Durl).updateApp();

			}
		}).setNegativeButton("ȡ��", null);
		builder.create();
		builder.show();
	}

	// ��������Ŀ¼
	// ���֮ǰ�������ļ��������˷��û��ռ�
	private void cleanUpdateFile(Context pContext) {
		File updateDir;
		File updateFile;
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			// ��SD�����ļ�д��SD��
			updateDir = new File(Environment.getExternalStorageDirectory(),
					UpdateInformation.downloadDir);
		}
		// ��SD��ʱ����ʱ�ļ�д���ڲ��洢����
		else {
			// filesĿ¼
			updateDir = pContext.getFilesDir();
		}
		updateFile = new File(updateDir.getPath(), pContext.getResources()
				.getString(R.string.app_name) + ".apk");
		if (updateFile.exists()) {
			Log.d("update", "���������ڣ�ɾ��������");
			updateFile.delete();
		} else {
			Log.d("update", "�����������ڣ�����ɾ��������");
		}

	}

	/**
	 * ��̨��Ⲣ�����ð汾�µ��߳���
	 * 
	 * @author jiangqingqing ��
	 * @time 2013/7/11 15:44
	 */
	class MyCheckUpThread implements Runnable {
		@Override
		public void run() {
			// ......
			// 1: ���к�̨��� ���ð汾�ĸ�����Ϣ��UpdateInformation.java
			// ������һ���ļ�ⷽ����ʱ������
			// 2:��ʼ�����жϰ汾��ǿ������������������
			checkVersion(mContext);
		}
	}
}
