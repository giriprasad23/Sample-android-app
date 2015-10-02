package com.se.helpp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class ListCharityPostActivity extends Activity implements OnItemClickListener {
	String[] values;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_charity_posts);

		final ListView listview = (ListView) findViewById(R.id.listViewCharityPosts);

		LinkToOmega omegaData = new LinkToOmega();
		try {
			values = omegaData.execute().get();
		} catch (InterruptedException e) {
			Log.e("ListCharityPostActivity - ", "Error in InterruptedException - " + e.toString());
		} catch (ExecutionException e) {
			Log.e("ListCharityPostActivity - ", "Error in ExecutionException - " + e.toString());
		}

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final String item = (String) parent.getItemAtPosition(position);
		Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();

		String dummyData = "Bread, Great Value Bread from Walmart. Available till October 4, 112 UTA Boulevard ERB 112 Arlington TX 76011, 1234567890";
		String[] sendData = dummyData.split(",");
		Intent i = new Intent(ListCharityPostActivity.this, DetailsCharityPostActivity.class);
		i.putExtra("textOmegaHeader", sendData[0]);
		i.putExtra("textOmegaDescription", sendData[1]);
		i.putExtra("textOmegaAddress", sendData[2]);
		i.putExtra("textOmegaPhoneNumber", sendData[3]);
		startActivity(i);
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	private class LinkToOmega extends AsyncTask<String, String, String[]> {
		HttpClient httpClient;
		HttpResponse httpResponse;
		HttpPost httpPost;
		HttpEntity httpEntity;
		InputStream isr;
		BufferedReader bReader;
		String line;
		String data[];

		@Override
		protected String[] doInBackground(String... params) {
			// Create Http request and response objects to connect to Omega
			try {
				httpClient = new DefaultHttpClient();
				Log.i("ListCharityPostActivity - ", "Created httpClient");

				httpPost = new HttpPost("http://omega.uta.edu/~sas4798/food.php");
				Log.i("ListCharityPostActivity - ", "Created httpPost to omega");

				httpResponse = httpClient.execute(httpPost);
				Log.i("ListCharityPostActivity - ", "Created httpResponse");

				httpEntity = httpResponse.getEntity();
				Log.i("ListCharityPostActivity - ", "Created httpEntity");
				if (httpEntity != null) {
					isr = httpEntity.getContent();
					Log.i("ListCharityPostActivity - ", "Availability of isr " + isr.available());
				}
			} catch (ClientProtocolException e) {
				Log.e("ListCharityPostActivity - ", "Error in ClientProtocolException - " + e.toString());
			} catch (IOException e) {
				Log.e("ListCharityPostActivity - ", "Error in IOException - " + e.toString());
			} catch (Exception e) {
				Log.e("ListCharityPostActivity - ", "Error in Connection - " + e.toString());
			}

			// Convert the data in InputStream to String
			try {
				bReader = new BufferedReader(new InputStreamReader(isr), 8);
				line = null;
				data = new String[] { " " };
				while ((line = bReader.readLine()) != null) {
					data = line.split("<br>");
					Log.i("ListCharityPostActivity - ", "Data from omega " + line);
				}
				for (int i = 0; i < data.length; i++) {
					Log.i("ListCharityPostActivity - ", "Data from omega converted to String [] " + data[i]);
				}
				return data;
			} catch (IOException e) {
				Log.e("ListCharityPostActivity - ", "Error in ISR to String conversion - " + e.toString());
				return null;
			}
		}
	}

}
