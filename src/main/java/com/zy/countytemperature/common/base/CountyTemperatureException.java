package com.zy.countytemperature.common.base;

/**
 * @author 张雨
 * @date 2021/5/16 18:25
 */
public class CountyTemperatureException extends Exception {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CountyTemperatureException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CountyTemperatureException(String message, int code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public CountyTemperatureException(String message, Throwable cause, int code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public CountyTemperatureException(Throwable cause, int code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public CountyTemperatureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
    }

    public CountyTemperatureException(String msg) {
        super(msg);
    }
}
