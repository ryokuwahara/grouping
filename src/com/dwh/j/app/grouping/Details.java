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

	// グループ数
	private int grupCount;
	// 不足人数
	private int fusoku;
	// グループ毎数（結果）
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

		setTitle("チーム分け結果");

		// 前画面よりデータセット
		if (getIntent().getExtras() != null) {
			listattend = getIntent().getExtras().getStringArrayList(
					Const.HMAP_ATTEND_NAME);
			grupPerCount = getIntent().getExtras().getInt(
					Const.HMAP_TEAM_PER_COUNT);
		}

		// 配列をシャッフル
		Collections.shuffle(listattend);
		DispList();

	}

	// リスト表示
	public void DispList() {
		listTeamWk.clear();
		// チーム数計算
		CalcTeam(listattend.size(), grupPerCount);
		// リストにデータをセット
		int cnt = 0;
		int cnt2 = 0;
		for (int i = 1; i <= grupCount; i++) {
			// チームタイトル
			listTeamWk.add("【チーム" + i + "】");
			// チーム毎人数
			cnt2 = grupPerCountResult;
			// 不足分を引く
			if (fusoku > 0) {
				cnt2--;
				fusoku--;
			}
			// リストデータセット
			for (int j = 1; j <= cnt2; j++) {
				listTeamWk.add(j + ":" + listattend.get(cnt));
				cnt++;
			}
		}
		ArrayAdapter<String> at = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listTeamWk);
		listTeam.setAdapter(at);
	}

	// チーム数を計算する(チーム毎の人数の差を一までにする)
	public void CalcTeam(int ninzuu, int teamPerCount) {
		if (ninzuu <= teamPerCount) {
			// 人数 <= 基準人数の場合
			grupCount = 1;
			fusoku = 0;
			grupPerCountResult = ninzuu;
		} else {
			// チーム数
			grupCount = (int) Math.floor(ninzuu / teamPerCount);
			if (ninzuu % teamPerCount != 0) {
				grupCount++;
			}
			// 不足人数
			fusoku = (teamPerCount * grupCount) - ninzuu;
			grupPerCountResult = teamPerCount;
			// チーム数＜不足人数の場合
			if (grupCount < fusoku) {
				// チーム基準数をへらしてもう一度計算
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
