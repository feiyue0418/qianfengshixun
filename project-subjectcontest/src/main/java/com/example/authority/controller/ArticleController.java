package com.example.authority.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.entity.Article;
import com.example.authority.service.ArticleService;
import com.example.authority.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 新增/修改
     * @param article
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Article article){
        if(article.getId() == null){
            article.setUser(JwtUtils.getCurrentUser().getUsername());
        }
        boolean b = articleService.saveOrUpdate(article);
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
        boolean b = articleService.removeById(id);
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
        boolean b = articleService.removeByIds(idList);
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
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(type)){
            queryWrapper.eq("type",type);
        }
        return Result.success(articleService.list(queryWrapper));
    }
    /**
     * 根据id查询文章信息
     * @return
     */
    @GetMapping("/findById/{id}")
    @NoAuth
    public Result findById(@PathVariable("id") Integer id){
        Article article = articleService.getById(id);
        if(null != article){
            return Result.success(article);
        }else{
            return Result.error("500","找不到文章信息");
        }
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param name：文章名称
     * @param user：创建用户名称
     * @return
     */
    @GetMapping("/findPage")
    @NoAuth
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name,
                           @RequestParam(name = "user",defaultValue = "") String user){
        Page<Article> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        if(StringUtils.isNotBlank(user)){
            queryWrapper.like("user",user);
        }
        Page<Article> articlePage = articleService.page(page, queryWrapper);
        return Result.success(articlePage);
    }
}
