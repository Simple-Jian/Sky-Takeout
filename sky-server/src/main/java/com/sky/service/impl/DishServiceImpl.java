package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Employee;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashSet;
import java.util.List;
@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;


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

    /**
     * 彩屏分页查询
     * @param categoryId
     * @param name
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public PageResult page(Long categoryId, String name, Integer page, Integer pageSize, Integer status) {
        //设置分页参数
        PageHelper.startPage(page, pageSize);
        //执行查询
        Page<DishVO> p = (Page)dishMapper.page(categoryId,name,status);
        //封装到pageBean对象中
        PageResult result = new PageResult(p.getTotal(), p.getResult());
        log.info("查询到的菜品总数:{}", p.getTotal());
        return result;
    }

    /**
     * 批量删除菜品
     * 当菜品处于起售中时,不允许删除菜品
     * 同时删除与之关联的口味
     * 同时删除与之关联的套餐
     * @param ids
     */
    @Transactional
    @Override
    public void deleteByIds(String ids) {
        String id[]=ids.split(",");
        Long id2[]=new Long[id.length];
        for (int i = 0; i < id2.length; i++) {
            id2[i]=Long.parseLong(id[i]);
        }
        //如果菜品处于在售状态,则不能删除
        for (Long aLong : id2) {
            Dish dish=dishMapper.getById(aLong);
            if(dish.getStatus()== StatusConstant.DISABLE)
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        //批量删除菜品
        dishMapper.deleteByIds(id2);
        //批量删除对应的口味
        dishFlavorMapper.deleteByDishIds(id2);
        //批量删除对应的套餐
        //1.查询对应的套餐
        List<Long> list=setmealDishMapper.getIds(id2);
        HashSet<Long> list1=new HashSet<>(list); //去重
        //2.删除对应的套餐
        setmealMapper.deleteByDishIds(list1);
        //3.删除对应的关联数据
        setmealDishMapper.deleteBySetmealIds(list1);
    }

    @Override
    public DishVO getById(Long id) {
        //先获得菜品的所有值
        Dish dish=dishMapper.getById1(id);
        //再获得菜品对应的口味
        List<DishFlavor> flavors=dishFlavorMapper.getByDishId(id);
        //封装结果
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    /**
     * 修改菜品
     * 并修改对应的口味信息
     * @param dishDTO
     */
    @Override
    public void updateDishAndFlavor(DishDTO dishDTO) {
        //先更新菜品的信息
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.updateDish(dish);
        //口味信息更改比较麻烦,因此先删除对应的口味信息,再添加新的信息
        Long ids[]=new Long[]{dishDTO.getId()};
        dishFlavorMapper.deleteByDishIds(ids);
        //再添加新的口味信息,而且由于重新提交,这些口味信息是没有dishId信息的
        List<DishFlavor> flavors=dishDTO.getFlavors();
        //如果口味信息非空,则添加id信息并添加到口味表中,否则不添加
        if(flavors!=null&flavors.size()>0){
            flavors.forEach(item->item.setDishId(dishDTO.getId()));
            dishFlavorMapper.insert(flavors);
        }
    }
}
