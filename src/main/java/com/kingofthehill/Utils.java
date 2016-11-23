package com.kingofthehill;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by patrik on 2016-11-23.
 */
public class Utils {
    public static LocalDate toLocalDate(Timestamp timestamp) {
        if (timestamp == null) return null;
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(timestamp);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateStr, dtf);
    }
}
