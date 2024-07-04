package com.ziru.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziru.bean.Member;
import com.ziru.bean.News;
import com.ziru.common.DateTimeUtils;
import com.ziru.common.Result;
import com.ziru.exception.CustomException;
import com.ziru.service.IMemberService;
import com.ziru.service.INewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 资讯表 前端控制器
 * </p>
 *
 * @author bruce
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    INewsService newsService;

    /**
     * 分页查询
     * @return
     */
    @ApiOperation(value = "根据标题模糊查询分页", notes = "根据标题模糊查询分页")
    @GetMapping("selectPage")
    public Result selectPage(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("title") String title){
        //使用分页插件
        PageHelper.startPage(pageNum,pageSize);
        //查询数据库，需要用到like查询
        List<News> list = newsService.list(new QueryWrapper<News>().like("title", title).eq("del", "0").orderByDesc("id"));
        //封装一下查询的结果集
        PageInfo<News> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }

    /**
     * 新增接口
     * @return
     */
    @ApiOperation(value = "新增资讯信息", notes = "新增资讯信息")
    @PostMapping("add")
    public Result add(@RequestBody News news){
        News one = newsService.getOne(new QueryWrapper<News>().eq("title", news.getTitle()).eq("del", "0"));
        if(one==null){
            news.setDel("0");//删除状态
            news.setCreateTime(DateTimeUtils.now()); //设置系统时间
            boolean b = newsService.save(news);
            if(b){
                return Result.success();
            }else{
                return Result.error();
            }
        }else{
            throw new CustomException("资讯不可重复");
        }
    }

    /**
     *  更新
     */
    @ApiOperation(value = "更新资讯信息", notes = "更新资讯信息")
    @PutMapping("update")
    public Result update(@RequestBody News news){
        boolean b = newsService.updateById(news);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }


    /**
     * 删除
     */
    @ApiOperation(value = "根据ID删除资讯信息", notes = "根据ID删除资讯信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") @ApiParam(value = "资讯主键ID")  Integer id){
        News news = new News();
        news.setId(id);
        news.setDel("1");//删除
        boolean b = newsService.updateById(news);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

}
