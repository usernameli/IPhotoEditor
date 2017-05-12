package com.liu.adserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class webViewActivity extends Activity {
	MapView mMapView = null;
	BaiduMap baiduMap;
	TextView jingdu;
	TextView weidu;
	Button queding;
	double jingdutext;
	double weidutext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
		// ע��÷���Ҫ��setContentView����֮ǰʵ��
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.web_act);
		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
		jingdu = (TextView) findViewById(R.id.act_jingdu);
		weidu = (TextView) findViewById(R.id.act_weidu);
		queding = (Button) findViewById(R.id.act_queding);
		queding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(webViewActivity.this,
						MainActivity.class);
				intent.putExtra("jingdu", jingdutext);
				intent.putExtra("weidu", weidutext);
				startActivity(intent);
			}
		});
		baiduMap = mMapView.getMap();

		OnMapClickListener listener = new OnMapClickListener() {
			/**
			 * ��ͼ�����¼��ص�����
			 * 
			 * @param point
			 *            ����ĵ�������
			 */
			public void onMapClick(LatLng point) {
				System.out.println(point.latitude + "  " + point.longitude);
				jingdu.setText("���ȣ�" + point.longitude);
				jingdutext = point.longitude;
				weidutext = point.latitude;
				baiduMap.clear();
				weidu.setText("γ��:" + point.latitude);
				// ����Maker�����
				// LatLng point = new LatLng(39.963175, 116.400244);
				// ����Markerͼ��
				BitmapDescriptor bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.loca);
				// ����MarkerOption�������ڵ�ͼ�����Marker
				OverlayOptions option = new MarkerOptions().position(point)
						.icon(bitmap);
				// �ڵ�ͼ�����Marker������ʾ
				baiduMap.addOverlay(option);
			}

			/**
			 * ��ͼ�� Poi �����¼��ص�����
			 * 
			 * @param poi
			 *            ����� poi ��Ϣ
			 */
			public boolean onMapPoiClick(MapPoi poi) {
				return false;
			}

		};
		baiduMap.setOnMapClickListener(listener);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}
}