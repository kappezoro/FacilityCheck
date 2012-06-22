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
	// aaa
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_list);
		String keyword = "KEYWORD";
		try {
			Intent i = getIntent();
			// iπC…C￢C!C±COC竕・†C!CΩilC・ｿC§COC竏・ECE
			keyword = i.getStringExtra("SEARCH");
			//Log.v("amiF", keyword + "COkeywordC竕・∑AB");
			String encodeKeyword = URLEncoder.encode(keyword, "UTF-8");

			String uri = "http://api.rakuten.co.jp/rws/3.0/rest?developerId=***"
					+ "&operation=KeywordHotelSearch"
					+ "&version=2009-10-20"
					+ "&keyword=" + encodeKeyword;
			// -----[httpENEaECEAEiEgCAe窶ｺiE]
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet();
			URL url;
			url = new URL(uri);
			URLConnection urlcon;
			urlcon = url.openConnection();
			urlcon.setDoOutput(true);// POSTa￢i\C…C∑CE

			InputStream is;
			is = urlcon.getInputStream();

			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is, "UTF-8");
			int eventType;
			eventType = parser.getEventType();

			// EzEeEai‘cUEaEXEg
			final ArrayList<String> hotelNo = new ArrayList<String>();
			// EzEeEanoEaEXEg
			ArrayList<String> hotelName = new ArrayList<String>();

			while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
				// hotelNoC・ｿacC￢COCΩCAA@hotelNoEaEXEgC…i≪a!
				if (eventType == XmlPullParser.START_TAG
						&& "hotelNo".equals(parser.getName())) {
					parser.next();
					//Log.v("Parse", parser.getText());
					hotelNo.add(parser.getText());
					// hotelNo[j]=parser.getText();
				}
				// hotelNameC・ｿacC￢COCΩCAhotelNameEaEXEgC…i≪a!
				if (eventType == XmlPullParser.START_TAG
						&& "hotelName".equals(parser.getName())) {
					parser.next();
					//Log.v("Parse", parser.getText());
					hotelName.add(parser.getText());
					// hotelName[j]=parser.getText();
				}
			}
			// EzEeEanoC・ｿi\e¶C∑CEEaEXEg
			ArrayAdapter<String> hotelName_list = new ArrayAdapter<String>(
					this, R.layout.view, hotelName);
			// EaEXEgC・ｿEZEbEg
			ListView listView = (ListView) findViewById(id.list);
			listView.setAdapter(hotelName_list);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ListView listView = (ListView) parent;
					// ENEaEbENC竕･CICΩEAECEeEAC・ｿeEia
					String item = (String) listView.getItemAtPosition(position);
					// E|EbEvEAEbEviIC≫C窶咾￢
					Toast.makeText(ShowHotel.this, item, Toast.LENGTH_LONG).show();
					//euCAaEnﾂꀀCOFacilityInfoCaCE
					Intent intent = new Intent(ShowHotel.this,
							FacilityInfo.class);
					// Log.i("ListViewSample", t.getText().toString() + position);
					// ENEaEbENCμCΩHotelNameCAEaEXEgCAi‘cUCAHotelNoC・ｿeEia
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