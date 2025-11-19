package com.yuyuding.tukubackend.model.dto.space;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新空间请求
 */
@Data
public class SpaceUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 空间 id
     */
    private Long id;

    /**
     * 空间名称
     */
    private String spaceName;

    /**
     * 空间级别：0-普通版 1-专业版 2-旗舰版
     */
    private Integer spaceLevel;

    /**
     * 空间图片的最大总体积
     */
    private Long maxSize;

    /**
     * 空间图片的最大图片数量
     */
    private Long maxCount;
}
