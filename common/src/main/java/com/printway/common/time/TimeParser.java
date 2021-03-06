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
        return localDateTime == null
                ? null
                : DateTimeFormatter.ISO_ZONED_DATE_TIME.format(localDateTime.atZone(ZoneId.systemDefault()));
    }

    public static LocalDateTime parseEpochMillisToLocalDateTime(Long epochMillis) {
        return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
