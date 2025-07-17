package com.takeout.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private long total; //总记录数
    private List<T> records; //当前页数据集合
}
