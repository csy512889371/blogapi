package com.cto.edu.common.entity.enums;

public enum BooleanEnum {
    TRUE((short)1, "是"), FALSE((short)2, "否");

    private final Short value;
    private final String info;

    private BooleanEnum(Short value, String info) {
        this.value = value;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public Short getValue() {
        return value;
    }
}
