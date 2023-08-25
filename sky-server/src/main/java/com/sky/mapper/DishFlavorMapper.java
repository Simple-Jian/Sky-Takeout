package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜品口味管理映射类
 */
@Mapper
public interface DishFlavorMapper {
    /**
     *
     * @param flavors
     */
    public void insert(List<DishFlavor> flavors);
}
