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
	// âÊñ ç\ê¨
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_list);
		String keyword = "KEYWORD";
		try {
			Intent i = getIntent();
			// ìπÇ…Ç¬Ç¡Ç±ÇÒÇ≈Ç†Ç¡ÇΩílÇÇ§ÇØÇ∆ÇÈÇÊ
			keyword = i.getStringExtra("SEARCH");
			//Log.v("ämîF", keyword + "ÇÕkeywordÇ≈Ç∑ÅB");
			String encodeKeyword = URLEncoder.encode(keyword, "UTF-8");

			String uri = "http://api.rakuten.co.jp/rws/3.0/rest?developerId=***"
					+ "&operation=KeywordHotelSearch"
					+ "&version=2009-10-20"
					+ "&keyword=" + encodeKeyword;
			// -----[httpÉNÉâÉCÉAÉìÉgÇÃê›íË]
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet();
			URL url;
			url = new URL(uri);
			URLConnection urlcon;
			urlcon = url.openConnection();
			urlcon.setDoOutput(true);// POSTâ¬î\Ç…Ç∑ÇÈ

			InputStream is;
			is = urlcon.getInputStream();

			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is, "UTF-8");
			int eventType;
			eventType = parser.getEventType();

			// ÉzÉeÉãî‘çÜÉäÉXÉg
			final ArrayList<String> hotelNo = new ArrayList<String>();
			// ÉzÉeÉãñºÉäÉXÉg
			ArrayList<String> hotelName = new ArrayList<String>();

			while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
				// hotelNoÇå©Ç¬ÇØÇΩÇÁÅ@hotelNoÉäÉXÉgÇ…í«â¡
				if (eventType == XmlPullParser.START_TAG
						&& "hotelNo".equals(parser.getName())) {
					parser.next();
					//Log.v("Parse", parser.getText());
					hotelNo.add(parser.getText());
					// hotelNo[j]=parser.getText();
				}
				// hotelNameÇå©Ç¬ÇØÇΩÇÁhotelNameÉäÉXÉgÇ…í«â¡
				if (eventType == XmlPullParser.START_TAG
						&& "hotelName".equals(parser.getName())) {
					parser.next();
					//Log.v("Parse", parser.getText());
					hotelName.add(parser.getText());
					// hotelName[j]=parser.getText();
				}
			}
			// ÉzÉeÉãñºÇï\é¶Ç∑ÇÈÉäÉXÉg
			ArrayAdapter<String> hotelName_list = new ArrayAdapter<String>(
					this, R.layout.view, hotelName);
			// ÉäÉXÉgÇÉZÉbÉg
			ListView listView = (ListView) findViewById(id.list);
			listView.setAdapter(hotelName_list);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ListView listView = (ListView) parent;
					// ÉNÉäÉbÉNÇ≥ÇÍÇΩÉAÉCÉeÉÄÇéÊìæ
					String item = (String) listView.getItemAtPosition(position);
					// É|ÉbÉvÉAÉbÉvìIÇ»Ç‚Ç¬
					Toast.makeText(ShowHotel.this, item, Toast.LENGTH_LONG).show();
					//éüÇÃâÊñ ÇÕFacilityInfoÇæÇÊ
					Intent intent = new Intent(ShowHotel.this,
							FacilityInfo.class);
					// Log.i("ListViewSample", t.getText().toString() + position);
					// ÉNÉäÉbÉNÇµÇΩHotelNameÇÃÉäÉXÉgÇÃî‘çÜÇÃHotelNoÇéÊìæ
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