package com.nniett.kikaishin.common;

public final class Constants {

    private Constants() {
    }

    public static final String API_ROOT_PATH = "/api";
    public static final String SHELF_PATH = API_ROOT_PATH + "/shelves";
    public static final String BOOK_PATH = API_ROOT_PATH + "/books";
    public static final String ID_PARAM_PATH = "/{id}";
    public static final String TOGGLE_STATUS_PATH = "/toggle_status";
    public static final String ID = "id";


    public static final int TEXT_BODY_SIZE = 500;
    public static final int DESCRIPTION_BODY_SIZE = 250;
    public static final int NAME_SIZE = 50;
    public static final String VARCHAR_DB_DEF = "VARCHAR";
    public static final String TINYINT_DB_DEF = "TINYINT";

    public static final String TEXT_BODY_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + TEXT_BODY_SIZE + ")";
    public static final String ACTIVE_COLUMN_DEFINITION = TINYINT_DB_DEF + " DEFAULT 1";
    public static final String NAME_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + NAME_SIZE + ")";
    public static final String DESCRIPTION_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + DESCRIPTION_BODY_SIZE + ")";

    public static final String CREATION_DATE_COLUMN_NAME = "create_date";
    public static final String UPDATE_DATE_COLUMN_NAME = "update_date";

}
