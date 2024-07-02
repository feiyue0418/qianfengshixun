package com.ziru.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziru.bean.Admins;
import com.ziru.mapper.AdminsMapper;
import com.ziru.service.AdminsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper,Admins> implements AdminsService {

    @Resource
    AdminsMapper adminsMapper;

    @Override
    public List<Admins> list() {
        return adminsMapper.list();
    }
}
