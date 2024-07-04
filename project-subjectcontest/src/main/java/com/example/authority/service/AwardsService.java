package com.example.authority.service;

import com.example.authority.entity.Awards;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-08
 */
public interface AwardsService extends IService<Awards> {

    List<Map<String, Object>> echart();
}
