package com.example.authority.service;

import com.example.authority.entity.Game;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 赛事表 服务类
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-06-08
 */
public interface GameService extends IService<Game> {

    void updateCount(Integer id);
}
