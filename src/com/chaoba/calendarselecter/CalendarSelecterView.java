package com.chaoba.calendarselecter;

import java.util.Calendar;
import java.util.Locale;

import com.chaoba.utils.Logger;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarSelecterView extends RelativeLayout implements OnItemClickListener {

	private int mYear;
	private int mMonth;
	private CalendarGridAdapter mDayOfMonthAdapter;

	public CalendarSelecterView(Context context) {
		super(context);
	}

	public CalendarSelecterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CalendarSelecterView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * set the year and month which this view will show
	 * 
	 * @param year
	 * @param month
	 */
	public void setTime(int year, int month) {
		mYear = year;
		mMonth = month;
		View layout = LayoutInflater.from(getContext()).inflate(
				R.layout.selecter_view, null, false);
		GridView dayOfWeekView = (GridView) layout.findViewById(R.id.day_of_week);
		DayOfWeekAdapter dayOfWeekAdapter = new DayOfWeekAdapter();
		dayOfWeekView.setAdapter(dayOfWeekAdapter);
		
		GridView dayOfMonthGridView = (GridView) layout.findViewById(R.id.day_of_month);
		mDayOfMonthAdapter = new CalendarGridAdapter(getContext(),
				year, month);
		dayOfMonthGridView.setAdapter(mDayOfMonthAdapter);
		dayOfMonthGridView.setOnItemClickListener(this);
		addView(layout);
	}

	class DayOfWeekAdapter extends BaseAdapter {
		private final String[] DAY_IN_WEEK = new String[7];

		public DayOfWeekAdapter() {

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			for (int i = 0; i < 7; i++) {
				DAY_IN_WEEK[i] = cal.getDisplayName(Calendar.DAY_OF_WEEK,
						Calendar.SHORT, Locale.getDefault());
				cal.add(Calendar.DAY_OF_WEEK, 1);
			}
		}

		@Override
		public int getCount() {
			return 7;
		}

		@Override
		public Object getItem(int position) {
			return DAY_IN_WEEK[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = View.inflate(getContext(), R.layout.day_of_week_grid_item, null);
			TextView t = (TextView) convertView
					.findViewById(R.id.grid_item_text);
			t.setText(DAY_IN_WEEK[position]);
			t.setTextAppearance(getContext(), R.style.DayOfWeekStyle);
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Logger.d(mDayOfMonthAdapter.getSelectedDate(position));
	}
}
