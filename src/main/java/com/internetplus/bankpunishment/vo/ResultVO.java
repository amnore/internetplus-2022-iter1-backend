package com.internetplus.bankpunishment.vo;

import lombok.Data;

/**
 * @author: xzh
 * @date: 2021-10-20
 */
@Data
public class ResultVO {
    /** 错误码. */
    private Integer code;
    /** 提示信息. */
    private String msg;
    /** 具体的内容. */
    private Object data;

    public ResultVO(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultVO buildSuccess(Object data) {
        return new ResultVO(0, "success", data);
    }

    public static ResultVO buildFailure(Integer code, Object data) {
        return new ResultVO(code, "fail", data);
    }
}