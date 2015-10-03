package com.se.helpp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpRetryException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DecompressingHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PostActivity extends Activity implements OnItemSelectedListener, OnClickListener {
	Intent intent;
	Spinner spinnerPost;
	TextView textViewAddress, textViewPhone, textViewDetails;
	EditText editTextDescription, editTextAddress, editTextPhone, editTextHeader;
	Button buttonPost = null;
	Button buttonMyPosts = null;
	String category = null;
	String header = null;
	String address = null;
	String details = null;
	String phone = null;
	private String[] state = { "Food", "Housing", "Clothing", "Furniture" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_by_charity);
		System.out.println(state.length);

		editTextDescription = (EditText) findViewById(R.id.editTextDescription);
		editTextAddress = (EditText) findViewById(R.id.editTextAddress);
		editTextPhone = (EditText) findViewById(R.id.editTextPhone);
		editTextHeader = (EditText) findViewById(R.id.editTextHeader);
		buttonPost = (Button) findViewById(R.id.buttonPost);
		buttonMyPosts = (Button) findViewById(R.id.buttonMyPosts);

		buttonPost.setOnClickListener(this);
		buttonMyPosts.setOnClickListener(this);

		spinnerPost = (Spinner) findViewById(R.id.spinnerPost);
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				state);
		adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPost.setAdapter(adapter_state);
		spinnerPost.setOnItemSelectedListener(this);

	}

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		spinnerPost.setSelection(position);

		String selState = (String) spinnerPost.getSelectedItem();
		System.out.println(selState);
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

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.buttonPost:
			category = (String) spinnerPost.getSelectedItem();
			header = editTextHeader.getText().toString().trim();
			address = editTextAddress.getText().toString().trim();
			details = editTextDescription.getText().toString().trim();
			phone = editTextPhone.getText().toString().trim();

			System.out.println(address);
			System.out.println(details);
			System.out.println(phone);

			System.out.println("Entered button post");
			// System.out.println("selectedStartTime"+selectedStartTime);
			if (editTextHeader == null) {
				Toast.makeText(getApplicationContext(), "Enter values in Header feild field!", Toast.LENGTH_SHORT)
						.show();
			} else if (editTextAddress == null) {
				Toast.makeText(getApplicationContext(), "Please enter the address!", Toast.LENGTH_SHORT).show();
			} else if (editTextDescription == null) {
				Toast.makeText(getApplicationContext(), "Please enter the description!", Toast.LENGTH_SHORT).show();
			} else if (editTextPhone == null) {
				Toast.makeText(getApplicationContext(), "Enter the phone number", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(PostActivity.this,
						"You selected : " + "\n" + String.valueOf(spinnerPost.getSelectedItem()) + "\n" + category
								+ "\n" + header + "\n" + address + "\n" + details + "\n" + phone,
						Toast.LENGTH_SHORT).show();

				new SendData().execute(category, header, address, details, phone);

				SendData omegaData = new SendData();
				omegaData.execute();
			}
			break;

		case R.id.buttonMyPosts:
			intent = new Intent("com.se.helpp.LISTCHARITYPOSTACTIVITY");
			startActivity(intent);
			break;

		}
	}

	private class SendData extends AsyncTask<String, String, String[]> {
		HttpClient httpClient;
		HttpResponse httpResponse;
		HttpPost httpPost;

		@SuppressWarnings("deprecation")
		@Override
		protected String[] doInBackground(String... params) {
			String category = params[0];
			String header = params[1];
			String Description = params[2];
			String Address = params[3];
			String Phone = params[4];

			String topostPHP = "category=" + category + "'&&" + "header='" + header + "'&&" + "description='"
					+ Description + "'&&" + "address='" + Address + "'&&" + "phone='" + Phone;
			System.out.println(topostPHP);
			httpClient = new DecompressingHttpClient();
			Log.i("ListCharityPostActivity - ", "Created httpClient");
			httpPost = new HttpPost("http://http://omega.uta.edu/~sas4798/food_post.php?" + topostPHP);

			System.out.println("wooooooooo hooo");

			try {
				httpResponse = httpClient.execute(httpPost);
			} catch (UnsupportedEncodingException e) {
				Log.e("PostActivity URL Encode - ", e.toString());
			} catch (IllegalArgumentException e) {
				Log.e("PostActivity Illegal Args - ", e.toString());
			} catch (org.apache.http.client.ClientProtocolException e) {
				Log.e("PostActivity Protocol - ", e.toString());
			} catch (HttpRetryException e) {
				Log.e("PostActivity Connection - ", e.toString());
			} catch (IOException e) {
				Log.e("CarOwnePostActivityrSetAvailableActivity IO - ", e.toString());
			}
			return null;
		}
	}
}
