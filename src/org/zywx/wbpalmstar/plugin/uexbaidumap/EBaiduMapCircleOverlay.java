package org.zywx.wbpalmstar.plugin.uexbaidumap;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Circle;

public class EBaiduMapCircleOverlay extends EBaiduMapOverlay {

	private Circle circle = null;
	
	public EBaiduMapCircleOverlay(String id, Context context, BaiduMap baiduMap) {
		super(id, context, baiduMap);
	}
	
	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	@Override
	public void clearOverlay() {
		if(circle != null) {
			circle.remove();
		}
	}

}
