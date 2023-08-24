package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.sky.result.Result.success;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return success();
    }

    /**
     *
     * @param employeeDTO
     * @return
     */
    @ApiOperation("添加員工")
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        employeeService.save(employeeDTO);
        return success();
    }

    @ApiOperation("分页查询员工")
    @GetMapping("/page")
    public Result<PageResult> getPages(EmployeePageQueryDTO epqd){
        int page=epqd.getPage();
        int pageSize=epqd.getPageSize();
        String name=epqd.getName();
        log.info("分页查询员工数据:页码{},每页条数{}", page, pageSize);
        PageResult result = employeeService.getPages(name,page,pageSize);
        return success(result);
    }

    @ApiOperation("启用/禁用员工账号")
    @PostMapping("/status/{status}")
    public Result empStatusChange(@PathVariable Integer status, Long id){
        log.info("启用/禁用员工账号:{},{}",id,status);
        employeeService.empStatusChange(status,id);
        return success();
    }

    @ApiOperation("根据id查询员工")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工:{}",id);
        Employee e=employeeService.getById(id);
        return Result.success(e);
    }

    @ApiOperation("修改员工信息")
    @PutMapping
    public Result updateEmp(@RequestBody Employee employee){

        employeeService.updateEmp(employee);
        return Result.success();
    }
}
