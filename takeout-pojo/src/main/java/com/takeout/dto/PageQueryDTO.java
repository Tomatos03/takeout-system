package com.takeout.dto;

import lombok.Data;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Data
public abstract class PageQueryDTO {
    protected int page; // 页面编号, 表示从第几页开始查询
    protected int pageSize; // 单页大小
}
