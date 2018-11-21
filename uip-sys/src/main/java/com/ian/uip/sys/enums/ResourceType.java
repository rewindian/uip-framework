package com.ian.uip.sys.enums;

public enum ResourceType {

    /**
     * 目录
     */
    CATALOG(0),
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2),

    /**
     * 数据权限
     */
    DATA(3);

    private int value;

    private ResourceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
