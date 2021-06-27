package com.frewen.network.response;

import java.io.Serializable;

/**
 * @filename: BaseResponse
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/14 10:07
 * @copyright: Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class AuraNetResponse<T> implements Serializable {

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 请求是否成功
     */
    private boolean isSuccess;

    public boolean isSuccess() {
        this.isSuccess = code == 0 && data != null;
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Response {" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data.toString() +
                '}';
    }

    public void setData(T data) {
        this.data = data;
    }
}
