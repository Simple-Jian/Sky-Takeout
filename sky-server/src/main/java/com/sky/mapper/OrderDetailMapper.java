package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单详情持久层
 */
@Mapper
public interface OrderDetailMapper {
    /**
     * 批量添加订单明细数据
     * @param
     */
    void insertBatch(List<OrderDetail> list);
}
