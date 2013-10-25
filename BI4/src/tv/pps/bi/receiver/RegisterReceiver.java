package tv.pps.bi.receiver;

import tv.pps.bi.db.config.TagConstance;
import tv.pps.bi.service.ListenService;
import tv.pps.bi.service.ManagerService;
import tv.pps.bi.utils.LogUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * �����㲥����
 * @author jiangqingqing
 *
 */
public class RegisterReceiver extends BroadcastReceiver {

	private static final String SCREEN_OFF = "android.intent.action.SCREEN_OFF";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		LogUtils.i(TagConstance.TAG_SERVICE, "�������㲥 = " + action);
		if (action.equals(SCREEN_OFF)) {

//			mListenService = new ListenService();
//			/** ����״̬��һ���Ӻ�Ͷ��һ������ */
//			mListenService.handler.sendEmptyMessageDelayed(
//					ListenService.DELIVERY,
//					IntervalTimeConstance.START_DELIVER_SERVICE_TIME_FIRST);
//			LogUtils.i(
//					TagConstance.TAG_SERVICE,
//					"������"
//							+ (IntervalTimeConstance.START_DELIVER_SERVICE_TIME_FIRST / 1000)
//							+ "s��������");
			
			ManagerService.startService(context);
		}
	}

}
