package com.example.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.authority.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {

    @Select("select count(*) as value ,r.name from sys_user u\n" +
            "left join sys_role r on u.role_id = r.id\n" +
            "where r.name is not NULL\n" +
            "group by u.role_id")
    List<Map<String, Object>> echart();

}
