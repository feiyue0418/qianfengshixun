package com.example.authority.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.CommonAuth;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.entity.Application;
import com.example.authority.entity.User;
import com.example.authority.service.ApplicationService;
import com.example.authority.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * @author 程序员云翼
 */
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    /**
     * 新增/修改
     * @param application
     * @return
     */
    @PostMapping("/save")
    @CommonAuth
    public Result save(@RequestBody Application application){
        User currentUser = JwtUtils.getCurrentUser();
        Integer gameId = application.getGameId();
        QueryWrapper<Application> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",currentUser.getId());
        queryWrapper.eq("game_id",gameId);
        Application one = applicationService.getOne(queryWrapper);
        if(one != null){
            return Result.error("500","你已经报名了，无需重复报名");
        }
        application.setUserId(currentUser.getId());
        boolean b = applicationService.saveOrUpdate(application);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        boolean b = applicationService.removeById(id);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     * 根据id批量删除
     * @param idList
     * @return
     */
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> idList){
        boolean b = applicationService.removeByIds(idList);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     * 审核
     * @param application
     * @return
     */
    @PostMapping("/checkStatus")
    public Result checkStatus(@RequestBody Application application){
        boolean b = applicationService.saveOrUpdate(application);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping("/findAll")
    @CommonAuth
    public Result findAll(){
        return Result.success(applicationService.list());
    }


    /**
     * @return
     */
    @GetMapping("/echarts")
    @CommonAuth
    public Result echarts(){
        Map<String, Object> resultMap = new HashMap<>();
        List<String> nameList = new ArrayList<>();
        List<Long> valueList = new ArrayList<>();
        List<Map<String, Object>> resultList = applicationService.echart();
        for (Map<String, Object> map : resultList) {
            nameList.add(map.get("name").toString());
            valueList.add((Long) map.get("total"));
        }
        resultMap.put("list1",nameList);
        resultMap.put("list2",valueList);
        resultMap.put("list3",resultList);
        return Result.success(resultMap);
    }
    /**
     * 根据id查询信息
     * @return
     */
    @GetMapping("/findById/{id}")
    @NoAuth
    public Result findById(@PathVariable("id") Integer id){
        Application application = applicationService.getById(id);
        if(null != application){
            return Result.success(application);
        }else{
            return Result.error("500","找不到文章信息");
        }
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param userId：用户id
     * @return
     */
    @GetMapping("/findPage")
    @NoAuth
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "userId",defaultValue = "") Integer userId,
                           @RequestParam(name = "gameId",defaultValue = "") Integer gameId){
            Page<Application> page = new Page<>(pageNum,pageSize);
            QueryWrapper<Application> queryWrapper = new QueryWrapper<>();
            if(userId != null){
                queryWrapper.eq("user_id",userId);
            }
            if(gameId != null){
                queryWrapper.eq("game_id",gameId);
            }
            User currentUser = JwtUtils.getCurrentUser();
            if(currentUser.getRoleId() == 10){
                //角色是学生
                queryWrapper.eq("user_id",currentUser.getId());
            }
            Page<Application> applicationPage = applicationService.page(page, queryWrapper);
            return Result.success(applicationPage);
    }

}

