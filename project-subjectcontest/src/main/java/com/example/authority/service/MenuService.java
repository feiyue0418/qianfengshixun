package com.example.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.authority.entity.Menu;
import com.example.authority.entity.Role;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> findAll(String name);
}
