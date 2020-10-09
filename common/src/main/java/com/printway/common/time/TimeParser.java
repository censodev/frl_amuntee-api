package com.printway.common.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeParser {
    public static LocalDateTime parseZonedDateTimeToLocalDateTime(String zonedDateTime) {
        if (zonedDateTime == null || zonedDateTime.isEmpty())
            return null;
        return ZonedDateTime
                .parse(zonedDateTime, DateTimeFormatter.ISO_DATE_TIME)
                .toLocalDateTime();
    }

    public static String parseLocalDateTimeToISOString(LocalDateTime localDateTime) {
        return  DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
    }

    public static LocalDateTime parseEpochMillisToLocalDateTime(Long epochMillis) {
        return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
