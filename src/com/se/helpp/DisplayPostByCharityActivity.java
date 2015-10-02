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
public class DisplayPostByCharityActivity extends Activity implements OnItemClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_post_by_charity);

		final ListView listview = (ListView) findViewById(R.id.listViewCharityPosts);
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile", "Blackberry", "WebOS", "Ubuntu", };

		LinkToOmega abc = new LinkToOmega();
		try {
			values = abc.execute().get();
		} catch (InterruptedException e) {
			Log.e("DisplayPostByCharityActivity - ", "Error in InterruptedException - " + e.toString());
		} catch (ExecutionException e) {
			Log.e("DisplayPostByCharityActivity - ", "Error in ExecutionException - " + e.toString());
		}

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);

		// listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
		// {
		//
		// @SuppressLint("NewApi")
		// @Override
		// public void onItemClick(AdapterView<?> parent, final View view, int
		// position, long id) {
		// final String item = (String) parent.getItemAtPosition(position);
		// // view.animate().setDuration(2000).alpha(0)
		// // .withEndAction(new Runnable() {
		// // @Override
		// // public void run() {
		// // list.remove(item);
		// // adapter.notifyDataSetChanged();
		// // view.setAlpha(1);
		// // }
		// // });
		// }
		//
		// });
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final String item = (String) parent.getItemAtPosition(position);
		Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
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
				Log.i("DisplayPostByCharityActivity - ", "Created httpClient");

				httpPost = new HttpPost("http://omega.uta.edu/~sas4798/food.php");
				Log.i("DisplayPostByCharityActivity - ", "Created httpPost to omega");

				httpResponse = httpClient.execute(httpPost);
				Log.i("DisplayPostByCharityActivity - ", "Created httpResponse");

				httpEntity = httpResponse.getEntity();
				Log.i("DisplayPostByCharityActivity - ", "Created httpEntity");
				if (httpEntity != null) {
					isr = httpEntity.getContent();
					Log.i("DisplayPostByCharityActivity - ", "Availability of isr " + isr.available());
				}
			} catch (ClientProtocolException e) {
				Log.e("DisplayPostByCharityActivity - ", "Error in ClientProtocolException - " + e.toString());
			} catch (IOException e) {
				Log.e("DisplayPostByCharityActivity - ", "Error in IOException - " + e.toString());
			} catch (Exception e) {
				Log.e("DisplayPostByCharityActivity - ", "Error in Connection - " + e.toString());
			}

			// Convert the data in InputStream to String
			try {
				bReader = new BufferedReader(new InputStreamReader(isr), 8);
				line = null;
				data = new String[] { " " };
				while ((line = bReader.readLine()) != null) {
					data = line.split("<br>");
					Log.i("DisplayPostByCharityActivity - ", "Data from omega " + line);
				}
				for (int i = 0; i < data.length; i++) {
					Log.i("DisplayPostByCharityActivity - ", "Data from omega converted to String [] " + data[i]);
				}
				return data;
			} catch (IOException e) {
				Log.e("DisplayPostByCharityActivity - ", "Error in ISR to String conversion - " + e.toString());
				return null;
			}
		}
	}

}
