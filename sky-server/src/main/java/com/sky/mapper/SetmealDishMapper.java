package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashSet;
import java.util.List;

/**
 * 多对多关联表的接口
 *
 */
@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品id的数组批量查询对应套餐的id
     * 使用HashSet对查到的数据去重
     * @param id2
     * @return
     */

    HashSet<Long> getIds(Long []id2);

    /**
     * 根据所传的套餐id删除关联的菜品和套餐
     * 这里也可以使用菜品的id,由于两者是关联的,等效
     * @param hs
     */
    void deleteBySetmealIds(HashSet hs);
}
