package org.zywx.wbpalmstar.plugin.uexbaidumap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.LatLngBounds.Builder;
import org.json.JSONObject;
import org.zywx.wbpalmstar.base.BUtility;

import java.util.HashMap;


public class EBaiduMapOverlayMgr implements OnMarkerClickListener{

	private HashMap<String, EBaiduMapOverlay> mEbaiduMapOverlays;
	protected Context mContext;
	protected BaiduMap mBaiduMap;
	protected MapView mMapView;
	
	public EBaiduMapOverlayMgr(Context context, BaiduMap baiduMap, MapView mapView) {
		
		mContext = context;
		mBaiduMap = baiduMap;
		mMapView = mapView;
		mEbaiduMapOverlays = new HashMap<String, EBaiduMapOverlay>();

        mBaiduMap.setOnMarkerClickListener(this);
		
	}

    public void removeMarkerOverlay(String markerId) {


        try {

            EBaiduMapMarkerOverlay mapMarkerOverlay = (EBaiduMapMarkerOverlay) mEbaiduMapOverlays.get(markerId);

            if (mapMarkerOverlay == null) {

                return;
            }


            // 1
            mEbaiduMapOverlays.remove(markerId);

            // 2
            mapMarkerOverlay.clearOverlay();




        } catch (Exception e) {
            // TODO: handle exception
        }
    }

	
	public void addMarkerOverlay(String markerInfo) {
		
//
//		{
//			id:11
//			lng:116.400244, //经度， 必选
//			lat:39.963175,  //纬度， 必选
//		    icon:res://icon_marka.png  //可选
//		}


        EBaiduMapMarkerOverlayOptions markerOverlayOptions = EBaiduMapUtils.getMarkerOverlayOpitonsWithJSON(markerInfo);
        if(markerOverlayOptions == null) {
        	return;
        }
        String markerId = markerOverlayOptions.getIdStr();

        if (mEbaiduMapOverlays.containsKey(markerId)) {

            return;
        }


        String lngStr = markerOverlayOptions.getLngStr();
        String latStr = markerOverlayOptions.getLatStr();
        String iconPath = markerOverlayOptions.getIconPath();
        Bitmap iconBitmap;


        if (iconPath != null) {

            iconBitmap = EBaiduMapUtils.getBitMapFromImageUrl(mContext, iconPath);

        } else {

            iconBitmap = EBaiduMapUtils.getDefaultMarkerBitMap(mContext);
        }

        BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromBitmap(iconBitmap);

        LatLng llMarker = new LatLng(Double.parseDouble(latStr), Double.parseDouble(lngStr));

        OverlayOptions ooM = new MarkerOptions().position(llMarker).icon(markerDescriptor);

        Marker marker = (Marker) mBaiduMap.addOverlay(ooM);

        EBaiduMapMarkerOverlay markerOverlay = new EBaiduMapMarkerOverlay(markerId, mContext, mBaiduMap);

        markerOverlay.setMarker(marker);

        Bundle b = new Bundle();

        b.putString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_ID, markerId);
        marker.setExtraInfo(b);

