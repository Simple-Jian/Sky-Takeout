package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.sky.result.Result.success;

@Api(tags="菜品管理控制类")
@Slf4j
@RestController("adminDishController")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品:{}",dishDTO);
        dishService.save(dishDTO);
        //菜品有改动时,清理缓存数据
//        String key="dish_"+dishDTO.getCategoryId();
//        redisTemplate.delete(key);
        cleanCache("dish_"+dishDTO.getCategoryId());
        return Result.success();
    }

    /**
     * 分页查询菜品
     * @param categoryId
     * @param name
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询菜品")
    public Result<PageResult> page(Long categoryId,String name,
                                   Integer page,Integer pageSize,
                                   Integer status){
        log.info("分页查询员工数据:页码{},每页条数{}", page, pageSize);
        PageResult result = dishService.page(categoryId,name,page,pageSize,status);
        return success(result);
    }

    /**
     * 删除菜品,同时删除对应的口味,套餐,如果套餐在售,则不能删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result deleteByIds(String ids){
        log.info("根据id批量删除菜品:{}",ids);
        dishService.deleteByIds(ids);

        //由于菜品对应了多个可能得分类Id,此处直接清理掉所有以dish_开头的缓存数据
//        Set keys = redisTemplate.keys("dish_*");   //获取所有keys,存入到集合中
//        redisTemplate.delete(keys);
        cleanCache("dish_*");

        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品,并返回相关口味信息")
    public Result<DishVO> getById1(@PathVariable Long id){
        DishVO dishVO=dishService.getById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品及对应口味信息")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        log.info("修改菜品:{}",dishDTO);
        dishService.updateDishAndFlavor(dishDTO);
        //由于菜品修改时可能改动分类等信息,比较复杂,此处直接清理掉所有以dish_开头的缓存数据
//        Set keys = redisTemplate.keys("dish_*");   //获取所有keys,存入到集合中
//        redisTemplate.delete(keys);
        cleanCache("dish_*");

        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> getByCategoryId(Long categoryId){
        List<Dish> result=dishService.list(categoryId);
        return Result.success(result);
    }
    //抽取一个用于清除缓存的函数
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
