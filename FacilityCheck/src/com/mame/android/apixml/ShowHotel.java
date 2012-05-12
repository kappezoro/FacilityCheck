package com.mame.android.apixml;

import java.io.IOException;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.id;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowHotel extends Activity {
	/** Called when the activity is first created. */
	@Override
	// 画面構成
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_list);
		String keyword = "KEYWORD";
		try {
			Intent i = getIntent();
			// 道につっこんであった値をうけとるよ
			keyword = i.getStringExtra("SEARCH");
			//Log.v("確認", keyword + "はkeywordです。");
			String encodeKeyword = URLEncoder.encode(keyword, "UTF-8");

			String uri = "http://api.rakuten.co.jp/rws/3.0/rest?developerId=c49629cf93f27be23c20d2f7af1086a5"
					+ "&operation=KeywordHotelSearch"
					+ "&version=2009-10-20"
					+ "&keyword=" + encodeKeyword;
			// -----[httpクライアントの設定]
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet();
			URL url;
			url = new URL(uri);
			URLConnection urlcon;
			urlcon = url.openConnection();
			urlcon.setDoOutput(true);// POST可能にする

			InputStream is;
			is = urlcon.getInputStream();

			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is, "UTF-8");
			int eventType;
			eventType = parser.getEventType();

			// ホテル番号リスト
			final ArrayList<String> hotelNo = new ArrayList<String>();
			// ホテル名リスト
			ArrayList<String> hotelName = new ArrayList<String>();

			while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
				// hotelNoを見つけたら　hotelNoリストに追加
				if (eventType == XmlPullParser.START_TAG
						&& "hotelNo".equals(parser.getName())) {
					parser.next();
					//Log.v("Parse", parser.getText());
					hotelNo.add(parser.getText());
					// hotelNo[j]=parser.getText();
				}
				// hotelNameを見つけたらhotelNameリストに追加
				if (eventType == XmlPullParser.START_TAG
						&& "hotelName".equals(parser.getName())) {
					parser.next();
					//Log.v("Parse", parser.getText());
					hotelName.add(parser.getText());
					// hotelName[j]=parser.getText();
				}
			}
			// ホテル名を表示するリスト
			ArrayAdapter<String> hotelName_list = new ArrayAdapter<String>(
					this, R.layout.view, hotelName);
			// リストをセット
			ListView listView = (ListView) findViewById(id.list);
			listView.setAdapter(hotelName_list);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ListView listView = (ListView) parent;
					// クリックされたアイテムを取得
					String item = (String) listView.getItemAtPosition(position);
					// ポップアップ的なやつ
					Toast.makeText(ShowHotel.this, item, Toast.LENGTH_LONG).show();
					//次の画面はFacilityInfoだよ
					Intent intent = new Intent(ShowHotel.this,
							FacilityInfo.class);
					// Log.i("ListViewSample", t.getText().toString() + position);
					// クリックしたHotelNameのリストの番号のHotelNoを取得
					intent.putExtra("HOTEL_NO", hotelNo.get(position));
					startActivityForResult(intent, 0);
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
}