        mEbaiduMapOverlays.put(markerId, markerOverlay);


    }
	
	public void setMarkerOverlay(String markerId, String markerInfo) {
		
//		{
//			markerInfo:
//			     {
//			          longitude:116.400244,
//			      latitude:39.963175,         //经纬度， 可选
//			             icon:res://icon_marka.png,                // 可选
//			             extraInfo:{ // 自定义json格式数据 }                 // 可选
//			            bubble:
//			               {
//			                    title: “”， //必选
//			                     subTitle:””, //可选
//			                    bgImage:”” //可选
//			               }   // 可选
//			     }
//
//		}
		
		try {
			
			EBaiduMapMarkerOverlay mapMarkerOverlay = (EBaiduMapMarkerOverlay) mEbaiduMapOverlays.get(markerId);
			
			if (mapMarkerOverlay == null) {
				
				return;
			}
			
			
			JSONObject makerJsonObject = new JSONObject(markerInfo);
			



            String markerJsonInfo = makerJsonObject.getString(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_MARKERINFO);


            EBaiduMapMarkerOverlayOptions markerOverlayOptions = EBaiduMapUtils.getMarkerOverlayOpitonsWithJSON(markerJsonInfo);


            String lngStr = markerOverlayOptions.getLngStr();
            String latStr = markerOverlayOptions.getLatStr();
            String iconPath = markerOverlayOptions.getIconPath();
            Bitmap iconBitmap;


			
			if (lngStr != null && latStr != null) {
				
				LatLng llMarker = new LatLng(Double.parseDouble(latStr), Double.parseDouble(lngStr));
				mapMarkerOverlay.getMarker().setPosition(llMarker);
			}
			
			
			if (iconPath != null) {

				
				iconBitmap = EBaiduMapUtils.getBitMapFromImageUrl(mContext, iconPath);
				BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromBitmap(iconBitmap);
				
				mapMarkerOverlay.getMarker().setIcon(markerDescriptor);
			}
			
			if (markerOverlayOptions.getBubbleTitle() != null) {

				
				boolean isUse = markerOverlayOptions.isiUseYOffset();
				int yOffset = markerOverlayOptions.getyOffset();
				String title = markerOverlayOptions.getBubbleTitle();
				String subTitle = markerOverlayOptions.getBubbleSubTitle();
				String bgImgPath = markerOverlayOptions.getBubbleBgImgPath();

				
				mapMarkerOverlay.setBubbleViewData(title, subTitle, bgImgPath, yOffset, isUse);
				
			}
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void showBubble(String markerId) {
		
		try {
			
			EBaiduMapMarkerOverlay mapMarkerOverlay = (EBaiduMapMarkerOverlay) mEbaiduMapOverlays.get(markerId);
			
			if (mapMarkerOverlay == null) {
				
				return;
			}
			
			mapMarkerOverlay.setBubbleShow(true);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

    public void hideBubble() {

        try {

            mBaiduMap.hideInfoWindow();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
		
	
	public void clearMapOverLayMgr() {

        for (String s : mEbaiduMapOverlays.keySet()) {

        	EBaiduMapOverlay eBaiduMapOverlay = mEbaiduMapOverlays.get(s);
        	eBaiduMapOverlay.clearOverlay();
        }
	}

    @Override
    public boolean onMarkerClick(Marker arg0) {
        // TODO Auto-generated method stub

        EBaiduMapBaseActivity activity;

        activity = (EBaiduMapBaseActivity)mContext;

        if (activity != null) {

            EUExBaiduMap uexBaiduMap = activity.getUexBaseObj();

            Bundle b = arg0.getExtraInfo();

            String markerId =  (String)b.get(EBaiduMapUtils.MAP_PARAMS_JSON_KEY_ID);



            String js = EUExBaiduMap.SCRIPT_HEADER + "if(" + EBaiduMapUtils.MAP_FUN_ON_MAKER_CLICK_LISTNER + "){" + EBaiduMapUtils.MAP_FUN_ON_MAKER_CLICK_LISTNER + "('"
                    + markerId
                    + "');}";
            uexBaiduMap.onCallback(js);

            return true;
        }

        return false;
    }
    
	public void addDotOverlay(String dotInfo) {
		try {
			EBaiduMapDotOptions info = EBaiduMapUtils.parseDotInfoJson(dotInfo);
			if(info == null || mEbaiduMapOverlays.containsKey(info.getIdStr())) {
				return;
			}
			int fillColor = BUtility.parseColor(info.getFillColor());
			int radius = (int)Float.parseFloat(info.getRadius());
			DotOptions  dotOptions = new DotOptions();
			dotOptions.center(info.getLatLng());
			dotOptions.color(fillColor);
			dotOptions.radius(radius);
			if (info.getVisibleStr() != null) {
				dotOptions.visible(Boolean.parseBoolean(info.getVisibleStr()));
			}
			if (info.getzIndexStr() != null) {
				dotOptions.zIndex((int)Float.parseFloat(info.getzIndexStr()));
			}
			Dot dot = (Dot) mBaiduMap.addOverlay(dotOptions);
			if (info.getExtraStr() !=  null) {
				Bundle b = new Bundle();
				b.putString(info.getIdStr(), info.getExtraStr());
				dot.setExtraInfo(b);
			}
			EBaiduMapDotOverlay eBaiduMapDotOverlay = new EBaiduMapDotOverlay(info.getIdStr(), mContext, mBaiduMap);
			eBaiduMapDotOverlay.setDot(dot);
			mEbaiduMapOverlays.put(info.getIdStr(), eBaiduMapDotOverlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeOverlay(String overlayInfo) {
		String[] names = overlayInfo.split(",");
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			EBaiduMapOverlay overlay = mEbaiduMapOverlays.get(name);
			if (overlay != null) {
				overlay.clearOverlay();
				mEbaiduMapOverlays.remove(name);
			}
		}
	}

	public void addPolylineOverlay(String polylineInfo) {
		try {
			EBaiduMapPolylineOptions info = EBaiduMapUtils.parseLineInfoJson(polylineInfo);
			if(info == null || mEbaiduMapOverlays.containsKey(info.getIdStr())) {
				return;
			}
			int fillColor = BUtility.parseColor(info.getFillColor());
			int lineWidth = (int)Float.parseFloat(info.getLineWidth());
			PolylineOptions polylineOptions = new PolylineOptions().points(info.getList()).color(fillColor).width(lineWidth);
			if (info.getVisibleStr() != null) {
				polylineOptions.visible(Boolean.parseBoolean(info.getVisibleStr()));
			}
			if (info.getzIndexStr() != null) {
				polylineOptions.zIndex((int) Float.parseFloat(info.getzIndexStr()));
			}
			Polyline polyline = (Polyline) mBaiduMap.addOverlay(polylineOptions);
			if (info.getExtraStr() != null) {
				Bundle b = new Bundle();
				b.putString(info.getIdStr(), info.getExtraStr());
				polyline.setExtraInfo(b);
			}
			EBaiduMapPolylineOverlay eBaiduMapPolylineOverlay = new EBaiduMapPolylineOverlay(info.getIdStr(), mContext, mBaiduMap);
			eBaiduMapPolylineOverlay.setPolyline(polyline);
			mEbaiduMapOverlays.put(info.getIdStr(), eBaiduMapPolylineOverlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addArcOverlay(String arcInfo) {
		try {
			EBaiduMapArcOptions info = EBaiduMapUtils.parseArcInfoJson(arcInfo);
			if(info == null || mEbaiduMapOverlays.containsKey(info.getIdStr())) {
				return;
			}
			int strokeColor = BUtility.parseColor(info.getStrokeColor());
			int lineWidth = (int)Float.parseFloat(info.getLineWidth());
			ArcOptions arcOptions = new ArcOptions().points(info.getStart(), info.getCenter(), info.getEnd()).color(strokeColor).width(lineWidth);
			if (info.getVisibleStr() != null) {
				arcOptions.visible(Boolean.parseBoolean(info.getVisibleStr()));
			}
			if (info.getzIndexStr() != null) {
				arcOptions.zIndex((int) Float.parseFloat(info.getzIndexStr()));
			}
			Arc arc = (Arc) mBaiduMap.addOverlay(arcOptions);
			if (info.getExtraStr() != null) {
				Bundle b = new Bundle();
				b.putString(info.getIdStr(), info.getExtraStr());
				arc.setExtraInfo(b);
			}
			EBaiduMapArcOverlay eBaiduMapArcOverlay = new EBaiduMapArcOverlay(info.getIdStr(), mContext, mBaiduMap);
			eBaiduMapArcOverlay.setArc(arc);
			mEbaiduMapOverlays.put(info.getIdStr(), eBaiduMapArcOverlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCircleOverlay(String circleInfo) {
		try {
			EBaiduMapCircleOptions info = EBaiduMapUtils.parseCircleInfoJson(circleInfo);
			if(info == null || mEbaiduMapOverlays.containsKey(info.getIdStr())) {
				return;
			}
			int radius = (int)Float.parseFloat(info.getRadius());
			int strokeColor = BUtility.parseColor(info.getStrokeColor());
			int lineWidth = (int)Float.parseFloat(info.getLineWidth());
			int fillColor = BUtility.parseColor(info.getFillColor());
			Stroke stroke = new Stroke(lineWidth, strokeColor);
			CircleOptions circleOptions = new CircleOptions().center(info.getCenterPoint()).fillColor(fillColor).radius(radius).stroke(stroke);
			if (info.getVisibleStr() != null) {
				circleOptions.visible(Boolean.parseBoolean(info.getVisibleStr()));
			}
			if (info.getzIndexStr() != null) {
				circleOptions.zIndex((int) Float.parseFloat(info.getzIndexStr()));
			}
			Circle circle = (Circle) mBaiduMap.addOverlay(circleOptions);
			if (info.getExtraStr() != null) {
				Bundle b = new Bundle();
				b.putString(info.getIdStr(), info.getExtraStr());
				circle.setExtraInfo(b);
			}
			EBaiduMapCircleOverlay eBaiduMapCircleOverlay  = new EBaiduMapCircleOverlay(info.getIdStr(), mContext, mBaiduMap);
			eBaiduMapCircleOverlay.setCircle(circle);
			mEbaiduMapOverlays.put(info.getIdStr(), eBaiduMapCircleOverlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPolygonOverlay(String polygonInfo) {
		try {
			EBaiduMapPolygonOptions info = EBaiduMapUtils.parasePolygonInfoJson(polygonInfo);
			if(info == null || mEbaiduMapOverlays.containsKey(info.getIdStr())) {
				return;
			}
			int fillColor = BUtility.parseColor(info.getFillColor());
			int strokeColor = BUtility.parseColor(info.getStrokeColor());
			int lineWidth = (int)Float.parseFloat(info.getLineWidth());
			Stroke stroke = new Stroke(lineWidth, strokeColor);
			PolygonOptions polygonOptions = new PolygonOptions().points(info.getList()).fillColor(fillColor).stroke(stroke);
			if (info.getVisibleStr() != null) {
				polygonOptions.visible(Boolean.parseBoolean(info.getVisibleStr()));
			}
			if (info.getzIndexStr() != null) {
				polygonOptions.zIndex((int) Float.parseFloat(info.getzIndexStr()));
			}
			Polygon polygon = (Polygon) mBaiduMap.addOverlay(polygonOptions);
			if (info.getExtraStr() != null) {
				Bundle b = new Bundle();
				b.putString(info.getIdStr(),info.getExtraStr());
				polygon.setExtraInfo(b);
			}
			EBaiduMapPolygonOverlay eBaiduMapPolygonOverlay = new EBaiduMapPolygonOverlay(info.getIdStr(), mContext, mBaiduMap);
			eBaiduMapPolygonOverlay.setPolygon(polygon);
			mEbaiduMapOverlays.put(info.getIdStr(), eBaiduMapPolygonOverlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addGroundOverlay(String groundInfo) {
		try {
			final EBaiduMapGroundOptions info = EBaiduMapUtils.parseGroundInfoJson(groundInfo);
			if(info == null) {
				return;
			}
			final Bitmap bitmap = EBaiduMapUtils.getImage(mContext,info.getImageUrl());
			((Activity)mContext).runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					if(bitmap == null || mEbaiduMapOverlays.containsKey(info.getIdStr())) {
						return;
					}
					GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
					if(info.getList().size() == 1) {
						LatLng lng = info.getList().get(0);
						groundOverlayOptions.position(lng);
						if(info.getGroundWidth() == null) {
							return;
						}
						if(info.getGroundHeight() != null) {
							groundOverlayOptions.dimensions((int)Float.parseFloat(info.getGroundWidth()), (int)Float.parseFloat(info.getGroundHeight()));
						}else {
							groundOverlayOptions.dimensions((int)Float.parseFloat(info.getGroundWidth()));
						}
					}
					if(info.getList().size() == 2) {
						Builder builder = new LatLngBounds.Builder();
						builder.include(info.getList().get(0));
						builder.include(info.getList().get(1));
						LatLngBounds bounds = builder.build();
						groundOverlayOptions.positionFromBounds(bounds);
					}
					groundOverlayOptions.image(BitmapDescriptorFactory.fromBitmap(bitmap));
					bitmap.recycle();
					groundOverlayOptions.transparency(Float.parseFloat(info.getTransparency()));
					if (info.getVisibleStr() != null) {
						groundOverlayOptions.visible(Boolean.parseBoolean(info.getVisibleStr()));
					}
					if (info.getzIndexStr() != null) {
						groundOverlayOptions.zIndex((int) Float.parseFloat(info.getzIndexStr()));
					}
					GroundOverlay groundOverlay = (GroundOverlay) mBaiduMap.addOverlay(groundOverlayOptions);
					if (info.getExtraStr() != null) {
						Bundle b = new Bundle();
						b.putString(info.getIdStr(), info.getExtraStr());
						groundOverlay.setExtraInfo(b);
					}
					EBaiduMapGroundOverlay eBaiduMapGroundOverlay = new EBaiduMapGroundOverlay(info.getIdStr(), mContext, mBaiduMap);
					eBaiduMapGroundOverlay.setGroundOverlay(groundOverlay);
					mEbaiduMapOverlays.put(info.getIdStr(), eBaiduMapGroundOverlay);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addTextOverlay(String textInfo) {
		try {
			EBaiduMapTextOptions info = EBaiduMapUtils.paraseTextInfo(textInfo);
			if(mEbaiduMapOverlays.containsKey(info.getIdStr())) {
				return;
			}
			TextOptions textOptions = new TextOptions();
			textOptions.position(info.getLatLng());
			textOptions.fontSize((int) Float.parseFloat(info.getFontSize()));
			textOptions.text(info.getText());
			if (info.getBgColor() != null) {
				textOptions.bgColor(BUtility.parseColor(info.getBgColor()));
			}
			if (info.getFontColor() != null) {
				textOptions.fontColor(BUtility.parseColor(info.getFontColor()));
			}
			if (info.getRotate() != null) {
				textOptions.rotate(Float.parseFloat(info.getRotate()));
			}
			if (info.getVisibleStr() != null) {
				textOptions.visible(Boolean.parseBoolean(info.getVisibleStr()));
			}
			if (info.getzIndexStr() != null) {
				textOptions.zIndex((int) Float.parseFloat(info.getzIndexStr()));
			}
			Text text = (Text) mBaiduMap.addOverlay(textOptions);
			if (info.getExtraStr() != null) {
				Bundle b = new Bundle();
				b.putString(info.getIdStr(), info.getExtraStr());
				text.setExtraInfo(b);
			}
			EBaiduMapTextOverlay eBaiduMapTextOverlay = new EBaiduMapTextOverlay(info.getIdStr(), mContext, mBaiduMap);
			eBaiduMapTextOverlay.setText(text);
			mEbaiduMapOverlays.put(info.getIdStr(), eBaiduMapTextOverlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
