package com.chaoba.calendarselecter;

import java.util.Calendar;
import java.util.Locale;

import com.chaoba.utils.Logger;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A view that let users select date
 * 
 * @author Liyanshun
 * 
 */
public class CalendarSelecterView extends RelativeLayout implements
		OnItemClickListener {

	private int mYear;
	private int mMonth;
	private OnDateSelectedListener mOnDateSelectedListener;
	private CalendarGridAdapter mDayOfMonthAdapter;
	private boolean mShowLastAndNextButton;
	private MonthChangeListenr mMonthChangeListenr;

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
	 * Register a callback to be invoked when last or next month button has been
	 * clicked.
	 * 
	 * @param l
	 *            The callback that will be invoked.
	 */
	public void setOnMonthChangeListenr(MonthChangeListenr l) {
		mMonthChangeListenr = l;
	}

	/**
	 * Register a callback to be invoked when a date has been clicked.
	 * 
	 * @param l
	 *            The callback that will be invoked.
	 */
	public void setOnDateSelectedListener(OnDateSelectedListener l) {
		mOnDateSelectedListener = l;
	}

	/**
	 * set if will show next and last month button
	 * 
	 * @param show
	 */
	public void showLastAndNextButton(boolean show) {
		mShowLastAndNextButton = show;
	}

	/**
	 * set the year and month which this view will show
	 * 
	 * @param year
	 * @param month
	 */
	public void setTime(int year, int month, boolean showOtherMonth) {
		mYear = year;
		mMonth = month;
		View layout = LayoutInflater.from(getContext()).inflate(
				R.layout.selecter_view, null, false);
		TextView currentMonthTextView = (TextView) layout
				.findViewById(R.id.current_month);
		currentMonthTextView.setText(mYear + "-" + mMonth);
		if (mShowLastAndNextButton) {
			ImageButton lastMonthButton = (ImageButton) layout
					.findViewById(R.id.last_month);
			lastMonthButton.setVisibility(View.VISIBLE);
			lastMonthButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mMonthChangeListenr != null) {
						mMonthChangeListenr.LastMonthClicked();
					}
				}
			});

			ImageButton nextMonthButton = (ImageButton) layout
					.findViewById(R.id.next_month);
			nextMonthButton.setVisibility(View.VISIBLE);
			nextMonthButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mMonthChangeListenr != null) {
						mMonthChangeListenr.nextMonthClicker();
					}
				}
			});
		}
		GridView dayOfWeekView = (GridView) layout
				.findViewById(R.id.day_of_week);
		DayOfWeekAdapter dayOfWeekAdapter = new DayOfWeekAdapter();
		dayOfWeekView.setAdapter(dayOfWeekAdapter);

		GridView dayOfMonthGridView = (GridView) layout
				.findViewById(R.id.day_of_month);
		mDayOfMonthAdapter = new CalendarGridAdapter(getContext(), year, month,
				showOtherMonth);
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
			convertView = View.inflate(getContext(),
					R.layout.day_of_week_grid_item, null);
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
		mDayOfMonthAdapter.setCheckedPosition(position);
		Logger.i("selected:" + mDayOfMonthAdapter.getSelectedDate(position));
		if (mOnDateSelectedListener != null) {
			mOnDateSelectedListener.OnDateSelected(mDayOfMonthAdapter
					.getSelectedDate(position));
		}
	}
}
