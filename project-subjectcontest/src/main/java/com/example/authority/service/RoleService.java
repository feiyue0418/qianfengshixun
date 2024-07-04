package com.example.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.authority.common.Result;
import com.example.authority.entity.Role;
import com.example.authority.entity.RoleMenu;

import java.util.List;

public interface RoleService extends IService<Role> {
    Result saveRoleMenu(Integer roleId, List<Integer> menuIds);

    List<RoleMenu> selectMenuByRole(Integer roleId);
}
