package com.example.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.authority.entity.Menu;
import com.example.authority.entity.Role;
import com.example.authority.mapper.MenuMapper;
import com.example.authority.mapper.RoleMapper;
import com.example.authority.service.MenuService;
import com.example.authority.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public List<Menu> findAll(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_num");
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        List<Menu> allList = this.baseMapper.selectList(queryWrapper);
        //构造一级菜单
        List<Menu> parentList = allList.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu parentMenu : parentList) {
            List<Menu> childrenList = allList.stream().filter(menu -> parentMenu.getId().equals(menu.getPid())).collect(Collectors.toList());
            parentMenu.setChildren(childrenList);
        }
        return parentList;
    }
}
