package tv.pps.bi.proto.biz;



/**
 * 
 * @author jiangqingqing
 * @deprecated
 */


public class PoiInfoService {
//	public BMapManager mapManager;// ��������������
//	public LocationListener  mLocationListener;
//	public PoiInfoStatistic(Context context){
//		Log.i("billsong","��ʼ��map");
//		mapManager = new BMapManager(context);
//		// init�����ĵ�һ�����������������APIKey
//		mapManager.init("285B415EBAB2A92293E85502150ADA7F03C777C4", null);
//		  mLocationListener = new LocationListener(){
//			public void onLocationChanged(Location arg0) {
//			
//				if (arg0 != null) {
//					GeoPoint geoPoint = new GeoPoint((int) (arg0.getLatitude() * 1e6),
//							(int) (arg0.getLongitude() * 1e6));
//					MKSearch mMKSearch = new MKSearch();
//					mMKSearch.init(mapManager, new MySearchListener());
//					// �������ݴ�ѧУ�ſڸ���500�׷�Χ���Զ�ȡ���
//					mMKSearch.poiSearchNearBy("ATM", geoPoint, 500);
//				}
//			}
//		};
//	
//	}
//	public void getPOI(){
//		if (mapManager != null) {
//			// �����ٶȵ�ͼAPI
//			// ע�ᶨλ�¼�����λ�󽫵�ͼ�ƶ�����λ��
//			mapManager.getLocationManager().requestLocationUpdates(mLocationListener);
//			mapManager.start();
//		}
//
//		
//		Log.i("billsong","����map");
//	}
//
//	/**
//	 * * ʵ��MKSearchListener�ӿ�,����ʵ���첽�������� * @author liufeng
//	 */
//	public class MySearchListener implements MKSearchListener {
//		/** * ���ݾ�γ��������ַ��Ϣ��� * * @param result ������� * @param iError ����� ��0��ʾ��ȷ���أ� */
//		@Override
//		public void onGetAddrResult(MKAddrInfo result, int iError) {
//			Log.i("billsong", "onGetAddrResult");
//		}
//
//		/** * �ݳ�·��������� * * @param result ������� * @param iError ����� */
//		@Override
//		public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {
//			Log.i("billsong", "onGetDrivingRouteResult");
//		}
//
//		/**
//		 * * POI�����������Χ����������POI�������ܱ߼����� * * @param result ������� * @param type
//		 * ���ؽ�����ͣ�11,12,21:poi�б� 7:�����б� * @param iError ����ţ�0��ʾ��ȷ���أ�
//		 */
//		@Override
//		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
//			Log.i("billsong", "onGetPoiResult");
//			if (result == null) {
//				return;
//			}
//            StringBuffer sb = new StringBuffer();  
//            // ��γ������Ӧ��λ��   
//
//
//            // �жϸõ�ַ�����Ƿ���POI��Point of Interest,����Ȥ�㣩   
//            if (null != result.getAllPoi()) {  
//                // �������е���Ȥ����Ϣ   
//            	int count = 0;
//                for (MKPoiInfo poiInfo : result.getAllPoi()) {  
//                	count++;
//                    sb.append(count+"##").append("--");  
//                    sb.append("���ƣ�").append(poiInfo.name).append("--");  
//                    sb.append("��ַ��").append(poiInfo.address).append("--");  
//                    sb.append("���ȣ�").append(poiInfo.pt.getLongitudeE6() / 1000000.0f).append("--");  
//                    sb.append("γ�ȣ�").append(poiInfo.pt.getLatitudeE6() / 1000000.0f).append("\n");  
//                }
//                Log.i("billsong", sb.toString());
//            } 
//       
//		}
//
//		/** * ��������·��������� * * @param result ������� * @param iError ����ţ�0��ʾ��ȷ���أ� */
//		@Override
//		public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {
//		}
//
//		/** * ����·��������� * * @param result ������� * @param iError ����ţ�0��ʾ��ȷ���أ� */
//		@Override
//		public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError) {
//		}
//	}
}