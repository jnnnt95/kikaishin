package com.nniett.kikaishin.app.persistence.entity.virtual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import static com.nniett.kikaishin.common.Constants.PREPARATION_LIST_PLACEHOLDER;

public class GenericUtils {

    private static final Logger logger = LoggerFactory.getLogger(GenericUtils.class);
    public static final SimpleDateFormat conversionFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static LocalDateTime toLocalDateTime(Date date) {
        logger.debug("Converting java.util.Date to java.time.LocalDateTime");
        logger.trace("Converting date {}.", date);
        return LocalDateTime.parse(conversionFormatter.format(date), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static long daysToMilliseconds(float days) {
        logger.debug("Converting days to milliseconds.");
        logger.trace("Converting days {}.", days);
        return (long) (days * 24f * 60f * 60f * 1000f);
    }

    public static String getQueryPreparationList(int size) {
        logger.debug("Preparing query with '?' characters in list.");
        logger.trace("Total parameters to prepare: {}.", size);
        return " (" + Arrays.stream(new String[size]).map(nll -> "?").collect(Collectors.joining(",")) + ") ";
    }

    public static String buildQueryWithPreparationList(String query, int listIndex, int size) {
        logger.debug("Setting parameter list for query preparation.");
        logger.trace("Query to set list: {}.", query);
        logger.trace("Query being set with list index {} and size {}.", listIndex, size);
        return query.replace(PREPARATION_LIST_PLACEHOLDER + listIndex, getQueryPreparationList(size));
    }
}
