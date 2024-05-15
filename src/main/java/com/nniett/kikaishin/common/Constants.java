package com.nniett.kikaishin.common;

public final class Constants {

    private Constants() {}

    public static final String API_ROOT_PATH = "/api";
    public static final String SHELF_PATH = API_ROOT_PATH + "/shelves";
    public static final String BOOK_PATH = API_ROOT_PATH + "/books";
    public static final String TOPIC_PATH = API_ROOT_PATH + "/topics";
    public static final String QUESTION_PATH = API_ROOT_PATH + "/questions";
    public static final String REVIEWS_ENDPOINT = "/reviews";
    public static final String GRADES_ENDPOINT = REVIEWS_ENDPOINT + "/grades";
    public static final String REVIEWS_SET_ENDPOINT = REVIEWS_ENDPOINT + "/sets";
    public static final String CUSTOM_REVIEWS_SET_ENDPOINT = REVIEWS_SET_ENDPOINT + "/custom";
    public static final String ANSWERS_ENDPOINT = "/answers";
    public static final String CLUES_ENDPOINT = "/clues";
    public static final String INFO_ENDPOINT = "/info";
    public static final String MULTI_INFO_ENDPOINT = "multi/info";
    public static final String RECOMMENDED = "/recommended";
    public static final String REVIEW_PATH = API_ROOT_PATH + REVIEWS_ENDPOINT;
    public static final String ID = "id";
    public static final String IDS = "ids";
    public static final String ID_PARAM_PATH = "/{" + ID + "}";
    public static final String IDS_PARAM_PATH = "/{" + IDS + "}";
    public static final String TOGGLE_STATUS_PATH = "/toggle_status";


    public static final int TEXT_BODY_SIZE = 500;
    public static final int DESCRIPTION_BODY_SIZE = 250;
    public static final int NAME_SIZE = 50;
    public static final int LOOKUP_KEY_SIZE = 30;
    public static final String VARCHAR_DB_DEF = "VARCHAR";
    public static final String TINYINT_DB_DEF = "TINYINT";

    public static final String TEXT_BODY_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + TEXT_BODY_SIZE + ")";
    public static final String LOOKUP_KEY_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + LOOKUP_KEY_SIZE + ")";
    public static final String ACTIVE_COLUMN_DEFINITION = TINYINT_DB_DEF + " DEFAULT 1";
    public static final String NAME_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + NAME_SIZE + ")";
    public static final String DESCRIPTION_COLUMN_DEFINITION = VARCHAR_DB_DEF + "(" + DESCRIPTION_BODY_SIZE + ")";

    public static final String CREATION_DATE_COLUMN_NAME = "create_date";
    public static final String UPDATE_DATE_COLUMN_NAME = "update_date";

    public static final String PREPARATION_LIST_PLACEHOLDER = "{list}#";


}
