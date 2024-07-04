package com.example.authority.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.authority.common.Result;
import com.example.authority.entity.Role;
import com.example.authority.entity.RoleMenu;
import com.example.authority.entity.User;
import com.example.authority.service.RoleService;
import com.example.authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * 给角色分配菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/saveRoleMenu/{roleId}")
    public Result saveRoleMenu(@PathVariable(name = "roleId") Integer roleId, @RequestBody List<Integer> menuIds){
        return roleService.saveRoleMenu(roleId,menuIds);
    }

    /**
     * 给角色分配菜单
     * @param roleId
     * @return
     */
    @PostMapping("/selectMenuByRole/{roleId}")
    public Result selectMenuByRole(@PathVariable(name = "roleId") Integer roleId){
        List<RoleMenu> roleMenuList = roleService.selectMenuByRole(roleId);
        List<Integer> menuIdList = roleMenuList.stream().map(roleMenu -> roleMenu.getMenuId()).collect(Collectors.toList());
        return Result.success(menuIdList);
    }

    /**
     * 新增/修改
     * @param role
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Role role){
        boolean b = roleService.saveOrUpdate(role);
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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",id);
        List<User> list = userService.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            return Result.error("500","角色信息被用户使用，不能删除");
        }
        boolean b = roleService.removeById(id);
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
        for (Integer roleId : idList) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",roleId);
            List<User> list = userService.list(queryWrapper);
            if(CollectionUtils.isNotEmpty(list)){
                return Result.error("500","角色信息被用户使用，不能删除");
            }
        }
        boolean b = roleService.removeByIds(idList);
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
    public Result findAll(){
        return Result.success(roleService.list());
    }

    /**
     * 分页查询
     * @param pageNum：页码
     * @param pageSize：每页条数
     * @param name：角色名称
     * @return
     */
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name){
        Page<Role> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        Page<Role> rolePage = roleService.page(page, queryWrapper);
        return Result.success(rolePage);
    }
}
