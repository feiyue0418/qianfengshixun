package com.example.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.authority.common.Result;
import com.example.authority.dto.UserDto;
import com.example.authority.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    Result register(UserDto userDto);

    Result login(UserDto userDto);

    List<Map<String, Object>> echart();


}
