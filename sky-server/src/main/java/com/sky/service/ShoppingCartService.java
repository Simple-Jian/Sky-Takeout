package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 购物车添加商品
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 根据解析的用户id查询购物车数据
     * @return
     */
    List<ShoppingCart> showShoppingCart();

    /**
     * 清空购物车
     */
    void cleanShoppingCart();
}
