package com.chaoba.calendarselecter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout body=(RelativeLayout) findViewById(R.id.body);
		CalendarSelecterView view=new CalendarSelecterView(MainActivity.this);
		view.setTime(2015, 3);
		body.addView(view);
	}

}
