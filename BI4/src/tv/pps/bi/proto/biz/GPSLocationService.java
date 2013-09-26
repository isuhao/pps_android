package tv.pps.bi.proto.biz;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class GPSLocationService {

	public static  String getLocation(Context context)  
    {  
        // ��ȡλ�ù������   
        LocationManager locationManager;  
        String serviceName = Context.LOCATION_SERVICE;  
        locationManager = (LocationManager) context.getSystemService(serviceName);  
        // ���ҵ�������Ϣ   
        Criteria criteria = new Criteria();  
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // �߾���   
        criteria.setAltitudeRequired(false);  
        criteria.setBearingRequired(false);  
        criteria.setCostAllowed(true);  
        criteria.setPowerRequirement(Criteria.POWER_LOW); // �͹���   

            String provider = locationManager.getBestProvider(criteria, true); // ��ȡGPS��Ϣ   
            Location location = locationManager.getLastKnownLocation(provider); // ͨ��GPS��ȡλ��  
            
            if(location !=null){
            double lat = location.getLatitude();
            double longti = location.getLongitude();
            double ati = location.getAltitude();
            return String.valueOf("latitude = "+lat+"--longitude = "+longti+"--altitude = "+ati);
            }
        return "��ȡ����GPS��Ϣ";
}
}
