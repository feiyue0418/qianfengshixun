package com.example.authority.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.authority.common.Result;
import com.example.authority.dto.UserDto;
import com.example.authority.entity.Menu;
import com.example.authority.entity.User;
import com.example.authority.mapper.MenuMapper;
import com.example.authority.mapper.UserMapper;
import com.example.authority.service.UserService;
import com.example.authority.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 注册方法
     * @param userDto
     * @return
     */
    @Override
    public Result register(UserDto userDto) {
        String username = userDto.getUsername();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> users = userMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(users)){
            return Result.error("500","用户名已经存在，不能重复");
        }
        User user = new User();
        user.setRoleId(10);
        user.setUsername(username);
        user.setPassword(userDto.getPassword());
        userMapper.insert(user);
        return Result.success("注册成功");
    }

    /**
     * 登录方法
     * @param userDto
     * @return
     */
    @Override
    public Result login(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User existUser = userMapper.selectOne(queryWrapper);
        if(existUser != null){
            //登录成功
            String token = JwtUtils.generateToken(existUser.getId().toString(), password);
            String userJson = JSON.toJSONString(existUser);
            //将token保存到redis
            stringRedisTemplate.opsForValue().set(token,userJson,1, TimeUnit.DAYS);
            userDto.setId(existUser.getId());
            userDto.setToken(token);
            userDto.setNickname(existUser.getNickname());
            userDto.setHeaderUrl(existUser.getHeaderUrl());
            Integer roleId = existUser.getRoleId();
            //先给用户分配一个空的菜单集合
            userDto.setMenuList(new ArrayList<>());
            if(null != roleId){
                List<Menu> menuList = findTreeMenu(roleId);
                userDto.setMenuList(menuList);
                userDto.setRoleId(roleId);
            }
            return Result.success(userDto);
        }
        return Result.error("500","登录信息不正确，请重新输入");
    }

    @Override
    public List<Map<String, Object>> echart() {
        return userMapper.echart();
    }



    public List<Menu> findTreeMenu(Integer roleId){
        List<Menu> menuList = menuMapper.selectByIdList(roleId);
        //构造一级菜单
        List<Menu> parentList = menuList.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu parentMenu : parentList) {
            List<Menu> childrenList = menuList.stream().filter(menu -> parentMenu.getId().equals(menu.getPid())).collect(Collectors.toList());
            parentMenu.setChildren(childrenList);
        }
        return parentList;
    }
}
