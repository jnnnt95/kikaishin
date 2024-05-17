package com.nniett.kikaishin.common;

public final class Constants {

    private Constants() {}

    public static final String API_ROOT_PATH = "/api";
    public static final String USER_PATH = API_ROOT_PATH + "/user";
    public static final String SHELF_PATH = API_ROOT_PATH + "/shelves";
    public static final String BOOK_PATH = API_ROOT_PATH + "/books";
    public static final String TOPIC_PATH = API_ROOT_PATH + "/topics";
    public static final String QUESTION_PATH = API_ROOT_PATH + "/questions";
    public static final String REVIEWS_ENDPOINT = "/reviews";
    public static final String GRADES_ENDPOINT = REVIEWS_ENDPOINT + "/grades";
    public static final String REVIEWS_SET_ENDPOINT = REVIEWS_ENDPOINT + "/sets";
    public static final String CUSTOM_REVIEWS_SET_ENDPOINT = REVIEWS_SET_ENDPOINT + "/custom";
    public static final String ANSWERS_ENDPOINT = "/answers";
    public static final String PASSWORD_ENDPOINT = "/password";
    public static final String DISPLAY_NAME_ENDPOINT = "/display_name";
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
    public static final String DISABLE_PATH = "/disable";
    public static final String ENABLE_PATH = "/enable";


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

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    // Regex for a valid password:
    //     1. must contain one digit from 1 to 9
    //     2. one lowercase letter
    //     3. one uppercase letter
    //     4. one special character
    //     5. no space
    //     6. must be 12-50 characters long.
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{12,50}$";

    // Regex for a valid username, only valid:
    // 1. lower case
    // 2. upper case
    // 3. numbers
    // 4. character "_"
    // 5. must be 5-16 characters long.
    public static final String USERNAME_REGEX = "^[_A-Za-z0-9]{5,16}$";

    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";


}
