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

		// EditTe xt�ɏo�Ă���l�𒊏o(�����œ���)
		keyword = (EditText) findViewById(R.id.text_search);

		// �{�^���������玟�̉�ʂ���i�������ʁj
		Button buttonShowResultSearch = (Button) findViewById(R.id.button_search_hotel);
		// ���̉�ʂɉ����������ݒ�i�������ʁj
		buttonShowResultSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(HotelSearch.this, ShowHotel.class);
				// ���͂��ꂽ�l��t�����ɓo�^�B�C���e���g���󂯎��������ʂŎ��R�ɎQ�Ƃł���
				// �t�����͕�������L�[�Ƃ���Map�Ƃ��ĕێ������
				intent.putExtra("SEARCH", keyword.getText().toString());
				startActivityForResult(intent, 0);
			}
		});

	}
	
	/*private TextView labelPreviousSearch;
	//result_display_override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		// �O��̌��ʂ�\��
		labelPreviousSearch = (TextView) findViewById(R.id.label_previous_hotel_search);
		if (resultCode == RESULT_OK) {
			//�f�[�^�ւ̃A�N�Z�X�A�I�u�W�F�N�g�̎擾
			SharedPreferences preferences = getSharedPreferences(
					"PREVIOUS_RESULT", MODE_PRIVATE);
			labelPreviousSearch.setText(String.valueOf(preferences.getInt(
					"PREVIOUS_SEARCH", 0)));
		}
	}*/
}