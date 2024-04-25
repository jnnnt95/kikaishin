package com.nniett.kikaishin.web.persistence;

public final class Constants {

    private Constants() {
    }

    private static final int TEXT_BODY_SIZE = 500;
    private static final int DESCRIPTION_BODY_SIZE = 250;
    private static final int NAME_SIZE = 50;
    private static final String VARCHAR_DB_DEF = "VARCHAR";
    public static final String TINYINT_DB_DEF = "TINYINT";

    public static final String TEXT_BODY_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + TEXT_BODY_SIZE + ")";
    public static final String ACTIVE_COLUMN_DEFINITION = TINYINT_DB_DEF + " DEFAULT 1";
    public static final String NAME_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + NAME_SIZE + ")";
    public static final String DESCRIPTION_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + DESCRIPTION_BODY_SIZE + ")";

    public static final String CREATION_DATE_COLUMN_NAME = "create_date";
    public static final String UPDATE_DATE_COLUMN_NAME = "update_date";

}
