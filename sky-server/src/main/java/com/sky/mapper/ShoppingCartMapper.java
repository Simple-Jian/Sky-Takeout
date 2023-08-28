package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 添加购物车
     *
     * @param shoppingCart
     */
    void addShoppingCart(ShoppingCart shoppingCart);

    /**
     * 动态根据菜品id或套餐id查询
     * @param shoppingCart
     */

    /**
     * 根据传入id修改商品数量
     *
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * 根据用户名查询购物车数据
     *
     * @param userId
     * @return
     */
    @Select("select * from shopping_cart where user_id=#{userId}")
    List<ShoppingCart> getByUserId(Long userId);

    /**
     * 根据用户id删除对应的所有购物车数据
     *
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id=#{userId}")
    void deleteByUserId(Long userId);
}
