package org.zywx.wbpalmstar.plugin.uexbaidumap;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Dot;

public class EBaiduMapDotOverlay extends EBaiduMapOverlay {

	private Dot dot = null;
	public EBaiduMapDotOverlay(String id, Context context, BaiduMap baiduMap) {
		super(id, context, baiduMap);
	}

	public void setDot(Dot dot) {
		this.dot = dot;
	}

	@Override
	public void clearOverlay() {
		if(dot != null) {
			dot.remove();
		}
	}

}
