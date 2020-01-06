package com.example.campus_tour;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker markerMe;
    int duration = Toast.LENGTH_SHORT;
    private static final String TAG = "MapsActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    /* 記錄軌跡 */
    private ArrayList<LatLng> traceOfMe;

    /* GPS */
    private LocationManager locationMgr;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //initMap();
        if (initLocationProvider()){
            whereAmI();
        }else{
            //Toast.makeText(this, "開啟GPS以獲得更精確的定位", duration).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("GPS定位已關閉")
                    .setMessage("建議開啟GPS以獲得更精確的定位。即將前往設定頁面，是否繼續?")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));// 開啟設定頁面
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MapsActivity.this.finish();
                        }
                    });
            builder.create().show();
        }// 使用者沒開啟GPS定位，建議他們開啟
    }

    @Override
    protected void onStop(){
        //locationMgr.removeUpdates(locationListener);
        super.onStop();
    }

    private boolean initLocationProvider(){
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //1.選擇最佳提供器
        /*建立選擇標準*/
//         Criteria criteria = new Criteria();
//         criteria.setAccuracy(Criteria.ACCURACY_FINE);
//         criteria.setAltitudeRequired(false);
//         criteria.setBearingRequired(false);
//         criteria.setCostAllowed(true);
//         criteria.setPowerRequirement(Criteria.POWER_LOW);
//
//         provider = locationMgr.getBestProvider(criteria, true);
//
//         if (provider != null){
//          return true;
//         }

        //2.選擇使用GPS提供器
        if (locationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
            return true;
        }

        //3.選擇使用網路提供器
//         if (locationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//          provider = LocationManager.NETWORK_PROVIDER;
//          return true;
//         }
        return false;
    }// 選擇提供器

    private void whereAmI(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)){
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
            else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }// 尚未取得使用者授權
        else {
            // 取得上次已知的位置
            Location location = locationMgr.getLastKnownLocation(provider);
            updateWithNewLocation(location);

            // GPS Listener
            locationMgr.addGpsStatusListener(gpsListener);

            // Location Listener
            int minTime = 5000;//ms
            int minDist = 5;//meter
            locationMgr.requestLocationUpdates(provider, minTime, minDist, locationListener);
        }// 已取得使用者授權
    }/*
     * 執行"我"在哪裡
     * 1.建立位置改變偵聽器
     * 2.預先顯示上次的已知位置*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(MapsActivity.this, "已取得GPS定位權限", duration).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MapsActivity.this, "無法取得GPS定位權限", duration).show();
                }
                //return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    GpsStatus.Listener gpsListener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            switch (event){
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.d(TAG, "GPS_EVENT_STARTED");
                    Toast.makeText(MapsActivity.this, "GPS_EVENT_STARTED", Toast.LENGTH_SHORT).show();
                    break;
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.d(TAG, "GPS_EVENT_STOPPED");
                    Toast.makeText(MapsActivity.this, "GPS_EVENT_STOPPED", Toast.LENGTH_SHORT).show();
                    break;
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    Log.d(TAG, "GPS_EVENT_FIRST_FIX");
                    Toast.makeText(MapsActivity.this, "GPS_EVENT_FIRST_FIX", Toast.LENGTH_SHORT).show();
                    break;
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    Log.d(TAG, "GPS_EVENT_SATELLITE_STATUS");
                    break;
            }
        }
    };// GPS 偵聽器

    LocationListener locationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location){
            updateWithNewLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider){
            updateWithNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider){

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status){
                case LocationProvider.OUT_OF_SERVICE:
                    Log.v(TAG, "Status Changed: Out of Service");
                    Toast.makeText(MapsActivity.this, "Status Changed: Out of Service", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.v(TAG, "Status Changed: Temporarily Unavailable");
                    Toast.makeText(MapsActivity.this, "Status Changed: Temporarily Unavailable", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.AVAILABLE:
                    Log.v(TAG, "Status Changed: Available");
                    Toast.makeText(MapsActivity.this, "Status Changed: Available", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };// 位置偵聽器

    private void showMarkerMe(double lat, double lng){
        if (markerMe != null){
            markerMe.remove();
        }// 把前一個 Marker 刪掉

        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(new LatLng(lat, lng))
                    .title("我在這裡");
        if (mMap != null){
            markerMe = mMap.addMarker(markerOpt);
        }
        Toast.makeText(this, "lat:" + lat + ",lng:" + lng, Toast.LENGTH_SHORT).show();
    }// 顯示"我"在哪裡

    private void cameraFocusOnMe(double lat, double lng){
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng))
                .zoom(16)
                .build();
        if (mMap != null){
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPosition));
        }

    }// 更新攝影機位置

    private void trackToMe(double lat, double lng){
        if (traceOfMe == null) {
            traceOfMe = new ArrayList<>();
        }
        traceOfMe.add(new LatLng(lat, lng));

        PolylineOptions polylineOpt = new PolylineOptions();
        for (LatLng latlng : traceOfMe) {
            polylineOpt.add(latlng);
        }

        polylineOpt.color(Color.RED);

        if (mMap != null){
            Polyline line = mMap.addPolyline(polylineOpt);
            line.setWidth(10);
        }
    }// 畫出移動軌跡

    private void updateWithNewLocation(Location location){
        CharSequence where;
        if (location != null){
            double lng = location.getLongitude();// 經度
            double lat = location.getLatitude();// 緯度
            float speed = location.getSpeed();// 速度
            long time = location.getTime();// 時間
            String timeString = getTimeString(time);

            where = "經度: " + lng +
                    "\n緯度: " + lat +
                    "\n速度: " + speed +
                    "\n時間: " + timeString +
                    "\nProvider: " + provider;

            //"我"
            showMarkerMe(lat, lng);
            cameraFocusOnMe(lat, lng);
            //trackToMe(lat, lng);
        }else{
            where = "No location found.";
        }
        Toast.makeText(this, where, duration).show();// 顯示資訊
    }// 更新並顯示新位置

    private String getTimeString(long timeInMilliseconds){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(timeInMilliseconds);
    }

    /**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 */
final LatLng YZU = new LatLng(24.970093, 121.263273);
    final LatLng PATH_OF_PHILOSOPHY = new LatLng(24.967640, 121.267516);
    final LatLng YZU_BUILDING7 = new LatLng(24.968542, 121.266913);
    private void createLocation(){
        mMap.addMarker(new MarkerOptions().position(YZU)
                .title("元智大學")
                .snippet("於1987年創校")
        );
        mMap.addMarker(new MarkerOptions().position(PATH_OF_PHILOSOPHY)
                .title("哲學之道")
                .snippet("本校參考日本「京都哲學之道」的理念")
        );
        mMap.addMarker(new MarkerOptions().position(YZU_BUILDING7)
                .title("元智七館")
                .snippet("電機通訊學院")
        );
    }// 創造各點的Marker

    @Override
    public void onMapReady(GoogleMap googleMap){
        if ((mMap = googleMap) != null){
            // Add a marker in Sydney and move the camera 緯度, 經度
            createLocation();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(YZU));// 先把攝影機移到點上面(移除會有導過去的動畫)

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(YZU)              // Sets the center of the map to ZINTUN
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east 順時針轉
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees 視角頃斜
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }// 開啟google map
}
