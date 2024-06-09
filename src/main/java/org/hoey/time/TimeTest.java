package org.hoey.time;

import java.sql.Date;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Joy
 * @date 2020-09-14
 */
public class TimeTest {

    public static void main(String[] args) {
        Instant instant = Instant.now();
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.of("+8"));
        System.out.println(offsetDateTime.toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        Date.from(instant);
    }

}
