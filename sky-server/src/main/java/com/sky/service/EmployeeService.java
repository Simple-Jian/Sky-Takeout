package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     *
     * @param employeeDTO
     * @return
     */
    int save(EmployeeDTO employeeDTO);
    //分页查询员工
    PageResult getPages(String name,int page, int pageSize);
    //启用/禁用员工
    void empStatusChange(Integer status, Long id);
    //根据id查询员工
    Employee getById(Long id);
    //更新员工信息
    void updateEmp(Employee emp);
}
