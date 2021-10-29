package com.internetplus.bankpunishment.webservice.exceptions;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/29
 * Web service 中自定义的异常
 */
public class CustomerException extends RuntimeException {
    private String code ;  //异常对应的返回码
    private String msg;  //异常对应的描述信息
    public CustomerException() {
        super();
    }

    public CustomerException(String message) {
        super(message);
        msg = message;
    }

    public CustomerException(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public String getcode() {
        return code;
    }

    public String getmsg() {
        return msg;
    }
}
