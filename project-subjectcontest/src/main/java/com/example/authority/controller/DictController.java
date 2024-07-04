package com.example.authority.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.Log;
import com.example.authority.common.Result;
import com.example.authority.entity.Dict;
import com.example.authority.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;


    /**
     * 新增/修改
     * @param dict
     * @return
     */
    @PostMapping("/save")
    @Log(record = "新增/修改字典分页",type = "新增/修改")
    public Result save(@RequestBody Dict dict){
        boolean b = dictService.saveOrUpdate(dict);
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
    @Log(record = "根据id删除字典",type = "删除")
    public Result deleteById(@PathVariable Integer id){
        boolean b = dictService.removeById(id);
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
    @Log(record = "批量删除字典",type = "删除")
    public Result deleteBatch(@RequestBody List<Integer> idList){
        boolean b = dictService.removeByIds(idList);
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
    @Log(record = "查询全部字典",type = "查询")
    public Result findAll(@RequestParam(name = "type",defaultValue = "") String type){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(type)){
            queryWrapper.eq("type",type);
        }
        return Result.success(dictService.list(queryWrapper));
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param name：角色名称
     * @return
     */
    @GetMapping("/findPage")
    @Log(record = "查询字典分页",type = "查询")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name){
        Page<Dict> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        Page<Dict> dictPage = dictService.page(page, queryWrapper);
        return Result.success(dictPage);
    }
}
