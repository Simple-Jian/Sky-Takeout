package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品
     * @param dish
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 根据菜品分类名,菜品名,状态条件查询菜品,将其封装到DsihVO对象中,返回该对象的集合
     * @param categoryId
     * @param name
     * @param status
     * @return
     */
    List<DishVO> page(Long categoryId, String name, Integer status);

    /**
     * 批量删除菜品
     * @param id2
     */
    void deleteByIds(Long[] id2);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Select("select * from dish where id=#{id}")
    Dish getById(Long id);

    /**
     * 根据id查询菜品及对应的口味,使用左外接查询
     * @param id
     * @return
     */
    @Select("select * from dish where id=#{id}")
    Dish getById1(Long id);

    /**
     * 根据传过来的对象修改信息
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateDish(Dish dish);

    /**
     * 根据dish中的参数进行条件查询
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);
}
