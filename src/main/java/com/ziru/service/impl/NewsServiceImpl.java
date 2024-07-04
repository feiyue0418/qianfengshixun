package com.ziru.service.impl;

import com.ziru.bean.News;
import com.ziru.mapper.NewsMapper;
import com.ziru.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资讯表 服务实现类
 * </p>
 *
 * @author bruce
 * @since 2024-07-03
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
