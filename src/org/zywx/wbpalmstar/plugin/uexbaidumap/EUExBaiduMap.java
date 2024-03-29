package org.zywx.wbpalmstar.plugin.uexbaidumap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;

public class EUExBaiduMap extends EUExBase implements Parcelable {

	private static boolean isBaiduSdkInit = false;

	public EUExBaiduMap(Context context, EBrowserView inParent) {
		super(context, inParent);
		if (isBaiduSdkInit == false) {
			// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
			SDKInitializer.initialize(context.getApplicationContext());
			isBaiduSdkInit = true;
		}
	}

	// uexBaiduMap.open(x, y, width, height, longitude, latitude)
	public void open(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_OPEN, params);
	}

	public void close(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_CLOSE, params);
	}

	public void setMapType(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_SETMAPTYPE, params);
	}

	public void setTrafficEnabled(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_SETTRAFFIC, params);
	}

	public void setCenter(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_SETCENTER, params);
	}

	public void setZoomLevel(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ZOOMTO, params);
	}

	public void zoomIn(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ZOOMIN, params);
	}

	public void zoomOut(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ZOOMOUT, params);
	}

	public void rotate(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ROTATE, params);
	}

	public void overlook(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_OVERLOOK, params);
	}

	public void setZoomEnable(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ZOOMENABLE, params);
	}

	public void setRotateEnable(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ROTATEENABLE, params);
	}
	
	public void setCompassEnable(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_COMPASSENABLE, params);
	}
	
	public void setScrollEnable(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_SCROLLENABLE, params);
	}
	
	public void setOverlookEnable(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_OVERLOOKENABLE, params);
	}

	public void addMarkersOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ADDMARKERSOVERLAY,
				params);
	}

	public void setMarkerOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_SETMARKERSOVERLAY,
				params);
	}
	
	public void showBubble(String[] params) {
        sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_SHOWBUBBLE, params);
    }

    public void hideBubble(String[] params) {
        sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_HIDEBUBBLE, params);
    }

	public void removeMakersOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_REMOVEMAKERSOVER,
				params);
	}

	public void removeOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_REMOVEOVERLAY, params);
	}

	public void addDotOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ADDDOTOVERLAY, params);
	}

	public void addPolylineOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ADDPOLYLINEOVERLAY,
				params);
	}

	public void addArcOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ADDARCOVERLAY, params);
	}

	public void addCircleOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ADDCIRCLEOVERLAY,
				params);
	}

	public void addPolygonOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ADDPOLYGONOVERLAY,
				params);
	}

	public void addGroundOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ADDGROUNDOVERLAY,
				params);
	}

	public void addTextOverlay(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_ADDTEXTOVERLAY, params);
	}	
	
	public void poiSearchInCity(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_POISEARCHINCITY, params);
	}
	
	public void poiNearbySearch(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_POINEARBYSEARCH, params);
	}
	
	public void poiBoundSearch(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_POIBOUNDSEARCH, params);
	}

	public void busLineSearch(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_BUSLINESEARCH, params);
	}
	
	public void preBusLineNode(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_PREBUSLINENODE, params);
	}
	
	public void nextBusLineNode(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_NEXTBUSLINENODE, params);
	}
	
	public void showRoutePlan(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_SHOWROUTEPLAN, params);
	}
	
	public void preRouteNode(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_PREROUTENODE, params);
	}
	
	public void nextRouteNode(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_NEXTROUTENODE, params);
	}
	
	public void geocode(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_GEOCODE, params);
	}
	
	public void reverseGeocode(String[] params){
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_REVERSEGEOCODE, params);
	}
	
	public void getCurrentLocation(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_GETCURRENTLOCATION,
				params);
	}
	
	public void startLocation(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_STARTLOCATION,
				params);
	}
	
	public void stopLocation(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_STOPTLOCATION,
				params);
	}	
	
	public void setMyLocationEnable(String[] params) {
		sendMessageWithType(EBaiduMapUtils.MAP_MSG_CODE_SETMYLOCATIONENABLE, params);
	}

	@Override
	protected boolean clean() {
		close(null);
		return false;
	}

	private void handleMessageInMap(Message msg) {
		LocalActivityManager mgr = ((ActivityGroup) mContext)
				.getLocalActivityManager();
		Activity activity = mgr.getActivity(EBaiduMapUtils.MAP_ACTIVITY_ID);

		if (activity != null && activity instanceof EBaiduMapBaseActivity) {
			String[] params = msg.getData().getStringArray(
					EBaiduMapUtils.MAP_FUN_PARAMS_KEY);
			EBaiduMapBaseActivity eBaiduMapBaseActivity = ((EBaiduMapBaseActivity) activity);

        	switch (msg.what) {
    		case EBaiduMapUtils.MAP_MSG_CODE_CLOSE:
    			handleCloseBaiduMap(params, eBaiduMapBaseActivity, mgr);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_SETMAPTYPE:
    			handleSetMapType(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_SETTRAFFIC:
    			handleSetTraffic(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_SETCENTER:
    			handleSetCenter(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_ZOOMTO:
    			handleZoomTo(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_ZOOMIN:
    			handleZoomIn(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_ZOOMOUT:
    			handleZoomOut(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_ROTATE:
    			handleRotate(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_OVERLOOK:
    			handleOverlook(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_ZOOMENABLE:
    			handleZoomEnable(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_ROTATEENABLE:
    			handleRotateEnable(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_COMPASSENABLE:
    			handleCompassEnable(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_SCROLLENABLE:
    			handleScrollEnable(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_OVERLOOKENABLE:
    			handleOverlookEnable(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_ADDMARKERSOVERLAY:
    			handleAddMarkersOverlay(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_SETMARKERSOVERLAY:
    			handleSetMarkeOverlay(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_SHOWBUBBLE:
    			handleShowBubble(params, eBaiduMapBaseActivity);
    			break;
            case EBaiduMapUtils.MAP_MSG_CODE_HIDEBUBBLE:
                handleHideBubble(params, eBaiduMapBaseActivity);
                break;
            case EBaiduMapUtils.MAP_MSG_CODE_REMOVEMAKERSOVER:
                handleRemoveMakersOverlay(params, eBaiduMapBaseActivity);
                break;
    		case EBaiduMapUtils.MAP_MSG_CODE_POISEARCHINCITY:
    			handlePoiSearchInCity(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_POINEARBYSEARCH:
    			handlePoiNearbySearch(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_POIBOUNDSEARCH:
    			handlePoiBoundSearch(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_BUSLINESEARCH:
    			handleBusLineSearch(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_PREBUSLINENODE:
    			handlePreBusLineNode(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_NEXTBUSLINENODE:
    			handleNextBusLineNode(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_SHOWROUTEPLAN:
    			handleShowRoutePlan(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_PREROUTENODE:
    			handlePreRouteNode(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_NEXTROUTENODE:
    			handleNextRouteNode(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_GEOCODE:
    			handleGeocode(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_REVERSEGEOCODE:
    			handleReverseGeocode(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_GETCURRENTLOCATION:
    			handleGetCurrentLocation(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_STARTLOCATION:
    			handleStartLocation(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_STOPTLOCATION:
    			handleStopLocation(params, eBaiduMapBaseActivity);
    			break;
    		case EBaiduMapUtils.MAP_MSG_CODE_SETMYLOCATIONENABLE:
    			handleSetMyLocationEnable(params, eBaiduMapBaseActivity);
    			break;
		    case EBaiduMapUtils.MAP_MSG_CODE_REMOVEOVERLAY:
				handleRemoveOverlay(params, eBaiduMapBaseActivity);
				break;
			case EBaiduMapUtils.MAP_MSG_CODE_ADDDOTOVERLAY:
				handleAddDotOverlay(params, eBaiduMapBaseActivity);
				break;
			case EBaiduMapUtils.MAP_MSG_CODE_ADDPOLYLINEOVERLAY:
				handleAddPolylineOverlay(params, eBaiduMapBaseActivity);
				break;
			case EBaiduMapUtils.MAP_MSG_CODE_ADDARCOVERLAY:
				handleAddArcOverlay(params, eBaiduMapBaseActivity);
				break;
			case EBaiduMapUtils.MAP_MSG_CODE_ADDCIRCLEOVERLAY:
				handleAddCircleOverlay(params, eBaiduMapBaseActivity);
				break;
			case EBaiduMapUtils.MAP_MSG_CODE_ADDPOLYGONOVERLAY:
				handleAddPolygonOverlay(params, eBaiduMapBaseActivity);
				break;
			case EBaiduMapUtils.MAP_MSG_CODE_ADDGROUNDOVERLAY:
				handleAddGroundOverlay(params, eBaiduMapBaseActivity);
				break;
			case EBaiduMapUtils.MAP_MSG_CODE_ADDTEXTOVERLAY:
				handleAddTextOverlay(params, eBaiduMapBaseActivity);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onHandleMessage(Message msg) {
		// TODO Auto-generated method stub
		// super.onHandleMessage(msg);
		if (msg.what == EBaiduMapUtils.MAP_MSG_CODE_OPEN) {
			handleOpen(msg);
		} else {
			handleMessageInMap(msg);
		}
	}

	private void handleOpen(Message msg) {
		String[] params = msg.getData().getStringArray(
				EBaiduMapUtils.MAP_FUN_PARAMS_KEY);
		if (params.length != 4 && params.length != 6) {
			return;
		}

		int x = 0, y = 0, w = 0, h = 0;
		double lng = 0.0, lat = 0.0;
		boolean isUseLngLat = false;

		try {
			x = Integer.parseInt(params[0]);
			y = Integer.parseInt(params[1]);
			w = Integer.parseInt(params[2]);
			h = Integer.parseInt(params[3]);

			if (params.length == 6) {
				lng = Double.parseDouble(params[4]);
				lat = Double.parseDouble(params[5]);
				isUseLngLat = true;
			}

			Intent intent = new Intent(mContext, EBaiduMapBaseActivity.class);
			if (isUseLngLat == true) {
				intent.putExtra(EBaiduMapUtils.MAP_EXTRA_LNG, lng);
				intent.putExtra(EBaiduMapUtils.MAP_EXTRA_LAN, lat);
			}
			intent.putExtra(EBaiduMapUtils.MAP_EXTRA_UEXBASE_OBJ, this);

			LocalActivityManager mgr = ((ActivityGroup) mContext)
					.getLocalActivityManager();
			EBaiduMapBaseActivity eBaiduMapBaseActivity = (EBaiduMapBaseActivity) mgr
					.getActivity(EBaiduMapUtils.MAP_ACTIVITY_ID);
			if (eBaiduMapBaseActivity != null) {
				return;
			}
			Window window = mgr.startActivity(EBaiduMapUtils.MAP_ACTIVITY_ID,
					intent);
			View decorView = window.getDecorView();
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w,	h);
			lp.leftMargin = x;
			lp.topMargin = y;
			addView2CurrentWindow(decorView, lp);
		} catch (Exception e) {
		}
	}

	private void handleCloseBaiduMap(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity,
			LocalActivityManager mgr) {
		View decorView = eBaiduMapBaseActivity.getWindow().getDecorView();
		mBrwView.removeViewFromCurrentWindow(decorView);
		mgr.destroyActivity(EBaiduMapUtils.MAP_ACTIVITY_ID, true);
	}

	private void handleSetMapType(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int type = Integer.parseInt(params[0]);
			if (type == 1) { // 普通地图
				type = BaiduMap.MAP_TYPE_NORMAL;
			} else if (type == 2) { // 卫星地图
				type = BaiduMap.MAP_TYPE_SATELLITE;
			}
			eBaiduMapBaseActivity.setMapType(type);
		} catch (Exception e) {
		}
	}

	private void handleSetTraffic(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int type = Integer.parseInt(params[0]);
			if (type == 0) { // 关闭交通图
				eBaiduMapBaseActivity.setTrafficEnabled(false);
			} else if (type == 1) { // 开启交通图
				eBaiduMapBaseActivity.setTrafficEnabled(true);
			}
		} catch (Exception e) {
		}
	}

	private void handleSetCenter(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			double lng = Double.parseDouble(params[0]);
			double lat = Double.parseDouble(params[1]);
			boolean isUseAnimate = false;
			if (params.length == 3) { // 设置动画
				int v = Integer.parseInt(params[2]);
				isUseAnimate = ((v == 1) ? true : false);
			}
			eBaiduMapBaseActivity.setCenter(lng, lat, isUseAnimate);
		} catch (Exception e) {
		}
	}

	private void handleZoomTo(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			float zoomLevel = Float.parseFloat(params[0]);
			if (zoomLevel < 3) {
				zoomLevel = 3.0f;
			}
			if (zoomLevel > 19) {
				zoomLevel = 19.0f;
			}
			eBaiduMapBaseActivity.zoomTo(zoomLevel);
		} catch (Exception e) {
		}
	}

	private void handleZoomIn(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.zoomIn();
		} catch (Exception e) {
		}
	}

	private void handleZoomOut(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.zoomOut();
		} catch (Exception e) {
		}
	}

	private void handleRotate(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int angle = Integer.parseInt(params[0]);
			eBaiduMapBaseActivity.rotate(angle);
		} catch (Exception e) {
		}
	}

	private void handleOverlook(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int angle = Integer.parseInt(params[0]);
			eBaiduMapBaseActivity.overlook(angle);
		} catch (Exception e) {
		}
	}

	private void handleZoomEnable(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int value = Integer.parseInt(params[0]);
			boolean enable = (value == 1) ? true : false;
			eBaiduMapBaseActivity.setZoomEnable(enable);
		} catch (Exception e) {
		}
	}

	private void handleRotateEnable(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int value = Integer.parseInt(params[0]);
			boolean enable = (value == 1) ? true : false;
			eBaiduMapBaseActivity.setRotateEnable(enable);
		} catch (Exception e) {
		}
	}

	private void handleCompassEnable(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int value = Integer.parseInt(params[0]);
			boolean enable = (value == 1) ? true : false;
			eBaiduMapBaseActivity.setCompassEnable(enable);
		} catch (Exception e) {
		}
	}

	private void handleScrollEnable(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int value = Integer.parseInt(params[0]);
			boolean enable = (value == 1) ? true : false;
			eBaiduMapBaseActivity.setScrollEnable(enable);
		} catch (Exception e) {
		}
	}

	private void handleOverlookEnable(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int value = Integer.parseInt(params[0]);
			boolean enable = (value == 1) ? true : false;
			eBaiduMapBaseActivity.setOverlookEnable(enable);
		} catch (Exception e) {
		}
	}

	private void handleAddMarkersOverlay(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			if (params.length != 1) {
				return;
			}
			String infoString = params[0];
			JSONArray jsonArray = new JSONArray(infoString);
			for (int i = 0; i < jsonArray.length(); i++) {
				infoString = jsonArray.getString(i);
				eBaiduMapBaseActivity.addMarkerOverlay(infoString);
			}
		} catch (Exception e) {
		}
	}

	private void handleSetMarkeOverlay(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			if (params.length != 2) {
				return;
			}
			String markerId = params[0];
			String markerInfo = params[1];
			eBaiduMapBaseActivity.setMarkerOverlay(markerId, markerInfo);
		} catch (Exception e) {
		}
	}

	private void handleShowBubble(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			if (params.length != 1) {
				return;
			}
			String markerId = params[0];
			eBaiduMapBaseActivity.showBubble(markerId);
		} catch (Exception e) {
		}
	}

	private void handleHideBubble(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.hideBubble();
		} catch (Exception e) {
		}
	}

	private void handleRemoveMakersOverlay(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			if (params.length != 1) {
				return;
			}
			String infoString = params[0];
			JSONArray jsonArray = new JSONArray(infoString);
			for (int i = 0; i < jsonArray.length(); i++) {
				infoString = jsonArray.getString(i);
				eBaiduMapBaseActivity.removeMarkerOverlay(infoString);
			}
		} catch (Exception e) {
		}
	}

	private void handlePoiSearchInCity(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			JSONObject json = new JSONObject(params[0]);
			String city = json
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_CITY);
			String searchKey = json
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_SEARCHKEY);
			int pageNum = Integer.parseInt(json
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_PAGENUM));
			eBaiduMapBaseActivity.poiSearchInCity(city, searchKey, pageNum);
		} catch (Exception e) {
		}
	}

	private void handlePoiNearbySearch(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			if (params == null || params.length == 0) {
				return;
			}
			JSONObject json = new JSONObject(params[0]);
			String lng = json.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LNG);
			String lat = json.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LAT);
			String radius = json
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_RADIUS);
			String searchKey = json
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_SEARCHKEY);
			String pageNum = json
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_PAGENUM);
			eBaiduMapBaseActivity.poiNearbySearch(Double.parseDouble(lng),
					Double.parseDouble(lat), (int) Float.parseFloat(radius),
					searchKey, Integer.parseInt(pageNum));
		} catch (Exception e) {
		}
	}

	private void handlePoiBoundSearch(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			// 1
			JSONObject jsonObject = new JSONObject(params[0]);
			String northeastStr = jsonObject
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_NORTHEAST);
			// 2
			JSONObject jsonNortheastObj = new JSONObject(northeastStr);
			String lngNE = jsonNortheastObj
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LNG);
			String latNE = jsonNortheastObj
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LAT);
			// 3
			String southwestStr = jsonObject
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_SOUTHWEST);
			// 4
			JSONObject jsonSouthwestObj = new JSONObject(southwestStr);
			String lngSW = jsonSouthwestObj
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LNG);
			String latSW = jsonSouthwestObj
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LAT);
			// 5
			String searchKey = jsonObject
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_SEARCHKEY);
			String pageNum = jsonObject
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_PAGENUM);
			eBaiduMapBaseActivity.poiBoundSearch(Double.parseDouble(lngNE),
					Double.parseDouble(latNE), Double.parseDouble(lngSW),
					Double.parseDouble(latSW), searchKey,
					Integer.parseInt(pageNum));
		} catch (Exception e) {
		}
	}

	private void handleBusLineSearch(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			JSONObject jsonObject = new JSONObject(params[0]);
			String cityStr = jsonObject
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_CITY);
			String busStr = jsonObject
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_BUSLINENAME);
			eBaiduMapBaseActivity.busLineSearch(cityStr, busStr);
		} catch (Exception e) {
		}
	}

	private void handlePreBusLineNode(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.preBusLineNode();
		} catch (Exception e) {
		}
	}

	private void handleNextBusLineNode(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.nextBusLineNode();
		} catch (Exception e) {
		}
	}

	private void handleShowRoutePlan(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			final EBaiduMapRoutePlanOptions routePlanOptions = EBaiduMapUtils
					.paraseRoutePlanOptions(params[0]);
			if (routePlanOptions == null) {
				return;
			}
			eBaiduMapBaseActivity.showRoutePlan(routePlanOptions);
		} catch (Exception e) {
		}
	}

	private void handlePreRouteNode(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.preRouteNode();
		} catch (Exception e) {
		}
	}

	private void handleNextRouteNode(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.nextRouteNode();
		} catch (Exception e) {
		}
	}

	private void handleGeocode(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			JSONObject jsonObject = new JSONObject(params[0]);
			String cityStr = jsonObject
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_CITY);
			String addrStr = jsonObject
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_ADDRESS);
			eBaiduMapBaseActivity.geocode(cityStr, addrStr);
		} catch (Exception e) {
		}
	}

	private void handleReverseGeocode(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			JSONObject json = new JSONObject(params[0]);
			double lng = Double.parseDouble(json
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LNG));
			double lat = Double.parseDouble(json
					.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_LAT));
			eBaiduMapBaseActivity.reverseGeoCode(lng, lat);
		} catch (Exception e) {
		}
	}

	private void handleGetCurrentLocation(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.getCurrentLocation();
		} catch (Exception e) {
		}
	}
	
	private void handleStartLocation(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.startLocation();
		} catch (Exception e) {
		}
	}
	
	private void handleStopLocation(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			eBaiduMapBaseActivity.stopLocation();
		} catch (Exception e) {
		}
	}
	
	private void handleSetMyLocationEnable(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			int value = Integer.parseInt(params[0]);
			boolean enable = (value == 1) ? true : false;
			eBaiduMapBaseActivity.setMyLocationEnabled(enable);
		} catch (Exception e) {
		}
	}

	private void sendMessageWithType(int msgType, String[] params) {
		Message msg = new Message();
		msg.what = msgType;
		msg.obj = this;
		Bundle b = new Bundle();
		b.putStringArray(EBaiduMapUtils.MAP_FUN_PARAMS_KEY, params);
		msg.setData(b);
		mHandler.sendMessage(msg);
	}

	private void addView2CurrentWindow(View child,
			RelativeLayout.LayoutParams parms) {
		int l = (int) (parms.leftMargin);
		int t = (int) (parms.topMargin);
		int w = parms.width;
		int h = parms.height;
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, h);
		lp.gravity = Gravity.NO_GRAVITY;
		lp.leftMargin = l;
		lp.topMargin = t;
		adptLayoutParams(parms, lp);
		mBrwView.addViewToCurrentWindow(child, lp);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
	}

	private void handleAddTextOverlay(String[] params,
			final EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		if (params.length != 1) {
			return;
		}
		eBaiduMapBaseActivity.addTextOverlay(params[0]);
	}

	private void handleAddGroundOverlay(String[] params,
			final EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		if (params.length != 1) {
			return;
		}
		final String groundInfo = params[0];
		new Thread(new Runnable() {
			@Override
			public void run() {
				eBaiduMapBaseActivity.addGroundOverlay(groundInfo);
			}
		}).start();
	}

	private void handleAddPolygonOverlay(String[] params,
			final EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		if (params.length != 1) {
			return;
		}
		eBaiduMapBaseActivity.addPolygonOverlay(params[0]);
	}

	private void handleAddCircleOverlay(String[] params,
			final EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		if (params.length != 1) {
			return;
		}
		eBaiduMapBaseActivity.addCircleOverlay(params[0]);
	}

	private void handleAddArcOverlay(String[] params,
			final EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		if (params.length != 1) {
			return;
		}
		eBaiduMapBaseActivity.addArcOverlay(params[0]);
	}

	private void handleAddPolylineOverlay(String[] params,
			final EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		if (params.length != 1) {
			return;
		}
		eBaiduMapBaseActivity.addPolylineOverlay(params[0]);
	}

	private void handleAddDotOverlay(String[] params,
			final EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		if (params.length != 1) {
			return;
		}
		eBaiduMapBaseActivity.addDotOverlay(params[0]);
	}

	private void handleRemoveOverlay(String[] params,
			EBaiduMapBaseActivity eBaiduMapBaseActivity) {
		try {
			if (params.length != 1) {
				return;
			}
			eBaiduMapBaseActivity.removeOverlay(params[0]);
		} catch (Exception e) {
		}
	}
}
