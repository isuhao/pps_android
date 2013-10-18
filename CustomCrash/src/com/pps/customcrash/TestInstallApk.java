package com.pps.customcrash;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class TestInstallApk extends Activity {

	private Button btn;
	private static final String apkUrl = "/sdcard/wanzhuan.apk";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_install);
		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				installApk(apkUrl);
				showDialog(TestInstallApk.this);
			}
		});
	}

	/**
	 * ������ʾdialog
	 * @param pContext
	 */
	private void showDialog(Context pContext) {
		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_layout);

		dialog.setTitle("Custom Dialog");
		dialog.show();

		/*
		 * ��ȡʥ����Ĵ��ڶ��󼰲����������޸ĶԻ���Ĳ�������, ����ֱ�ӵ���getWindow(),��ʾ������Activity��Window
		 * ����,�����������ͬ���ķ�ʽ�ı����Activity������.
		 */
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		/*
		 * lp.x��lp.y��ʾ�����ԭʼλ�õ�ƫ��.
		 * ������ֵ����Gravity.LEFTʱ,�Ի�����������,����lp.x�ͱ�ʾ�����ߵ�ƫ��,��ֵ����.
		 * ������ֵ����Gravity.RIGHTʱ,�Ի���������ұ�,����lp.x�ͱ�ʾ����ұߵ�ƫ��,��ֵ����.
		 * ������ֵ����Gravity.TOPʱ,�Ի���������ϱ�,����lp.y�ͱ�ʾ����ϱߵ�ƫ��,��ֵ����.
		 * ������ֵ����Gravity.BOTTOMʱ,�Ի���������±�,����lp.y�ͱ�ʾ����±ߵ�ƫ��,��ֵ����.
		 * ������ֵ����Gravity.CENTER_HORIZONTALʱ
		 * ,�Ի���ˮƽ����,����lp.x�ͱ�ʾ��ˮƽ���е�λ���ƶ�lp.x����,��ֵ�����ƶ�,��ֵ�����ƶ�.
		 * ������ֵ����Gravity.CENTER_VERTICALʱ
		 * ,�Ի���ֱ����,����lp.y�ͱ�ʾ�ڴ�ֱ���е�λ���ƶ�lp.y����,��ֵ�����ƶ�,��ֵ�����ƶ�.
		 * gravity��Ĭ��ֵΪGravity.CENTER,��Gravity.CENTER_HORIZONTAL
		 * |Gravity.CENTER_VERTICAL.
		 * 
		 * ����setGravity�Ĳ���ֵΪGravity.LEFT | Gravity.TOPʱ�Ի���Ӧ�����ڳ�������Ͻ�,����
		 * ���ֻ��ϲ���ʱ���־�������ϱ߶���һС�ξ���,���Ҵ�ֱ����ѳ��������Ҳ����������, Gravity.LEFT, Gravity.TOP,
		 * Gravity.BOTTOM��Gravity.RIGHT�������,��߽���һС�ξ���
		 */
		lp.x = 500; // ��λ��X����
		lp.y = 200; // ��λ��Y����
		lp.width = 300; // ���
		lp.height = 300; // �߶�
		lp.alpha = 0.9f; // ͸����

		// ��Window��Attributes�ı�ʱϵͳ����ô˺���,����ֱ�ӵ�����Ӧ������Դ��ڲ����ĸ���,Ҳ������setAttributes
		// dialog.onWindowAttributesChanged(lp);
		dialogWindow.setAttributes(lp);
	}

	/**
	 * ���о�Ĭ��װapk
	 */
	private String installApk(String apkInstallPath) {
		String[] args = { "pm", "install", "-r", apkInstallPath };
		String result = "";
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		Process process = null;
		InputStream errIs = null;
		InputStream inIs = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = -1;
			process = processBuilder.start();
			errIs = process.getErrorStream();
			while ((read = errIs.read()) != -1) {
				baos.write(read);
			}
			baos.write('/');
			inIs = process.getInputStream();
			while ((read = (inIs.read())) != -1) {
				baos.write(read);
			}
			byte[] data = baos.toByteArray();
			result = new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (errIs != null) {
					errIs.close();
				}
				if (inIs != null) {
					inIs.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			if (process != null) {
				process.destroy();
			}
		}
		return result;
	}
}
