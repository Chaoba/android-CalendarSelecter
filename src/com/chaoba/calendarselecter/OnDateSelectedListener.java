package com.chaoba.calendarselecter;

/**
 * Interface definition for a callback to be invoked when date has been
 * selected.
 * 
 * @author Liyanshun
 * 
 */
public interface OnDateSelectedListener {

	/**
	 * call back method when data has been selected
	 * @param date-the data selected in the format of "yyyy-MM-dd"
	 */
	public void OnDateSelected(String date);

}
