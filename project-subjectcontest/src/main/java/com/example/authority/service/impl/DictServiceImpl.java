package com.example.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.authority.entity.Dict;
import com.example.authority.entity.Role;
import com.example.authority.mapper.DictMapper;
import com.example.authority.mapper.RoleMapper;
import com.example.authority.service.DictService;
import com.example.authority.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
}
