package com.example.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.authority.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资讯表 Mapper 接口
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-04
 */
public interface NewsMapper extends BaseMapper<News> {
    List<News> findNewListByType(@Param("sort") String sort);

    void updateCount(@Param("id") Integer id);
}
