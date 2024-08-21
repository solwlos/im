package com.sol.admin.common.constants;

import lombok.Getter;

/**
 * @author sol
 * @since 2024/6/20
 */
@Getter
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

}
