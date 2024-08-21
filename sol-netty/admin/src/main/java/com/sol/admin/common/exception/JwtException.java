package com.sol.admin.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sol
 * @date 2024/08/21
 */
@Getter
@Setter
public class JwtException extends RuntimeException{

    /**
     * 错误码
     */
    protected String code;
    /**
     * 错误信息
     */
    protected String msg;

    public JwtException() {
        super();
    }


    public JwtException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public JwtException(String code, String msg) {
        super(code);
        this.code = code;
        this.msg = msg;
    }

    public JwtException(String code, String msg, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }


}
