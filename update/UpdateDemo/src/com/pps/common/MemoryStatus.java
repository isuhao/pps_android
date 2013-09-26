package com.pps.common;

import java.io.File;
import android.os.Environment;
import android.os.StatFs;

/**
 * �����������ʱΪ�������񣬴洢�豸��״̬������Ϣ
 * @author wangli
 *
 */
public class MemoryStatus {
	private static final int ERROR = -1;

	//�ж�SD���Ƿ����
	static public boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	//��ȡ�ڲ��洢�����ÿռ��С
	//data�ļ�����
	static public long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	//��ȡ�ڲ��洢���ռ��ܴ�С
	static public long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}
	
	
	//��ȡSD�����ÿռ��С�����󷵻�-1
	static public long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return ERROR;
		}
	}
	//��ȡSD���ܿռ��С�����󷵻�-1
	static public long getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			return ERROR;
		}
	}
	//���ļ���Сת���ַ���
	static public String formatSize(long size) {
		String suffix = null;

		if (size >= 1024) {
			suffix = "KB";
			size /= 1024;
			if (size >= 1024) {
				suffix = "MB";
				size /= 1024;
				if(size >= 1024){
					suffix = "G";
					size /= 1024;
				}
			}
		}

		StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

		int commaOffset = resultBuffer.length() - 3;
		while (commaOffset > 0) {
			resultBuffer.insert(commaOffset, ',');
			commaOffset -= 3;
		}

		if (suffix != null)
			resultBuffer.append(suffix);
		return resultBuffer.toString();
	}
}
