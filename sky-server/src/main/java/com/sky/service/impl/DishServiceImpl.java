package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    /**
     * 新增菜品及其口味数据
     * @param dishDTO
     */
    @Override
    @Transactional       //开启事务,添加菜品和口味同时成功和失败
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //向菜品插入1条数据
        dishMapper.insert(dish);
        long id=dish.getId();   //此时id已经生成并赋值给该对象
        //向口味插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        //遍历集合,插入id
        flavors.forEach(df->df.setDishId(id));

        if(flavors!=null&flavors.size()>0){
            //
            dishFlavorMapper.insert(flavors);
        }
    }
}
