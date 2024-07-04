package com.example.authority.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.entity.News;
import com.example.authority.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资讯表 前端控制器
 * </p>
 * entity:  News
 * @author 程序员云翼
 * @since 2024-04-20
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;


    /**
     * 新增/修改
     * @param news
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody News news){
        boolean b = newsService.saveOrUpdate(news);
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
        boolean b = newsService.removeById(id);
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
        boolean b = newsService.removeByIds(idList);
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
        return Result.success(newsService.list());
    }
    /**
     * 根据id查询信息
     * @return
     */
    @GetMapping("/findById/{id}")
    @NoAuth
    public Result findById(@PathVariable("id") Integer id){
        News news = newsService.getById(id);
        if(null != news){
            return Result.success(news);
        }else{
            return Result.error("500","找不到文章信息");
        }
    }
    /**
     * 根据类型获取数据
     * @return
     */
    @GetMapping("/findNewListByType")
    @NoAuth
    public Result findNewListByType(@RequestParam("sort") String sort){
        return Result.success(newsService.findNewListByType(sort));
    }

    @PutMapping("/updateCount/{id}")
    @NoAuth
    public Result updateCount(@PathVariable Integer id) {
        newsService.updateCount(id);
        return Result.success();
    }
    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param title：标题
     * @return
     */
    @GetMapping("/findPage")
    @NoAuth
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "title",defaultValue = "") String title,
                           @RequestParam(name = "type",defaultValue = "") String type){
        Page<News> page = new Page<>(pageNum,pageSize);
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(title)){
            queryWrapper.like("title",title);
        }
        if(StringUtils.isNotBlank(type)){
            queryWrapper.like("type",type);
        }
        Page<News> newsPage = newsService.page(page, queryWrapper);
        return Result.success(newsPage);
    }

}

