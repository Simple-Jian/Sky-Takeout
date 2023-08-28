package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * 如果商品已经存在,执行更新操作,其数量+1
     * 如果商品不存在,执行插入操作
     *
     * @param shoppingCartDTO
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断商品是否已经存在,如果存在,则更新数量+1
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        //向shoppingCart中添加用户id,这个id是在拦截器中设置的
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        //查询
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        //判断是否存在,存在更将其数量+1
        if (list != null && list.size() > 0) {  //根据查询条件,一定只有一个数据
            ShoppingCart cart = list.get(0);
            //将其数量+1然后更新
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        } else {//如果商品不存在,则插入一条购物车数据
            //首先判断添加到购物车的是菜品还是套餐
            Long dishId = shoppingCart.getDishId();
            Long setmealId = shoppingCart.getSetmealId();
            if (dishId != null) { //菜品id不为空,则添加的是菜品,否则是套餐
                //查询菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                //查询套餐
                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);  //数量
            shoppingCart.setCreateTime(LocalDateTime.now());
            //插入新购物车物品
            shoppingCartMapper.addShoppingCart(shoppingCart);
        }
    }

    /**
     * 根据用户id查询购物车数据
     *
     * @return
     */
    @Override
    public List<ShoppingCart> showShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> list = shoppingCartMapper.getByUserId(userId);
        return list;
    }

    /**
     * 清空购物车
     */
    @Override
    public void cleanShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }
}
