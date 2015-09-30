package com.se.helpp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PostActivity extends Activity implements OnItemSelectedListener {
	 Spinner spinnerPost;
	 TextView textViewAddress;
	 private String[] state = { "Food", "Housing", "Clothing", "Furnture"};

	  @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_post);
	  System.out.println(state.length);
	  textViewAddress = (TextView) findViewById(R.id.textViewAddress);
	  spinnerPost = (Spinner) findViewById(R.id.spinnerPost);
	  ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
	    android.R.layout.simple_spinner_item, state);
	  adapter_state
	    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  spinnerPost.setAdapter(adapter_state);
	  spinnerPost.setOnItemSelectedListener(this);

	  }

	  public void onItemSelected(AdapterView<?> parent, View view, int position,
	   long id) {
	  spinnerPost.setSelection(position);
	  String selState = (String) spinnerPost.getSelectedItem();
	  textViewAddress.setText("Selected Android OS:" + selState);
	 }

	  @Override
	 public void onNothingSelected(AdapterView<?> arg0) {
	  // TODO Auto-generated method stub

	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_helpp, menu);
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
