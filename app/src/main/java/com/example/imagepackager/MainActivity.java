package com.example.imagepackager;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button Main_Button;
	Spinner Condition_Select;
	EditText key;
	public static final String destination =
            Environment.getExternalStorageDirectory().getPath() + "/Pictures/ImagePackager";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Main_Button = (Button) findViewById(R.id.Main_Button);
		Condition_Select = (Spinner) findViewById(R.id.Condition_Select);
		key = (EditText) findViewById(R.id.keyword);
		
		// if the directory is not created
		File file = new File(destination);
		if ( file != null){
			Boolean check = file.mkdir();
			if( check )
				Toast.makeText(this, destination + "가 생성되었습니다.", Toast.LENGTH_SHORT);
		}

		Main_Button.setOnClickListener(new OnClickListener() {
			// onClick
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Grid.class);
				// send filename to Grid
				final String keyword = key.getText().toString();
				intent.putExtra("keyword", keyword);
				startActivity(intent);
			}
		});

		ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
				R.array.Condition,
				android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Condition_Select.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
