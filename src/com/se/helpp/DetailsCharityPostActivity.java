package com.se.helpp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsCharityPostActivity extends Activity {
	TextView textOmegaHeader, textOmegaDescription, textOmegaAddress, textOmegaPhoneNumber;
	String stringOmegaHeader, stringOmegaDescription, stringOmegaAddress, stringOmegaPhoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_charity_posts);

		textOmegaHeader = (TextView) findViewById(R.id.textOmegaHeader);
		textOmegaDescription = (TextView) findViewById(R.id.textOmegaDescription);
		textOmegaAddress = (TextView) findViewById(R.id.textOmegaAddress);
		textOmegaPhoneNumber = (TextView) findViewById(R.id.textOmegaPhoneNumber);
		
		stringOmegaHeader = getIntent().getStringExtra("textOmegaHeader");
		stringOmegaDescription = getIntent().getStringExtra("textOmegaDescription");
		stringOmegaAddress = getIntent().getStringExtra("textOmegaAddress");
		stringOmegaPhoneNumber = getIntent().getStringExtra("textOmegaPhoneNumber");
		
		textOmegaHeader.setText(stringOmegaHeader);
		textOmegaDescription.setText(stringOmegaDescription);
		textOmegaAddress.setText(stringOmegaAddress);
		textOmegaPhoneNumber.setText(stringOmegaPhoneNumber);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_helpp, menu);
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
