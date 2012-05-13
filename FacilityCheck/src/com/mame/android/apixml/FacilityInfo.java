package com.mame.android.apixml;

import java.io.IOException;


import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;

public class FacilityInfo extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// layout_set
		setContentView(R.layout.facility_list);

		String hotelNo = "hotelNo";
		try {
			Intent i = getIntent();
			// ���ɂ�����ł������l�������Ƃ��
			hotelNo = i.getStringExtra("HOTEL_NO");
			//Log.v("�m�F", hotelNo + "��hotelNo.�ł��B");

			String uri = "http://api.rakuten.co.jp/rws/3.0/rest?"
					+ "developerId=***"
					+ "&operation=HotelDetailSearch" + "&responseType=large"
					+ "&version=2009-09-09" + "&hotelNo=" + hotelNo;
			// -----[http�N���C�A���g�̐ݒ�]
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet();
			URL url;
			url = new URL(uri);
			URLConnection urlcon;
			urlcon = url.openConnection();

			urlcon.setDoOutput(true);// POST�\�ɂ���
			InputStream is;

			is = urlcon.getInputStream();
			
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is, "UTF-8");

			int eventType;
			eventType = parser.getEventType();
			// �{�ݏ�������锠(list�o�[�W����)
			List<String> roomFacilities = new ArrayList<String>();
			while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
				// roomFacilities���������烊�X�g(����)�ɒǉ�
				if (eventType == XmlPullParser.START_TAG
						&& "item".equals(parser.getName())) {
					parser.next();
					//Log.v("Parse", parser.getText());
					roomFacilities.add(parser.getText());
				}
			}
			// �{�ݏ��\��
			ArrayAdapter<String> roomFacilities_list = new ArrayAdapter<String>(
					this, R.layout.view, roomFacilities);
			setListAdapter(roomFacilities_list);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
}
