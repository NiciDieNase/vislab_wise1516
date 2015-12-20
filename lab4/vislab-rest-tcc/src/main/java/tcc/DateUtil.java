package tcc;

import java.util.GregorianCalendar;

public class DateUtil {

	/**
	 * Utility function to normalize dates per day. I.e. all individual dates
	 * within one day will be mapped to the same date.
	 * 
	 * @param date
	 *            the individual date
	 * @return the date normalized for a day
	 */
	public static long normalize(long date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(date);
		GregorianCalendar ncal = new GregorianCalendar();
		ncal.set(GregorianCalendar.YEAR, cal.get(GregorianCalendar.YEAR));
		ncal.set(GregorianCalendar.DAY_OF_YEAR, cal.get(GregorianCalendar.DAY_OF_YEAR));
		return ncal.getTimeInMillis();
	}

}
