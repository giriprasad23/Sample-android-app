package com.se.helpp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	Button buttonLogIn = null;
	Button buttonSignUp = null;
	Button buttonForgotPassword = null;
	EditText editTextUserName = null;
	EditText editTextPassword = null;
	// String userType = null;
	String username = null;
	String password = null;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// userType = getIntent().getExtras().getString("UserType");
		buttonLogIn = (Button) findViewById(R.id.buttonLogin);
		buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
		buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);
		editTextUserName = (EditText) findViewById(R.id.editTextUsername);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);

		buttonLogIn.setOnClickListener(this);
		buttonSignUp.setOnClickListener(this);
		buttonForgotPassword.setOnClickListener(this);
		// userType = getIntent().getStringExtra("UserType");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_help, menu);
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
	public void onClick(View view) {
		username = editTextUserName.getText().toString().trim();
		password = editTextPassword.getText().toString();

		if (username.isEmpty()) {
			Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
		} else if (password.isEmpty()) {
			Toast.makeText(getApplicationContext(), "Enter the password!", Toast.LENGTH_SHORT).show();
		} else if (password.length() < 8
				|| !(password.contains("@") || password.contains("!") || password.contains("&"))) {
			Toast.makeText(getApplicationContext(),
					"Password length should contain minimum 8 characters and atleast one special symbol( @,&,!)!",
					Toast.LENGTH_LONG).show();
		} else {
			System.out.println(username);
			System.out.println(password);
			
			SharedPreferences sharedpreferences = getSharedPreferences(
					"MyData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedpreferences.edit();
			editor.putString("name", username);
			editor.putString("pass", password);
			editor.commit();
			
			// System.out.println(userType);
			if (password.equals("refugee@1234") && username.equals("refugee")) {
				intent = new Intent("com.se.helpp.LISTREFUGEEVIEWACTIVITY");
				startActivity(intent);
			} else if (password.equals("charity@1234") && username.equals("charity")) {
				intent = new Intent("com.se.helpp.POSTACTIVITY");
				startActivity(intent);
			} else {
				System.out.println("Wrong credentials");
			}

		}

	}
}
