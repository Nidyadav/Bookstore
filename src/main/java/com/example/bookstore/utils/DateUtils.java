package com.example.bookstore.utils;

import com.example.bookstore.batch.SpringBatchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    static final Logger logger = LoggerFactory.getLogger(SpringBatchConfig.class);
    static LocalDate date = null;

    public static LocalDate parseDate(String dateStr) {
        try {
            DateTimeFormatter dtfInput = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            date = LocalDate.parse(dateStr, dtfInput);
        } catch (Exception e) {
            logger.error(e.getMessage());
       }
        return date;
    }
}
