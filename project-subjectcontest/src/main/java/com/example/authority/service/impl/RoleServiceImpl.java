package com.example.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.authority.common.Result;
import com.example.authority.entity.Menu;
import com.example.authority.entity.Role;
import com.example.authority.entity.RoleMenu;
import com.example.authority.mapper.MenuMapper;
import com.example.authority.mapper.RoleMapper;
import com.example.authority.mapper.RoleMenuMapper;
import com.example.authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    @Transactional
    public Result saveRoleMenu(Integer roleId, List<Integer> menuIds) {
        //保存角色菜单关联数据前先删除roleId本来的菜单信息
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);
        //保存角色菜单关联数据
        for (Integer menuId : menuIds) {
            Menu menu = menuMapper.selectById(menuId);
            Integer pid = menu.getPid();
            if(pid != null && !menuIds.contains(pid)){
                //该菜单是二级菜单并且父菜单不在menuIds里面
                QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
                wrapper.eq("role_id",roleId);
                wrapper.eq("menu_id",pid);
                RoleMenu existRoleMenu = roleMenuMapper.selectOne(wrapper);
                if(existRoleMenu == null){
                    existRoleMenu = new RoleMenu();
                    existRoleMenu.setRoleId(roleId);
                    existRoleMenu.setMenuId(pid);
                    roleMenuMapper.insert(existRoleMenu);
                }
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
        return Result.success();
    }

    @Override
    public List<RoleMenu> selectMenuByRole(Integer roleId) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        return roleMenuMapper.selectList(queryWrapper);
    }
}
