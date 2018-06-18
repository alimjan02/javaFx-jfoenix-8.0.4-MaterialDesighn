package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class DateUtils {

    public static String getAfterDay(String date, int day) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date a = null;
        try {
            a = parser.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(a);
            calendar.add(Calendar.DATE, day);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            return simpleDateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
