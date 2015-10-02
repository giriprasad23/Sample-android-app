package com.se.helpp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class HomeActivity extends Activity implements OnClickListener {
	Intent intent;
	Button refugee_User, charity_User;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		refugee_User = (Button) findViewById(R.id.launchRefugee);
		charity_User = (Button) findViewById(R.id.launchCharity);

		refugee_User.setOnClickListener(this);
		charity_User.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.launchRefugee:
			intent = new Intent("com.se.helpp.LOGINACTIVITY");
			intent.putExtra("UserType", "Refugee");
			startActivity(intent);
			break;

		case R.id.launchCharity:
			intent = new Intent("com.se.helpp.LOGINACTIVITY");
			intent.putExtra("UserType", "Charity");
			startActivity(intent);
			break;
		}
	}
}
