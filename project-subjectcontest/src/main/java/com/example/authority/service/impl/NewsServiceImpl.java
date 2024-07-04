package com.example.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.authority.entity.News;
import com.example.authority.mapper.NewsMapper;
import com.example.authority.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资讯表 服务实现类
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-04
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    @Override
    public List<News> findNewListByType(String sort) {
        return this.baseMapper.findNewListByType(sort);
    }

    @Override
    public void updateCount(Integer id) {
        this.baseMapper.updateCount(id);
    }
}
