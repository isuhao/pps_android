package com.pps.biz;

import android.os.AsyncTask;

/**
 * ���л�ȡ����İ汾��Ϣ->������Ӧ������
 * @author jiangqingqing
 * @time 2013/08/06 11:41
 */
public class JudgeData {
    
	public JudgeData()
	{
		
		new JudgeInformation().execute();
		
	}
	
	
	
	/**
	 * ��̨�첽�����࣬ȥ�������л�ȡ����İ汾��Ϣ��������Ϣ
	 * @author jiangqingqing
	 *
	 */
	private class JudgeInformation extends AsyncTask<Void, Integer, Integer>
	{

		@Override
		protected Integer doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer result) {
			
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			
			super.onProgressUpdate(values);
		}
		
	}
}
