package com.takeout.service;

import com.takeout.vo.BusinessDataVO;
import com.takeout.vo.DishOverViewVO;
import com.takeout.vo.OrderOverViewVO;
import com.takeout.vo.SetMealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {
    /**
     * 根据时间段统计营业数据
     * @param begin
     * @param end
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 查询订单管理数据
     * @return
     */
    OrderOverViewVO getOrderOverView();

    /**
     * 查询菜品总览
     * @return
     */
    DishOverViewVO getDishOverView();

    /**
     * 查询套餐总览
     * @return
     */
    SetMealOverViewVO getSetMealOverView();
}
