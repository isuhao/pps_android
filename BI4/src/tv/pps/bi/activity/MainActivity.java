package tv.pps.bi.activity;
import java.util.UUID;

import tv.pps.bi.db.config.IntervalTimeConstance;
import tv.pps.bi.proto.biz.DeviceInfoStatistic;
import tv.pps.bi.utils.LogUtils;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class MainActivity extends Activity {

	Button open,close;
	Context mContext;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext =this;
		LogUtils.setShowLog(true);
        // ����UUId

		//DeviceInfoStatistic.setUid("11111111111111111111111111111111",this);
		//DeviceInfoStatistic.setPlatform("pps_android",this);
		
		DeviceInfoStatistic.setUuidAndPlatform("11111111111111111111111111111111", "pps_android", this);
		DeviceInfoStatistic.setLoginId("123456",this);
		
		// ��������Ͷ��ʱ�����ڣ��Ժ���Ϊ��λ,Ĭ��:2*60*60*1000

		IntervalTimeConstance.setStartDeliverServiceTime(2*60*1000);
		// �����û���Ϊ�����Ѽ�ʱ����,�Ժ���Ϊ��λ,Ĭ�ϣ�30*60*1000
		IntervalTimeConstance.setStartUserInfoSearchTime(60*1000);

        // �����Ƿ�����û���Ϊ�Ѽ�
		IntervalTimeConstance.setStartServiceSwitch(this, true);
		initView();
	}
	public void initView(){
		open = (Button)findViewById(R.id.button1);
		close=(Button)findViewById(R.id.button2);
		open.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {

				
				IntervalTimeConstance.setStartServiceSwitch(MainActivity.this,true);

				LogUtils.i("billsong", "�򿪷���");
			}
		});
		close.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				LogUtils.i("billsong", "�رշ���");

				IntervalTimeConstance.setStartServiceSwitch(MainActivity.this,false);

			}
			
		});
	}
}
