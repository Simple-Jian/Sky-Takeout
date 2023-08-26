package com.sky.controller.user;

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
 * 用户关于商店的接口,如营业状态
 */
@Api(tags = "店铺相关接口")
@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *用户查询营业状态
     * @return
     */
    @ApiOperation("查询营业状态")
    @GetMapping("/status")
    public Result<Integer> getShopStatus(){
        Integer shop_status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        return Result.success(shop_status);
    }
}
