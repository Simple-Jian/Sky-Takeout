package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单管理持久层
 */
@Mapper
public interface OrderMapper {
    /**
     * 向订单中插入数据
     * @param order
     */
    void save(Orders order);
}
