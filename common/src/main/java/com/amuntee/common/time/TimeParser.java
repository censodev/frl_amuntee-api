package com.amuntee.common.time;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeParser {
    public static LocalDateTime parse(String zonedDateTime) {
        if (zonedDateTime == null || zonedDateTime.isEmpty())
            return null;
        return ZonedDateTime
                .parse(zonedDateTime, DateTimeFormatter.ISO_DATE_TIME)
                .toLocalDateTime();
    }
}
