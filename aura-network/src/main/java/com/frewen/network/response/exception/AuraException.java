package com.frewen.network.response.exception;

/**
 * @filename: AuraException
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/20 22:17
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AuraException extends Exception {

    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int METHOD_NOT_ALLOWED = 405;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private final int errorCode;
    private final String errorMessage;

    public AuraException(int errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorMessage = throwable.getMessage();
    }

    public int getErrorCode() {
        return errorCode;
    }
}
