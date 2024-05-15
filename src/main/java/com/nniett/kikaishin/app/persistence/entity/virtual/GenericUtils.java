package com.nniett.kikaishin.app.persistence.entity.virtual;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import static com.nniett.kikaishin.common.Constants.PREPARATION_LIST_PLACEHOLDER;

public class GenericUtils {

    public static final SimpleDateFormat conversionFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.parse(conversionFormatter.format(date), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static long daysToMilliseconds(float days) {
        return (long) (days * 24f * 60f * 60f * 1000f);
    }

    public static String getQueryPreparationList(int size) {
        return " (" + Arrays.stream(new String[size]).map(nll -> "?").collect(Collectors.joining(",")) + ") ";
    }

    public static String buildQueryWithPreparationList(String query, int listIndex, int size) {
        return query.replace(PREPARATION_LIST_PLACEHOLDER + listIndex, getQueryPreparationList(size));
    }
}
