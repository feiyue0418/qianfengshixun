package com.example.authority.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.common.Result;
import com.example.authority.entity.SysLog;
import com.example.authority.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;


    /**
     * 查询全部数据
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(@RequestParam(name = "type",defaultValue = "") String type){
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(type)){
            queryWrapper.eq("type",type);
        }
        return Result.success(sysLogService.list(queryWrapper));
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param username：操作用户名称
     * @param type：操作类型
     * @return
     */
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "username",defaultValue = "") String username,
                           @RequestParam(name = "type",defaultValue = "") String type){
        Page<SysLog> page = new Page<>(pageNum,pageSize);
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        if(StringUtils.isNotBlank(type)){
            queryWrapper.like("type",type);
        }
        Page<SysLog> sysLogPage = sysLogService.page(page, queryWrapper);
        return Result.success(sysLogPage);
    }
}
