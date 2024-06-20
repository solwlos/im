package com.sol.admin.modules.constants;

/**
 * @author sol
 * @since 2024/6/20
 */
public enum MenuType {
    CATALOG(0, "目录"),
    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    private final int value;
    private final String desc;

    MenuType(int value, String desc) {
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
