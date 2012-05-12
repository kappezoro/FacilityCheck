package com.mame.android.apixml;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HotelSearch extends Activity {
	private EditText keyword;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// layout_set
		setContentView(R.layout.main);

		// EditTe xtに出てくる値を抽出(自分で入力)
		keyword = (EditText) findViewById(R.id.text_search);

		// ボタン押したら次の画面だよ（検索結果）
		Button buttonShowResultSearch = (Button) findViewById(R.id.button_search_hotel);
		// 次の画面に何をだすか設定（検索結果）
		buttonShowResultSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(HotelSearch.this, ShowHotel.class);
				// 入力された値を付加情報に登録。インテントを受け取った次画面で自由に参照できる
				// 付加情報は文字列をキーとしたMapとして保持される
				intent.putExtra("SEARCH", keyword.getText().toString());
				startActivityForResult(intent, 0);
			}
		});

	}
	
	/*private TextView labelPreviousSearch;
	//result_display_override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		// 前回の結果を表示
		labelPreviousSearch = (TextView) findViewById(R.id.label_previous_hotel_search);
		if (resultCode == RESULT_OK) {
			//データへのアクセス、オブジェクトの取得
			SharedPreferences preferences = getSharedPreferences(
					"PREVIOUS_RESULT", MODE_PRIVATE);
			labelPreviousSearch.setText(String.valueOf(preferences.getInt(
					"PREVIOUS_SEARCH", 0)));
		}
	}*/
}