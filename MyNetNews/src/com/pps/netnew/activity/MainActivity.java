package com.pps.netnew.activity;

import tv.pps.bi.config.IntervalTimeConstance;
import tv.pps.bi.proto.biz.DeviceInfoStatistic;
import tv.pps.bi.service.ManagerService;
import tv.pps.bi.utils.LogUtils;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.pps.netnew.adapter.MainPagerAdapter;
import com.pps.netnew.fragment.LeftCategoryFragment;
import com.pps.netnew.fragment.RightPerMsgCenterFragment;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends SlidingFragmentActivity {

	private ImageButton main_left_imgbtn;
	private ImageButton main_right_imgbtn;
	private ViewPager myViewPager;
	private PagerTitleStrip pagertitle;
	private TabPageIndicator mTabPageIndicator;
	private PagerAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// ����log,Ĭ��Ϊ�رգ�true��ʾ������false��ʾ�ر�
		LogUtils.setShowLog(true);
		// ���ÿ����û���Ϊ�Ѽ�����
		IntervalTimeConstance.setStartServiceSwitch(this, true);
		// ����uuid���豸Ψһ��ʶ����ƽ̨��Ϣ
		DeviceInfoStatistic.setUuidAndPlatform(
				"UUID_0001_MyNetNews", "pps_android", this);
		// ����loginid�����û���¼ʱ����
		DeviceInfoStatistic.setLoginId("123456", this);
		// ��������Ͷ��ʱ�����ڣ��Ժ���Ϊ��λ,Ĭ��1Сʱ:1*60*60*1000
		IntervalTimeConstance.setStartDeliverServiceTime(2 * 60 * 1000);
		// �����û���Ϊ�����Ѽ�ʱ����,�Ժ���Ϊ��λ,Ĭ��30���ӣ�30*60*1000
		IntervalTimeConstance.setStartUserInfoSearchTime(60 * 1000);
		// �����û���Ϊ��������
		ManagerService.startService(this);

		IntervalTimeConstance.setStartServiceSwitch(MainActivity.this, true);

		initSlidingMenu();
		initView();
		initValidata();
		bindData();
		initListener();
	}

	/**
	 * ��ʼ��SlidingMenu��ͼ
	 */
	private void initSlidingMenu() {
		// ���û����˵�������ֵ
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);  // �������ҵĻ����˵�
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);  // ���ô�������Ļ�ı�Ե����Ļ��������
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width); //������Ӱ�Ŀ��
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);   //������Ӱ��Ч��
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);  
		getSlidingMenu().setFadeDegree(0.35f);
		// �������������ͼ -�м����ͼ
		setContentView(R.layout.main);
		// ������߲˵��򿪺����ͼ����  
		setBehindContentView(R.layout.left_content);
		getSupportFragmentManager()
		        .beginTransaction()
				.replace(R.id.left_content_id, new LeftCategoryFragment())
				.commit();  
		// �����ұ߲˵��򿪺����ͼ����
		getSlidingMenu().setSecondaryMenu(R.layout.right_content);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.right_content_id, new RightPerMsgCenterFragment())
				.commit();
	}

	private void initView() {
		main_left_imgbtn = (ImageButton) this
				.findViewById(R.id.main_left_imgbtn);
		main_right_imgbtn = (ImageButton) this
				.findViewById(R.id.main_right_imgbtn);
		myViewPager = (ViewPager) this.findViewById(R.id.myviewpager);
		//pagertitle = (PagerTitleStrip) this.findViewById(R.id.pagertitle);
		//
		mTabPageIndicator=(TabPageIndicator)this.findViewById(R.id.indicator);

	}

	/**
	 * ��ʼ������
	 */
	private void initValidata() {
		//pagertitle.setTextSize(0, 25);
		mAdapter = new MainPagerAdapter(getSupportFragmentManager());

	}

	/**
	 * ������
	 */
	private void bindData() {
		myViewPager.setAdapter(mAdapter);
		myViewPager.setCurrentItem(0);
		// 
		mTabPageIndicator.setViewPager(myViewPager);
	}

	private void initListener() {
		main_left_imgbtn.setOnClickListener(new MySetOnClickListener());
		main_right_imgbtn.setOnClickListener(new MySetOnClickListener());
		myViewPager.setOnPageChangeListener(new MySetOnPageChangeListener());
	}

	/**
	 * ViewPagerҳ��ѡ��л�������
	 */
	class MySetOnPageChangeListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
		}
	}

	/**
	 * ���в໬�������ر�
	 * 
	 * @author jiangqq
	 * 
	 */
	class MySetOnClickListener implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_left_imgbtn:
				showMenu();
				break;

			case R.id.main_right_imgbtn:
				showSecondaryMenu();
				break;
			}
		}
	}
}
