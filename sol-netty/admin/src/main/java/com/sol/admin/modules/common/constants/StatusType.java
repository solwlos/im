package com.sol.admin.modules.common.constants;

public enum StatusType {

    NORMAL(0, "正常"),

    DELETE(1, "删除");

    private int value;
    private String desc;

    StatusType(int value, String desc)
    {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
