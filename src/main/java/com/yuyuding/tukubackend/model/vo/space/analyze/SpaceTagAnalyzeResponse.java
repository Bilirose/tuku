package com.yuyuding.tukubackend.model.vo.space.analyze;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 空间图片标签分析响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceTagAnalyzeResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 图片标签
     */
    private String tag;

    /**
     * 使用次数
     */
    private Long count;
}
