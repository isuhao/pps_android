package com.pps.broadcast;

import java.io.File;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pps.activity.R;
import com.pps.bean.UpdateInformation;
import com.pps.service.UpdateService;

/**
 * �㲥�����ߣ������������
 * 
 * @author jiangqingqing
 * @time 2013/08/05 16:02
 */
public class UpdateBroadcastReceiver extends BroadcastReceiver {

	private static final String UPDATE_ACTION = "com.pps.receiver.update";
	private Context mContext;
	private Dialog mDialog;
	private LayoutInflater mLayoutInflater;
	private View mDialogView;
	private Button btn_update_dialog_commit; //ȷ��
	private Button btn_update_dialog_cancel; //����
 	private Button btn_update_dialog_force;  //ǿ�Ƹ�������ȷ��
 	//private TextView tv_update_dialog_info_version;
 	private TextView tv_update_dialog_info_content;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (intent.getAction().equals(UPDATE_ACTION)) {
			Log.d("update", "�յ�������¹㲥...");
			mContext=context;
			mLayoutInflater=LayoutInflater.from(mContext);
			mDialogView=mLayoutInflater.inflate(R.layout.update_dialog_tv, null);
			initView();
			// �յ������Ĺ㲥
			checkVersion(context);
		}
	}

	/**
	 * ���汾��->�Զ�����
	 * 
	 * @param pContext
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
//		AlertDialog builder = new AlertDialog.Builder(pContext).setTitle("��������").setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// new UpdateAppManager(pContext, R.string.app_name,
//				// UpdateInformation.Durl).updateApp();
//				Intent intent = new Intent(pContext, UpdateService.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.putExtra("appname", R.string.app_name);
//				intent.putExtra("appurl", UpdateInformation.Durl);
//				intent.putExtra("update_msg", 1); //ǿ������������1
//				// ������������
//				pContext.startService(intent);
//			}
//		}).setCancelable(false).create();
//		builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//		builder.show();
		//
		initDialog();
		btn_update_dialog_commit.setVisibility(View.GONE);
		btn_update_dialog_cancel.setVisibility(View.GONE);
		btn_update_dialog_force.setVisibility(View.VISIBLE);
		btn_update_dialog_force.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(pContext, UpdateService.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("appname", R.string.app_name);
				intent.putExtra("appurl", UpdateInformation.Durl);
				intent.putExtra("update_msg", 1); //ǿ������������1
				// ������������
				pContext.startService(intent);
				if(mDialog!=null&&mDialog.isShowing())
				{
					mDialog.dismiss();
				}
			}
		});
	}
	// ��������
	private void normalUpdate(final Context pContext) {
//		AlertDialog builder = new AlertDialog.Builder(pContext).setTitle("��������").setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// new UpdateAppManager(pContext, R.string.app_name,
//				// UpdateInformation.Durl).updateApp();
//				Intent intent = new Intent(pContext, UpdateService.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.putExtra("appname", R.string.app_name);
//				intent.putExtra("appurl", UpdateInformation.Durl);
//				intent.putExtra("update_msg", 2); //��������������2
//				// ������������
//				pContext.startService(intent);
//			}
//		}).setNegativeButton("ȡ��", null).create();
//		builder.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//		builder.show();
		initDialog();
		btn_update_dialog_commit.setVisibility(View.VISIBLE);
		btn_update_dialog_cancel.setVisibility(View.VISIBLE);
		btn_update_dialog_force.setVisibility(View.GONE);
		btn_update_dialog_commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(pContext, UpdateService.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("appname", R.string.app_name);
				intent.putExtra("appurl", UpdateInformation.Durl);
				intent.putExtra("update_msg", 2); //��������������2
				// ������������
				pContext.startService(intent);	
				if(mDialog!=null&&mDialog.isShowing())
				{
					mDialog.dismiss();
				}
			}
		});
		btn_update_dialog_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mDialog!=null&&mDialog.isShowing())
				{
					mDialog.dismiss();
				}
			}
		});
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
	 * ��ʼ������Ԫ��
	 */
	private void initView()
	{
		//tv_update_dialog_info_version=(TextView)mDialogView.findViewById(R.id.tv_update_dialog_info_version);
		tv_update_dialog_info_content=(TextView)mDialogView.findViewById(R.id.tv_update_dialog_info_content);
		btn_update_dialog_commit=(Button)mDialogView.findViewById(R.id.btn_update_dialog_commit);
		btn_update_dialog_cancel=(Button)mDialogView.findViewById(R.id.btn_update_dialog_cancel);
		btn_update_dialog_force=(Button)mDialogView.findViewById(R.id.btn_update_dialog_force);		
	}
	/**
	 * ��ʼ��������ʾDialog
	 */
	private void initDialog()
	{
		mDialog=new Dialog(mContext, R.style.updateDialogTheme);
		//����������Ϣ
		//�°汾(v1.5.1)������ʾ:
		//tv_update_dialog_info_version.setText("�°汾(v"+UpdateInformation.serverVersion+")������ʾ:");
		tv_update_dialog_info_content.setText(UpdateInformation.upgradeinfo);
		mDialog.setContentView(mDialogView);
		mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		mDialog.setCancelable(false);
		mDialog.show();
	}
}
