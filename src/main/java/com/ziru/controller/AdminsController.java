package com.ziru.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ziru.bean.Admins;
import com.ziru.common.Result;
import com.ziru.exception.CustomException;
import com.ziru.service.AdminsService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 控制层
 */
@RestController
@RequestMapping("admins")
@Api("管理员控制器")
@ApiResponses({
        @ApiResponse(code = 400, message = "请求参数错误"),
        @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"),
        @ApiResponse(code = 403, message = "没有权限操作"),
        @ApiResponse(code = 500, message = "服务器异常")
})
public class AdminsController {

    @Resource
    AdminsService adminsService;

    /**
     * 查询接口
     */
    @ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
    @GetMapping("list")
    public Result list(){
        List<Admins> adminsList = adminsService.list();
        return Result.success(adminsList);
    }

    /**
     * 新增接口
     * @return
     */
    @ApiOperation(value = "新增管理员信息", notes = "新增管理员信息")
    @PostMapping("add")
    public Result add(@RequestBody Admins admins){
        //根据用户名查询对象是否存在于数据库中
        Admins adminDb = adminsService.getOne(new QueryWrapper<Admins>().eq("username", admins.getUsername()).eq("deleted", "0"));
        if(adminDb==null){
            boolean b = adminsService.save(admins);
            if(b){
                return Result.success();
            }else{
                return Result.error();
            }
        }else {
            //数据库存在这个账号
            throw new CustomException("用户名已存在");
        }
    }

    /**
     *  更新
     */
    @ApiOperation(value = "更新管理员信息", notes = "更新管理员信息")
    @PutMapping("update")
    public Result update(@RequestBody Admins admins){
        boolean b = adminsService.updateById(admins);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }


    /**
     * 删除
     */
    @ApiOperation(value = "根据ID删除管理员信息", notes = "根据ID删除管理员信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") @ApiParam(value = "管理员主键ID")  Integer id){
        Admins admins = new Admins();
        admins.setId(id);
        admins.setDeleted("1");//删除
        boolean b = adminsService.updateById(admins);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }


}
