package com.chaoba.calendarselecter;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaoba.utils.Logger;

public class CalendarGridAdapter extends BaseAdapter {
	private Calendar mCalendar;
	private ArrayList<Integer> mDaysArrayList = new ArrayList<Integer>();
	private ArrayList<Integer> mDaysTypeList = new ArrayList<Integer>();
	private Context mContext;
	private final static int DAYS_OF_LAST_MONTHS = 0;
	private final static int DAYS_OF_NEXT_MONTHS = 1;
	private final static int DAYS_OF_CURRENT_MONTH = 2;
	private int mYear;
	private int mMonth;
	private boolean mshowOtherMonth;
	private int mSelectedPosition;

	public void setCheckedPosition(int checkedPosition) {
		mSelectedPosition = checkedPosition;
		notifyDataSetChanged();
	}

	public CalendarGridAdapter(Context c, int year, int month,
			boolean showOtherMonth) {
		mContext = c;
		mYear = year;
		mMonth = month;
		mshowOtherMonth = showOtherMonth;
		mCalendar = Calendar.getInstance();
		mCalendar.set(Calendar.YEAR, year);
		mCalendar.set(Calendar.MONTH, month - 1);
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		int daySpacing = getDaySpacing(mCalendar.get(Calendar.DAY_OF_WEEK));
		Logger.d("daySpaceingBegin:" + daySpacing);
		if (daySpacing > 0) {
			Calendar lastMonth = (Calendar) mCalendar.clone();
			lastMonth.add(Calendar.MONTH, -1);
			lastMonth.set(Calendar.DAY_OF_MONTH,
					lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
							- daySpacing + 1);
			for (int i = 0; i < daySpacing; i++) {
				mDaysArrayList.add(lastMonth.get(Calendar.DAY_OF_MONTH));
				mDaysTypeList.add(DAYS_OF_LAST_MONTHS);
				lastMonth.add(Calendar.DAY_OF_MONTH, 1);
			}
		}

		int daysOfThisMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= daysOfThisMonth; i++) {
			// mCalendar.add(Calendar.DAY_OF_MONTH, 1);
			mDaysArrayList.add(i);
			mDaysTypeList.add(DAYS_OF_CURRENT_MONTH);
		}

		mCalendar.set(Calendar.DAY_OF_MONTH,
				mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		daySpacing = getDaySpacingEnd(mCalendar.get(Calendar.DAY_OF_WEEK));

		Logger.d("daySpacing end:" + daySpacing);
		if (daySpacing > 0) {
			Calendar nextMonth = (Calendar) mCalendar.clone();
			nextMonth.add(Calendar.MONTH, 1);
			nextMonth.set(Calendar.DAY_OF_MONTH, 1);
			for (int i = 0; i < daySpacing; i++) {
				mDaysArrayList.add(nextMonth.get(Calendar.DAY_OF_MONTH));
				mDaysTypeList.add(DAYS_OF_NEXT_MONTHS);
				nextMonth.add(Calendar.DAY_OF_MONTH, 1);
			}
		}

	}

	private int getDaySpacing(int dayOfWeek) {
		if (Calendar.SATURDAY == dayOfWeek)
			return 6;
		else
			return dayOfWeek - 1;
	}

	private int getDaySpacingEnd(int dayOfWeek) {
		return 7 - dayOfWeek;
	}

	@Override
	public int getCount() {
		return mDaysArrayList.size();
	}

	@Override
	public Integer getItem(int position) {
		return mDaysArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		return mDaysTypeList.get(position);
	}

	public int getViewTypeCount() {
		return 3;
	}

	public String getSelectedDate(int position) {
		String dateString = null;
		switch (getItemViewType(position)) {
		case DAYS_OF_CURRENT_MONTH:
			dateString = mYear + "-" + mMonth + "-" + getItem(position);
			break;
		case DAYS_OF_NEXT_MONTHS:
			if (mshowOtherMonth) {
				dateString = mYear + "-" + (mMonth + 1) + "-"
						+ getItem(position);
			}
			break;
		case DAYS_OF_LAST_MONTHS:
			if (mshowOtherMonth) {
				dateString = mYear + "-" + (mMonth - 1) + "-"
						+ getItem(position);
			}
			break;
		}
		return dateString;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.grid_item, null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.grid_item_text);
			holder.img = (ImageView) convertView
					.findViewById(R.id.grid_item_background);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		switch (getItemViewType(position)) {
		case DAYS_OF_CURRENT_MONTH:
			holder.textView.setText(String.valueOf(getItem(position)));
			holder.textView.setTextAppearance(mContext,
					R.style.DayOfCurrentMonthStyle);
			if (mSelectedPosition == position) {
				holder.img.setVisibility(View.VISIBLE);
				holder.textView.setTextColor(mContext.getResources().getColor(
						android.R.color.white));
			} else {
				holder.img.setVisibility(View.GONE);
			}
			break;
		case DAYS_OF_NEXT_MONTHS:
		case DAYS_OF_LAST_MONTHS:
			holder.textView.setTextAppearance(mContext,
					R.style.DayOfOtherMonthsStyle);
			if (mshowOtherMonth) {
				holder.textView.setText(String.valueOf(getItem(position)));
				if (mSelectedPosition == position) {
					holder.img.setVisibility(View.VISIBLE);
					holder.textView.setTextColor(mContext.getResources()
							.getColor(android.R.color.white));
				} else {
					holder.img.setVisibility(View.GONE);
				}
			} else {
				holder.textView.setText("");
				holder.img.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}

		return convertView;
	}

	class ViewHolder {
		TextView textView;
		ImageView img;
	}
}
