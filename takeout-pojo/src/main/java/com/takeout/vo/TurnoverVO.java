package com.takeout.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TurnoverVO {
    private String dateList;
    private String turnoverList;
}
