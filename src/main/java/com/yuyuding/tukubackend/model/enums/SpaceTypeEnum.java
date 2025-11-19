package com.yuyuding.tukubackend.model.enums;

import cn.hutool.core.util.ObjUtil;
import com.yuyuding.tukubackend.exception.BusinessException;
import com.yuyuding.tukubackend.exception.ErrorCode;
import com.yuyuding.tukubackend.exception.ThrowUtils;
import lombok.Getter;

/**
 * 空间类型枚举类
 */
@Getter
public enum SpaceTypeEnum {

    PRIVATE("私有空间",0),
    TEAM("团队空间",1);

    private final String text;

    private final int value;

    /**
     * @param text  文本
     * @param value 值
     */
    SpaceTypeEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     * @param value
     * @return
     */
    public static SpaceTypeEnum getEnumByValue(Integer value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (SpaceTypeEnum spaceTypeEnum : SpaceTypeEnum.values()) {
            if (spaceTypeEnum.getValue() == value) {
                return spaceTypeEnum;
            }
        }
        return null;
    }
}
