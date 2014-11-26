package org.zywx.wbpalmstar.plugin.uexbaidumap;

import org.zywx.wbpalmstar.engine.universalex.EUExUtil;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

public class EBaiduMapRoutePlanSearch implements OnGetRoutePlanResultListener {
	private String TAG = "EBaiduMapRoutePlanSearch";
	protected Context mContext;
	protected BaiduMap mBaiduMap;
	protected MapView mMapView;
	private RoutePlanSearch mRoutePlanSearch = null;
    private RouteLine mRouteLine = null; //保存路径数据的变量，供浏览节点时使用
	private int routeNodeIndex = -1; //路径节点索引,供浏览节点时使用

	public EBaiduMapRoutePlanSearch(Context context, BaiduMap baiduMap,
			MapView mapView) {
		mContext = context;
		mBaiduMap = baiduMap;
		mMapView = mapView;
		// 初始化搜索模块，注册搜索事件监听
		mRoutePlanSearch = RoutePlanSearch.newInstance();
		mRoutePlanSearch.setOnGetRoutePlanResultListener(this);
	}
	
    /**
     * 发起路线规划搜索
     * @param routePlanOptions
     */
    public void showRoutePlan(EBaiduMapRoutePlanOptions routePlanOptions) {
		Log.i(TAG, "showRoutePlan");
        //重置浏览节点的路线数据
        mRouteLine = null;
        mBaiduMap.clear();
        //设置起终点信息，对于tranist search 来说，城市名无意义
        PlanNode stNode = routePlanOptions.getStartNode();
        PlanNode enNode = routePlanOptions.getEndNode();

        // 处理搜索按钮响应
        // 实际使用中请对起点终点城市进行正确的设定
        switch (routePlanOptions.getType()) {
			case EBaiduMapRoutePlanOptions.PLAN_TYPE_DRIVE:
				mRoutePlanSearch.drivingSearch((new DrivingRoutePlanOption())
						.from(stNode).to(enNode));
				break;
			case EBaiduMapRoutePlanOptions.PLAN_TYPE_WALK:
				mRoutePlanSearch.walkingSearch((new WalkingRoutePlanOption())
						.from(stNode).to(enNode));
				break;
			case EBaiduMapRoutePlanOptions.PLAN_TYPE_TRANS:
				mRoutePlanSearch.transitSearch((new TransitRoutePlanOption())
						.from(stNode).to(enNode).city(routePlanOptions.getStartCity()));
				break;
    		default:
    			break;
		}
    }

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            routeNodeIndex = -1;
            mRouteLine = result.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            routeNodeIndex = -1;
            mRouteLine = result.getRouteLines().get(0);
            TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            routeNodeIndex = -1;
            mRouteLine = result.getRouteLines().get(0);
            WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
	}
	
    /**
     * 得到线路的上一个节点
     */
    public void preRouteNode() {
        if (routeNodeIndex == -1) {
        	return;
        }
        //设置节点索引
    	if (routeNodeIndex > 0) {
    		routeNodeIndex--;
        	showRouteNode();
    	} else {
        	return;
        }
    }
    
    /**
     * 得到线路的下一个节点
     */
    public void nextRouteNode() {
        //设置节点索引
        if (routeNodeIndex < mRouteLine.getAllStep().size() - 1) {
        	routeNodeIndex++;
        	showRouteNode();
        } else {
        	return;
        }
    }
	
    /**
     * 路径节点显示
     */
    private void showRouteNode() {
        if (mRouteLine == null ||
        		mRouteLine.getAllStep() == null) {
            return;
        }
        //获取节结果信息
        LatLng nodeLocation = null;
        String nodeTitle = null;
        Object step = mRouteLine.getAllStep().get(routeNodeIndex);
        if (step instanceof DrivingRouteLine.DrivingStep) {
            nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrace().getLocation();
            nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
        } else if (step instanceof WalkingRouteLine.WalkingStep) {
            nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrace().getLocation();
            nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
        } else if (step instanceof TransitRouteLine.TransitStep) {
            nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrace().getLocation();
            nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
        }

        if (nodeLocation == null || nodeTitle == null) {
            return;
        }
        //移动节点至中心
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
        // show popup
		TextView popupText = new TextView(mContext);
        popupText.setBackgroundResource(EUExUtil.getResDrawableID("plugin_map_bubble_bg_default"));
        popupText.setTextColor(0xFF000000);
        popupText.setText(nodeTitle);
        mBaiduMap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
    }

	public void destroy() {
		mRoutePlanSearch.destroy();
	}
}
