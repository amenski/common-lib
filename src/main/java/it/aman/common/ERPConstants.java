package it.aman.common;

import it.aman.common.exception.ERPException;

public class ERPConstants {

    // LOGGER
    public static final String PARAMETER_2 = "{} {}";
    public static final String PARAMETER_3 = "{} {} {}";
    public static final String METHOD_START = " method start.";
    public static final String METHOD_END = " method end.";
    public static final String INPUT_PARAMETER = " input parameter ";

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    // auth
    public static final String ACCOUNT_ACTIVE = "ACTIVE";
    public static final String ACCOUNT_NOT_FOUND = "User account not found, Please check again!";
    public static final String INSUFFICENT_PERMISSION = "Insufficient permission to perform this action.";

    public static final int AUTH_TOKEN_VALIDITY = 1000 * 60 * 30; // 30min
    public static final String AUTH_TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_STRING = "Authorization";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String X_REQUESTED_URL = "X-Requested-Url";
    public static final String TRANSACTION_ID_KEY = "X-Transaction-Id";
    public static final String X_REQUESTED_URL_HTTP_METHOD = "X-Requested-Url-http-method";
    public static final String X_REQUESTED_URL_SUBJECT = "X-Requested-User-Id";

    public static final String ANONYMOUS_USER = "anonymousUser";

    // converter
    public static final String ATTRIBUTE_SEPARATOR = "#";

    private ERPConstants() throws ERPException {
        throw new ERPException("Utility class.");
    }
}
