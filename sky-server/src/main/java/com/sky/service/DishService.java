package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

//菜品管理服务层
public interface DishService {
    public void save(DishDTO dishDTO);
    //菜品分页查询
    PageResult page(Long categoryId, String name, Integer page, Integer pageSize, Integer status);
    //批量删除菜品
    void deleteByIds(String ids);

    /**
     * 根据id查询菜品和对应的口味信息,封装到返回值对象中
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 修改菜品
     * 并修改对应的口味信息
     * @param dishDTO
     */
    void updateDishAndFlavor(DishDTO dishDTO);
}
