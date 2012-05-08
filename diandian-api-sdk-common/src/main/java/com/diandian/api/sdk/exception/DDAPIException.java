/**
 * 
 */
package com.diandian.api.sdk.exception;

/**
 * diandian-api-sdk通过的异常类。
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-4-9下午4:16:42
 */
public class DDAPIException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    public DDAPIException(int code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
        this.msg = msg;
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

    @Override
    public String toString() {
        return "DDAPIException [code=" + code + ", msg=" + msg + "]";
    }

    public static final int DEFAULT_EXCEPTION_CODE = 400;

    public static final int INVALID_PARAMATERS = 400001;

}
