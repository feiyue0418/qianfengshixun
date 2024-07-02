package com.ziru.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziru.bean.Admins;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据库访问层
 * extends BaseMapper<Admins>  继承父接口中常用方法
 */
public interface AdminsMapper extends BaseMapper<Admins> {

    /**
     * 查询所有的管理员
     * @return
     */
    List<Admins> list();

}
