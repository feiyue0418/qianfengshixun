package com.example.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.authority.entity.SysFile;
import com.example.authority.mapper.SysFileMapper;
import com.example.authority.service.SysFileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {
    @Override
    public List<SysFile> getByMD5(String md5) {
        QueryWrapper<SysFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        queryWrapper.eq("md5",md5);
        return this.baseMapper.selectList(queryWrapper);
    }
}
