package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        //MD5加密密码
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public int save(EmployeeDTO employeeDTO) {
        //将DTO对象转换为实体对象
        Employee employee = new Employee();
        //拷贝实体对象,属性名相同则拷贝
        BeanUtils.copyProperties(employeeDTO, employee);
        //设置创建时间,默认状态等
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setStatus(StatusConstant.ENABLE);
        //TODO 设置创建人和修改人id 后期需要改为当前登录用户的ID
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        int r = employeeMapper.insert(employee);
        return r;
    }

    @Override
    public PageResult getPages(String name, int page, int pageSize) {
        //设置分页参数
        PageHelper.startPage(page, pageSize);
        //执行查询
        Page<Employee> p = (Page) employeeMapper.getPages(name);
        //封装到pageBean对象中
        PageResult result = new PageResult(p.getTotal(), p.getResult());
        log.info("查询到的员工总数:{}", p.getTotal());
        return result;
    }

    /**
     * 启用/禁用员工
     *
     * @param status
     * @param id
     */
    @Override
    public void empStatusChange(Integer status, Long id) {
        Employee emp = new Employee();
        emp.setStatus(status);
        emp.setId(id);
        emp.setUpdateTime(LocalDateTime.now());
        emp.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.updateEmp(emp);
    }

    @Override
    public Employee getById(Long id) {
        Employee e=employeeMapper.getById(id);
        return e;
    }

    @Override
    public void updateEmp(Employee emp) {
       emp.setUpdateTime(LocalDateTime.now());
       emp.setUpdateUser(BaseContext.getCurrentId());
       employeeMapper.updateEmp(emp);
    }

}
