package com.antman.extensionmarket42.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SqlDateParser {
    public static java.sql.Date parseSqlDateISO8601(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = sdf.parse(dateString);
        return new java.sql.Date(date.getTime());
    }
}
