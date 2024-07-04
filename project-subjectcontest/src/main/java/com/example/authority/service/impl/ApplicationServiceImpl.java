package com.example.authority.service.impl;

import com.example.authority.entity.Application;
import com.example.authority.mapper.ApplicationMapper;
import com.example.authority.service.ApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-08
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Override
    public List<Map<String, Object>> echart() {
        return this.baseMapper.echart();
    }
}
