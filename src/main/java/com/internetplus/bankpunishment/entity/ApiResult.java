package com.internetplus.bankpunishment.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 接口返回数据对象
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResult<T> implements Serializable {

    public static ApiResult success() {
        return new ApiResult(true, "200", "success");
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<T>(true, "200", "success", data);
    }

    public static ApiResult fail(String message) {
        return new ApiResult(false, "500", message);
    }

    public static ApiResult paramError(String message) {
        return new ApiResult(false, "400", message);
    }

    public ApiResult() {
    }

    public ApiResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResult(Boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public ApiResult(Boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private static final long serialVersionUID = -2402122704294916086L;

    private Boolean success = Boolean.TRUE;
    private String code;
    private String message;
    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
