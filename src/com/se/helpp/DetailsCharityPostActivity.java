package com.se.helpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsCharityPostActivity extends Activity implements OnClickListener {
	TextView textOmegaHeader, textOmegaDescription, textOmegaAddress, textOmegaPhoneNumber;
	String stringOmegaHeader, stringOmegaDescription, stringOmegaAddress, stringOmegaPhoneNumber;
	Button buttonDeletePost;
	String omegaPostID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_charity_posts);

		textOmegaHeader = (TextView) findViewById(R.id.textOmegaHeader);
		textOmegaDescription = (TextView) findViewById(R.id.textOmegaDescription);
		textOmegaAddress = (TextView) findViewById(R.id.textOmegaAddress);
		textOmegaPhoneNumber = (TextView) findViewById(R.id.textOmegaPhoneNumber);

		buttonDeletePost = (Button) findViewById(R.id.buttonDeletePost);

		omegaPostID = getIntent().getStringExtra("textOmegaID");
		stringOmegaHeader = getIntent().getStringExtra("textOmegaHeader");
		stringOmegaDescription = getIntent().getStringExtra("textOmegaDescription");
		stringOmegaAddress = getIntent().getStringExtra("textOmegaAddress");
		stringOmegaPhoneNumber = getIntent().getStringExtra("textOmegaPhoneNumber");

		textOmegaHeader.setText(stringOmegaHeader);
		textOmegaDescription.setText(stringOmegaDescription);
		textOmegaAddress.setText(stringOmegaAddress);
		textOmegaPhoneNumber.setText(stringOmegaPhoneNumber);

		buttonDeletePost.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		Toast.makeText(getApplicationContext(), omegaPostID, Toast.LENGTH_LONG).show();
		Log.i("DetailsCharityPostActivity - ", "Post ID - " + omegaPostID);

		DeleteInOmega omegaData = new DeleteInOmega();
		try {
			String status = omegaData.execute(omegaPostID).get();
			if (status != null) {
				Log.i("DetailsCharityPostActivity - ", "Delete unsuccessful");
			} else {
				Log.i("DetailsCharityPostActivity - ", "Delete successful");
			}
		} catch (InterruptedException e) {
			Log.e("DetailsCharityPostActivity - ", "Delete Interrupted");
		} catch (ExecutionException e) {
			Log.e("DetailsCharityPostActivity - ", "Delete Execution unsuccessful");
		}
		
		Intent i = new Intent("com.se.helpp.POSTACTIVITY");
		startActivity(i);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	private class DeleteInOmega extends AsyncTask<String, String, String> {
		HttpClient httpClient;
		HttpResponse httpResponse;
		HttpPost httpPost;
		HttpEntity httpEntity;
		InputStream isr;
		BufferedReader bReader;
		String line;

		@Override
		protected String doInBackground(String... params) {
			// Create Http request and response objects to connect to Omega
			try {
				String postID = params[0];
				httpClient = new DefaultHttpClient();
				Log.i("DetailsCharityPostActivity - ", "Created httpClient");

				httpPost = new HttpPost("http://omega.uta.edu/~gxr7481/charity_delete.php?postid=" + postID);
				Log.i("DetailsCharityPostActivity - ", "Created httpPost to omega");

				httpResponse = httpClient.execute(httpPost);

				Log.i("DetailsCharityPostActivity - ", "Created httpResponse");

				httpEntity = httpResponse.getEntity();
				Log.i("DetailsCharityPostActivity - ", "Created httpEntity");
				if (httpEntity != null) {
					isr = httpEntity.getContent();
					Log.i("DetailsCharityPostActivity - ", "Availability of isr " + isr.available());
				}
			} catch (ClientProtocolException e) {
				Log.e("DetailsCharityPostActivity - ", "Error in ClientProtocolException - " + e.toString());
			} catch (IOException e) {
				Log.e("DetailsCharityPostActivity - ", "Error in IOException - " + e.toString());
			} catch (Exception e) {
				Log.e("DetailsCharityPostActivity - ", "Error in Connection - " + e.toString());
			}

			// Convert the data in InputStream to String
			try {
				bReader = new BufferedReader(new InputStreamReader(isr), 8);
				line = null;
				while ((line = bReader.readLine()) != null) {
					Log.i("DetailsCharityPostActivity - ", "Data from omega " + line);
				}
				return line;
			} catch (IOException e) {
				Log.e("DetailsCharityPostActivity - ", "Error in ISR to String conversion - " + e.toString());
				return null;
			}
		}
	}
}
