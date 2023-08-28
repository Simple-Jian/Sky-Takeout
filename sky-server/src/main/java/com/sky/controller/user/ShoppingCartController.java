package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;
import java.util.List;

/**
 * 购物车控制类
 */

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api("购物车管理类")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("向购物车添加物品")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车:{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
       return Result.success();
    }

    @ApiOperation("查询购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list(){
        log.info("查询购物车数据:{}", BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCarts = shoppingCartService.showShoppingCart();
        return Result.success(shoppingCarts);
    }

    /**
     * 根据用户id清空购物车
     * @return
     */
    @ApiOperation("清空购物车")
    @DeleteMapping("/clean")
    public Result clean(){
        log.info("清空用户id为{}的购物车",BaseContext.getCurrentId());
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }
}
