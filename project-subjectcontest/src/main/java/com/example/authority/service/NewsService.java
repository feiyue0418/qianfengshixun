package com.example.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.authority.entity.News;

import java.util.List;

/**
 * <p>
 * 资讯表 服务类
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-04
 */
public interface NewsService extends IService<News> {
    List<News> findNewListByType(String sort);

    void updateCount(Integer id);
}
