package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据dish_id数组批量删除对应的口味
     * @param id2
     */
    void deleteByDishIds(Long[] id2);

    /**
     * 根据菜品id查询对应的口味
     * @param id
     * @return
     */
    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getByDishId(Long id);
}
