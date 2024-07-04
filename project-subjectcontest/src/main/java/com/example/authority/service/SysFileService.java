package com.example.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.authority.entity.SysFile;

import java.util.List;

public interface SysFileService extends IService<SysFile> {
    List<SysFile> getByMD5(String md5);
}
