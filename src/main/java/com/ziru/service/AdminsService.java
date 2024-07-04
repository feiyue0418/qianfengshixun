package com.ziru.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ziru.bean.Admins;
import com.ziru.bean.House;

import java.util.List;

public interface AdminsService extends IService<Admins> {

    /**
     * 查询所有的管理员
     * @return
     */
    List<Admins> list();


}
