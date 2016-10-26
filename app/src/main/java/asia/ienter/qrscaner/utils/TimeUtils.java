package asia.ienter.qrscaner.utils;

/**
 * Created by phamquangmanh on 10/26/16.
 */

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import asia.ienter.qrscaner.R;


public class TimeUtils {
    public static final String FORMAT_DOB = "dd/MM/yyyy";

    public static String convertNowToFullDateString() {
        SimpleDateFormat dateformat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return dateformat.format(new Date());
    }

    public static String convertNowToDateString(String format) {
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(new Date());
    }

    public static String initDateString() {
        return "1900-01-01 09:00:00";
    }

    public static int compareDate(String dateFormat, String dateA, String dateB) {
        int result = 0;
        String aDateFormat = dateFormat.equalsIgnoreCase("") ? "yyyy-MM-dd HH:mm:ss"
                : dateFormat;
        SimpleDateFormat inputFormat = new SimpleDateFormat(aDateFormat);
        Date startDate;
        Date endDate;
        try {
            startDate = inputFormat.parse(dateA);
            endDate = inputFormat.parse(dateB);
            result = (startDate.getTime() - endDate.getTime()) > 0 ? -1 : 1;
//            Log.d("TimeUtility", "Compare date : " + dateA + " : " + dateB
//                    + " : " + result);
        } catch (ParseException e) {
        }
        return result;

    }

    public static String reformatDateTime(String dateFormat,
                                          String outputFormat, String dateInput) {
        String outputStr = "";

        String aDateFormat = dateFormat.equalsIgnoreCase("") ? "yyyy-MM-dd HH:mm:ss"
                : dateFormat;
        SimpleDateFormat inputFormat = new SimpleDateFormat(aDateFormat);
        SimpleDateFormat outputForm = new SimpleDateFormat(outputFormat);

        Date startDate;
        try {
            if (dateInput != null) {
                startDate = inputFormat.parse(dateInput);
                outputStr = outputForm.format(startDate);
            }
        } catch (ParseException e) {
            Log.e("convert time", e.toString());
        }
        return outputStr;

    }

    public static String getDateString(String dateFormat, String longVvalue) {
        try {
            String outputStr = "";
            SimpleDateFormat outputForm = new SimpleDateFormat(dateFormat);

            Date startDate;
            startDate = new Date(Long.parseLong(longVvalue));
            outputStr = outputForm.format(startDate);
            return outputStr;
        } catch (Exception e) {
            return "";
        }
    }

    public static long getLongValueDate(String inputDate, String dateFormat) {

        String aDateFormat = dateFormat.equalsIgnoreCase("") ? "yyyy-MM-dd HH:mm:ss"
                : dateFormat;
        SimpleDateFormat outputForm = new SimpleDateFormat(aDateFormat);

        Date startDate = new Date();
        try {
            startDate = outputForm.parse(inputDate);
        } catch (ParseException e) {
        }
        return startDate.getTime();
    }

    public static String getDateFromAutoTimeZone(String inputDate, String dateFormat) {
        String aDateFormat = dateFormat.equalsIgnoreCase("") ? "yyyy-MM-dd HH:mm:ss"
                : dateFormat;
        SimpleDateFormat outputForm = new SimpleDateFormat(aDateFormat);

        Date startDate = new Date(inputDate);
        try {
            String outDate = outputForm.format(startDate);
            return outDate;
        } catch (Exception e) {
        }
        return "";
    }

    public static String getDateFromAutoTimeZone(Date date, String dateFormat) {
        String aDateFormat = dateFormat.equalsIgnoreCase("") ? "yyyy-MM-dd HH:mm:ss"
                : dateFormat;
        SimpleDateFormat outputForm = new SimpleDateFormat(aDateFormat);
        try {
            String outDate = outputForm.format(date);
            return outDate;
        } catch (Exception e) {
            return null;
        }
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static long getDistanceTime(long olderTime, long currentTime) {
        return currentTime - olderTime;
    }

    public static String getCurrentTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentTimeFormat(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String convertTimestamp(String ngaySinh) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(ngaySinh);
            Timestamp time = new Timestamp(date.getTime());
            long tm = (time.getTime()) / 1000;
            return String.valueOf(tm);
        } catch (ParseException e) {
            return "";
        }
    }

    public static long getCurrentTimestamp() {
        long tsLong = System.currentTimeMillis() / 1000;

        return tsLong;
    }

    public static String timeAgo(Context context, long millis) {
        long tsLongCurrent = System.currentTimeMillis() / 1000;
        long diff = tsLongCurrent - millis;
        if (context == null) {
            return "";
        }
        Resources r = context.getResources();

        String prefix = r.getString(R.string.time_ago_prefix);
        String suffix = r.getString(R.string.time_ago_suffix);

        double seconds = Math.abs(((diff / 1)));
        double minutes = seconds / 60;
        double hours = minutes / 60;
        int days = (int) (hours / 24);
        int weeks = (int) (days / 7);
        int month = (int) (days / 30);
        int years = (int) (days / 365);

        String words;


        if (seconds < 60) {
            words = r.getString(R.string.time_ago_now);
        } else if (minutes < 60) {
            words = r.getString(R.string.time_ago_minutes, Math.round(minutes));
        } else if (hours < 24) {
            words = r.getString(R.string.time_ago_hours, Math.round(hours));
        } else if (days < 7) {
            words = days + " " + r.getString(R.string.time_ago_days);
        } else if (weeks <= 4) {
            words = weeks + " " + r.getString(R.string.time_ago_weeks);
        } else if (month < 12) {
            words = month + " " + r.getString(R.string.time_ago_months);
        } else {
            words = years + " " + r.getString(R.string.time_ago_years);
        }

        StringBuilder sb = new StringBuilder();

        if (prefix != null && prefix.length() > 0) {
            sb.append(prefix).append(" ");
        }

        sb.append(words);

        if (suffix != null && suffix.length() > 0) {
            sb.append(" ").append(suffix);
        }

        return sb.toString().trim();
    }

    public static long convertTimestampLong(String ngaySinh) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        long tm = 0;
        java.util.Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(ngaySinh);
//            Log.d("ngaySinh", "ngaySinh " + ngaySinh);
            Timestamp time = new Timestamp(date.getTime());

            tm = (time.getTime()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static String stringForTime(int timeMs) {

        Formatter mFormatter;
        StringBuilder mFormatBuilder;

        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
}