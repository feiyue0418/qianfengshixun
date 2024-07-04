package com.example.authority.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.CommonAuth;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.dto.UserDto;
import com.example.authority.dto.UserPasswordDto;
import com.example.authority.entity.User;
import com.example.authority.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户的controller类")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 新增/修改
     *
     * @param user
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增或者修改用户信息接口")
    @CommonAuth
    public Result save(@RequestBody User user) {
        if (user.getId() == null) {
            //新增
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername());
            User existUser = userService.getOne(queryWrapper);
            if (null != existUser) {
                return Result.error("500", "用户名:" + user.getUsername() + " 已经存在，不能新增数据");
            }
        } else {
            //修改
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername());
            User existUser = userService.getOne(queryWrapper);
            //1.修改了用户的username
            //2.没有修改用户的username
            if (null != existUser && !existUser.getId().equals(user.getId())) {
                return Result.error("500", "用户名:" + user.getUsername() + "已经存在，不能修改数据");
            }
        }
        boolean b = userService.saveOrUpdate(user);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 登录
     *
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            return userService.login(userDto);
        } else {
            return Result.error("500", "用户名或者密码不能为空，请重新输入");
        }
    }

    /**
     * 退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @CommonAuth
    public Result logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        Boolean delete = stringRedisTemplate.delete(token);
        if (delete) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 退出
     *
     * @param userPasswordDto
     * @return
     */
    @PostMapping("/updatePassword")
    @CommonAuth
    public Result updatePassword(@RequestBody UserPasswordDto userPasswordDto) {
        String newPassword = userPasswordDto.getNewPassword();
        String confirmPassword = userPasswordDto.getConfirmPassword();
        Integer id = userPasswordDto.getId();
        if (StringUtils.isBlank(newPassword) || StringUtils.isBlank(confirmPassword)) {
            return Result.error("500", "缺少信息");
        }
        if (!newPassword.equals(confirmPassword)) {
            return Result.error("500", "两次输入密码不一致");
        }
        User user = userService.getById(id);
        if (user.getPassword().equals(userPasswordDto.getPassword())) {
            user.setPassword(newPassword);
            userService.updateById(user);
            return Result.success();
        } else {
            return Result.error("500", "输入密码有误，请重新输入");
        }

    }

    /**
     * 注册
     *
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            userDto.setRoleId(10);
            return userService.register(userDto);
        } else {
            return Result.error("500", "用户名或者密码不能为空，请重新输入");
        }
    }

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    @CommonAuth
    public Result findById(@PathVariable Integer id) {
        User user = userService.getById(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error();
        }
    }

    /**
     * 获取首页数据
     * @return
     */
    @GetMapping("/home")
    @CommonAuth
    public Result home() {
       Map<String,Object> result = new HashMap<>();
       return Result.success(result);
    }

    /**
     * 获取echart数据
     *
     * @param
     * @return
     */
    @GetMapping("/echart")
    @CommonAuth
    public Result echart() {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> nameList = new ArrayList<>();
        List<Long> valueList = new ArrayList<>();
        List<Map<String, Object>> resultList = userService.echart();
        for (Map<String, Object> map : resultList) {
            nameList.add(map.get("name").toString());
            valueList.add((Long) map.get("value"));
        }
        resultMap.put("list1",nameList);
        resultMap.put("list2",valueList);
        resultMap.put("list3",resultList);
        return Result.success(resultMap);
    }

    /**
     * @param
     * @return
     */
    @GetMapping("/findAll")
    @CommonAuth
    public Result findAll() {
        return Result.success(userService.list());
    }

    /**
     * @param
     * @return
     */
    @GetMapping("/findAllByRoleId/{roleId}")
    @NoAuth
    public Result findAllByRoleId(@PathVariable(name = "roleId") Integer roleId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        return Result.success(userService.list(queryWrapper));
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        boolean b = userService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 根据id批量删除
     *
     * @param idList
     * @return
     */
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> idList) {
        boolean b = userService.removeByIds(idList);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 分页查询
     *
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param username：用户名
     * @param email：邮箱
     * @return
     */
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "roleId", defaultValue = "") Integer roleId,
                           @RequestParam(name = "username", defaultValue = "") String username,
                           @RequestParam(name = "email", defaultValue = "") String email) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        if (StringUtils.isNotBlank(email)) {
            queryWrapper.like("email", email);
        }
        if(roleId != null){
            queryWrapper.eq("role_id",roleId);
        }
        Page<User> userPage = userService.page(page, queryWrapper);
        return Result.success(userPage);
    }
}
