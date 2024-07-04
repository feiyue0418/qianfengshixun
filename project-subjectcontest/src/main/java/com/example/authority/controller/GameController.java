package com.example.authority.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.annotation.NoAuth;
import com.example.authority.common.Result;
import com.example.authority.entity.Game;
import com.example.authority.service.GameService;
import com.example.authority.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * @author 程序员云翼
 */
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 新增/修改
     * @param game
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Game game){
        String startTime = game.getStartTime();
        String endTime = game.getEndTime();
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)){
            return Result.error("500","赛事的开始时间和结束时间不能为空");
        }
        boolean b = gameService.saveOrUpdate(game);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    @PutMapping("/updateCount/{id}")
    @NoAuth
    public Result updateCount(@PathVariable Integer id) {
        gameService.updateCount(id);
        return Result.success();
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        boolean b = gameService.removeById(id);
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
        boolean b = gameService.removeByIds(idList);
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
        return Result.success(gameService.list());
    }
    /**
     * 根据id查询信息
     * @return
     */
    @GetMapping("/findById/{id}")
    @NoAuth
    public Result findById(@PathVariable("id") Integer id) throws ParseException {
        Game game = gameService.getById(id);
        if(null != game){
            String startTime = game.getStartTime();
            String endTime = game.getEndTime();
            String nowTime = DateUtils.getNowDate();
            if(startTime.compareTo(nowTime) > 0){
                game.setStatus(0); //赛事未开始
            }else if(nowTime.compareTo(startTime) >= 0 && endTime.compareTo(nowTime) >= 0){
                game.setStatus(1); //赛事开始
            }else{
                game.setStatus(2); //赛事结束
            }
            return Result.success(game);
        }else{
            return Result.error("500","找不到信息");
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
            Page<Game> page = new Page<>(pageNum,pageSize);
            QueryWrapper<Game> queryWrapper = new QueryWrapper<>();
            if(StringUtils.isNotBlank(name)){
                queryWrapper.like("name",name);
            }
            Page<Game> gamePage = gameService.page(page, queryWrapper);
            return Result.success(gamePage);
    }

}

