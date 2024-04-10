package com.sol.admin.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author sol
 * @date 2024/1/10 15:09
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result implements Serializable {

    private Integer code;

    private String desc;

    private Object data;

    public static Result error() {
        return Result.builder().code(1).desc("失败").build();
    }

    public static Result error(int code) {
        return Result.builder().code(code).desc("失败").build();
    }

    public static Result error(String desc) {
        return Result.builder().code(1).desc(desc).build();
    }

    public static Result error(int code, String desc) {
        return Result.builder().code(code).desc(desc).build();
    }

    public static Result success() {
        return Result.builder().code(0).desc("成功").build();
    }

    public static Result success(Object data) {
        return Result.builder().code(0).desc("成功").data(data).build();
    }

    public static Result success(String desc) {
        return Result.builder().code(0).desc(desc).build();
    }

    public static Result success(Object data, String desc) {
        return Result.builder().code(0).desc(desc).data(data).build();
    }
}
