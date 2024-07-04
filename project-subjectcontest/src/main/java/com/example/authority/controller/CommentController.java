package com.example.authority.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.CommonAuth;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.entity.Comment;
import com.example.authority.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 * entity:  Comment
 * @author 程序员云翼
 * @since 2024-05-01
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 新增/修改
     * @param comment
     * @return
     */
    @PostMapping("/save")
    @CommonAuth
    public Result save(@RequestBody Comment comment){
        if(StringUtils.isBlank(comment.getContent())){
            return Result.error("500","内容不能为空");
        }
        if(comment.getId() == null){
            commentService.add(comment);
        }else{
            commentService.updateById(comment);
        }
        return Result.success();
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    @CommonAuth
    public Result deleteById(@PathVariable Integer id){
        boolean b = commentService.removeById(id);
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
    @CommonAuth
    public Result deleteBatch(@RequestBody List<Integer> idList){
        for (Integer id : idList) {
            commentService.deepDelete(id);
        }
        return Result.success();
    }
    /**
     * 查询全部数据
     * @return
     */
    @GetMapping("/findAll")
    @CommonAuth
    public Result findAll(){
        return Result.success(commentService.list());
    }
    /**
     * 根据id查询信息
     * @return
     */
    @GetMapping("/findById/{id}")
    @CommonAuth
    public Result findById(@PathVariable("id") Integer id){
        Comment comment = commentService.getById(id);
        if(null != comment){
            return Result.success(comment);
        }else{
            return Result.error("500","找不到文章信息");
        }
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param content：内容
     * @return
     */
    @GetMapping("/findPage")
    @NoAuth
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "content",defaultValue = "") String content,
                           @RequestParam(name = "relationId",defaultValue = "") Integer relationId){
            Page<Comment> page = new Page<>(pageNum,pageSize);
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            if(StringUtils.isNotBlank(content)){
                queryWrapper.like("content",content);
            }
            if(null != relationId){
                queryWrapper.eq("relation_id",relationId);
            }else{
                queryWrapper.notIn("relation_id",-1);
            }
            Page<Comment> commentPage = commentService.page(page, queryWrapper);
            return Result.success(commentPage);
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/deepDelete/{id}")
    @CommonAuth
    public Result deepDelete(@PathVariable Integer id) {
        commentService.deepDelete(id);
        return Result.success();
    }

    /**
     * 查询前台评论列表
     */
    @GetMapping("/selectTree/{fid}/{module}")
    @NoAuth
    public Result selectTree(@PathVariable Integer fid,
                             @PathVariable String module) {
        List<Comment> list = commentService.selectTree(fid,module);
        return Result.success(list);
    }
    /**
     * 查询前台评论数量
     */
    @GetMapping("/selectCount/{fid}/{module}")
    @NoAuth
    public Result selectCount(@PathVariable Integer fid,
                              @PathVariable String module) {
        Integer count = commentService.selectCount(fid,module);
        return Result.success(count);
    }

}

