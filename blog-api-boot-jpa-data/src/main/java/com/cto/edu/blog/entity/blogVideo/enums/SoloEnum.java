package com.cto.edu.blog.entity.blogVideo.enums;

/**
 * 是否单销
 */
public enum SoloEnum {

    TRUE((short)1, "单售"), FALSE((short)2, "打包销售");

    private final Short value;
    private final String info;

    private SoloEnum(Short value, String info) {
        this.value = value;
        this.info = info;
    }

    public static String getInfo(Short value){
        for(SoloEnum ae : SoloEnum.values()){
            if(ae.getValue().equals(value)){
                return ae.getInfo();
            }
        }
        return null;
    }

    public String getInfo() {
        return info;
    }

    public Short getValue() {
        return value;
    }
}
