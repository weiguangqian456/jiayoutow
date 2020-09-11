package com.edawtech.jiayou.config.bean;

import java.io.Serializable;

public class BaseResultData implements Serializable {

    /**
     * {
     *     "code": 200,
     *     "data": Object对象,
     *     "message": "请求成功",
     *     "success": true
     * }
     */
    private int     code;
    private String message;
    private Object data;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
