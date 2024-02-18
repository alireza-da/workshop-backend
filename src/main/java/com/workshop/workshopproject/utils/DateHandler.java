package com.workshop.workshopproject.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateHandler {
    public Calendar getCurrentDate() {
        ConvertFromString convertFromString = new ConvertFromString();
        Date date= new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        Calendar createdDate = convertFromString.toCalendar(convertFromString.convertTime(timestamp.toString()));
        return createdDate;
    }

    static public boolean coincides(String startTime1, String endTime1, String startTime2, String endTime2, String pattern) {
        boolean result = false;
        try {
            Date sTime1 = new SimpleDateFormat(pattern).parse(startTime1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(sTime1);
            calendar1.add(Calendar.DATE, 1);


            Date sTime2 = new SimpleDateFormat(pattern).parse(startTime2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(sTime2);
            calendar2.add(Calendar.DATE, 1);

            Date eTime1 = new SimpleDateFormat(pattern).parse(endTime1);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(eTime1);
            calendar3.add(Calendar.DATE, 1);

            Date eTime2 = new SimpleDateFormat(pattern).parse(endTime1);
            Calendar calendar4 = Calendar.getInstance();
            calendar4.setTime(eTime2);
            calendar4.add(Calendar.DATE, 1);

            if ((calendar1.before(calendar2) && calendar3.before(calendar2)) || (calendar1.after(calendar4) && calendar3.after(calendar4))) {
                result = false;
            } else {
                result = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    static public boolean isCoincident(Calendar startTime1, Calendar endTime1, Calendar startTime2, Calendar endTime2) {
        if ((startTime1.before(startTime2) && endTime1.before(startTime2)) || (startTime1.after(endTime2) && endTime2.after(endTime2))) {
            return false;
        }
        return true;
    }
}
