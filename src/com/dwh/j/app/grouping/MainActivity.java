package com.dwh.j.app.grouping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dwh.j.app.grouping2.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView listSeito;
	private Button bt1;
	private EditText editNinzu;
	
	//出席者一覧	
	private ArrayList<String> listattend = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("クラス名簿(欠席者をチェックして下さい)");

		setContentView(R.layout.activity_main);

		listSeito = (ListView) findViewById(R.id.listSeito);
		bt1 = (Button) findViewById(R.id.button1);
		editNinzu = (EditText) findViewById(R.id.editNinzu);

		
		ArrayAdapter<String> at = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, Const.LIST_SEITO);

		listSeito.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		listSeito.setAdapter(at);

		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (editNinzu.getText() == null || editNinzu.getText().equals("") ) {
					return;
				}
				
				SparseBooleanArray checked = listSeito.getCheckedItemPositions();
				listattend.clear();
				for (int i = 0; i < Const.LIST_SEITO.size(); i++) {
					if (checked.get(i) == false) {
						listattend.add(Const.LIST_SEITO.get(i));
					}
				}
				 Intent intent = new Intent(MainActivity.this, Details.class);
				 intent.putStringArrayListExtra(Const.HMAP_ATTEND_NAME, listattend);
				 intent.putExtra(Const.HMAP_TEAM_PER_COUNT, Integer.parseInt(editNinzu.getText().toString()));
					
				 startActivity(intent);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
