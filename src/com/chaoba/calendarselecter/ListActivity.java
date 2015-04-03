package com.chaoba.calendarselecter;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView listView = new ListView(this);
		listView.setAdapter(new CalendarListAdapter());
		setContentView(listView);
	}

	class CalendarListAdapter extends BaseAdapter {
		CalendarSelecterView[] views = new CalendarSelecterView[5];
		private Calendar mCalendar;

		public CalendarListAdapter() {
			for (int i = 0; i < 5; i++) {
				views[i] = new CalendarSelecterView(getBaseContext());
			}
			mCalendar = Calendar.getInstance();
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CalendarSelecterView v = new CalendarSelecterView(getBaseContext());
			Calendar calendar = (Calendar) mCalendar.clone();
			calendar.add(Calendar.MONTH, -(position));
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			v.setTime(year, month, false);
			return v;
		}
	}

}
