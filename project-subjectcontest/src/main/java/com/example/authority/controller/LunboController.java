package com.example.authority.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.entity.Lunbo;
import com.example.authority.service.LunboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lunbo")
public class LunboController {

    @Autowired
    private LunboService lunboService;


    /**
     * 新增/修改
     * @param lunbo
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Lunbo lunbo){
        boolean b = lunboService.saveOrUpdate(lunbo);
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
        boolean b = lunboService.removeById(id);
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
        boolean b = lunboService.removeByIds(idList);
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
    public Result findAll(@RequestParam(name = "type",defaultValue = "") String type){
        QueryWrapper<Lunbo> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(type)){
            queryWrapper.eq("type",type);
        }
        return Result.success(lunboService.list(queryWrapper));
    }
    /**
     * 查询全部数据
     * @return
     */
    @GetMapping("/getAllUrlList")
    @NoAuth
    public Result getAllUrlList(){
        List<Lunbo> list = lunboService.list();
        List<String> urlList = list.stream().map(lunbo -> lunbo.getUrl()).collect(Collectors.toList());
        return Result.success(urlList);
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param name：名称
     * @param user：创建用户名称
     * @return
     */
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name,
                           @RequestParam(name = "user",defaultValue = "") String user){
        Page<Lunbo> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Lunbo> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        if(StringUtils.isNotBlank(user)){
            queryWrapper.like("user",user);
        }
        Page<Lunbo> lunboPage = lunboService.page(page, queryWrapper);
        return Result.success(lunboPage);
    }
}
