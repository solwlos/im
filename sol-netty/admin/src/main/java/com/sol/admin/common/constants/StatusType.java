package com.sol.admin.common.constants;

import lombok.Getter;

@Getter
public enum StatusType {

    NORMAL(0, "正常"),

    DELETE(1, "删除");

    private final int value;
    private final String desc;

    StatusType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
