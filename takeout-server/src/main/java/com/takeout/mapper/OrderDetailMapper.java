package com.takeout.mapper;

import com.takeout.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Mapper
public interface OrderDetailMapper {
    void insert(OrderDetail orderDetail);

    void insertBatch(List<OrderDetail> orderDetails);

    List<OrderDetail> queryByOrderId(Long orderId);
}
