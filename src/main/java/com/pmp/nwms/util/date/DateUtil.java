package com.pmp.nwms.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

    private static final Map<String, SimpleDateFormat> SIMPLE_DATE_FORMAT = new HashMap<>();

    public static String formatDate(Date date, String pattern, boolean solar) {
        SimpleDateFormat dateFormat = getDateFormat(pattern, solar);
        return dateFormat.format(date);

    }

    public static Date parseDate(String dateValue, String pattern, boolean solar) throws ParseException {
        SimpleDateFormat dateFormat = getDateFormat(pattern, solar);
        return dateFormat.parse(dateValue);
    }

    private static SimpleDateFormat getDateFormat(String pattern, boolean solar) {
        String key = pattern + "_" + solar;
        if (!SIMPLE_DATE_FORMAT.containsKey(key)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            if (solar) {
                simpleDateFormat.setCalendar(new HijriShamsiCalendar());
            }
            SIMPLE_DATE_FORMAT.put(key, simpleDateFormat);
        }
        return SIMPLE_DATE_FORMAT.get(key);
    }

    public static Date plusDays(Date date, int daysCount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + daysCount);
        return cal.getTime();
    }

    public static Date plusMonths(Date date, int monthsCount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + monthsCount);
        return cal.getTime();
    }

    public static Date convertInstantToDate(Instant instant) {
        if (instant != null) {
            return Date.from(instant);
        }
        return null;
    }

    public static Calendar roundDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Calendar ceilDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal;
    }

    public static Date getSolarWeekStartDate(Date date) {
        Calendar cal = roundDate(date);
        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            return plusDays(cal.getTime(), -1);
        }
        return cal.getTime();
    }

    public static Date getSolarWeekEndDate(Date date) {
        Calendar cal = ceilDate(date);
        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            return plusDays(cal.getTime(), 1);
        }
        return cal.getTime();
    }

    public static Date getSolarMonthStartDate(Date date) {
        HijriShamsiCalendar cal = new HijriShamsiCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getSolarMonthEndDate(Date date) {
        HijriShamsiCalendar cal = new HijriShamsiCalendar();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        if (month < 6) {
            cal.set(Calendar.DAY_OF_MONTH, 31);
        } else if (month < 11) {
            cal.set(Calendar.DAY_OF_MONTH, 30);
        } else if (cal.isLeapYear(cal.get(Calendar.YEAR))) {
            cal.set(Calendar.DAY_OF_MONTH, 30);
        } else {
            cal.set(Calendar.DAY_OF_MONTH, 29);
        }
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }


    public static void main(String[] args) {
        Date now = new Date();
        Date solarWeekStartForDate = getSolarWeekStartDate(now);
        System.out.println("solarWeekStartForDate = " + solarWeekStartForDate);
        Date solarWeekEndForDate = getSolarWeekEndDate(now);
        System.out.println("solarWeekEndForDate = " + solarWeekEndForDate);
        Date solarMonthStartDate = getSolarMonthStartDate(now);
        System.out.println("solarMonthStartDate = " + solarMonthStartDate);
        Date solarMonthEndDate = getSolarMonthEndDate(now);
        System.out.println("solarMonthEndDate = " + solarMonthEndDate);


    }

    public static int getDatesDiffInMinutes(Date date1, Date date2) {
        return (getDatesDiffInSeconds(date1, date2) / 60);
    }

    public static int getDatesDiffInSeconds(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        return (int) (diff / 1000);
    }

    public static Date plusHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + hours);
        return cal.getTime();

    }

    public static Date plusMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + minutes);
        return cal.getTime();

    }
}
