package com.se.helpp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);
				}catch (InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openMainActivity = new Intent ("com.se.helpp.LOGINACTIVITY");
					startActivity(openMainActivity);
				}
			}
			
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
