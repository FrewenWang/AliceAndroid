package com.frewen.network.response.exception;

import android.net.ParseException;

import com.frewen.network.response.AuraNetResponse;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * @filename: AuraException
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/20 22:17
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AuraNetException extends Exception {

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


    /**
     * 约定异常
     */
    public static class ErrorCode {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = UNKNOWN + 1;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = PARSE_ERROR + 1;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = NETWORD_ERROR + 1;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = HTTP_ERROR + 1;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = SSL_ERROR + 1;

        /**
         * 调用错误
         */
        public static final int INVOKE_ERROR = TIMEOUT_ERROR + 1;
        /**
         * 类转换错误
         */
        public static final int CAST_ERROR = INVOKE_ERROR + 1;
        /**
         * 请求取消
         */
        public static final int REQUEST_CANCEL = CAST_ERROR + 1;
        /**
         * 未知主机错误
         */
        public static final int UNKNOWNHOST_ERROR = REQUEST_CANCEL + 1;

        /**
         * 空指针错误
         */
        public static final int NULLPOINTER_EXCEPTION = UNKNOWNHOST_ERROR + 1;
    }

    private final int errorCode;
    private final String errorMessage;

    public AuraNetException(int errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorMessage = throwable.getMessage();
    }

    public AuraNetException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMessage = errorMsg;
    }

    public AuraNetException(int errorCode, String errorMsg, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorMessage = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMessage;
    }

    /**
     * 判断Response响应结果是否OK
     *
     * @param response
     */
    public static boolean isResponseOk(AuraNetResponse response) {
        if (response == null)
            return false;
        if (response.isSuccess() /*|| ignoreSomeIssue(apiResult.getCode())*/)
            return true;
        else
            return false;
    }


    public static AuraNetException handleException(Throwable throwable) {
        AuraNetException exception;
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            exception = new AuraNetException(httpException.code(), httpException);
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof JsonSyntaxException
                || throwable instanceof JsonSerializer
                || throwable instanceof NotSerializableException
                || throwable instanceof ParseException) {
            exception = new AuraNetException(ErrorCode.PARSE_ERROR, "解析错误", throwable);
        } else if (throwable instanceof ClassCastException) {
            exception = new AuraNetException(ErrorCode.CAST_ERROR, "类型转换错误", throwable);
        } else if (throwable instanceof ConnectException) {
            exception = new AuraNetException(ErrorCode.NETWORD_ERROR, "连接失败", throwable);
        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
            exception = new AuraNetException(ErrorCode.SSL_ERROR, "证书验证失败", throwable);
        } else if (throwable instanceof ConnectTimeoutException) {
            exception = new AuraNetException(ErrorCode.TIMEOUT_ERROR, "连接超时", throwable);
        } else if (throwable instanceof java.net.SocketTimeoutException) {
            exception = new AuraNetException(ErrorCode.TIMEOUT_ERROR, "连接超时", throwable);
        } else if (throwable instanceof UnknownHostException) {
            exception = new AuraNetException(ErrorCode.UNKNOWNHOST_ERROR, "无法解析该域名", throwable);
        } else if (throwable instanceof NullPointerException) {
            exception = new AuraNetException(ErrorCode.NULLPOINTER_EXCEPTION, "NullPointerException", throwable);
        } else {
            exception = new AuraNetException(ErrorCode.UNKNOWN, "未知错误", throwable);
        }
        return exception;
    }


}
