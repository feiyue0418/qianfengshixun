package com.ziru.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import com.ziru.bean.House;
import com.ziru.common.Result;

import com.ziru.service.IHouseService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("house")
@Api("房源控制器")
@ApiResponses({
        @ApiResponse(code = 400, message = "请求参数错误"),
        @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"),
        @ApiResponse(code = 403, message = "没有权限操作"),
        @ApiResponse(code = 500, message = "服务器异常")
})
public class HouseController {

    @Resource
    IHouseService houseService;

    /**
     * 查询接口
     */
    @ApiOperation(value = "获取房源列表", notes = "获取房源列表")
    @GetMapping("list")
    public Result list(){
        List<House> adminsList = houseService.list(new QueryWrapper<House>().eq("del","0"));
        return Result.success(adminsList);
    }

    /**
     * 新增接口
     * @return
     */
    @ApiOperation(value = "新增房源信息", notes = "新增房源信息")
    @PostMapping("add")
    public Result add(@RequestBody House house){
        house.setDel("0");
        boolean b = houseService.save(house);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     *  更新
     */
    @ApiOperation(value = "更新房源信息", notes = "更新房源信息")
    @PutMapping("update")
    public Result update(@RequestBody House house){
        boolean b = houseService.updateById(house);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }


    /**
     * 删除
     */
    @ApiOperation(value = "根据ID删除房源信息", notes = "根据ID删除房源信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") @ApiParam(value = "房源主键ID")  Integer id){
        House house = new House();
        house.setId(id);
        house.setDel("1");//删除
        boolean b = houseService.updateById(house);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /*
    * 分页查询
    * @return
    * */
    @GetMapping("selectPage")
    public Result selectPage(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("title") String title){

        //使用分页插件
        PageHelper.startPage(pageNum,pageSize);
        List<House> list = houseService.list(new QueryWrapper<House>().like("title",title).eq("del","0"));
        PageInfo<House> pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }



}
