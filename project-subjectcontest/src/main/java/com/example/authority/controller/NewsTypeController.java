package com.example.authority.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.entity.NewsType;
import com.example.authority.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * @author 程序员云翼
 */
@RestController
@RequestMapping("/newsType")
public class NewsTypeController {

    @Autowired
    private NewsTypeService newsTypeService;


    /**
     * 新增/修改
     * @param newsType
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody NewsType newsType){
        boolean b = newsTypeService.saveOrUpdate(newsType);
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
        boolean b = newsTypeService.removeById(id);
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
        boolean b = newsTypeService.removeByIds(idList);
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
    @NoAuth
    public Result findAll(){
        return Result.success(newsTypeService.list());
    }
    /**
     * 根据id查询信息
     * @return
     */
    @GetMapping("/findById/{id}")
    @NoAuth
    public Result findById(@PathVariable("id") Integer id){
        NewsType newsType = newsTypeService.getById(id);
        if(null != newsType){
            return Result.success(newsType);
        }else{
            return Result.error("500","找不到文章信息");
        }
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param name：名称
     * @return
     */
    @GetMapping("/findPage")
    @NoAuth
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name){
            Page<NewsType> page = new Page<>(pageNum,pageSize);
            QueryWrapper<NewsType> queryWrapper = new QueryWrapper<>();
            if(StringUtils.isNotBlank(name)){
                queryWrapper.like("name",name);
            }
            Page<NewsType> newsTypePage = newsTypeService.page(page, queryWrapper);
            return Result.success(newsTypePage);
    }

}

