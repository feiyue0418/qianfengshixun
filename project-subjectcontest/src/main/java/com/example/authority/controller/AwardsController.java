package com.example.authority.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.CommonAuth;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.entity.Awards;
import com.example.authority.entity.User;
import com.example.authority.service.AwardsService;
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
@RequestMapping("/awards")
public class AwardsController {

    @Autowired
    private AwardsService awardsService;


    /**
     * 新增/修改
     * @param awards
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Awards awards){
        boolean b = awardsService.saveOrUpdate(awards);
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
        boolean b = awardsService.removeById(id);
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
        boolean b = awardsService.removeByIds(idList);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
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
        List<Map<String, Object>> resultList = awardsService.echart();

        resultMap.put("list3",resultList);
        return Result.success(resultMap);
    }

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping("/findAll")
    @NoAuth
    public Result findAll(){
        return Result.success(awardsService.list());
    }
    /**
     * 根据id查询信息
     * @return
     */
    @GetMapping("/findById/{id}")
    @NoAuth
    public Result findById(@PathVariable("id") Integer id){
        Awards awards = awardsService.getById(id);
        if(null != awards){
            return Result.success(awards);
        }else{
            return Result.error("500","找不到文章信息");
        }
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @return
     */
    @GetMapping("/findPage")
    @NoAuth
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "userId",defaultValue = "") Integer userId,
                           @RequestParam(name = "gameId",defaultValue = "") Integer gameId){
            Page<Awards> page = new Page<>(pageNum,pageSize);
            QueryWrapper<Awards> queryWrapper = new QueryWrapper<>();
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
            Page<Awards> awardsPage = awardsService.page(page, queryWrapper);
            return Result.success(awardsPage);
    }

}

