package com.sol.admin.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sol
 * @date 2024/08/21
 */
@Getter
@Setter
public class GlobalException extends RuntimeException{

    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误信息
     */
    protected String msg;

    public GlobalException() {
        super();
    }


    public GlobalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GlobalException(Integer code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    public GlobalException(Integer code, String msg, Throwable cause) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }


}
