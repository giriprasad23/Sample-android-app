package com.se.helpp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {
	Button buttonLogIn = null;
	Button buttonSignUp = null;
	Button buttonForgotPassword = null;
	EditText editTextUserName = null;
	EditText editTextPassword = null;
	String userType = null;
	String username = null;
	String password = null;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		userType = getIntent().getExtras().getString("UserType");
		buttonLogIn = (Button) findViewById(R.id.buttonLogin);
		buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
		buttonForgotPassword = (Button) findViewById(R.id.buttonForgotPassword);
		editTextUserName = (EditText) findViewById(R.id.editTextUsername);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);

		buttonLogIn.setOnClickListener(this);
		buttonSignUp.setOnClickListener(this);
		buttonForgotPassword.setOnClickListener(this);
		userType = getIntent().getStringExtra("UserType");
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
		System.out.println(username);
		System.out.println(password);
		System.out.println(userType);
		if (password.equals("giri") && username.equals("giri") && userType.equals("Refugee")) {
			intent = new Intent("com.se.helpp.VIEWACTIVITY");
			startActivity(intent);
		} else if (password.equals("giri") && username.equals("giri") && userType.equals("Charity")) {
			intent = new Intent("com.se.helpp.POSTACTIVITY");
			startActivity(intent);
		} else {
			System.out.println("Wrong credentials");
		}
	}
}
