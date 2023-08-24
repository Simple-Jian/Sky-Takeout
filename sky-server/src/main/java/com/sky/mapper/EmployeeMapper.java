package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

//    @Insert("insert into employee( name, username, phone, sex, id_number, " +
//            "status, create_time, update_time, create_user, update_user) values(" +
//            "#{name},#{username},#{ phone},#{sex},#{idNumber},#{status},#{createTime}," +
//            "#{updateTime},#{createUser},#{updateUser})")

    public int insert(Employee emp);

    //分页查询
    List<Employee> getPages(String name);

    /**
     * 更新员工,也可以用来改变员工状态
     * @param emp
     */
    void updateEmp(Employee emp);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Select("select * from employee where id=#{id}")
    Employee getById(Long id);
}
