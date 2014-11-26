package org.zywx.wbpalmstar.plugin.uexbaidumap;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.map.BaiduMap.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.zywx.wbpalmstar.engine.universalex.EUExUtil;

public class EBaiduMapBaseActivity extends Activity implements OnMapClickListener, OnMapStatusChangeListener,
OnMapLoadedCallback, OnMapDoubleClickListener, OnMapLongClickListener, OnMyLocationClickListener,
SnapshotReadyCallback, OnGetGeoCoderResultListener {
	
	private static final String LTAG = EBaiduMapBaseActivity.class.getSimpleName();
	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;
	private UiSettings mUiSettings = null;
	private EBaiduMapOverlayMgr eBaiduMapOverlayMgr = null;
	private EBaiduMapPoiSearch eBaiduMapPoiSearch = null;
	private EBaiduMapBusLineSearch eBaiduMapBusLineSearch = null;
	private EBaiduMapRoutePlanSearch eBaiduMapRoutePlanSearch = null;
	private EUExBaiduMap uexBaseObj;
	private SDKReceiver mSDKReceiver = null;
	// 定位相关
	private LocationClient mLocClient;
	boolean isOneTimeLocation = false;// 是否是一次定位
	private BDLocation mBDLocation;// 定位数据
	private MyLocationListenner myListener = new MyLocationListenner();
	private GeoCoder mGeoCoder = null;
	
	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	private class SDKReceiver extends BroadcastReceiver {  
	    public void onReceive(Context context, Intent intent) { 
	    	String action = intent.getAction();
	    	if (action
	    			.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
	    		jsonSDKReceiverErrorCallback(action);
	    	} else if (action
	    			.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
	    		jsonSDKReceiverErrorCallback(action);
	    	}
	    }  
	}
	
	private void jsonSDKReceiverErrorCallback(String errorInfo) {
		if (uexBaseObj != null) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_ERRORINFO, errorInfo);
				String js = EUExBaiduMap.SCRIPT_HEADER + "if(" + EBaiduMapUtils.MAP_FUN_ON_SDK_RECEIVER_ERROR + "){" 
						+ EBaiduMapUtils.MAP_FUN_ON_SDK_RECEIVER_ERROR + "('"+ jsonObject.toString() + "');}";
				uexBaseObj.onCallback(js);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (intent.hasExtra(EBaiduMapUtils.MAP_EXTRA_LNG) && intent.hasExtra(EBaiduMapUtils.MAP_EXTRA_LAN)) {
			// 当用intent参数时，设置中心点为指定点
			Bundle b = intent.getExtras();
			LatLng p = new LatLng(b.getDouble(EBaiduMapUtils.MAP_EXTRA_LAN), b.getDouble(EBaiduMapUtils.MAP_EXTRA_LNG));
			mMapView = new MapView(this,
					new BaiduMapOptions().mapStatus(new MapStatus.Builder()
							.target(p).build()));
		} else {
			mMapView = new MapView(this, new BaiduMapOptions());
		}
		
		if (intent.hasExtra(EBaiduMapUtils.MAP_EXTRA_UEXBASE_OBJ)) {
			setUexBaseObj((EUExBaiduMap)intent.getParcelableExtra(EBaiduMapUtils.MAP_EXTRA_UEXBASE_OBJ));
		}
		
		setContentView(mMapView);
		mBaiduMap = mMapView.getMap();
		mUiSettings = mBaiduMap.getUiSettings();
		eBaiduMapOverlayMgr = new EBaiduMapOverlayMgr(this, mBaiduMap, mMapView);
		eBaiduMapPoiSearch = new EBaiduMapPoiSearch(this, mBaiduMap, mMapView);
		eBaiduMapBusLineSearch = new EBaiduMapBusLineSearch(this, mBaiduMap, mMapView);
		eBaiduMapRoutePlanSearch = new EBaiduMapRoutePlanSearch(this, mBaiduMap, mMapView);

		IntentFilter iFilter = new IntentFilter();  
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mSDKReceiver = new SDKReceiver();  
		registerReceiver(mSDKReceiver, iFilter);

		// 定位初始化
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(2000);
		mLocClient.setLocOption(option);
		
		mGeoCoder = GeoCoder.newInstance();
		mGeoCoder.setOnGetGeoCodeResultListener(this);
		
		mBaiduMap.setOnMapClickListener(this);
		mBaiduMap.setOnMapStatusChangeListener(this);
		mBaiduMap.setOnMapLoadedCallback(this);
		mBaiduMap.setOnMapDoubleClickListener(this);
		mBaiduMap.setOnMapLongClickListener(this);
		mBaiduMap.setOnMyLocationClickListener(this);
		mBaiduMap.snapshot(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mSDKReceiver);
		eBaiduMapOverlayMgr.clearMapOverLayMgr();
		eBaiduMapPoiSearch.destroy();
		eBaiduMapBusLineSearch.destroy();
		eBaiduMapRoutePlanSearch.destroy();
		mGeoCoder.destroy();
		stopLocation();
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
		Log.i(LTAG, "onDestroy");
	}
	
	public void setMapType(int type) {
		mBaiduMap.setMapType(type);
	}
	
	public void setTrafficEnabled(boolean enable) {
		mBaiduMap.setTrafficEnabled(enable);
	}
	
	public void setCenter(double lng, double lat, boolean isUseAnimate) {
		LatLng ll = new LatLng(lat, lng);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		
		if (isUseAnimate) {
			mBaiduMap.animateMapStatus(u);
		} else {
			mBaiduMap.setMapStatus(u);
		}	
	}
	
	/**
	 * 处理缩放 sdk 缩放级别范围： [3.0,19.0]
	 */
	public void zoomTo(float zoomLevel) {
		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(zoomLevel);
		mBaiduMap.animateMapStatus(u);
	}
	
	public void zoomIn() {
		MapStatusUpdate u = MapStatusUpdateFactory.zoomIn();
		mBaiduMap.animateMapStatus(u);
	}
	
	public void zoomOut() {
		MapStatusUpdate u = MapStatusUpdateFactory.zoomOut();
		mBaiduMap.animateMapStatus(u);
	}
	
	public void rotate(int angle) {
		MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).rotate(angle).build();
		MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
		mBaiduMap.animateMapStatus(u);
	}
	
	public void overlook(int angle) {
		MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).overlook(angle).build();
		MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
		mBaiduMap.animateMapStatus(u);
	}
	
	public void setZoomEnable(boolean enable){
		mUiSettings.setZoomGesturesEnabled(enable);
	}

	public void setRotateEnable(boolean enable){
		mUiSettings.setRotateGesturesEnabled(enable);
	}
	
	public void setCompassEnable(boolean enable){
		mUiSettings.setCompassEnabled(enable);
	}
	
	public void setScrollEnable(boolean enable){
		mUiSettings.setScrollGesturesEnabled(enable);
	}
	
	public void setOverlookEnable(boolean enable){
		mUiSettings.setOverlookingGesturesEnabled(enable);
	}

	public void addMarkerOverlay(String markerInfo) {
		eBaiduMapOverlayMgr.addMarkerOverlay(markerInfo);
	}

    public void removeMarkerOverlay(String markerId) {
        eBaiduMapOverlayMgr.removeMarkerOverlay(markerId);
    }
	
	public void setMarkerOverlay(String markerId, String markerInfo) {
		eBaiduMapOverlayMgr.setMarkerOverlay(markerId, markerInfo);
	}
	
	public void showBubble(String markerId) {
		eBaiduMapOverlayMgr.showBubble(markerId);
	}

    public void hideBubble() {
        eBaiduMapOverlayMgr.hideBubble();
    }

	public EUExBaiduMap getUexBaseObj() {
		return uexBaseObj;
	}

	public void setUexBaseObj(EUExBaiduMap uexBaseObj) {
		this.uexBaseObj = uexBaseObj;
	}
	
	public void poiSearchInCity(String city, String searchKey, int pageNum) {
		eBaiduMapPoiSearch.poiSearchInCity(city, searchKey, pageNum);
	}
	
	public void poiNearbySearch(double lng, double lat, int radius, String searchKey, int pageNum) {
		eBaiduMapPoiSearch.poiNearbySearch(lng, lat, radius, searchKey, pageNum);
	}
	
	public void poiBoundSearch(double east, double north, double west, double south, String searchKey, int pageNum) {
		eBaiduMapPoiSearch.poiBoundSearch(east, north, west, south, searchKey, pageNum);
	}

	public void busLineSearch(String city, String searchKey) {
		eBaiduMapBusLineSearch.busLineSearch(city, searchKey);
	}
	
	public void preBusLineNode() {
		eBaiduMapBusLineSearch.preBusLineNode();
	}
	
	public void nextBusLineNode() {
		eBaiduMapBusLineSearch.nextBusLineNode();
	}
	
    public void showRoutePlan(EBaiduMapRoutePlanOptions routePlanOptions) {
    	eBaiduMapRoutePlanSearch.showRoutePlan(routePlanOptions);
    }
    
    public void preRouteNode() {
    	eBaiduMapRoutePlanSearch.preRouteNode();
    }
    
    public void nextRouteNode() {
    	eBaiduMapRoutePlanSearch.nextRouteNode();
    }

	public void geocode(String city, String address) {
		Log.i(LTAG, "geocode");
		mGeoCoder.geocode(new GeoCodeOption().city(city).address(address));
	}
	
	public void reverseGeoCode(double lng, double lat) {
		Log.i(LTAG, "reverseGeoCode");
		LatLng ll = new LatLng(lat, lng);
		mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			jsonLatLngCallback(null,EBaiduMapUtils.MAP_FUN_CB_GET_GEOCODE_RESULT);
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
				.fromResource(EUExUtil.getResDrawableID("plugin_map_icon_marker_default"))));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		jsonLatLngCallback(result.getLocation(),EBaiduMapUtils.MAP_FUN_CB_GET_GEOCODE_RESULT);
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			jsonAddressCallback(null,EBaiduMapUtils.MAP_FUN_CB_GET_REVERSE_GEOCODE_RESULT);
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
				.fromResource(EUExUtil.getResDrawableID("plugin_map_icon_marker_default"))));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		jsonAddressCallback(result.getAddress(),EBaiduMapUtils.MAP_FUN_CB_GET_REVERSE_GEOCODE_RESULT);
	}

	/**
	 * 获得当前位置
	 */
	public void getCurrentLocation() {
		Log.i(LTAG, "getCurrentLocation");
		if(mLocClient != null && !mLocClient.isStarted()) {
			mLocClient.start();
			isOneTimeLocation = true;
		}
	}

	/**
	 * 开始定位
	 */
	public void startLocation() {
		Log.i(LTAG, "startLocation");
		if(mLocClient != null && !mLocClient.isStarted()) {
			mLocClient.start();
		}
	}
	
	/**
	 * 结束定位
	 */
	public void stopLocation() {
		Log.i(LTAG, "stopLocation");
		if(mLocClient != null && mLocClient.isStarted()) {
			// 退出时销毁定位
			mLocClient.stop();
			// 关闭定位图层
			setMyLocationEnabled(false);
		}
	}
	
	/**
	 * 显示或隐藏用户位置
	 */
	public void setMyLocationEnabled(boolean enable) {
		Log.i(LTAG, "setMyLocationEnabled");
		mBaiduMap.setMyLocationEnabled(enable);
		if (enable) {
			MyLocationData locationData = new MyLocationData.Builder()
					.accuracy(mBDLocation.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(mBDLocation.getLatitude())
					.longitude(mBDLocation.getLongitude()).build();
			mBaiduMap.setMyLocationData(locationData);
			LatLng ll = new LatLng(mBDLocation.getLatitude(),
					mBDLocation.getLongitude());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.animateMapStatus(u);
		}
	}
	
	/**
	 * 定位SDK监听函数
	 */
	private class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null) {
				jsonReceiveLocationCallback(null,
						EBaiduMapUtils.MAP_FUN_CB_CURRENT_LOCATION);
				return;
			}
			mBDLocation = location;
			if (isOneTimeLocation) {
				jsonReceiveLocationCallback(location,
						EBaiduMapUtils.MAP_FUN_CB_CURRENT_LOCATION);
				isOneTimeLocation = false;
				mLocClient.stop();
			} else {
				jsonReceiveLocationCallback(location,
						EBaiduMapUtils.MAP_FUN_ON_RECEIVE_LOCATION);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	
	private void jsonReceiveLocationCallback(BDLocation location, String header) {
		if (uexBaseObj != null) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LNG, Double.toString(location.getLatitude()));
				jsonObject.put(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LAT, Double.toString(location.getLongitude()));
				jsonObject.put(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_TIMESTAMP, location.getTime());
				String js = EUExBaiduMap.SCRIPT_HEADER + "if(" + header + "){" 
						+ header + "('"+ jsonObject.toString() + "');}";
				uexBaseObj.onCallback(js);
			} catch (JSONException e) {
				String js = EUExBaiduMap.SCRIPT_HEADER + "if(" + header + "){" 
						+ header + "('"+ null + "');}";
				uexBaseObj.onCallback(js);
				e.printStackTrace();
			}
		}
	}  
	
	private void jsonAddressCallback(String address, String header) {
		if (uexBaseObj != null) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_ADDRESS, address);
				String js = EUExBaiduMap.SCRIPT_HEADER + "if(" + header + "){" 
						+ header + "('"+ jsonObject.toString() + "');}";
				uexBaseObj.onCallback(js);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}  
	
	private void jsonLatLngCallback(LatLng point, String header) {
		if (uexBaseObj != null) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LNG, Double.toString(point.longitude));
				jsonObject.put(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LAT, Double.toString(point.latitude));
				String js = EUExBaiduMap.SCRIPT_HEADER + "if(" + header + "){" 
						+ header + "('"+ jsonObject.toString() + "');}";
				uexBaseObj.onCallback(js);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}  
	
	/** 
	 * 地图单击事件回调函数 
	 * @param point 点击的地理坐标 
	 */  
	public void onMapClick(LatLng point){  
		jsonLatLngCallback(point, EBaiduMapUtils.MAP_FUN_ON_MAP_CLICK_LISTNER);
	}
	
	/** 
	 * 地图内 Poi 单击事件回调函数 
	 * @param poi 点击的 poi 信息 
	 */  
	public boolean onMapPoiClick(MapPoi poi){
		return false;  
	}  
	
	/** 
	 * 手势操作地图，设置地图状态等操作导致地图状态开始改变。 
	 * @param status 地图状态改变开始时的地图状态 
	 */  
	public void onMapStatusChangeStart(MapStatus status){
	}  
	
	/** 
	 * 地图状态变化中 
	 * @param status 当前地图状态 
	 */  
	public void onMapStatusChange(MapStatus status){
	}  
	
	/** 
	 * 地图状态改变结束 
	 * @param status 地图状态改变结束后的地图状态 
	 */  
	public void onMapStatusChangeFinish(MapStatus status){
	}  

	/** 
	 * 地图加载完成回调函数 
	 */  
	public void onMapLoaded(){
		Log.i(LTAG, "onMapLoaded");
	}  

	/** 
	 * 地图双击事件监听回调函数 
	 * @param point 双击的地理坐标 
	 */  
	public void onMapDoubleClick(LatLng point){  
		jsonLatLngCallback(point, EBaiduMapUtils.MAP_FUN_ON_MAP_DOUBLE_CLICK_LISTNER);
	}  

	/** 
	 * 地图长按事件监听回调函数 
	 * @param point 长按的地理坐标 
	 */  
	public void onMapLongClick(LatLng point){  
		jsonLatLngCallback(point, EBaiduMapUtils.MAP_FUN_ON_MAP_LONG_CLICK_LISTNER);
	}  

	/** 
	 * 地图定位图标点击事件监听函数 
	 */  
	public boolean onMyLocationClick(){  
		return true; 
	}  
	
	/** 
	 * 地图截屏回调接口 
	 * @param snapshot 截屏返回的 bitmap 数据 
	 */  
	public void onSnapshotReady(Bitmap snapshot){  
	}  
	
	
	public void removeOverlay(String overlayInfo){
		eBaiduMapOverlayMgr.removeOverlay(overlayInfo);
		
	}
	
	public void addDotOverlay(String dotInfo){
		eBaiduMapOverlayMgr.addDotOverlay(dotInfo);
	}
	
	public void addPolylineOverlay(String polylineInfo){
		eBaiduMapOverlayMgr.addPolylineOverlay(polylineInfo);
	}
	
	public void addArcOverlay(String arcInfo){
		eBaiduMapOverlayMgr.addArcOverlay(arcInfo);
	}
	
	public void addCircleOverlay(String circleInfo){
		eBaiduMapOverlayMgr.addCircleOverlay(circleInfo);
	}
	
	public void addPolygonOverlay(String polygonInfo){
		eBaiduMapOverlayMgr.addPolygonOverlay(polygonInfo);
	}
	
	public void addGroundOverlay(String groundInfo){
		eBaiduMapOverlayMgr.addGroundOverlay(groundInfo);
	}
	
	public void addTextOverlay(String textInfo){
		eBaiduMapOverlayMgr.addTextOverlay(textInfo);
	}
}
