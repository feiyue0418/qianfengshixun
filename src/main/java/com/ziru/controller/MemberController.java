package com.ziru.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziru.bean.Admins;
import com.ziru.bean.House;
import com.ziru.bean.Member;
import com.ziru.common.DateTimeUtils;
import com.ziru.common.Result;
import com.ziru.exception.CustomException;
import com.ziru.service.IMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bruce
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Resource
    IMemberService memberService;

    /**
     * 分页查询
     * @return
     */
    @ApiOperation(value = "根据昵称模糊查询分页", notes = "根据昵称模糊查询分页")
    @GetMapping("selectPage")
    public Result selectPage(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("nickname") String nickname){
        //使用分页插件
        PageHelper.startPage(pageNum,pageSize);
        //查询数据库，需要用到like查询
        List<Member> list = memberService.list(new QueryWrapper<Member>().like("nickname", nickname).eq("del", "0").orderByDesc("id"));
        //封装一下查询的结果集
        PageInfo<Member> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }

    /**
     * 新增接口
     * @return
     */
    @ApiOperation(value = "新增会员信息", notes = "新增会员信息")
    @PostMapping("add")
    public Result add(@RequestBody Member member){
        Member one = memberService.getOne(new QueryWrapper<Member>().eq("uname", member.getUname()).eq("del", "0"));
        if(one==null){
            member.setDel("0");//删除状态
            member.setCreatetime(DateTimeUtils.now()); //设置系统时间
            boolean b = memberService.save(member);
            if(b){
                return Result.success();
            }else{
                return Result.error();
            }
        }else{
            throw new CustomException("会员账号已存在");
        }
    }

    /**
     *  更新
     */
    @ApiOperation(value = "更新会员信息", notes = "更新会员信息")
    @PutMapping("update")
    public Result update(@RequestBody Member member){
        boolean b = memberService.updateById(member);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }


    /**
     * 删除
     */
    @ApiOperation(value = "根据ID删除会员信息", notes = "根据ID删除会员信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") @ApiParam(value = "会员主键ID")  Integer id){
        Member member = new Member();
        member.setId(id);
        member.setDel("1");//删除
        boolean b = memberService.updateById(member);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }


    /**
     * 会员登录
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody Member member){
        log.info("登录的信息是:"+member);
        Member a = memberService.getOne(new QueryWrapper<Member>().eq("uname", member.getUname()).eq("upass",member.getUpass()));
        if(a!=null){
            return Result.success(a);
        }else{
            return Result.error("账号或密码错误！");
        }
    }


    /**
     * 新增接口
     * @return
     */
    @ApiOperation(value = "注册会员信息", notes = "注册会员信息")
    @PostMapping("register")
    public Result register(@RequestBody Member member){
        Member one = memberService.getOne(new QueryWrapper<Member>().eq("uname", member.getUname()).eq("del", "0"));
        if(one==null){
            member.setRole("TENANT");
            member.setDel("0");//删除状态
            member.setCreatetime(DateTimeUtils.now()); //设置系统时间
            boolean b = memberService.save(member);
            if(b){
                return Result.success();
            }else{
                return Result.error();
            }
        }else{
            throw new CustomException("会员账号已存在");
        }
    }

}
