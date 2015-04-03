package com.chaoba.calendarselecter;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerActivity extends Activity {

	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		mViewPager = (ViewPager) findViewById(R.id.calendar_pager);
		mViewPager.setAdapter(new CalendarAdapter());
		mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
	}

	MonthChangeListenr mMonthChangeListenr = new MonthChangeListenr() {

		@Override
		public void LastMonthClicked() {
			mViewPager.arrowScroll(View.FOCUS_LEFT);
		}

		@Override
		public void nextMonthClicker() {
			mViewPager.arrowScroll(View.FOCUS_RIGHT);

		}

	};

	class CalendarAdapter extends PagerAdapter {
		CalendarSelecterView[] views = new CalendarSelecterView[5];
		private Calendar mCalendar;

		public CalendarAdapter() {
			for (int i = 0; i < 5; i++) {
				views[i] = new CalendarSelecterView(getBaseContext());
				views[i].setOnMonthChangeListenr(mMonthChangeListenr);
			}
			mCalendar = Calendar.getInstance();
		}

		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView((View) view);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			CalendarSelecterView v = views[position % 5];
			int months = Integer.MAX_VALUE / 2 - position;
			Calendar calendar = (Calendar) mCalendar.clone();
			calendar.add(Calendar.MONTH, -(months));
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			v.setTime(year, month, false);
			container.addView(v);
			return v;
		}
	}

}
