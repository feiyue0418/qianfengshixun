package com.example.authority.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.entity.Notice;
import com.example.authority.service.NoticeService;
import com.example.authority.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 新增/修改
     * @param notice
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Notice notice){
        if(notice.getId() == null){
            notice.setUser(JwtUtils.getCurrentUser().getUsername());
        }
        boolean b = noticeService.saveOrUpdate(notice);
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
        boolean b = noticeService.removeById(id);
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
        boolean b = noticeService.removeByIds(idList);
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
    public Result findAll(@RequestParam(name = "type",defaultValue = "") String type){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(type)){
            queryWrapper.eq("type",type);
        }
        return Result.success(noticeService.list(queryWrapper));
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param title：文章名称
     * @param user：创建用户名称
     * @return
     */
    @GetMapping("/findPage")
    @NoAuth
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "title",defaultValue = "") String title,
                           @RequestParam(name = "user",defaultValue = "") String user){
        Page<Notice> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(title)){
            queryWrapper.like("title",title);
        }
        if(StringUtils.isNotBlank(user)){
            queryWrapper.like("user",user);
        }
        Page<Notice> noticePage = noticeService.page(page, queryWrapper);
        return Result.success(noticePage);
    }
    /**
     * 获取最新公告
     * @return
     */

    @GetMapping("/findNewNoticeList")
    @NoAuth
    public Result findNewNoticeList(){
        List<Notice> noticeList = null;
        if(stringRedisTemplate.hasKey("newNoticeList")){
            String redisNoticeList = stringRedisTemplate.opsForValue().get("newNoticeList");
            noticeList = JSON.parseObject(redisNoticeList, List.class);
        }else{
            noticeList = noticeService.findNewNoticeList();
            stringRedisTemplate.opsForValue().set("newNoticeList",JSON.toJSONString(noticeList),5, TimeUnit.MINUTES);
        }
        return Result.success(noticeList);
    }
    /**
     * 获取最新公告
     * @return
     */

    @GetMapping("/findById/{id}")
    @NoAuth
    public Result findById(@PathVariable Integer id){
        Notice notice = noticeService.getById(id);
        if(null != notice){
            return Result.success(notice);
        }else{
            return Result.error();
        }
    }
}
