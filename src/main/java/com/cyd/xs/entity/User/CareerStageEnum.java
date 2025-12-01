package com.cyd.xs.entity.User;
/*
 * @Description: 职业阶段枚举
 */
public enum CareerStageEnum {

    STUDENT("学生"),
    NOVICE("职场菜鸟"),
    EXPERT("专家"),
    VETERAN("职场老手");

    private final String desc;

    CareerStageEnum(String desc) {
        this.desc = desc;
    }

    // 校验值是否合法
    public static boolean isValid(String value) {
        if (value == null) return false;
        for (CareerStageEnum e : values()) {
            if (e.name().equals(value) || e.desc.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
