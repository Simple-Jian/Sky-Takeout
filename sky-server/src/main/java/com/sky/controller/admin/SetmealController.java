package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商家套餐相关接口
 */
@Slf4j
@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "管理员操作套餐相关接口")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @ApiOperation("添加套餐")
    @PostMapping
    @CacheEvict(cacheNames = "setmealCache",key="#setmealDTO.id")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐:{}", setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }

    /**
     * 分页条件查询套餐
     *
     * @param
     * @return
     */
    @ApiOperation("分页条件查询套餐")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(Long categoryId, String name,
                                        Integer page, Integer pageSize,
                                        Integer status) {
        SetmealPageQueryDTO setmealPageQueryDTO = new SetmealPageQueryDTO(page, pageSize, name,
                categoryId, status);
        log.info("分页条件查询套餐:{}", setmealPageQueryDTO);
        PageResult result = setmealService.page(setmealPageQueryDTO);
        return Result.success(result);
    }

    /**
     * 更改套餐状态,
     * 原则上套餐状态更改后对应菜品如果处于停售状态也应该更改状态
     * 此处没有添加菜品起售停售状态的功能,
     * 如果有,可以先根据套餐查询菜品id,在进行更改
     *
     * @param status
     * @return
     */
    @ApiOperation("更改套餐状态")
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries=true)
    public Result updateStatus(@PathVariable Integer status, Long id) {
        log.info("更改套餐状态为,id:{},status:{}", id, status ==
                        StatusConstant.ENABLE ? "起售" : "停售");
        setmealService.updateStatus(status, id);
        return Result.success();
    }
}
