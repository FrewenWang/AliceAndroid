package com.frewen.network.response;

/**
 * @filename: BaseResponse
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/14 10:07
 * @copyright: Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class Response<Data> {

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
    private Data data;
    /**
     * 请求是否成功
     */
    public boolean isSuccess;

    public boolean isSuccess() {
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

    public Data getData() {
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
}
