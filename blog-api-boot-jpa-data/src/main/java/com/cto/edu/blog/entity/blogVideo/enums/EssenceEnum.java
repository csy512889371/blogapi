package com.cto.edu.blog.entity.blogVideo.enums;

/**
 * 精华
 */
public enum EssenceEnum {

    TRUE((short)1, "精华"), FALSE((short)2, "普通");

    private final Short value;
    private final String info;

    private EssenceEnum(Short value, String info) {
        this.value = value;
        this.info = info;
    }

    public static String getInfo(Short value){
        for(EssenceEnum ae : EssenceEnum.values()){
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
