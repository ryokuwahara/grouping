package com.dwh.j.app.grouping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dwh.j.app.grouping2.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Details extends Activity {

	// �O���[�v��
	private int grupCount;
	// �s���l��
	private int fusoku;
	// �O���[�v�����i���ʁj
	private int grupPerCountResult;

	private int grupPerCount;

	private List<String> listattend;
	private List<String> listTeamWk;
	private ListView listTeam;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		listattend = new ArrayList<String>();
		listTeamWk = new ArrayList<String>();
		listTeam = (ListView) findViewById(R.id.listTeam);

		setTitle("�`�[����������");

		// �O��ʂ��f�[�^�Z�b�g
		if (getIntent().getExtras() != null) {
			listattend = getIntent().getExtras().getStringArrayList(
					Const.HMAP_ATTEND_NAME);
			grupPerCount = getIntent().getExtras().getInt(
					Const.HMAP_TEAM_PER_COUNT);
		}

		// �z����V���b�t��
		Collections.shuffle(listattend);
		DispList();

	}

	// ���X�g�\��
	public void DispList() {
		listTeamWk.clear();
		// �`�[�����v�Z
		CalcTeam(listattend.size(), grupPerCount);
		// ���X�g�Ƀf�[�^���Z�b�g
		int cnt = 0;
		int cnt2 = 0;
		for (int i = 1; i <= grupCount; i++) {
			// �`�[���^�C�g��
			listTeamWk.add("�y�`�[��" + i + "�z");
			// �`�[�����l��
			cnt2 = grupPerCountResult;
			// �s����������
			if (fusoku > 0) {
				cnt2--;
				fusoku--;
			}
			// ���X�g�f�[�^�Z�b�g
			for (int j = 1; j <= cnt2; j++) {
				listTeamWk.add(j + ":" + listattend.get(cnt));
				cnt++;
			}
		}
		ArrayAdapter<String> at = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listTeamWk);
		listTeam.setAdapter(at);
	}

	// �`�[�������v�Z����(�`�[�����̐l���̍�����܂łɂ���)
	public void CalcTeam(int ninzuu, int teamPerCount) {
		if (ninzuu <= teamPerCount) {
			// �l�� <= ��l���̏ꍇ
			grupCount = 1;
			fusoku = 0;
			grupPerCountResult = ninzuu;
		} else {
			// �`�[����
			grupCount = (int) Math.floor(ninzuu / teamPerCount);
			if (ninzuu % teamPerCount != 0) {
				grupCount++;
			}
			// �s���l��
			fusoku = (teamPerCount * grupCount) - ninzuu;
			grupPerCountResult = teamPerCount;
			// �`�[�������s���l���̏ꍇ
			if (grupCount < fusoku) {
				// �`�[��������ւ炵�Ă�����x�v�Z
				CalcTeam(ninzuu, teamPerCount - 1);
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
