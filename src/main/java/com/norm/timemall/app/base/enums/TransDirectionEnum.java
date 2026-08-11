package com.norm.timemall.app.base.enums;


public enum TransDirectionEnum {
    DEBIT(-1,"债务"),
    CREDIT(1,"债权");
    private Integer mark;
    private String desc;

    TransDirectionEnum(Integer mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(Integer value) {
        for (TransDirectionEnum s : TransDirectionEnum.values()) {
            if (s.getMark()==value) {
                return true;
            }
        }
        return false;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
