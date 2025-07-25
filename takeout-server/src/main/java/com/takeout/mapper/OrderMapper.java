package com.takeout.mapper;

import com.takeout.dto.page.OrdersPageQueryDTO;
import com.takeout.entity.Order;
import com.takeout.vo.GoodsSalesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Mapper
public interface OrderMapper {
    void addOrder(Order order);

    List<Order> getOverdueOrders(Integer status, LocalDateTime nowTime);

    void update(Order order);

    Order queryById(Long id);

    Double queryTurnoverByMap(Map<String, Object> map);

    Integer queryOrderNumByMap(Map map);

    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);

    List<Order> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    Integer countStatus(Integer status);
}
