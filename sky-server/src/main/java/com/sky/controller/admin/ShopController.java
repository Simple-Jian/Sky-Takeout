package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * 关于商店的接口,如营业状态
 */
@Api(tags = "店铺相关接口")
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    //定义一个店铺营业状态的常量
    public static final String STATUS="SHOP_STATUS";

    /**
     * 使用redis设置店铺营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置营业状态")
    public Result updateShopStatus(@PathVariable Integer status){
        log.info("设置店铺营业状态为:{}",status==1?"营业中":"打烊中");
        //获取操作redis的string类型的对象
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(STATUS,status);
        return Result.success();
    }

    /**
     *商家查询营业状态
     * @return
     */
    @ApiOperation("查询营业状态")
    @GetMapping("/status")
    public Result<Integer> getShopStatus(){
        Integer shop_status = (Integer) redisTemplate.opsForValue().get(STATUS);
        return Result.success(shop_status);
    }
}
